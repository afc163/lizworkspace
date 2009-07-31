import javax.swing.JFrame;


public class tempApplicationWindow {

	private JFrame frame;

	/**
	 * Launch the application
	 * @param args
	 */
	public static void main(String args[]) {
		try {
			tempApplicationWindow window = new tempApplicationWindow();
			window.frame.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the application
	 */
	public tempApplicationWindow() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 500, 375);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

}
