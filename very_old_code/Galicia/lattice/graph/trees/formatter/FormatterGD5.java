// Decompiled by Jad v1.5.7g. Copyright 2000 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/SiliconValley/Bridge/8617/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi 
// Source File Name:   FormatterGD5.java

package lattice.graph.trees.formatter;

import java.awt.Point;
import java.awt.Rectangle;
import java.util.Vector;
import lattice.graph.trees.Noeud;

// Referenced classes of package lattice.graph.trees.formatter:
//            Formatter

public class FormatterGD5 extends Formatter
{

    Rectangle rectParent;

    public FormatterGD5(Vector noeuds, Rectangle rectParent)
    {
        super(noeuds);
        this.rectParent = rectParent;
    }

    public void formatter(Noeud unNoeud)
    {
        demarquer();
        int p = prof(unNoeud);
        Rectangle b = new Rectangle(rectParent.x + 20, rectParent.y, rectParent.width, rectParent.height - 40);
        int l = b.width / (p + 1);
        int l2 = b.x;
        demarquer2();
        for(int i = 0; i <= p; i++)
        {
            int Y = b.y;
            Vector fils = fils(unNoeud, i);
            if(fils.size() != 0)
            {
                int h = b.height / fils.size();
                for(int j = 0; j < fils.size(); j++)
                {
                    ((Noeud)fils.elementAt(j)).setPosSup(new Point(l2, Y + h / 2));
                    Y += h;
                }

            }
            l2 += l;
        }

    }
}
