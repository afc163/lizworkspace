// Decompiled by Jad v1.5.7g. Copyright 2000 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/SiliconValley/Bridge/8617/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi 
// Source File Name:   FormatterHBLattice.java

package lattice.gui.graph;

import java.awt.*;
import java.util.TreeSet;
import java.util.Vector;
import lattice.graph.trees.*;
import lattice.graph.trees.formatter.Formatter;
import lattice.gui.graph.magneto.MagnetableRelation;

// Referenced classes of package lattice.gui.graph:
//            LatticeNodeGraph, LatticeRelation, LatticeGraphViewer

public class FormatterHBLattice extends Formatter
{

    public static final int DIST_FROM_TOP = 10;
    public int cLargeur;
    public int cHauteur;
    protected Rectangle rectParent;
    protected int maxElement;
    public Vector vNiveau;
    public Vector vNiveauRelation;
    public int nbNiveau;
    public boolean fitScreen;
    public boolean optimizerOrdre;
    public boolean keepOrder;
    public boolean init;
    public LatticeGraphViewer lgv;

    public FormatterHBLattice(Vector noeuds, Rectangle rectParent, int zoom, boolean fitScreen)
    {
        super(noeuds);
        cLargeur = 140;
        cHauteur = 100;
        maxElement = -1;
        nbNiveau = 0;
        this.fitScreen = false;
        optimizerOrdre = false;
        keepOrder = false;
        init = true;
        this.rectParent = rectParent;
        super.zoom = zoom;
        this.fitScreen = fitScreen;
    }

    public FormatterHBLattice(LatticeGraphViewer lgv, Rectangle rectParent, int zoom, boolean fitScreen)
    {
        this(((GraphViewer) (lgv)).noeuds, rectParent, zoom, fitScreen);
        this.lgv = lgv;
    }

    public int getNbNiveau()
    {
        return nbNiveau;
    }

    public int getcLargeurRel()
    {
        return (int)(((float)cLargeur * (float)zoom) / 10F);
    }

    public int getcHauteurRel()
    {
        return (int)(((float)cHauteur * (float)zoom) / 10F);
    }

    protected void effacerNiveau()
    {
        for(int i = 0; i < noeuds.size(); i++)
            ((LatticeNodeGraph)noeuds.elementAt(i)).setNiveau(-1);

        vNiveau = null;
    }

    public void initContraintes(LatticeNodeGraph top)
    {
        setCl(getcLargeurRel());
        if(fitScreen)
        {
            Vector f = feuilles(top);
            LatticeNodeGraph bottom = (LatticeNodeGraph)f.elementAt(0);
            nbNiveau = bottom.getNiveau();
            setCh((rectParent.height - 10) / (nbNiveau + 1));
        } else
        {
            setCh(getcHauteurRel());
        }
    }

    public void formatter(Noeud unNoeud)
    {
        if(init)
            formatterInit(unNoeud);
        else
        if(optimizerOrdre)
            formatterSecond(unNoeud);
        else
            formatterInit(unNoeud);
        init = false;
    }

    public void formatterInit(Noeud unNoeud)
    {
        initContraintes((LatticeNodeGraph)unNoeud);
        effacerNiveau();
        maxElement = -1;
        demarquer();
        unNoeud.setPosSup(new Point(rectParent.width / 2 - 15, 10));
        ((LatticeNodeGraph)unNoeud).setNiveau(0);
        vNiveau = new Vector();
        vNiveauRelation = new Vector();
        formatterYNiveau((LatticeNodeGraph)unNoeud, 0);
        buildvNiveau();
        formatterXNiveau((LatticeNodeGraph)unNoeud);
    }

    public void formatterSecond(Noeud unNoeud)
    {
        unNoeud.setPosSup(new Point(rectParent.width / 2 - 15, 10));
        positionneYNiveau((LatticeNodeGraph)unNoeud);
        formatterXNiveau((LatticeNodeGraph)unNoeud);
    }

    public void positionneYNiveau(LatticeNodeGraph top)
    {
        LatticeNodeGraph uneFeuille = null;
        int h = top.y();
        for(int i = 1; i < vNiveau.size(); i++)
        {
            h += ch;
            Vector vi = (Vector)vNiveau.elementAt(i);
            for(int j = 0; j < vi.size(); j++)
            {
                uneFeuille = (LatticeNodeGraph)vi.elementAt(j);
                uneFeuille.setPosSup(new Point(0, h));
            }

        }

    }

