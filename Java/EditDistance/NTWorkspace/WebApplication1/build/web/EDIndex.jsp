<%@page contentType="text/html; charset=gb2312" language="java"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
   "http://www.w3.org/TR/html4/loose.dtd">
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=gb2312">
        <title>Liz's JSP Page</title>
        <script language="JavaScript" type="text/JavaScript">
          function red_sub()
            {
              var sTo,sFrom;
              sTo=document.bd.stringTo.value;
              sFrom=document.bd.stringFrom.value;
              location.href='EDIndex.jsp?stringTo='+sTo+'&stringFrom='+sFrom;
            }
        </script>
    </head>
    <% 
       request.setCharacterEncoding("gb2312");
       String sFrom = request.getParameter("stringFrom");
       String sTo = request.getParameter("stringTo");
    %>
    <body>
      <form name="bd">
            <h2>The result</h2>
            
            <table width="40%" border="1">
                        <tr>
                            <td height="30" align="left">
                               <textarea name="resultArea" rows="20" cols="120">
                               <%if((sFrom == null) || (sTo == null))
                               {%>
                               <%="!"%>
                               <%}
                               else { 
                                     editdistance.EditDistance editDistance = new editdistance.EditDistance(sFrom, sTo);
                                     int rowSize = editDistance.GetsFromSize() + 1;
                                     int colSize = editDistance.GetsToSize() + 1;
                                     editDistance.LevenshteinDistance();
                                     
                                     String view = "";
                                     for (int i = 0; i < rowSize; i++)
                                     {                        
                                         for (int j = 0; j< colSize; j++)
                                         { 
                                             view += "......" + String.valueOf(editDistance.Get(i, j));
                                         }
                                         view = view + "\n";
                                     }
                                     view += "\n" + "The Result is: " + String.valueOf(editDistance.Get(rowSize-1, colSize-1));
                                     out.print(view);
                                }%>
                               </textarea>
                            </td>
                        </tr>
                        <tr>
                            <td><input type="text" name="stringFrom" value="sunny" /></td>
                        </tr>
                        <tr>
                            <td><input type="text" name="stringTo" value="rainny" /></td>
                        </tr>
            </table>
            <input type="button" value="compute" name="OK" onClick="red_sub();"/> 
       </form>
    </body>
</html>
