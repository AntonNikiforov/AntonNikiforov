<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title></title>
</head>
<body>

<c:if test="${not empty requestScope.msg}">
    <div class="container alert alert-danger alert-dismissible" role="alert" style="max-width: 500px">
        <button type="button" class="close" data-dismiss="alert"><span aria-hidden="true">&times;</span><span class="sr-only">Close</span></button>
        <strong>Warning!</strong> ${requestScope.msg}
    </div>
</c:if>

</body>
</html>
