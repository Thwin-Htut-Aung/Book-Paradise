package com.book.paradise.entity;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name="password_reset")
public class PasswordResetToken {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long tokenId;
	
	@Column(name="reset_token", nullable = false)
	private String resetToken;
	
	@Column(name="is_expired", nullable = false)
	private boolean expired;
	
	@OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id", referencedColumnName = "user_id")
	private Users user;
	
	
	public PasswordResetToken(Users user, String resetToken) {
		super();
		this.user = user;
		this.resetToken = resetToken;
	}
	
	
	public long getTokenId() {
		return tokenId;
	}


	public void setTokenId(long tokenId) {
		this.tokenId = tokenId;
	}


	public Users getUser() {
		return user;
	}

	public void setUser(Users user) {
		this.user = user;
	}

	public String getResetToken() {
		return resetToken;
	}

	public void setResetToken(String resetToken) {
		this.resetToken = resetToken;
	}


	public boolean isExpired() {
		return expired;
	}


	public void setExpired(boolean expired) {
		this.expired = expired;
	}


	
	
	
	
}
