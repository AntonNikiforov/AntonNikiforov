
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html>
<head>

    <meta charset="utf-8">
    <title>Main Page</title>

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

    <div class="jumbotron container">
        <center>

            <c:choose>
                <c:when test="${empty sessionScope.id}">

                    <h2>KEEP</br>CALM</br><small>AND</small></br>READ</br>ON</h2>

                    <div class="btn-toolbar" style="margin-top: 50px;">
                        <a href="sign_in" class="btn btn-primary btn-lg" role="button">
                            <fmt:message key="sign_in" bundle="${locale}" />
                        </a>
                        <a href="sign_up" class="btn btn-primary btn-lg" role="button">
                            <fmt:message key="sign_up" bundle="${locale}" />
                        </a>
                    </div>

                </c:when>
                <c:otherwise>

                    <h1><fmt:message key="greetings" bundle="${locale}" /></h1>

                    <h2>${requestScope.user.name} ${requestScope.user.surname}</h2>

                    <form action="sign_out" method="post"
                          class="form-horizontal" role="form" lang="en">
                        <button type="submit" class="btn btn-default">
                            <fmt:message key="sign_out" bundle="${locale}" />
                        </button>
                    </form>

                </c:otherwise>
            </c:choose>
        </center>
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