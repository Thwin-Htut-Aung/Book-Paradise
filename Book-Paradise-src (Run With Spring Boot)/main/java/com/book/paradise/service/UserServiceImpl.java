package com.book.paradise.service;


import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.book.paradise.entity.Comments;
import com.book.paradise.entity.Feedback;
import com.book.paradise.entity.Replies;
import com.book.paradise.entity.Users;
import com.book.paradise.repo.CommentRepo;
import com.book.paradise.repo.FeedbackRepo;
import com.book.paradise.repo.ReplyRepo;
import com.book.paradise.repo.UserRepo;

@Service
@Transactional
public class UserServiceImpl implements UserService{
	
	@Autowired
	private UserRepo userRepo;
	
	@Autowired
	private CommentRepo commentRepo;
	
	@Autowired
	private ReplyRepo replyRepo;

	@Autowired
	private FeedbackRepo feedbackRepo;
	
	@Override
	public void saveUser(Users user) {
		userRepo.save(user);
		
	}
	
	@Override
	public boolean checkByEmail(String email) {
		if(userRepo.existsByEmail(email)) {
			return true;
		}
		return false;
	}

	@Override
	public Users findByEmail(String email) {
		return userRepo.findByEmail(email).get();
	}


	@Override
	public List<Users> getUsers() {
		return userRepo.findAll(Sort.by(Sort.Direction.ASC, "userId"));
	}

	public Users getUserById(Long id) {
		return userRepo.findById(id).get();
	}
	
	public void deleteUser(Long id) {
		userRepo.deleteById(id);
	}

	@Override
	public List<Users> searchByParam(String key) {
		return userRepo.searchByParam(key);
	}

	@Override
	public void saveComment(Comments comment) {
		commentRepo.save(comment);
		
	}

	@Override
	public Comments getCommentById(long id) {
		return commentRepo.getById(id);
	}

	@Override
	public void saveReply(Replies reply) {
		replyRepo.save(reply);
		
	}

	@Override
	public void deleteCommentById(long id) {
		commentRepo.deleteById(id);
		
	}

	@Override
	public void deleteReplyById(long id) {
		replyRepo.deleteById(id);
		
	}

	@Override
	public void saveFeedback(Feedback feedback) {
		feedbackRepo.save(feedback);
		
	}

	@Override
	public List<Feedback> getAllFeedbacks() {
		return feedbackRepo.findAll();
	}



}
