// Decompiled by Jad v1.5.7g. Copyright 2000 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/SiliconValley/Bridge/8617/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi 
// Source File Name:   MenuList.java

package lattice.graph.trees;

import java.awt.*;
import java.util.Vector;

// Referenced classes of package lattice.graph.trees:
//            ComposantList, Composant, Noeud

public class MenuList extends ComposantList
{

    Noeud noeud;
    int cl;
    int ch;
    int hauteur;

    MenuList(Noeud unNoeud)
    {
        cl = 5;
        ch = 1;
        hauteur = 0;
        noeud = unNoeud;
        init2();
    }

    void init2()
    {
        setLabelColor(Color.black);
        setBgColor(new Color(210, 210, 210));
        setWidth(0);
        setHeight(0);
    }

    public String item(int i)
    {
        return (String)liste.elementAt(i);
    }

    public void remove(String nom)
    {
        liste.removeElementAt(find(nom));
    }

    public int find(String nom)
    {
        for(int i = nbElement() - 1; i >= 0; i--)
            if(nom == item(i))
                return i;

        return -1;
    }

    public void calculDimension(FontMetrics fm)
    {
        if(nbElement() != 0)
        {
            hauteur = fm.getHeight();
            int indexMax = -1;
            int maxWidth = 0;
            for(int i = 0; i < nbElement(); i++)
                if(maxWidth < fm.stringWidth(item(i)))
                {
                    maxWidth = fm.stringWidth(item(i));
                    indexMax = i;
                }

            setWidthLabel(maxWidth);
            setHeightLabel(hauteur * nbElement());
            setWidth(widthLabel() + 2 * cl);
            setHeight(heightLabel() + nbElement() * ch + ch + fm.getAscent());
        }
    }

    public Rectangle rect(int x, int y)
    {
        setPos(x, y);
        return new Rectangle(x, y, width(), height());
    }

    public Rectangle rect()
    {
        return new Rectangle(0, 0, width(), height());
    }

    public void paint(Graphics g, FontMetrics fm)
    {
        g.setColor(bgColor());
        g.fill3DRect(x(), y(), width(), height(), true);
        g.setColor(labelColor());
        int X = x() + cl;
        int Y = y() + ch + hauteur;
        for(int i = 0; i < nbElement(); i++)
        {
            g.drawString(item(i), X, Y);
            Y = Y + hauteur + ch;
        }

    }

    public void paint(Graphics g, FontMetrics fm, int xRel, int yRel)
    {
        g.setColor(bgColor());
        g.fill3DRect(x(), y(), width(), height(), true);
        g.setColor(labelColor());
        int X = x() + cl;
        int Y = y() + ch + hauteur;
        for(int i = 0; i < nbElement(); i++)
        {
            g.drawString(item(i), X, Y);
            Y = Y + hauteur + ch;
        }

    }
}
