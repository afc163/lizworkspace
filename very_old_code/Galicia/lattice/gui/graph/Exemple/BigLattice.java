// Decompiled by Jad v1.5.7g. Copyright 2000 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/SiliconValley/Bridge/8617/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi 
// Source File Name:   BigLattice.java

package lattice.gui.graph.Exemple;

import java.util.Vector;
import lattice.util.Node;

// Referenced classes of package lattice.gui.graph.Exemple:
//            AbstractExempleLattice

public class BigLattice extends AbstractExempleLattice
{

    public BigLattice()
    {
    }

    public Node creer()
    {
        Vector vNode = new Vector();
        vNode.add("top,5,12,13,15,16");
        vNode.add("5,19");
        vNode.add("12,14,18");
        vNode.add("13,14,20");
        vNode.add("15,2,17,21");
        vNode.add("16,18,21,10");
        vNode.add("14,27");
        vNode.add("18,23,27");
        vNode.add("2,6,1");
        vNode.add("17,19,20,6");
        vNode.add("21,23,1");
        vNode.add("10,11,8");
        vNode.add("19,3,23,25");
        vNode.add("27,22,28");
        vNode.add("11,28,9");
        vNode.add("20,25,7");
        vNode.add("6,3,7");
        vNode.add("8,9,1");
        vNode.add("3,24,26");
        vNode.add("23,24,22");
        vNode.add("25,26,22");
        vNode.add("28,26");
        vNode.add("9,24");
        vNode.add("7,26");
        vNode.add("1,24");
        vNode.add("24,bottom");
        vNode.add("26,bottom");
        vNode.add("22,bottom");
        return creer(vNode);
    }
}
