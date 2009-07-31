import java.net.*;
import java.io.*;

import org.eclipse.swt.widgets.Display;

//���̷߳�����
class Server extends Thread 
{
	static final int SPORT = 8000;
	ServerSocket serverSocket;
	private boolean stopSign = true;
	
	//�о����Ӧ�ðѸ��������̵߳����Ҳ��¼�������ͱ�ʾ��ǰ�м����ͻ��������������������
	
	public Server()
	{
		try
		{
			serverSocket = new ServerSocket(SPORT);//������ǰ�������̵߳ļ�������
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
			while (stopSign)  //���������ʱӦ��ֹͣ���߳�
			{
				Socket socket = serverSocket.accept();//����һ���ͻ�������
				
				new CreateServerThread(socket);       //����һ������ͻ�������ķ����߳�
				//createOneDialog(socket);
				//������Ϣ����MDialog�Ի���
				//MDialog myDialog = new MDialog(Display.getCurrent().getActiveShell());
				//myDialog.SetUser(socket.getInetAddress().getHostName());
				//myDialog.setBlockOnOpen(false);
				//myDialog.open();
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
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
	
	public void setStopSign()
	{
		stopSign = false;
	}

	public void createOneDialog(Socket socket)
	{
		//������Ϣ����MDialog�Ի���
		MDialog myDialog = new MDialog(Display.getCurrent().getActiveShell());
		myDialog.SetUser(socket.getInetAddress().getHostName());
		//myDialog.setBlockOnOpen(false);
		myDialog.open();
	}
	
	class CreateServerThread extends Thread
	{
		Socket socket;
		BufferedReader in;
		PrintWriter out;
		
		public CreateServerThread(Socket skt)
		{
			socket = skt;
			try
			{
				in = new BufferedReader(new InputStreamReader(socket.getInputStream(), "GB2312"));
				out = new PrintWriter(socket.getOutputStream(), true);
				
				start();
			}
			catch (Exception e)
			{
				System.out.println("Exception in CreateServerThread: " + e);
			}
		}
		
		public void run()
		{
			try
			{
				boolean exitFlag = true;
				while (exitFlag)
				{	
					Message oneMsg = ReceiveMessage();
					switch (oneMsg.messageType)
					{
					case Message.LINK:			//--------���ܵ���ϢΪ  1 �û�����������
						String hostName = socket.getInetAddress().getHostName();
						User oneUser = new User(oneMsg.content, hostName, socket.getInetAddress().getAddress());
						//���Ѵ����������
						//����   �û������ָı���Ҫ��Ⲣ�޸�
						if (DetectHost.exisitUser.contains(oneUser))
						{
						}
						else                   //---------�����������
						{
							DetectHost.exisitUser.addElement(oneUser);
						}
						exitFlag = false;
						break;
					case Message.MSG:			//--------���ܵ���ϢΪ  2 ��Ϣ���ݣ�һ����Ϣ

						System.out.println("from Client:" + socket + oneMsg.content);
						break;
					case Message.END:			//--------���ܵ���ϢΪ  3 �û�����������Ի�
						Message oneMessage = new Message(Message.END, "end");
						SendMessage(oneMessage);
						exitFlag = false;
						break;
					default:					//--------�������͵���Ϣ����
					}
				}
				in.close();
				out.close();
				socket.close();
			}
			catch (Exception e)
			{
				System.out.println("Exception in CreateServerThread Running: " + e);
			}
		}
		
		public Message ReceiveMessage()
		{
			try
			{
				//int type = bf.read();
				//String msg = bf.readLine();
				String msg = in.readLine();
				int type = msg.charAt(0) - '0';
				String content = msg.substring(2);
				return new Message(type, content);
			}
			catch (IOException e)
			{
				System.out.println("Exception in ReceiveMessage: " + e);
			}
			return null;
		}
		
		public boolean SendMessage(Message msg)
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
}
