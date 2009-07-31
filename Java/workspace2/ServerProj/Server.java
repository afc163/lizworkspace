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
			ServerSocket sserver = new ServerSocket(8000);//������ǰ�̵߳ļ�������
			System.out.println("Started : " + sserver);
			Socket socket = sserver.accept();//����C/Sͨ�ŵ�Socket����
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
				System.out.println("�ȴ��ͻ��˵���Ϣ������");
				str = in.readLine();//���ͻ��˴��͵��ַ���
				System.out.println("�ͻ��ˣ�" + str);
				if (str.equalsIgnoreCase("end"))
				{
					break;
				}
				System.out.println("���ͻ��˷��ͣ�");
				str = userin.readLine();
				out.println(str);//��ͻ��˷�����Ϣ
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
			System.out.println("�쳣��" + e);
		}		
	}

}
