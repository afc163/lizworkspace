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
			//��ö�ӦSocket������/�����
			InputStream fIn = socket.getInputStream();
			OutputStream fOut = socket.getOutputStream();
			//����������
			InputStreamReader isr = new InputStreamReader(fIn);
			BufferedReader in = new BufferedReader(isr);
			
			PrintStream out = new PrintStream(fOut);
			
			InputStreamReader userisr = new InputStreamReader(System.in);
			BufferedReader userin = new BufferedReader(userisr);
			
			while (true)
			{
				System.out.println("�����ַ�����");
				str = userin.readLine();//���û�������ַ���
				out.println(str);//���ַ���������������
				
				if (str.equalsIgnoreCase("end"))
				{
					break;
				}
				System.out.println("�ȴ�����������Ϣ...");
				str = in.readLine();//��÷��������ַ���
				System.out.println("���������ַ���" + str);
				if (str.equalsIgnoreCase("end"))
				{
					break;
				}
			}
			socket.close();
		}
		catch (Exception e)
		{
			System.out.println("�쳣��" + e);
		}		
	}
}
