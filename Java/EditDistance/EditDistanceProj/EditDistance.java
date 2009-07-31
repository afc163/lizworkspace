import java.io.*;

public class EditDistance 
{
	private String strFrom;
	private String strTo;
	
	private int sFromSize;
	private int sToSize;
	
	private int [][] matrix;
	
	public EditDistance()
	{
		strFrom = null;
		strTo = null;
		
		sFromSize = 0;
		sToSize = 0;
		
		matrix = null;
	}
	
	public EditDistance(String sFrom, String sTo)
	{
		strFrom = sFrom;
		strTo = sTo;
		
		sFromSize = strFrom.trim().length();
		sToSize = strTo.trim().length();
		
		matrix = new int [sFromSize + 1][sToSize + 1];
	}
	
	public void LevenshteinDistance()
	{
		 for (int i = 0; i <= sFromSize; i++)
	    {
	    	matrix[i][0] = i;
	    }
    	for (int j = 1; j <= sToSize; j++)
    	{
    		matrix[0][j] = j;
	    }
 	
    	for (int i = 1; i <= sFromSize; i++)
    	{
    		for (int j = 1; j<= sToSize; j++)
    		{
    			int rest = 1;
    			if (strFrom.charAt(i-1) == strTo.charAt(j-1))
    			{
    				rest = 0;	
    			}
			    //min{m[i-1,j-1]+rest, m[i-1,j]+1, m[i, j-1]+1}
			    int min = matrix[i-1][j-1] + rest;
			    if (min > matrix[i-1][j] + 1)
		     	{
		     		min = matrix[i-1][j] + 1;
		    	}
		    	if (min > matrix[i][j-1] + 1)
		    	{
		    		min = matrix[i][j-1] + 1;
		    	}
		    	matrix[i][j] = min;
	    	}
    	}
	}
	
	public int Get(int row, int col)
	{
		if ((row < 0 || row > sFromSize) && (col < 0 || col > sToSize))
		{
			return -1;
		}
		
		return matrix[row][col];
	}
	
	public int GetsFromSize()
	{
		return sFromSize;
	}
	
	public int GetsToSize()
	{
		return sToSize;
	}
	
	public static void main(String [] args) throws IOException
	{
		String strFrom = null;
	    String strTo = null;

		BufferedReader myReader = new BufferedReader(new InputStreamReader(System.in));
		strFrom = myReader.readLine();
		System.out.println("输入" + strFrom);
		
		strTo = myReader.readLine();
		System.out.println("输入" + strTo);
		myReader.close();
		
		EditDistance eDistance = new EditDistance(strFrom, strTo);
		eDistance.LevenshteinDistance();
	    int rowSize = eDistance.GetsFromSize() + 1;
    	int colSize = eDistance.GetsToSize() + 1;
    	
	    for (int i = 0; i < rowSize; i++)
    	{
	    	for (int j = 0; j< colSize; j++)
	    	{
		    	System.out.print(eDistance.Get(i, j) + "----");
		    }
	    	System.out.println();
	    }
	    
	    System.out.println("结果是：" + eDistance.Get(rowSize-1, colSize-1));
	}
	
}
