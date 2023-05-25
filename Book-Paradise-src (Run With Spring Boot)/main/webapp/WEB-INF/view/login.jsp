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
  <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.1/css/all.min.css"/>
  <script src="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.1/js/all.min.js"></script>
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
                  <h4 class="mt-1 mb-5 pb-1">Welcome to Book Paradise!</h4>
                </div>
                
                <c:if test="${error_msg != null}">
					<div class="alert alert-danger">${error_msg}</div>
				</c:if>

                <c:url var="post_url" value="/dologin" />
		        <form action="${post_url}" method="post">
				  <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />

                  <div class="form-outline mb-4">
                    <input type="email" id="username" class="form-control" name="username"/>
                    <label class="form-label" for="username">Email</label>
                  </div>

                  <div class="form-outline mb-4">
                    <input type="password" id="password" class="form-control" name="password"/>
                    <label class="form-label" for="password">Password</label>
                  </div>
                  
                  <div class="text-center pt-1 mb-5 pb-1">
                  <input type="submit" class="btn btn-primary fa-lg py-3 px-5 gradient-custom-2" name="Login" value="Login"/>
                  </div>

                </form>
                
                  <div class="d-flex align-items-center justify-content-center pb-4">
                    <p class="mb-0 me-2">Don't have an account?</p>
                    <a class="btn btn-outline-success" href="/account/user-register">Create new</a>
                  </div>
                  
                  <div class="d-flex align-items-center justify-content-center pb-4">
                    <p class="mb-0 me-2">Forgot password?</p>
                    <a href="#">Recover</a>
                  </div>

              </div>
            </div>
            <div class="col-lg-6 d-flex align-items-center gradient-custom-2">
              <div class="px-3 py-4 p-md-5 mx-md-4">
                <h4 class="mb-4" style="text-align: justify">Join Book Paradise to enjoy all your favorite e-books in one place!</h4>
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