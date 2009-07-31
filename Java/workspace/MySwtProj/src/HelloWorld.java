import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.SashForm;
import org.eclipse.swt.custom.StackLayout;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.List;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;
import com.swtdesigner.SWTResourceManager;


public class HelloWorld {

	private static Text text;
	private static List list;
	/**
	 * Launch the application
	 * @param args
	 */
	public static void main(String[] args) {
		final Display display = Display.getDefault();
		final Shell hiShell = new Shell();
		hiShell.setLayout(new FormLayout());
		hiShell.setSize(500, 375);
		hiShell.setText("Hi!");
		//

		hiShell.open();

		final Composite composite = new Composite(hiShell, SWT.NONE);
		composite.setLayout(new GridLayout());
		final FormData fd_composite = new FormData();
		fd_composite.top = new FormAttachment(0, 5);
		fd_composite.right = new FormAttachment(100, -5);
		fd_composite.bottom = new FormAttachment(100, -5);
		fd_composite.left = new FormAttachment(0, 5);
		composite.setLayoutData(fd_composite);

		final SashForm sashForm = new SashForm(composite, SWT.HORIZONTAL);

		list = new List(sashForm, SWT.NONE);
		final GridData gd_sashForm = new GridData(SWT.FILL, SWT.FILL, false, false);
		gd_sashForm.heightHint = 280;
		gd_sashForm.widthHint = 473;
		sashForm.setLayoutData(gd_sashForm);

		final Composite composite_2 = new Composite(sashForm, SWT.NONE);

		final Label label = new Label(composite_2, SWT.NONE);
		label.setBounds(5, 5, 96, 24);
		label.setForeground(SWTResourceManager.getColor(0, 128, 0));
		label.setFont(SWTResourceManager.getFont("楷体_GB2312", 18, SWT.NONE));
		label.setText("个人资料");

		final Label label_1 = new Label(composite_2, SWT.NONE);
		label_1.setBounds(5, 59, 36, 13);
		label_1.setText("号码：");

		text = new Text(composite_2, SWT.BORDER);
		text.setBounds(46, 56, 71, 19);

		final Canvas canvas = new Canvas(composite_2, SWT.NONE);
		canvas.setBounds(122, 34, 64, 64);
		canvas.setBackground(SWTResourceManager.getColor(255, 255, 255));
		sashForm.setWeights(new int[] {135, 332 });

		final Composite composite_1 = new Composite(composite, SWT.NONE);
		final RowLayout rowLayout = new RowLayout(SWT.VERTICAL);
		rowLayout.spacing = 30;
		composite_1.setLayout(rowLayout);
		final GridData gd_composite_1 = new GridData(SWT.RIGHT, SWT.BOTTOM, false, false);
		gd_composite_1.widthHint = 182;
		gd_composite_1.heightHint = 35;
		composite_1.setLayoutData(gd_composite_1);

		final Button button = new Button(composite_1, SWT.NONE);
		button.setText("确定");

		final Button button_1 = new Button(composite_1, SWT.NONE);
		button_1.setText("取消");

		final Button button_2 = new Button(composite_1, SWT.NONE);
		button_2.setText("应用");
		hiShell.layout();
		while (!hiShell.isDisposed()) {
			if (!display.readAndDispatch())
				display.sleep();
		}
	}

}
