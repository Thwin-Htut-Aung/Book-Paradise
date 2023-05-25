package com.book.paradise.controller;

import java.util.Random;

import javax.mail.MessagingException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.book.paradise.service.PasswordResetService;

@Controller
public class AuthController {
	
	@Autowired
	private PasswordResetService resetService;
	
	
	//login page
	@GetMapping(value="/login")
    public ModelAndView onLogin(ModelAndView mav) {
    	
    	mav.addObject("action", "action");
    	mav.setViewName("login");
        return mav;
    }

	//show login error
    @GetMapping(value="/login-error")
    public ModelAndView onLoginError(ModelAndView mav) {
        String error_msg = "Incorrect user or password. Please re-enter.";
        mav.addObject("action", "action");
        mav.addObject("error_msg", error_msg);
        mav.setViewName("login");
        return mav;
    }
    
    
    @GetMapping(value="/send-reset-email")
    public String sendResetEmail(){
    
    	return "enter_email";
    }
    
    
    @GetMapping(value="/reset-password")
    public ModelAndView resetPassword(@RequestParam("email") String email, ModelAndView mav) throws MessagingException{
    	
    	Random random = new Random();
    	int resetCode = random.nextInt(100000);
    	
    	boolean sendEmail = resetService.sendPasswordResetEmail(email, resetCode);
    	if(!sendEmail){
    		mav.setViewName("enter_email");
    		mav.addObject("fail_msg","There is no account with this email. Please enter the email you use for your account.");
    	}
    	else if(sendEmail){
    		mav.addObject("resetCode", resetCode);
    		mav.addObject("email", email);
    		mav.setViewName("password_reset");
    	}
    	else{
    		mav.setViewName("enter_email");
    		mav.addObject("fail_msg","There was an error. Please try again.");
    	}
    	
    	
    	return mav;
    }
    
    
    @PostMapping(value="/reset-password")
    public ModelAndView saveNewPassword(@RequestParam("email") String email, @RequestParam("resetCode") int resetCode,
    		@RequestParam("realCode") int realCode, @RequestParam("newPassword") String newPassword, ModelAndView mav){
    	
    	if(resetCode==realCode) {
    		resetService.resetPassword(email, newPassword);
        	mav.addObject("reset_success", "Your password has been reset. Please log in.");
        	mav.setViewName("login");
    	}
    	else {
    		mav.addObject("reset_fail", "Your code is incorrect. Please type again.");
        	mav.setViewName("reset_password");
    	}
    	
    	return mav;
    }
    

}
