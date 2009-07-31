import java.net.*;
import java.io.*;

public class Client extends Thread {
	public Client()
	{

	}
	public void run()
	{
		String str;
		//InetAddress ipme = InetAddress.getLocalHost();
		//System.out.println("Local Host Name: " + ipme.getHostName());
		//System.out.println("Local Host IP Address: " + ipme.getHostAddress());

		//byte [] addr = ipme.getAddress();
		
		try
		{
			
			//InetAddress address = InetAddress.getByAddress(addr);
			//Socket socket = new Socket(address, 8000);
			Socket socket = new Socket(InetAddress.getLocalHost(), 8000);
			System.out.println("Socket: " + socket);
			//获得对应Socket的输入/输出流
			InputStream fIn = socket.getInputStream();
			OutputStream fOut = socket.getOutputStream();
			//建立数据流
			InputStreamReader isr = new InputStreamReader(fIn);
			BufferedReader in = new BufferedReader(isr);
			
			PrintStream out = new PrintStream(fOut);
			
			InputStreamReader userisr = new InputStreamReader(System.in);
			BufferedReader userin = new BufferedReader(userisr);
			
			while (true)
			{
				System.out.println("发送字符串：");
				str = userin.readLine();//读用户输入的字符串
				out.println(str);//将字符串传给服务器端
				
				if (str.equalsIgnoreCase("end"))
				{
					break;
				}
				System.out.println("等待服务器端消息...");
				str = in.readLine();//获得服务器的字符串
				System.out.println("服务器端字符：" + str);
				if (str.equalsIgnoreCase("end"))
				{
					break;
				}
			}
			socket.close();
		}
		catch (Exception e)
		{
			System.out.println("异常：" + e);
		}		
	}
}
