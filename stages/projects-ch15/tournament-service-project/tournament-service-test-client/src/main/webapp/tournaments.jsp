<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
	"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
	<head>
		<title>Upcoming golf tournaments</title>
	</head>
	<body>
		<h1>Upcoming golf tournaments</h1>
		<ul>
			<c:forEach var="_tournament" items="#{upcomingTournaments}">
			<c:url var="tournamentUrl" value="${_tournament.website}"/>
			<li><a href="${tournamentUrl}"><c:out value="${_tournament.name}"/></a></li>
			</c:forEach>
		</ul>
	</body>
</html>
