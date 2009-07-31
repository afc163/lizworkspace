import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JApplet;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.border.LineBorder;


public class tempApplet extends JApplet {

	private JTextArea readTextArea;
	/**
	 * Create the applet
	 */
	public tempApplet() {
		super();

		final JPanel Panel = new JPanel();
		final BorderLayout borderLayout = new BorderLayout();
		Panel.setLayout(borderLayout);
		getContentPane().add(Panel, BorderLayout.SOUTH);

		final JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBorder(new LineBorder(Color.black, 1, false));
		scrollPane.setAutoscrolls(true);
		Panel.add(scrollPane);

		readTextArea = new JTextArea();
		scrollPane.setViewportView(readTextArea);
		readTextArea.setRows(5);
		readTextArea.setBackground(new Color(248, 248, 255));
		readTextArea.setForeground(new Color(255, 153, 0));
		readTextArea.setBorder(new LineBorder(new Color(51, 153, 204), 2, true));

		final JPanel buttonPanel = new JPanel();
		Panel.add(buttonPanel, BorderLayout.SOUTH);

		final JButton computeButton = new JButton();
		computeButton.setText("Compute");
		buttonPanel.add(computeButton);

		final JButton traceButton = new JButton();
		traceButton.setText("Trace");
		buttonPanel.add(traceButton);

		final JButton resetButton = new JButton();
		resetButton.setText("Reset");
		buttonPanel.add(resetButton);

		final JButton quitButton = new JButton();
		quitButton.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				//e.getModifiers();判断MouseEvent的getModifiers()是BUTTON1_MASK（左键），还是BUTTON3_MASK（右键）。   
				//(e.getModifiers()==e.META_MASK)也可以表示右键 
			}
		});
		quitButton.setText("Quit");
		buttonPanel.add(quitButton);

		final JCheckBox standCheckBox = new JCheckBox();
		standCheckBox.setText("Stand");
		buttonPanel.add(standCheckBox);

		final JPanel matrixPanel = new JPanel();
		matrixPanel.setBackground(new Color(240, 255, 255));
		matrixPanel.setLayout(new GridLayout(1, 0));
		getContentPane().add(matrixPanel, BorderLayout.CENTER);
		//
	}

}
