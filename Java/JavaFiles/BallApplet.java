import java.applet.Applet;
import java.awt.*;
//import javax.swing.*;

public class BallApplet extends Applet
{
	public void paint(Graphics g)
	{
/*		int [] ballColors = new int[3];
		ballColors[0] = 4;
		ballColors[1] = 3;
		ballColors[2] = 3;
		int totalBall = 10;
		
		//try
		//{
		//	ballColors[0] = Integer.parseInt(JOptionPane.showInputDialog("�������ɫ�����Ŀ"));
		//	ballColors[1] = Integer.parseInt(JOptionPane.showInputDialog("�������ɫ�����Ŀ"));
		//	ballColors[2] = Integer.parseInt(JOptionPane.showInputDialog("�������ɫ�����Ŀ"));
		//	totalBall = ballColors[0] + ballColors[1] + ballColors[2];
		//}
		//catch (Exception e)
		//{
		//	g.drawString("������� " + e.getMessage(), 10, 10);
		//	return;
		//}
		
		Permutation permutation = new Permutation(ballColors, totalBall);
		permutation.perm(g, 0);
		
		g.drawString("����" + permutation.getNumber() + "������!", 10, 10);		
*/	}
}

/*class Permutation
{
	private int number;
	private int [] ballColors;
	private int totalBall;
	private int [] result;
	private int x;
	private int y;
	
	
	public Permutation(int [] bColors, int tBall)
	{
		x = 20;
		y = 20;
		number = 0;
		ballColors = bColors;
		totalBall = tBall;
		result = new int[totalBall];
	}
	
	public void perm(Graphics g, int i)
	{
		for (int j = 0; j < 3; j++)
		{
			if (ballColors[j] > 0)
			{
				result[i] = j;
				ballColors[j]--;
				if (i < (totalBall-1))
				{
					perm(g, i + 1);
				}
				else
				{
					output(g);
					//g.drawString("\n");
				}
				ballColors[j]++;
			}
		}
	}

	public void output(Graphics g)
	{
		number++;
		for (int j = 0; j < totalBall; j++)
		{
			switch (result[j])
			{
			case 0: 
				g.drawString("��", x, y);
				break;
			case 1:
				g.drawString("��", x + 20, y);
				break;
			case 2: 
				g.drawString("��", x + 40, y);
				break;
			}
		}
		y += 30;
	}	
	public int getNumber()
	{
		return number;
	}
}
*/