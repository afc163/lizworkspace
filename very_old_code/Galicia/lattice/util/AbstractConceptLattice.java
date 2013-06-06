// Decompiled by Jad v1.5.7g. Copyright 2000 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/SiliconValley/Bridge/8617/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi 
// Source File Name:   AbstractConceptLattice.java

package lattice.util;

import java.util.*;

// Referenced classes of package lattice.util:
//            ConceptLattice, Node, Concept, FormalObject, 
//            FormalAttribute, Intent

public abstract class AbstractConceptLattice
    implements ConceptLattice
{

    String description;

    public AbstractConceptLattice()
    {
        description = "No Description";
    }

    public void fillSimplify()
    {
        if(getTop() != null)
            fillSimplifyRec(getTop());
    }

    public void fillSimplifyRec(Node N)
    {
        if(N.getConcept().getSimplifyExtent().size() == 0 && N.getConcept().getSimplifyIntent().size() == 0)
        {
            N.getConcept().getSimplifyExtent().addAll(N.getConcept().getExtent());
            for(Iterator it = N.getConcept().getExtent().iterator(); it.hasNext();)
            {
                FormalObject fo = (FormalObject)it.next();
                boolean trouve = false;
                for(Iterator it2 = N.getChildren().iterator(); !trouve && it2.hasNext();)
                    if(((Node)it2.next()).getConcept().getExtent().contains(fo))
                        trouve = true;

                if(trouve)
                    N.getConcept().getSimplifyExtent().remove(fo);
            }

            N.getConcept().getSimplifyIntent().addAll(N.getConcept().getIntent());
            for(Iterator it = N.getConcept().getIntent().iterator(); it.hasNext();)
            {
                FormalAttribute fa = (FormalAttribute)it.next();
                boolean trouve = false;
                for(Iterator it2 = N.getParents().iterator(); !trouve && it2.hasNext();)
                    if(((Node)it2.next()).getConcept().getIntent().contains(fa))
                        trouve = true;

                if(trouve)
                    N.getConcept().getSimplifyIntent().remove(fa);
            }

            for(Iterator it = N.getChildren().iterator(); it.hasNext(); fillSimplifyRec((Node)it.next()));
        }
    }

    public String getDescription()
    {
        return description;
    }

    public void setDescription(String aDesc)
    {
        description = aDesc;
    }

    public abstract int size();

    public abstract boolean isEmpty();

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

    public abstract void initialiseVector(Vector vector, int i);

    public abstract void updateIndexWithNewIntents(Intent intent);

    public abstract void linkNodeToUpperCover(Node node, Node node1);

    public abstract boolean isAMofiedNode(Concept concept, Node node);

    public abstract Vector[] candidates(Node node, Node anode[]);
    
    //by cjj 2005-4-8
    public abstract void del_chd(Node Ipt,Node Icd);
    public abstract void add_chd(Node Ipt,Node Icd);
    //--
}
