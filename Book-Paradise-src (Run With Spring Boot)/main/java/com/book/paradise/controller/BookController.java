package com.book.paradise.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import java.text.SimpleDateFormat;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.book.paradise.entity.Books;
import com.book.paradise.entity.Chapter;
import com.book.paradise.entity.Comments;
import com.book.paradise.entity.Replies;
import com.book.paradise.entity.Users;
import com.book.paradise.service.BookStorageService;
import com.book.paradise.service.BookParadiseUserDetails;
import com.book.paradise.service.UserService;

@Controller
public class BookController {

	@Autowired
	private BookStorageService bookService;
	
	@Autowired
	private UserService userService;
	
	
	@PostMapping(value="/add-bookmarks")
	public String addBookmarks(@RequestParam Map<String, String> bookNames, @AuthenticationPrincipal BookParadiseUserDetails userDetails){
 	
		Users user = userService.findByEmail(userDetails.getUsername());
		
		List<Books> bookmarks = user.getBookmarks();
		
		for (Map.Entry<String,String> entry : bookNames.entrySet()){
    		String bookName = entry.getValue();
    		
    		Books bookmark = bookService.getBook(bookName);
    		bookmarks.add(bookmark);
    		
		}
		
		user.setBookmarks(bookmarks);
		userService.saveUser(user);
		return "redirect:/bookmarks";
	}
	
	
	@GetMapping(value="/bookmarks")
	@PreAuthorize("isAuthenticated()")
	public ModelAndView getBookmarks(@AuthenticationPrincipal BookParadiseUserDetails userDetails, 
			@RequestParam(name="genre", defaultValue="All Genres") String currentGenre, ModelAndView mav){
 	
		
		Users user = userService.findByEmail(userDetails.getUsername());
		List<Books> bookmarks = user.getBookmarks();
		
		if(currentGenre.equals("All Genres")){
			mav.addObject("bookmarks", bookmarks);
			if(bookmarks.isEmpty()) {
				mav.addObject("empty_state", "all_empty");
			}
		}
		
		else{
			List<Books> genreBookmarks = new ArrayList<>();
			for(Books bookmark:bookmarks) {
				if(bookmark.getGenre().equals(currentGenre)) {
					genreBookmarks.add(bookmark);
					mav.addObject("bookmarks", genreBookmarks);
				}
			}
			if(genreBookmarks.isEmpty()) {
				mav.addObject("empty_state", "genre_empty");
			}
		
		}
		
		
		mav.addObject("current_genre", currentGenre);
		List<String> genres = new ArrayList<>();
		if(!currentGenre.equals("All Genres")){
			genres.add("All Genres");
		}
		
		List<Books> allBooks = bookService.getAllBooks();
		for(Books book : allBooks){
			String genre = book.getGenre();
			if(!genre.equals(currentGenre) && !genres.contains(genre)){
				genres.add(genre);
			}
		}
		
		mav.addObject("genres", genres);
		
		mav.setViewName("bookmarks");
		return mav;
	}
			
		
	@GetMapping(value="/delete-bookmark")
	public String deleteBookmark(@RequestParam String bookName, @AuthenticationPrincipal BookParadiseUserDetails userDetails){
 	
		Users user = userService.findByEmail(userDetails.getUsername());
		
		List<Books> bookmarks = user.getBookmarks();
		Books bookmark = bookService.getBook(bookName);
		
		bookmarks.remove(bookmark);
		
		user.setBookmarks(bookmarks);
		userService.saveUser(user);
		return "redirect:/bookmarks";
	}
	
		
		@GetMapping(value="/view-all-books")
		public ModelAndView getAllBooks(@RequestParam(name="genre", defaultValue="All Genres") String currentGenre, ModelAndView mav){
	 	
			if(currentGenre.equals("All Genres")){
				List<Books> allBooks = bookService.getAllBooks();
				mav.addObject("books", allBooks);
			}
			
			else{			
				List<Books> genreBooks = bookService.getBooksByGenre(currentGenre);
				mav.addObject("books", genreBooks);
			}
			
			
			mav.addObject("current_genre", currentGenre);
			List<Books> allTheBooks = bookService.getAllBooks();
			List<String> genres = new ArrayList<>();
			if(!currentGenre.equals("All Genres")){
				genres.add("All Genres");
			}
			
			for(Books book : allTheBooks){
				String genre = book.getGenre();
				if(!genre.equals(currentGenre) && !genres.contains(genre)){
					genres.add(genre);
				}
			}
			
			mav.addObject("genres", genres);
			
			mav.setViewName("view_all_books");
			return mav;
		}

	
	
