import java.net.*;
import java.io.*;

class Server extends Thread {
	static final int SPort = 5656;
	//private User [] userArray = new User[10]; 
	//private int userNumber = 0;
	//private String userName = null;

	User [] userArray = new User[10]; 
	int userNumber = 0;
	String userName = null;
	
	public Server()
	{		
		try
		{
			userArray[userNumber++] = new User(userName, InetAddress.getLocalHost().getHostName(), InetAddress.getLocalHost().getAddress());
		}
		catch (UnknownHostException e)
		{
			System.out.println(e);
		}
	}
	
	public void AddUser()
	{
		
	}
	
	private Message ReadOneMsg(BufferedReader in) 
	{
		int messageType;
		String message;
		try
		{			
			messageType = in.read();
			message = in.readLine();
			Message msg = new Message(messageType, message);
			return msg;
		}
		catch (IOException e)
		{
			System.out.println(e);
		}
		return null;
	}
	
	public void run()
	{
		Message oneMsg = null;
		try
		{
			ServerSocket myServerSocket = new ServerSocket(SPort);//创建当前线程的监听对象
			Socket socket = myServerSocket.accept();//负责C/S通信的Socket对象
			//获得对应Socket的输入/输出流
			InputStream fIn = socket.getInputStream();
			InputStreamReader isr = new InputStreamReader(fIn);
			BufferedReader in = new BufferedReader(isr);
					
			try
			{	//读一条消息，待改进
				int messageType = in.read();
				String message = in.readLine();
				oneMsg = new Message(messageType, message);
				switch (oneMsg.messageType)
				{
				case Message.LINK://连接信号
					String hostName = socket.getInetAddress().getHostName();
					//若已存在则需查找
					byte [] IPAddress = socket.getInetAddress().getAddress();
					userArray[userNumber++] = new User(message, hostName, IPAddress);
					//返回消息 可能不要
					//Client MyClient = new Client(userArray[0].hostName);
					//MyClient.SetMessage(Message.LINK + " shengyan");
					//MyClient.run();
					break;
				case Message.MSG:
					//输出message
					MessageDialog messageDialog = new MessageDialog(null);
					messageDialog.getShell().setText("和" + socket.getInetAddress().getHostName() + "的对话");
					messageDialog.SetWriteText(socket.getInetAddress().getHostName() + "说：");
					messageDialog.SetWriteText(message);
					messageDialog.open();					
					break;
				case Message.END:
					break;
				default://其他类型的信息丢弃
				}
			}
			catch (IOException e)
			{
				System.out.println(e);
			}			
			socket.close();
			myServerSocket.close();
		}
		catch (Exception e)
		{
			System.out.println("异常：" + e);
		}		
	}

}
