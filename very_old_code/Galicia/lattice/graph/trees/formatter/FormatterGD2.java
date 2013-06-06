// Decompiled by Jad v1.5.7g. Copyright 2000 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/SiliconValley/Bridge/8617/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi 
// Source File Name:   FormatterGD2.java

package lattice.graph.trees.formatter;

import java.awt.Point;
import java.awt.Rectangle;
import java.util.Vector;
import lattice.graph.trees.Noeud;

// Referenced classes of package lattice.graph.trees.formatter:
//            Formatter

public class FormatterGD2 extends Formatter
{

    Rectangle rectParent;

    public FormatterGD2(Vector noeuds, Rectangle rectParent)
    {
        super(noeuds);
        this.rectParent = rectParent;
    }

    public void formatter(Noeud unNoeud)
    {
        setCl(8);
        setCh(2);
        int p = prof(unNoeud);
        demarquer();
        Rectangle b = new Rectangle(rectParent.x + 20, rectParent.y, rectParent.width, rectParent.height - 40);
        formatter(unNoeud, b, b.width / (p + 1));
    }

    public void formatter(Noeud n, Rectangle r, int l)
    {
        n.setMarque(true);
        int nbFils = 0;
        Vector f = n.fils();
        n.setPosSup(new Point(r.x, r.y + r.height / 2));
        for(int i = 0; i < f.size(); i++)
        {
            int p = plusGdNbFil((Noeud)f.elementAt(i));
            if(p == 0)
                p = 1;
            nbFils += p;
        }

        int Y = r.y;
        for(int i = 0; i < f.size(); i++)
        {
            Noeud s = (Noeud)f.elementAt(i);
            if(!s.getMarque())
            {
                int ps = plusGdNbFil(s);
                if(ps == 0)
                    ps = 1;
                int h = (r.height * ps) / nbFils;
                Rectangle rec = new Rectangle(r.x + l, Y, l, h);
                formatter(s, rec, l);
                Y += h;
            }
        }

    }
}
