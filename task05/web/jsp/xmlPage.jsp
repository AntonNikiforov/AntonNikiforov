<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<html>
<head>
    <title>XML Page</title>
</head>
<body>
    <p>command: <%= request.getParameter("command") %></p>
    <p>file: <%= request.getParameter("file") %></p>
    <p>parser: <%= request.getParameter("parser") %></p>
    <center><table>
        <caption>Old Cards</caption>
        <tr>
            <th>Theme</th>
            <th>Type</th>
            <th>Country</th>
            <th>Year</th>
            <th>Author</th>
            <th>Valuable</th>
            <th>Sent</th>
        </tr>
        <c:forEach var="card" items="${cards}">
            <tr>
                <td>${card.theme}</td>
                <td>${card.type}</td>
                <td>${card.country}</td>
                <td>${card.year}</td>
                <td>${card.author}</td>
                <td>${card.valuable}</td>
                <td>${card.sent}</td>
            </tr>
        </c:forEach>
    </table></center>
    <!--<p>cards: <%= request.getAttribute("cards") %></p>-->
</body>
</html>