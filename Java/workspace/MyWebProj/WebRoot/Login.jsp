<%@ page contentType = "text/html; charset = gb2312" language = "java" %>
<jsp:useBean id ="login" class ="login.Login" scope = "page" />
<jsp:setProperty name = "login" property = "userName" param = "userName" />
<jsp:setProperty name = "login" property = "password" param = "password" />
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
   <head>
      <meta http-equiv = "Content-Type" content = "text/html; charset = gb2312">
      <title>JavaBean的应用实例JSP</title>
   </head>
   <body>
      <div align = "center">
      <h2>用户登录信息如下：</h2>
      <p>用户名：<font color = "#0000FF"><%= login.getUserName() %></font></p>
      <p>密码：<font color = "#00FF00"><%= login.getPassword() %></font></p>
      </div>
   </body>
</html>