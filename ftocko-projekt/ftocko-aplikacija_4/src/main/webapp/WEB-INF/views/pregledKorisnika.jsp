<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Pregled korisnika</title>
<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath}/resources/css/administracija.css">
</head>
<body>
	<div>
		<h1>Pregled korisnika</h1>
		<ul><li><a
			href="${pageContext.servletContext.contextPath}/mvc/administracija/pocetak">
			Početna </a></li></ul> <br> <br> <a
			href="${pageContext.servletContext.contextPath}/mvc/administracija/obrisiVlastitiZeton">
			Obriši trenutni vlastiti žeton </a> <br> <br>

		<c:choose>
			<c:when test="${requestScope.korisnici == null}">

				<c:redirect url="/mvc/administracija/pocetak" />

			</c:when>

			<c:otherwise>

				<table border="1">
					<tr>
						<th>Ime</th>
						<th>Prezime</th>
						<th>Korisnik</th>
						<th>Email</th>
						<th>Lozinka</th>
						<c:if test="${requestScope.admin == 'admin'}">
							<th>Brisanje žetona</th>
						</c:if>
					</tr>
					<c:forEach var="k" items="${requestScope.korisnici}">
						<tr>
							<td>${k.ime}</td>
							<td>${k.prezime}</td>
							<td>${k.korIme}</td>
							<td>${k.email}</td>
							<td>${k.lozinka}</td>
							<c:if test="${requestScope.admin == 'admin'}">
								<td><a
									href="${pageContext.servletContext.contextPath}/mvc/administracija/obrisiZetoneKorisnika?korime=${k.korIme}">
										Obriši žetone korisnika </a></td>
							</c:if>
						</tr>
					</c:forEach>
				</table>

			</c:otherwise>
		</c:choose>

		<br> <br>

		<c:if test="${requestScope.poruka != null}">
			<p>${requestScope.poruka}</p>
	</c:if>
	</div>
</body>
</html>