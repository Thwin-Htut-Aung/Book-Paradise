package com.book.paradise.service;


import java.util.List;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import com.book.paradise.entity.Comments;
import com.book.paradise.entity.Feedback;
import com.book.paradise.entity.Replies;
import com.book.paradise.entity.Users;


@Service
@Transactional
public interface UserService {
	
	void saveUser(Users user);
	
	boolean checkByEmail(String email);
	Users findByEmail(String email);
	
	List<Users> getUsers();
	Users getUserById(Long id);
	void deleteUser(Long id);
	
	List<Users> searchByParam(String key);
	
	void saveComment(Comments comment);
	Comments getCommentById(long id);
	void deleteCommentById(long id);
	
	void saveReply(Replies reply);
	void deleteReplyById(long id);

	void saveFeedback(Feedback feedback);
	List<Feedback> getAllFeedbacks();
}
