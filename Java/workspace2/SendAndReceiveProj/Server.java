import java.net.*;
import java.io.*;

import org.eclipse.swt.widgets.Display;

//多线程服务器
class Server extends Thread 
{
	static final int SPORT = 8000;
	ServerSocket serverSocket;
	private boolean stopSign = true;
	
	//感觉这边应该把各个服务线程的情况也记录下来，就表示当前有几个客户端连上这个服务器端了
	
	public Server()
	{
		try
		{
			serverSocket = new ServerSocket(SPORT);//创建当前服务器线程的监听对象
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
			while (stopSign)  //当程序结束时应该停止此线程
			{
				Socket socket = serverSocket.accept();//接受一个客户端请求
				
				new CreateServerThread(socket);       //产生一个处理客户端请求的服务线程
				//createOneDialog(socket);
				//接收消息产生MDialog对话框
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
		//接收消息产生MDialog对话框
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
					case Message.LINK:			//--------接受的消息为  1 用户名，表连接
						String hostName = socket.getInetAddress().getHostName();
						User oneUser = new User(oneMsg.content, hostName, socket.getInetAddress().getAddress());
						//若已存在则需查找
						//待做   用户的名字改变则要检测并修改
						if (DetectHost.exisitUser.contains(oneUser))
						{
						}
						else                   //---------不存在则加入
						{
							DetectHost.exisitUser.addElement(oneUser);
						}
						exitFlag = false;
						break;
					case Message.MSG:			//--------接受的消息为  2 消息内容，一般消息

						System.out.println("from Client:" + socket + oneMsg.content);
						break;
					case Message.END:			//--------接受的消息为  3 用户名，表结束对话
						Message oneMessage = new Message(Message.END, "end");
						SendMessage(oneMessage);
						exitFlag = false;
						break;
					default:					//--------其他类型的信息丢弃
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
