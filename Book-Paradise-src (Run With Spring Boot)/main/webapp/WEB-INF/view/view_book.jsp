<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@include file="init.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>Book Paradise</title>
 <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.2.3/dist/css/bootstrap.min.css" rel="stylesheet">
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.2.3/dist/js/bootstrap.bundle.min.js"></script>
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.1/css/all.min.css">
  <script src="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.1/js/all.min.js"></script>
<link href="/style/comments.css" rel="stylesheet">
<script src="/script/upload-valid.js"></script>
<script src="/script/show-replies.js"></script>
<style>
    img{
    width: 50%;
    height: 50%
    }
    section, .foot{
    background-color: #008040;
    color: white }
    
    .chapter-link{
    color: white
    }
    .chapter-link:hover{
    color:black}
    
    }
</style>
</head>
<body>
<%@include file="header.jsp"%>

    <section>
    <div class="container">
     <div class="row d-flex justify-content-center align-items-center h-100">
     
        <div class="cover text-center">
            <img src="cover images/${book.bookName}-cover image.jpg"><br>
            <h1>${book.bookName}</h1>
        </div>
        
        <div class="info-panel text-center">
        <h3>Author(s)</h3>
        <p>${book.author}</p>
        <h3>Translator(s)</h3>
        <p>${book.translators}</p>
        <h3>Genre</h3>
        <p>${book.genre}</p>
        <h3>Tags</h3>
        <p>${book.tags}</p>
        
        <h3>Chapters</h3>
        <c:forEach items="${book.chapters}" var="chapter">
        <p><a href="/read-chapter?chapterName=${chapter.name}" class="chapter-link">${chapter.name}</a></p>
        </c:forEach>
        
        <sec:authorize access="isAuthenticated()">
        <c:if test="${bookmark_status eq 'No'}">
        <form action="/add-bookmarks" method="post">
        <input type="hidden" name="bookName" value="${book.bookName}">
        <button type="submit" class="btn btn-warning">Add to Bookmarks</button>
        </form>
        </c:if>
        <c:if test="${bookmark_status eq 'Yes'}">
        <a class="btn btn-danger" href="delete-bookmark?bookName=${book.bookName}">Remove from Bookmarks</a></c:if>
        
        <sec:authorize access="hasAuthority('Role_Admin')">
        <br><br>
        <form action="/upload-chapter?bookName=${book.bookName}" method="POST" name="chapterForm" enctype="multipart/form-data"
        onsubmit="return validateChapter()">
        <input type="file" name="newChapter" accept="application/pdf">
        <button type="submit" class="btn btn-warning">Add Chapter</button>
        </form>
        <br>
        <div id="err" style="display:none">Please upload a chapter.</div>
   
        </sec:authorize>
        </sec:authorize>
        </div>
        
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
        <button class="btn btn-danger" onclick="confirmDelete('${comment.commentId}','comment','book','${book.bookName}')">
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
          	<button class="btn btn-danger" onclick="confirmDelete('${reply.replyId}','reply','book','${book.bookName}')">
        	Delete Reply</button><br>
        	</c:if>
      		</c:forEach>
      	  </c:if>
      		
      			<sec:authorize access="isAuthenticated()">
    			<br>
    			<div id="write-replies">
    			<form action="/post-reply" method="post">
    			<input type="hidden" name="commentId" value="${comment.commentId}">
   			 	<input type="hidden" name="contentName" value="${book.bookName}">
   				<input type="hidden" name="commentTarget" value="book">
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
    <input type="hidden" name="commentTarget" value="book">
    <input type="hidden" name="contentName" value="${book.bookName}">
    <textarea id="comment-area" name="commentBody" placeholder="Write a comment..."></textarea>
    <button type="submit" class="btn btn-primary"><i class="fas fa-paper-plane"></i></button>
    </form>
    </div>
    </sec:authorize>
    
	</div>
    
    
        </div>
        </div>
    </section>
<%@include file="footer.jsp"%>
</body>
</html>