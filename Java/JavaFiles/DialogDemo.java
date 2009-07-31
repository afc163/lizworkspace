import java.awt.*;
import java.awt.event.*;

public class DialogDemo
{
	public static void main(String [] args)
	{
		Panel P = new Panel();
		TextField T = new TextField(10);
	
		T.setEchoChar('*');
	
		Label L = new Label("�������������: ");
		Button B1 = new Button("ȷ��");
		Button B2 = new Button("ȡ��");
	
		P.add(L);
		P.add(T);
		P.add(B1);
		P.add(B2);
	
		Frame f = new Frame("ʹ�öԻ���");
	
		Dialog D1 = new Dialog(f, "�ҵ�����Ի���", false);
	
		D1.setBounds(0, 50, 200, 100);
		D1.add(P);
	
		FileDialog FD = new FileDialog(f, "������ļ���", FileDialog.LOAD);
	
		f.setBounds(0, 0, 500, 400);
		f.add(new Label("������"));
		f.addWindowListener(new WindowAdapter()
												{
													public void windowClosing(WindowEvent e)
													{
														System.exit(0);
													}
												}
												);
		f.show();
	
		D1.show();
		FD.show();
	}	
}