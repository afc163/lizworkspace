// Decompiled by Jad v1.5.7g. Copyright 2000 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/SiliconValley/Bridge/8617/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi 
// Source File Name:   LatticeNodeNR.java

package lattice.util;


// Referenced classes of package lattice.util:
//            LatticeNode, Concept

public class LatticeNodeNR extends LatticeNode
{

    private int compteur;

    public LatticeNodeNR(Concept concept)
    {
        super(concept);
        compteur = 0;
    }

    public LatticeNodeNR(Integer id, Concept concept)
    {
        super(id, concept);
        compteur = 0;
    }

    public void setCompteur(int c)
    {
        compteur = c;
    }

    public void incCompteur()
    {
        compteur++;
    }

    public int getCompteur()
    {
        return compteur;
    }
}
