package handledata;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.Random;
import java.util.Vector;

public class BinaryRelationCreat {
	int objNum=0;
    int attNum=0;
    Vector Obj=new Vector();
    Vector Att=new Vector();
    Vector att0=new Vector();
    Vector att1=new Vector();

    Random rand=new Random();
    int rowMarks[];
    int m[];
    int mk=0;//判断是否删除行标志
    int mr=1;//判断是否删除列标志
    
    /*public BinaryRelation(int nbObjs, int nbAtts)
    {  //理解二元关系的数据结构
        super(nbObjs, nbAtts);
        
        for(int i = 0; i < aPourProp.size(); i++)
        {
            for(int j = 0; j < lesAtributs.size(); j++)  //初始化，压入lesAtributs.size()个0,优化角度看，可以省略
                ((Vector)aPourProp.elementAt(i)).addElement(new FormalAttributeValue());

        }
    }*/
    
    public BinaryRelationCreat(int nbObjs,int nbAtts,int marks,int pp,boolean delRZero, boolean delROne,boolean delRSame,boolean delCZero,boolean delCOne,boolean delCSame)
    {//随机生成符合要求的形式背景
     //编写代码生成二维关系，并响应用户要求
    	initParameter(nbObjs, nbAtts);

        if(marks==0)//对一概率没有要求
        {
        	createContextNo(delRZero,delROne,delRSame,delCZero,delCOne,delCSame);
        }//--结束对一概率无要求的显示二维关系
        
        //1行概率有要求
        if(marks==1)
        {
        	createContextRow(delRZero,delROne,delRSame,delCZero,delCOne,delCSame,pp);
        }//结束对行概率1的要求
        
        //对整体1的概率的要求
        if(marks==2)
        {
        	createContextGlobal(delRZero,delROne,delRSame,delCZero,delCOne,delCSame,pp);
        }
        //save to sql database
        //svsql(attNum,objNum);
    }
    // --李拓加于3.29
    private void initParameter(int nbObjs, int nbAtts)
    {
        Obj.clear();
        objNum=nbObjs;
        attNum=nbAtts;
        rowMarks=new int [attNum];
        m=new int[attNum];
        for(int i=0;i<attNum;i++)
        {
            rowMarks[i] = 0;
            m[i]=0;
        }
        for(int i=0;i<attNum;i++)
            att1.add(i,new Integer(1));

        for(int i=0;i<attNum;i++)
            att0.add(i,new Integer(0));
    }
    
