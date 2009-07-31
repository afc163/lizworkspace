// Decompiled by Jad v1.5.7g. Copyright 2000 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/SiliconValley/Bridge/8617/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi 
// Source File Name:   AbstractGodinAlgorithm.java

package lattice.algorithm;

import java.io.PrintStream;
import java.util.*;
import lattice.gui.tooltask.AbstractJob;
import lattice.util.*;

// Referenced classes of package lattice.algorithm:
//            LatticeAlgorithm


//Godin算法德抽象类
public abstract class AbstractGodinAlgorithm extends LatticeAlgorithm
{

    public AbstractGodinAlgorithm()
    {
    }

    public AbstractGodinAlgorithm(BinaryRelation br)
    {
        super(br);
    }

    public abstract void addConcept(Concept concept);

    public void doAlgorithm()
    {
        long time = System.currentTimeMillis();
        addAll_Godin(binRel.getInitialObjectPreConcept(BinaryRelation.NO_SORT));
        binRel.setLattice(getLattice());
        System.out.println("FIN " + (System.currentTimeMillis() - time) + " mSec.\n");
    }

    public void addAll_Godin(Collection coll)
    {
        int maxClass = coll.size();
        int nbClass = 0;
        for(Iterator iter = coll.iterator(); iter.hasNext(); sendJobPercentage((nbClass * 100) / maxClass))
        {
            addConcept((Concept)iter.next());
            nbClass++;
        }

    }

    public void initFirst(Concept concept)
    {
    	//LatticeNode 为格节点信息，共有6项内容,其中有存储concept
        concept.slit.add(concept.getIntent());
    	lattice.util.Node n = new LatticeNode(concept);
        
        getLattice().setTop(n);
        getLattice().setBottom(n);
        getLattice().initialiseIntentLevelIndex(concept.getIntent().size() + 1);
        getLattice().set_max_transaction_size(concept.getIntent().size());
        getLattice().addNodeToIntentLevelIndex(getLattice().getBottom());
        getLattice().updateIndexWithNewIntents(concept.getIntent());
        getLattice().inc_nbr_concept();
    }
    
    
    
    
}
