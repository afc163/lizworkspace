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
			ServerSocket myServerSocket = new ServerSocket(SPort);//������ǰ�̵߳ļ�������
			Socket socket = myServerSocket.accept();//����C/Sͨ�ŵ�Socket����
			//��ö�ӦSocket������/�����
			InputStream fIn = socket.getInputStream();
			InputStreamReader isr = new InputStreamReader(fIn);
			BufferedReader in = new BufferedReader(isr);
					
			try
			{	//��һ����Ϣ�����Ľ�
				int messageType = in.read();
				String message = in.readLine();
				oneMsg = new Message(messageType, message);
				switch (oneMsg.messageType)
				{
				case Message.LINK://�����ź�
					String hostName = socket.getInetAddress().getHostName();
					//���Ѵ����������
					byte [] IPAddress = socket.getInetAddress().getAddress();
					userArray[userNumber++] = new User(message, hostName, IPAddress);
					//������Ϣ ���ܲ�Ҫ
					//Client MyClient = new Client(userArray[0].hostName);
					//MyClient.SetMessage(Message.LINK + " shengyan");
					//MyClient.run();
					break;
				case Message.MSG:
					//���message
					MessageDialog messageDialog = new MessageDialog(null);
					messageDialog.getShell().setText("��" + socket.getInetAddress().getHostName() + "�ĶԻ�");
					messageDialog.SetWriteText(socket.getInetAddress().getHostName() + "˵��");
					messageDialog.SetWriteText(message);
					messageDialog.open();					
					break;
				case Message.END:
					break;
				default://�������͵���Ϣ����
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
			System.out.println("�쳣��" + e);
		}		
	}

}
