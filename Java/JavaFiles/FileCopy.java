import java.io.*;

public class FileCopy
{
  public static void main(String [] args)
  {
    FileInputStream inFile = null;
    FileOutputStream outFile = null;
    
    try
    {
      inFile = new FileInputStream("FileCopy.java");
      outFile = new FileOutputStream("FileCopy.java.bak");
      
      int data;
      while ((data = inFile.read()) != -1)
      {
        outFile.write(data);
      }
    }
    catch (FileNotFoundException e)
    {
      System.out.println(e.getMessage());
    }
    catch (IOException e)
    {
      System.out.println(e.getMessage());
    }
    finally
    {
      if (inFile != null)
      {
        try
        {
          inFile.close();
        }
        catch (IOException e)
        {
          System.out.println(e);
        }
      }
      if (outFile != null)
      {
        try
        {
          outFile.close();
        }
        catch (IOException e)
        {
          System.out.println(e);
        }
      }     
    }
  }
}