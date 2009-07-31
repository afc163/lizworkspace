import java.io.*;

public class McCathy91
{
	public static void main(String [] args)
	{
		System.out.print("������x��ֵ��");
		try
		{
			int x = System.in.read();
			
			McCathy91 mc = new McCathy91();
			
			System.out.println("�õ��Ľ���ǣ�" + mc.getM(x));
		}
		catch (IOException e)
		{
			System.out.println("�������" + e.getMessage());
		}
	}
	
	public int getM(int x)
	{
		if (x > 100)
		{
			return x - 10;
		}
		else
		{
			return getM(getM(x+11));
		}
	}
}