import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.MenuManager;
import org.eclipse.jface.action.Separator;
import org.eclipse.jface.action.StatusLineManager;
import org.eclipse.jface.action.ToolBarManager;
import org.eclipse.jface.window.ApplicationWindow;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.SashForm;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.List;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.Text;
import swing2swt.layout.FlowLayout;
import com.swtdesigner.SWTResourceManager;
import swing2swt.layout.BorderLayout;


public class MainFrame extends ApplicationWindow {

	private List list;
	private Action exitAction;
	private Action sendAction;
	private Text text;
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
		container.setForeground(SWTResourceManager.getColor(210, 210, 255));
		container.setLayout(new BorderLayout(0, 0));

		final SashForm sashForm = new SashForm(container, SWT.VERTICAL);
		sashForm.setForeground(SWTResourceManager.getColor(255, 255, 238));
		sashForm.setLayoutData(BorderLayout.CENTER);

		list = new List(sashForm, SWT.BORDER);
		list.setItems(new String[] {"shengyan sheng", "lizzie"});
		list.setData("Other", "1");
		list.setData("IP Address", "1");
		list.setData("Host Name", "1");
		list.setData("Name", "1");

		text = new Text(sashForm, SWT.BORDER);
		text.setBackground(SWTResourceManager.getColor(244, 255, 244));
		sashForm.setWeights(new int[] {1, 1 });

		final Composite composite = new Composite(container, SWT.BORDER);
		composite.setBackground(SWTResourceManager.getColor(255, 255, 238));
		final FlowLayout flowLayout = new FlowLayout();
		flowLayout.setVgap(10);
		flowLayout.setHgap(10);
		composite.setLayout(flowLayout);
		composite.setForeground(SWTResourceManager.getColor(255, 247, 238));
		composite.setLayoutData(BorderLayout.SOUTH);

		final Label onlineLabel = new Label(composite, SWT.NONE);
		onlineLabel.setBackground(SWTResourceManager.getColor(255, 255, 238));
		onlineLabel.setText("online :");

		final Button flushButton = new Button(composite, SWT.NONE);
		flushButton.setText("Flush");

		final Button sendButton = new Button(composite, SWT.NONE);
		sendButton.setText("Send");

		final Button cancelButton = new Button(composite, SWT.NONE);
		cancelButton.setText("Cancel");
		//
		return container;
	}

	/**
	 * Create the actions
	 */
	private void createActions() {

		sendAction = new Action("Send") {
			public void run() {
			}
		};

		exitAction = new Action("Exit") {
			public void run() {
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

		final MenuManager menuManager_1 = new MenuManager("Preferences");
		menuManager.add(menuManager_1);

		final MenuManager menuManager_2 = new MenuManager("Help");
		menuManager.add(menuManager_2);
		return menuManager;
	}

	/**
	 * Create the toolbar manager
	 * @return the toolbar manager
	 */
	protected ToolBarManager createToolBarManager(int style) {
		ToolBarManager toolBarManager = new ToolBarManager(style);

		toolBarManager.add(sendAction);

		toolBarManager.add(new Separator());

		toolBarManager.add(exitAction);
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
	 * Launch the application
	 * @param args
	 */
	public static void main(String args[]) {
		try {
			MainFrame window = new MainFrame();
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
		newShell.setText("SendAndReceiver");
	}

	/**
	 * Return the initial size of the window
	 */
	protected Point getInitialSize() {
		return new Point(400, 300);
	}

}
