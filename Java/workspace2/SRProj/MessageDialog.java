import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.SashForm;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;
import com.swtdesigner.SWTResourceManager;


public class MessageDialog extends Dialog {

	private Text Write_Text;
	private Text Show_Text;
	private static final int Send_ID = IDialogConstants.CLIENT_ID + 1;
	/**
	 * Create the dialog
	 * @param parentShell
	 */
	public MessageDialog(Shell parentShell) {
		super(parentShell);
	}

	/**
	 * Create contents of the dialog
	 * @param parent
	 */
	protected Control createDialogArea(Composite parent) {
		Composite container = (Composite) super.createDialogArea(parent);
		container.setLayout(new FillLayout(SWT.VERTICAL));

		final SashForm sashForm = new SashForm(container, SWT.VERTICAL);
		sashForm.setBackground(SWTResourceManager.getColor(255, 255, 217));

		Show_Text = new Text(sashForm, SWT.V_SCROLL | SWT.READ_ONLY | SWT.MULTI | SWT.H_SCROLL | SWT.BORDER);
		Show_Text.setEditable(false);
		Show_Text.setBackground(SWTResourceManager.getColor(243, 254, 231));

		Write_Text = new Text(sashForm, SWT.V_SCROLL | SWT.MULTI | SWT.H_SCROLL | SWT.BORDER);
		sashForm.setWeights(new int[] {145, 75 });
		//
		return container;
	}

	/**
	 * Create contents of the button bar
	 * @param parent
	 */
	protected void createButtonsForButtonBar(Composite parent) {
		createButton(parent, IDialogConstants.CANCEL_ID, "取消",
				true);
		createButton(parent, Send_ID,
				"发送", false);
	}

	/**
	 * Return the initial size of the dialog
	 */
	protected Point getInitialSize() {
		return new Point(300, 300);
	}
	protected void configureShell(Shell newShell) {
		super.configureShell(newShell);
		newShell.setText("对话框");
	}
	
	public void SetWriteText(String line)
	{
		Write_Text.append(line + '\n');
	}

}
