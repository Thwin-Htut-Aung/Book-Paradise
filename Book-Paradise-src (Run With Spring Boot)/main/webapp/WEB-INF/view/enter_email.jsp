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
<title>Reset | BP</title>
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
                  <h4 class="mt-1 mb-5 pb-1">Enter your email</h4>
                </div>
                
                <c:if test="${error_msg != null}">
					<div class="alert alert-danger">${error_msg}</div>
				</c:if>

                <c:url var="post_url" value="/reset-password"/>
		        <form action="${post_url}" method="get">
				  <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />

                  <div class="form-outline mb-4">
                    <input type="email" class="form-control" name="email"/>
                    <label class="form-label" for="email">Email</label>
                  </div>
                  
                  <div class="text-center pt-1 mb-5 pb-1">
                  <button type="submit" class="btn btn-primary fa-lg py-3 px-5 gradient-custom-2">Continue</button>
                  </div>

                </form>
                

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