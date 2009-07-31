<%@page contentType="text/html; charset=gb2312" language="java"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
   "http://www.w3.org/TR/html4/loose.dtdlocation.href='EditDistanceAnother.jsp?stringFrom='+sFrom +'&stringTo='+sTo;">
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=gb2312">
        <title>JSP Page</title>
        <SCRIPT language="JavaScript" TYPE="text/JavaScrpit">
            function Do()
            {
                var sFrom = document.StringForm.stringFrom.value;
                var sTo = document.StringForm.stringTo.value;
                redirect(EditDistanceAnother.jsp?stringFrom=sFrom&stringTo=sTo);
                
            }         
        </SCRIPT>
    </head>
    
    <% 
       request.setCharacterEncoding("gb2312");
       String sFrom = request.getParameter("stringFrom");
       String sTo = request.getParameter("stringTo");
       String view = "";
       if (sFrom != null && sTo != null)
       {
           editdistance.EditDistance editDistance = new editdistance.EditDistance(sFrom, sTo);
           //editDistance.SetString(sFrom, sTo);
           editDistance.LevenshteinDistance();
           int rowSize = editDistance.GetsFromSize() + 1;
           int colSize = editDistance.GetsToSize() + 1;
           
           for (int i = 0; i < rowSize; i++)
           {                        
                   for (int j = 0; j< colSize; j++)
                   { 
                           view += "......" + String.valueOf(editDistance.Get(i, j));
                   }
                           view = view + "\n";
           }
       }
       else
       {
           view = "Please Input";
        }
    %>
    <body>
               
    <h1>Levenshtein Distance Another Way</h1>
        <form name="StringForm" method="POST">
                        <p align="center">&nbsp;&nbsp;&nbsp;&nbsp;Input One String: <input type="text" name="stringFrom"/>
                                  <%= (sFrom == null)?"sunny":sFrom %></p>
                        <p align="center">Input Another String: <input type="text" name="stringTo"/>
                                  <%= (sTo == null)?"rainny":sTo %></p> 
                        <p align="center"><input type="submit" value="Go" name="computeButton" onclick="Do();" /></p>
                        <p align="center"><textarea name="result" rows="10" cols="100" readonly="readonly" ><%=view%></textarea></p>
        </form>
    </body>
</html>
