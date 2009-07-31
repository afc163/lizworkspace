// Decompiled by Jad v1.5.7g. Copyright 2000 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/SiliconValley/Bridge/8617/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi 
// Source File Name:   LevelIndex.java

package lattice.util;

import java.util.Set;
import java.util.Vector;

// Referenced classes of package lattice.util:
//            LatticeNode, Node, Concept

class LevelIndex
{

    public Vector intent_level_index;

    public LevelIndex()
    {
        intent_level_index = new Vector();
    }

    public Vector get_intent_level_index()
    {
        return intent_level_index;
    }

    public Vector lastElement()
    {
        return (Vector)intent_level_index.lastElement();
    }

    public Vector get(int i)
    {
        return (Vector)intent_level_index.get(i);
    }

    public int size()
    {
        return intent_level_index.size();
    }

    public void add(Vector v)
    {
        intent_level_index.add(v);
    }

    public LatticeNode first(int i)
    {
        return (LatticeNode)get(i).elementAt(0);
    }

    public boolean isEmpty(int i)
    {
        return ((Vector)intent_level_index.get(i)).isEmpty();
    }

    public void initialiseIntentLevelIndex(int size)
    {
        for(int i = 0; i < size; i++)
            intent_level_index.add(new Vector());

    }

    public void addNodeToIntentLevelIndex(Node node)
    {
    	
    	if(intent_level_index.size()<node.getConcept().getIntent().size())
    	{
    		int length=intent_level_index.size();
    	//	LatticeNode tempnode=new LatticeNode(null);
    		//Vector tempnode=new Vector();
    		//by cjj 2005-8-1 <ÐÞ¸ÄÎª<=
    		for(int i=0;i<=node.getConcept().getIntent().size()-length;i++)
    		{
    			Vector tempnode=new Vector();
    			intent_level_index.add(tempnode);
    		}
    	}
    	
    	
        ((Vector)intent_level_index.elementAt(node.getConcept().getIntent().size())).add(node);
    }

    public void addBottomToIntentLevelIndex(Node node)
    {
        lastElement().add(node);
    }

    public void removeNodeFromIntentLevelIndex(Node node)
    {
        lastElement().remove(node);
    }

    public void removeBottomFromIntentLevelIndex(Node node)
    {
        lastElement().remove(node);
    }
}
