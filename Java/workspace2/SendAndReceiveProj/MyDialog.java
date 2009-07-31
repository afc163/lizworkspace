import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.*;
import org.eclipse.swt.events.*;

public class MyDialog {

	public static void main(String [] args)
	{
		Display display = new Display( );		
		final Shell shell = new Shell(display);		
		shell.setSize(300, 200);
		shell.setText("main");

		final Button opener = new Button(shell, SWT.PUSH);
		opener.setText("Click Me");
		opener.setBounds(20, 20, 50, 25);

		final Shell dialog = new Shell(shell, SWT.APPLICATION_MODAL | SWT.DIALOG_TRIM);
		dialog.setText("dialog");
		dialog.setBounds(10,10,50,60);
		dialog.addDisposeListener(new DisposeListener(){
			public void widgetDisposed(DisposeEvent e){
				opener.setText("dialog is disposed");
				}
			});

		Listener openerListener = new Listener() {
			public void handleEvent(Event event) {
				dialog.open();
				}
			};
		opener.addListener(SWT.Selection, openerListener);

		shell.open();
		while(!dialog.isDisposed()) 
		{
			if(!display.readAndDispatch()) display.sleep();
		}
		
		while (!shell.isDisposed()) 
		{
			if (!display.readAndDispatch())
			{
				display.sleep();
			}
		}
		display.dispose();
	}
}
