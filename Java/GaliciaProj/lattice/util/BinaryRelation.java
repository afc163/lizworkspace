// Decompiled by Jad v1.5.7g. Copyright 2000 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/SiliconValley/Bridge/8617/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi 
// Source File Name:   BinaryRelation.java

package lattice.util;

import java.io.PrintStream;
import java.util.*;

// Referenced classes of package lattice.util:
//            AbstractRelation, FormalAttributeValue, BadInputDataException, AbstractFormalAttributeValue, 
//            SetExtent, SetIntent, FormalObject, FormalAttribute, 
//            Concept, ConceptLattice, Node, Intent, 
//            Extent

public class BinaryRelation extends AbstractRelation
{

    public static int NO_SORT = 0;
    public static int AS_EXTENT_SORTED = 1;
    public static int DS_EXTENT_SORTED = 2;
    public static int AS_INTENT_SORTED = 3;
    public static int DS_INTENT_SORTED = 4;
    
    
    //李拓加于3.29
    //定义变量
    int ObjNum,AttNum;
    Vector Obj=new Vector();
    Vector Att=new Vector();
    Vector att0=new Vector();
    Vector att1=new Vector();
    Vector rowmark=new Vector();

    Random rand=new Random();
    int rowmarks[];
    int m[];
    int mk=0;//判断是否删除行标志
    int mr=1;//判断是否删除列标志
    //--李拓加于3.29
    
    
    public BinaryRelation(int nbObjs, int nbAtts)
    {  //理解二元关系的数据结构
        super(nbObjs, nbAtts);
        
        for(int i = 0; i < aPourProp.size(); i++)
        {
            for(int j = 0; j < lesAtributs.size(); j++)  //初始化，压入lesAtributs.size()个0,优化角度看，可以省略
                ((Vector)aPourProp.elementAt(i)).addElement(new FormalAttributeValue());

        }
    }
    
