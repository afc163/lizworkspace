// Decompiled by Jad v1.5.7g. Copyright 2000 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/SiliconValley/Bridge/8617/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi 
// Source File Name:   FormatterGD.java

package lattice.graph.trees.formatter;

import java.awt.Point;
import java.awt.Rectangle;
import java.util.Vector;
import lattice.graph.trees.Noeud;

// Referenced classes of package lattice.graph.trees.formatter:
//            Formatter

public class FormatterGD extends Formatter
{

    Rectangle rectParent;

    public FormatterGD(Vector noeuds, Rectangle rectParent)
    {
        super(noeuds);
        this.rectParent = rectParent;
    }

    public void formatter(Noeud unNoeud)
    {
        int p = prof(unNoeud);
        demarquer();
        Rectangle b = new Rectangle(rectParent.x + 20, rectParent.y, rectParent.width, rectParent.height - 40);
        formatter(unNoeud, b, b.width / (p + 1));
    }

    protected void formatter(Noeud n, Rectangle r, int l)
    {
        n.setMarque(true);
        Vector f = n.fils();
        n.setPosSup(new Point(r.x, r.y + r.height / 2));
        for(int i = 0; i < f.size(); i++)
        {
            Noeud s = (Noeud)f.elementAt(i);
            if(!s.getMarque())
            {
                Rectangle rec = new Rectangle(r.x + l, r.y + (i * r.height) / f.size(), l, r.height / f.size());
                formatter(s, rec, l);
            }
        }

    }
}
