
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html>
<head>

    <meta charset="utf-8">
    <title>Profile</title>

    <!--CSS-->
    <link href="view/css/bootstrap.min.css" rel="stylesheet">
    <link href="view/css/bootstrap-theme.min.css" rel="stylesheet">
    <link href="view/css/footer.css" rel="stylesheet">

</head>
<body>

<fmt:setLocale value="${sessionScope.locale}"/>
<fmt:setBundle basename="locale" var="locale"/>

<div id="wrap">

    <%@include file="header.jsp"%>

    <c:choose>
        <c:when test="${not empty requestScope.user}">
            <div class="container well-sm" style="max-width: 500px">
                <c:if test="${sessionScope.admin}">
                    <div class="form-group">
                        <div class="pull-left">
                            <a class="btn btn-default" href="add_booking?user_id=${requestScope.user.id}" role="button">Новый заказ</a>
                            <a class="btn btn-default" href="bookings" role="button">История заказов</a>
                        </div>
                        <div class="btn-group pull-right">
                            <a class="btn btn-warning" href="edit_user?id=${requestScope.user.id}" role="button">
                                <fmt:message key="edit" bundle="${locale}"/>
                            </a>
                            <a class="btn btn-danger" href="delete_user?id=${requestScope.user.id}" role="button">
                                <fmt:message key="delete" bundle="${locale}"/>
                            </a>
                        </div>
                    </div>
                </c:if>
            </div>

            <div class="container jumbotron well-lg" style="max-width: 500px">

                <c:if test="${sessionScope.id eq requestScope.user.id and not sessionScope.admin}">

                    <div class="container">
                        <div class="btn-group pull-left">
                            <a class="btn btn-default" href="#" role="button">History</a>
                        </div>
                        <div class="btn-group pull-right">
                            <a class="btn btn-warning" href="edit_user?id=${requestScope.user.id}" role="button">
                                <fmt:message key="edit" bundle="${locale}"/>
                            </a>
                            <a class="btn btn-danger" href="delete_user?id=${requestScope.user.id}" role="button">
                                <fmt:message key="delete" bundle="${locale}"/>
                            </a>
                        </div>
                    </div>

                </c:if>

                <div class="container well-sm">
                    <div class="panel panel-default">
                        <div class="panel-body">
                            <div class="text-center">
                                <h3>${requestScope.user.name} ${requestScope.user.surname}
                                    <c:if test="${sessionScope.id eq requestScope.user.id or sessionScope.admin}">
                                        <small> #${requestScope.user.id}</small>
                                    </c:if>
                                </h3>
                            </div>
                            <dl class="dl-horizontal">
                                <dt><fmt:message key="email" bundle="${locale}" /></dt>
                                <dd>${requestScope.user.email}</dd>
                            </dl>
                        </div>
                    </div>
                </div>

            </div>
        </c:when>
        <c:otherwise>
        <div class="container jumbotron well-sm text-center" style="max-width: 500px">
            <h3>NO SUCH USER</h3>
        </div>
        </c:otherwise>
    </c:choose>


    <div id="push"></div>
</div>

<div id="footer">
    <div class="container">
        <p class="muted credit text-center">footer ёpta</p>
    </div>
</div>

<script src="view/js/jQuery.js"></script>
<script src="view/js/bootstrap.min.js"></script>

</body>
</html>