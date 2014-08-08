
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Settings</title>

    <!---->
    <link href="../../view/css/bootstrap.min.css" rel="stylesheet">
    <link href="../../view/css/bootstrap-theme.min.css" rel="stylesheet">
    <link href="../../view/css/footer.css" rel="stylesheet">

</head>
<body>

<fmt:setLocale value="${sessionScope.locale}"/>
<fmt:setBundle basename="locale" var="locale"/>

<div id="wrap">

    <%@include file="../../header.jsp"%>

    <div class="container jumbotron well-lg" style="max-width: 500px;">

        <form action="edit_user" method="post"
              class="form-horizontal" role="form" lang="en">

            <input type="hidden" name="id" value="${requestScope.user.id}">

            <div class="form-group">
                <label for="inputEmail" class="col-sm-2 control-label">
                    <fmt:message key="email" bundle="${locale}"/>
                </label>
                <div class="col-sm-10">
                    <input name="email" value=""
                           type="email" class="form-control" id="inputEmail" placeholder="${requestScope.user.email}">
                </div>
            </div>
            <div class="form-group">
                <label for="inputPassword" class="col-sm-2 control-label">
                    <fmt:message key="password" bundle="${locale}"/>
                </label>
                <div class="col-sm-10">
                    <input name="password"
                           type="password" class="form-control" id="inputPassword" placeholder="${requestScope.user.password}">
                </div>
            </div>
            <div class="form-group">
                <label for="inputName" class="col-sm-2 control-label">
                    <fmt:message key="user_name" bundle="${locale}"/>
                </label>
                <div class="col-sm-10">
                    <input name="name" value=""
                           type="text" class="form-control" id="inputName" placeholder="${requestScope.user.name}">
                </div>
            </div>
            <div class="form-group">
                <label for="inputSurname" class="col-sm-2 control-label">
                    <fmt:message key="surname" bundle="${locale}"/>
                </label>
                <div class="col-sm-10">
                    <input name="surname" value=""
                           type="text" class="form-control" id="inputSurname" placeholder="${requestScope.user.surname}">
                </div>
            </div>
            <c:if test="${sessionScope.admin}">
                <div class="form-group">
                    <label for="chooseRole" class="col-sm-2 control-label">
                        <fmt:message key="role" bundle="${locale}"/>
                    </label>
                    <div class="col-sm-10">
                        <select name="role_id" class="form-control" id="chooseRole" style="width: 200px">
                            <c:forEach var="role" items="${requestScope.role_list}">
                                <c:choose>
                                    <c:when test="${role.name.equals(requestScope.user.role.name)}">
                                        <option selected value="${role.id}">${role.name}</option>
                                    </c:when>
                                    <c:otherwise>
                                        <option value="${role.id}">${role.name}</option>
                                    </c:otherwise>
                                </c:choose>
                            </c:forEach>
                        </select>
                    </div>
                </div>
            </c:if>
            <div class="form-group">
                <label for="chooseLang" class="col-sm-2 control-label"><fmt:message key="language" bundle="${locale}" /></label>
                <div class="col-sm-10">
                    <select name="lang_id" class="form-control" id="chooseLang" style="width: 200px">
                        <c:forEach var="lang" items="${requestScope.lang_list}">
                            <c:choose>
                                <c:when test="${lang.name.equals(requestScope.user.lang.name)}">
                                    <option selected value="${lang.id}">${lang.name}</option>
                                </c:when>
                                <c:otherwise>
                                    <option value="${lang.id}">${lang.name}</option>
                                </c:otherwise>
                            </c:choose>
                        </c:forEach>
                    </select>
                </div>
            </div>
            <div class="form-group">
                <div class="col-sm-offset-2 col-sm-10">
                    <button type="submit" class="btn btn-default">
                        <fmt:message key="change" bundle="${locale}" />
                    </button>
                </div>
            </div>
        </form>

    </div>

    <div id="push"></div>
</div>

<div id="footer">
    <div class="container">
        <p class="muted credit text-center">footer ёpta</p>
    </div>
</div>

<script src="../../view/js/jQuery.js"></script>
<script src="../../view/js/bootstrap.min.js"></script>
</body>
</html>