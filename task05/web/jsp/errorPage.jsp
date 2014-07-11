<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Error Page</title>
</head>
<body>
    <p>command: <%= request.getParameter("command") %></p>
    <p>file: <%= request.getParameter("file") %></p>
    <p>parser: <%= request.getParameter("parser") %></p>
    <p>error: <%= request.getAttribute("error") %></p>
    <p>real path: <%= request.getAttribute("real") %></p>
</body>
</html>
