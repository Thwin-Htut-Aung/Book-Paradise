package com.book.paradise.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import com.book.paradise.entity.Books;
import com.book.paradise.entity.Feedback;
import com.book.paradise.entity.Users;
import com.book.paradise.service.BookParadiseUserDetails;
import com.book.paradise.service.BookStorageService;
import com.book.paradise.service.UserService;

@Controller
public class OtherControllers {

	@Autowired
	BookStorageService bookService;
	
	@Autowired
	UserService userService;
	
	@GetMapping(value="/")
	public ModelAndView showHome(ModelAndView mav){
		
		List<Books> books = bookService.getAllBooks();
	
		List<Books> hotTopics = new ArrayList<>();
		for(Books book : books){
			if(book.isHotTopic()){
				hotTopics.add(book);
			}
		}
		
		List<Books> mostViewed = new ArrayList<>();
		for(Books book : books){
			if(book.isMostViewed()){
				mostViewed.add(book);
			}
		}
		
		List<Books> latestUpdates = new ArrayList<>();
		for(Books book : books){
			if(book.isLatest()){
				latestUpdates.add(book);
			}
		}
		
		List<Books> newReleases = new ArrayList<>();
		for(Books book : books){
			if(book.isNewRelease()){
				newReleases.add(book);
			}
		}
		
		//List<Books> firstFiveBooks = books.subList(0, 5);
 	
		
		mav.addObject("hotTopics", hotTopics);
		mav.addObject("mostViewed", mostViewed);
		mav.addObject("latest", latestUpdates);
		mav.addObject("newReleases", newReleases);
		mav.addObject("firstBooks", books); 
		
		mav.addObject("allBooks", books);
		mav.setViewName("index");
		return mav;
	}
	
	@GetMapping(value="/about-us")
	public String showAboutUs(){
		return "about_us";
	}
	
	@GetMapping(value="/contact-us")
	public ModelAndView showContactUs(@AuthenticationPrincipal BookParadiseUserDetails userDetails,
			ModelAndView mav, Model model){
		
		if(userDetails!=null) {
			Users user = userService.findByEmail(userDetails.getUsername());
			if(user.getRole().equals("Role_Admin")) {
				List<Feedback> allFeedbacks = userService.getAllFeedbacks();
				mav.addObject("feedbacks", allFeedbacks);
			}
		}
		
		Feedback feedback = new Feedback();
		model.addAttribute("feedback", feedback);
		mav.setViewName("contact_us");
		return mav;
	}
	
	@PostMapping(value="/submit-feedback")
	public ModelAndView submitFeedback(@ModelAttribute("feedback") Feedback feedback, ModelAndView mav){
		
		if(feedback.getUserName().isEmpty() || feedback.getFeedbackContent().isEmpty()) {
			mav.addObject("error_msg", "You must write your name and a piece of feedback.");
		}
		else {
			userService.saveFeedback(feedback);
			mav.addObject("success_msg", "Thank you for your feedback!");
		}
		
		mav.setViewName("contact_us");
		return mav;
	}
}
