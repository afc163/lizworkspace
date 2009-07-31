// Decompiled by Jad v1.5.7g. Copyright 2000 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/SiliconValley/Bridge/8617/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi 
// Source File Name:   LatticeGraph.java

package lattice.util;

import java.util.*;

// Referenced classes of package lattice.util:
//            AbstractConceptLattice, Node, Concept, Intent

public class LatticeGraph extends AbstractConceptLattice
{

    protected Vector setOfNodes;
    protected Node top;
    protected Node bottom;

    public LatticeGraph()
    {
        top = null;
        bottom = null;
        setOfNodes = new Vector();
    }

    public int size()
    {
        return setOfNodes.size();
    }

    public boolean isEmpty()
    {
        return size() == 2 && top.getConcept().getIntent().size() == 0 && bottom.getConcept().getExtent().size() == 0;
    }

    public Node getBottom()
    {
        return bottom;
    }

    public void setBottom(Node N)
    {
        if(bottom != null && bottom.getChildren().size() == 0 && bottom.getParents().size() == 0)
            setOfNodes.remove(bottom);
        bottom = N;
        if(N != null && !setOfNodes.contains(N))
            setOfNodes.add(N);
    }

    public void findNSetBottom()
    {
        int nbBot = 0;
        Node theBot = null;
        for(int i = 0; i < setOfNodes.size() && nbBot < 2; i++)
            if(((Node)setOfNodes.elementAt(i)).getChildren().size() == 0)
            {
                nbBot++;
                theBot = (Node)setOfNodes.elementAt(i);
            }

        if(nbBot == 1)
            bottom = theBot;
        else
            bottom = null;
    }

    public Node getTop()
    {
        return top;
    }

    public void setTop(Node N)
    {
        if(top != null && top.getChildren().size() == 0 && top.getParents().size() == 0)
            setOfNodes.remove(top);
        top = N;
        if(N != null && !setOfNodes.contains(N))
            setOfNodes.add(N);
    }

    public void findNSetTop()
    {
        int nbTop = 0;
        Node theTop = null;
        for(int i = 0; i < setOfNodes.size() && nbTop < 2; i++)
            if(((Node)setOfNodes.elementAt(i)).getParents().size() == 0)
            {
                nbTop++;
                theTop = (Node)setOfNodes.elementAt(i);
            }

        if(nbTop == 1)
            top = theTop;
        else
            top = null;
    }

    public Collection getAllNodes()
    {
        return setOfNodes;
    }

    public void addNode(Node N)
    {
        setOfNodes.add(N);
    }

    public Node findBottom()
    {
        return null;
    }

    public Node findTop()
    {
        return null;
    }

    public Iterator children(Node node)
    {
        return null;
    }

    public Iterator parents(Node node)
    {
        return null;
    }

    public Iterator iterator()
    {
        return null;
    }

    public int get_nbr_concept()
    {
        return 0;
    }

    public void set_nbr_concept(int i)
    {
    }

    public void inc_nbr_concept()
    {
    }

    public int get_max_transaction_size()
    {
        return 0;
    }

    public void set_max_transaction_size(int i)
    {
    }

    public Map get_item_index()
    {
        return null;
    }

    public Vector get_intent_level_index()
    {
        return null;
    }

    public void addNodeToIntentLevelIndex(Node node)
    {
    }

    public void initialiseIntentLevelIndex(int i)
    {
    }

    public void addBottomToIntentLevelIndex(Node node1)
    {
    }

    public void adjustBottom(Concept concept)
    {
    }

    public void initialiseArray(Vector avector[])
    {
    }

    public boolean isAGenerator(Intent i, Vector v[])
    {
        return false;
    }

    public void modifyEdges(Node node, Node node1, Vector avector[])
    {
    }

    public void initialiseVector(Vector vector, int j)
    {
    }

    public void updateIndexWithNewIntents(Intent intent)
    {
    }

    public void linkNodeToUpperCover(Node node, Node node1)
    {
    }

    public boolean isAMofiedNode(Concept c, Node n)
    {
        return false;
    }

