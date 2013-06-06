import java.io.*;
class copy{
	public static void main(String args[])throws IOException
	{
		int i;
		FileReader fr;
		FileWriter fw;
		try
		{
  			try
			{
  				fr=new FileReader("test1.txt");
			}
			catch (FileNotFoundException e)
			{
				System.out.println("input file not found");
				return;
			}
			try
			{
				fw=new FileWriter("test2.txt");
			}
			catch(FileNotFoundException e)
			{
				System.out.println ("output file not found");
				return;
			}
		}
		catch(ArrayIndexOutOfBoundsException e)
		{
			System.out.println("copy from to");
			return;
		}
		try
		{
			do{
				i=fr.read();
				if(i!=-1) fw.write(i);
			}while(i!=-1);
		}
		catch(IOException e)
		{
			System.out.println("File error");
		}
		fr.close();
		fw.close();
	}
}
