package com.book.paradise.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.book.paradise.entity.Comments;

@Repository
public interface CommentRepo extends JpaRepository<Comments, Long>{

}
