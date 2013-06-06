// Decompiled by Jad v1.5.7g. Copyright 2000 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/SiliconValley/Bridge/8617/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi 
// Source File Name:   LatticeAttributeGraph.java

package lattice.gui.graph;

import lattice.graph.trees.Attribut;

// Referenced classes of package lattice.gui.graph:
//            LatticeNodeGraph

public class LatticeAttributeGraph extends Attribut
{

    protected LatticeNodeGraph concept;
    protected String element;

    public LatticeAttributeGraph(LatticeNodeGraph concept, String element)
    {
        super(concept, element);
        this.element = element;
    }
}
