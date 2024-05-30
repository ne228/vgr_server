<jsp:useBean id="createUD" scope="request" type="com.example.ais_ecc.entity.actions.CreateUD"/>
<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="MyTagLib" prefix="my" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="com.example.ais_ecc.entity.User" %>
<%@ page import="com.example.ais_ecc.entity.Role" %>

<%@ page import="java.util.Set" %>


<!DOCTYPE html>
<html lang="ru">
<head>
    <title></title>
    <%@ include file="../template/_metaStyle.jsp" %>
</head>

<body>
<jsp:include page="../template/header.jsp"/>

<div class="container">
    <h1 class="mt-4" align="center">Уголовное дело №${createUD.id}</h1>
    <h2 class="mt-4" align="center">Возбуждение уголовного дела</h2>
    <div class="mt-4">
        <h4>Повод:</h4>
        <p class="lead border p-3 mt-3">${createUD.occasion}</p>
    </div>
    <div class="mt-4">
        <h4>Основание:</h4>
        <p class="lead border p-3 mt-3">${createUD.base}</p>
    </div>
    <c:if test="${createUD.documents != null}">
        <div class="card">
            <div class="card-body">

                <h3 class="card-title mt-4">Приложения:</h3>
                <ul class="list-group">
                    <c:forEach var="document" items="${createUD.documents}">
                        <li class="list-group-item">
                            <strong><c:out value="${document.fileName}" /></strong> -
                            <c:out value="${document.filePath}" />
                        </li>
                    </c:forEach>
                </ul>
            </div>
        </div>
    </c:if>
    <div class="mt-4">
        <h4>Статья:</h4>
        <p class="lead border p-3 mt-3">${createUD.article}</p>
    </div>
    <div class="mt-4">
        <h4>Текст заявления:</h4>
        <p class="lead border p-3 mt-3">${createUD.textOfApplicant}</p>
    </div>
    <div class="mt-4">
        <h4>Фабула:</h4>
        <p class="lead border p-3 mt-3">${createUD.fabula}</p>
    </div>

    <div class="mt-4">
        <h3>Заявители:</h3>
        <c:if test="${not empty createUD.applicant}">
            <c:forEach var="applicant" items="${createUD.applicant}" varStatus="iter">
                <div class="border border-success p-3 mt-3">
                    <h5>Заявитель ${iter.index + 1}</h5>
                    <p><strong>Фамилия:</strong> <span class="lead">${applicant.surname}</span></p>
                    <p><strong>Имя:</strong> <span class="lead">${applicant.name}</span></p>
                    <p><strong>Отчество:</strong> <span class="lead">${applicant.lastName}</span></p>
                    <p><strong>Паспортные данные:</strong> <span class="lead">${applicant.passport}</span></p>
                    <p><strong>Дата рождения:</strong> <span class="lead">${applicant.dateOfBirth}</span></p>
                    <!-- Добавьте другие поля объекта Applicant по необходимости -->
                </div>
            </c:forEach>
        </c:if>
    </div>

    <div class="mt-4">
        <h3>Подозреваемые:</h3>
        <c:if test="${not empty createUD.suspect}">
            <c:forEach var="suspect" items="${createUD.suspect}" varStatus="iter">
                <div class="border border-danger p-3 mt-3">
                    <h5>Подозреваемый ${iter.index + 1}</h5>
                    <p><strong>Фамилия:</strong> <span class="lead">${suspect.surname}</span></p>
                    <p><strong>Имя:</strong> <span class="lead">${suspect.name}</span></p>
                    <p><strong>Отчество:</strong> <span class="lead">${suspect.lastName}</span></p>
                    <p><strong>Паспортные данные:</strong> <span class="lead">${suspect.passport}</span></p>
                    <p><strong>Дата рождения:</strong> <span class="lead">${suspect.dateOfBirth}</span></p>
                    <!-- Добавьте другие поля объекта Suspect по необходимости -->
                </div>
            </c:forEach>
        </c:if>
    </div>

    <div class="mt-4">
        <h4>Дата и время возбуждения уголовного дела:</h4>
        <p class="lead border p-3 mt-3">${createUD.dateTime}</p>
    </div>



    <c:if test="${createUD.submit eq true}">
        <div class="mt-4">
            <h4>Дата и время подтверждения прокурором уголовного дела:</h4>
            <p class="lead border p-3 mt-3">${createUD.dateOfSubmit}</p>
        </div>

        <div class="mt-4">
            <h4>Прокурор</h4>
            <p class="lead border p-3 mt-3">${createUD.prosecutor.post} ${createUD.prosecutor.surname} ${createUD.prosecutor.name} ${createUD.prosecutor.lastName}</p>
        </div>

    </c:if>
    <%--    Если нет потверждени прокурора--%>
    <c:if test="${createUD.submit ne true}">
        <my:roleCheck roles="${pageContext.request.userPrincipal.authorities}" allowedRole="ROLE_PROSECUTOR">
            <div class="mt-4" align="center">
                <div class="btn btn-primary">
                    <a href="/createUD/submit/${createUD.id}">
                        Дать согласие прокурора
                    </a>
                </div>
            </div>
        </my:roleCheck>
    </c:if>

    <div class="mt-4">
        <h4>Следователь</h4>
        <p class="lead border p-3 mt-3">${createUD.investigator.post} ${createUD.investigator.surname} ${createUD.investigator.name} ${createUD.investigator.lastName}</p>
    </div>


    <div class="mt-4" align="center">
        <a  href="report/${createUD.id}" class="btn btn-primary">Создать отчет</a>
    </div>

</div>

<jsp:include page="../template/footer.jsp"/>

</body>
</html>
