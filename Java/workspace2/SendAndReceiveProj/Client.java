import java.net.*;
import java.io.*;

class Client extends Thread {
	static final int CPORT = 8000;
	Socket clientSocket;
	PrintWriter out;
	BufferedReader in;
	
	public Client()
	{
		try
		{
			//LocalHost's Socket
			clientSocket = new Socket(InetAddress.getLocalHost(), CPORT);
			System.out.println("clientSocket: " + clientSocket);
			
			in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream(), "GB2312"));
			out = new PrintWriter(clientSocket.getOutputStream(), true);
		}
		catch (Exception e)
		{
			System.out.println("Client Construct Exception: " + e);
		}
	}
	
	public Client(String hostName)
	{
		try
		{
			//OtherHost's Socket
			InetAddress inetAddress = InetAddress.getByName(hostName);
			clientSocket = new Socket(inetAddress, CPORT);
			System.out.println("clientSocket: " + clientSocket);
			
			in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream(), "GB2312"));
			out = new PrintWriter(clientSocket.getOutputStream(), true);
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
			boolean exitFlag = true;
			while (exitFlag)
			{	
				Message oneMsg = ReceiveMessage();
				switch (oneMsg.messageType)
				{
				case Message.LINK:			//--------接受的消息为  1 用户名，表连接
					String hostName = clientSocket.getInetAddress().getHostName();
					User oneUser = new User(oneMsg.content, hostName, clientSocket.getInetAddress().getAddress());
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
					//输出message
					//MessageDialog messageDialog = new MessageDialog(null);
					//messageDialog.getShell().setText("和" + socket.getInetAddress().getHostName() + "的对话");
					//messageDialog.SetWriteText(socket.getInetAddress().getHostName() + "说：");
					//messageDialog.SetWriteText(message);
					//messageDialog.open();	
					break;
				case Message.END:			//--------接受的消息为  3 用户名，表结束对话
					exitFlag = false;
					break;
				default:					//--------其他类型的信息丢弃
				}
			}
			in.close();
			out.close();
			clientSocket.close();
		}
		catch (Exception e)
		{
			System.out.println("Exceptin in Client : " + e);
		}
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
	
	public Message ReceiveMessage()
	{
		try
		{
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
}