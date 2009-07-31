package org.apache.jsp;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.jsp.*;

public final class EDIndex_jsp extends org.apache.jasper.runtime.HttpJspBase
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
      out.write("\n");
      out.write("<!DOCTYPE HTML PUBLIC \"-//W3C//DTD HTML 4.01 Transitional//EN\"\n");
      out.write("   \"http://www.w3.org/TR/html4/loose.dtd\">\n");
      out.write("<html>\n");
      out.write("    <head>\n");
      out.write("        <meta http-equiv=\"Content-Type\" content=\"text/html; charset=gb2312\">\n");
      out.write("        <title>Liz's JSP Page</title>\n");
      out.write("        <script language=\"JavaScript\" type=\"text/JavaScript\">\n");
      out.write("          function red_sub()\n");
      out.write("            {\n");
      out.write("              var sTo,sFrom;\n");
      out.write("              sTo=document.bd.stringTo.value;\n");
      out.write("              sFrom=document.bd.stringFrom.value;\n");
      out.write("              location.href='EDIndex.jsp?stringTo='+sTo+'&stringFrom='+sFrom;\n");
      out.write("            }\n");
      out.write("        </script>\n");
      out.write("    </head>\n");
      out.write("    ");
 
       request.setCharacterEncoding("gb2312");
       String sFrom = request.getParameter("stringFrom");
       String sTo = request.getParameter("stringTo");
    
      out.write("\n");
      out.write("    <body>\n");
      out.write("      <form name=\"bd\">\n");
      out.write("            <h2>The result</h2>\n");
      out.write("            \n");
      out.write("            <table width=\"40%\" border=\"1\">\n");
      out.write("                        <tr>\n");
      out.write("                            <td height=\"30\" align=\"left\">\n");
      out.write("                               <textarea name=\"resultArea\" rows=\"20\" cols=\"120\">\n");
      out.write("                               ");
if((sFrom == null) || (sTo == null))
                               {
      out.write("\n");
      out.write("                               ");
      out.print("!");
      out.write("\n");
      out.write("                               ");
}
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
                                }
      out.write("\n");
      out.write("                               </textarea>\n");
      out.write("                            </td>\n");
      out.write("                        </tr>\n");
      out.write("                        <tr>\n");
      out.write("                            <td><input type=\"text\" name=\"stringFrom\" value=\"sunny\" /></td>\n");
      out.write("                        </tr>\n");
      out.write("                        <tr>\n");
      out.write("                            <td><input type=\"text\" name=\"stringTo\" value=\"rainny\" /></td>\n");
      out.write("                        </tr>\n");
      out.write("            </table>\n");
      out.write("            <input type=\"button\" value=\"compute\" name=\"OK\" onClick=\"red_sub();\"/> \n");
      out.write("       </form>\n");
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
