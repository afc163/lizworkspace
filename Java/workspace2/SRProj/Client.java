import java.net.*;
import java.io.*;

public class Client extends Thread {
	static final int CPort = 5656;
	String hostName;
	String message;
	
	public Client(String hName)
	{
		hostName = hName;
	}
	public void SetMessage(String msg)
	{
		message = msg;
	}
	
	public void run()
	{
		try
		{
			Socket socket = new Socket(InetAddress.getByName(hostName), CPort);
			OutputStream fOut = socket.getOutputStream();
			
			PrintStream out = new PrintStream(fOut);
			out.println(message);
			socket.close();
		}
		catch (Exception e)
		{
			System.out.println("“Ï≥££∫" + e);
		}		
	}
}