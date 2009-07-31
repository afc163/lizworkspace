import java.awt.*;
import java.awt.event.*;

public class TextFieldDemo
{
	public static void main(String [] args)
	{
		TextField T1 = new TextField();
		TextField T2 = new TextField("TextField����!");
		TextField T3 = new TextField("�����޸�", 25);
		
		T1.setEchoChar('*');
		T1.setText("����������");
		T2.setBackground(Color.yellow);
		T2.setColumns(20);
		T3.setEditable(false);
		T3.selectAll();
		
		Panel p = new Panel();
		Frame f = new Frame();
		
		f.setTitle("ʹ��TextField���");
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