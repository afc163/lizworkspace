import java.awt.*;
import java.awt.event.*;

public class FrameDemo
{
	public static void main(String [] args)
	{
		Frame f = new Frame("我的第一个窗口程序...");
		
		f.setBounds(0, 0, 400, 400);
		f.setBackground(Color.lightGray);
		f.addWindowListener(new WindowAdapter()
												{
													public void windowClosing(WindowEvent e)
													{
														System.exit(0);
													}
												}
												);
		f.show();
	}
}