package com.book.paradise.entity;

import java.util.ArrayList;
import java.util.List;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;


@Entity
@Table(name = "books")
public class Books {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="book_id")
	private long id;
	
	@Column(name="book_name", nullable=false)
	private String bookName;
	
	@Column(name="book_genre")
	private String genre;
	
	@Column(name="book_author")
	private String author;
	
	@Column(name="book_translator")
	private String translators;
	
	@Column(name="book_tags")
	private String tags;
	
	@Column(name="cover_image")
	private String coverImagePath;
	
	@Column(name="is_hot")
	private boolean hotTopic = false;
	
	@Column(name="is_most_viewed")
	private boolean mostViewed = false;
	
	@Column(name="is_latest")
	private boolean latest = false;
	
	@Column(name="is_new")
	private boolean newRelease = false;
	
    @ManyToMany(fetch = FetchType.EAGER)
	private Set<Users> bookmarkers = new HashSet<>();
	
    @OneToMany(mappedBy = "book", orphanRemoval = true)
    private List<Chapter> chapters = new ArrayList<>();
    
    @OneToMany(mappedBy = "commentedBook", orphanRemoval = true)
    @OrderBy("commentId ASC")
    private List<Comments> comments = new ArrayList<>();


	
	public Books() {
		super();
	}

	public Books(String bookName, String genre, String author, String translators, String tags, String coverImagePath) {
		super();
		this.bookName = bookName;
		this.genre = genre;
		this.author = author;
		this.translators = translators;
		this.tags = tags;
		this.coverImagePath = coverImagePath;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	
	public String getBookName() {
		return bookName;
	}


	public void setBookName(String bookName) {
		this.bookName = bookName;
	}


	public String getGenre() {
		return genre;
	}

	public void setGenre(String genre) {
		this.genre = genre;
	}



	public Set<Users> getBookmarkers() {
		return bookmarkers;
	}

	public void setBookmarkers(Set<Users> bookmarkers) {
		this.bookmarkers = bookmarkers;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public String getTranslators() {
		return translators;
	}

	public void setTranslators(String translators) {
		this.translators = translators;
	}

	public String getTags() {
		return tags;
	}

	public void setTags(String tags) {
		this.tags = tags;
	}
	

	public String getCoverImagePath() {
		return coverImagePath;
	}

	public void setCoverImagePath(String coverImagePath) {
		this.coverImagePath = coverImagePath;
	}


	public List<Chapter> getChapters() {
		return chapters;
	}

	public void setChapters(List<Chapter> chapters) {
		this.chapters = chapters;
	}

	public List<Comments> getComments() {
		return comments;
	}

	public void setComments(List<Comments> comments) {
		this.comments = comments;
	}

	public boolean isHotTopic() {
		return hotTopic;
	}


	public void setHotTopic(boolean hotTopic) {
		this.hotTopic = hotTopic;
	}


	public boolean isMostViewed() {
		return mostViewed;
	}


	public void setMostViewed(boolean mostViewed) {
		this.mostViewed = mostViewed;
	}


	public boolean isLatest() {
		return latest;
	}


	public void setLatest(boolean latest) {
		this.latest = latest;
	}


	public boolean isNewRelease() {
		return newRelease;
	}


	public void setNewRelease(boolean newRelease) {
		this.newRelease = newRelease;
	}

	

	

}
