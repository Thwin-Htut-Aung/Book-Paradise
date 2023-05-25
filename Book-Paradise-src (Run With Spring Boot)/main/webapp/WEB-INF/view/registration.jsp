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


<title>Register | Book Paradise</title>
<style>
	.regisForm{
		background-color: rgba(68, 150, 61, 0.5);
	}
</style>
</head>
<body>
<%@include file="header.jsp"%>

	<div
		class="d-flex flex-wrap align-items-center justify-content-center mx-auto py-5 my-5 col-lg-6 col-md-10 col-sm-12 rounded regisForm">
		<form:form method="post" class="col-lg-8 col-md-10" id="regForm"
			modelAttribute="user" action= "${action}" onSubmit="validation()">
			<h2 class="d-flex justify-content-center mb-5 text-capitalize fw-bold">Registration Form</h2>
	
				<div class="col-sm-12">
				<form:label class="form-label" for="fullName" path="fullName">Full Name</form:label>
				<form:input type="text" class="form-control" path="fullName" id="fullName" />
			</div>
			<div class="col-sm-12">
				<form:label class="form-label" for="email" path="email">Email</form:label>
				<form:input type="email" class="form-control" path="email" id="email" />
				<c:if test="${not empty msg_dupe}"><div style="color:red">${msg_dupe}</div></c:if>
				<c:if test="${not empty msg_no_email}"><div style="color:red">${msg_no_email}</div></c:if>
			</div>
			<div class="col-sm-12">
				<form:label class="form-label" for="password" path="password">Password</form:label>
				<form:input type="password" class="form-control" path="password" id="password" />
			</div>
			<div class="col-sm-12">
				<label class="form-label" for="rePassword">Confirm Password</label>
				<input type="password" class="form-control" name="rePassword" id="rePassword">
				<c:if test="${not empty msg_wrong_password}"><div style="color:red">${msg_wrong_password}</div></c:if>
			</div>
				
	
			<form:button type="submit" class="btn btn-primary" id="register"
				name="register" style="margin-top: 1vh">Register</form:button>
		</form:form>
	</div>
	
	<%@include file="footer.jsp"%>


</body>
</html>