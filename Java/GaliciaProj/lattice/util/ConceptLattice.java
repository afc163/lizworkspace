// Decompiled by Jad v1.5.7g. Copyright 2000 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/SiliconValley/Bridge/8617/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi 
// Source File Name:   ConceptLattice.java

package lattice.util;

import java.util.*;

// Referenced classes of package lattice.util:
//            Node, Concept, Intent

public interface ConceptLattice
{

	//返回格节点的数目
    public abstract int size();

    //判断格节点个数是否为0
    public abstract boolean isEmpty();

    //返回最底层的节点（intent最大的）
    public abstract Node getBottom();

    public abstract void setBottom(Node node);

    public abstract Node findBottom();

    public abstract Node getTop();

    public abstract void setTop(Node node);

    public abstract Node findTop();

    public abstract Iterator children(Node node);

    public abstract Iterator parents(Node node);

    public abstract Iterator iterator();

    public abstract int get_nbr_concept();

    public abstract void set_nbr_concept(int i);

    public abstract void inc_nbr_concept();
    
    //by cjj 2005-4-14
    public int getnbr_concept();

    public abstract int get_max_transaction_size();

    public abstract void set_max_transaction_size(int i);

    public abstract Map get_item_index();

    public abstract Vector get_intent_level_index();  
   
    public abstract void addNodeToIntentLevelIndex(Node node);

    public abstract void initialiseIntentLevelIndex(int i);

    public abstract void addBottomToIntentLevelIndex(Node node);

    public abstract void adjustBottom(Concept concept);

    public abstract void initialiseArray(Vector avector[]);

    public abstract boolean isAGenerator(Intent intent, Vector avector[]);

    public abstract void modifyEdges(Node node, Node node1, Vector avector[]);

    public abstract void modifyEdgesAD(Node node, Node node1, Vector avector[]);
    public abstract void initialiseVector(Vector vector, int i);

    public abstract void updateIndexWithNewIntents(Intent intent);

    public abstract void linkNodeToUpperCover(Node node, Node node1);

    public abstract boolean isAMofiedNode(Concept concept, Node node);

    public abstract Vector[] candidates(Node node, Node anode[]);

    public abstract boolean equals(Object obj);

    public abstract void fillSimplify();

    public abstract String getDescription();

    public abstract void setDescription(String s);
    
    //by cjj 2005.1.10
  //  public abstract void findSLIT(Node node);
    
    public abstract void del_chd(Node Ipt,Node Icd);
    public abstract void add_chd(Node Ipt,Node Icd);
}
