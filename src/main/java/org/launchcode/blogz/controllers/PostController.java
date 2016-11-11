package org.launchcode.blogz.controllers;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.launchcode.blogz.models.Post;
import org.launchcode.blogz.models.User;
import org.launchcode.blogz.models.dao.PostDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class PostController extends AbstractController {
	
	@Autowired
	PostDao postDao;
	


	@RequestMapping(value = "/blog/newpost", method = RequestMethod.GET)
	public String newPostForm() {
		return "newpost";
	}
	
	@RequestMapping(value = "/blog/newpost", method = RequestMethod.POST)
	public String newPost(HttpServletRequest request, Model model) {
		
		String title = request.getParameter("title");
		String body = request.getParameter("body");
		
		HttpSession thisSession = request.getSession();
		User author = getUserFromSession(thisSession);
		Post newPost = new Post(title,body,author);
		postDao.save(newPost);

		return "redirect:/blog/{newPost.uid}"; 	
	}
	
	@RequestMapping(value = "/blog/{username}/{uid}", method = RequestMethod.GET)
	public String singlePost(@PathVariable String username, @PathVariable int uid, Model model) {
		
		//TODO: finish up posts; the post variable isn't working right
		Post post = postDao.findByUid(uid);
		model.addAttribute("post",post);
		return "post";
	}
	
	@RequestMapping(value = "/blog/{username}", method = RequestMethod.GET)
	public String userPosts(@PathVariable String username, Model model) {
		
		//YOU GOT THIS <3
		// TODO - implement userPosts
		
		return "blog";
	}
	
}
