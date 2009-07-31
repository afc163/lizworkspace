import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JDialog;


public class tempJDialog extends JDialog {

	/**
	 * Launch the application
	 * @param args
	 */
	public static void main(String args[]) {
		try {
			tempJDialog dialog = new tempJDialog();
			dialog.addWindowListener(new WindowAdapter() {
				public void windowClosing(WindowEvent e) {
					System.exit(0);
				}
			});
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog
	 */
	public tempJDialog() {
		super();
		setBounds(100, 100, 500, 375);
		//
	}

}
