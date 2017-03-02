<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>My Guest book</title>
</head>
<body>
  <h1>Welcome to my guestbook !!!</h1>
  <form action="/guestbook" method="post">
    <div>
        <h2>Leave your message</h2>
        <div>
          <label for="name">Name:</label>
          <input type="text" id="name" name="name" placeholder="Enter your name">
        </div>
        <div>
          <div>
            <label form="message">Message</label>
          </div>
          <textarea id="message" name="message" placeholder="Enter your message"></textarea>
        </div>
        <div>
          <label for="rating">Rating</label>
          <select id="rating" name="rating">
            <option selected>1</option>
            <option>2</option>
            <option>3</option>
            <option>4</option>
            <option>5</option>
          </select>
        </div>
        <div>
          <input type="submit" value="Send">
        </div>
    </div>
  </form>
  <div>
    <h2>Messages</h2>
    <c:forEach var="message" items="${messages}">
          <dl>
            <dt>${message.name} <a href="/guestbook?action=del&id=${message.id}">Delete</a></dt>
            <dd>${message.message}</dd>
              <jsp:useBean id="dateValue" class="java.util.Date"/>
              <jsp:setProperty name="dateValue" property="time" value="${message.date}"/>
              <dd>${message.rating} <fmt:formatDate value="${dateValue}" pattern="MM/dd/yyyy HH:mm"/></dd>
          </dl>
    </c:forEach>
  </div>
</body>
</html>
