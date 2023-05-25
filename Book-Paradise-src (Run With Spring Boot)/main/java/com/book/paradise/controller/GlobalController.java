package com.book.paradise.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

import com.book.paradise.service.BookParadiseUserDetails;
import com.book.paradise.service.UserService;

@ControllerAdvice
public class GlobalController {
	
	@Autowired
	UserService userService;

	@ModelAttribute("globalUserName")
    public String getUserName(@AuthenticationPrincipal BookParadiseUserDetails userDetails) {
		if(userDetails==null) {
			return null;
		}
		else {
        return userService.findByEmail(userDetails.getUsername()).getFullName();
		}
    }
	
	@ModelAttribute("globalEmail")
    public String getEmail(@AuthenticationPrincipal BookParadiseUserDetails userDetails) {
		if(userDetails==null) {
			return null;
		}
		else {
        return userDetails.getUsername();
		}
    }
	
	@ModelAttribute("globalTextAvatar")
    public String getTextAvatar(@AuthenticationPrincipal BookParadiseUserDetails userDetails) {
		if(userDetails==null) {
			return null;
		}
		else {
			String userName = userService.findByEmail(userDetails.getUsername()).getFullName();
			String[] names = userName.split(" ");
			String initials = String.valueOf(names[0].charAt(0)) + String.valueOf(names[names.length-1].charAt(0));
			return initials; 
		}
		
		
    }
	
}
