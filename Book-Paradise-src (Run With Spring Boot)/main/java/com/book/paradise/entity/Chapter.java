package com.book.paradise.entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;

@Entity
@Table(name = "chapters")
public class Chapter {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="chapter_id")
	private long id;
	
	@Column(name="chapter_name")
	private String name;
	
	@Column(name="file_type")
	private String type;
	
	@Column(name="file_path")
	private String filePath;
	
	@Column(name="number_of_pages")
	private int numberOfPages;
	
	
	@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "book_id", nullable=true)
	private Books book;
	
	@OneToMany(mappedBy = "commentedChapter", orphanRemoval = true)
	@OrderBy("commentId ASC")
    private List<Comments> comments = new ArrayList<>();


	public Chapter() {
		super();
	}

	public Chapter(String name, String type, String filePath, int numberOfPages, Books book) {
		super();
		this.name = name;
		this.type = type;
		this.filePath = filePath;
		this.numberOfPages = numberOfPages;
		this.book = book;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}


	public String getFilePath() {
		return filePath;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}

	public String getType() {
		return type;
	}


	public void setType(String type) {
		this.type = type;
	}


	public int getNumberOfPages() {
		return numberOfPages;
	}

	public void setNumberOfPages(int numberOfPages) {
		this.numberOfPages = numberOfPages;
	}


	public Books getBook() {
		return book;
	}

	public void setBook(Books book) {
		this.book = book;
	}

	public List<Comments> getComments() {
		return comments;
	}

	public void setComments(List<Comments> comments) {
		this.comments = comments;
	}
	
	

}
