/*
 * EditDistance.java
 *
 * Created on 2007年8月27日, 下午4:36
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package editdistance;

import java.io.*;
/**
 *
 * @author shengyan
 */
public class EditDistance 
{
    private String strFrom;
    private String strTo;
    
    private int strFromSize;
    private int strToSize;
    
    private int [][] matrix;                            //结果矩阵
    private boolean [][] matrix2;                      //标记矩阵
    
    String result = "";
    
    /** Creates a new instance of EditDistance */
    public EditDistance() 
    {
        strFrom = null;
	strTo = null;
		
	strFromSize = 0;
	strToSize = 0;
		
	matrix = null;
        matrix2 = null;
    }

    public EditDistance(String sFrom, String sTo)
    { 
        strFrom = sFrom;
        strTo = sTo;
        strFromSize = strFrom.trim().length();
        strToSize = strTo.trim().length();
        matrix = new int [strFromSize + 1][strToSize + 1];
        initMatrix2();
        
    }

    public void SetString(String sFrom, String sTo)
    {
        strFrom = sFrom;
        strTo = sTo;
            strFromSize = strFrom.trim().length();
            strToSize = strTo.trim().length();
            strToSize = 0;
        matrix = new int [strFromSize + 1][strToSize + 1];
        initMatrix2();
    }
    
    public void LevenshteinDistance()            //Compute Levenshtein Distance
    {
        for (int i = 0; i <= strFromSize; i++)      //initialize matrix
        {
            matrix[i][0] = i;
        }
    	for (int j = 1; j <= strToSize; j++)
    	{
            matrix[0][j] = j;
        }
 	//compute matrix: 主要思想是有点递推的感觉，假设之前的已经计算出来之前的min，加入当前元素时的几种变化情况
    	for (int i = 1; i <= strFromSize; i++)     
    	{
    		for (int j = 1; j<= strToSize; j++)
    		{
    			int rest = 1;              //strFrom[i-1] == strTo[j-1]??匹配
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
        if ((row < 0 || row > strFromSize) && (col < 0 || col > strToSize))
        {
            return -1;
        }
        return matrix[row][col];
    }
	
    public int GetsFromSize()
    {
        return strFromSize;
    }
	
    public int GetsToSize()
    {
        return strToSize;
    }
    
    public void traceBackAnother(String row1, String row2, String row3, int i, int j) throws IOException
    {
        if (i > 0 && j > 0)
        {
            int diag = matrix[i-1][j-1];
            char diagCh = '|';
            //String diagStr = new String("|".getBytes("ISO8859_1"), "GB2312");
            if (strFrom.charAt(i-1) != strTo.charAt(j-1))
            {
                diag++;
                diagCh = ' ';
                //diagStr = "  ";
            }
            if (matrix[i][j] == diag)
            {
                //change or match
                matrix2[i][j] = true;
                matrix2[i-1][j-1] = true;
                traceBackAnother(strFrom.charAt(i-1)+row1, diagCh+row2, strTo.charAt(j-1)+row3, i-1, j-1);
            }
            if (matrix[i][j] == matrix[i-1][j] +1)  //up element
            {     //delete
                matrix2[i-1][j] = true;
                traceBackAnother(strFrom.charAt(i-1)+row1, ' '+row2, '-'+row3, i-1, j);
            }
            if (matrix[i][j] == matrix[i][j-1] +1)  //left element
            {      //insert——其实delete和insert差不多，只是相对的字符串不同
                matrix2[i][j-1] = true;
                traceBackAnother('-'+row1, ' '+row2, strTo.charAt(j-1)+row3, i, j-1);
            }  
        }
        else if (i > 0)
        {
            matrix2[i-1][j] = true;
            traceBackAnother(strFrom.charAt(i-1)+row1, ' '+row2, '-'+row3, i-1, j);
        }
        else if (j > 0)
        {
            matrix2[i][j-1] = true;
            traceBackAnother('-'+row1, ' '+row2, strTo.charAt(j-1)+row3, i, j-1);
        }
        else
        {
            matrix2[i][j] = true;
            //result = row1+"\n"+row2+"\n"+row3;
            System.out.println(row1+"\n"+row2+"\n"+row3);
        }
    }
    
    public void traceBack(String row1, String row2, String row3, int i, int j)
    {    //跟踪结果得到matrix2,用递归实现
        if (i > 0 && j > 0)
        {
            int diag = matrix[i-1][j-1];
            char diagCh = '|';//match sign
            if ( strFrom.charAt(i-1) != strTo.charAt(j-1))
            {
                diag++;
                diagCh = ' ';
            }
            if (matrix[i][j] == diag)
            {    //change or match
                matrix2[i][j] = true;
                matrix2[i-1][j-1] = true;
                traceBack(strFrom.charAt(i-1)+row1, diagCh+row2, strTo.charAt(j-1)+row3, i-1, j-1);
            }
            else if (matrix[i][j] == matrix[i-1][j] +1)
            {     //delete
                matrix2[i-1][j] = true;
                traceBack(strFrom.charAt(i-1)+row1, ' '+row2, '-'+row3, i-1, j);
            }
            else
            {      //insert——其实delete和insert差不多，只是相对的字符串不同
                matrix2[i][j-1] = true;
                traceBack('-'+row1, ' '+row2, strTo.charAt(j-1)+row3, i, j-1);
            }  
        }
        else if (i > 0)
        {
            matrix2[i-1][j] = true;
            traceBack(strFrom.charAt(i-1)+row1, ' '+row2, '-'+row3, i-1, j);
        }
        else if (j > 0)
        {
            matrix2[i][j-1] = true;
            traceBack('-'+row1, ' '+row2, strTo.charAt(j-1)+row3, i, j-1);
        }
        else
        {
            matrix2[i][j] = true;
            result = row1+"\n"+row2+"\n"+row3;
        }
    }
    
    public void initMatrix2()
    {
        matrix2 = new boolean [strFromSize + 1][strToSize + 1];
        for (int i = 0; i <= strFromSize; i++)
            for (int j = 0; j <= strToSize; j++)
            {
                  matrix2[i][j] = false;
            }
    }
    
    public boolean Get2(int row, int col)
    {
        return matrix2[row][col];
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
                
                eDistance.traceBackAnother("", "", "", eDistance.GetsFromSize(), eDistance.GetsToSize());              
                System.out.println(eDistance.result);
                
                
                System.out.println("跟踪的结果：");
                for (int i = 0; i < rowSize; i++)
    	       {
	    	    for (int j = 0; j< colSize; j++)
	    	    {
		    	if (eDistance.Get2(i, j))
                        {
                            System.out.print(eDistance.Get(i, j) + "*---");
                        }
                        else
                        {
                            System.out.print(eDistance.Get(i, j) + "----");
                        }
		    }
	    	    System.out.println();
	        }
	}
}