    //  李拓加于3.29
    public BinaryRelation(int nbObjs,int nbAtts,int marks,int pp)
    { //marks标志1的概率 3值 ,pp表示1的概率
    	super(nbObjs, nbAtts);
        
        for(int i = 0; i < aPourProp.size(); i++)
        {
            for(int j = 0; j < lesAtributs.size(); j++)  //初始化，压入lesAtributs.size()个0,优化角度看，可以省略
                ((Vector)aPourProp.elementAt(i)).addElement(new FormalAttributeValue());

        }
    	
        //编写代码生成二维关系，并响应用户要求
        ObjNum=nbObjs;
        AttNum=nbAtts;
        rowmarks=new int [AttNum];
        m=new int[AttNum];
        for(int i=0;i<AttNum;i++)
        {
            rowmarks[i] = 0;
            m[i]=0;
        }
        byte a1 = 1;
        Integer b1 = new Integer(a1);
        for(int i=0;i<AttNum;i++)
            att1.add(i,b1);

        byte a0 = 0;
        Integer b0 = new Integer(a0);
        for(int i=0;i<AttNum;i++)
            att0.add(i,b0);

        if(marks==0)//对一概率没有要求
        {
        		for(int i=0;i<ObjNum;i++)
            	{
                 	Att.clear();
                 	for (int j = 0; j < AttNum; j++)
                 	{
                 		byte a = (byte)rand.nextInt(2);
                 		Integer b = new Integer(a);
                 		if (a == 0) rowmarks[j]--;
                 		if (a == 1) rowmarks[j]++;
                 		Att.add(b);
                 	}
                 	Obj.add(Att.clone());
                                
                 	//对相同行，全零行全一行的判断
                 	if(1==1 ) if(delsamecol(i)) mk=1;//判断相同行
                 	if(1==1 ) if( this.delcolone(i) ) mk=1;//判断全一行
                 	if(1==1 ) if( this.delcolzero(i) ) mk=1;//判断全零行
                                
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
                
            		if(1==1 ) delrowzero();//删除全零列，并且生成新列
            		if(1==1 ) delrowone();//删除全一列，并且生成新列
            		//重置标志位为零
            		for(int i=0;i<AttNum;i++)
            			rowmarks[i]=0;
                
            		if(1==1 ) delsamerow();//判断并且删除相同列
                            
            		//重置标志位为零
            		for (int i = 0; i < AttNum; i++)
            			m[i] = 0;
            	}
            	//--结束对二维关系的构造，并且相应操作结束
        
            	//显示二维关系
            	showBR();
        }//--结束对一概率无要求的显示二维关系
        
        //1行概率有要求
        if(marks==1)
        {
        	int number=AttNum*pp/100;
            int t=0;
            double p=pp*1.0/100.0;
            int r[]=new int [AttNum];
            double aa = rand.nextDouble();
            for(int i=0;i<ObjNum;i++)
            {
                Att.clear();
                while(t!=number)
                {
                    t=0;
                    for(int c=0;c<AttNum;c++)
                    {
                        if(t==number) r[c]=0;
                        else
                        {
                            aa=rand.nextDouble();
                            if (aa < p)
                            {
                                r[c] = 1;
                                t++;
                            }
                            if (aa >= p) r[c] = 0;
                        }
                    }
                }

                for (int j = 0; j < AttNum; j++)
                {
                    if (r[j] == 1) rowmarks[j]++;
                    if (r[j] == 0) rowmarks[j]--;
                    int a = r[j];
                    Integer b = new Integer(a);
                    Att.add(b);
                }
                Obj.add(Att.clone());
                
                if(1==1 ) if(delsamecol(i)) mk=1;//判断相同行
                if(mk==1)
             	{
             		mk=0;
             		Obj.removeElementAt(i);
             		i--;
             	}
                t=0;
            }
            // 开始对列的判断
        	while(mr==1)
        	{
        		mr=0;
            
        		if(1==1 ) delrowzero();//删除全零列，并且生成新列
        		if(1==1 ) delrowone();//删除全一列，并且生成新列
        		//重置标志位为零
        		for(int i=0;i<AttNum;i++)
        			rowmarks[i]=0;
            
        		if(1==1 ) delsamerow();//判断并且删除相同列
                        
        		//重置标志位为零
        		for (int i = 0; i < AttNum; i++)
        			m[i] = 0;
        	}
        	//--结束对二维关系的构造，并且相应操作结束
    
        	//显示二维关系
        	showBR();
        }//结束对行概率1的要求
        
        //对整体1的概率的要求
        if(marks==2)
        {
        	byte bb;
            for(int i=0;i<ObjNum;i++)
            {
                    Att.clear();
                    for (int j = 0; j < AttNum; j++)
                    {
                            double a = rand.nextDouble();
                            double p = pp * 1.0 / 100.0;
                            if (a < p)
                            {
                                    rowmarks[j]++;
                                    bb=1;
                                    Integer b = new Integer(bb);
                                    Att.add(j, b);
                            }
                            if (a >= p)
                            {
                                    rowmarks[j]--;
                                    bb=0;
                                    Integer b = new Integer(bb);
                                    Att.add(j, b);
                            }
                    }
                    Obj.add(Att.clone());
                    // 对相同行，全零行全一行的判断
                 	if(1==1 ) if(delsamecol(i)) mk=1;//判断相同行
                 	if(1==1 ) if( this.delcolone(i) ) mk=1;//判断全一行
                 	if(1==1 ) if( this.delcolzero(i) ) mk=1;//判断全零行
                                
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
            
        		if(1==1 ) delrowzero();//删除全零列，并且生成新列
        		if(1==1 ) delrowone();//删除全一列，并且生成新列
        		//重置标志位为零
        		for(int i=0;i<AttNum;i++)
        			rowmarks[i]=0;
            
        		if(1==1 ) delsamerow();//判断并且删除相同列
                        
        		//重置标志位为零
        		for (int i = 0; i < AttNum; i++)
        			m[i] = 0;
        	}
        	//--结束对二维关系的构造，并且相应操作结束
    
        	//显示二维关系
        	showBR();
        }
    }
    
    //显示二元关系
    public void showBR()
    {
    	boolean s;
    	for(int i=0;i<ObjNum;i++)
    	{
    		for(int j=0;j<AttNum;j++)
    		{
    			String str=((Vector)Obj.get(i)).get(j).toString();
    			if(str.equals("0")) s=false;
    			else s=true;
    			setRelation(i,j,s);
    		}
    	}
    }
    
    //查找全0行
    public boolean delcolzero(int oindex)
    {
        Vector v = (Vector) Obj.get(oindex);
        if ( v.toString().equals(att0.toString()) ) return true;
        else return false;

    }

    //查找全1行
    public boolean delcolone(int oindex)
    {
            Vector v = (Vector) Obj.get(oindex);
            if ( v.toString().equals(att1.toString()) ) return true;
            else return false;
    }

