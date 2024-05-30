<jsp:useBean id="authRequest" scope="request" type="com.example.ais_ecc.models.AuthRequest"/>
<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="MyTagLib" prefix="my" %>

<!DOCTYPE html>

<head>
    <title></title>
    <%@ include file="../template/_metaStyle.jsp" %>
</head>
<body>

<jsp:include page="../template/header.jsp" />


<div class="container" >

    <h2>Login</h2>
    <form action="${pageContext.request.contextPath}/auth/loginAction" method="POST">
        <div class="form-group">
            <label for="email">Email</label>
            <input type="text" class="form-control" placeholder="Введите email" id="email" name="email" value="${authRequest.email}" />
             <my:ErrorTag errors="${errors}" fieldName="email" />

        </div>
        <div class="form-group">
            <label for="password">Пароль</label>
            <input type="password" class="form-control" placeholder="Введите пароль" id="password" name="password" value="${authRequest.password}" />
            <my:ErrorTag errors="${errors}" fieldName="password" />
        </div>

        <input type="hidden" name="returnUrl" value="${authRequest.returnUrl}" />
        <button type="submit" class="btn btn-primary">Войти</button>
    </form>
</div>

<jsp:include page="../template/footer.jsp" />

</body>
</html>
