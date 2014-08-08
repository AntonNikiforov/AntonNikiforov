
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title></title>
</head>
<body>

<fmt:setLocale value="${sessionScope.locale}"/>
<fmt:setBundle basename="locale" var="locale"/>

<nav class="navbar navbar-default" role="navigation">
    <div class="container-fluid container">
        <!-- Brand and toggle get grouped for better mobile display -->
        <div class="navbar-header">
            <button type="button" class="navbar-toggle" data-toggle="collapse" data-target="#bs-example-navbar-collapse-1">
                <span class="sr-only">Toggle navigation</span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
            </button>
            <a class="navbar-brand" href="start"><fmt:message key="library" bundle="${locale}"/></a>
        </div>

        <div class="collapse navbar-collapse" id="bs-example-navbar-collapse-1">
<c:choose>
    <c:when test="${not empty sessionScope.id}">
            <ul class="nav navbar-nav">
                <li><a href="books">
                    <fmt:message key="books" bundle="${locale}"/>
                </a></li>
                <li><a href="users">
                    <fmt:message key="users" bundle="${locale}"/>
                </a></li>
                <li><a href="bookings">
                    <fmt:message key="bookings" bundle="${locale}"/>
                </a></li>

            </ul>

            <form action="search" method="post"
                    class="navbar-form navbar-left" role="search">
                <div class="form-group">

                    <c:set var="search">
                        <fmt:message key="search" bundle="${locale}"/>
                    </c:set>
                    <input name="q" value="${requestScope.q}" type="text" class="form-control" placeholder="${search}">
                </div>
                <!--<button type="submit" class="btn btn-default"><fmt:message key="search" bundle="${locale}" /></button>-->
            </form>

            <ul class="nav navbar-nav navbar-right">
                <li><a href="user">
                    <fmt:message key="profile" bundle="${locale}"/>
                </a></li>
                <li><a href="sign_out">
                    <fmt:message key="sign_out" bundle="${locale}"/>
                </a></li>
            </ul>
    </c:when>
    <c:otherwise>
            <ul class="nav navbar-nav navbar-right">
                <li class="dropdown">
                    <a href="#" class="dropdown-toggle" data-toggle="dropdown">
                        <fmt:message key="language" bundle="${locale}" /> <span class="caret"/>
                    </a>
                    <ul class="dropdown-menu" role="menu">
                        <li>
                            <a href="switch_lang?locale=en">English</a>
                        </li>
                        <li>
                            <a href="switch_lang?&locale=ru">Русский</a>
                        </li>

                    </ul>
                </li>
            </ul>
    </c:otherwise>
</c:choose>



        </div>
    </div>
</nav>

</body>
</html>
