
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Error</title>
</head>
<body>

    <h1><a href="index.jsp">HOME</a></h1>
    <p>error msg: ${requestScope.error_msg}</p>
    <p>page name: ${requestScope.page_name}</p>
</body>
</html>
