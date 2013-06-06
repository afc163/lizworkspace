// Decompiled by Jad v1.5.7g. Copyright 2000 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/SiliconValley/Bridge/8617/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi 
// Source File Name:   NouReynaud.java

package lattice.algorithm;

import java.util.Set;
import java.util.Vector;
import lattice.util.*;

// Referenced classes of package lattice.algorithm:
//            NouReynaud

private class ExtentIndex
{

    private Vector ei[];

    protected void ajouterNoeud(LatticeNodeNR n)
    {
        ei[n.getConcept().getExtent().size()].add(n);
    }

    protected LatticeNodeNR trouverNoeudCandidat(Extent e)
    {
        for(int i = 0; i < ei[e.size()].size(); i++)
        {
            LatticeNodeNR lnr = (LatticeNodeNR)ei[e.size()].elementAt(i);
            if(lnr.getConcept().getExtent().equals(e))
                return lnr;
        }

        return null;
    }

    protected int size()
    {
        return ei.length;
    }

    protected Vector getAt(int i)
    {
        return ei[i];
    }

    protected void effacerNoeud(LatticeNodeNR n)
    {
        ei[n.getConcept().getExtent().size()].remove(n);
    }

    protected ExtentIndex()
    {
        ei = new Vector[NouReynaud.access$0(NouReynaud.this).getObjectsNumber() + 1];
        for(int i = 0; i < ei.length; i++)
            ei[i] = new Vector();

    }
}
