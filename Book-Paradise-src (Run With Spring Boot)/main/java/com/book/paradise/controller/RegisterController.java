package com.book.paradise.controller;

import java.util.Date;

import javax.naming.NamingException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.ModelAndViewDefiningException;

import com.book.paradise.entity.Users;
import com.book.paradise.service.UserService;

@Controller
@RequestMapping("/account")
public class RegisterController {
	

	
	
	@Autowired
	private UserService userService;
	
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	
	// ========== Registration =============
	@GetMapping(value = "/user-register")
	public ModelAndView registerUser(@ModelAttribute("user") Users user, ModelAndView mav) {
		mav.setViewName("registration");
		mav.addObject("action", "/account/user-register");
		return mav;
	}
	
	@PostMapping(value = "/user-register")
	public ModelAndView saveUser(@ModelAttribute("user") Users user, @RequestParam("rePassword") String rePassword,
			ModelAndView mav) throws ModelAndViewDefiningException, NamingException {
		if(userService.checkByEmail(user.getEmail())) {
			mav.addObject("action", "/account/user-register");
			mav.addObject("msg_dupe", "Email already used. Please use another email.");
    		mav.setViewName("registration");
		}
		
		else if(!user.getPassword().equals(rePassword)){
			mav.addObject("action", "/account/user-register");
			mav.addObject("msg_wrong_password", "Please make sure your password and confirmed password are the same.");
    		mav.setViewName("registration");
		}
		
		else{
		
		user.setPassword(passwordEncoder.encode(user.getPassword()));
		user.setRole("Role_User");
		user.setCreated(new Date());
		userService.saveUser(user);
		mav.setViewName("thankyou_page");
		}
		
		return mav;
	}
	
	
	


}
