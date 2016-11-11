package org.launchcode.blogz.controllers;

import java.util.List;

import org.launchcode.blogz.models.Post;
import org.launchcode.blogz.models.User;
import org.launchcode.blogz.models.dao.PostDao;
import org.launchcode.blogz.models.dao.UserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class BlogController extends AbstractController {
	
	@Autowired
	UserDao userDao;
	
	@Autowired
	PostDao postDao;
	
	@RequestMapping(value = "/")
	public String index(Model model){
		
		List<User> user = userDao.findAll();
		model.addAttribute("user",user);
		
		return "index";
		
	}
	
	@RequestMapping(value = "/blog")
	public String blogIndex(Model model) {
		
		List<Post> posts = postDao.findAll();
		model.addAttribute("posts",posts);
		return "blog";
	}
	
}
