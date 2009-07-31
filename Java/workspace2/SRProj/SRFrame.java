import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.CoolBarManager;
import org.eclipse.jface.action.MenuManager;
import org.eclipse.jface.action.Separator;
import org.eclipse.jface.action.StatusLineManager;
import org.eclipse.jface.action.ToolBarManager;
import org.eclipse.jface.viewers.DoubleClickEvent;
import org.eclipse.jface.viewers.IDoubleClickListener;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.ListViewer;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.window.ApplicationWindow;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.SashForm;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Button;
import swing2swt.layout.BorderLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.List;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;
import com.swtdesigner.SWTResourceManager;
import swing2swt.layout.FlowLayout;
import java.net.*;

public class SRFrame extends ApplicationWindow {

	private Action CancelButtonAction;
	private Action SendButtonAction;
	private Text Write_Text;
	private Text Inform_Text;
	private List myList;
	
	private String selectListString;
	private int selectListIndex;
	
	Server myServer;
	
	public void NotifyList()
	{
        //��ʾ���е��û�
		//list_1.removeAll();
		for (int i = 0; i < myServer.userNumber; i++)
		{
			myList.add(myServer.userArray[i].hostName);
		}
	}
	/**
	 * Create the application window
	 */
	public SRFrame() {
		
		super(null);
		createActions();
		addCoolBar(SWT.FLAT);
		addMenuBar();
		addStatusLine();
		
		myServer = new Server();
		//myServer.run();
		
		//�������  ���Ƿ���LINK��Ϣ�����ظ����ظ�������Ӽ���userArray
		//byte [] ipAddress = InetAddress.getLocalHost().getAddress();
		/*for (int index = 0; index < 256; index++)
		{
			ipAddress[3] = (byte)index;
			InetAddress ipa1 = InetAddress.getByAddress(ipAddress);
			try 
			{
			  if (ipa1.isReachable(1))
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
		}*/
	}

	/**
	 * Create contents of the application window
	 * @param parent
	 */
	protected Control createContents(Composite parent) {
		Composite container = new Composite(parent, SWT.BORDER);
		container.setBackground(SWTResourceManager.getColor(0, 128, 255));
		container.setLayout(new BorderLayout());

		final SashForm sashForm = new SashForm(container, SWT.NONE);
		sashForm.setBackground(Display.getCurrent().getSystemColor(SWT.COLOR_WHITE));

		final Composite composite = new Composite(sashForm, SWT.NONE);
		composite.setBackground(Display.getCurrent().getSystemColor(SWT.COLOR_WHITE));
		composite.setLayout(new FillLayout());

		final ListViewer MyListViewer = new ListViewer(composite, SWT.V_SCROLL | SWT.H_SCROLL | SWT.BORDER);
		MyListViewer.addSelectionChangedListener(new ISelectionChangedListener() {
			public void selectionChanged(SelectionChangedEvent event) {
				
			}
		});

		MyListViewer.addDoubleClickListener(new IDoubleClickListener() {
			public void doubleClick(DoubleClickEvent event) {
			}
		});
		myList = MyListViewer.getList();
		myList.setBackground(SWTResourceManager.getColor(255, 244, 250));
			
		final SashForm sashForm_1 = new SashForm(sashForm, SWT.VERTICAL);

		Inform_Text = new Text(sashForm_1, SWT.READ_ONLY | SWT.MULTI | SWT.BORDER);
		Inform_Text.setForeground(SWTResourceManager.getColor(0, 64, 0));
		Inform_Text.setFont(SWTResourceManager.getFont("����", 12, SWT.NONE));
		Inform_Text.setBackground(SWTResourceManager.getColor(254, 251, 241));

		Write_Text = new Text(sashForm_1, SWT.BORDER);
		Write_Text.setForeground(SWTResourceManager.getColor(0, 64, 128));
		Write_Text.setFont(SWTResourceManager.getFont("����_GB2312", 12, SWT.NONE));
		Write_Text.setBackground(SWTResourceManager.getColor(238, 247, 255));
		sashForm.setWeights(new int[] {98, 287 });
		sashForm_1.setWeights(new int[] {98, 60 });

		final Composite composite_1 = new Composite(container, SWT.BORDER);
		final FlowLayout flowLayout = new FlowLayout();
		flowLayout.setHgap(10);
		flowLayout.setAlignment(FlowLayout.TRAILING);
		composite_1.setLayout(flowLayout);
		composite_1.setBackground(SWTResourceManager.getColor(238, 255, 255));
		composite_1.setLayoutData(BorderLayout.SOUTH);

		final Button Send_Button = new Button(composite_1, SWT.NONE);
		Send_Button.addMouseListener(new MouseAdapter() {
			public void mouseUp(MouseEvent e) {
				SendButtonAction.run();
			}
		});
		Send_Button.setText("����(&E)");

		final Button Cancel_Button = new Button(composite_1, SWT.NONE);
		Cancel_Button.addMouseListener(new MouseAdapter() {
			public void mouseUp(MouseEvent e) {
				CancelButtonAction.run();
			}
		});
		Cancel_Button.setText("ȡ��(&C)");
		
        this.NotifyList();

		return container;
	}

	/**
	 * Create the actions
	 */
	private void createActions() {

		SendButtonAction = new Action("����") {
			public void run() {
				selectListIndex = myList.getSelectionIndex();
				if (selectListIndex >= 0)
				{	//+ ' ' + MyServer.userArray[0].HostName + ' ' 
					String SendText = Message.MSG + Write_Text.getText();
					Client MyClient = new Client(myServer.userArray[selectListIndex].hostName);
					MyClient.SetMessage(SendText);
					MyClient.run();
				}
				else
				{
					//δѡ�����
				}
			}
		};

		CancelButtonAction = new Action("ȡ��") {
			public void run() {
				//JOptionPane.showMessageDialog(null, "Cancel");
			}
		};
		// Create the actions
	}

	/**
	 * Create the menu manager
	 * @return the menu manager
	 */
	protected MenuManager createMenuManager() {
		MenuManager menuManager = new MenuManager();

		final MenuManager menuManager_1 = new MenuManager("ѡ��");
		menuManager.add(menuManager_1);

		menuManager_1.add(SendButtonAction);

		menuManager_1.add(CancelButtonAction);

		menuManager.add(new Separator());
		return menuManager;
	}

	/**
	 * Create the coolbar manager
	 * @return the coolbar manager
	 */
	protected CoolBarManager createCoolBarManager(int style) {
		CoolBarManager coolBarManager = new CoolBarManager(style);

		final ToolBarManager toolBarManager = new ToolBarManager();
		coolBarManager.add(toolBarManager);

		toolBarManager.add(SendButtonAction);

		toolBarManager.add(CancelButtonAction);
		return coolBarManager;
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
	 * Launch the application
	 * @param args
	 */
	public static void main(String args[]) {
		try {			
			SRFrame window = new SRFrame();
			window.setBlockOnOpen(true);
			window.open();
			Display.getCurrent().dispose();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Configure the shell
	 * @param newShell
	 */
	protected void configureShell(Shell newShell) {
		super.configureShell(newShell);
		newShell.setText("SRApplication");
	}

	/**
	 * Return the initial size of the window
	 */
	protected Point getInitialSize() {
		return new Point(400, 300);
	}

}