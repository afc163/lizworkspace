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
				String content = userin.readLine();//���û�������ַ���
				
				
				if (content.equalsIgnoreCase("end"))
				{
Message oneMessage = new Message(Message.END, content);
				SendMessage(oneMessage, out);             //���ַ���������������
					break;
				}
Message oneMessage = new Message(Message.MSG, content);
				SendMessage(oneMessage, out);             //���ַ���������������
				//str = in.readLine();           //��÷��������ַ���
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
			System.out.println("�쳣��" + e);
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