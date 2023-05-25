package com.book.paradise.entity;


import java.util.List;
import java.util.ArrayList;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;

@Entity
@Table(name = "comments")
public class Comments {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="comment_id")
	private long commentId;
	
	@Lob
	@Column(name="comment", nullable=false)
	private String commentBody;
	
    @Column(name="created_date", nullable = false)
    private String createdDate;
	
	@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable=false)
	private Users user;
	
	@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "book_id", nullable=true)
	private Books commentedBook;
	
	@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "chapter_id", nullable=true)
	private Chapter commentedChapter;
	
	@OneToMany(mappedBy = "repliedComment", orphanRemoval = true)
	@OrderBy("replyId ASC")
    private List<Replies> replies = new ArrayList<>();
	
	
	public long getCommentId() {
		return commentId;
	}

	public void setCommentId(long commentId) {
		this.commentId = commentId;
	}

	public String getCommentBody() {
		return commentBody;
	}

	public void setCommentBody(String commentBody) {
		this.commentBody = commentBody;
	}

	public String getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(String createdDate) {
		this.createdDate = createdDate;
	}

	public Users getUser() {
		return user;
	}

	public void setUser(Users user) {
		this.user = user;
	}

	public Books getCommentedBook() {
		return commentedBook;
	}

	public void setCommentedBook(Books commentedBook) {
		this.commentedBook = commentedBook;
	}

	public Chapter getCommentedChapter() {
		return commentedChapter;
	}

	public void setCommentedChapter(Chapter commentedChapter) {
		this.commentedChapter = commentedChapter;
	}

	public List<Replies> getReplies() {
		return replies;
	}

	public void setReplies(List<Replies> replies) {
		this.replies = replies;
	}

	

}
