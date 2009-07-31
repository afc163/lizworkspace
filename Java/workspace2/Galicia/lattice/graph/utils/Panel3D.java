// Decompiled by Jad v1.5.7g. Copyright 2000 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/SiliconValley/Bridge/8617/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi 
// Source File Name:   Panel3D.java

package lattice.graph.utils;

import java.awt.*;
import javax.swing.JComponent;

// Referenced classes of package lattice.graph.utils:
//            IkbsPanel

public class Panel3D extends IkbsPanel
{

    public static final int WITH_ALL = 0;
    public static final int WITHOUT_TOP = 1;
    public static final int WITHOUT_LEFT = 2;
    public static final int WITHOUT_RIGHT = 3;
    public static final int WITHOUT_BOTTOM = 4;
    public static final int NOTHING = 5;
    int choix;
    int longueur;
    int index;

    public Panel3D()
    {
        choix = 0;
    }

    public Panel3D(int choix)
    {
        this.choix = choix;
        index = 0;
        longueur = 0;
    }

    public Panel3D(int choix, int index, int longueur)
    {
        this.choix = choix;
        this.index = index;
        this.longueur = longueur;
    }

    public Panel3D(int choix, Component c)
    {
        this(choix, 0, c.getSize().height);
    }

    public synchronized void paint(Graphics g)
    {
        switch(choix)
        {
        case 0: // '\0'
            paintComplet(g);
            break;

        case 1: // '\001'
            paintWithoutTop(g);
            break;

        case 2: // '\002'
            paintWithoutLeft(g);
            break;

        case 3: // '\003'
            paintWithoutRight(g);
            break;

        case 4: // '\004'
            paintWithoutBottom(g);
            break;

        case 5: // '\005'
            super.paint(g);
            // fall through

        default:
            super.paint(g);
            break;
        }
    }

    public void setLongueur(int longueur)
    {
        this.longueur = longueur;
    }

    public int getLongeur()
    {
        return longueur;
    }

    public void setIndex(int index)
    {
        this.index = index;
    }

    public int getIndex()
    {
        return index;
    }

    public synchronized void paintComplet(Graphics g)
    {
        int w = getSize().width;
        int h = getSize().height;
        paintTop(g, w, h);
        paintLeft(g, w, h);
        paintBottom(g, w, h);
        paintRight(g, w, h);
    }

    public void paintWithoutTop(Graphics g)
    {
        int w = getSize().width;
        int h = getSize().height;
        paintLeft(g, w, h);
        paintRight(g, w, h);
        paintBottom(g, w, h);
        if(longueur != 0)
            paintTop(g, w, h, index, longueur);
    }

    public void paintWithoutLeft(Graphics g)
    {
        int w = getSize().width;
        int h = getSize().height;
        paintTop(g, w, h);
        paintBottom(g, w, h);
        paintRight(g, w, h);
        if(longueur != 0)
            paintLeft(g, w, h, index, longueur);
    }

    public synchronized void paintWithoutRight(Graphics g)
    {
        int w = getSize().width;
        int h = getSize().height;
        paintTop(g, w, h);
        paintLeft(g, w, h);
        paintBottom(g, w, h);
        if(longueur != 0)
            paintRight(g, w, h, index, longueur);
    }

    public synchronized void paintWithoutBottom(Graphics g)
    {
        int w = getSize().width;
        int h = getSize().height;
        paintTop(g, w, h);
        paintLeft(g, w, h);
        paintRight(g, w, h);
        if(longueur != 0)
            paintBottom(g, w, h, index, longueur);
    }

    public synchronized void paintTop(Graphics g, int w, int h)
    {
        g.setColor(getBackground().brighter());
        g.drawLine(0, 0, w - 1, 0);
    }

    public synchronized void paintTop(Graphics g, int w, int h, int index, int longueur)
    {
        g.setColor(getBackground().brighter());
        g.drawLine(0, 0, index, 0);
        g.drawLine(0, index + longueur, 0, w - 1);
    }

    public synchronized void paintLeft(Graphics g, int w, int h)
    {
        g.setColor(getBackground().brighter());
        g.drawLine(0, 0, 0, h - 2);
    }

    public synchronized void paintLeft(Graphics g, int w, int h, int index, int longueur)
    {
        g.setColor(getBackground().brighter());
        g.drawLine(0, 0, 0, index);
        g.drawLine(0, index + longueur, 0, h);
    }

    public synchronized void paintBottom(Graphics g, int w, int h)
    {
        g.setColor(getBackground().darker());
        g.drawLine(0, h - 2, w - 2, h - 2);
        g.setColor(getBackground().darker().darker());
        g.drawLine(0, h - 1, w, h - 1);
    }

    public synchronized void paintBottom(Graphics g, int w, int h, int index, int longueur)
    {
        g.setColor(getBackground().darker());
        g.drawLine(1, h - 2, index, h - 2);
        g.drawLine(index + longueur, h - 2, w - 2, h - 2);
        g.setColor(getBackground().darker().darker());
        g.drawLine(0, h - 1, index, h - 1);
        g.drawLine(index + longueur, h - 1, w, h - 1);
    }

    public synchronized void paintRight(Graphics g, int w, int h)
    {
        g.setColor(getBackground().darker());
        g.drawLine(w - 2, 1, w - 2, h - 2);
        g.setColor(getBackground().darker().darker());
        g.drawLine(w - 1, 0, w - 1, h - 1);
    }

    public synchronized void paintRight(Graphics g, int w, int h, int index, int longueur)
    {
        g.setColor(getBackground().darker());
        if(index != 0)
            g.drawLine(w - 2, 1, w - 2, index);
        g.drawLine(w - 2, index + longueur, w - 1, index + longueur);
        g.drawLine(w - 2, index + longueur, w - 2, h - 2);
        g.setColor(getBackground().darker().darker());
        if(index != 0)
            g.drawLine(w - 1, 0, w - 1, index);
        g.drawLine(w - 1, index + longueur + 1, w - 1, h - 1);
    }
}
