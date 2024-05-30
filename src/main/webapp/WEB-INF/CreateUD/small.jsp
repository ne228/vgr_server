<jsp:useBean id="createUD" scope="request" type="com.example.ais_ecc.entity.actions.CreateUD"/>
<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>


<a href="/createUD/${createUD.id}">
    <div class="action">
        <div class="action-icon">

            <img src="${pageContext.request.contextPath}/img/icons8-pencil-48.png" alt="Icon">
        </div>

        <div class="action-header">
            <strong>Type</strong>
            <p>${createUD.dateTime}</p>
        </div>

        <div class="action-desc">
            <strong>Возбуждение уголовного дела</strong>
        </div>

    </div>
</a>

