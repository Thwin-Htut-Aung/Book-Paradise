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
	
	.deleting, .bookmarking{
	 display: none;
	 text-align: center
	}
	
	#cancel-button, form .bookmark-button{
	 display: none
	}
	

	</style>
</head>
<body>

<%@include file="header.jsp"%>

<section id="allbooks">

		<div class="heading row">
		<h1 class="col-lg-6 col-sm-12">All Books</h1>
		
		<c:set var="currentGen" value="${current_genre}"></c:set>
		<jsp:useBean id="currentGen" type="java.lang.String"/>
		
		<c:if test='<%=!currentGen.equals("All Genres")%>'>
		<h2 class="heading col-lg-6 col-sm-12">Showing all the books for "${current_genre}"</h2>
		</c:if>
		<form class="form-inline col-12">
		<select id="genreSelect" class="" onchange="changeGenre()">
		<option value="${current_genre}">${current_genre}</option>
		<c:forEach items="${genres}" var="genre">
		<option value="${genre}">${genre}</option>
		</c:forEach>
		</select>
		</form>
		</div>
		
		
		<form action="/add-bookmarks" method="post">
		<div class="row">
		
			<%int i=0; %>
			<c:forEach items="${books}" var="book">
			
				<div class="col-lg-3 col-sm-4 col-6">
				<a href="/view-book?bookName=${book.bookName}">
				<img class="col-12" src="/cover images/${book.bookName}-cover image.jpg" alt="image" width="600" height="400"></a>
				<div style="padding: 15px; text-align: center; font-weight: bold">${book.bookName}</div>
					  
  				<div class="bookmarking">  
  				<input type="checkbox" value="${book.bookName}" name="book<%=i %>" style="width: 30px; height: 30px">
  				</div>	
  				<div class="deleting">  
  				<a class="btn btn-danger" href="/delete-book?bookName=${book.bookName}">Delete</a>
  				</div>
  				</div>
  				
				<%i++; %>	
			</c:forEach>
			</div>
			
			<div class="row">
			
			<div class="col-12" style="text-align: center">
			<button type="submit" class="btn btn-primary bookmark-button">Add</button>
			<button type="reset" class="btn btn-danger bookmark-button" onclick="hideBookmarkForm()">Cancel</button>
			</div>
			
			</div><br>
			</form>
			
			<sec:authorize access="isAuthenticated()">
			<div style="text-align: center">
			<button class="btn btn-success" id="show-form-button" onclick="showBookmarkForm()">Add Bookmarks</button>
		
			<sec:authorize access="hasAuthority('Role_Admin')">
			<a type="button" class="btn btn-success" href="/add-book">Add Book</a>
			<button class="btn btn-danger" id="delete-button" onclick="showDeleteButtons()">Delete Books</button>
			<button class="btn btn-primary" id="cancel-button" onclick="hideDeleteButtons()">Cancel</button>
      		</sec:authorize>
      		</div>
			</sec:authorize>
			  
		</section>
		<div class="footer">
			<%@include file="footer.jsp"%>
			</div>
		
</body>
</html>