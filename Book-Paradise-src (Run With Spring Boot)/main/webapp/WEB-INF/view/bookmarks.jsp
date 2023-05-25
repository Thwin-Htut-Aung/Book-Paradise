<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@include file="init.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
 <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.2.3/dist/css/bootstrap.min.css" rel="stylesheet">
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.2.3/dist/js/bootstrap.bundle.min.js"></script>
<script src="/script/genre.js"></script>
<script src="/script/edit-books.js"></script>
<title>Book Paradise</title>
<style>

	img{
	width: 70%;
	height: 70%
	}
	
	.footer{
	float: none
	}
	
	.heading{
	color: #008040;
	padding: 2vh}
	
	.deleting{
	 display: none;
	 text-align: center
	}
	
	#cancel-button{
	 display: none
	}
	
	.empty-mes{
	color: red;
	text-align: center
	}
	</style>
</head>
<body>

<%@include file="header.jsp"%>

<section id="allbooks">

		<div class="heading row">
		<h1 class="col-lg-6 col-sm-12">All Your Bookmarks</h1>
		
		<c:set var="currentGen" value="${current_genre}"></c:set>
		<jsp:useBean id="currentGen" type="java.lang.String"/>
		
		<c:if test='<%=!currentGen.equals("All Genres")%>'>
		<c:if test="${not empty bookmarks}">
		<h2 class="heading col-lg-6 col-sm-12">Showing all the bookmarks for "${current_genre}"</h2>
		</c:if></c:if>
		<form class="form-inline col-12">
		<select id="genreSelect" class="" onchange="changeBookmarkGenre()">
		<option value="${current_genre}">${current_genre}</option>
		<c:forEach items="${genres}" var="genre">
		<option value="${genre}">${genre}</option>
		</c:forEach>
		</select>
		</form>
		</div>
		
		<div class="row">
		
			<c:choose>
			
			<c:when test="${empty bookmarks}">
			<c:if test="${empty_state eq 'all_empty'}"><h2 class="empty-mes">You currently have no bookmarks. Please add bookmarks.</h2>
			</c:if>
			<c:if test="${empty_state eq 'genre_empty'}"><h2 class="empty-mes">You currently have no bookmarks for the genre ${current_genre}.</h2>
			</c:if>
			</c:when>
			
			<c:when test="${not empty bookmarks}">
		
			<c:forEach items="${bookmarks}" var="bookmark">
			
				<div class="col-lg-3 col-sm-4 col-6">
				<a href="/view-book?bookName=${bookmark.bookName}">
				<img class="col-12" src="/cover images/${bookmark.bookName}-cover image.jpg" alt="image" width="600" height="400"></a>
				<div style="padding: 15px; text-align: center; font-weight: bold">${bookmark.bookName}</div>
				
				<div class="deleting">  
  				<a class="btn btn-danger" href="/delete-bookmark?bookName=${bookmark.bookName}">Delete</a>
  				</div>	  
  				</div>
					
			</c:forEach>
			</c:when>
			
			</c:choose>
			</div>
			
		
			<sec:authorize access="isAuthenticated()">
			<div style="text-align: center">
			<a type="button" class="btn btn-success" href="/view-all-books">Add Bookmark</a>
			<button class="btn btn-danger" id="delete-button" onclick="showDeleteButtons()">Delete Bookmark</button>
			<button class="btn btn-primary" id="cancel-button" onclick="hideDeleteButtons()">Cancel</button>
      		</div>
      		</sec:authorize>
			
			  
		</section>
		<div class="footer">
			<%@include file="footer.jsp"%>
			</div>
		
</body>
</html>