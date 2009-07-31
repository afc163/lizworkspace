import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.ComponentOrientation;
import java.awt.Dimension;
import java.awt.FlowLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JSplitPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SpringLayout;
import javax.swing.border.BevelBorder;
import javax.swing.border.LineBorder;


public class Eframe {

	private JTextArea textArea_1;
	private JTextArea textArea;
	private JList list;
	private SpringLayout springLayout;
	private JFrame frame;

	/**
	 * Launch the application
	 * @param args
	 */
	public static void main(String args[]) {
		try {
			Eframe window = new Eframe();
			window.frame.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the application
	 */
	public Eframe() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setSize(new Dimension(500, 400));
		frame.setResizable(false);
		frame.setTitle("SR");
		springLayout = new SpringLayout();
		frame.getContentPane().setLayout(springLayout);
		frame.setBounds(100, 100, 500, 375);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		final JPanel panel = new JPanel();
		panel.setLayout(new BorderLayout());
		frame.getContentPane().add(panel);
		springLayout.putConstraint(SpringLayout.SOUTH, panel, 294, SpringLayout.NORTH, frame.getContentPane());
		springLayout.putConstraint(SpringLayout.NORTH, panel, 5, SpringLayout.NORTH, frame.getContentPane());
		springLayout.putConstraint(SpringLayout.EAST, panel, 175, SpringLayout.WEST, frame.getContentPane());
		springLayout.putConstraint(SpringLayout.WEST, panel, 5, SpringLayout.WEST, frame.getContentPane());

		final JPanel panel_1 = new JPanel();
		panel_1.setLayout(new BorderLayout());
		frame.getContentPane().add(panel_1);
		springLayout.putConstraint(SpringLayout.EAST, panel_1, 487, SpringLayout.WEST, frame.getContentPane());
		springLayout.putConstraint(SpringLayout.WEST, panel_1, 5, SpringLayout.EAST, panel);
		springLayout.putConstraint(SpringLayout.SOUTH, panel_1, 188, SpringLayout.NORTH, frame.getContentPane());
		springLayout.putConstraint(SpringLayout.NORTH, panel_1, 5, SpringLayout.NORTH, frame.getContentPane());

		final JPanel panel_2 = new JPanel();
		panel_2.setLayout(new BorderLayout());
		frame.getContentPane().add(panel_2);
		springLayout.putConstraint(SpringLayout.SOUTH, panel_2, 0, SpringLayout.SOUTH, panel);
		springLayout.putConstraint(SpringLayout.NORTH, panel_2, 5, SpringLayout.SOUTH, panel_1);
		springLayout.putConstraint(SpringLayout.EAST, panel_2, 0, SpringLayout.EAST, panel_1);
		springLayout.putConstraint(SpringLayout.WEST, panel_2, 0, SpringLayout.WEST, panel_1);

		textArea = new JTextArea();
		textArea.setBorder(new LineBorder(Color.GRAY, 1, false));
		textArea.setEditable(false);
		panel_1.add(textArea, BorderLayout.CENTER);

		final JPanel panel_3 = new JPanel();
		final FlowLayout flowLayout = new FlowLayout();
		flowLayout.setAlignment(FlowLayout.TRAILING);
		panel_3.setLayout(flowLayout);
		frame.getContentPane().add(panel_3);
		springLayout.putConstraint(SpringLayout.SOUTH, panel_3, 338, SpringLayout.NORTH, frame.getContentPane());
		springLayout.putConstraint(SpringLayout.NORTH, panel_3, 5, SpringLayout.SOUTH, panel);
		springLayout.putConstraint(SpringLayout.EAST, panel_3, 0, SpringLayout.EAST, panel_2);

		textArea_1 = new JTextArea();
		textArea_1.setBorder(new LineBorder(Color.GRAY, 1, false));
		panel_2.add(textArea_1, BorderLayout.CENTER);
		springLayout.putConstraint(SpringLayout.WEST, panel_3, 0, SpringLayout.WEST, panel);

		list = new JList();
		list.setBorder(new LineBorder(Color.GRAY, 1, false));
		panel.add(list, BorderLayout.CENTER);

		final JButton Send_Button = new JButton();
		Send_Button.setSelected(true);
		Send_Button.setText("·¢ËÍ(E)");
		panel_3.add(Send_Button);

		final JButton Cancel_Button = new JButton();
		Cancel_Button.setText("È¡Ïû(C)");
		panel_3.add(Cancel_Button);
	}

}
