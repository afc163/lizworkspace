<%@ page contentType="text/html; charset=gb2312" language="java" %>
<%@ page import="java.sql.*" %>
<jsp:useBean id="JDBCCon" class="jdbccon.JDBCCon" scope="page" />
<%! //�������ݿ����Ӷ���dbCon
    Connection dbCon;
 %>
 <!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd" >
 <html>
   <head>
     <meta http-equiv="Content-Type" content="text/html; charset=gb2312" >
     <title>JDBC�������ݿ�ʵ��</title>    
   </head>
   <body>
     <h1 align="center">JDBC�������ݿ�ʵ��</h1>
     <% 
     //��ȡ���ݿ����Ӷ���
     dbCon = JDBCCon.getConn();
     if (dbCon != null)
     {
         out.println("<p align=\"center\"><font color=blue>���ݿ����ӳɹ�</font></p>");
     }
     else
     {
        out.println("<p align=\"center\"><font color=red>���ݿ�����ʧ�ܲ��ɹ�</font></p>");
        out.println(JDBCCon.getErr());
     }
     %>
   </body>
 </html>