import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.MenuManager;
import org.eclipse.jface.action.StatusLineManager;
import org.eclipse.jface.action.ToolBarManager;
import org.eclipse.jface.window.ApplicationWindow;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.SashForm;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.List;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;
import swing2swt.layout.FlowLayout;
import com.swtdesigner.SWTResourceManager;
import swing2swt.layout.BorderLayout;
import java.util.Vector;


public class MainFrame extends ApplicationWindow 
{
	private Action freshAction;
	private Action sendMAction;
	private Text outputText;
	private Action chooseUserAction;
	private Action cancelAction;
	private Action sendAction;
	private Text inputText;
	private List userList;
	
	static Server myServer;
	static DetectHost detectHost;
	
	private Client myClient = null;
	/**
	 * Create the application window
	 */
	public MainFrame() {
		super(null);
		createActions();
		addToolBar(SWT.FLAT | SWT.WRAP);
		addMenuBar();
		addStatusLine();
	}

	/**
	 * Create contents of the application window
	 * @param parent
	 */
	protected Control createContents(Composite parent) {
		Composite container = new Composite(parent, SWT.NONE);
		container.setLayout(new BorderLayout(0, 0));

		final SashForm sashForm = new SashForm(container, SWT.NONE);

		userList = new List(sashForm, SWT.V_SCROLL | SWT.H_SCROLL | SWT.BORDER);
		userList.addMouseListener(new MouseAdapter() {
			public void mouseDown(MouseEvent e) {
					chooseUserAction.run();
			}
			public void mouseDoubleClick(MouseEvent e) {
					sendMAction.run();
			}
		});
		userList.setForeground(SWTResourceManager.getColor(64, 128, 128));
		sashForm.setBackground(SWTResourceManager.getColor(218, 237, 237));
		sashForm.setLayoutData(BorderLayout.CENTER);

		final SashForm sashForm_1 = new SashForm(sashForm, SWT.VERTICAL);
		sashForm_1.setBackground(SWTResourceManager.getColor(218, 237, 237));

		outputText = new Text(sashForm_1, SWT.V_SCROLL | SWT.READ_ONLY | SWT.MULTI | SWT.H_SCROLL | SWT.BORDER);
		outputText.setEditable(false);
		outputText.setForeground(SWTResourceManager.getColor(64, 128, 128));
		outputText.setText("OutputInformation");

		inputText = new Text(sashForm_1, SWT.V_SCROLL | SWT.MULTI | SWT.H_SCROLL | SWT.BORDER);
		inputText.setForeground(SWTResourceManager.getColor(64, 128, 128));
		inputText.setText("InputSomething");
		sashForm_1.setWeights(new int[] {165, 96 });
		sashForm.setWeights(new int[] {147, 342 });

		final Composite composite = new Composite(container, SWT.NONE);
		final FlowLayout flowLayout = new FlowLayout();
		flowLayout.setAlignment(FlowLayout.RIGHT);
		composite.setLayout(flowLayout);
		composite.setBackground(SWTResourceManager.getColor(218, 237, 237));
		composite.setLayoutData(BorderLayout.SOUTH);

		final Button freshButton = new Button(composite, SWT.NONE);
		freshButton.addMouseListener(new MouseAdapter() {
			public void mouseUp(MouseEvent e) {
				freshAction.run();
			}
		});
		freshButton.setText("Fresh");

		final Button sendButton = new Button(composite, SWT.NONE);
		sendButton.setSelection(true);
		sendButton.addMouseListener(new MouseAdapter() {
			public void mouseDown(MouseEvent e) {
				sendAction.run();
			}
		});
		sendButton.setText("Send");

		final Button cancelButton = new Button(composite, SWT.NONE);
		cancelButton.addMouseListener(new MouseAdapter() {
			public void mouseDown(MouseEvent e) {
				cancelAction.run();
			}
		});
		cancelButton.setText("Cancel");
		//
		
		updateUserList(DetectHost.exisitUser);
		
		
		return container;
	}

