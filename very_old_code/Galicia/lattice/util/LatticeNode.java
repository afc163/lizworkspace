// Decompiled by Jad v1.5.7g. Copyright 2000 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/SiliconValley/Bridge/8617/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi 
// Source File Name:   LatticeNode.java
//��latticeNode �е����з���ֻ��������ڵ�Ĳ����ҹ�

package lattice.util;

import java.util.*;

// Referenced classes of package lattice.util:
//            Node, Concept

public class LatticeNode
    implements Node
{

    public Integer id; //�ڵ���
    public boolean visited;
    
   
    
  
    public Concept concept;    //����
    public Set parents;     //˫�׼���
    public List children;    //�����б�
    public static int next_id = 0;   //���ڲ������
    private int degre;

    public LatticeNode(Concept concept)
    {
        visited = false;
        degre = -1;
        this.concept = concept;
        parents = new HashSet();
        children = new Vector();
        id = new Integer(next_id);  //���صĵط�
        next_id++;
    }

    public LatticeNode(Integer id, Concept concept)
    {
        visited = false;
        degre = -1;
        this.concept = concept;
        parents = new HashSet();
        children = new Vector();
        this.id = id;
        if(id.compareTo(new Integer(next_id)) > 0 || id.compareTo(new Integer(next_id)) == 0)
            next_id = id.intValue() + 1;
        
    }
    
    //by cjj 2005-4-8
    //����slit�Ĺ��캯��
    public LatticeNode(Concept concept,Slit slit)
    {
        visited = false;
        degre = -1;
        this.concept = concept;
        parents = new HashSet();
        children = new Vector();
        id = new Integer(next_id);  //���صĵط�
        next_id++;
    }

    //--
    public Concept getConcept()
    {
        return concept;
    }
    
   

    public Integer getId()
    {
        return id;
    }

    public Set getParents()
    {
        return parents;
    }

    public void addParent(Node N)
    {
        if(!parents.contains(N))
            parents.add(N);
    }

    public void removeParent(Node N)
    {
        parents.remove(N);
    }

    public List getChildren()
    {
        return children;
    }

    public void addChild(Node N)
    {
        if(!children.contains(N))
            children.add(N);
    }

    public void removeChild(Node N)
    {
        children.remove(N);
    }

    public boolean getVisited()
    {
        return visited;
    }

    public void setVisited(boolean b)
    {
        visited = b;
    }

    public String toString()
    {
        return String.valueOf(id);
    }

    public boolean equals(Object o)
    {
        Concept c = ((Node)o).getConcept();
        return getConcept().equals(c);
    }

    public void setDegre(int d)
    {
        degre = d;
    }

    public int getDegre()
    {
        return degre;
    }

    public void resetDegre()
    {
        degre = -1;
        for(Iterator it = getChildren().iterator(); it.hasNext(); ((LatticeNode)it.next()).resetDegre());
    }

}
