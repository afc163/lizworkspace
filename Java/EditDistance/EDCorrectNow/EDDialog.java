import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.border.BevelBorder;
import javax.swing.border.LineBorder;

import java.util.Timer;
import java.util.TimerTask;

class EDDialog extends JDialog {

	/**
	 * Launch the application
	 * @param args
	 */
    public int dialogWidth = 500;
    public int dialogHeight = 500;
    
    private JTextArea readTextArea;    
    private MatrixPanel matrixPanel;
    
    final JPanel downPanel = new JPanel();
    final JScrollPane scrollPane = new JScrollPane();
    final JPanel buttonPanel = new JPanel();
    
    String stringFrom = "sunny";
    String stringTo = "ynnus";
    
	public static void main(String args[]) {
		try {
			EDDialog dialog = new EDDialog("sunny0", "ynnus1");
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
	public EDDialog(String sf, String st) {
		super();
		setTitle("EditDistance");
		setBounds(100, 100, 500, 375);
		stringFrom = sf;
		stringTo = st;
		init();
	}
   
    public void paint(Graphics g)//?
    {
    	super.paint(g);
    	matrixPanel.setWidth(getWidth());
    	downPanel.setSize(getWidth(), getHeight()-matrixPanel.getHeight());
    	readTextArea.setRows((downPanel.getHeight()-buttonPanel.getHeight())/30);
    	System.out.println("TextAreaRows:"+readTextArea.getRows());        
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
				//窗口关闭
			}
		});
		quitButton.setText("Quit");
		buttonPanel.add(quitButton);

		final JCheckBox standCheckBox = new JCheckBox();
		standCheckBox.setText("Stand");
		buttonPanel.add(standCheckBox);

		try
		{
			matrixPanel = new MatrixPanel(stringFrom, stringTo);
			getContentPane().add(matrixPanel, BorderLayout.CENTER);
			
			dialogWidth = matrixPanel.getWidth();//初始化的applet窗口大小
			downPanel.setSize(dialogWidth, 100);
	        dialogHeight = matrixPanel.getHeight() + downPanel.getHeight();
			setSize(dialogWidth, dialogHeight);
		}
		catch (IOException e)
		{
			System.out.println(e);
			System.exit(-1);
		}		
    } 
}

class MatrixPanel extends JPanel implements Runnable
{
	private boolean isShowOne = true;
	private boolean isShowTwo = false;
	private boolean isShowThree = false;
	
    int indexWidth = 5;
    int indexHeight = 5;
    int panelWidth = 500;
    int panelHeight = 500;
    int everyWidth = 30;
    int everyHeight = 30;
    int rowNum = 0;
    int colNum = 0;
    
    String strFrom = null;
    String strTo = null;
    private int strFromSize;
    private int strToSize;   
    private int [][] matrix;                            //结果矩阵
    private boolean [][] matrix2;                      //标记矩阵   现在其实可以省略
    String result = "";
    int resultNum = 0;
    Graphics myG;
    Vector OLVector = new Vector(100);
    JTextArea readTextArea;

    Timer timer = new Timer();

    public MatrixPanel(String sFrom, String sTo) throws IOException
    {
    	strFrom = sFrom;
    	strTo = sTo;
    	strFromSize = strFrom.trim().length();
        strToSize = strTo.trim().length();
    		
    	matrix = new int [strFromSize + 1][strToSize + 1];
        initMatrix2();
        
    	rowNum = strFromSize+3;
        colNum = strToSize+3;
        panelWidth = rowNum*everyWidth;
        panelHeight = colNum*everyHeight;
        
        everyWidth = panelWidth / colNum;
        everyHeight = panelHeight / rowNum;
        indexWidth = everyWidth / 2;
        indexHeight = everyHeight / 2;
        setBackground(new Color(240, 255, 255));
    }     
    public void Compute()
    {
    	isShowTwo = true;
    	LevenshteinDistance();
    	repaint();
    }
    