    public void formatterYNiveau(LatticeNodeGraph unNoeud, int niv)
    {
        Vector v = unNoeud.fils();
        LatticeNodeGraph uneFeuille = null;
        int h = ch + unNoeud.y();
        niv++;
        if(v.size() == 0)
            nbNiveau = niv;
        for(int i = 0; i < v.size(); i++)
        {
            uneFeuille = (LatticeNodeGraph)v.elementAt(i);
            if(uneFeuille.getNiveau() < niv)
            {
                uneFeuille.setPosSup(new Point(0, h));
                uneFeuille.setNiveau(niv);
                formatterYNiveau(uneFeuille, niv);
            }
        }

    }

    public int calcRel(int nbElement)
    {
        int cl2 = getcLargeurRel();
        int clRel = cl2 - 5 * nbElement;
        if(clRel < cl2 / 2)
            cl = cl2 / 2;
        else
            cl = clRel;
        return cl;
    }

    public int calcX(LatticeNodeGraph top, int nbElement)
    {
        if(fitScreen)
            cl = rectParent.width / nbElement;
        else
            calcRel(nbElement);
        return (top.x() + cl / 2) - (cl * nbElement) / 2;
    }

    public int incX()
    {
        return cl;
    }

    public void formatterXNiveau(LatticeNodeGraph top)
    {
        Vector noeuds = new Vector();
        LatticeNodeGraph uneFeuille = null;
        Vector vNiveauNiv = null;
        int niv = 1;
        int pX = 0;
        for(int niveau = niv; niveau < nbNiveau; niveau++)
        {
            vNiveauNiv = getNiveau(niveau);
            pX = calcX(top, vNiveauNiv.size());
            for(int i = 0; i < vNiveauNiv.size(); i++)
            {
                uneFeuille = (LatticeNodeGraph)vNiveauNiv.elementAt(i);
                uneFeuille.setPosSup(new Point(pX, uneFeuille.y()));
                pX += incX();
            }

            if(optimizerOrdre)
                minCrossing(getNiveau(niveau), false);
        }

        if(optimizerOrdre)
        {
            for(int niveau = niv; niveau < nbNiveau; niveau++)
                minCrossing(getNiveau(niveau), true);

        }
    }

    public Vector getvNiveau()
    {
        return vNiveau;
    }

    public Vector getvNiveauRelation()
    {
        return vNiveauRelation;
    }

    public Vector getNiveau(int i)
    {
        return (Vector)vNiveau.elementAt(i);
    }

    public void buildvNiveau()
    {
        for(int i = 0; i < nbNiveau; i++)
        {
            vNiveauRelation.add(new Vector());
            vNiveau.add(buildvNiveau(i));
        }

    }

    public Vector buildvNiveau(int niv)
    {
        Vector v = new Vector();
        LatticeNodeGraph noeud = null;
        for(int i = 0; i < noeuds.size(); i++)
        {
            noeud = (LatticeNodeGraph)noeuds.elementAt(i);
            if(noeud.getNiveau() == niv)
            {
                v.addElement(noeud);
                buildvNiveauRelation(noeud, niv);
            }
        }

        return v;
    }

    public void buildvNiveauRelation(LatticeNodeGraph noeud, int niv)
    {
        LatticeRelation lr = null;
        for(int i = 0; i < noeud.nbRelationArrive(); i++)
        {
            lr = (LatticeRelation)noeud.relationArrive(i);
            int niv2 = ((LatticeNodeGraph)lr.origine()).getNiveau();
            int diffNiv = niv - niv2;
            if(diffNiv > 1)
            {
                Color bgColor = LatticeRelation.NORMAL_COLOR.brighter();
                for(int j = 1; j < diffNiv; j++)
                {
                    lr.setBgColor(bgColor);
                    MagnetableRelation mr = new MagnetableRelation(lr, diffNiv, j);
                    ((Vector)vNiveauRelation.elementAt(niv2 + j)).add(mr);
                    bgColor = new Color(Math.min(bgColor.getRed() + 20, 200), Math.min(bgColor.getGreen() + 20, 200), Math.min(bgColor.getBlue() + 20, 200));
                }

            }
        }

    }

    public Vector ordonner(Vector v, LatticeNodeGraph top, int niv)
    {
        if(optimizerOrdre)
        {
            if(niv == 1)
                return v;
            else
                return ordonnerOther(v);
        } else
        {
            return v;
        }
    }

