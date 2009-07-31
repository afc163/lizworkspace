package simpleSever;
import java.io.*;
import java.net.*;

public class SimpleSeverWorker implements Runnable {

	private Socket theSocket = null;
	private BufferedReader theReader = null;
	private PrintWriter theWriter = null;
	
	public SimpleSeverWorker(Socket theSocket)
	{
		this.theSocket = theSocket;
	}
	public void run() {
		// TODO Auto-generated method stub

		try
		{
			InetAddress inet = theSocket.getInetAddress();
			String clientName = inet.getHostName();
			
			System.out.println(clientName + " is connected");
			theReader = new BufferedReader(new InputStreamReader(theSocket.getInputStream()));
			theWriter = new PrintWriter(new OutputStreamWriter(theSocket.getOutputStream()));
			
			String mesg;
			
			while (true)
			{
				mesg = theReader.readLine();
				if (mesg.equalsIgnoreCase("end"))
				{
					break;
				}
				theWriter.println(mesg);
				theWriter.flush();
			}
			System.out.println(clientName + " is disconnected ");
		}
		catch (Exception e)
		{
			System.out.println(e);
		}
		finally
		{
			try
			{
				theWriter.close();
				theReader.close();
				theSocket.close();
			}
			catch (Exception e)
			{
			}
		}
	}

}