    public void Trace()
    {
    	try
    	{
    		isShowThree = true;
    		traceBackAnother("", "", "", strFromSize, strToSize);
    		System.out.println("跟踪的结果：");
            for (int i = 0; i < rowNum-2; i++)
           {
	          for (int j = 0; j< colNum-2; j++)
	          {
    	         if (Get2(i, j))
                         {
                              System.out.print(Get(i, j) + "*---");
                          }
                          else
                         {
                              System.out.print(Get(i, j) + "----");
                          }
               }
	         System.out.println();
           }
repaint();
    	}
    	catch (IOException e)
    	{
    		System.out.println(e);
    	}
    }
    public void Reset()
    {
    	isShowOne = true;
    	isShowTwo = false;
    	isShowThree = false;
    	if (readTextArea != null)
	{
		readTextArea.setText("");
	}
    	repaint();
    }
    
    public int getWidth()
    {
    	return panelWidth;
    }
    
    public int getHeight()
    {
    	return panelHeight;
    }
    
    public void setWidth(int w)
    {
    	panelWidth = w;
    }
    
    public void paintComponent(Graphics g)
    {
    	super.paintComponent(g);
    	myG = g;
    	everyWidth = panelWidth / colNum;
        everyHeight = panelHeight / rowNum;
        indexWidth = everyWidth / 2;
        indexHeight = everyHeight / 2;
    	
    	if (isShowOne)
    	{
    		ShowOne(g);
    	}
    	if (isShowTwo)
    	{
    		ShowTwo(g);
    	}
    	if (isShowThree)
    	{
    		ShowThree(g);
    	}
    }
    
    private void ShowOne(Graphics g)
    {
    	for (int i = 0; i <= rowNum; i++)  //draw rowline
        {
            g.drawLine(0, everyHeight*i, panelWidth, everyHeight*i);
        }
        for (int j = 0; j <= colNum; j++)  //draw colline
        {
            g.drawLine(everyWidth*j, 0, everyWidth*j, panelHeight);
        }

        int xpos = everyWidth*2 + indexWidth;
        for (int ii = 0; ii < colNum-2; ii++)  //draw col number and stringTo
        {
            g.drawString(Integer.toString(ii-1), xpos, indexHeight);
            if (ii != 0)
            {
                g.drawString(String.valueOf(strTo.charAt(ii-1)), xpos, indexHeight+everyHeight);
            }
            xpos += everyWidth;                
        }
        int ypos = everyHeight*2 + indexHeight;
        for (int jj = 0; jj < rowNum-2; jj++)  //draw row number and stringFrom
        {
            g.drawString(Integer.toString(jj-1), indexWidth, ypos);
            if (jj != 0)
            {
                g.drawString(String.valueOf(strFrom.charAt(jj-1)), indexWidth+everyWidth, ypos);
            }
            ypos += everyHeight;
        }            
    }
    
    private void ShowTwo(Graphics g)
    {
        for (int i = 0; i < rowNum-2; i++)  
        {
        	for (int j = 0; j < colNum-2; j++)
        	{
        		//加上颜色      		
        		int oneElement = Get(i, j);
        		g.setColor(new Color(180+oneElement*5, oneElement*10, oneElement*20));
        		g.drawString(String.valueOf(oneElement), (j+2)*everyWidth + everyWidth/2 - 5, (i+2)*everyHeight + everyHeight/2 + 5);
        		g.setColor(new Color(0, 0 , 0));
        	}
        }
    }
    
    private void ShowThree(Graphics g)
    {
        int OLNum = OLVector.size();
    	if (OLNum != 0)
    	{
    		for (int index=0; index < OLNum; index++)
    		{
    			OvelAndLine temp = (OvelAndLine)(OLVector.elementAt(index));
    			if (temp.type)
    			{
    				int i = temp.fi;
    				int j = temp.fj;
    				myG.drawOval(everyWidth*(i+2)+everyWidth/4, everyHeight*(j+2)+everyHeight/4, everyWidth / 2, everyHeight / 2);
    			}
    			else
    			{
    				int fi = temp.fi;
    				int fj = temp.fj;
    				int ti = temp.ti;
    				int tj = temp.tj;
    				if ((fi == ti) && (fj > tj) )
    		    	{
    		    		//横向的线
    		    		myG.drawLine(everyWidth*(fj+2) + everyWidth/4, everyHeight*(fi+2) + everyHeight/2, everyWidth*(tj+2) + 3*everyWidth/4, everyHeight*(ti+2) + everyHeight/2);
    		    	}
    		    	if ((fi > ti) && (fj == tj))
    		    	{
    		    		//竖向的线
    		    		myG.drawLine(everyWidth*(fj+2) + everyWidth/2, everyHeight*(fi+2) + everyHeight/4, everyWidth*(tj+2) + everyWidth/2, everyHeight*(ti+2) + 3*everyHeight/4);
    		    	}
    		    	if ((fi > ti) && (fj > tj))
    		    	{
    		    		//斜上的线
    		    		myG.drawLine(everyWidth*(fj+2) + everyWidth/4, everyHeight*(fi+2) + everyHeight/4, everyWidth*(tj+2) + 3*everyWidth/4, everyHeight*(ti+2) + 3*everyHeight/4);
    		    	}
    			}
    		}
    	}
    }

