
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%@ taglib prefix="library" uri="/WEB-INF/tld/taglib.tld" %>

<!DOCTYPE html>
<html>
<head>

    <meta charset="utf-8">
    <title>Search</title>

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

    <div class="container jumbotron well-lg" style="max-width: 500px">

        <c:if test="${sessionScope.admin}">
            <div class="form-group">
                <form action="add_${requestScope.type}" method="post"
                      class="form-horizontal " role="form" lang="en">

                    <button type="submit" class="btn btn-default btn-group-justified">
                        <fmt:message key="add" bundle="${locale}"/>
                    </button>
                </form>
            </div>
        </c:if>

        <library:searchResult var="${requestScope.list}"/>

        <library:pagination numAtAll="${requestScope.num}"/>


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