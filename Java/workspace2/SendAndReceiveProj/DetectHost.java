import java.util.*;
import java.io.PrintWriter;
import java.net.*;

public class DetectHost implements Runnable
{
	static Vector exisitUser;
	Thread myThread;
	
	public DetectHost()//现在这个可以省略，但是这个比较快，所以下面还要去除本机的情况
	{
		exisitUser = new Vector();
		try
		{	//本机用户信息，默认用户名和主机名是一样的
			User myHostUser = new User(InetAddress.getLocalHost().getHostName(),
					InetAddress.getLocalHost().getHostName(), InetAddress.getLocalHost().getAddress());
			if (!exisitUser.add(myHostUser))
			{
				System.out.println("加入本机失败");
			}
			else
			{
				//System.out.println("加入本机");
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
		//尝试加入局域网中的机子
		
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
			//表明未产生异常加入到Vector中
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
					//发送连接确认，并把自己的用户名发送到对方
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
		//输出Vector看看
		for (int i = 0; i < exisitUser.size(); i++)
		{
			System.out.println(i + ((User)exisitUser.get(i)).hostName);
		}
	}
}
