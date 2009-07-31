
public class User {
	String userName;
	String hostName;
	byte [] iPAddress;
	public User(String uName, String hName, byte [] ipAddress)
	{
		userName = uName;
		hostName = hName;
		iPAddress = new byte [4];
		iPAddress[0] = ipAddress[0];
		iPAddress[1] = ipAddress[1];
		iPAddress[2] = ipAddress[2];
		iPAddress[3] = ipAddress[3];
		//或者
		//IPAddress = ipAddress;
	}
}

class Message
{
	//消息的不同类型
	static final int LINK = 1;
	static final int MSG = 2;
	static final int END = 3;
	
	int messageType;
	String content;
	
	public Message(int msgType, String cont)
	{
		messageType = msgType;
		content = cont;
	}
}
