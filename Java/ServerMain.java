import java.net.*;
import java.io.*;

//���̷߳�����
class Server extends Thread 
{
	
	static final int SPORT = 8000;
	ServerSocket serverSocket;
	
	public Server()
	{
		try
		{
			serverSocket = new ServerSocket(SPORT);//������ǰ�������̵߳ļ�������
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
				Socket socket = serverSocket.accept();//����һ���ͻ�������
				new CreateServerThread(socket);       //����һ������ͻ�������ķ����߳�
			}
		}
		catch (Exception e)
		{
			System.out.println("�쳣��" + e);
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
				
				start();//����
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
					String str = in.readLine();//���ͻ��˴��͵��ַ���
					System.out.println("form client: " +socket + "--->" + str);
					if (str.equalsIgnoreCase("end"))
					{
						break;
					}
					//str = userin.readLine();
					out.println("�յ�" + str);//��ͻ��˷�����Ϣ
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
				System.out.println("�����߳��� " + e);
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