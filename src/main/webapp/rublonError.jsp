<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<link rel="stylesheet"
			href="https://stackpath.bootstrapcdn.com/bootstrap/4.1.3/css/bootstrap.min.css"
			integrity="sha384-MCw98/SFnGE8fJT3GXwEOngsV7Zt27NXFoaoApmYm81iuXoPkFOJwJ8ERdknLPMO"
			crossorigin="anonymous">
		<link rel="stylesheet" type="text/css" href="style.css">
		<link rel="icon" href="favicon.png">
		<title>Rublon example</title>
	</head>
	<body>
		<div class="align-center">
			<div class="alert alert-danger align-center" role="alert"><%=request.getAttribute("error")%></div>
			<a href="login" class="btn btn-primary mt-3">Back</a>
		</div>
	</body>
</html>
