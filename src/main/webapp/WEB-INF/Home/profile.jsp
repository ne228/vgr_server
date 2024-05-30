<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<jsp:useBean id="user" scope="request" type="com.example.ais_ecc.entity.User"/>
<!DOCTYPE html>
<html>
<head>
    <title></title>
    <%@ include file="../template/_metaStyle.jsp" %>
</head>
<body>
<jsp:include page="../template/header.jsp"/>

<div class="container">
    <a href="/UD/create">Create UD</a>
    <h1>User Details</h1>

    <%@ page import="com.example.ais_ecc.entity.User" %>
    <%@ page import="com.example.ais_ecc.entity.Role" %>

    <%@ page import="java.util.Set" %>

    <%
        User user1 = (User) request.getAttribute("user");
        Set<Role> roles = user1.getRoles();
    %>

    <c:choose>
        <c:when test="${roles.contains('ROLE_PROSECUTROR')}">
            <p>Строка для роли ROLE_PROSECUTRO</p>
        </c:when>
        <c:otherwise>
            <p>Другая строка</p>
        </c:otherwise>
    </c:choose>


    <a href="/UD/create">Create UD</a>
    <h1>User Details</h1>
    <p>ID: ${user.id}</p>
    <p>Username: ${user.username}</p>
    <p>Email: ${user.email}</p>
    <p>Password: ${user.password}</p>
    <p>Surname: ${user.surname}</p>
    <p>Name: ${user.name}</p>
    <p>Last Name: ${user.lastName}</p>
    <p>Passport: ${user.passport}</p>
    <p>Date of Birth: ${user.dateOfBirth}</p>

    <h3>Roles:</h3>
    <c:forEach var="role" items="${user.roles}">
        <li>${role.name}</li>
    </c:forEach>

</div>

<jsp:include page="../template/footer.jsp"/>
</body>
</html>
