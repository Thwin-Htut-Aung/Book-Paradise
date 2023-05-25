package com.book.paradise.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.book.paradise.entity.Books;

@Repository
public interface BookRepo extends JpaRepository<Books, Long>{
	
	Books findByBookName(String bookName);
	List<Books> findByGenre(String genre);

}
