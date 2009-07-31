

public class MainP {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Server oneServer = new Server();
		Client oneClient = new Client();
		oneServer.start();
		oneClient.start();
		System.out.println("both started!");
	}

}
