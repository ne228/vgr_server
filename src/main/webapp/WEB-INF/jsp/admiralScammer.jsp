<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Загрузка файлов</title>
    <link href="/css/bootstrap.css" rel="stylesheet">
</head>
<body>

<br/>
<h1>АДМИРАЛ МОШЕННИК</h1>
<form id="loadForm" method="POST" action="load" enctype="multipart/form-data">
    <label class="text-body">Загрузите файл</label>
    <br/>
    <input type="file" name="multipartFile" class="form-control"/>
    <br/>
    <button type="submit" class="btn btn-primary" value="Загрузить файл">Заугрзить файл</button>
</form>
<hr/>
<c:choose>
    <c:when test="${filename != null}">
        <table class="table">
            <thead>
            <tr>
                <th>Id</th>
                <th>КУСП</th>
                <th>Дата и время регистрации</th>
                <th>Тер. орган</th>
                <th>Заявитель</th>
                <th>Адрес заявителя</th>
                <th>Телефон заявителя</th>
                <th>Дата и время происшествия</th>
                <th>Фабула первичная</th>
                <th>Тип происшествия</th>
                <th>Исполнитель/th>
                <th>Тип заявления</th>
            </tr>
            </thead>
            <tbody>
            <br/>
            <p class="text-body">"${filename}" загружен</p>
            <p class="text-body">Загружено в базу: ${size}</p>
            <c:forEach var="item" items="${table}">
                <tr>
                    <td>${item.id}</td>
                    <td>${item.nCusp}</td>
                    <td>${item.dateTimeReg}</td>
                    <td>${item.department}</td>
                    <td>${item.applicant}</td>
                    <td>${item.address}</td>
                    <td>${item.phone}</td>
                    <td>${item.dateTime}</td>
                    <td>${item.typeIncident}</td>
                    <td>${item.executor}</td>
                    <td>${item.typeApplicant}</td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
    </c:when>
</c:choose>

</body>
</html>
