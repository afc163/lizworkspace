// Decompiled by Jad v1.5.7g. Copyright 2000 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/SiliconValley/Bridge/8617/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi 
// Source File Name:   AttributsList.java

package lattice.graph.trees;

import java.awt.*;
import java.util.Vector;

// Referenced classes of package lattice.graph.trees:
//            ComposantList, Composant, Attribut, Noeud

public class AttributsList extends ComposantList
{

    protected static final String POINT = "\245 ";
    protected static final String siImage = "|";
    protected Noeud noeud;
    protected int widthMax;
    protected int cl;
    protected int ch;
    protected int hauteur;

    public AttributsList(Noeud unNoeud)
    {
        widthMax = 0;
        cl = 5;
        ch = 1;
        hauteur = 0;
        setNoeud(unNoeud);
        init2();
    }

    void init2()
    {
        setLabelColor(Color.black);
        setBgColor(new Color(210, 210, 210));
        setWidth(0);
        setHeight(0);
    }

    public Attribut attribut(int i)
    {
        return (Attribut)liste.elementAt(i);
    }

    public void setNoeud(Noeud n)
    {
        noeud = n;
    }

    public void remove(String nom)
    {
        int index = find(nom);
        if(index != -1)
            liste.removeElementAt(index);
    }

    public int find(String nom)
    {
        for(int i = nbElement() - 1; i >= 0; i--)
            if(nom == attribut(i).getLabel())
                return i;

        return -1;
    }

    public void setElementAt(Attribut unAttribut, int index)
    {
        liste.setElementAt(unAttribut, index);
    }

    public Attribut elementName(String nom)
    {
        return (Attribut)liste.elementAt(find(nom));
    }

    public Object clone(Noeud n)
    {
        AttributsList newList = new AttributsList(n);
        for(int i = 0; i < nbElement(); i++)
        {
            Attribut att = (Attribut)attribut(i).clone();
            att.initNewPere(n);
            newList.add(att);
        }

        return newList;
    }

    public void calculDimension(FontMetrics fm)
    {
        if(nbElement() != 0)
        {
            hauteur = fm.getHeight();
            int indexMax = -1;
            int maxWidth = 0;
            int tempoWidth = 0;
            for(int i = 0; i < nbElement(); i++)
            {
                tempoWidth = fm.stringWidth("\245 " + attribut(i).getLabel());
                if(maxWidth < tempoWidth)
                {
                    maxWidth = tempoWidth;
                    indexMax = i;
                }
            }

            setWidthLabel(maxWidth);
            setHeightLabel(hauteur * nbElement());
            setWidth(widthLabel() + 2 * cl);
            setHeight(heightLabel() + nbElement() * ch + ch + 3);
            widthMax = 0;
        }
    }

    public Rectangle rect()
    {
        Rectangle r = rect2();
        if(widthMax != 0)
            r.setSize(widthMax + r.width, r.height);
        return r;
    }

    public Rectangle rect2()
    {
        return new Rectangle(noeud.supGaucheX() + noeud.width() / 3, (noeud.supGaucheY() + noeud.height()) - 2, width() + Composant.shadowSize.width + 1, height() + Composant.shadowSize.height + 1);
    }

    public Attribut dansAttributs(int x, int y)
    {
        Attribut unAttribut = null;
        if(sourisDans(x, y))
            unAttribut = rechAttribut(y);
        return unAttribut;
    }

    public boolean sourisDans(int x, int y)
    {
        return rect2().contains(x, y);
    }

    public Attribut rechAttribut(int y)
    {
        for(int i = 0; i < nbElement(); i++)
            if(y < y() + ch + hauteur + (ch + hauteur) * i)
                return attribut(i);

        return null;
    }

    public void paintShadow(Graphics g, int xRel, int yRel)
    {
        g.setColor(Composant.shadow_color);
        g.fillRect((noeud.supGaucheX() + noeud.width() / 3 + Composant.shadowSize.width) - 4, (((noeud.supGaucheY() + noeud.height()) - 3) + Composant.shadowSize.height) - 4, width() + 4, height() + 4);
    }

