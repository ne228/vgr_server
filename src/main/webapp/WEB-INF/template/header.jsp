<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="MyTagLib" prefix="my" %>
<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>


<nav class="navbar navbar-expand-lg navbar-light bg-light">
    <a class="navbar-brand" href="#">
        <img class="logo" src="${pageContext.request.contextPath}/img/gerb-mvd.png" alt="logo">
    </a>
    <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarNav"
            aria-controls="navbarNav" aria-expanded="false" aria-label="Toggle navigation">
        <span class="navbar-toggler-icon">

        </span>
    </button>
    <div class="collapse navbar-collapse" id="navbarNav">
        <ul class="navbar-nav">
            <li class="nav-item active">
                <a class="nav-link" href="/home">Главная <span class="sr-only">(текущая)</span></a>
            </li>
            <li class="nav-item active">
                <a class="nav-link" href="/UD">Уголовные дела</a>
            </li>

            <c:if test="${not empty pageContext.request.userPrincipal.name}">
                <li class="nav-item active">
                    <a class="nav-link">${pageContext.request.userPrincipal.name}</a>
                </li>

                <li class="nav-item active">
                    <a class="nav-link" href="/auth/logout">Выйти</a>
                </li>
            </c:if>

            <li class="nav-item">
                <my:roleCheck roles="${pageContext.request.userPrincipal.authorities}" allowedRole="ROLE_ADMIN">
                    <p class="nav-link">hello role admin</p>
                </my:roleCheck>
            </li>

        </ul>
    </div>
</nav>