    public void run()
    {

	timer.schedule(new TimerTask() 
    	{
    		public void run() 
    		{
    			System.out.println("time...");
    			repaint();
timer.cancel();
    		}
    	}, 500);
    }

    public void LevenshteinDistance()            //Compute Levenshtein Distance
    {
        for (int i = 0; i <= strFromSize; i++)      //initialize matrix
        {
            matrix[i][0] = i;
        }
    	for (int j = 1; j <= strToSize; j++)
    	{
            matrix[0][j] = j;
        }
 	    //compute matrix: 主要思想是有点递推的感觉，假设之前的已经计算出来之前的min，加入当前元素时的几种变化情况
    	for (int i = 1; i <= strFromSize; i++)     
    	{
    		for (int j = 1; j<= strToSize; j++)
    		{
    			int rest = 1;              //strFrom[i-1] == strTo[j-1]??匹配
    			if (strFrom.charAt(i-1) == strTo.charAt(j-1))
    			{
    			    rest = 0;	
    			}
			//min{m[i-1,j-1]+rest, m[i-1,j]+1, m[i, j-1]+1}
			int min = matrix[i-1][j-1] + rest;
			if (min > matrix[i-1][j] + 1)
		     	{
                            min = matrix[i-1][j] + 1;
		    	}
		    	if (min > matrix[i][j-1] + 1)
		    	{
                            min = matrix[i][j-1] + 1;
		    	}
		    	matrix[i][j] = min;
	    	}
    	}
    }
    
    public int Get(int row, int col)
    {
        if ((row < 0 || row > strFromSize) && (col < 0 || col > strToSize))
        {
            return -1;
        }
        return matrix[row][col];
    }
	
    public int GetsFromSize()
    {
        return strFromSize;
    }
	
    public int GetsToSize()
    {
        return strToSize;
    }
    
    public String GetStrFrom()
    {
        return strFrom;
    }
    
    public String GetStrTo()
    {
        return strTo;
    }
    
    private void delay(int time)
    {
    	for (int i=0; i < time; i++)
    	{
    		for (int j=0; j < time; j++)
    		{   			
    		}
    	}
    }
    
    private void addOvel(int i, int j)
    {    	
    	OvelAndLine oneOvel = new OvelAndLine(true, j, i, 0, 0);
    	for (int index=0; index < OLVector.size(); index++)
    	{
    		OvelAndLine t = (OvelAndLine)OLVector.elementAt(index);
    		if (t.type && t.fi==j && t.fj==i )
        	{
        		return;
        	}
    	}    	
    	OLVector.add(oneOvel);
    	System.out.println("OVEL: "+ i + j);
    }
    
    private void addLine(int fi, int fj, int ti, int tj)
    {
    	OvelAndLine oneLine = new OvelAndLine(false, fi, fj, ti, tj);
    	for (int index=0; index < OLVector.size(); index++)
    	{
    		OvelAndLine t = (OvelAndLine)OLVector.elementAt(index);
    		if (!t.type && t.fi==fi && t.fj==fj && t.ti==ti && t.tj==tj)
        	{
        		return;
        	}
    	} 
    	OLVector.add(oneLine);
    	System.out.println("LINE: "+fi+fj+ti+tj);
    }
    
