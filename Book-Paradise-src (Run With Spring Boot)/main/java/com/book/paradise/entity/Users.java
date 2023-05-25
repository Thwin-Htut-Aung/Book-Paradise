package com.book.paradise.entity;


import java.util.ArrayList;
import java.util.Date;
import java.util.List;


import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.UniqueConstraint;



@Entity
@Table(name = "users", 
	   uniqueConstraints = {
			   @UniqueConstraint(columnNames = "email")
	   })
public class Users {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="user_id")
	private long userId;
	
	@Column(name="user_name", nullable = false)
	private String fullName;
	
	@Column(name="email", nullable = false)
	private String email;
	
    @Column(name="password", nullable = false)
    private String password;
    
    @Column(name="user_role", nullable = false)
    private String role;
    
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name="created_date", nullable = false)
    private Date created;
    
    @OneToOne(mappedBy = "user")
    private PasswordResetToken token;
    
    
    @ManyToMany(cascade = CascadeType.PERSIST, fetch = FetchType.EAGER)
    @JoinTable(
            name = "users_books",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "book_id")
            )
    private List<Books> bookmarks = new ArrayList<>();
    

    @OneToMany(mappedBy = "user", orphanRemoval = true)
    private List<Comments> comments = new ArrayList<>();
    
    
	public long getUserId() {
		return userId;
	}

	public void setUserId(long userId) {
		this.userId = userId;
	}

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}


	public Date getCreated() {
		return created;
	}

	public void setCreated(Date created) {
		this.created = created;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	
	public PasswordResetToken getToken() {
		return token;
	}

	public void setToken(PasswordResetToken token) {
		this.token = token;
	}

	public List<Books> getBookmarks() {
		return bookmarks;
	}

	public void setBookmarks(List<Books> bookmarks) {
		this.bookmarks = bookmarks;
	}

	public List<Comments> getComments() {
		return comments;
	}

	public void setComments(List<Comments> comments) {
		this.comments = comments;
	}
	

    
    
}
