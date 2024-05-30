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
    <h1 class="mt-4" align="center">Действия</h1>
    <div class="actions">
    <c:forEach var="action" items="${actions}">
        <my:Action action="${action}" />
    </c:forEach>
    </div>

</div>

<jsp:include page="../template/footer.jsp"/>

</body>
</html>
