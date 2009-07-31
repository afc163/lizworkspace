import org.eclipse.jface.action.MenuManager;
import org.eclipse.jface.action.StatusLineManager;
import org.eclipse.jface.action.ToolBarManager;
import org.eclipse.jface.viewers.ListViewer;
import org.eclipse.jface.window.ApplicationWindow;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.SashForm;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.widgets.Button;
import swing2swt.layout.FlowLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.List;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;
import com.swtdesigner.SWTResourceManager;
import swing2swt.layout.BorderLayout;


public class MainFram extends ApplicationWindow {

	private List list;
	private Text Write_Text;
	private Text Read_Text;
	/**
	 * Create the application window
	 */
	public MainFram() {
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
		final FillLayout fillLayout = new FillLayout(SWT.VERTICAL);
		fillLayout.spacing = 5;
		fillLayout.marginWidth = 5;
		fillLayout.marginHeight = 5;
		container.setLayout(fillLayout);
		container.setBackground(SWTResourceManager.getColor(255, 255, 255));

		final SashForm sashForm = new SashForm(container, SWT.NONE);

		final Composite composite = new Composite(sashForm, SWT.NONE);
		composite.setLayout(new FillLayout());

		final ListViewer MyListViewer = new ListViewer(composite, SWT.V_SCROLL | SWT.H_SCROLL | SWT.BORDER);
		list = MyListViewer.getList();

		final SashForm sashForm_1 = new SashForm(sashForm, SWT.VERTICAL);

		Read_Text = new Text(sashForm_1, SWT.V_SCROLL | SWT.READ_ONLY | SWT.MULTI | SWT.H_SCROLL | SWT.BORDER);
		Read_Text.setForeground(SWTResourceManager.getColor(0, 64, 0));
		Read_Text.setText("lizzie");
		Read_Text.setFont(SWTResourceManager.getFont("ËÎÌå", 12, SWT.NONE));
		Read_Text.setBackground(SWTResourceManager.getColor(254, 251, 241));

		Write_Text = new Text(sashForm_1, SWT.BORDER);
		Write_Text.setForeground(SWTResourceManager.getColor(0, 64, 128));
		Write_Text.setFont(SWTResourceManager.getFont("·ÂËÎ_GB2312", 12, SWT.NONE));
		Write_Text.setBackground(SWTResourceManager.getColor(238, 247, 255));
		Write_Text.setText("write");
		sashForm_1.setWeights(new int[] {1, 1 });
		sashForm.setWeights(new int[] {1, 1 });
		//
		return container;
	}

	/**
	 * Create the actions
	 */
	private void createActions() {
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
			MainFram window = new MainFram();
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
		newShell.setText("Sender&Receiver");
	}

	/**
	 * Return the initial size of the window
	 */
	protected Point getInitialSize() {
		return new Point(500, 400);
	}

}
