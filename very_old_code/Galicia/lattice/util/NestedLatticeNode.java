// Decompiled by Jad v1.5.7g. Copyright 2000 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/SiliconValley/Bridge/8617/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi 
// Source File Name:   NestedLatticeNode.java

package lattice.util;


// Referenced classes of package lattice.util:
//            LatticeNode, ConceptLattice, Concept

public class NestedLatticeNode extends LatticeNode
{

    public ConceptLattice nestedLattice;
    boolean isEmpy;

    public NestedLatticeNode(Integer id, Concept concept)
    {
        super(id, concept);
        isEmpy = false;
    }

    public NestedLatticeNode(Concept concept)
    {
        super(concept);
        isEmpy = false;
    }

    public ConceptLattice getNestedLattice()
    {
        return nestedLattice;
    }

    public void setNestedLattice(ConceptLattice cl)
    {
        nestedLattice = cl;
    }
}
