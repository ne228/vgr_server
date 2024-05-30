<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="MyTagLib" prefix="my" %>
<jsp:useBean id="createUD" scope="request" type="com.example.ais_ecc.models.CreateUDModel"/>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml" lang="ru">
<head>
    <title></title>
    <%@ include file="../template/_metaStyle.jsp" %>
</head>
<body>


<jsp:include page="../template/header.jsp"/>

<div class="container">
    <h2>Login</h2>
    <form action="/createUD/create" method="POST" enctype="multipart/form-data">
        <div class="form-group">
            <label for="Occasion">Повод</label>
            <textarea  type="text" class="form-control" placeholder="Введите повод" id="Occasion" name="Occasion"></textarea>
            <my:ErrorTag errors="${errors}" fieldName="Occasion"/>
        </div>
        <div class="form-group">
            <label for="Base">Основание</label>
            <textarea  type="text" class="form-control" placeholder="Введите основание" id="Base" name="Base"></textarea>
            <my:ErrorTag errors="${errors}" fieldName="Base"/>
        </div>

        <div class="form-group">
            <label for="files">Загрузите приложения:</label>
            <input type="file" class="form-control-file" name="files" id="files" multiple>
            <my:ErrorTag errors="${errors}" fieldName="files"/>
        </div>

        <div class="form-group">
            <label for="Article">Статья</label>
            <textarea  type="text" class="form-control" placeholder="Введите статью" id="Article" name="Article"></textarea>
            <my:ErrorTag errors="${errors}" fieldName="Article"/>
        </div>

        <div class="form-group">
            <label for="TextOfApplicant">Текст заявления</label>
            <textarea  type="text" class="form-control" placeholder="Введите текст заявления" id="TextOfApplicant"
                   name="TextOfApplicant"></textarea>
            <my:ErrorTag errors="${errors}" fieldName="TextOfApplicant"/>
        </div>

        <div class="form-group">
            <label for="Fabula">Фабула</label>
            <textarea  type="text" class="form-control" placeholder="Введите фабулу" id="Fabula" name="Fabula"></textarea>
            <my:ErrorTag errors="${errors}" fieldName="Fabula"/>
        </div>

        <div class="form-group">
            <label for="applicant">Укажите заявителей:</label>
            <select id="applicant" class="form-control form-select form-select-lg mb-3" name="ApplicantIds"
                    multiple="multiple">

                <c:forEach var="user" items="${users}">
                    <option value="${user.id}">${user.surname} ${user.name} ${user.lastName} ${user.dateOfBirth}</option>
                </c:forEach>
            </select>
            <my:ErrorTag errors="${errors}" fieldName="ApplicantIds"/>
        </div>

        <div class="form-group">
            <label for="suspect">Укажите подозреваемых:</label>
            <select id="suspect" class="form-control form-select form-select-lg mb-3" name="SuspectIds"
                    multiple="multiple">

                <c:forEach var="user" items="${users}">
                    <option value="${user.id}">${user.surname} ${user.name} ${user.lastName} ${user.dateOfBirth}</option>
                </c:forEach>
            </select>
            <my:ErrorTag errors="${errors}" fieldName="SuspectIds"/>
        </div>

        <br/>
        <button type="submit" class="btn btn-primary">Создать</button>
    </form>
</div>

<jsp:include page="../template/footer.jsp"/>

</body>
</html>
