package com.book.paradise.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.book.paradise.entity.Chapter;

@Repository
public interface ChapterRepo extends JpaRepository<Chapter, Long> {

	public Chapter findByName(String name);
}