    public void paintValue(Graphics g1, Attribut attribut1)
    {
    }

    public void paintSelected(Graphics g, int xRel, int yRel, boolean b)
    {
        g.fillRect(xRel, yRel, width() + 4, hauteur + 4);
        g.setColor(bgColor());
        g.fill3DRect(xRel + 2, yRel + 2, width(), hauteur, b);
        g.setColor(labelColor());
    }

    public void paint(Graphics g)
    {
        FontMetrics fm = g.getFontMetrics();
        Rectangle r = rect2();
        setPos(r.x, r.y);
        g.setColor(bgColor());
        g.fill3DRect(x(), y(), width(), height(), true);
        g.setColor(labelColor());
        int X = x() + cl;
        int Y = (y() + hauteur) - 1;
        String s2 = null;
        int l = fm.stringWidth("\245 ");
        for(int i = 0; i < nbElement(); i++)
        {
            Attribut av = attribut(i);
            s2 = av.getLabel();
            if(av.getSelected())
            {
                g.setColor(Composant.shadow_color);
                paintSelected(g, x() - 2, (Y - hauteur) + 1, !av.getClicked());
            }
            if(av.getCible())
            {
                g.setColor(Composant.cible_color);
                paintSelected(g, x() - 2, (Y - hauteur) + 1, !av.getClicked());
            }
            if(av.getClicked())
            {
                paintValue(g, av);
                g.setColor(av.getColorType());
                g.drawString("\245 ", X + 1, Y + 1);
            } else
            {
                paintValue(g, av);
                g.drawString("\245 ", X + 1, Y + 1);
                g.setColor(attribut(i).getColorType());
                g.drawString("\245 ", X, Y);
                g.setColor(labelColor());
            }
            g.drawString(s2, X + l, Y);
            Y = Y + hauteur + ch;
        }

    }

    public void paint(Graphics g, int xRel, int yRel)
    {
        FontMetrics fm = g.getFontMetrics();
        setPos(noeud.supGaucheX() + noeud.width() / 3, (noeud.supGaucheY() + noeud.height()) - 3);
        g.setColor(bgColor());
        g.fill3DRect(x() + xRel, y() + yRel, width(), height(), true);
        g.setColor(labelColor());
        int X = x() + cl + xRel;
        int Y = y() + ch / 2 + hauteur + yRel;
        String s2 = null;
        int l2 = 0;
        int l = fm.stringWidth("\245 ");
        for(int i = 0; i < nbElement(); i++)
        {
            Attribut av = attribut(i);
            s2 = av.getLabel();
            int nbIllu = av.nbIllustration();
            if(nbIllu > 0)
            {
                String nIllu = (new Integer(nbIllu)).toString();
                l2 = fm.stringWidth("|" + nIllu);
                l2 = fm.stringWidth(nIllu);
                g.drawString(nIllu, (x() - cl - l2) + xRel, Y + yRel);
            }
            if(av.getSelected())
            {
                g.setColor(Composant.shadow_color);
                paintSelected(g, (x() - 2) + xRel, ((Y + yRel) - hauteur) + 1, !av.getClicked());
            }
            if(av.getCible())
            {
                g.setColor(Composant.cible_color);
                paintSelected(g, (x() - 2) + xRel, ((Y + yRel) - hauteur) + 1, !av.getClicked());
            }
            if(av.getClicked())
            {
                paintValue(g, av);
                g.setColor(av.getColorType());
                g.drawString("\245 ", X + 1, Y + 1);
            } else
            {
                g.drawString("\245 ", X + 1, Y + 1);
                g.setColor(av.getColorType());
                g.drawString("\245 ", X, Y);
                g.setColor(labelColor());
            }
            g.drawString(s2, X + l, Y);
            Y = Y + hauteur + ch;
        }

    }
}
