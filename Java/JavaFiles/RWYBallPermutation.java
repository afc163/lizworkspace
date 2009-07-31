import javax.swing.*;

public class RWYBallPermutation
{
	public static void main(String [] args)
	{
		int [] ballColors = new int[3];
		int totalBall;
		
		try
		{
			ballColors[0] = Integer.parseInt(JOptionPane.showInputDialog("�������ɫ�����Ŀ"));
			ballColors[1] = Integer.parseInt(JOptionPane.showInputDialog("�������ɫ�����Ŀ"));
			ballColors[2] = Integer.parseInt(JOptionPane.showInputDialog("�������ɫ�����Ŀ"));
			totalBall = ballColors[0] + ballColors[1] + ballColors[2];
		}
		catch (Exception e)
		{
			System.out.println("������� " + e.getMessage());
			return;
		}
		
		Permutation permutation = new Permutation(ballColors, totalBall);
		permutation.perm(0);
		
		System.out.println("����" + permutation.getNumber() + "������!");		
	}
}

class Permutation
{
	private int number;
	private int [] ballColors;
	private int totalBall;
	private int [] result;
	
	
	public Permutation(int [] bColors, int tBall)
	{
		number = 0;
		ballColors = bColors;
		totalBall = tBall;
		result = new int[totalBall];
	}
	
	public void perm(int i)
	{
		for (int j = 0; j < 3; j++)
		{
			if (ballColors[j] > 0)
			{
				result[i] = j;
				ballColors[j]--;
				if (i < (totalBall-1))
				{
					perm(i + 1);
				}
				else
				{
					output();
					System.out.println();
				}
				ballColors[j]++;
			}
		}
	}

	public void output()
	{
		number++;
		for (int j = 0; j < totalBall; j++)
		{
			switch (result[j])
			{
			case 0: 
				System.out.print(" �� ");
				break;
			case 1:
				System.out.print(" �� ");
				break;
			case 2: 
				System.out.print(" �� ");
				break;
			}
		}
	}	
	public int getNumber()
	{
		return number;
	}
}