import java.net.*;
import java.io.*;

//多线程服务器
class Server extends Thread 
{
	
	static final int SPORT = 8000;
	ServerSocket serverSocket;
	
	public Server()
	{
		try
		{
			serverSocket = new ServerSocket(SPORT);//创建当前服务器线程的监听对象
			System.out.println("Started : Server" + serverSocket);
		}
		catch (Exception e)
		{
			System.out.println("Server Started Exception! Cause: " + e);
		}
	}
	
	public void run()
	{
		try
		{
			while (true)
			{
				Socket socket = serverSocket.accept();//接受一个客户端请求
				new CreateServerThread(socket);       //产生一个处理客户端请求的服务线程
			}
		}
		catch (Exception e)
		{
			System.out.println("异常：" + e);
		}
		finally
		{
			try
			{
				serverSocket.close();
			}
			catch (Exception e)
			{
				System.out.println(e);
			}			
		}
	}

	class CreateServerThread extends Thread
	{
		Socket socket;
		BufferedReader in;
		PrintWriter out;
		public CreateServerThread(Socket skt)
		{
			socket = skt;
			System.out.println("Socket in CreateServerThread: " + socket);
			try
			{
				in = new BufferedReader(new InputStreamReader(socket.getInputStream(), "GB2312"));
				out = new PrintWriter(socket.getOutputStream(), true);
				
				start();//运行
			}
			catch (Exception e)
			{
				
			}
		}
		
		public void run()
		{
			try
			{
				while (true)
				{				
					String str = in.readLine();//读客户端传送的字符串
					System.out.println("form client: " +socket + "--->" + str);
					if (str.equalsIgnoreCase("end"))
					{
						break;
					}
					//str = userin.readLine();
					out.println("收到" + str);//向客户端发送消息
					//if (str.equalsIgnoreCase("end"))
					//{
					//	break;
					//}
				}
				in.close();
				out.close();
				socket.close();
			}
			catch (Exception e)
			{
				System.out.println("服务线程中 " + e);
			}
		}
	}
}

public class ServerMain
{

	public static void main(String[] args) {
		Server myServer = new Server();
		myServer.start();
	}
}