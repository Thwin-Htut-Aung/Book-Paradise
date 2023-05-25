package com.book.paradise.service;


import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.book.paradise.entity.Books;
import com.book.paradise.entity.Chapter;
import com.book.paradise.repo.BookRepo;
import com.book.paradise.repo.ChapterRepo;

@Service
@Transactional
public class BookStorageServiceImpl implements BookStorageService{
	

	@Autowired
	private BookRepo bookRepo;
	
	@Autowired
	private ChapterRepo chapterRepo;

	@Override
	public void storeChapter(Chapter chapter) {
		chapterRepo.save(chapter);
		
	}

	@Override
	public Chapter getChapter(String chapterName) {
		Chapter chapter = chapterRepo.findByName(chapterName);
		return chapter;
	}

	@Override
	public void saveBook(Books book) {
		bookRepo.save(book);
		
	}

	@Override
	public Books getBook(String name) {
		return bookRepo.findByBookName(name);
		
	}

	@Override
	public List<Books> getAllBooks() {
		return bookRepo.findAll();
		
	}

	@Override
	public List<Books> getBooksByGenre(String genre) {
		List<Books> genreBooks = bookRepo.findByGenre(genre);
		return genreBooks;
	}

	@Override
	public void deleteBook(Books book) {
		bookRepo.delete(book);
		
	}

	@Override
	public void deleteChapter(Chapter chapter) {
		chapterRepo.delete(chapter);
		
	}
	

	



	

}