    //查找相同行
    public boolean delsamecol(int i)
    {
    	for(int k=0;k<i;k++)
        {//判断相同行
            if( Obj.get(k).equals(Obj.get(i)) ) return true;
        }
    	return false;
    }
    
    //删除全0列
    public void delrowzero()
    {
    	for (int i = 0; i < AttNum; i++) 
        { //全零的判断
            while (rowmarks[i] == -ObjNum) 
            {
                rowmarks[i] = 0;
                for (int k = 0; k < ObjNum; k++) 
                {
                    byte a = (byte) rand.nextInt(2);
                    Integer b = new Integer(a);
                    if (a == 0) rowmarks[i]--;
                    if (a == 1) rowmarks[i]++;
                    ((Vector) Obj.get(k)).removeElementAt(i);
                    ((Vector) Obj.get(k)).add(i, b);
                }
            }
        }
    }
    
    //删除全1列
    public void delrowone()
    {
    	for (int i = 0; i < AttNum; i++) 
        { //全一的判断
            while (rowmarks[i] == ObjNum) 
            {
                rowmarks[i] = 0;
                for (int k = 0; k < ObjNum; k++) 
                {
                    byte a = (byte) rand.nextInt(2);
                    Integer b = new Integer(a);
                    if (a == 0) rowmarks[i]--;
                    if (a == 1) rowmarks[i]++;
                    ((Vector) Obj.get(k)).set(i, b);
                }
            }
        }
    }
    
    //删除相同列
    public void delsamerow()
    {
    	for (int i = 0; i < AttNum; i++) 
        { //检查相同列
            for (int j = 0; j < ObjNum; j++) 
            {
                for (int k = 1; i + k < AttNum; k++) 
                {
                    if ((m[i + k] == i) && !((Vector) Obj.get(j)).get(i).equals(((Vector) Obj.get(j)).get(i +k)))
                        m[i + k]++;
                    if ((j == ObjNum - 1) && (m[i + k] == i))
                    { //判断是不是有相同列
                        m[i + k] = -1;
                        mr = 1; //还有相同列，要重新生成新列
                    }
                }
            }
        }
        //删除相同的列
        for (int i = AttNum - 1; i >= 0; i--) 
        {
            if (m[i] == -1)
            {
                for (int j = 0; j < ObjNum; j++) 
                {
                    ((Vector) Obj.get(j)).removeElementAt(i);
                    byte a = (byte) rand.nextInt(2);
                    Integer b = new Integer(a);
                    ((Vector) Obj.get(j)).add(i,b);//生成新列
                    if(a==0)rowmarks[i]--;
                    if(a==1)rowmarks[i]++;
                }
                m[i] = 0;
                i++;
            }
        }
    }
    //--相关函数
    //--李拓加于3.29
    

    public void addObject()
    {
        super.addObject();
        for(int j = 0; j < lesAtributs.size(); j++)
            ((Vector)aPourProp.elementAt(aPourProp.size() - 1)).addElement(new FormalAttributeValue());

    }

    public void addAttribute()
    {
        super.addAttribute();
        for(int i = 0; i < aPourProp.size(); i++)
            ((Vector)aPourProp.elementAt(i)).addElement(new FormalAttributeValue());

    }

    public AbstractFormalAttributeValue getRelation(FormalObject obj, FormalAttribute att)
    {
        int idxO = lesObjets.indexOf(obj);
        int idxA = lesAtributs.indexOf(att);
        return (FormalAttributeValue)((Vector)aPourProp.elementAt(idxO)).elementAt(idxA);
    }

    public void setRelation(FormalObject Obj, FormalAttribute Att, AbstractFormalAttributeValue rel)
        throws BadInputDataException
    {
        setRelation(Obj, Att, (FormalAttributeValue)rel);
    }

    //具体操作，在内存中存放数据-----核心的核心的核心--------------------------
    public void setRelation(FormalObject Obj, FormalAttribute Att, FormalAttributeValue rel)
        throws BadInputDataException
    {
        if(!rel.toString().equals("0") && !rel.toString().equals("X"))
        {
            throw new BadInputDataException("Bad input format for Setting a Relation");
        } else
        {
            int idxO = lesObjets.indexOf(Obj);
            int idxA = lesAtributs.indexOf(Att);
            ((Vector)aPourProp.elementAt(idxO)).set(idxA, rel);  
            return;
        }
    }

