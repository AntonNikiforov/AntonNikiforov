<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
  <head>
      <title>WebXML</title>
  </head>
  <body>
      <form action="controller" method="post">
          <input type="hidden" name="command" value="parse">
          <!--<input type="file" accept="text/xml" name="file"/>-->
          <input type="hidden" name="file" value="cards.xml">
          <p><b>Какой парсер использовать?</b></p>
          <p>
              <input type="radio" name="parser" value="dom">DOM<Br>
              <input type="radio" name="parser" value="sax">SAX<Br>
              <input type="radio" name="parser" value="stax" checked>StAX
          </p>
          <input type="submit">
      </form>
  </body>
</html>
