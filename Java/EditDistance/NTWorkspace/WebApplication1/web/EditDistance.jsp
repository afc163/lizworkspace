<%@page contentType="text/html; charset=gb2312" language="java"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
   "http://www.w3.org/TR/html4/loose.dtd">
   <jsp:useBean id="editDistance" scope="page" class="editdistance.EditDistance" />
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=gb2312">
        <title>Liz's JSP Page</title>
    </head>
    <% 
       request.setCharacterEncoding("gb2312");
       
       String sFrom = request.getParameter("stringFrom");
       String sTo = request.getParameter("stringTo");
       editDistance.SetString(sFrom, sTo);
       editDistance.LevenshteinDistance();
    %>
    <body>
            <h2>The result</h2>
            <table width="40%" border="1">
                <tbody>
                   <% 
                    int rowSize = editDistance.GetsFromSize() + 1;
                    int colSize = editDistance.GetsToSize() + 1;
                    
                    for (int i = 0; i < rowSize; i++)
                    {
                   %>
                        <tr>
                        <%                         
                        for (int j = 0; j< colSize; j++)
                        {
                            //int oneElement = editDistance.Get(i, j);
                        %>
                            <td height="30" align="center">
                                <%= editDistance.Get(i, j) %>
                            </td>
                        <%
                        }
                        %>
                    </tr>
                <%
                    }
                %>    
                </tbody>              
            </table>
            <p> EditDistance is : <%=editDistance.Get(rowSize-1, colSize-1) %> </p>          
    <%--
    This example uses JSTL, uncomment the taglib directive above.
    To test, display the page like this: index.jsp?sayHello=true&name=Murphy
    --%>
    <%--
    <c:if test="${param.sayHello}">
        <!-- Let's welcome the user ${param.name} -->
        Hello ${param.name}!
    </c:if>
    --%>

    </body>
</html>
