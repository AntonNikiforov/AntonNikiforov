
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Add Booking</title>

    <!---->
    <link href="view/css/bootstrap.min.css" rel="stylesheet">
    <link href="view/css/bootstrap-theme.min.css" rel="stylesheet">
    <link href="view/css/footer.css" rel="stylesheet">

</head>
<body>

<fmt:setLocale value="${sessionScope.locale}"/>
<fmt:setBundle basename="locale" var="locale"/>

<div id="wrap">

    <%@include file="header.jsp"%>

    <%@include file="message.jsp"%>

    <div class="container jumbotron well-lg" style="max-width: 500px;">
        <form action="add_booking" method="post"
              class="form-horizontal" role="form" lang="en">

            <div class="form-group">
                <label for="inputUserId" class="col-sm-2 control-label">
                    <fmt:message key="user_id" bundle="${locale}"/>
                </label>
                <div class="col-sm-10">
                    <input name="user_id" value="${requestScope.user_id}"
                           type="number" class="form-control" id="inputUserId" placeholder="">
                </div>
            </div>
            <div class="form-group">
                <label for="inputBookId" class="col-sm-2 control-label">
                    <fmt:message key="book_id" bundle="${locale}"/>
                </label>
                <div class="col-sm-10">
                    <input name="book_id" value="${requestScope.book_id}"
                           type="number" class="form-control" id="inputBookId" placeholder="">
                </div>
            </div>
            <div class="form-group">
                <label for="chooseBookingType" class="col-sm-2 control-label">
                    <fmt:message key="type" bundle="${locale}"/>
                </label>
                <div class="col-sm-10">
                    <select name="type_id" class="form-control" id="chooseBookingType" style="width: 200px">
                        <c:forEach var="type" items="${requestScope.type_list}">

                            <option value="${type.id}">${type.name}</option>

                        </c:forEach>
                    </select>
                </div>
            </div>

            <div class="form-group">
                <div class="col-sm-offset-2 col-sm-10">
                    <button type="submit" class="btn btn-default">
                        <fmt:message key="add" bundle="${locale}"/>
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

<script src="view/js/jQuery.js"></script>
<script src="view/js/bootstrap.min.js"></script>

</body>
</html>