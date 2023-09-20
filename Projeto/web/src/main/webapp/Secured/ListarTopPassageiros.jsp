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
  <title>Top 5 passageiros</title>
</head>
<body>
<c:forEach var="s" items="${lista}">
    <p> ${s} </p>
</c:forEach>
<a href="/web/Secured/ManagerLogout.html">Logout</a>

</body>
</html>