    public Vector ordonnerFirst(Vector v, LatticeNodeGraph top)
    {
        int pX = 0;
        LatticeNodeGraph uneFeuille = null;
        pX = calcX(top, v.size());
        for(int i = 0; i < v.size(); i++)
        {
            uneFeuille = (LatticeNodeGraph)v.elementAt(i);
            uneFeuille.setPosSup(new Point(pX, uneFeuille.y()));
            pX += incX();
        }

        return v;
    }

    public Vector ordonnerOther(Vector v)
    {
        TreeSet vTreeSet = new TreeSet();
        int sommeX = 0;
        for(int i = 0; i < v.size(); i++)
        {
            LatticeNodeGraph noeud = (LatticeNodeGraph)v.elementAt(i);
            Vector vPere = peres(noeud);
            sommeX = 0;
            for(int j = 0; j < vPere.size(); j++)
            {
                LatticeNodeGraph unPere = (LatticeNodeGraph)vPere.elementAt(j);
                sommeX += unPere.x();
            }

            noeud.setX(sommeX / vPere.size());
            vTreeSet.add(noeud);
        }

        return new Vector(vTreeSet);
    }

    public int minCrossing(Vector v, boolean fils)
    {
        boolean test = false;
        int nbTotal = 0;
        int nbCrossing1 = 0;
        int nbCrossing2 = 0;
        int i = 0;
        int j = 0;
        for(; i < v.size(); i++)
        {
            if(test)
                i = 0;
            test = false;
            LatticeNodeGraph noeud1 = (LatticeNodeGraph)v.elementAt(i);
            for(j = i + 1; j < v.size(); j++)
            {
                LatticeNodeGraph noeud2 = (LatticeNodeGraph)v.elementAt(j);
                nbCrossing1 = nbCrossing(peres(noeud1), peres(noeud2));
                nbCrossing2 = nbCrossing(peres(noeud2), peres(noeud1));
                if(fils)
                {
                    nbCrossing1 += nbCrossing(noeud1.fils(), noeud2.fils());
                    nbCrossing2 += nbCrossing(noeud2.fils(), noeud1.fils());
                }
                if(nbCrossing1 > nbCrossing2)
                {
                    permutter(v, i, j);
                    nbTotal += nbCrossing2;
                    test = true;
                } else
                {
                    nbTotal += nbCrossing1;
                }
            }

        }

        return nbTotal;
    }

    public void permutter(Vector v, int i, int j)
    {
        LatticeNodeGraph noeud1 = (LatticeNodeGraph)v.elementAt(i);
        LatticeNodeGraph noeud2 = (LatticeNodeGraph)v.elementAt(j);
        v.setElementAt(noeud2, i);
        int x = noeud2.x();
        noeud2.setX(noeud1.x());
        v.setElementAt(noeud1, j);
        noeud1.setX(x);
    }

    public int nbCrossing(Vector v1, Vector v2)
    {
        int nbCrossing = 0;
        for(int i = 0; i < v1.size(); i++)
        {
            LatticeNodeGraph n1 = (LatticeNodeGraph)v1.elementAt(i);
            for(int j = 0; j < v2.size(); j++)
            {
                LatticeNodeGraph n2 = (LatticeNodeGraph)v2.elementAt(j);
                if(n1.x() > n2.x())
                    nbCrossing++;
            }

        }

        return nbCrossing;
    }

    public Vector ordonnerMax(Vector v1)
    {
        Vector v2 = new Vector(v1.size());
        while(!v1.isEmpty()) 
        {
            LatticeNodeGraph max = max(v1);
            v2.add(max);
            int index = v1.indexOf(max);
            if(index != -1)
                v1.removeElementAt(index);
        }
        return v2;
    }

    public LatticeNodeGraph max(Vector v1)
    {
        int maxNbFils = -1;
        LatticeNodeGraph max = null;
        for(int i = 0; i < v1.size(); i++)
        {
            LatticeNodeGraph n1 = (LatticeNodeGraph)v1.elementAt(i);
            if(n1.nbFils() > maxNbFils)
            {
                maxNbFils = n1.nbFils();
                max = n1;
            }
        }

        return max;
    }

    public int nbCommonFils(LatticeNodeGraph n1, LatticeNodeGraph n2)
    {
        Vector f1 = n1.fils();
        Vector f2 = n2.fils();
        int n = 0;
        for(int i = 0; i < f1.size(); i++)
            if(f2.indexOf(f1.elementAt(i)) != -1)
                n++;

        return n;
    }
}
