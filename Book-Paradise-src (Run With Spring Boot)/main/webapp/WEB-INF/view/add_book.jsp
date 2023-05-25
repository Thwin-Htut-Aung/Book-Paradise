<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@include file="init.jsp"%>
<!DOCTYPE html>
<html>

<head>

<!--  Enable Bootstrap -->
<link href="https://fonts.googleapis.com/css?family=Lato"
	rel="stylesheet" type="text/css">
<link href="https://fonts.googleapis.com/css?family=Montserrat"
	rel="stylesheet" type="text/css">
 <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.2.3/dist/css/bootstrap.min.css" rel="stylesheet">
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.2.3/dist/js/bootstrap.bundle.min.js"></script>

<script src="/script/upload-valid.js"></script>
<title>Add Book | Book Paradise</title>
<style>
label{
font-weight: bold
}

.error-message{
font-weight: bold;
color: red;
display:none
}
</style>
</head>

<body>

	<%@ include file="header.jsp"%>

	<div>
	<div class="container text-center my-5" style="background-color: rgba(68, 150, 61, 0.5);">
		<h2 class="margin">Add a book to Book Paradise!</h2>
		<br>
		
		<div class="row">

			<div class="col-md-4"></div>

			<div class="col-md-4">


				<c:url value="/upload-book" var="book_upload_url"/>
				<form method="POST" action="${book_upload_url}" enctype="multipart/form-data" class="form-inline" 
				name="myForm" onsubmit="return validateInputs()">

					<input type="hidden" value="${_csrf.token}"/>
					
				
					
					<div class="form-group">
					<label for="bookName" >Book Name:</label>
					<input type="text" name="bookName" class="form-control"/>
					</div>
					<br>
					<br>
					<div class="form-group">
						<label for="genre">Genre:</label>
						<input type="text" name="genre" class="form-control"/>
					</div>
					<br>	
					<br>
					<div class="form-group">
						<label for="author">Authors:</label>
						<input type="text" name="author" class="form-control"/>
					</div>
					<br>	
					<br>
					<div class="form-group">
						<label for="translators">Translators:</label>
						<input type="text" name="translators" class="form-control"/>
					</div>
					<br>	
					<br>
					<div class="form-group">
						<label for="tags">Tags:</label>
						<input type="text" name="tags" class="form-control"/>
					</div>
					<br>	
					<br>
					
					<div class="form-group">
					<label for="coverImage">Upload cover image:</label>
					<input type="file" name="coverImage" accept=".jpg"/><br><br>
					</div>
					
					<div class="form-group">
					
					<label for="firstChapter">Upload first chapter:</label>
					<input type="file" name="firstChapter" accept="application/pdf"/><br><br>
					<label for="secondChapter">Upload second chapter:</label>
					<input type="file" name="secondChapter" accept="application/pdf"/><br><br>
					<label for="thirdChapter">Upload third chapter:</label>
					<input type="file" name="thirdChapter" accept="application/pdf"/>
					</div>
					<br>	
					<div class="error-message" id="e1">Please fill all the necessary fields.</div>
					<div class="error-message" id="e2">Please upload at least one chapter.</div>
					<div class="error-message" id="e3">Please upload a cover image.</div>
					<br>
					<div class="form-group" style="padding: 1em">
					<button type="submit" class="btn btn-primary">Save Book</button>
					</div>
			
			
				</form>

			</div>


			<div class="col-md-4"></div>

		</div>

	</div>

</div>
	<%@ include file="footer.jsp"%>

</body>
</html>
