package org.launchcode.blogz.controllers;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.launchcode.blogz.models.User;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;


@Controller
public class AuthenticationController extends AbstractController {
	
	String[] errors = {"Your username is invalid.", "Your password is invalid.", "Your passwords don't match!"};
	
	//Signup
	
	@RequestMapping(value = "/signup", method = RequestMethod.GET)
	public String signupForm() {
		return "signup";
	}
	
	@RequestMapping(value = "/signup", method = RequestMethod.POST)
	public String signup(HttpServletRequest request, Model model) {
		
		String sub_usern = request.getParameter("username");
		String sub_pass = request.getParameter("password");
		String verify = request.getParameter("verify");
		
		

		if(User.isValidPassword(sub_pass) && User.isValidUsername(sub_usern) && verify.equals(sub_pass)) {
			
			User user = new User(sub_usern,sub_pass);
			
			HttpSession thisSession = request.getSession(); //Gets current session
			
			setUserInSession(thisSession, user); //Sets user from Session
		
			userDao.save(user);
			return "redirect:blog/newpost";
			
		} else {
			
			if(!User.isValidUsername(sub_usern)) {
				model.addAttribute("username_error",errors[0]);
			} else if(!User.isValidPassword(sub_pass)) {
				model.addAttribute("password_error",errors[1]);
			} else if(sub_pass != verify){
				model.addAttribute("verify_error", errors[2]); 
			} else {}
			return "signup";
			
		}
	}
	
	//Login
	
	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public String loginForm() {
		return "login";
	}
	
	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public String login(HttpServletRequest request, Model model) {
		
		String sub_usern = request.getParameter("username");
		String sub_pass = request.getParameter("password");
		
		if(sub_usern == "") {
			model.addAttribute("error",errors[0]);
			return "login";
		} else {
		
		User userToFind = userDao.findByUsername(sub_usern);
			
		if(userToFind.isMatchingPassword(sub_pass) && userToFind.isMatchingPassword(sub_usern)) {
			
			HttpSession thisSession = request.getSession();
			
			setUserInSession(thisSession, userToFind);
			
			
			return "redirect:/blog";
			
		} else {
			
			if(userToFind.getUsername() != sub_usern) {
				model.addAttribute("error",errors[1]);
			} else if(!userToFind.isMatchingPassword(sub_pass)) {
				model.addAttribute("error",errors[2]);
			} else {}
			
			return "login";
		}
	}
}
	
	@RequestMapping(value = "/logout", method = RequestMethod.GET)
	public String logout(HttpServletRequest request){
        request.getSession().invalidate();
		return "redirect:/";
	}
}
