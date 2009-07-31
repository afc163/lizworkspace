/*
 * ShowJApplet.java
 *
 * Created on 2007年9月10日, 下午6:41
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

/**
 *
 * @author shengyan
 */

public class ShowJApplet extends javax.swing.JApplet {
    
    /** Creates a new instance of ShowJApplet */
    public ShowJApplet() 
    {
        super();
    }
   
    public void init()
    {        
		downPanel.setLayout(new BorderLayout());
		downPanel.setBorder(new BevelBorder(BevelBorder.LOWERED));
		downPanel.setBackground(Color.WHITE);
		getContentPane().add(downPanel, BorderLayout.SOUTH);
		
		scrollPane.setBorder(new LineBorder(Color.black, 1, false));
		scrollPane.setBackground(new Color(0, 128, 128));
		scrollPane.setAutoscrolls(true);
		downPanel.add(scrollPane);

		readTextArea = new JTextArea();
		scrollPane.setViewportView(readTextArea);
		readTextArea.setEditable(false);

		readTextArea.setRows((downPanel.getHeight()-buttonPanel.getHeight())/20);//?
		readTextArea.setBackground(new Color(248, 248, 255));
		readTextArea.setForeground(new Color(255, 153, 0));
		readTextArea.setBorder(new LineBorder(new Color(51, 153, 204), 2, true));
		
		downPanel.add(buttonPanel, BorderLayout.SOUTH);
		buttonPanel.setBackground(new Color(255, 250, 250));

		final JButton computeButton = new JButton();
		final JButton resetButton = new JButton();
		final JButton traceButton = new JButton();
		final JButton quitButton = new JButton();
		
		computeButton.addMouseListener(new MouseAdapter()
				{
					public void mouseClicked(MouseEvent e)
					{
						computeButton.setEnabled(false);
						traceButton.setEnabled(true);
						resetButton.setEnabled(true);
						matrixPanel.Compute();
					}
				});
		computeButton.setText("Compute");
		buttonPanel.add(computeButton);

		traceButton.addMouseListener(new MouseAdapter()
		{
			public void mouseClicked(MouseEvent e)
			{
				traceButton.setEnabled(false);
				resetButton.setEnabled(false);
				matrixPanel.readTextArea = readTextArea;
				matrixPanel.Trace();
				resetButton.setEnabled(true);
			}
		});
		traceButton.setText("Trace");
		traceButton.setEnabled(false);
		buttonPanel.add(traceButton);

		resetButton.addMouseListener(new MouseAdapter()
		{
			public void mouseClicked(MouseEvent e)
			{
				computeButton.setEnabled(true);
				traceButton.setEnabled(false);
				resetButton.setEnabled(false);
				matrixPanel.Reset();
			}
		});
		resetButton.setText("Reset");
		resetButton.setEnabled(false);
		buttonPanel.add(resetButton);

		quitButton.addMouseListener(new MouseAdapter() 
                {
			public void mouseClicked(MouseEvent e) 
                        {
				//e.getModifiers();判断MouseEvent的getModifiers()是BUTTON1_MASK（左键），还是BUTTON3_MASK（右键）。   
				//(e.getModifiers()==e.META_MASK)也可以表示右键 
				System.exit(0);
			}
		});
		quitButton.setText("Quit");
		buttonPanel.add(quitButton);

		final JCheckBox standCheckBox = new JCheckBox();
		standCheckBox.setText("Stand");
		buttonPanel.add(standCheckBox);

		//网页中的两个参数传递到这
		try
		{
			String from = getParameter("x");
			String to = getParameter("y");
			if (from == null)
			{
				from = "";
			}
			if (to == null)
			{
				to = "";
			}
			matrixPanel = new MatrixPanel(from, to);
			getContentPane().add(matrixPanel, BorderLayout.CENTER);
			
			appletWidth = matrixPanel.getWidth();//初始化的applet窗口大小
			downPanel.setSize(appletWidth, 100);
	        appletHeight = matrixPanel.getHeight() + downPanel.getHeight();
			setSize(appletWidth, appletHeight);
		}
		catch (IOException e)
		{
			System.out.println(e);
			System.exit(-1);
		}		
    } 

    private JLabel xLabel = new JLabel("x");
    private JLable yLabel = new JLabel("y");
    private javax.
}