    private void createContextNo(boolean delRZero, boolean delROne,boolean delRSame,boolean delCZero,boolean delCOne,boolean delCSame)
    {
    	for(int i=0;i<objNum;i++)
    	{
         	Att.clear();
         	for (int j = 0; j < attNum; j++)
         	{
         		byte a = (byte)rand.nextInt(2);
         		if (a == 0) rowMarks[j]--;
         		if (a == 1) rowMarks[j]++;
         		Att.add(new Integer(a));
         	}
         	Obj.add(Att.clone());
                        
         	//对相同行，全零行全一行的判断
         	if(delRSame==true ) if(delRowSame(i)) mk=1;//判断相同行
         	if(delROne==true ) if( this.delRowOne(i) ) mk=1;//判断全一行
         	if(delRZero==true ) if( this.delRowZero(i) ) mk=1;//判断全零行
                        
         	if(mk==1)
         	{
         		mk=0;
         		Obj.removeElementAt(i);
         		i--;
         	}
         	//结束对行的判断
    	}
    	//开始对列的判断
    	while(mr==1)
    	{
    		mr=0;
        
    		if(delCZero==true ) delColZero();//删除全零列，并且生成新列
    		if(delCOne==true ) delColOne();//删除全一列，并且生成新列
    		//重置标志位为零
    		for(int i=0;i<attNum;i++)
    			rowMarks[i]=0;
        
    		if(delCSame==true ) delColSame();//判断并且删除相同列
                    
    		//重置标志位为零
    		for (int i = 0; i < attNum; i++)
    			m[i] = 0;
    	}
    	//--结束对二维关系的构造，并且相应操作结束
    	//显示二维关系
    	showBinaryRelation();
    }
     
    
    private void createContextRow(boolean delRZero, boolean delROne,boolean delRSame,boolean delCZero,boolean delCOne,boolean delCSame,int pp)
    {
    	int number=attNum*pp/100;
        int countNum=0;
        double p=pp*1.0/100.0;
        int r[]=new int [attNum];
        double a = rand.nextDouble();
        for(int i=0;i<objNum;i++)
        {
            Att.clear();
            while(countNum!=number)
            {
            	countNum=0;
                for(int c=0;c<attNum;c++)
                {
                    if(countNum==number) r[c]=0;
                    else
                    {
                        a=rand.nextDouble();
                        if (a < p)
                        {
                            r[c] = 1;
                            countNum++;
                        }
                        if (a >= p) r[c] = 0;
                    }
                }
            }

            for (int j = 0; j < attNum; j++)
            {
                if (r[j] == 1) rowMarks[j]++;
                if (r[j] == 0) rowMarks[j]--;
                Att.add(new Integer(r[j]));
            }
            Obj.add(Att.clone());
            
            if(delRSame==true ) if(delRowSame(i)) mk=1;//判断相同行
            if(mk==1)
         	{
         		mk=0;
         		Obj.removeElementAt(i);
         		i--;
         	}
            countNum=0;
        }
        // 开始对列的判断
    	while(mr==1)
    	{
    		mr=0;
    		if(delCZero==true ) delColZero();//删除全零列，并且生成新列
    		if(delCOne==true ) delColOne();//删除全一列，并且生成新列
    		//重置标志位为零
    		for(int i=0;i<attNum;i++)
    			rowMarks[i]=0;
    		if(delCSame==true ) delColSame();//判断并且删除相同列
    		//重置标志位为零
    		for (int i = 0; i < attNum; i++)
    			m[i] = 0;
    	}
    	//--结束对二维关系的构造，并且相应操作结束
    	//显示二维关系
    	showBinaryRelation();
    }
    
    
    private void createContextGlobal(boolean delRZero, boolean delROne,boolean delRSame,boolean delCZero,boolean delCOne,boolean delCSame,int pp)
    {
        double p = pp * 1.0 / 100.0;
    	for(int i=0;i<objNum;i++)
        {
        	Att.clear();
        	for (int j = 0; j < attNum; j++)
            {
        		double a = rand.nextDouble();
                if (a < p)
                {
                	rowMarks[j]++;
                    Att.add(j, new Integer(1));
                }
                if (a >= p)
                {
                	rowMarks[j]--;
                	Att.add(j, new Integer(0));
                }
             }
             Obj.add(Att.clone());
             // 对相同行，全零行全一行的判断
             if(delRSame==true ) if(delRowSame(i)) mk=1;//判断相同行
             if(delROne==true ) if( this.delRowOne(i) ) mk=1;//判断全一行
             if(delRZero==true ) if( this.delRowZero(i) ) mk=1;//判断全零行
                            
             if(mk==1)
             {
             	mk=0;
             	Obj.removeElementAt(i);
             	i--;
             }
        //结束对行的判断
        }
        // 开始对列的判断
    	while(mr==1)
    	{
    		mr=0;
        
    		if(delCZero==true ) delColZero();//删除全零列，并且生成新列
    		if(delCOne==true ) delColOne();//删除全一列，并且生成新列
    		//重置标志位为零
    		for(int i=0;i<attNum;i++)
    			rowMarks[i]=0;
        
    		if(delCSame==true ) delColSame();//判断并且删除相同列
                    
    		//重置标志位为零
    		for (int i = 0; i < attNum; i++)
    			m[i] = 0;
    	}
    	//--结束对二维关系的构造，并且相应操作结束

    	//显示二维关系
    	showBinaryRelation();
    }
    //// --李拓加于4.15
    /*private BinaryRelation(int nbObjs, int nbAtts,int num,int mk)
    {//用于分割形式背景
    	super(nbObjs, nbAtts,num,mk);
        
        for(int i = 0; i < aPourProp.size(); i++)
        {
            for(int j = 0; j < lesAtributs.size(); j++)  //初始化，压入lesAtributs.size()个0,优化角度看，可以省略
                ((Vector)aPourProp.elementAt(i)).addElement(new FormalAttributeValue());

        }
        
        boolean s=true;
        int ocount=0,acount=0;
        
        if(mk==0)
        {
        if(num==1)
        {
        	for(int i=0;i<nbObjs;i++)
        	{
        		for(int j=0;j<nbAtts;j++)
        		{
        			String str=((Vector)Obj.get(i)).get(j).toString();
        			if(str.equals("0")) s=false;
        			else s=true;
        			setRelation(i,j,s);
        		}
        	}
        }
        else if(num==2)
        {//ko  ka用于对新的形式背景置数
        	for(int i=0,ko=0;i<nbObjs;i++,ko++)
        	{
        		for(int j=attNum-nbAtts,ka=0;j<attNum;j++,ka++)
        		{
        			String str=((Vector)Obj.get(i)).get(j).toString();
        			if(str.equals("0")) s=false;
        			else s=true;
        			setRelation(ko,ka,s);
        		}
        	}
        }
        else if(num==3)
        {
        	for(int i=nbObjs,ko=0;i<2*nbObjs;i++,ko++)
        	{
        		for(int j=0,ka=0;j<nbAtts;j++,ka++)
        		{
        			String str=((Vector)Obj.get(i)).get(j).toString();
        			if(str.equals("0")) s=false;
        			else s=true;
        			setRelation(ko,ka,s);
        		}
        	}
        }
        else if(num==4)
        {
        	for(int i=nbObjs,ko=0;i<2*nbObjs;i++,ko++)
        	{
        		for(int j=attNum-nbAtts,ka=0;j<attNum;j++,ka++)
        		{
        			String str=((Vector)Obj.get(i)).get(j).toString();
        			if(str.equals("0")) s=false;
        			else s=true;
        			setRelation(ko,ka,s);
        		}
        	}
        }
        else if(num==5)
        {
        	for(int i=Obj.size()-nbObjs,ko=0;i<Obj.size();i++,ko++)
        	{
        		for(int j=0,ka=0;j<nbAtts;j++,ka++)
        		{
        			String str=((Vector)Obj.get(i)).get(j).toString();
        			if(str.equals("0")) s=false;
        			else s=true;
        			setRelation(ko,ka,s);
        		}
        	}
        }
        else if(num==6)
        {
        	for(int i=Obj.size()-nbObjs,ko=0;i<Obj.size();i++,ko++)
        	{
        		for(int j=attNum-nbAtts,ka=0;j<attNum;j++,ka++)
        		{
        			String str=((Vector)Obj.get(i)).get(j).toString();
        			if(str.equals("0")) s=false;
        			else s=true;
        			setRelation(ko,ka,s);
        		}
        	}
        }
        }//自动分割endif mk==0
        
        if(mk==1)
        {
        	if(num!=20)
        	{
        		for(int i=0,ko=0;i<nbObjs;i++,ko++)
        		{
        			for(int j=0;j<attNum;j++)
        			{
        				String str=((Vector)Obj.get(i+(num-1)*nbObjs)).get(j).toString();
        				if(str.equals("0")) s=false;
        				else s=true;
        				setRelation(ko,j,s);
        			}
        		}
        	}
        	else
        	{
        		for(int i=objNum-nbObjs,ko=0;i<objNum;i++,ko++)
        		{
        			for(int j=0;j<attNum;j++)
        			{
        				String str=((Vector)Obj.get(i)).get(j).toString();
        				if(str.equals("0")) s=false;
        				else s=true;
        				setRelation(ko,j,s);
        			}
        		}
        	}
        }//横向分割endif mk==1
        if(mk==2)
        {
        	if(num!=10)
        	{
        		for(int i=0;i<objNum;i++)
        		{
        			for(int j=0,ka=0;j<nbAtts;j++,ka++)
        			{
        				String str=((Vector)Obj.get(i)).get(j+(num-1)*nbAtts).toString();
        				if(str.equals("0")) s=false;
        				else s=true;
        				setRelation(i,ka,s);
        			}
        		}
        	}
        	else
        	{
        		for(int i=0;i<objNum;i++)
        		{
        			for(int j=attNum-nbAtts,ka=0;j<attNum;j++,ka++)
        			{
        				String str=((Vector)Obj.get(i)).get(j).toString();
        				if(str.equals("0")) s=false;
        				else s=true;
        				setRelation(i,ka,s);
        			}
        		}
        	}
        }//纵向分割endif mk==2
    }*/
    /// --李拓加于4.15-----------------------------------------------
    
