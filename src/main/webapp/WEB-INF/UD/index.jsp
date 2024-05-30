<%@ page import="com.example.ais_ecc.entity.User" %>
<%@ page import="java.util.List" %>
<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="MyTagLib" prefix="my" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="ru">
<head>
    <title></title>
    <%@ include file="../template/_metaStyle.jsp" %>
</head>

<body>
<jsp:include page="../template/header.jsp"/>

<div class="container">
    <h1 class="mt-4" align="center">Уголовные дела</h1>
    <a class="mt-4" align="center" href="createUD/create">Создать</a>
    <ul>
        <jsp:useBean id="uds" scope="request" type="java.util.List"/>

        <c:forEach items="${uds}" var="ud">
            <li><a href="/UD/${ud.id}">${ud.investigator.name} ${ud.dateTime}</a></li>
        </c:forEach>
    </ul>
</div>

<jsp:include page="../template/footer.jsp"/>

</body>
</html>
