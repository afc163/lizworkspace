import java.net.*;

public class CS {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		try {

			InetAddress ipme = InetAddress.getLocalHost();
			System.out.println("Local Host Name: " + ipme.getHostName());
			System.out.println("Local Host IP Address: " + ipme.getHostAddress());

			byte [] addr = ipme.getAddress();
			    //InetAddress ipa1 = InetAddress.getByName("STARCRAF-CEFISG");
			for (int index = 20; index < 30; index++)
			{
				addr[3] = (byte)index;
				InetAddress ipa1 = InetAddress.getByAddress(addr);
				try 
				{
				  if (ipa1.isReachable(1000))
				  {
				     System.out.println("Host name: " + ipa1.getHostName());
				     System.out.println("Host IP Address: " + ipa1.getHostAddress());
				  }
				  else
				  {
					  System.out.println("Not Reachable to : " + ipa1.getHostAddress());
				  }
				}
				catch (Exception e)
				{
					System.out.println(e.toString());
				}
			}
			
		}
		catch (UnknownHostException e)
		{
			System.out.println(e.toString());
		}

	}

}
