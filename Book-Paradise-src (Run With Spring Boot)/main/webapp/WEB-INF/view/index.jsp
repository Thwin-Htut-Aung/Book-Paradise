<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@include file="init.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<meta name="viewport" content="width=device-width, initial-scale=1">
 <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.2.3/dist/css/bootstrap.min.css" rel="stylesheet">
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.2.3/dist/js/bootstrap.bundle.min.js"></script>


	<link href="/style/index.css" rel="stylesheet">
<script src="/script/slideshow.js"></script>
<script src="/script/remove.js"></script>
<script>
function checkBook(formId, errorIndex){

	if(document.getElementById(formId).selectedIndex===0){
		document.getElementById(errorIndex).innerHTML="Please select a book."
		document.getElementById(errorIndex).style.display="block"
		return false
	}
	else{
		return true
	}
}
</script>
<title>Book Paradise</title>
</head>
<body onload="showAllSlides()">
<%@include file="header.jsp"%>
<div class="mainContainer d-flex">
<div class="container">

<br>
	<div class="flex" id="head-title">

		<div class="flex-items write"><h2>Welcome to <span class="auto-type"></span></h2>
			<h2>Come and have fun at <u>BOOK PARADISE</u></h2>
			<h2>We have all the <u>E-BOOKS</u> you want!</h2>
			<br>
 			
		</div>
	</div>
	<br>
	
	

	<section id="hot-topics">
		<h2 class="book-title">Hot Topics</h2>
		
		
		<div class="slideshow-container">

  <form action="/remove-hot-topic" method="post">
  <%int i=0; %>
  <c:forEach items="${hotTopics}" var="hotTopic">
  <div class="mySlides innerPart">
 
    <a href="/view-book?bookName=${hotTopic.bookName}"><img src="/cover images/${hotTopic.bookName}-cover image.jpg"></a> 
    <h3 class="text">${hotTopic.bookName}</h3>
  
  <div class="removing" >  
  <input type="checkbox" value="${hotTopic.bookName}" name="book<%=i %>" style="width:30px; height:30px">
  </div>

  </div>
  <%i++; %>
 </c:forEach>
 
 <div class="removing">
 <button type="submit" class="btn btn-danger">Remove</button>
  <button type="reset" class="btn btn-primary" onclick="hideRemoveForm()">Cancel</button>
  </div>
 </form>
 
 <a class="prev" onclick="moveSlides(-1,0,'prev')">&#10094;</a>
  <a class="next" onclick="moveSlides(1,0,'next')">&#10095;</a>
</div>

	<sec:authorize access="hasAuthority('Role_Admin')">
		<form class="form-inline" action="/add-hot-topic" method="GET" onsubmit="return checkBook('form1', 'err1')">
		<button type="button" id="remove-button" class="btn btn-danger" onclick="showRemoveForm()">Remove Hot Topic</button>
		<select name="hotTopic" id="form1">
		<option value="default">Add a hot topic.</option>
		
		<c:forEach items="${allBooks}" var="book">
		<c:if test="${book.hotTopic==false}">
		<option value="${book.bookName}">${book.bookName}</option>
		</c:if>
		</c:forEach>

		</select>
		<button type="submit" class="btn btn-primary">Add</button>
		<div id="err1"></div>
		</form>
		<br>
	</sec:authorize>
	
		</section>


		<section id="most-viewed">
			<h2 class="book-title">Most Viewed</h2>
			
		
			<div class="slideshow-container">
			
  <form action="/remove-most-viewed" method="post">
  <%int j=0; %>
  <c:forEach items="${mostViewed}" var="mostViewedOne">
  <div class="mySlides2 innerPart">
    <a href="/view-book?bookName=${mostViewedOne.bookName}"><img src="/cover images/${mostViewedOne.bookName}-cover image.jpg"></a> 
    <h3 class="text">${mostViewedOne.bookName}</h3>
    
    <div class="removing2" >  
  <input type="checkbox" value="${mostViewedOne.bookName}" name="book<%=j %>" style="width:30px; height:30px">
  </div>
  
  </div>
  <%j++; %>
 </c:forEach>
 
  <div class="removing2">
 <button type="submit" class="btn btn-danger">Remove</button>
  <button type="reset" class="btn btn-primary" onclick="hideRemoveForm2()">Cancel</button>
  </div>
  </form>
  
 <a class="prev" onclick="moveSlides(-1,1,'prev')">&#10094;</a>
  <a class="next" onclick="moveSlides(1,1,'next')">&#10095;</a>