	/**
	 * Create the actions
	 */
	private void createActions() {

		sendAction = new Action("Send") {
			public void run() {
				int index = userList.getSelectionIndex();
				if (index >= 0) //Choose One Item to Sending
				{
					//Get Text to Send
					String content = inputText.getText().trim();
					if (content != null)
					{
						String hostName = ((User)DetectHost.exisitUser.elementAt(index)).hostName;
						//Gernerate One Client
						if (myClient == null)
						{
							myClient = new Client(hostName);
						}
						Message oneMessage = new Message(Message.MSG, content);
						myClient.SendMessage(oneMessage);
					}
				}
			}
		};

		cancelAction = new Action("Cancel") {
			public void run() {
			}
		};

		chooseUserAction = new Action("ChooseUser") {
			public void run() {
				int index = userList.getSelectionIndex();
				if (index >= 0) //Choose One Item
				{
					outputText.setText("");
					String hostName = ((User)DetectHost.exisitUser.elementAt(index)).hostName;
					byte [] temp = ((User)DetectHost.exisitUser.elementAt(index)).iPAddress;
					                                   //IP地址有点问题
					String ipAddress = String.valueOf(temp[0] + 256) + '.'
					                   + String.valueOf(temp[1] + 256) + '.'
					                   + String.valueOf(temp[2]) + '.'
					                   + String.valueOf(temp[3]);
					String showString = userList.getItem(index) + " 的信息是：" + '\n'
					                   + "主机名为：" + hostName + '\n'
					                   + "IP地址为：" + ipAddress + '\n';
					outputText.setText(showString);
				}
			}
		};

		sendMAction = new Action("SendWithDialog") {
			public void run() {
				int index = userList.getSelectionIndex();
				if (index >= 0) //Choose One Item
				{
					String hostName = ((User)DetectHost.exisitUser.elementAt(index)).hostName;				
					//双击用户名产生MDialog对话框
					MDialog myDialog = new MDialog(Display.getCurrent().getActiveShell());
					myDialog.SetUser(hostName);
					//myDialog.setBlockOnOpen(false);
					myDialog.open();
				}
			}
		};

		freshAction = new Action("Fresh") {
			public void run() {
				//MDialog tempDialog = new MDialog(Display.getCurrent().getActiveShell());
				//tempDialog.setBlockOnOpen(false);
				//tempDialog.open();
			}
		};
		// Create the actions
	}

	/**
	 * Create the menu manager
	 * @return the menu manager
	 */
	protected MenuManager createMenuManager() {
		MenuManager menuManager = new MenuManager("menu");
		return menuManager;
	}

	/**
	 * Create the toolbar manager
	 * @return the toolbar manager
	 */
	protected ToolBarManager createToolBarManager(int style) {
		ToolBarManager toolBarManager = new ToolBarManager(style);

		toolBarManager.add(sendAction);

		toolBarManager.add(cancelAction);
		return toolBarManager;
	}

	/**
	 * Create the status line manager
	 * @return the status line manager
	 */
	protected StatusLineManager createStatusLineManager() {
		StatusLineManager statusLineManager = new StatusLineManager();
		statusLineManager.setMessage(null, "shengyan");
		return statusLineManager;
	}

	/**
	 * Configure the shell
	 * @param newShell
	 */
	protected void configureShell(Shell newShell) {
		super.configureShell(newShell);
		newShell.setText("MainFrame");
	}

	/**
	 * Return the initial size of the window
	 */
	protected Point getInitialSize() {
		return new Point(500, 375);
	}

	/**
	 * 更新List
	 */
	public void updateUserList(Vector vector)
	{
		for (int index = 0; index < vector.size(); index++)
		{
			userList.add(((User)vector.get(index)).hostName);
		}
	}
	
	/**
	 * Launch the application
	 * @param args
	 */
	public static void main(String args[]) 
	{
		try 
		{
			myServer = new Server();
			myServer.start();
			detectHost = new DetectHost();
			
			//while (detectHost.myThread.isAlive())
			//{
			//	System.out.println("-");
			//}
			
			MainFrame window = new MainFrame();
			window.setBlockOnOpen(true);
			window.open();
			Display.getCurrent().dispose();
			//myServer.setStopSign();
			//发送给服务器一个结束消息才能离开accept的等待状态
			//Client endClient = new Client();
			//endClient.SendMessage(msg, out)
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
		}
	}
}
