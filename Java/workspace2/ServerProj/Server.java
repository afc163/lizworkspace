import java.net.*;
import java.io.*;

public class Server extends Thread {
	public Server()
	{

	}
	public void run()
	{
		String str;
		try
		{
			ServerSocket sserver = new ServerSocket(8000);//创建当前线程的监听对象
			System.out.println("Started : " + sserver);
			Socket socket = sserver.accept();//负责C/S通信的Socket对象
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
				System.out.println("等待客户端的消息。。。");
				str = in.readLine();//读客户端传送的字符串
				System.out.println("客户端：" + str);
				if (str.equalsIgnoreCase("end"))
				{
					break;
				}
				System.out.println("给客户端发送：");
				str = userin.readLine();
				out.println(str);//向客户端发送消息
				if (str.equalsIgnoreCase("end"))
				{
					break;
				}
			}
			socket.close();
			sserver.close();
		}
		catch (Exception e)
		{
			System.out.println("异常：" + e);
		}		
	}

}
