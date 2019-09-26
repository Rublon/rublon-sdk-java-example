<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.1.3/css/bootstrap.min.css" integrity="sha384-MCw98/SFnGE8fJT3GXwEOngsV7Zt27NXFoaoApmYm81iuXoPkFOJwJ8ERdknLPMO" crossorigin="anonymous">
    <link rel="stylesheet" href="https://use.fontawesome.com/releases/v5.6.1/css/all.css" integrity="sha384-gfdkjb5BdAXd+lj+gudLWI+BXq4IuLW5IT+brZEZsLFm++aCMlF1V92rMkPaX4PP" crossorigin="anonymous">
    <link rel="stylesheet" href="style.css">
    <script type="text/javascript" src="validator.js"></script>
    <link rel="icon" href="Rublon-favicon-128.png">
    <title>Rublon example</title>
</head>
<body>
    <nav class="navbar">
        <div class="container">
            <div class="row">
                <div class="col-4 logo-container">
                    <img src="Rublon-favicon-128.png" class="mr-3 logo">
                    <a href="index.jsp">Rublon Example</a>
                </div>
                <div class="col-4"></div>
                <div class="col-4 align-right">
                    <a href="index.jsp" class="nav-link"><i class="fas fa-sign-in-alt"></i> Switch to 2factor login</a>
                </div>
            </div>
        </div>
    </nav>

    <c:choose>
        <c:when test="${configError == true}">
            <div class="alert alert-warning align-center" role="alert">Please first fill config file.</div>
        </c:when>
        <c:otherwise>
            ${rublonWidget}
        </c:otherwise>
    </c:choose>
</body>
</html>
