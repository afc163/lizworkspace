package simpleSever;
import java.io.*;
import java.net.*;

public class SimpleSever {

	/**
	 * @param args
	 */
	private static final int PORT_NUMBER = 10000;
	public static void main(String[] args) {
		// TODO Auto-generated method stub

		ServerSocket server = null;
		Socket theSocket = null;
		DataInputStream theReader = null;
		PrintStream theWriter = null;
		
		try
		{
			server = new ServerSocket(PORT_NUMBER);
			SimpleSeverWorker worker;
			Thread thread;
			
			while (true)
			{
				//创建一个线程处理客户端的请求
				theSocket = server.accept();
				worker = new SimpleSeverWorker(theSocket);
				thread = new Thread(worker);
				thread.start();
			}
		}
		catch (Exception e)
		{
			System.out.print(e);
		}
		finally
		{
			try
			{
				server.close();
			}
			catch (Exception e)
			{
			}
		}
	}
}
