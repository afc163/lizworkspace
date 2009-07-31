package editdistance;

import java.awt.SystemColor;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JApplet;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JTextField;

public class ShowJApplet extends JApplet {

	private JTextField textField_x;
	private JTextField textField_y;
	final JButton button = new JButton();
	/**
	 * Create the applet
	 */
	public ShowJApplet() {
		super();
		getContentPane().setBackground(SystemColor.window);
		getContentPane().setLayout(null);

		final JLabel xLabel = new JLabel();
		xLabel.setBounds(40, 20, 18, 30);
		getContentPane().add(xLabel);
		xLabel.setText("x: ");

		textField_x = new JTextField();
		textField_x.setBounds(64, 20, 120, 30);
		getContentPane().add(textField_x);

		final JLabel yLabel = new JLabel();
		yLabel.setBounds(40, 56, 18, 30);
		getContentPane().add(yLabel);
		yLabel.setText("y: ");

		textField_y = new JTextField();
		textField_y.setBounds(64, 56, 120, 30);
		getContentPane().add(textField_y);
		
		button.setBounds(64, 92, 120, 30);
		button.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				//´ò¿ªEditDistanceJApplet
				EDDialog myDialog = new EDDialog(textField_x.getText(), textField_y.getText());
				myDialog.show();
			}
		});
		getContentPane().add(button);

		final JLabel label_inform = new JLabel();
		label_inform.setText("..........................");
		label_inform.setBounds(64, 128, 120, 23);
		getContentPane().add(label_inform);
		//
	}
	
	public void init()
	{
		String buttonText = getParameter("buttonText");
		if (buttonText == null)
		{
			buttonText = "";
		}
		button.setText(buttonText);
		String textFieldx = getParameter("x");
		if (textFieldx == null)
		{
			textFieldx = "";
		}
		textField_x.setText(textFieldx);
		String textFieldy = getParameter("y");
		if (textFieldy == null)
		{
			textFieldy = "";
		}
		textField_y.setText(textFieldy);
	}
}

