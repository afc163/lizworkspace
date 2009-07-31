import java.awt.*;
import java.awt.event.*;

public class TextFieldDemo
{
	public static void main(String [] args)
	{
		TextField T1 = new TextField();
		TextField T2 = new TextField("TextField例子!");
		TextField T3 = new TextField("不可修改", 25);
		
		T1.setEchoChar('*');
		T1.setText("看不见？？");
		T2.setBackground(Color.yellow);
		T2.setColumns(20);
		T3.setEditable(false);
		T3.selectAll();
		
		Panel p = new Panel();
		Frame f = new Frame();
		
		f.setTitle("使用TextField组件");
		f.setBounds(0, 0, 400, 300);
		f.setResizable(false);
		
		p.add(T1);
		p.add(T2);
		p.add(T3);
		f.add(p);
		
		f.addWindowListener(new WindowAdapter()
												{
													public void windowClosing(WindowEvent e)
													{
														System.exit(0);
													}
												}
												);
		f.setVisible(true);
		f.show();
	}
}