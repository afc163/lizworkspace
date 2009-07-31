// Decompiled by Jad v1.5.7g. Copyright 2000 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/SiliconValley/Bridge/8617/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi 
// Source File Name:   ARES.java

package lattice.shg.algorithm;

import java.util.*;
import lattice.algorithm.AbstractARES;
import lattice.algorithm.LatticeAlgorithm;
import lattice.util.*;

public class ARES extends AbstractARES
{

    public ARES()
    {
    }

    public ARES(BinaryRelation bRel)
    {
        super(bRel);
    }

    public ARES(BinaryRelation bRel, FormalObject objectToAdd, Vector setOfAttributes)
    {
        super(bRel, objectToAdd, setOfAttributes);
    }

    public void doAlgorithm()
    {
        FormalObject objetsInit[] = binRel.getFormalObjects();
        algoPrincipal();
        if(lcl.getTop().getChildren().size() == 1 && lcl.getTop().getConcept().getSimplifyExtent().size() == 0 && lcl.getTop().getConcept().getSimplifyIntent().size() == 0)
        {
            Node nouvTop = (Node)lcl.getTop().getChildren().get(0);
            Node ancTop = lcl.getTop();
            nouvTop.removeParent(ancTop);
            ancTop.removeChild(nouvTop);
            lcl.setTop(nouvTop);
        }
        ((LatticeGraph)lcl).findNSetBottom();
        ((LatticeGraph)lcl).set_nbr_concept(((LatticeGraph)lcl).getAllNodes().size());
        binRel.setLattice(lcl);
    }

    public String getDescription()
    {
        return "ARES Algo.";
    }
}
