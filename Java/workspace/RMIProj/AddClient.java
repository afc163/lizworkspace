import java.io.*;
import java.rmi.*;

public class AddClient 
{
	public AddClient()
	{
		try
		{
			BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
			System.out.println("Please enter first number:");
			System.out.flush();
			String s = input.readLine();
			int num1 = Integer.parseInt(s);
			System.out.println("Please enter second number:");
			System.out.flush();
			s = input.readLine();
			int num2 = Integer.parseInt(s);
			AddServer addserver = (AddServer)Naming.lookup("rmi://127.0.0.1/add");
			int i = addserver.addData(num1, num2);
			System.out.println("Result is :" + i);
		}
		catch (Exception e)
		{
			System.out.println(e);
		}
	}
	public static void main(String [] args)
	{
		new AddClient();
	}
}
