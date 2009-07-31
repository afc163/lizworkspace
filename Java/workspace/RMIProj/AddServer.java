import java.rmi.Remote;
import java.rmi.*;
import java.rmi.server.*;

public interface AddServer extends Remote {

	public int addData(int a, int b)throws RemoteException;
}
