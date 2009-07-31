import java.net.*;
import java.io.*;
 
public class Server
{
	private static final int SPORT = 8000;
	private ServerSocket serverSocket;
	private Socket socket;
	private BufferedReader in;
	private PrintWriter out;
	
	public Server()
	{
		try
		{
			serverSocket = new ServerSocket(SPORT);
			//while (true)
			//{
				socket = serverSocket.accept();
				in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
				out = new PrintWriter(socket.getOutputStream(),true);
				String line = in.readLine();
				out.println("Form Server--->you input is :" + line);
				out.close();
				in.close();
				socket.close();
				//if (line.equalsIgnoreCase("end"))
				//{
				//	break;
				//}
			//}
			serverSocket.close();
		}
		catch (IOException e)
		{	
			System.out.println("Server Exception : " + e);
		}
	}
	
	public static void main(String[] args)
	{
		new Server();
	}
}