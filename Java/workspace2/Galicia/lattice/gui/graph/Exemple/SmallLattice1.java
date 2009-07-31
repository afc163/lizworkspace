// Decompiled by Jad v1.5.7g. Copyright 2000 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/SiliconValley/Bridge/8617/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi 
// Source File Name:   SmallLattice1.java

package lattice.gui.graph.Exemple;

import java.util.Vector;
import lattice.util.Node;

// Referenced classes of package lattice.gui.graph.Exemple:
//            AbstractExempleLattice

public class SmallLattice1 extends AbstractExempleLattice
{

    public SmallLattice1()
    {
    }

    public Node creer()
    {
        Vector vNode = new Vector();
        vNode.add("top,7,8,9");
        vNode.add("7,10");
        vNode.add("8,1,11");
        vNode.add("9,10,1,11,5");
        vNode.add("10,12,6");
        vNode.add("1,0");
        vNode.add("11,12,0");
        vNode.add("5,6,3");
        vNode.add("12,bottom");
        vNode.add("6,4");
        vNode.add("3,10,4");
        vNode.add("0,bottom");
        vNode.add("4,bottom");
        return creer(vNode);
    }
}