    //加于4.13
    /*private BinaryRelation(int nbObjs,int nbAtts,Vector Obj,int att,StringBuffer s[])
    {//把多值的关系转换成为二值的关系。所传递变量意义：对象数目，属性数目，存储位置，文件的列数（Obj中向量的长度），属性的名称
    	
    	super(nbObjs, nbAtts,s);//对属性名称的修改！
        
        for(int i = 0; i < aPourProp.size(); i++)
        {
            for(int j = 0; j < lesAtributs.size(); j++)  //初始化，压入lesAtributs.size()个0,优化角度看，可以省略
                ((Vector)aPourProp.elementAt(i)).addElement(new FormalAttributeValue());
        }
        
        for(int i=0;i<nbObjs;i++)
        {
        	for(int j=0;j<att;j++)
        	{
        		String ss=((Vector)(Obj.get(i))) . get(j).toString();
        		String ssnm=new String("a_lie"+(j+1)+"_zhi"+ss);//得到对应的属性名称
        		//System.out.println("?"+ssnm+"?"+j+"?"+nbAtts);
        		for(int k=0;k<nbAtts;k++)
        		{//！！！！！！！！！！！！！！显示二元关系，此处有问题！！！！！！！！！！！！！
        			if(s[k].toString().equals(ssnm)) this.setRelation(i,k,true);
        			else this.setRelation(i,k,false);
        		}
        	}
        }
    }*/
    //加于4.13------------------------------
    
