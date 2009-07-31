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
    
    
    //���ؼ���3.29
    //�������
    int ObjNum,AttNum;
    Vector Obj=new Vector();
    Vector Att=new Vector();
    Vector att0=new Vector();
    Vector att1=new Vector();
    Vector rowmark=new Vector();

    Random rand=new Random();
    int rowmarks[];
    int m[];
    int mk=0;//�ж��Ƿ�ɾ���б�־
    int mr=1;//�ж��Ƿ�ɾ���б�־
    //--���ؼ���3.29
    
    
    public BinaryRelation(int nbObjs, int nbAtts)
    {  //����Ԫ��ϵ�����ݽṹ
        super(nbObjs, nbAtts);
        
        for(int i = 0; i < aPourProp.size(); i++)
        {
            for(int j = 0; j < lesAtributs.size(); j++)  //��ʼ����ѹ��lesAtributs.size()��0,�Ż��Ƕȿ�������ʡ��
                ((Vector)aPourProp.elementAt(i)).addElement(new FormalAttributeValue());

        }
    }
    
    //  ���ؼ���3.29
    public BinaryRelation(int nbObjs,int nbAtts,int marks,int pp)
    { //marks��־1�ĸ��� 3ֵ ,pp��ʾ1�ĸ���
    	super(nbObjs, nbAtts);
        
        for(int i = 0; i < aPourProp.size(); i++)
        {
            for(int j = 0; j < lesAtributs.size(); j++)  //��ʼ����ѹ��lesAtributs.size()��0,�Ż��Ƕȿ�������ʡ��
                ((Vector)aPourProp.elementAt(i)).addElement(new FormalAttributeValue());

        }
    	
        //��д�������ɶ�ά��ϵ������Ӧ�û�Ҫ��
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

        if(marks==0)//��һ����û��Ҫ��
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
                                
                 	//����ͬ�У�ȫ����ȫһ�е��ж�
                 	if(1==1 ) if(delsamecol(i)) mk=1;//�ж���ͬ��
                 	if(1==1 ) if( this.delcolone(i) ) mk=1;//�ж�ȫһ��
                 	if(1==1 ) if( this.delcolzero(i) ) mk=1;//�ж�ȫ����
                                
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
                
            		if(1==1 ) delrowzero();//ɾ��ȫ���У�������������
            		if(1==1 ) delrowone();//ɾ��ȫһ�У�������������
            		//���ñ�־λΪ��
            		for(int i=0;i<AttNum;i++)
            			rowmarks[i]=0;
                
            		if(1==1 ) delsamerow();//�жϲ���ɾ����ͬ��
                            
            		//���ñ�־λΪ��
            		for (int i = 0; i < AttNum; i++)
            			m[i] = 0;
            	}
            	//--�����Զ�ά��ϵ�Ĺ��죬������Ӧ��������
        
            	//��ʾ��ά��ϵ
            	showBR();
        }//--������һ������Ҫ�����ʾ��ά��ϵ
        
        //1�и�����Ҫ��
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
                
                if(1==1 ) if(delsamecol(i)) mk=1;//�ж���ͬ��
                if(mk==1)
             	{
             		mk=0;
             		Obj.removeElementAt(i);
             		i--;
             	}
                t=0;
            }
            // ��ʼ���е��ж�
        	while(mr==1)
        	{
        		mr=0;
            
        		if(1==1 ) delrowzero();//ɾ��ȫ���У�������������
        		if(1==1 ) delrowone();//ɾ��ȫһ�У�������������
        		//���ñ�־λΪ��
        		for(int i=0;i<AttNum;i++)
        			rowmarks[i]=0;
            
        		if(1==1 ) delsamerow();//�жϲ���ɾ����ͬ��
                        
        		//���ñ�־λΪ��
        		for (int i = 0; i < AttNum; i++)
        			m[i] = 0;
        	}
        	//--�����Զ�ά��ϵ�Ĺ��죬������Ӧ��������
    
        	//��ʾ��ά��ϵ
        	showBR();
        }//�������и���1��Ҫ��
        
        //������1�ĸ��ʵ�Ҫ��
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
                    // ����ͬ�У�ȫ����ȫһ�е��ж�
                 	if(1==1 ) if(delsamecol(i)) mk=1;//�ж���ͬ��
                 	if(1==1 ) if( this.delcolone(i) ) mk=1;//�ж�ȫһ��
                 	if(1==1 ) if( this.delcolzero(i) ) mk=1;//�ж�ȫ����
                                
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
            
        		if(1==1 ) delrowzero();//ɾ��ȫ���У�������������
        		if(1==1 ) delrowone();//ɾ��ȫһ�У�������������
        		//���ñ�־λΪ��
        		for(int i=0;i<AttNum;i++)
        			rowmarks[i]=0;
            
        		if(1==1 ) delsamerow();//�жϲ���ɾ����ͬ��
                        
        		//���ñ�־λΪ��
        		for (int i = 0; i < AttNum; i++)
        			m[i] = 0;
        	}
        	//--�����Զ�ά��ϵ�Ĺ��죬������Ӧ��������
    
        	//��ʾ��ά��ϵ
        	showBR();
        }
    }
    
    //��ʾ��Ԫ��ϵ
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
    
    //����ȫ0��
    public boolean delcolzero(int oindex)
    {
        Vector v = (Vector) Obj.get(oindex);
        if ( v.toString().equals(att0.toString()) ) return true;
        else return false;

    }

    //����ȫ1��
    public boolean delcolone(int oindex)
    {
            Vector v = (Vector) Obj.get(oindex);
            if ( v.toString().equals(att1.toString()) ) return true;
            else return false;
    }

    //������ͬ��
    public boolean delsamecol(int i)
    {
    	for(int k=0;k<i;k++)
        {//�ж���ͬ��
            if( Obj.get(k).equals(Obj.get(i)) ) return true;
        }
    	return false;
    }
    
    //ɾ��ȫ0��
    public void delrowzero()
    {
    	for (int i = 0; i < AttNum; i++) 
        { //ȫ����ж�
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
    
    //ɾ��ȫ1��
    public void delrowone()
    {
    	for (int i = 0; i < AttNum; i++) 
        { //ȫһ���ж�
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
    
    //ɾ����ͬ��
    public void delsamerow()
    {
    	for (int i = 0; i < AttNum; i++) 
        { //�����ͬ��
            for (int j = 0; j < ObjNum; j++) 
            {
                for (int k = 1; i + k < AttNum; k++) 
                {
                    if ((m[i + k] == i) && !((Vector) Obj.get(j)).get(i).equals(((Vector) Obj.get(j)).get(i +k)))
                        m[i + k]++;
                    if ((j == ObjNum - 1) && (m[i + k] == i))
                    { //�ж��ǲ�������ͬ��
                        m[i + k] = -1;
                        mr = 1; //������ͬ�У�Ҫ������������
                    }
                }
            }
        }
        //ɾ����ͬ����
        for (int i = AttNum - 1; i >= 0; i--) 
        {
            if (m[i] == -1)
            {
                for (int j = 0; j < ObjNum; j++) 
                {
                    ((Vector) Obj.get(j)).removeElementAt(i);
                    byte a = (byte) rand.nextInt(2);
                    Integer b = new Integer(a);
                    ((Vector) Obj.get(j)).add(i,b);//��������
                    if(a==0)rowmarks[i]--;
                    if(a==1)rowmarks[i]++;
                }
                m[i] = 0;
                i++;
            }
        }
    }
    //--��غ���
    //--���ؼ���3.29
    

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

    //������������ڴ��д������-----���ĵĺ��ĵĺ���--------------------------
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
        	//������ﵱǰΪ0�����޸�Ϊѡ��״̬
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
        //o��������Ӧ��������
        int idxO = lesObjets.indexOf(o);
        for(int j = 0; j < lesAtributs.size(); j++)
        {
        	//Ϊʲô��ֱ�Ӱ�o����Ӧ�����Լ��뵽intent��ȥ�أ�
            AbstractFormalAttributeValue absfav = getRelation((FormalObject)lesObjets.elementAt(idxO), (FormalAttribute)lesAtributs.elementAt(j));
            if(!absfav.isEmpty())  //�˴��ж��Ƿ���X
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
