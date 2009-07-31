// Decompiled by Jad v1.5.7g. Copyright 2000 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/SiliconValley/Bridge/8617/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi 
// Source File Name:   FormatterGD4.java

package lattice.graph.trees.formatter;

import java.awt.Point;
import java.util.Vector;
import lattice.graph.trees.Noeud;

// Referenced classes of package lattice.graph.trees.formatter:
//            Formatter

public class FormatterGD4 extends Formatter
{

    public FormatterGD4(Vector noeuds)
    {
        super(noeuds);
    }

    public void formatter(Noeud unNoeud)
    {
        setCl(8);
        setCh(2);
        demarquer();
        int h = 0;
        Vector v = feuilles(unNoeud);
        demarquer2();
        for(int i = 0; i < v.size(); i++)
        {
            Noeud uneFeuille = (Noeud)v.elementAt(i);
            uneFeuille.setPosSup(new Point(uneFeuille.x(), h));
            uneFeuille.setMarque(true);
            h += calculH(uneFeuille);
            Vector peres = peres(uneFeuille);
            for(int j = 0; j < peres.size(); j++)
            {
                Noeud papa = (Noeud)peres.elementAt(j);
                if(tousFilsMarque(papa))
                    h += positionneYPeres((Noeud)peres.elementAt(j)) - (uneFeuille.supGaucheY() + calculH(uneFeuille));
            }

        }

        positionneX(unNoeud, cl());
    }
}
