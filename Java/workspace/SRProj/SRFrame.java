
import org.eclipse.jface.action.CoolBarManager;
import org.eclipse.jface.action.MenuManager;
import org.eclipse.jface.action.Separator;
import org.eclipse.jface.action.StatusLineManager;
import org.eclipse.jface.action.ToolBarManager;
import org.eclipse.jface.viewers.ListViewer;
import org.eclipse.jface.window.ApplicationWindow;
//import org.eclipse.swt.SWT;
//import org.eclipse.swt.custom.SashForm;
//import org.eclipse.swt.events.MouseAdapter;
//import org.eclipse.swt.events.MouseEvent;
//import org.eclipse.jface.action.Action;
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
//import org.eclipse.jface.dialogs.*;

public class SRFrame extends ApplicationWindow {

	private Action CancelButtonAction;
	private Action SendButtonAction;
	private Text Write_Text;
	private Text Read_Text;
	private List list_1;
	/**
	 * Create the application window
	 */
	public SRFrame() {
		super(null);
		createActions();
		addCoolBar(SWT.FLAT);
		addMenuBar();
		addStatusLine();
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
		list_1 = MyListViewer.getList();
		list_1.setItems(new String[] {"shengyan", "lizzie", "jinjin"});
		list_1.setBackground(SWTResourceManager.getColor(255, 244, 250));

		final SashForm sashForm_1 = new SashForm(sashForm, SWT.VERTICAL);

		Read_Text = new Text(sashForm_1, SWT.V_SCROLL | SWT.READ_ONLY | SWT.MULTI | SWT.H_SCROLL | SWT.BORDER);
		Read_Text.setText("lizzie");
		Read_Text.setForeground(SWTResourceManager.getColor(0, 64, 0));
		Read_Text.setFont(SWTResourceManager.getFont("宋体", 12, SWT.NONE));
		Read_Text.setBackground(SWTResourceManager.getColor(254, 251, 241));

		Write_Text = new Text(sashForm_1, SWT.BORDER);
		Write_Text.setText("write");
		Write_Text.setForeground(SWTResourceManager.getColor(0, 64, 128));
		Write_Text.setFont(SWTResourceManager.getFont("仿宋_GB2312", 12, SWT.NONE));
		Write_Text.setBackground(SWTResourceManager.getColor(238, 247, 255));
		sashForm_1.setWeights(new int[] {1, 1 });

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
		Send_Button.setText("发送(&E)");

		final Button Cancel_Button = new Button(composite_1, SWT.NONE);
		Cancel_Button.addMouseListener(new MouseAdapter() {
			public void mouseUp(MouseEvent e) {
				CancelButtonAction.run();
			}
		});
		Cancel_Button.setText("取消(&C)");
		sashForm.setWeights(new int[] {1, 1 });

		return container;
	}

	/**
	 * Create the actions
	 */
	private void createActions() {

		SendButtonAction = new Action("发送") {
			public void run() {
				//Dialog SendeDialog = new Dialog(null);
				//SendeDialog.DLG_IMG_MESSAGE_INFO = "Sender";
				//JOptionPane.showMessageDialog(null, "Sender");
			}
		};

		CancelButtonAction = new Action("取消") {
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

		final MenuManager menuManager_1 = new MenuManager("选择");
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
