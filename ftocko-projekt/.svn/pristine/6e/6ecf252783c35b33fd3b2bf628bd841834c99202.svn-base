<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Upravljanje poslužiteljem</title>
<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath}/resources/css/administracija.css">
</head>
<body>
	<div>
		<h1>Upravljanje poslužiteljem</h1>
		<ul><li><a
			href="${pageContext.servletContext.contextPath}/mvc/administracija/pocetak">
				Početna </a></li></ul> <br> <br>

		<c:choose>
			<c:when test="${requestScope.status == null}">

				<c:redirect url="/mvc/administracija/pocetak" />

			</c:when>

			<c:otherwise>

			<p>Status poslužitelja: ${requestScope.status}</p>
			<br>
				<br>
				<br>
				<form method="POST"
					action="${pageContext.servletContext.contextPath}/mvc/administracija/posaljiKomandu">

					<label for="select">Odaberi komandu:</label> <select id="select"
						name="komanda">
						<option value="Inicijalizacija poslužitelja">Inicijalizacija
							poslužitelja</option>
						<option value="Prekid rada poslužitelja">Prekid rada
							poslužitelja</option>
						<option value="Učitavanje podataka">Učitavanje podataka</option>
						<option value="Brisanje podataka">Brisanje podataka</option>
					</select> <br> <br> <input type="submit" value="Aktiviraj komandu">
					<br>

				</form>

				<br>
				<c:if test="${requestScope.tekst != null}">
					<p>${requestScope.tekst}</p>
				</c:if>

			</c:otherwise>
		</c:choose>

		<br>
	</div>
</body>
</html>