<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Registracija</title>
<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath}/resources/css/administracija.css">
</head>
<body>
	<div>
		<h1>Registracija</h1>
		<ul><li><a
			href="${pageContext.servletContext.contextPath}/mvc/administracija/pocetak">
				Početna </a></li></ul><br> <br>
		<form method="POST"
			action="${pageContext.servletContext.contextPath}/mvc/administracija/registracija">

			<label for="ime">Ime:</label><br> <input type="text" id="ime"
				name="ime"> <br><br> <label for="prezime">Prezime:</label><br>
			<input type="text" id="prezime" name="prezime"> <br><br> <label
				for="email">Email:</label><br> <input type="email" id="email"
				name="email"> <br><br> <label for="korime">Korisničko
				ime:</label><br> <input type="text" id="korime" name="korime">
			<br><br> <label for="lozinka">Lozinka:</label><br><input
				type="password" id="lozinka" name="lozinka"> <br> <br>
			<br> <input type="submit" value="Registracija"> <br>

		</form>


		<br>

		<c:if test="${requestScope.poruka != null}">
			<p>${requestScope.poruka}</p>
		</c:if>
	</div>
</body>
</html>