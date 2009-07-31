// Decompiled by Jad v1.5.7g. Copyright 2000 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/SiliconValley/Bridge/8617/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi 
// Source File Name:   SmallLattice2.java

package lattice.gui.graph.Exemple;

import lattice.util.Node;

// Referenced classes of package lattice.gui.graph.Exemple:
//            AbstractExempleLattice, ExempleNode

public class SmallLattice2 extends AbstractExempleLattice
{

    public SmallLattice2()
    {
    }

    public Node creer()
    {
        ExempleNode top = new ExempleNode(1);
        ExempleNode bottom = new ExempleNode(26);
        ExempleNode c2 = new ExempleNode(2);
        ExempleNode c3 = new ExempleNode(3);
        ExempleNode c4 = new ExempleNode(4);
        ExempleNode c5 = new ExempleNode(5);
        ExempleNode c6 = new ExempleNode(6);
        ExempleNode c7 = new ExempleNode(7);
        ExempleNode c8 = new ExempleNode(8);
        ExempleNode c9 = new ExempleNode(9);
        ExempleNode c10 = new ExempleNode(10);
        ExempleNode c11 = new ExempleNode(11);
        ExempleNode c12 = new ExempleNode(12);
        ExempleNode c13 = new ExempleNode(13);
        ExempleNode c14 = new ExempleNode(14);
        ExempleNode c15 = new ExempleNode(15);
        ExempleNode c16 = new ExempleNode(16);
        ExempleNode c17 = new ExempleNode(17);
        ExempleNode c18 = new ExempleNode(18);
        ExempleNode c19 = new ExempleNode(19);
        ExempleNode c20 = new ExempleNode(20);
        ExempleNode c21 = new ExempleNode(21);
        ExempleNode c22 = new ExempleNode(22);
        ExempleNode c23 = new ExempleNode(23);
        ExempleNode c24 = new ExempleNode(24);
        ExempleNode c25 = new ExempleNode(25);
        top.addChild(c2);
        top.addChild(c3);
        top.addChild(c4);
        c2.addChild(c5);
        c2.addChild(c6);
        c2.addChild(c7);
        c3.addChild(c8);
        c3.addChild(c9);
        c3.addChild(c10);
        c4.addChild(c11);
        c4.addChild(c12);
        c4.addChild(c13);
        c5.addChild(c14);
        c6.addChild(c15);
        c7.addChild(c16);
        c8.addChild(c17);
        c9.addChild(c18);
        c10.addChild(c19);
        c11.addChild(c20);
        c12.addChild(c21);
        c13.addChild(c22);
        c14.addChild(c23);
        c15.addChild(c23);
        c16.addChild(c23);
        c17.addChild(c24);
        c18.addChild(c24);
        c19.addChild(c24);
        c20.addChild(c25);
        c21.addChild(c25);
        c22.addChild(c25);
        c23.addChild(bottom);
        c24.addChild(bottom);
        c25.addChild(bottom);
        c6.addChild(c14);
        c7.addChild(c15);
        c8.addChild(c16);
        c9.addChild(c17);
        c10.addChild(c18);
        c11.addChild(c19);
        c12.addChild(c20);
        c13.addChild(c21);
        c2.addChild(c8);
        c17.addChild(c23);
        c19.addChild(c25);
        return top;
    }
}
