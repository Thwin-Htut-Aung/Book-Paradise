package com.book.paradise.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.book.paradise.entity.Feedback;

@Repository
public interface FeedbackRepo extends JpaRepository<Feedback, Long>{

}
