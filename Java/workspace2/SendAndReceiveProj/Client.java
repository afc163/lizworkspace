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
				case Message.LINK:			//--------���ܵ���ϢΪ  1 �û�����������
					String hostName = clientSocket.getInetAddress().getHostName();
					User oneUser = new User(oneMsg.content, hostName, clientSocket.getInetAddress().getAddress());
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
					//���message
					//MessageDialog messageDialog = new MessageDialog(null);
					//messageDialog.getShell().setText("��" + socket.getInetAddress().getHostName() + "�ĶԻ�");
					//messageDialog.SetWriteText(socket.getInetAddress().getHostName() + "˵��");
					//messageDialog.SetWriteText(message);
					//messageDialog.open();	
					break;
				case Message.END:			//--------���ܵ���ϢΪ  3 �û�����������Ի�
					exitFlag = false;
					break;
				default:					//--------�������͵���Ϣ����
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