package com.book.paradise.service;




import java.util.List;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import com.book.paradise.entity.Books;
import com.book.paradise.entity.Chapter;


@Service
@Transactional
public interface BookStorageService {
	
	void storeChapter(Chapter chapter);
	Chapter getChapter(String name);
	void saveBook(Books book);
	Books getBook(String name);
	List<Books> getBooksByGenre(String genre);
	List<Books> getAllBooks();
	void deleteBook(Books book);
	void deleteChapter(Chapter chapter);

}