    public Vector[] candidates(Node current_node, Node psi[])
    {
        return null;
    }
//  by cjj 2005-4-8
    public void del_chd(Node Ipt,Node Icd)
    { //Icd为子节点  Ipt为父亲节点
    	Slit SLITn=null;  //新产生的项集   
    	Intent Diff=(Intent)Icd.getConcept().getIntent().clone();
    	Diff.removeAll(Ipt.getConcept().getIntent());  //差集
    	Slit SLIT0=(Slit)Icd.getConcept().getSlit().clone();
    	for(Iterator iter_intent=Icd.getConcept().getSlit().iterator();iter_intent.hasNext();iter_intent.next())
    	{ //对于SLIT中的每个Item
    		Intent d=(Intent)((Intent)iter_intent.next()).clone();
    		if(Diff.size()==1)
    		{
    			//Slit s=new SetSlit();
    			Intent temp_d=(Intent)d.clone();
    			temp_d.removeAll(Diff);
    			//s.add(d);
    			//SLITn=SLITn.union(s);
    			SLITn.add(temp_d);
    			
    		}
    		else
    		{//|Diff|<>1
    			
    			Intent Temp=d.intersection(Diff);
    			Intent Genitem=(Intent)d.clone();
    			SLITn=(Slit)SLIT0.clone();
    			
    			if(Temp.size()==1)
    			{
    				boolean Flg=true;
    				for(Iterator iter_parent=Icd.getParents().iterator();iter_parent.hasNext();iter_parent.next())
    				{
    					Intent Chd=(Intent)((LatticeNode)(iter_parent.next())).getConcept().getIntent().clone();
    					Intent temp_Ipt=(Intent)Ipt.getConcept().getIntent().clone();
    					temp_Ipt.removeAll(Chd);
    					if(temp_Ipt.contains(Temp))
    					{
    						Flg=false;
    						break;
    					}
    				}
    				if(Flg)
    				{
    					Intent temp_d=(Intent)d.clone();
    					temp_d.removeAll(Temp);
    					Genitem=(Intent)temp_d.clone();
    				}
    				
    			}
    			
    			//SLITn=SLITn\Item
    			//Slit s=new SetSlit();
    			//s.add(d);
    			//SLITn=SLITn.difference(s);
    			
    			//by cjj 2005-4-9
    			SLITn.removeAll(d);
    			
    			if(!SLITn.isEmpty())
    			{
    				boolean Flg=true;
    				for(Iterator iter_it=SLITn.iterator();iter_it.hasNext();iter_it.next())
    				{
    					if(Genitem.contains(iter_it.next()))
    					{
    						Flg=false;
    						break;
    					}
    				}
    				if(Flg)
    				{
    				//	Slit ss=new SetSlit();
    				//	ss.add(Genitem);
    				//	SLITn=SLITn.union(ss);
    					SLITn.add(Genitem);
    				}
    			}
    			
    		}//end if
    		SLIT0=(Slit)SLITn.clone();
    		
    	}
    	Icd.getConcept().setSlit(SLITn);
    }
    
    public void add_chd(Node Icd,Node Ipt)
    { //函数调用时,new_node=>Ipt   parent_node=>Icd
    	Slit SLITn=null;  //新产生的项集   
    	Slit SLITk=null;  //保持不变的最小项集
    	//因为Icd比Ipt大,所以需要Icd-Ipt
    	Intent Diff=(Intent)Icd.getConcept().getIntent().clone();
    	Diff.removeAll(Ipt.getConcept().getIntent());
    	
    	for(Iterator iter_slit=Icd.getConcept().getSlit().iterator();iter_slit.hasNext();iter_slit.next())
    	{//扫描Slit中的每个项集item,现在把它假设成Intent吧
    		Intent Item=(Intent)iter_slit.next();
    		Intent Temp=(Intent)Item.clone();
    		Temp.intersection(Diff);
    		if(!Temp.isEmpty())
    		{
    			//Slit s=new SetSlit();  //暂时存放
    			//s.add(Item);
    			//SLITk=SLITk.union(s);
    			SLITk.add(Item);
    		}
    	}
    	
    	Slit Diff_SLIT=Icd.getConcept().getSlit().difference(SLITk);
    	
    	for(Iterator iter_slit=Diff_SLIT.iterator();iter_slit.hasNext();iter_slit.next())
    	{
    		for(Iterator iter_intent=Diff.iterator();iter_intent.hasNext();iter_intent.next())
    		{
    			//Slit s=new SetSlit();
    			//s.add((Object)iter_intent.next());
    			//Slit Genitem=((Slit)iter_slit.next()).union(s);
    			Intent Item1=(Intent)((Intent)iter_slit.next()).clone();
    			Object Element=(Object)iter_intent.next();
    			Item1.add(Element);
    			Intent Genitem=(Intent)Item1.clone();
    			if(!SLITk.isEmpty())
    			{
    				boolean Flg=true;
    				for(Iterator iter_it=SLITk.iterator();iter_it.hasNext();iter_it.next())
    				{
    					if(Genitem.contains(iter_it.next()))
    					{
    						Flg=false;
    						break;
    					}
    				}
    				if(Flg)
    				{
    					//SLITn=SLITn.union(Genitem);
    					SLITn.add(Genitem);
    				}
    			}
    		}
    		
    	}
    	SLITn=SLITn.union(SLITk);
    	Icd.getConcept().setSlit(SLITn);
    }
    //---
}
