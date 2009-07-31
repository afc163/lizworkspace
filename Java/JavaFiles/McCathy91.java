import java.io.*;

public class McCathy91
{
	public static void main(String [] args)
	{
		System.out.print("请输入x的值：");
		try
		{
			int x = System.in.read();
			
			McCathy91 mc = new McCathy91();
			
			System.out.println("得到的结果是：" + mc.getM(x));
		}
		catch (IOException e)
		{
			System.out.println("输入错误" + e.getMessage());
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