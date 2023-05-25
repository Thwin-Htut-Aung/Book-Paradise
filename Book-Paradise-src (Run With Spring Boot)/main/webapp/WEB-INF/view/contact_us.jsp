<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@include file="init.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Contact Book Paradise</title>
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.2.3/dist/css/bootstrap.min.css" rel="stylesheet">
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.2.3/dist/js/bootstrap.bundle.min.js"></script>
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.1/css/all.min.css">
  <script src="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.1/js/all.min.js"></script>
<style>

.contact-session{
 background-color: rgba(0, 128, 64, .5);
}

#contact-info, p{
text-align: center}
</style>
</head>

<body>
<%@include file="header.jsp"%>

<div
class="contact-session container">
		
		<sec:authorize access="hasAuthority('Role_User') or !isAuthenticated()">
		<div id="contact-info">
		<h1>Contact Us!</h1>

		<h3>We are always welcoming feedback from our viewers!</h3>
		<h3><i class="fas fa-address-book"></i> Thwin Htut Aung, Founder</h3>
		<h3><i class="fas fa-envelope"></i> thwinhtut@bookparadise.org</h3>
		<h3><i class="fas fa-phone"></i> +959 785664399</h3>
		</div>
		
		<form:form method="post" modelAttribute="feedback"
		action= "/submit-feedback" id="feedbackForm" name="feedbackForm"
		class="d-flex flex-wrap align-items-center justify-content-center mx-auto py-5 my-5 col-lg-6 col-md-10 col-sm-12 rounded">
			<h2 class="text-capitalize fw-bold">Submit Feedback</h2>
	
			<div class="col-sm-12">
				<form:label class="form-label" for="userName" path="userName">Name</form:label>
				<form:input type="text" class="form-control" path="userName" id="userName"/>
			</div>
			<div class="col-sm-12">
				<form:label class="form-label" for="email" path="email">Email</form:label>
				<form:input type="email" class="form-control" path="email" id="email" />
			</div>
			<div class="col-sm-12">
				<form:label class="form-label" for="feedback" path="feedbackContent">Feedback</form:label>
				<form:input type="text" class="form-control" path="feedbackContent" id="feedback"/>
			</div>
				
	
			<form:button type="submit" class="btn btn-primary"
				 style="margin-top: 1vh">Submit</form:button>
		</form:form>
		
		<c:if test="${not empty success_msg}">
		<p style="color: green">${success_msg}</p>
		</c:if>
		<c:if test="${not empty error_msg}">
			<p style="color: red">${error_msg}</p>
			</c:if>
		
		</sec:authorize>
		
		
		<sec:authorize access="hasAuthority('Role_Admin')">
		
		<h2>User Feedback</h2>
		
		<table class="table table-striped table-bordered">

						<thead>
							<tr>
								<th>User Name</th>
								<th>Email</th>
								<th>Feedback</th>
							</tr>
						</thead>
						
						<tbody>
						
						
						<c:forEach var="feedback" items="${feedbacks}">
						
						<input type="hidden" name="id" value="${feedback.feedbackId}">
							<tr>
								<td>${feedback.userName}</td>
								<td>${feedback.email}</td>
								<td>${feedback.feedbackContent}</td>
							</tr>

						</c:forEach>
						
						</tbody>
					</table>
		
		</sec:authorize>
	</div>




<%@include file="footer.jsp"%>
</body>
</html>