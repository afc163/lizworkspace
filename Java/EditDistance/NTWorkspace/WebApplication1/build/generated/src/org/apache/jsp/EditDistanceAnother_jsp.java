package org.apache.jsp;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.jsp.*;

public final class EditDistanceAnother_jsp extends org.apache.jasper.runtime.HttpJspBase
    implements org.apache.jasper.runtime.JspSourceDependent {

  private static java.util.List _jspx_dependants;

  public Object getDependants() {
    return _jspx_dependants;
  }

  public void _jspService(HttpServletRequest request, HttpServletResponse response)
        throws java.io.IOException, ServletException {

    JspFactory _jspxFactory = null;
    PageContext pageContext = null;
    HttpSession session = null;
    ServletContext application = null;
    ServletConfig config = null;
    JspWriter out = null;
    Object page = this;
    JspWriter _jspx_out = null;
    PageContext _jspx_page_context = null;


    try {
      _jspxFactory = JspFactory.getDefaultFactory();
      response.setContentType("text/html; charset=gb2312");
      pageContext = _jspxFactory.getPageContext(this, request, response,
      			null, true, 8192, true);
      _jspx_page_context = pageContext;
      application = pageContext.getServletContext();
      config = pageContext.getServletConfig();
      session = pageContext.getSession();
      out = pageContext.getOut();
      _jspx_out = out;

      out.write("\n");
      out.write("<!DOCTYPE HTML PUBLIC \"-//W3C//DTD HTML 4.01 Transitional//EN\"\n");
      out.write("   \"http://www.w3.org/TR/html4/loose.dtdlocation.href='EditDistanceAnother.jsp?stringFrom='+sFrom +'&stringTo='+sTo;\">\n");
      out.write("<html>\n");
      out.write("    <head>\n");
      out.write("        <meta http-equiv=\"Content-Type\" content=\"text/html; charset=gb2312\">\n");
      out.write("        <title>JSP Page</title>\n");
      out.write("        <SCRIPT language=\"JavaScript\" TYPE=\"text/JavaScrpit\">\n");
      out.write("            function Do()\n");
      out.write("            {\n");
      out.write("                var sFrom = document.StringForm.stringFrom.value;\n");
      out.write("                var sTo = document.StringForm.stringTo.value;\n");
      out.write("                redirect(EditDistanceAnother.jsp?stringFrom=sFrom&stringTo=sTo);\n");
      out.write("                \n");
      out.write("            }         \n");
      out.write("        </SCRIPT>\n");
      out.write("    </head>\n");
      out.write("    \n");
      out.write("    ");
 
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
    
      out.write("\n");
      out.write("    <body>\n");
      out.write("               \n");
      out.write("    <h1>Levenshtein Distance Another Way</h1>\n");
      out.write("        <form name=\"StringForm\" method=\"POST\">\n");
      out.write("                        <p align=\"center\">&nbsp;&nbsp;&nbsp;&nbsp;Input One String: <input type=\"text\" name=\"stringFrom\"/>\n");
      out.write("                                  ");
      out.print( (sFrom == null)?"sunny":sFrom );
      out.write("</p>\n");
      out.write("                        <p align=\"center\">Input Another String: <input type=\"text\" name=\"stringTo\"/>\n");
      out.write("                                  ");
      out.print( (sTo == null)?"rainny":sTo );
      out.write("</p> \n");
      out.write("                        <p align=\"center\"><input type=\"submit\" value=\"Go\" name=\"computeButton\" onclick=\"Do();\" /></p>\n");
      out.write("                        <p align=\"center\"><textarea name=\"result\" rows=\"10\" cols=\"100\" readonly=\"readonly\" >");
      out.print(view);
      out.write("</textarea></p>\n");
      out.write("        </form>\n");
      out.write("    </body>\n");
      out.write("</html>\n");
    } catch (Throwable t) {
      if (!(t instanceof SkipPageException)){
        out = _jspx_out;
        if (out != null && out.getBufferSize() != 0)
          out.clearBuffer();
        if (_jspx_page_context != null) _jspx_page_context.handlePageException(t);
      }
    } finally {
      if (_jspxFactory != null) _jspxFactory.releasePageContext(_jspx_page_context);
    }
  }
}
