import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.rmi.*;
import java.rmi.server.*;

public class AddServerImpl extends UnicastRemoteObject implements AddServer {

	public AddServerImpl() throws RemoteException
	{
		super();
	}
	
	public int addData(int a, int b) throws RemoteException 
	{
		// TODO Auto-generated method stub
		return a + b;
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) 
	{
		// TODO Auto-generated method stub
		try
		{
			//����Զ�̶���
			AddServerImpl instance = new AddServerImpl(); 
		    //ע��Զ�̶���
			Naming.rebind("add", instance);
			System.out.println("Server Registered");
		}
		catch (Exception e)
		{
			System.out.println(e);
		}
	}
}
