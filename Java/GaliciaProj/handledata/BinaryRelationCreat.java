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
    int mk=0;//�ж��Ƿ�ɾ���б�־
    int mr=1;//�ж��Ƿ�ɾ���б�־
    
    /*public BinaryRelation(int nbObjs, int nbAtts)
    {  //����Ԫ��ϵ�����ݽṹ
        super(nbObjs, nbAtts);
        
        for(int i = 0; i < aPourProp.size(); i++)
        {
            for(int j = 0; j < lesAtributs.size(); j++)  //��ʼ����ѹ��lesAtributs.size()��0,�Ż��Ƕȿ�������ʡ��
                ((Vector)aPourProp.elementAt(i)).addElement(new FormalAttributeValue());

        }
    }*/
    
    public BinaryRelationCreat(int nbObjs,int nbAtts,int marks,int pp,boolean delRZero, boolean delROne,boolean delRSame,boolean delCZero,boolean delCOne,boolean delCSame)
    {//������ɷ���Ҫ�����ʽ����
     //��д�������ɶ�ά��ϵ������Ӧ�û�Ҫ��
    	initParameter(nbObjs, nbAtts);

        if(marks==0)//��һ����û��Ҫ��
        {
        	createContextNo(delRZero,delROne,delRSame,delCZero,delCOne,delCSame);
        }//--������һ������Ҫ�����ʾ��ά��ϵ
        
        //1�и�����Ҫ��
        if(marks==1)
        {
        	createContextRow(delRZero,delROne,delRSame,delCZero,delCOne,delCSame,pp);
        }//�������и���1��Ҫ��
        
        //������1�ĸ��ʵ�Ҫ��
        if(marks==2)
        {
        	createContextGlobal(delRZero,delROne,delRSame,delCZero,delCOne,delCSame,pp);
        }
        //save to sql database
        //svsql(attNum,objNum);
    }
    // --���ؼ���3.29
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
                        
         	//����ͬ�У�ȫ����ȫһ�е��ж�
         	if(delRSame==true ) if(delRowSame(i)) mk=1;//�ж���ͬ��
         	if(delROne==true ) if( this.delRowOne(i) ) mk=1;//�ж�ȫһ��
         	if(delRZero==true ) if( this.delRowZero(i) ) mk=1;//�ж�ȫ����
                        
         	if(mk==1)
         	{
         		mk=0;
         		Obj.removeElementAt(i);
         		i--;
         	}
         	//�������е��ж�
    	}
    	//��ʼ���е��ж�
    	while(mr==1)
    	{
    		mr=0;
        
    		if(delCZero==true ) delColZero();//ɾ��ȫ���У�������������
    		if(delCOne==true ) delColOne();//ɾ��ȫһ�У�������������
    		//���ñ�־λΪ��
    		for(int i=0;i<attNum;i++)
    			rowMarks[i]=0;
        
    		if(delCSame==true ) delColSame();//�жϲ���ɾ����ͬ��
                    
    		//���ñ�־λΪ��
    		for (int i = 0; i < attNum; i++)
    			m[i] = 0;
    	}
    	//--�����Զ�ά��ϵ�Ĺ��죬������Ӧ��������
    	//��ʾ��ά��ϵ
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
            
            if(delRSame==true ) if(delRowSame(i)) mk=1;//�ж���ͬ��
            if(mk==1)
         	{
         		mk=0;
         		Obj.removeElementAt(i);
         		i--;
         	}
            countNum=0;
        }
        // ��ʼ���е��ж�
    	while(mr==1)
    	{
    		mr=0;
    		if(delCZero==true ) delColZero();//ɾ��ȫ���У�������������
    		if(delCOne==true ) delColOne();//ɾ��ȫһ�У�������������
    		//���ñ�־λΪ��
    		for(int i=0;i<attNum;i++)
    			rowMarks[i]=0;
    		if(delCSame==true ) delColSame();//�жϲ���ɾ����ͬ��
    		//���ñ�־λΪ��
    		for (int i = 0; i < attNum; i++)
    			m[i] = 0;
    	}
    	//--�����Զ�ά��ϵ�Ĺ��죬������Ӧ��������
    	//��ʾ��ά��ϵ
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
             // ����ͬ�У�ȫ����ȫһ�е��ж�
             if(delRSame==true ) if(delRowSame(i)) mk=1;//�ж���ͬ��
             if(delROne==true ) if( this.delRowOne(i) ) mk=1;//�ж�ȫһ��
             if(delRZero==true ) if( this.delRowZero(i) ) mk=1;//�ж�ȫ����
                            
             if(mk==1)
             {
             	mk=0;
             	Obj.removeElementAt(i);
             	i--;
             }
        //�������е��ж�
        }
        // ��ʼ���е��ж�
    	while(mr==1)
    	{
    		mr=0;
        
    		if(delCZero==true ) delColZero();//ɾ��ȫ���У�������������
    		if(delCOne==true ) delColOne();//ɾ��ȫһ�У�������������
    		//���ñ�־λΪ��
    		for(int i=0;i<attNum;i++)
    			rowMarks[i]=0;
        
    		if(delCSame==true ) delColSame();//�жϲ���ɾ����ͬ��
                    
    		//���ñ�־λΪ��
    		for (int i = 0; i < attNum; i++)
    			m[i] = 0;
    	}
    	//--�����Զ�ά��ϵ�Ĺ��죬������Ӧ��������

    	//��ʾ��ά��ϵ
    	showBinaryRelation();
    }
    //// --���ؼ���4.15
    /*private BinaryRelation(int nbObjs, int nbAtts,int num,int mk)
    {//���ڷָ���ʽ����
    	super(nbObjs, nbAtts,num,mk);
        
        for(int i = 0; i < aPourProp.size(); i++)
        {
            for(int j = 0; j < lesAtributs.size(); j++)  //��ʼ����ѹ��lesAtributs.size()��0,�Ż��Ƕȿ�������ʡ��
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
        {//ko  ka���ڶ��µ���ʽ��������
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
        }//�Զ��ָ�endif mk==0
        
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
        }//����ָ�endif mk==1
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
        }//����ָ�endif mk==2
    }*/
    /// --���ؼ���4.15-----------------------------------------------
    
    //����4.13
    /*private BinaryRelation(int nbObjs,int nbAtts,Vector Obj,int att,StringBuffer s[])
    {//�Ѷ�ֵ�Ĺ�ϵת����Ϊ��ֵ�Ĺ�ϵ�������ݱ������壺������Ŀ��������Ŀ���洢λ�ã��ļ���������Obj�������ĳ��ȣ������Ե�����
    	
    	super(nbObjs, nbAtts,s);//���������Ƶ��޸ģ�
        
        for(int i = 0; i < aPourProp.size(); i++)
        {
            for(int j = 0; j < lesAtributs.size(); j++)  //��ʼ����ѹ��lesAtributs.size()��0,�Ż��Ƕȿ�������ʡ��
                ((Vector)aPourProp.elementAt(i)).addElement(new FormalAttributeValue());
        }
        
        for(int i=0;i<nbObjs;i++)
        {
        	for(int j=0;j<att;j++)
        	{
        		String ss=((Vector)(Obj.get(i))) . get(j).toString();
        		String ssnm=new String("a_lie"+(j+1)+"_zhi"+ss);//�õ���Ӧ����������
        		//System.out.println("?"+ssnm+"?"+j+"?"+nbAtts);
        		for(int k=0;k<nbAtts;k++)
        		{//������������������������������ʾ��Ԫ��ϵ���˴������⣡������������������������
        			if(s[k].toString().equals(ssnm)) this.setRelation(i,k,true);
        			else this.setRelation(i,k,false);
        		}
        	}
        }
    }*/
    //����4.13------------------------------
    
    /*private BinaryRelation(String tbnm)
    {
    	super(tbnm);
    }*/
    //��غ���
    
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
        {//�ж���ͬ��
            if( Obj.get(k).equals(Obj.get(i)) ) return true;
        }
    	return false;
    }
    
    private void delColZero()
    {
    	for (int i = 0; i < attNum; i++) 
        { //ȫ����ж�
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
        { //ȫһ���ж�
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
        { //�����ͬ��
            for (int j = 0; j < objNum; j++) 
            {
                for (int k = 1; i + k < attNum; k++) 
                {
                    if ((m[i + k] == i) && !((Vector) Obj.get(j)).get(i).equals(((Vector) Obj.get(j)).get(i +k)))
                        m[i + k]++;
                    if ((j == objNum - 1) && (m[i + k] == i))
                    { //�ж��ǲ�������ͬ��
                        m[i + k] = -1;
                        mr = 1; //������ͬ�У�Ҫ������������
                    }
                }
            }
        }
        //ɾ����ͬ����
        for (int i = attNum - 1; i >= 0; i--) 
        {
            if (m[i] == -1)
            {
                for (int j = 0; j < objNum; j++) 
                {
                    ((Vector) Obj.get(j)).removeElementAt(i);
                    byte a = (byte) rand.nextInt(2);
                    ((Vector) Obj.get(j)).add(i,new Integer(a));//��������
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
	String tbnm=tbnm1.replace(':','_');//����
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
	{//�������
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
{//��obj������ת����String
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