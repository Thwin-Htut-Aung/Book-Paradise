package com.book.paradise.controller;


import java.util.Date;
import java.util.List;

import javax.naming.NamingException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.ModelAndViewDefiningException;

import com.book.paradise.entity.Users;
import com.book.paradise.repo.UserRepo;
import com.book.paradise.service.UserService;

@Controller
@RequestMapping("/admin")
@PreAuthorize("hasAuthority('Role_Admin')")
public class AdminController {
	
	//injecting dependencies
	
	@Autowired
	private UserRepo usersRepo;
	
	
	@Autowired
	private UserService userService;

	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@GetMapping(value = "/admin-register")
	public ModelAndView registerAdmin(@ModelAttribute("user") Users user, ModelAndView mav) {
		mav.addObject("action", "admin-register");
		mav.setViewName("registration");
		return mav;
	}
	
	@PostMapping(value = "/admin-register")
	public ModelAndView saveAdmin(@ModelAttribute("user") Users user,
			@RequestParam("rePassword") String rePassword,
			ModelAndView mav) throws ModelAndViewDefiningException, NamingException {
		
		if(userService.checkByEmail(user.getEmail())) {
			mav.addObject("action", "/admin/admin-register");
			mav.addObject("msg_dupe", "Email already used. Please use another email.");
    		mav.setViewName("registration");
		}
		
		else if(!user.getPassword().equals(rePassword)){
			mav.addObject("action", "/admin/admin-register");
			mav.addObject("msg_wrong_password", "Please make sure your password and confirmed password are the same.");
    		mav.setViewName("registration");
		}
		
		else{
		user.setPassword(passwordEncoder.encode(user.getPassword()));
		user.setRole("Role_Admin");
		user.setCreated(new Date());
		usersRepo.save(user);
		
		mav.setViewName("thankyou_page");
		}
		
		return mav;
	}
	
	//request mapping for add user page
	@GetMapping(value = "/add-user")
	public ModelAndView addUser(@ModelAttribute("user") Users user, ModelAndView mav) {
		mav.addObject("action", "add-user");
		mav.setViewName("add_user");
		return mav;
	}
	
	//request mapping for saving new user
	@PostMapping(value = "/add-user")
	public ModelAndView saveUser(@ModelAttribute("user") Users user, ModelAndView mav,
			@RequestParam("role") String role) throws ModelAndViewDefiningException {
		if(userService.checkByEmail(user.getEmail())) {
			mav.addObject("action", "add-user");
			mav.addObject("msg_dupe", "Email already used. Please create another one");
    		mav.setViewName("add_user");
		}
		
		else{
		
		user.setPassword(passwordEncoder.encode(user.getPassword()));
		
		if (role.equalsIgnoreCase("admin")) {
			user.setRole("User_Role");
		} else if (role.equalsIgnoreCase("user")) {
			user.setRole("Admin_Role");
		}
		
		user.setCreated(new Date());
		usersRepo.save(user);
		
		mav.addObject("action", "add_user");
		mav.addObject("msg", "add success");
		mav.setViewName("add_user");
		}
		
		return mav;
	}
	
	//request mapping for view users page
	@GetMapping("/list_users")
	public ModelAndView listAllUsers(ModelAndView mav) {
		List<Users> listusers = userService.getUsers();
		mav.addObject("listusers", listusers);
		mav.addObject("msg_delete", "delete success");
		mav.setViewName("admin_dash");
		return mav;
	}
	
	//requesr mapping for viewing user by id
	@GetMapping("/view_user/{id}")
	public ModelAndView viewUser(@PathVariable long id, ModelAndView mav) {
		Users user = userService.getUserById(id);
		mav.addObject("user", user);
		mav.setViewName("view_user");
		return mav;
	}
	

	
	//request mapping for edit user page
	@GetMapping(value = "/edit_user/{id}")
	public ModelAndView editUser(@PathVariable long id, ModelAndView mav) {
		Users user = userService.getUserById(id);
		mav.addObject("user", user);
		mav.setViewName("edit_user");
		return mav;
	}
	
	
	//request mapping for deleting user
	@GetMapping(value = "/delete_user/{id}")
	public ModelAndView deleteUser(@PathVariable long id, ModelAndView mav) {
		userService.deleteUser(id);
		mav.setViewName("redirect:/admin/list_users");
		return mav;
	}
	
	
	//request mapping for searching for users
	@GetMapping(value = "/search")
	public ModelAndView searchUser(@RequestParam(value = "keyword", required = false) String keyword, ModelAndView mav) {
		List<Users> listusers = userService.searchByParam(keyword);
		
		if(listusers == null || listusers.isEmpty()) {
    		mav.addObject("msg", "Sorry we couldn't find any result for '"+keyword+"'");
    	} else {
    		mav.addObject("msg", "Result of your search for '"+keyword+"'");
    		mav.addObject("listusers", listusers);
    	}
    	mav.setViewName("admin_dash");
		
		return mav;
	}

}
