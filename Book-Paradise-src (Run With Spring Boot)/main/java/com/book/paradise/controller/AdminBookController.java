package com.book.paradise.controller;


import java.io.File;
import java.io.IOException;
import java.util.Map;
import java.util.List;
import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.book.paradise.entity.Books;
import com.book.paradise.entity.Chapter;
import com.book.paradise.service.BookStorageService;
import com.book.paradise.service.PDFToImageService;


@Controller
@PreAuthorize("hasAuthority('Role_Admin')")
public class AdminBookController {

	@Autowired
	private BookStorageService bookService;
	
	@Autowired
	private PDFToImageService p2iService;
	
	@GetMapping(value="/add-book")
	public String addBook(){
		return "add_book";
	}
	
	// Upload File Controller
	@PostMapping(value="/upload-book")
	public String uploadBook(@RequestParam("bookName") String bookName, @RequestParam("genre") String genre,
			@RequestParam("author") String author, @RequestParam("translators") String translators,
			@RequestParam("tags") String tags, @RequestParam("coverImage") MultipartFile coverImage,
			@RequestParam("firstChapter") MultipartFile firstChapter,
			@RequestParam("secondChapter") MultipartFile secondChapter,
			@RequestParam("thirdChapter") MultipartFile thirdChapter, Model model) throws IOException {
		
	
		List<MultipartFile> chapters = new ArrayList<>();
		chapters.add(firstChapter);
		chapters.add(secondChapter);
		chapters.add(thirdChapter);
		
		for(MultipartFile chapter : chapters) {
			if(!chapter.isEmpty()) {
				String folderPath = "D:/Spring Project Workspace/Sartoon-Web-Novel/src/main/resources/static/novel chapters/";
				String chapterPath = folderPath+bookName+"-"+chapter.getOriginalFilename();
				
				chapter.transferTo(new File(chapterPath));
				int chapterPages = p2iService.convertPDFToImage(chapterPath, bookName+"-"+chapter.getOriginalFilename());
				
				Chapter chap = new Chapter(bookName+"-"+chapter.getOriginalFilename(), chapter.getContentType(),
						chapterPath, chapterPages, bookService.getBook(bookName));
				bookService.storeChapter(chap);
			}
		}
	
		
		String coverImagePath = "D:/Spring Project Workspace/Sartoon-Web-Novel/src/main/resources/static/cover images/"
				+bookName+"-cover image.jpg";
		coverImage.transferTo(new File(coverImagePath));
		Books book = new Books(bookName, genre, author, translators, tags, coverImagePath);
		bookService.saveBook(book);
		
		
		return "redirect:/view-all-books";
		
			
	}

	
	@PostMapping(value="/upload-chapter")
	public String uploadChapter(@RequestParam("newChapter") MultipartFile file, @RequestParam String bookName) throws IOException {
		
		String folderPath = "D:/Spring Project Workspace/Sartoon-Web-Novel/src/main/resources/static/novel chapters/";
		String chapterPath = folderPath+bookName+"-"+file.getOriginalFilename();
		
		file.transferTo(new File(chapterPath));
		int chapterPages = p2iService.convertPDFToImage(chapterPath, file.getOriginalFilename());
		
		Books book = bookService.getBook(bookName);
		
		Chapter chapter = new Chapter(file.getOriginalFilename(), file.getContentType(),
				chapterPath, chapterPages, book);
		bookService.storeChapter(chapter);
		
		return "redirect:/view-book?bookName="+bookName;
	}
	
	@GetMapping(value="/delete-book")
	public String deleteBook(@RequestParam String bookName, Model model) {
		
		boolean deletion = true;
		int failedChapters = 0;
		int failedPages = 0;
		boolean coverDeletion = true;
		
		File cover = new File("D:/Spring Project Workspace/Sartoon-Web-Novel/src/main/resources/static/cover images/"+
				bookName+"-cover image.jpg");
				if(cover.exists()) {
					if(!cover.delete()) {
						deletion = false;
						coverDeletion = false;
					}
				}
				else {
					deletion = false;
					coverDeletion = false;
				}
				
				
		Books book = bookService.getBook(bookName);
				
		List<Chapter> chapters = book.getChapters();
		
		
		for(Chapter chapter : chapters) {
		File chap = new File("D:/Spring Project Workspace/Sartoon-Web-Novel/src/main/resources/static/novel chapters/"+
		chapter.getName());
		if(chap.exists()) {
			if(!chap.delete()) {
				deletion = false;
				failedChapters++;
			}
		
		}
		else {
			deletion = false;
			failedChapters++;
		}
		
			for(int i=1; i<=chapter.getNumberOfPages(); i++) {
				File page = new File("D:/Spring Project Workspace/Sartoon-Web-Novel/src/main/resources/static/novel pages/"
										+chapter.getName()+"-page"+i+".jpg");
				if(page.exists()) {
					if(!page.delete()) {
						deletion = false;
						failedPages++;
					}
				}
				else {
					deletion = false;
					failedPages++;
				}
			}
		}
		
		bookService.deleteBook(book);
		if(!deletion) {
			if(failedChapters>0 && failedPages>0) {
				model.addAttribute("delete_status", failedChapters+
						" chapters and "+failedPages+" pages were not deleted due to an error.");
			}
			else if(failedChapters>0) {
				model.addAttribute("delete_status", failedChapters+" chapters were not deleted due to an error.");
			}
			else if(failedPages>0) {
				model.addAttribute("delete_status", failedPages+" pages were not deleted due to an error.");
			}
			else if(!coverDeletion) {
				model.addAttribute("delete_status", "The cover image was not deleted due to an error.");
			}
			
		}
		else {
			model.addAttribute("delete_status", "Book successfully deleted!");
		}
			
		return "redirect:/view-all-books";
		
	}
	
