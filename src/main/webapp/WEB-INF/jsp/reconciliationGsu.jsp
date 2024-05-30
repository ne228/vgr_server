<%--suppress HtmlUnknownTarget --%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Загрузка файлов</title>
    <link href="/css/bootstrap.css" rel="stylesheet">
</head>
<body>

<br/>
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
                <th>№ основного уголовного дела</th>
                <th>Дата возбуждения УД</th>
                <th>Фамилия</th>
                <th>Имя</th>
                <th>Отчество</th>
                <th>Полная дата рождения</th>
                <th>№ розыскного дела </th>
                <th>Дата заведения розыскного дела</th>

            </tr>
            </thead>
            <tbody>
            <br/>
            <p class="text-body">"${filename}" загружен</p>
            <p class="text-body">Загружено в базу: ${size}</p>
            <c:forEach var="item" items="${table}">
                <tr>
                    <td>${item.id}</td>
                    <td>${item.nUd}</td>
                    <td>${item.dtUd}</td>
                    <td>${item.fam}</td>
                    <td>${item.imj}</td>
                    <td>${item.othc}</td>
                    <td>${item.dtRojd}</td>
                    <td>${item.nRd}</td>
                    <td>${item.dtRd}</td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
    </c:when>
</c:choose>

</body>
</html>
