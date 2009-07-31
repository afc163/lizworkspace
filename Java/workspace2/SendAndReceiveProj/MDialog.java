import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.SashForm;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;
import com.swtdesigner.SWTResourceManager;
import swing2swt.layout.BorderLayout;

public class MDialog extends Dialog
{	
	private static final int SEND_ID = IDialogConstants.CLIENT_ID + 1;
	private Text inputText;
	private Text outputText;
	private String userHostName = null;
	
	MDialogThread myThread = null;
	/**
	 * Create the dialog
	 * @param parentShell
	 */
	public MDialog(Shell parentShell) {
		super(parentShell);
	}

	/**
	 * Create contents of the dialog
	 * @param parent
	 */
	protected Control createDialogArea(Composite parent) {
		parent.setBackground(SWTResourceManager.getColor(218, 237, 237));
		Composite container = (Composite) super.createDialogArea(parent);
		container.setBackground(SWTResourceManager.getColor(147, 201, 255));
		container.setLayout(new BorderLayout(0, 0));

		final SashForm sashForm = new SashForm(container, SWT.VERTICAL);
		sashForm.setBackground(SWTResourceManager.getColor(218, 237, 237));

		outputText = new Text(sashForm, SWT.V_SCROLL | SWT.READ_ONLY | SWT.MULTI | SWT.H_SCROLL | SWT.BORDER);
		outputText.setText("OutputText");
		sashForm.setLayoutData(BorderLayout.CENTER);

		inputText = new Text(sashForm, SWT.V_SCROLL | SWT.MULTI | SWT.H_SCROLL | SWT.BORDER);
		inputText.setText("InputText");
		sashForm.setWeights(new int[] {203, 92 });
		//
		getShell().setText("与 " + userHostName + " 对话中");
		return container;
	}

	/**
	 * Create contents of the button bar
	 * @param parent
	 */
	protected void createButtonsForButtonBar(Composite parent) {
		parent.setBackground(SWTResourceManager.getColor(218, 237, 237));
		final Button sendButton = createButton(parent, SEND_ID, "Send",
				true);
		sendButton.addMouseListener(new MouseAdapter() {
			public void mouseUp(MouseEvent e) {  
				String content = inputText.getText().trim();
				if (content != null)
				{
					if (myThread == null)
					{
						myThread = new MDialogThread(userHostName);
					}					
					Message oneMessage = new Message(Message.MSG, content);
					myThread.SendMessage(oneMessage);
					outputText.append('\n' + userHostName + "say: " + '\n' + content);
				}
			}
		});
		final Button closeButton = createButton(parent, IDialogConstants.CLOSE_ID,
				"Close", false);
		closeButton.addMouseListener(new MouseAdapter() {
			public void mouseUp(MouseEvent e) {
				
			}
		});
	}

	/**
	 * Return the initial size of the dialog
	 */
	protected Point getInitialSize() {
		return new Point(400, 300);
	}
	protected void configureShell(Shell newShell) {
		super.configureShell(newShell);
		newShell.setText("MessageDialog");
	}

	public void SetUser(String hName)
	{
		userHostName = hName;
	}
	
	public boolean close()
	{
		Message oneMessage = new Message(Message.END, userHostName);
		myThread.SendMessage(oneMessage);		
		return super.close();
	}
	
	//内部类MDialogThread
	class MDialogThread extends Thread {
		static final int CPORT = 8000;
		Socket socket;
		PrintWriter out;
		BufferedReader in;
		
		public MDialogThread()
		{
			try
			{
				//LocalHost's Socket
				socket = new Socket(InetAddress.getLocalHost(), CPORT);
				
				in = new BufferedReader(new InputStreamReader(socket.getInputStream(), "GB2312"));
				out = new PrintWriter(socket.getOutputStream(), true);
				
				start();
			}
			catch (Exception e)
			{
				System.out.println("MDialogThread Construct Exception: " + e);
			}
		}
		
		public MDialogThread(String hostName)
		{
			try
			{
				//OtherHost's Socket
				InetAddress inetAddress = InetAddress.getByName(hostName);
				socket = new Socket(inetAddress, CPORT);
				
				in = new BufferedReader(new InputStreamReader(socket.getInputStream(), "GB2312"));
				out = new PrintWriter(socket.getOutputStream(), true);
				
				start();
			}
			catch (Exception e)
			{
				System.out.println("MDialogThread Exception: " + e);
			}
		}
		
		public void run()
		{
			try
			{
				boolean exitFlag = true;
				while (exitFlag)
				{	
					Message oneMsg = ReceiveMessage();
					switch (oneMsg.messageType)
					{
					case Message.LINK:			//--------接受的消息为  1 用户名，表连接
						String hostName = socket.getInetAddress().getHostName();
						User oneUser = new User(oneMsg.content, hostName, socket.getInetAddress().getAddress());
						//若已存在则需查找
						//待做   用户的名字改变则要检测并修改
						if (DetectHost.exisitUser.contains(oneUser))
						{
						}
						else                   //---------不存在则加入
						{
							DetectHost.exisitUser.addElement(oneUser);
						}
						exitFlag = false;
						break;
					case Message.MSG:			//--------接受的消息为  2 消息内容，一般消息
						//输出message
						outputText.append(socket.getInetAddress().getHostName() + "说：\n");
						outputText.append(oneMsg.content);
						break;
					case Message.END:			//--------接受的消息为  3 用户名，表结束对话
						exitFlag = false;
						break;
					default:					//--------其他类型的信息丢弃
					}
				}
				in.close();
				out.close();
				socket.close();
			}
			catch (Exception e)
			{
				System.out.println("Exceptin in MDialogThread : " + e);
			}
		}
		
		public boolean SendMessage(Message msg)
		{
			try
			{
				out.println(msg);
				return true;
			}
			catch(Exception e)
			{
				return false;
			}
		}
		
		public Message ReceiveMessage()
		{
			try
			{
				String msg = in.readLine();
				int type = msg.charAt(0) - '0';
				String content = msg.substring(2);
				return new Message(type, content);
			}
			catch (IOException e)
			{
				System.out.println("Exception in ReceiveMessage: " + e);
			}
			return null;
		}
	}
}
































