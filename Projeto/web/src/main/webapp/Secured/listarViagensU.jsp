<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="java.util.ArrayList" %><%--
  Created by IntelliJ IDEA.
  User: Beatriz Madeira
  Date: 13/11/2021
  Time: 00:25
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Listar Viagens</title>
</head>
<body>
    <c:forEach var="arrS" items="${lista}">
        <c:forEach var="s" items="${arrS}">
            <p> ${s} </p>
        </c:forEach>
    </c:forEach>
    <a href="/web/Secured/Logout.html">Logout</a>
    <a href="/web/Secured/display.jsp">Voltar para menu inicial</a>

</body>
</html>
