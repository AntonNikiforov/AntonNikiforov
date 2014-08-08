
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html>
<head>

    <meta charset="utf-8">
    <title>Book</title>

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

    <div class="container well-sm" style="max-width: 500px">
        <c:if test="${sessionScope.admin}">
            <div class="form-group">
                <div class="pull-left">

                    <c:choose>
                        <c:when test="${empty requestScope.booking.dateOfReturn}">
                            <a class="btn btn-default" href="close_booking?id=${requestScope.booking.id}" role="button">
                                <fmt:message key="close" bundle="${locale}"/>
                            </a>
                        </c:when>
                        <c:otherwise>
                            <a class="btn btn-default disabled" href="#" role="button">
                                <fmt:message key="close" bundle="${locale}"/>
                            </a>
                        </c:otherwise>
                    </c:choose>

                </div>
                <div class="btn-group pull-right">
                    <a class="btn btn-warning" href="edit_booking?id=${requestScope.booking.id}" role="button">
                        <fmt:message key="edit" bundle="${locale}"/>
                    </a>
                    <a class="btn btn-danger" href="delete_booking?id=${requestScope.booking.id}" role="button">
                        <fmt:message key="delete" bundle="${locale}"/>
                    </a>
                </div>
            </div>
        </c:if>
    </div>

    <div class="container jumbotron well-lg" style="max-width: 500px">


        <div>
            <dl class="dl-horizontal text-capitalize">
                <dt>#</dt>
                <dd>${requestScope.booking.id}</dd>
                <dt><fmt:message key="user" bundle="${locale}"/></dt>
                <dd>${requestScope.booking.user}</dd>
                <dt><fmt:message key="book" bundle="${locale}"/></dt>
                <dd>${requestScope.booking.book}</dd>
                <dt><fmt:message key="date_of_issue" bundle="${locale}"/></dt>
                <dd>${requestScope.booking.dateOfIssue}</dd>
                <dt><fmt:message key="date_of_return" bundle="${locale}"/></dt>
                <dd>${requestScope.booking.dateOfReturn}</dd>
                <dt><fmt:message key="type" bundle="${locale}"/></dt>
                <dd>${requestScope.booking.type.name}</dd>
            </dl>
        </div>

    </div>

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