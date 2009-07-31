import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;

public class HelloWWW extends HttpServlet 
{
    //Initialize global variables
	public void init(ServletConfig config) throws ServletException 
	{
		super.init(config);
	}
    //Process the HTTP Get request
	public void doGet(HttpServletRequest request, HttpServletResponse response)	throws ServletException, IOException 
	{
		response.setContentType("text/html;charset=GB2312");
		PrintWriter out = response.getWriter();
		out.println("<html>");
		out.println("<head><title>CH2 - HelloServlet</title></head>");
		out.println("<body>");
		out.println(" Hello World <br>");
		out.println("´ó¼ÒºÃ");
		out.println("</body>");
		out.println("</html>");
		out.close();
	}
    //Get Servlet information
	public String getServletInfo() 
	{
		return "tw.com.javaworld.CH2.HelloSerlvet Information";
	}
}