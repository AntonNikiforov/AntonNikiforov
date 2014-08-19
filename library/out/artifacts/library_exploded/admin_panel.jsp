
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title></title>
</head>
<body>

<fmt:setLocale value="${sessionScope.locale}"/>
<fmt:setBundle basename="locale" var="locale"/>

<div class="container well-sm" style="max-width: 500px">
    <c:if test="${sessionScope.admin}">
        <div class="form-group">
            <div class="pull-left">
                <a class="btn btn-default" href="#" role="button">Новый заказ</a>
                <a class="btn btn-default" href="#" role="button">История заказов</a>
            </div>
            <div class="btn-group pull-right">
                <a class="btn btn-warning" href="edit_user?id=${requestScope.user.id}" role="button">Edit</a>
                <a class="btn btn-danger" href="delete_user?id=${requestScope.user.id}" role="button">Delete</a>
            </div>
        </div>
    </c:if>
</div>

</body>
</html>
