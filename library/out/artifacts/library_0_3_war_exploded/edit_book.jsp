
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Edit Book</title>

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

    <div class="container jumbotron well-lg" style="max-width: 500px;">
        <p>${requestScope.msg}</p>
        <form action="edit_book" method="post"
              class="form-horizontal" role="form" lang="en">

            <input type="hidden" name="id" value="${requestScope.book.id}">

            <div class="form-group">
                <label for="inputName" class="col-sm-2 control-label">
                    <fmt:message key="book_name" bundle="${locale}"/>
                </label>
                <div class="col-sm-10">
                    <input name="name" value=""
                           type="text" class="form-control" id="inputName" placeholder="${requestScope.book.name}">
                </div>
            </div>
            <div class="form-group">
                <label for="inputAuthor" class="col-sm-2 control-label">
                    <fmt:message key="author" bundle="${locale}"/>
                </label>
                <div class="col-sm-10">
                    <input name="author"
                           type="text" class="form-control" id="inputAuthor" placeholder="${requestScope.book.author}">
                </div>
            </div>
            <div class="form-group">
                <label for="inputYear" class="col-sm-2 control-label">
                    <fmt:message key="year" bundle="${locale}"/>
                </label>
                <div class="col-sm-10">
                    <input name="year" value=""
                           type="number" class="form-control" id="inputYear" placeholder="${requestScope.book.year}">
                </div>
            </div>
            <div class="form-group">
                <label for="inputNumInLibAll" class="col-sm-2 control-label">
                    Number in library all
                </label>
                <div class="col-sm-10">
                    <input name="num_in_lib_all" value=""
                           type="number" class="form-control" id="inputNumInLibAll" placeholder="${requestScope.book.numInLibAll}">
                </div>
            </div>
            <div class="form-group">
                <label for="inputNumInLibNow" class="col-sm-2 control-label">
                    Number in library now
                </label>
                <div class="col-sm-10">
                    <input name="num_in_lib_now" value=""
                           type="number" class="form-control" id="inputNumInLibNow" placeholder="${requestScope.book.numInLibNow}">
                </div>
            </div>
            <div class="form-group">
                <label for="chooseGenre" class="col-sm-2 control-label">
                    <fmt:message key="genre" bundle="${locale}"/>
                </label>
                <div class="col-sm-10">
                    <select name="genre_id" class="form-control" id="chooseGenre" style="width: 200px">
                        <c:forEach var="genre" items="${requestScope.genre_list}">
                            <c:choose>
                                <c:when test="${genre.id eq requestScope.book.genre.id}">
                                    <option selected value="${genre.id}">${genre.name}</option>
                                </c:when>
                                <c:otherwise>
                                    <option value="${genre.id}">${genre.name}</option>
                                </c:otherwise>
                            </c:choose>
                        </c:forEach>
                    </select>
                </div>
            </div>
            <div class="form-group">
                <div class="col-sm-offset-2 col-sm-10">
                    <button type="submit" class="btn btn-default">
                        <fmt:message key="edit" bundle="${locale}"/>
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