// Decompiled by Jad v1.5.7g. Copyright 2000 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/SiliconValley/Bridge/8617/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi 
// Source File Name:   Formatter.java

package lattice.graph.trees.formatter;

import java.awt.Point;
import java.awt.Rectangle;
import java.util.Vector;
import lattice.graph.trees.Noeud;
import lattice.graph.trees.Relation;

public abstract class Formatter
{

    protected Vector noeuds;
    protected int cl;
    protected int ch;
    public int zoom;

    public Formatter(Vector noeuds)
    {
        cl = 6;
        ch = 2;
        this.noeuds = noeuds;
    }

    public abstract void formatter(Noeud noeud);

    public void setCl(int cl)
    {
        this.cl = cl;
    }

    public int cl()
    {
        return cl + zoom / 5;
    }

    public void setCh(int ch)
    {
        this.ch = ch;
    }

    public int ch()
    {
        return ch + zoom / 5;
    }

    public int getZoom()
    {
        return zoom;
    }

    public void setZoom(int zoom)
    {
        this.zoom = zoom;
    }

    public Noeud noeuds(int i)
    {
        return (Noeud)noeuds.elementAt(i);
    }

    protected void marquer()
    {
        for(int i = 0; i < noeuds.size(); i++)
            noeuds(i).setMarque(true);

    }

    protected void demarquer()
    {
        for(int i = 0; i < noeuds.size(); i++)
            noeuds(i).setMarque(false);

    }

    protected void marquer2()
    {
        for(int i = 0; i < noeuds.size(); i++)
            noeuds(i).setMarque2(true);

    }

    protected void demarquer2()
    {
        for(int i = 0; i < noeuds.size(); i++)
            noeuds(i).setMarque2(false);

    }

    public Vector fil(Noeud unNoeud, int p)
    {
        demarquer2();
        return fils(unNoeud, p);
    }

    protected Vector fils(Noeud unNoeud, int p)
    {
        Vector fils = new Vector();
        Vector f = unNoeud.fils();
        if(p == 0)
        {
            if(!unNoeud.getMarque2())
            {
                fils.addElement(unNoeud);
                unNoeud.getMarque2();
            }
            return fils;
        }
        for(int i = 0; i < f.size(); i++)
        {
            Vector lesFils = fils((Noeud)f.elementAt(i), p - 1);
            for(int j = 0; j < lesFils.size(); j++)
                fils.addElement(lesFils.elementAt(j));

        }

        return fils;
    }

    public Vector peres(Noeud unNoeud)
    {
        Vector peres = new Vector();
        for(int i = 0; i < unNoeud.nbRelationArrive(); i++)
            peres.addElement(unNoeud.relationArrive(i).origine());

        return peres;
    }

    public Vector feuilles(Noeud unNoeud)
    {
        demarquer2();
        return feuillesRec(unNoeud);
    }

    protected Vector feuillesRec(Noeud unNoeud)
    {
        Vector feuilles = new Vector();
        Vector f = unNoeud.fils();
        if(f.size() == 0 || !((Noeud)f.firstElement()).visible())
        {
            if(!unNoeud.getMarque2())
            {
                feuilles.addElement(unNoeud);
                unNoeud.setMarque2(true);
            }
            return feuilles;
        }
        for(int i = 0; i < f.size(); i++)
        {
            Vector lesFeuilles = feuilles((Noeud)f.elementAt(i));
            for(int j = 0; j < lesFeuilles.size(); j++)
                feuilles.addElement(lesFeuilles.elementAt(j));

        }

        return feuilles;
    }

    public int prof(Noeud unNoeud)
    {
        demarquer2();
        return profondeur(unNoeud);
    }

    protected int profondeur(Noeud unNoeud)
    {
        unNoeud.setMarque2(true);
        int p = 0;
        int k = 0;
        Vector f = unNoeud.fils();
        if(f.size() == 0)
            return 0;
        for(int i = 0; i < f.size(); i++)
            if(!((Noeud)f.elementAt(i)).getMarque2())
            {
                k = profondeur((Noeud)f.elementAt(i));
                if(k > p)
                    p = k;
            }

        return p + 1;
    }

    public int plusGdNbFil(Noeud unNoeud)
    {
        demarquer2();
        return plusGdNbFils(unNoeud);
    }

