import java.io.*;
import java.net.*;

public class Client
{
	static final int CPORT = 8000;
	Socket socket;
	BufferedReader in;
	PrintWriter out;
	public Client()
	{
		try
		{
			socket = new Socket(InetAddress.getLocalHost(), 8000);
			in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			out = new PrintWriter(socket.getOutputStream(),true);
			BufferedReader line = new BufferedReader(new InputStreamReader(System.in));
			out.println(line.readLine());
			line.close();
			out.close();
			in.close();
			socket.close();
		}
		catch (IOException e)
		{
			System.out.println("Client Exception: " + e);
		}
	}
	
	public static void main(String[] args)
	{
		new Client();
	}
}