	@GetMapping(value="/delete-chapter")
	public String deleteChapter(@RequestParam String chapterName, Model model) {
		
		File chapter = new File("D:/Spring Project Workspace/Sartoon-Web-Novel/src/main/resources/static/novel chapters/"+
				chapterName);
		
		if(chapter.exists()) {
			if(chapter.delete()) {
				model.addAttribute("delete_status", "Book successfully deleted!");
			}
			else {
				model.addAttribute("delete_status", "Deletion failed. Please try again.");
			}
		}
		else {
			model.addAttribute("delete_status", "Book no longer exists.");
		}
		
		Chapter chap = bookService.getChapter(chapterName);
		String bookName = chap.getBook().getBookName();
		bookService.deleteChapter(chap);
		return "redirect:/view-book?bookName="+bookName;
	}
	
	
	@GetMapping(value="/add-hot-topic")
	public String addHotTopic(@RequestParam("hotTopic") String bookName){
 	
		Books book = bookService.getBook(bookName);
		book.setHotTopic(true);
		bookService.saveBook(book);
		return "redirect:/";
	}
	
	@PostMapping(value="/remove-hot-topic")
	public String removeHotTopic(@RequestParam Map<String,String> books){
		
		for(Map.Entry<String,String> entry : books.entrySet()){
			if(!entry.getValue().isEmpty()) {
				Books book = bookService.getBook(entry.getValue());
				book.setHotTopic(false);
				bookService.saveBook(book);
			}
		}
 	
		return "redirect:/";
	}
	
	@GetMapping(value="/add-most-viewed")
	public String addMostViewed(@RequestParam("mostViewed") String bookName){
 	
		Books book = bookService.getBook(bookName);
		book.setMostViewed(true);
		bookService.saveBook(book);
		return "redirect:/";
	}
	
	@PostMapping(value="/remove-most-viewed")
	public String removeMostViewed(@RequestParam Map<String,String> books){
 	
		for(Map.Entry<String,String> entry : books.entrySet()){
			if(!entry.getValue().isEmpty()) {
				Books book = bookService.getBook(entry.getValue());
				book.setMostViewed(false);
				bookService.saveBook(book);
			}
		}
 	
		return "redirect:/";
	}
	
	@GetMapping(value="/add-latest")
	public String addLatest(@RequestParam("latestUpdate") String bookName){
 	
		Books book = bookService.getBook(bookName);
		book.setLatest(true);
		bookService.saveBook(book);
		return "redirect:/";
	}
	
	@PostMapping(value="/remove-latest")
	public String removeLatest(@RequestParam Map<String,String> books){
 	
		for(Map.Entry<String,String> entry : books.entrySet()){
			if(!entry.getValue().isEmpty()) {
				Books book = bookService.getBook(entry.getValue());
				book.setLatest(false);
				bookService.saveBook(book);
			}
		}
 	
		return "redirect:/";
	}
	
	@GetMapping(value="/add-new-release")
	public String addNewRelease(@RequestParam("newRelease") String bookName){
 	
		Books book = bookService.getBook(bookName);
		book.setNewRelease(true);
		bookService.saveBook(book);
		return "redirect:/";
	}
	
	@PostMapping(value="/remove-new-release")
	public String removeNewRelease(@RequestParam Map<String,String> books){
 	
		for(Map.Entry<String,String> entry : books.entrySet()){
			if(!entry.getValue().isEmpty()) {
				Books book = bookService.getBook(entry.getValue());
				book.setNewRelease(false);
				bookService.saveBook(book);
			}
		}
 	
		return "redirect:/";
	}
	
	
	
	
}