    public void traceBackAnother(String row1, String row2, String row3, int i, int j) throws IOException
    {
        if (i > 0 && j > 0)
        {
            int diag = matrix[i-1][j-1];
            char diagCh = '|';
            if (strFrom.charAt(i-1) != strTo.charAt(j-1))
            {
                diag++;
                diagCh = ' ';
            }
            matrix2[i][j] = true;
            addOvel(i, j);
            if (matrix[i][j] == diag)
            {
                //change or match                
                matrix2[i-1][j-1] = true;
                addOvel(i, j);
                addOvel(i-1, j-1);
                addLine(i, j, i-1, j-1);
                traceBackAnother(strFrom.charAt(i-1)+row1, diagCh+row2, strTo.charAt(j-1)+row3, i-1, j-1);
            }
            if (matrix[i][j] == matrix[i-1][j] +1)  //up element
            {     //delete
                matrix2[i-1][j] = true;
                addOvel(i-1, j);
                addLine(i, j, i-1, j);
                traceBackAnother(strFrom.charAt(i-1)+row1, ' '+row2, '-'+row3, i-1, j);
            }
            if (matrix[i][j] == matrix[i][j-1] +1)  //left element
            {      //insert——其实delete和insert差不多，只是相对的字符串不同
                matrix2[i][j-1] = true;
                addOvel(i, j-1);
                addLine(i, j, i, j-1);
                traceBackAnother('-'+row1, ' '+row2, strTo.charAt(j-1)+row3, i, j-1);
            }  
        }
        else if (i > 0)
        {
            matrix2[i-1][j] = true;
            addOvel(i-1, j);
            addLine(i, j, i-1, j);
            traceBackAnother(strFrom.charAt(i-1)+row1, ' '+row2, '-'+row3, i-1, j);
        }
        else if (j > 0)
        {
            matrix2[i][j-1] = true;
            addOvel(i, j-1);
            addLine(i, j, i, j-1);
            traceBackAnother('-'+row1, ' '+row2, strTo.charAt(j-1)+row3, i, j-1);
        }
        else
        {
            matrix2[i][j] = true;
            addOvel(i, j);
            //result = row1+"\n"+row2+"\n"+row3+"\n\n";
            System.out.println(row1+"\n"+row2+"\n"+row3);
            readTextArea.append("第"+(++resultNum)+"个：\n"+ row1+"\n"+row2+"\n"+row3+"\n");
        }
    }
  
    public void traceBack(String row1, String row2, String row3, int i, int j)
    {    //跟踪结果得到matrix2,用递归实现
        if (i > 0 && j > 0)
        {
            int diag = matrix[i-1][j-1];
            char diagCh = '|';//match sign
            if ( strFrom.charAt(i-1) != strTo.charAt(j-1))
            {
                diag++;
                diagCh = ' ';
            }
            if (matrix[i][j] == diag)
            {    //change or match
                matrix2[i][j] = true;
                matrix2[i-1][j-1] = true;
                traceBack(strFrom.charAt(i-1)+row1, diagCh+row2, strTo.charAt(j-1)+row3, i-1, j-1);
            }
            else if (matrix[i][j] == matrix[i-1][j] +1)
            {     //delete
                matrix2[i-1][j] = true;
                traceBack(strFrom.charAt(i-1)+row1, ' '+row2, '-'+row3, i-1, j);
            }
            else
            {      //insert——其实delete和insert差不多，只是相对的字符串不同
                matrix2[i][j-1] = true;
                traceBack('-'+row1, ' '+row2, strTo.charAt(j-1)+row3, i, j-1);
            }  
        }
        else if (i > 0)
        {
            matrix2[i-1][j] = true;
            traceBack(strFrom.charAt(i-1)+row1, ' '+row2, '-'+row3, i-1, j);
        }
        else if (j > 0)
        {
            matrix2[i][j-1] = true;
            traceBack('-'+row1, ' '+row2, strTo.charAt(j-1)+row3, i, j-1);
        }
        else
        {
            matrix2[i][j] = true;
            result = row1+"\n"+row2+"\n"+row3;
        }
    }
    
    public void initMatrix2()
    {
        matrix2 = new boolean [strFromSize + 1][strToSize + 1];
        for (int i = 0; i <= strFromSize; i++)
            for (int j = 0; j <= strToSize; j++)
            {
                  matrix2[i][j] = false;
            }
    }
    
    public boolean Get2(int row, int col)
    {
        return matrix2[row][col];
    }
}

class OvelAndLine
{
	boolean type = true; //true:Ovel;false:Line
	int fi = 0;
	int fj = 0;
	int ti = 0;
	int tj = 0;
	
	public OvelAndLine(boolean tp, int i, int j, int ii, int jj)
	{
		type = tp;
		fi = i;
		fj = j;
		ti = ii;
		tj = jj;
	}
}
