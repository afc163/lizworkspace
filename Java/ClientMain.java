import java.net.*;
import java.io.*;

class Client extends Thread {
	static final int CPORT = 8000;
	Socket clientSocket;
	BufferedReader in;
	PrintWriter out;
	BufferedReader userin;
	
	public Client()
	{
		try
		{
			clientSocket = new Socket(InetAddress.getLocalHost(), CPORT);
			System.out.println("clientSocket: " + clientSocket);
			
			in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream(), "GB2312"));
			out = new PrintWriter(clientSocket.getOutputStream(), true);
			
			userin = new BufferedReader(new InputStreamReader(System.in));
		}
		catch (Exception e)
		{
			System.out.println("Client Exception: " + e);
		}
	}
	
	public void run()
	{
		try
		{
			while (true)
			{
				String content = userin.readLine();//读用户输入的字符串
				
				
				if (content.equalsIgnoreCase("end"))
				{
Message oneMessage = new Message(Message.END, content);
				SendMessage(oneMessage, out);             //将字符串传给服务器端
					break;
				}
Message oneMessage = new Message(Message.MSG, content);
				SendMessage(oneMessage, out);             //将字符串传给服务器端
				//str = in.readLine();           //获得服务器的字符串
				//System.out.println("form server: " + str);
				//if (str.equalsIgnoreCase("end"))
				//{
				//	break;
				//}
			}
			in.close();
			out.close();
			userin.close();
			clientSocket.close();
		}
		catch (Exception e)
		{
			System.out.println("异常：" + e);
		}		
	}
	
	public boolean SendMessage(Message msg, PrintWriter out)
	{
		try
		{
			out.println(msg);
			return true;
		}
		catch(Exception e)
		{
			return false;
		}
	}
}