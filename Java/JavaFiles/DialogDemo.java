import java.awt.*;
import java.awt.event.*;

public class DialogDemo
{
	public static void main(String [] args)
	{
		Panel P = new Panel();
		TextField T = new TextField(10);
	
		T.setEchoChar('*');
	
		Label L = new Label("请输入你的密码: ");
		Button B1 = new Button("确定");
		Button B2 = new Button("取消");
	
		P.add(L);
		P.add(T);
		P.add(B1);
		P.add(B2);
	
		Frame f = new Frame("使用对话框");
	
		Dialog D1 = new Dialog(f, "我的密码对话框", false);
	
		D1.setBounds(0, 50, 200, 100);
		D1.add(P);
	
		FileDialog FD = new FileDialog(f, "你想打开文件吗？", FileDialog.LOAD);
	
		f.setBounds(0, 0, 500, 400);
		f.add(new Label("主窗口"));
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