    /*private BinaryRelation(String tbnm)
    {
    	super(tbnm);
    }*/
    //相关函数
    
    private boolean showBinaryRelation()
    {
    	boolean s=true;
    	for(int i=0;i<objNum;i++)
    	{
    		for(int j=0;j<attNum;j++)
    		{
    			String str=((Vector)Obj.get(i)).get(j).toString();
    			if(str.equals("0")) s=false;
    			else s=true;
    		}
    	}
    	return s;
    }
    private boolean delRowZero(int oindex)
    {
        if ( Obj.get(oindex).toString().equals(att0.toString()) ) return true;
        else return false;
    }

    private boolean delRowOne(int oindex)
    {
    	if ( Obj.get(oindex).toString().equals(att1.toString()) ) return true;
        else return false;
    }

    private boolean delRowSame(int i)
    {
    	for(int k=0;k<i;k++)
        {//判断相同行
            if( Obj.get(k).equals(Obj.get(i)) ) return true;
        }
    	return false;
    }
    
    private void delColZero()
    {
    	for (int i = 0; i < attNum; i++) 
        { //全零的判断
            while (rowMarks[i] == -objNum) 
            {
                rowMarks[i] = 0;
                for (int k = 0; k < objNum; k++) 
                {
                    byte a = (byte) rand.nextInt(2);
                    if (a == 0) rowMarks[i]--;
                    if (a == 1) rowMarks[i]++;
                    ((Vector) Obj.get(k)).removeElementAt(i);
                    ((Vector) Obj.get(k)).add(i, new Integer(a));
                }
            }
        }
    }
    
    private void delColOne()
    {
    	for (int i = 0; i < attNum; i++) 
        { //全一的判断
            while (rowMarks[i] == objNum) 
            {
                rowMarks[i] = 0;
                for (int k = 0; k < objNum; k++) 
                {
                    byte a = (byte) rand.nextInt(2);
                    if (a == 0) rowMarks[i]--;
                    if (a == 1) rowMarks[i]++;
                    ((Vector) Obj.get(k)).removeElementAt(i);
                    ((Vector) Obj.get(k)).add(i, new Integer(a));
                }
            }
        }
    }
    