    public void setRelation(int idxO, int idxA, boolean val)
    {
        if(idxO < lesObjets.size() && idxA < lesAtributs.size() && val)
            ((Vector)aPourProp.elementAt(idxO)).set(idxA, new FormalAttributeValue("X"));
        if(idxO < lesObjets.size() && idxA < lesAtributs.size() && !val)
            ((Vector)aPourProp.elementAt(idxO)).set(idxA, new FormalAttributeValue("0"));
    }

    public FormalAttributeValue getRelation(int idxO, int idxA)
    {
        return (FormalAttributeValue)((Vector)aPourProp.elementAt(idxO)).elementAt(idxA);
    }

    public void revertRelation(FormalObject obj, FormalAttribute att)
    {
        try
        {
        	//如果这里当前为0，则修改为选中状态
            if(getRelation(obj, att).toString() == "0")
                setRelation(obj, att, new FormalAttributeValue("X"));
            else
                setRelation(obj, att, new FormalAttributeValue("0"));
        }
        catch(Exception e)
        {
            System.out.println(e.getMessage() + "\n");
        }
    }

    public void revertRelation(int idxO, int idxA)
    {
        try
        {
            if(getRelation(idxO, idxA).isEmpty())
                setRelation(idxO, idxA, true);
            else
                setRelation(idxO, idxA, false);
        }
        catch(Exception e)
        {
            System.out.println(e.getMessage() + "\n");
        }
    }

    public Collection getInitialObjectPreConcept(int sortType)
    {
        Vector sortedSetOfConcept = new Vector();
        SetExtent obj = null;
        SetIntent lesAtts = null;
        for(int i = 0; i < lesObjets.size(); i++)
        {
            obj = new SetExtent();
            obj.add(lesObjets.elementAt(i));
            lesAtts = new SetIntent();
            for(int j = 0; j < lesAtributs.size(); j++)
            {
                AbstractFormalAttributeValue absfav = getRelation((FormalObject)lesObjets.elementAt(i), (FormalAttribute)lesAtributs.elementAt(j));
                if(!absfav.isEmpty())
                    lesAtts.add(lesAtributs.elementAt(j));
            }

            Concept newConcept = new Concept(obj, lesAtts);
            if(sortType == NO_SORT || sortedSetOfConcept.size() == 0)
            {
                sortedSetOfConcept.addElement(newConcept);
            } else
            {
                for(int j = 0; j < sortedSetOfConcept.size(); j++)
                {
                    if(sortType == AS_INTENT_SORTED && ((Concept)sortedSetOfConcept.elementAt(j)).getIntent().size() > newConcept.getIntent().size())
                    {
                        sortedSetOfConcept.insertElementAt(newConcept, j);
                        break;
                    }
                    if(sortType != DS_INTENT_SORTED || ((Concept)sortedSetOfConcept.elementAt(j)).getIntent().size() >= newConcept.getIntent().size())
                        continue;
                    sortedSetOfConcept.insertElementAt(newConcept, j);
                    break;
                }

            }
        }

        return sortedSetOfConcept;
    }

    public Collection getInitialAttributePreConcept(int sortType)
    {
        Vector sortedSetOfConcept = new Vector();
        SetExtent lesObjs = null;
        SetIntent att = null;
        for(int i = 0; i < lesAtributs.size(); i++)
        {
            att = new SetIntent();
            att.add(lesAtributs.elementAt(i));
            lesObjs = new SetExtent();
            for(int j = 0; j < lesObjets.size(); j++)
            {
                AbstractFormalAttributeValue absfav = getRelation((FormalObject)lesObjets.elementAt(j), (FormalAttribute)lesAtributs.elementAt(i));
                if(!absfav.isEmpty())
                    lesObjs.add(lesObjets.elementAt(j));
            }

            Concept newConcept = new Concept(lesObjs, att);
            if(sortType == NO_SORT)
            {
                sortedSetOfConcept.addElement(newConcept);
            } else
            {
                boolean trouve = false;
                for(int j = 0; j < sortedSetOfConcept.size() && !trouve; j++)
                {
                    Concept c = (Concept)sortedSetOfConcept.elementAt(j);
                    if(sortType == AS_EXTENT_SORTED && c.getExtent().size() > newConcept.getExtent().size())
                    {
                        sortedSetOfConcept.insertElementAt(newConcept, j);
                        trouve = true;
                    }
                    if(sortType == DS_EXTENT_SORTED && c.getExtent().size() < newConcept.getExtent().size())
                    {
                        sortedSetOfConcept.insertElementAt(newConcept, j);
                        trouve = true;
                    }
                }

                if(!trouve)
                    sortedSetOfConcept.add(newConcept);
            }
        }

        return sortedSetOfConcept;
    }

