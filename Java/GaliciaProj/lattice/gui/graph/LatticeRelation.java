// Decompiled by Jad v1.5.7g. Copyright 2000 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/SiliconValley/Bridge/8617/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi 
// Source File Name:   LatticeRelation.java

package lattice.gui.graph;

import java.awt.Color;
import lattice.graph.trees.*;

public class LatticeRelation extends Relation
{

    public static final Color NORMAL_COLOR = new Color(40, 40, 40);

    public LatticeRelation(Noeud unNoeud, Noeud unFils)
    {
        super(unNoeud, unFils);
        setBgColor(NORMAL_COLOR);
    }

    public double longueur()
    {
        int dx = origine.x() - extremite.x();
        int dy = origine.y() - extremite.y();
        return (double)Math.round(Math.sqrt(dx * dx + dy * dy));
    }

}