</div>

	<sec:authorize access="hasAuthority('Role_Admin')">
		<form action="/add-most-viewed" method="GET" onsubmit="return checkBook('form2', 'err2')">
		<button type="button" id="remove-button-2" class="btn btn-danger" onclick="showRemoveForm2()">Remove Most Viewed</button>
		<select name="mostViewed" id="form2">
		<option value="default">Add a most viewed book.</option>
		
		<c:forEach items="${allBooks}" var="book">
		<c:if test="${book.mostViewed==false}">
		<option value="${book.bookName}">${book.bookName}</option>
		</c:if>
		</c:forEach>
		
		</select>
		<button type="submit" class="btn btn-primary">Add</button>
		<div id="err2"></div>
		</form>
		<br>
	</sec:authorize>
			</section>


			<section id="latest">
				<h2 class="book-title">Latest Updates</h2>
				
		
				<div class="slideshow-container">

  <form action="/remove-latest" method="post">
  <%int k=0; %>
  <c:forEach items="${latest}" var="latestUpdate">
  
  <div class="mySlides3 innerPart">
    <a href="/view-book?bookName=${latestUpdate.bookName}"><img src="/cover images/${latestUpdate.bookName}-cover image.jpg"></a> 
    <h3 class="text">${latestUpdate.bookName}</h3>
    
    <div class="removing3">  
  <input type="checkbox" value="${latestUpdate.bookName}" name="book<%=k %>" style="width:30px; height:30px">
  </div>
  
  </div>
  <%k++; %>
 </c:forEach>
 
 <div class="removing3">
 <button type="submit" class="btn btn-danger">Remove</button>
  <button type="reset" class="btn btn-primary" onclick="hideRemoveForm3()">Cancel</button>
  </div>
  </form>
  
 <a class="prev" onclick="moveSlides(-1,2,'prev')">&#10094;</a>
  <a class="next" onclick="moveSlides(1,2,'next')">&#10095;</a>
</div>

	<sec:authorize access="hasAuthority('Role_Admin')">
			<form action="/add-latest" method="GET" onsubmit="return checkBook('form3', 'err3')">
			<button type="button" id="remove-button-3" class="btn btn-danger" onclick="showRemoveForm3()">Remove Latest Update</button>
			<select name="latestUpdate" id="form3">
			<option value="default">Add a latest update.</option>
		
		<c:forEach items="${allBooks}" var="book">
		<c:if test="${book.latest==false}">
		<option value="${book.bookName}">${book.bookName}</option>
		</c:if>
		</c:forEach>
		
		</select>
		<button type="submit" class="btn btn-primary">Add</button>
		<div id="err3"></div>
		</form>
		<br>
	</sec:authorize>
				</section>


				<section id="new-releases">
					<h2 class="book-title">New Releases</h2>
					
					
		
					<div class="slideshow-container">

  <form action="/remove-new-release" method="post">
  <%int n=0; %>
  <c:forEach items="${newReleases}" var="newRelease">
  <div class="mySlides4 innerPart">
    <a href="/view-book?bookName=${newRelease.bookName}"><img src="/cover images/${newRelease.bookName}-cover image.jpg"></a> 
    <h3 class="text">${newRelease.bookName}</h3>
    
    <div class="removing4" >  
  <input type="checkbox" value="${newRelease.bookName}" name="book<%=n %>" style="width:30px; height:30px">
  </div>
  
  </div>
  <%n++; %>
 </c:forEach>
 
 <div class="removing4">
 <button type="submit" class="btn btn-danger">Remove</button>
  <button type="reset" class="btn btn-primary" onclick="hideRemoveForm4()">Cancel</button>
  </div>
  </form>
 
 <a class="prev" onclick="moveSlides(-1,3,'prev')">&#10094;</a>
  <a class="next" onclick="moveSlides(1,3,'next')">&#10095;</a>
</div>

	<sec:authorize access="hasAuthority('Role_Admin')">
					<form action="/add-new-release" method="GET" onsubmit="return checkBook('form4', 'err4')">
					<button type="button" id="remove-button-4" class="btn btn-danger" onclick="showRemoveForm4()">Remove New Release</button>
					<select name="newRelease" id="form4">
					<option value="default">Add a new release.</option>
		
					<c:forEach items="${allBooks}" var="book">
					<c:if test="${book.newRelease==false}">
					<option value="${book.bookName}">${book.bookName}</option>
					</c:if>
					</c:forEach>
		
				</select>
				<button type="submit" class="btn btn-primary">Add</button>
				<div id="err4"></div>
				</form>
				<br>
		</sec:authorize>
					</section>

					<section id="allbooks">
						<h2 class="book-title">All In General</h2>
						<div class="slideshow-container">

  <c:forEach items="${firstBooks}" var="book">
  <div class="mySlides5 innerPart">
    <img src="/cover images/${book.bookName}-cover image.jpg">
    <h3 class="text">${book.bookName}</h3>
  </div>
 </c:forEach>
 
 <a class="prev" onclick="moveSlides(-1,4,'prev')">&#10094;</a>
  <a class="next" onclick="moveSlides(1,4,'next')">&#10095;</a>
</div>
						</section>
	 
</div>
</div>

	<%@include file="footer.jsp"%>




<script src="https://cdn.jsdelivr.net/npm/typed.js@2.0.12"></script>
    <script>
        var typed = new Typed(".auto-type", {
            strings: ["Book Paradise..."],
            typeSpeed: 120,
            backSpeed: 120,
            loop: true
        }) 




 
    </script>
</body>
</html>