    public Intent getF(FormalObject o)
    {
        Intent intent = new SetIntent();
        //o对象所对应得索引号
        int idxO = lesObjets.indexOf(o);
        for(int j = 0; j < lesAtributs.size(); j++)
        {
        	//为什么不直接把o所对应得属性加入到intent中去呢？
            AbstractFormalAttributeValue absfav = getRelation((FormalObject)lesObjets.elementAt(idxO), (FormalAttribute)lesAtributs.elementAt(j));
            if(!absfav.isEmpty())  //此处判断是否有X
                intent.add(lesAtributs.elementAt(j));
        }

        return intent;
    }

    public Extent getG(FormalAttribute a)
    {
        Extent extent = new SetExtent();
        int idxA = lesAtributs.indexOf(a);
        for(int j = 0; j < lesObjets.size(); j++)
        {
            AbstractFormalAttributeValue absfav = getRelation((FormalObject)lesObjets.elementAt(j), (FormalAttribute)lesAtributs.elementAt(idxA));
            if(!absfav.isEmpty())
                extent.add(lesObjets.elementAt(j));
        }

        return extent;
    }

    public Object clone()
    {
        BinaryRelation binRel = new BinaryRelation(lesObjets.size(), lesAtributs.size());
        binRel.setRelationName(getRelationName());
        binRel.lesObjets = (Vector)lesObjets.clone();
        binRel.lesAtributs = (Vector)lesAtributs.clone();
        for(int i = 0; i < lesObjets.size(); i++)
        {
            for(int j = 0; j < lesAtributs.size(); j++)
                binRel.setRelation(i, j, !getRelation(i, j).isEmpty());

        }

        return binRel;
    }

    public void emptyRelation()
    {
        for(int i = 0; i < lesObjets.size(); i++)
        {
            for(int j = 0; j < lesAtributs.size(); j++)
                if(!getRelation(i, j).isEmpty())
                    revertRelation(i, j);

        }

    }

    public static BinaryRelation getInstanceFromLattice(ConceptLattice lcl)
    {
        Vector objs = new Vector();
        Vector atts = new Vector();
        for(Iterator it = lcl.getTop().getConcept().getExtent().iterator(); it.hasNext(); objs.add(it.next()));
        lcl.fillSimplify();
        lcl.getTop().resetDegre();
        Vector Q = new Vector();
        Q.add(lcl.getTop());
        while(Q.size() != 0) 
        {
            Node nodeToTest = (Node)Q.remove(0);
            for(Iterator it = nodeToTest.getChildren().iterator(); it.hasNext();)
            {
                Node P = (Node)it.next();
                if(P.getDegre() == -1)
                    P.setDegre(P.getParents().size());
                P.setDegre(P.getDegre() - 1);
                if(P.getDegre() == 0)
                    Q.add(P);
            }

            for(Iterator it = nodeToTest.getConcept().getSimplifyIntent().iterator(); it.hasNext(); atts.add(it.next()));
        }
        BinaryRelation binRel = new BinaryRelation(objs.size(), atts.size());
        try
        {
            for(int i = 0; i < objs.size(); i++)
                binRel.replaceObject(i, (FormalObject)objs.elementAt(i));

            for(int i = 0; i < atts.size(); i++)
                binRel.replaceAttribute(i, (FormalAttribute)atts.elementAt(i));

        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        lcl.getTop().resetDegre();
        Q = new Vector();
        Q.add(lcl.getTop());
        while(Q.size() != 0) 
        {
            Node nodeToTest = (Node)Q.remove(0);
            for(Iterator it = nodeToTest.getChildren().iterator(); it.hasNext();)
            {
                Node P = (Node)it.next();
                if(P.getDegre() == -1)
                    P.setDegre(P.getParents().size());
                P.setDegre(P.getDegre() - 1);
                if(P.getDegre() == 0)
                    Q.add(P);
            }

            for(Iterator it = nodeToTest.getConcept().getIntent().iterator(); it.hasNext();)
            {
                FormalAttribute fa = (FormalAttribute)it.next();
                FormalObject fo;
                for(Iterator it2 = nodeToTest.getConcept().getSimplifyExtent().iterator(); it2.hasNext(); binRel.revertRelation(fo, fa))
                    fo = (FormalObject)it2.next();

            }

        }
        return binRel;
    }

}
