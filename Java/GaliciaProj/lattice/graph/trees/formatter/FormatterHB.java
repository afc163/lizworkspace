// Decompiled by Jad v1.5.7g. Copyright 2000 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/SiliconValley/Bridge/8617/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi 
// Source File Name:   FormatterHB.java

package lattice.graph.trees.formatter;

import java.awt.Point;
import java.awt.Rectangle;
import java.util.Vector;
import lattice.graph.trees.Noeud;

// Referenced classes of package lattice.graph.trees.formatter:
//            Formatter

public class FormatterHB extends Formatter
{

    public FormatterHB(Vector noeuds)
    {
        super(noeuds);
    }

    public void formatter(Noeud unNoeud)
    {
        setCl(2);
        setCh(25);
        demarquer();
        demarquer2();
        Vector v = feuilles(unNoeud);
        demarquer2();
        int l = ch();
        for(int i = 0; i < v.size(); i++)
        {
            Noeud uneFeuille = (Noeud)v.elementAt(i);
            uneFeuille.setPosSup(new Point(l, uneFeuille.y()));
            uneFeuille.setMarque(true);
            l = l + uneFeuille.rect().width + cl();
        }

        for(int i = 0; i < v.size(); i++)
        {
            Noeud uneFeuille = (Noeud)v.elementAt(i);
            Vector peres = peres(uneFeuille);
            for(int j = 0; j < peres.size(); j++)
                positionneXPeres((Noeud)peres.elementAt(j));

        }

        positionneY(unNoeud, ch());
        positionne(unNoeud);
    }
}