    private void delColSame()
    {
    	for (int i = 0; i < attNum; i++) 
        { //检查相同列
            for (int j = 0; j < objNum; j++) 
            {
                for (int k = 1; i + k < attNum; k++) 
                {
                    if ((m[i + k] == i) && !((Vector) Obj.get(j)).get(i).equals(((Vector) Obj.get(j)).get(i +k)))
                        m[i + k]++;
                    if ((j == objNum - 1) && (m[i + k] == i))
                    { //判断是不是有相同列
                        m[i + k] = -1;
                        mr = 1; //还有相同列，要重新生成新列
                    }
                }
            }
        }
        //删除相同的列
        for (int i = attNum - 1; i >= 0; i--) 
        {
            if (m[i] == -1)
            {
                for (int j = 0; j < objNum; j++) 
                {
                    ((Vector) Obj.get(j)).removeElementAt(i);
                    byte a = (byte) rand.nextInt(2);
                    ((Vector) Obj.get(j)).add(i,new Integer(a));//生成新列
                    if(a==0)rowMarks[i]--;
                    if(a==1)rowMarks[i]++;
                }
                m[i] = 0;
                i++;
            }
        }
    }



//////////////////////////////////////////////////////////////////////
    /*
private void SaveToDatabase(int nmatt,int nmobj)
{
	Date today=new Date();
	String tbnm1=today.toString().replace(' ','_');
	String tbnm=tbnm1.replace(':','_');//表名
	CreateTable(tbnm,nmatt,nmobj);
	InsertTable(tbnm,nmatt,nmobj);
	//showdb(tbnm);
}

private void CreateTable(String tbnm,int nmatt,int nmobj)
{//create table
	long time = System.currentTimeMillis();
	String Jdrv="sun.jdbc.odbc.JdbcOdbcDriver";
	String url="jdbc:odbc:lattice";
	String scol="ObjectName varchar(50),";	//by cjj 2005-9-18
	
	for(int i=1;i<nmatt;i++)
	{//获得列名
		scol=scol+"att_"+(i-1)+" int,";
	}
	
	try
	{
		Class.forName(Jdrv);
	}
	catch(java.lang.ClassNotFoundException e )
	{
		System.out.println("jdbc connection error"+e.getMessage());
	}
	try
	{
		Connection con=DriverManager.getConnection(url,"sa","215342");
		Statement s=con.createStatement();
		String sql="create table "+tbnm+"("
			+scol
			+"att_"+(nmatt-1)+" int"
			+")";
		//System.out.println(sql1);
		s.executeUpdate(sql);
		s.close();
		con.close();
		System.out.println("create table time " + (System.currentTimeMillis() - time) + " mSec.");
		System.out.println("the table name is : "+tbnm);
	}
	catch(SQLException e)
	{
		System.out.println("create table error"+e.getMessage());
	}
}

private void InsertTable(String tbnm,int nmatt,int nmobj)
{//insert record to table
	long time = System.currentTimeMillis();
	String Jdrv="sun.jdbc.odbc.JdbcOdbcDriver";
	String url="jdbc:odbc:lattice";
	try
	{
		Class.forName(Jdrv);
	}
	catch(java.lang.ClassNotFoundException e )
	{
		System.out.println("jdbc connection error"+e.getMessage());
	}
	try
	{
		Connection con=DriverManager.getConnection(url,"sa","215342");
		Statement s=con.createStatement();
		String str="'obj_";
		for(int i=0;i<nmobj;i++)
		{	
			str=str+i+"',"+toStr(i,0);
			for(int j=1;j<nmatt;j++)
			{
				str=str+","+toStr(i,j);
			}
			String sql="insert into "+tbnm+" values("
				+str+")";
			//System.out.println(sql);
			s.executeUpdate(sql);
			str="'obj_";
			//sendJobPercentage(i);
		}
		s.close();
		con.close();
		System.out.println("insert table time " + (System.currentTimeMillis() - time) + " mSec.");
		//sendJobPercentage((i * 100) / nbObjets);
	}
	catch(SQLException e)
	{
		System.out.println("insert table error"+e.getMessage());
	}
}

private String toStr(int i,int j)
{//把obj中数据转换成String
	String str=((Vector)aPourProp.get(i)).get(j).toString();
	String str1="";
	if(str.equals("X")) str1="1";
	else str1="0";
	return str1;
}

private void ShowDatabase(String tbnm)
{
	System.out.println("OKOKOKOKOK");
}*/
}