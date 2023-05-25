<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@include file="init.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
 <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.2.3/dist/css/bootstrap.min.css" rel="stylesheet"/>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.2.3/dist/js/bootstrap.bundle.min.js"></script>
<title>Login | Book Paradise</title>
<style>

h4{
color: #008040}

</style>
</head>
<body>
<%@include file="header.jsp"%>
<section class="h-100 gradient-form">
  <div class="container py-5 h-100">
    <div class="row d-flex justify-content-center align-items-center h-100">
   
        <div class="card rounded-3" style="background-color: #fddbb4">
          <div class="row g-0">
            <div class="col-lg-6">
              <div class="card-body p-md-5 mx-md-4">

                <div class="text-center">
                  <h4 class="mt-1 mb-5 pb-1">Reset your password</h4>
                </div>
                
                <c:if test="${error_msg != null}">
					<div class="alert alert-danger">${error_msg}</div>
				</c:if>

                <c:url var="post_url" value="/reset-password"/>
		        <form action="${post_url}" method="post">
				  <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
					
				  <input type="hidden" value="${email}" name="email"/>
				  <input type="hidden" value="${resetCode}" name="realCode"/>

                  <div class="form-outline mb-4">
                    <input type="number" class="form-control" name="resetCode"/>
                    <label class="form-label" for="username">Code</label>
                  </div>

                  <div class="form-outline mb-4">
                    <input type="password" class="form-control" name="newPassword"/>
                    <label class="form-label" for="password">Password</label>
                  </div>
                  
                  <div class="form-outline mb-4">
                    <input type="password" class="form-control" name="confirmPassword"/>
                    <label class="form-label" for="password">Confirm Password</label>
                  </div>
                  
                  <div class="text-center pt-1 mb-5 pb-1">
                  <button type="submit" class="btn btn-primary fa-lg py-3 px-5 gradient-custom-2">Save New Password</button>
                  </div>

                </form>
                
                  <div class="d-flex align-items-center justify-content-center pb-4">
                    <p class="mb-0 me-2">Didn't receive an email?</p>
                    <a class="btn btn-success" href="/reset-password?email=${email}">Send Code Again</a>
                  </div>

              </div>
            </div>
            <div class="col-lg-6 d-flex align-items-center gradient-custom-2">
              <div class="px-3 py-4 p-md-5 mx-md-4">
                <h4 class="mb-4" style="text-align: justify">Join Sartoon to enjoy all your favorite novels and manhuas in one place!</h4>
              </div>
            </div>
          </div>
        </div>
     
    </div>
  </div>
</section>
<%@include file="footer.jsp"%>
</body>
</html>