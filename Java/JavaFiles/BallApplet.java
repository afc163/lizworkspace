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
		//	ballColors[0] = Integer.parseInt(JOptionPane.showInputDialog("请输入红色球的数目"));
		//	ballColors[1] = Integer.parseInt(JOptionPane.showInputDialog("请输入白色球的数目"));
		//	ballColors[2] = Integer.parseInt(JOptionPane.showInputDialog("请输入黄色球的数目"));
		//	totalBall = ballColors[0] + ballColors[1] + ballColors[2];
		//}
		//catch (Exception e)
		//{
		//	g.drawString("输入错误 " + e.getMessage(), 10, 10);
		//	return;
		//}
		
		Permutation permutation = new Permutation(ballColors, totalBall);
		permutation.perm(g, 0);
		
		g.drawString("共有" + permutation.getNumber() + "种排列!", 10, 10);		
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
				g.drawString("红", x, y);
				break;
			case 1:
				g.drawString("白", x + 20, y);
				break;
			case 2: 
				g.drawString("黄", x + 40, y);
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