	@GetMapping(value="/view-book")
	public ModelAndView getBook(ModelAndView mav, @RequestParam("bookName") String bookName,
			@AuthenticationPrincipal BookParadiseUserDetails userDetails){
 	
		Books book = bookService.getBook(bookName);
		
		if(userDetails!=null) {
			Users user = userService.findByEmail(userDetails.getUsername());
			List<Books> bookmarks = user.getBookmarks();
			
			if(bookmarks.contains(book)) {
				mav.addObject("bookmark_status", "Yes");
			}
			else {
				mav.addObject("bookmark_status", "No");
			}
		}
		
		mav.addObject("book", book);
		
		List<Comments> bookComments = book.getComments();
		mav.addObject("comments", bookComments);
		
		mav.setViewName("view_book");
		return mav;
	}
	
	@GetMapping(value="/read-chapter")
	public ModelAndView readChapter(ModelAndView mav, @RequestParam("chapterName") String chapterName){
 	
		Chapter chapter = bookService.getChapter(chapterName);
		int numberOfPages = chapter.getNumberOfPages();
		
		List<Integer> pageNumbers = new ArrayList<>();
		for(int i = 1; i<=numberOfPages; i++){
			pageNumbers.add(i);
		}
		
		mav.addObject("chapterName", chapter.getName());
		mav.addObject("pageNumbers", pageNumbers);
		
		List<Comments> chapterComments = chapter.getComments();
		mav.addObject("comments", chapterComments);
		
		mav.setViewName("read_chapter");
		return mav;
	}
	
	@PostMapping(value="/post-comment")
	public String postComment(@RequestParam("commentBody") String comment, @RequestParam("commentTarget") String target,
			@RequestParam("contentName") String contentName, @AuthenticationPrincipal BookParadiseUserDetails userDetails){
 	
		Users user = userService.findByEmail(userDetails.getUsername());
		
		Comments userComment = new Comments();
		userComment.setCommentBody(comment);
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd MMMM yyyy");
		userComment.setCreatedDate(dateFormat.format(new Date()));
		userComment.setUser(user);
		
		if(target.equals("book")) {
			Books commentedBook = bookService.getBook(contentName);
			userComment.setCommentedBook(commentedBook);
			userService.saveComment(userComment);
			return "redirect:/view-book?bookName="+contentName;
		}
		else {
			Chapter commentedChapter = bookService.getChapter(contentName);
			userComment.setCommentedChapter(commentedChapter);
			userService.saveComment(userComment);
			return "redirect:/read-chapter?chapterName="+contentName;
		}
	
		
	}
	
	
	@GetMapping(value="/delete-comment")
	public String deleteComment(@RequestParam("commentId") long id, @RequestParam("type") String type,
			@RequestParam("target") String target, @RequestParam("contentName") String contentName){
		
		if(type.equals("comment")) {
			userService.deleteCommentById(id);
		}
		else {
			userService.deleteReplyById(id);
		}
		
		if(target.equals("book")) {
			return "redirect:/view-book?bookName="+contentName;
		}
		else {
			return "redirect:/read-chapter?chapterName="+contentName;
		}
	}
	
	
	@PostMapping(value="/post-reply")
	public String postReply(@RequestParam("replyBody") String replyBody, @RequestParam("commentId") long id,
			@RequestParam("commentTarget") String target, @RequestParam("contentName") String contentName,
			@AuthenticationPrincipal BookParadiseUserDetails userDetails){
		
		Users user = userService.findByEmail(userDetails.getUsername());
		Comments repliedComment = userService.getCommentById(id);
		
		Replies reply = new Replies();
		reply.setReplyBody(replyBody);
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd MMMM yyyy");
		reply.setCreatedDate(dateFormat.format(new Date()));
		reply.setUser(user);
		reply.setRepliedComment(repliedComment);
		userService.saveReply(reply);
		
		if(target.equals("book")) {
			return "redirect:/view-book?bookName="+contentName;
		}
		else {
			return "redirect:/read-chapter?chapterName="+contentName;
		}
	}
	
	
}
