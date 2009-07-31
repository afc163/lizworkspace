package editDistance;
/*
 * EditDistanceJApplet.java
 *
 * Created on 2007年9月4日, 下午8:01
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

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

import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.*;

import java.io.*;
/**
 *
 * @author shengyan
 */
public class EditDistanceJAppletOLD extends JApplet 
{
    /** Creates a new instance of EditDistanceJApplet */
    public int appletWidth = 500;
    public int appletHeight = 500;
    
    private JTextArea readTextArea;    
    private EditDistance eDistance;
    private MatrixPanel matrixPanel;
    
    public void update(Graphics g)
    {    
    }
    
    public void paint(Graphics g)
    {
        this.setSize(appletWidth, appletHeight);
        //repaint();
    }

    public void init()
    {
        //从网页中传递参数。。。。。
                ///try
                ///{
                    ///BufferedReader myReader = new BufferedReader(new InputStreamReader(System.in));
                    ///String strOne = myReader.readLine();
                    ///String strTwo = myReader.readLine();
                    ///myReader.close();
                    
                    //FileInputStream in = new FileInputStream(new File("EditDistanceInput.txt"));
                    //BufferedReader myReader = new BufferedReader(new InputStreamReader(in));
                    //String strOne = myReader.readLine();
                    //String strTwo = myReader.readLine();
                    //myReader.close();
                    
                    ///eDistance = new EditDistance(strOne, strTwo);
                    eDistance = new EditDistance("ynnus", "sunny");
                    int rowSize = eDistance.GetsFromSize() + 1;
    	            int colSize = eDistance.GetsToSize() + 1;
                    appletWidth = colSize * MatrixPanel.LENGTH;
                    appletHeight = rowSize * MatrixPanel.LENGTH;
                    
                    /*eDistance.LevenshteinDistance();                   
    	
	            for (int i = 0; i < rowSize; i++)
    	           {
	    	         for (int j = 0; j< colSize; j++)
	    	         {
		    	       System.out.print(eDistance.Get(i, j) + "----");
		          }
	    	          System.out.println();
	            }
	    
	            System.out.println("结果是：" + eDistance.Get(rowSize-1, colSize-1));
                
                    eDistance.traceBackAnother("", "", "", eDistance.GetsFromSize(), eDistance.GetsToSize());              
                    System.out.println(eDistance.result);
                
                
                    System.out.println("跟踪的结果：");
                    for (int i = 0; i < rowSize; i++)
    	           {
	    	          for (int j = 0; j< colSize; j++)
	    	          {
		    	         if (eDistance.Get2(i, j))
                                 {
                                      System.out.print(eDistance.Get(i, j) + "*---");
                                  }
                                  else
                                 {
                                      System.out.print(eDistance.Get(i, j) + "----");
                                  }
		          }
	    	         System.out.println();
	             } */
                ///}
                ///catch (IOException e)
                ///{
                ///    System.out.println(e);
                ///}               
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
		quitButton.addMouseListener(new MouseAdapter() 
                {
			public void mouseClicked(MouseEvent e) 
                        {
				//e.getModifiers();判断MouseEvent的getModifiers()是BUTTON1_MASK（左键），还是BUTTON3_MASK（右键）。   
				//(e.getModifiers()==e.META_MASK)也可以表示右键 
			}
		});
		quitButton.setText("Quit");
		buttonPanel.add(quitButton);

		final JCheckBox standCheckBox = new JCheckBox();
		standCheckBox.setText("Stand");
		buttonPanel.add(standCheckBox);

		matrixPanel = new MatrixPanel();               
		getContentPane().add(matrixPanel, BorderLayout.CENTER);             
    }
    
    class MatrixPanel extends JPanel
    {
        static final int INDEX = 5;
        static final int LENGTH = 20;
        int rowSize = 0;
        int colSize = 0;
        public MatrixPanel()
        {
            setBackground(new Color(240, 255, 255));
            //setLayout(new GridLayout(1, 0));
            rowSize = eDistance.GetsFromSize();
            colSize = eDistance.GetsToSize();
        }
        
        public void paintComponent(Graphics g)
        {
            for (int i = 0; i < rowSize+4; i++)
            {
                g.drawLine(0, LENGTH*i, appletWidth, LENGTH*i);
            }
            for (int j = 0; j < colSize+4; j++)
            {
                g.drawLine(LENGTH*j, 0, LENGTH*j, appletHeight);
            }
            int colpos = LENGTH*2 + INDEX;
            for (int ii = 0; ii < colSize+1; ii++)
            {
                g.drawString(Integer.toString(ii-1), INDEX, colpos);
                if (ii != 0)
                {
                    g.drawString(String.valueOf(eDistance.GetStrTo().charAt(ii-1)), INDEX + LENGTH, colpos);
                }
                colpos += LENGTH;
                
            }
            int rowpos = LENGTH*2 + INDEX;
            for (int jj = 0; jj < rowSize+1; jj++)
            {
                g.drawString(Integer.toString(jj-1), rowpos, INDEX);
                if (jj != 0)
                {
                    g.drawString(String.valueOf(eDistance.GetStrFrom().charAt(jj-1)), rowpos, INDEX + LENGTH);
                }
                rowpos += LENGTH;
            }            
        }
    }
}

