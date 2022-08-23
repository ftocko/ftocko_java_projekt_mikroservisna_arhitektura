<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Prijava</title>
<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath}/resources/css/administracija.css">
</head>
<body>
	<div>
		<h1>Prijava</h1>
		<ul><li><a
			href="${pageContext.servletContext.contextPath}/mvc/administracija/pocetak">
				Početna </a></li></ul> <br> <br>
		<form method="POST"
			action="${pageContext.servletContext.contextPath}/mvc/administracija/prijava">

			<label for="korisnik">Unesite korisničko ime:</label><br> <input
				type="text" id="korisnik" name="korisnik"> <br> <br>
			<label for="lozinka">Unesite lozinku:</label><br> <input
				type="password" id="lozinka" name="lozinka"> <br> <br>
			<input type="submit" value="Prijava"> <br>

		</form>

		<br>

		<c:if test="${requestScope.poruka != null}">
			<p>${requestScope.poruka}</p>
	</c:if>
	</div>
</body>
</html>