<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Administracija</title>
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/css/administracija.css">
<body>
<div>
	<h1>Administracija</h1>
	<c:choose>
		<c:when test="${requestScope.token != 'valjan'}">
			<ul>
			<li><a
				href="${pageContext.servletContext.contextPath}/mvc/administracija/registracija">Registracija</a></li>
			<li><a
				href="${pageContext.servletContext.contextPath}/mvc/administracija/prijava">Prijava</a></li>
			</ul>
		</c:when>

		<c:otherwise>

			<ul>
			<li><a
				href="${pageContext.servletContext.contextPath}/mvc/administracija/registracija">Registracija</a></li>
			<li><a
				href="${pageContext.servletContext.contextPath}/mvc/administracija/pregledKorisnika">Pregled
					korisnika</a></li>

			<li><a
				href="${pageContext.servletContext.contextPath}/mvc/administracija/upravljanjePosluziteljem">Upravljanje poslužiteljem</a></li>
			</ul>

		</c:otherwise>
	</c:choose>
</div>
</body>
</html>