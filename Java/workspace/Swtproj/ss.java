import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;


public class ss {

	public static void main(String[] args) {
		final Display display = new Display();
		final Shell shell = new Shell();
		shell.setLayout(new FillLayout(SWT.HORIZONTAL));
		shell.setText("FillLayout test");
		{
			final Button button = new Button(shell, SWT.NONE);
			button.setText("button");
		}
		{
			final Group group = new Group(shell, SWT.NONE);
			group.setLayout(new FillLayout(SWT.VERTICAL));
			group.setText("Group");
			{
				final Button button = new Button(group, SWT.NONE);
				button.setText("button 1");
			}
			{
				final Button button = new Button(group, SWT.NONE);
				button.setText("button 2");
			}
			{
				final Button button = new Button(group, SWT.NONE);
				button.setText("button 3");
			}
		}
		{
			final Text text = new Text(shell, SWT.BORDER | SWT.MULTI);
			text.setText("text");
		}
		shell.open();
		shell.layout();
		while (!shell.isDisposed()) {
			if (!display.readAndDispatch())
				display.sleep();
		}
	}

}
