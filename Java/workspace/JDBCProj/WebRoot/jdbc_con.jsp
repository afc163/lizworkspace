<%@ page contentType="text/html; charset=gb2312" language="java" %>
<%@ page import="java.sql.*" %>
<jsp:useBean id="JDBCCon" class="jdbccon.JDBCCon" scope="page" />
<%! //定义数据库连接对象dbCon
    Connection dbCon;
 %>
 <!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd" >
 <html>
   <head>
     <meta http-equiv="Content-Type" content="text/html; charset=gb2312" >
     <title>JDBC连接数据库实例</title>    
   </head>
   <body>
     <h1 align="center">JDBC连接数据库实例</h1>
     <% 
     //获取数据库连接对象
     dbCon = JDBCCon.getConn();
     if (dbCon != null)
     {
         out.println("<p align=\"center\"><font color=blue>数据库连接成功</font></p>");
     }
     else
     {
        out.println("<p align=\"center\"><font color=red>数据库连接失败不成功</font></p>");
        out.println(JDBCCon.getErr());
     }
     %>
   </body>
 </html>