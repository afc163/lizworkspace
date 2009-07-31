package mouseDraw;

import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.*;

public class MouseDraw {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		MouseFrame frame = new MouseFrame();
		
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE );
		frame.setVisible(true);
	}

}

class MouseFrame extends JFrame
{
	public MouseFrame()
	{
		setTitle("利用鼠标画图");
		setSize(WIDTH, HEIGHT);
		
		MousePanel mPanel = new MousePanel();
		Container contentPane = getContentPane();
		contentPane.add(mPanel);
	}
	
	public static final int WIDTH = 600;
	public static final int HEIGHT = 500;
}

class MousePanel extends JPanel
{
	public MousePanel()
	{
		faces = new ArrayList();
		facetypes =new ArrayList();
		current = null;
		//String imageName = "smile.gif";
		smileimg =Toolkit.getDefaultToolkit().getImage("smile.gif");
		cryimg = Toolkit.getDefaultToolkit().getImage("cry.gif");
		
		MediaTracker mtracker = new MediaTracker(this);
		mtracker.addImage(smileimg, 0);
		mtracker.addImage(cryimg, 1);
		try
		{
			mtracker.waitForID(0);
			mtracker.waitForID(1);
		}
		catch (InterruptedException exception)
		{
			JOptionPane.showMessageDialog(this, "mtracker " + exception.getMessage());
			System.exit(0);
		}
		
		imageWidth = smileimg.getWidth(this);
		imageHeight = smileimg.getHeight(this);
		
		addMouseListener(new MouseHandler());
		addMouseMotionListener(new MouseMotionHandler());
	}
	
	public void paintComponent(Graphics g)
	{
		super.paintComponent(g);
		
		String text = "鼠标指针位置：" + mousex + ": " + mousey;
		g.drawString(text, 10, 10);
		
		for (int i = 0; i < faces.size(); i++)
		{
			int x = (int)((Rectangle)faces.get(i)).getX();
			int y = (int)((Rectangle)faces.get(i)).getY();
			
			text = (String)(facetypes.get(i));
			
			if (text.equals("smile"))
			{
				g.drawImage(smileimg, x, y, null);
			}
			else
			{
				g.drawImage(cryimg, x, y, null);
			}
		}
	}
	
	public Rectangle search(Point pt)
	{
		for (int i = 0; i < faces.size(); i++)
		{
			Rectangle rect = (Rectangle)faces.get(i);
			if (rect.contains(pt))
			{
				return rect;
			}
		}
		return null;
	}
	
	public void add(Point pt)
	{
		int x = (int)pt.getX();
		int y = (int)pt.getY();
		
		current = new Rectangle(x - imageWidth / 2, y - imageHeight / 2, imageWidth, imageHeight);
		faces.add(current);
		facetypes.add(facetype);
		
		repaint();
	}
	
	public void modify(Rectangle rect)
	{
		if (rect == null) return;
		
		int index = faces.indexOf(rect);
		String text = (String)(facetypes.get(index));
		
		if (text.equals("smile"))
		{
			facetypes.set(index, "cry");
		}
		else
		{
			facetypes.set(index, "smile");
		}
		repaint();
	}
	
	public void delete(Rectangle rect)
	{
		if (rect == null) return;
		
		int index = faces.indexOf(rect);
		faces.remove(index);
		facetypes.remove(index);
		
		repaint();
	}
	
	private ArrayList faces;
	private ArrayList facetypes;
	private Rectangle current;
	private Image smileimg;
	private Image cryimg;
	private int imageWidth;
	private int imageHeight;
	private String facetype;
	private int mousex;
	private int mousey;
	
	private class MouseHandler extends MouseAdapter
	{
		public void mouseClicked(MouseEvent event)
		{
			mousex = event.getX();
			mousey = event.getY();
			
			current = search(event.getPoint());
			if (current == null)
			{
				if (event.getButton() == MouseEvent.BUTTON1)
				{
					facetype = "smile";
					add(event.getPoint());
				}
			}
			else
			{
				if (event.getButton() == MouseEvent.BUTTON1)
				{
					modify(current);
				}
				else if (event.getButton() == MouseEvent.BUTTON3)
				{
					delete(current);
				}
			}
		}
		
	}
	
	private class MouseMotionHandler implements MouseMotionListener
	{
		public void mouseMoved(MouseEvent event)
		{
			mousex = event.getX();
			mousey = event.getY();
			
			repaint();
		}
		
		public void mouseDragged(MouseEvent event)
		{
			mousex = event.getX();
			mousey = event.getY();
			
			current.setFrame(mousex - imageWidth / 2, mousey - imageHeight / 2, imageWidth, imageHeight);
			
			repaint();
		}
	}
}