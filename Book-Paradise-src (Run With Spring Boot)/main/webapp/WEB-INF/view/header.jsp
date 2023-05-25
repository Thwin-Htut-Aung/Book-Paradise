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
<link rel="stylesheet" href="https://fonts.googleapis.com/css?family=Tangerine">
<link href="/style/header.css" rel="stylesheet" />
<title>Insert title here</title>
<style>
	#logo{
	margin: 1em;
	color: #008040;
	font-family: 'Tangerine', serif; 
	font-size: 8vh;
	font-weight: bold
}
.nav-item{
font-weight: bold}
</style>
</head>
<body>

  
  <header class="p-3 border-bottom background">
    <div>
      <div class="d-flex flex-wrap align-items-center justify-content-center justify-content-lg-start">
	  		
	  		<nav class="navbar navbar-expand-sm">
  			<div class="container-fluid">
   			 <h1><a class="navbar-brand" id="logo" href="/">Book Paradise</a></h1>
   			 <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#mynavbar">
    		  <span class="navbar-toggler-icon"></span>
    		</button>
    		<div class="collapse navbar-collapse" id="mynavbar">
     		 <ul class="navbar-nav">
     		   <li class="nav-item">
     		     <a class="nav-link" href="/">Home</a>
     		   </li>
      		  
      		  <sec:authorize access="isAuthenticated()">
      		  <li class="nav-item">
      		    <a class="nav-link" href="/bookmarks">Bookmarks</a>
     		   </li>
      		  </sec:authorize>
      		  
      		  <sec:authorize access="hasRole('Role_Admin')">
      		  <li class="nav-item">
      		    <a class="nav-link" href="/add-book">Add Novel</a>
     		   </li>
      		  </sec:authorize>
      		  
      		  <li class="nav-item">
      		    <a class="nav-link" href="/view-all-books">All Books</a>
     		   </li>
     		   
      		  <li class="nav-item">
      		    <a class="nav-link" href="/about-us">About Us</a>
     		   </li>
     		   <li class="nav-item">
      		    <a class="nav-link" href="/contact-us">Contact Us</a>
     		   </li>
     		   
     		   <sec:authorize access="!isAuthenticated()">
     		   <li class="nav-item" style="margin-left: 1em; margin-right: 1em; margin-bottom: 1em;">
	           <button class="signin" onclick="window.location.href='/login';">Sign In</button>
	        	</li></sec:authorize>
      		 
     		   <sec:authorize access="isAuthenticated()">
     		   <li class="nav-item" style="margin-right: 1em; margin-bottom: 1em;">
			   <a href="/dologout" class="btn btn-danger nav-link">Sign Out</a></li>
     		   </sec:authorize>
     		   
     		   <sec:authorize access="isAuthenticated()">
     		    <li class="nav-item">
     		    <div class="avatar-container">
     		    <div class="text-avatar">${globalTextAvatar}</div></div>
     		    <div class="user-name">${globalUserName}</div>
     		    </li>
     		   </sec:authorize>
     		
    			  </ul>
				  
  
   	 </div>
  	</div>
	</nav>
	
        
      </div>
    </div>
  </header>

</body>
</html>