    protected int plusGdNbFils(Noeud unNoeud)
    {
        unNoeud.setMarque2(true);
        Vector f = unNoeud.fils();
        int p = f.size();
        for(int i = 0; i < f.size(); i++)
            if(!((Noeud)f.elementAt(i)).getMarque2())
            {
                int k = plusGdNbFils((Noeud)f.elementAt(i));
                if(k > p)
                    p = k;
            }

        return p;
    }

    protected void positionne(Noeud unNoeud)
    {
        unNoeud.setPos(new Point(unNoeud.x(), unNoeud.y()));
        Vector fils = unNoeud.fils();
        for(int i = 0; i < fils.size(); i++)
            positionne((Noeud)fils.elementAt(i));

    }

    protected void positionneY(Noeud unNoeud, int c)
    {
        unNoeud.setPosSup(new Point(unNoeud.x(), c));
        unNoeud.setMarque2(true);
        Vector fils = unNoeud.fils();
        int hauteur = unNoeud.maxHauteur();
        for(int i = 0; i < fils.size(); i++)
        {
            Noeud unFils = (Noeud)fils.elementAt(i);
            if(!unFils.getMarque2())
                positionneY(unFils, c + unNoeud.rect().height + ch() + hauteur);
        }

    }

    protected void positionneX(Noeud unNoeud, int c)
    {
        unNoeud.setPosSup(new Point(c, unNoeud.y()));
        unNoeud.setMarque2(true);
        Vector fils = unNoeud.fils();
        for(int i = 0; i < fils.size(); i++)
        {
            Noeud unFils = (Noeud)fils.elementAt(i);
            if(!unFils.getMarque2())
            {
                int largeur = unNoeud.largeur(unFils);
                positionneX(unFils, c + unNoeud.rect().width + cl() + largeur);
            }
        }

    }

    protected int calculH(Noeud unNoeud)
    {
        return unNoeud.rect().height + ch();
    }

    protected boolean tousFilsMarque(Noeud unNoeud)
    {
        Vector fils = unNoeud.fils();
        boolean marq = true;
        for(int i = 0; i < fils.size(); i++)
            if(!((Noeud)fils.elementAt(i)).getMarque())
                marq = false;

        return marq;
    }

    public int sommeYFils(Noeud unNoeud)
    {
        Vector fils = unNoeud.fils();
        int Y = 0;
        for(int i = 0; i < fils.size(); i++)
            Y += ((Noeud)fils.elementAt(i)).y();

        return Y;
    }

    public int sommeXFils(Noeud unNoeud)
    {
        Vector fils = unNoeud.fils();
        int X = 0;
        for(int i = 0; i < fils.size(); i++)
        {
            Noeud unFils = (Noeud)fils.elementAt(i);
            X = X + unFils.x() + unFils.rect().width / 2;
        }

        return X;
    }

    protected int positionneYPeres(Noeud unNoeud)
    {
        unNoeud.setPosSup(new Point(unNoeud.x(), sommeYFils(unNoeud) / unNoeud.nbFils() - unNoeud.height() / 2));
        unNoeud.setMarque(true);
        Vector peres = peres(unNoeud);
        for(int j = 0; j < peres.size();)
        {
            Noeud papa = (Noeud)peres.elementAt(j);
            if(tousFilsMarque(papa))
                return positionneYPeres((Noeud)peres.elementAt(j));
            else
                return calcMaxH(unNoeud);
        }

        return 0;
    }

    public int calcMaxH(Noeud unNoeud)
    {
        int hMaxThis = unNoeud.supGaucheY() + calculH(unNoeud);
        if(unNoeud.nbFils() == 0)
            return hMaxThis;
        Noeud dernierFils = unNoeud.fils(unNoeud.fils().size() - 1);
        int hMaxFils = calcMaxH(dernierFils);
        if(hMaxThis > hMaxFils)
            return hMaxThis;
        else
            return hMaxFils;
    }

    protected void positionneXPeres(Noeud unNoeud)
    {
        if(tousFilsMarque(unNoeud))
        {
            unNoeud.setPos(new Point(sommeXFils(unNoeud) / unNoeud.nbFils() - unNoeud.rect().width / 2, unNoeud.y()));
            unNoeud.setMarque(true);
            Vector peres = peres(unNoeud);
            for(int j = 0; j < peres.size(); j++)
                positionneXPeres((Noeud)peres.elementAt(j));

        }
    }
}
