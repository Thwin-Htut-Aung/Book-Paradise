package com.book.paradise.entity;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "replies")
public class Replies {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="reply_id")
	private long replyId;
	
	@Lob
	@Column(name="reply", nullable=false)
	private String replyBody;
	
    @Column(name="created_date", nullable = false)
    private String createdDate;
	
	@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable=false)
	private Users user;
	
	@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "comment_id", nullable=true)
	private Comments repliedComment;

	
	public long getReplyId() {
		return replyId;
	}

	public void setReplyId(long replyId) {
		this.replyId = replyId;
	}

	public String getReplyBody() {
		return replyBody;
	}

	public void setReplyBody(String replyBody) {
		this.replyBody = replyBody;
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

	public Comments getRepliedComment() {
		return repliedComment;
	}

	public void setRepliedComment(Comments repliedComment) {
		this.repliedComment = repliedComment;
	}

	
	
}
