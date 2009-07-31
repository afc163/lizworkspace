import java.util.*;
import java.io.PrintWriter;
import java.net.*;

public class DetectHost implements Runnable
{
	static Vector exisitUser;
	Thread myThread;
	
	public DetectHost()//�����������ʡ�ԣ���������ȽϿ죬�������滹Ҫȥ�����������
	{
		exisitUser = new Vector();
		try
		{	//�����û���Ϣ��Ĭ���û�������������һ����
			User myHostUser = new User(InetAddress.getLocalHost().getHostName(),
					InetAddress.getLocalHost().getHostName(), InetAddress.getLocalHost().getAddress());
			if (!exisitUser.add(myHostUser))
			{
				System.out.println("���뱾��ʧ��");
			}
			else
			{
				//System.out.println("���뱾��");
			}			
			//myThread = new Thread(this);
			//myThread.start();
		}
		catch (Exception e)
		{
			System.out.println(e);
		}		
	}
	
	public void run()
	{
		//���Լ���������еĻ���
		
		byte [] ipAddress = ((User)exisitUser.elementAt(0)).iPAddress;

		for (int index = 22; index < 24; index++)
		{
			Socket clientSocket;
			ipAddress[3] = (byte)index;
			try
			{
				InetAddress otherHostAddress = InetAddress.getByAddress(ipAddress);
				clientSocket = new Socket(otherHostAddress, Client.CPORT);
			}
			catch (Exception e)
			{
				System.out.println("Unable connect" + index);
				continue;
			}
			//����δ�����쳣���뵽Vector��
			try
			{	
				User otherHostUser = new User(clientSocket.getInetAddress().getHostName(),
						clientSocket.getInetAddress().getHostName(), clientSocket.getInetAddress().getAddress());				
				if (!exisitUser.add(otherHostUser))
				{
					System.out.println("Add User Failed");
				}
				else
				{
					//��������ȷ�ϣ������Լ����û������͵��Է�
					PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
					out.println(Message.LINK + ' ' + ((User)exisitUser.elementAt(0)).userName);
				}
				clientSocket.close();
			}
			catch (Exception e)
			{
				System.out.println("Exception in DetectHost Running: " + e);
			}
		}
		//���Vector����
		for (int i = 0; i < exisitUser.size(); i++)
		{
			System.out.println(i + ((User)exisitUser.get(i)).hostName);
		}
	}
}
