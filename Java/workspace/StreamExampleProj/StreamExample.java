import java.io.*;

public class StreamExample 
{
	public static void main(String [] args) throws IOException
	{
		BufferedReader stdin = new BufferedReader(new InputStreamReader(System.in));
		System.out.print("Enter a line: ");
		System.out.println(stdin.readLine());
		stdin.close();
		
		BufferedReader in = new BufferedReader(new FileReader("StreamExample.java"));
		String s;
		String s2 = new String();
		while ((s = in.readLine()) != null)
		{
			s2 += s + "\n";
		}
		in.close();
		
		StringReader inStr = new StringReader(s2);
		int c;
		while ((c = inStr.read()) != -1)
		{
			System.out.print((char)c);
		}
		inStr.close();
		
		try
		{
			BufferedReader inStr2 = new BufferedReader(new StringReader(s2));
			PrintWriter outStr = new PrintWriter(new BufferedWriter(new FileWriter("StreamExample.out")));
			int lineCount = 1;
			while ((s = inStr2.readLine()) != null)
			{
				outStr.println(lineCount++ + ": " + s);
			}
			inStr2.close();
			outStr.close();
		}
		catch (EOFException e)
		{
			System.err.println("流操作非法结果！");
		}
	}
}
