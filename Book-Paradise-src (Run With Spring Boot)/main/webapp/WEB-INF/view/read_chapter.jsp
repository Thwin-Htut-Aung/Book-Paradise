<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@include file="init.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Book Paradise</title>
 <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.2.3/dist/css/bootstrap.min.css" rel="stylesheet">
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.2.3/dist/js/bootstrap.bundle.min.js"></script>
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.1/css/all.min.css">
  <script src="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.1/js/all.min.js"></script>
  
  <link href="/style/comments.css" rel="stylesheet">
  <script src="/script/show-replies.js"></script>
  
<style>

@media only screen and (max-width: 600px){
img{
width: 80vw;
height: 60vh
}
}

@media only screen and (min-width: 601px){
img{
width: 60vw;
height: 160vh
}
}

img{
margin-bottom: 10px
}
.pages{
text-align: center;
background-color: #008040
}
.foot{
background-color: #008040}
</style>
</head>
<body>
<%@include file="header.jsp"%>

<div class="pages">
<div class="container">
     

<c:forEach items="${pageNumbers}" var="pageNumber">
<img src="/novel pages/${chapterName}-page${pageNumber}.jpg" alt="image">
</c:forEach>     


<div id="comment-box" class="container">
    <h2>Comments</h2>
    
    <c:if test="${empty comments}">
    <div>There are no comments yet.</div>
    </c:if>
    
    <c:if test="${not empty comments}">
    <%int i=0; %>
    <c:forEach items="${comments}" var="comment">
      
      <div class="comment">
        <div class="comment-author">${comment.user.fullName}</div>
        <div class="comment-date">${comment.createdDate}</div>
        <div class="comment-text">${comment.commentBody}</div><br>
        <button class="btn btn-primary" onclick="showReplies('reply<%=i %>')">Replies <i class="fas fa-bars"></i></button>
        <c:if test="${comment.user.email eq globalEmail}">
        <button class="btn btn-danger" onclick="confirmDelete('${comment.commentId}','comment','chapter','${chapterName}')">
        Delete Comment</button>
        </c:if>
        
          <div class="reply-box" id="reply<%=i %>">
          
          <c:if test="${empty comment.replies}">
    	  <div>There are no replies yet.</div>
    	  </c:if>
    	  
    	  <c:if test="${not empty comment.replies}">
          	<c:forEach items="${comment.replies}" var="reply">
          	<div class="reply">
            <div class="reply-author">${reply.user.fullName}</div>
            <div class="reply-date">${reply.createdDate}</div>
            <div class="reply-text">${reply.replyBody}</div>
          	</div>
          	<c:if test="${reply.user.email eq globalEmail}">
          	<button class="btn btn-danger" onclick="confirmDelete('${reply.replyId}','reply','chapter','${chapterName}')">
        	Delete Reply</button><br>
        	</c:if>
      		</c:forEach>
      	  </c:if>
      		
      			<sec:authorize access="isAuthenticated()">
    			<br>
    			<div id="write-replies">
    			<form action="/post-reply" method="post">
    			<input type="hidden" name="commentId" value="${comment.commentId}">
   			 	<input type="hidden" name="contentName" value="${chapterName}">
   				<input type="hidden" name="commentTarget" value="chapter">
   			 	<textarea id="reply-area" name="replyBody" placeholder="Write a reply..."></textarea>
   			 	<button type="submit" class="btn btn-warning"><i class="fas fa-paper-plane"></i></button>
    			</form>
    			</div>
    			</sec:authorize>
    
          </div>
      </div>
     <%i++; %>
    </c:forEach>
    </c:if>
    
    <sec:authorize access="isAuthenticated()">
    <br>
    <div id="write-comments">
    <form action="/post-comment" method="post">
    <input type="hidden" name="commentTarget" value="chapter">
    <input type="hidden" name="contentName" value="${chapterName}">
    <textarea id="comment-area" name="commentBody" placeholder="Write a comment..."></textarea>
    <button type="submit" class="btn btn-primary"><i class="fas fa-paper-plane"></i></button>
    </form>
    </div>
    </sec:authorize>
    
	</div>
	
	</div>
	</div>
	
<%@include file="footer.jsp"%>
</body>
</html>