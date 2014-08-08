
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
                    <a class="btn btn-default" href="add_booking?book_id=${requestScope.book.id}" role="button">Новый заказ</a>
                    <a class="btn btn-default" href="#" role="button">История заказов</a>
                </div>
                <div class="btn-group pull-right">
                    <a class="btn btn-warning" href="edit_book?id=${requestScope.book.id}" role="button">
                        <fmt:message key="edit" bundle="${locale}"/>
                    </a>
                    <a class="btn btn-danger" href="delete_book?id=${requestScope.book.id}" role="button">
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
                <dd>${requestScope.book.id}</dd>
                <dt><fmt:message key="book_name" bundle="${locale}"/></dt>
                <dd>${requestScope.book.name}</dd>
                <dt><fmt:message key="author" bundle="${locale}"/></dt>
                <dd>${requestScope.book.author}</dd>
                <dt><fmt:message key="year" bundle="${locale}"/></dt>
                <dd>${requestScope.book.year}</dd>
                <dt><fmt:message key="genre" bundle="${locale}"/></dt>
                <dd>${requestScope.book.genre.name}</dd>
                <dt><fmt:message key="num" bundle="${locale}"/></dt>
                <dd>${requestScope.book.numInLibNow}/${requestScope.book.numInLibAll}</dd>
            </dl>
        </div>

        <c:forEach var="comment" items="${comments}">
            <div>
                <dl class="dl-horizontal text-capitalize">
                    <dt>${comment.user.name} ${comment.user.surname}</dt>
                    <dd>${comment.date}</dd>
                    <dt></dt>
                    <dd>${comment.text}</dd>
                </dl>
            </div>
        </c:forEach>

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