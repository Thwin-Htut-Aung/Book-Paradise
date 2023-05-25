package com.book.paradise.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.book.paradise.entity.Replies;

@Repository
public interface ReplyRepo extends JpaRepository<Replies, Long>{

}