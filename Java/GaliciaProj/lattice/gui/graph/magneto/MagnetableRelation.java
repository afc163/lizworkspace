// Decompiled by Jad v1.5.7g. Copyright 2000 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/SiliconValley/Bridge/8617/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi 
// Source File Name:   MagnetableRelation.java

package lattice.gui.graph.magneto;

import java.util.Vector;
import lattice.graph.trees.Noeud;
import lattice.graph.trees.Relation;
import lattice.gui.graph.LatticeNodeGraph;
import lattice.gui.graph.LatticeRelation;

// Referenced classes of package lattice.gui.graph.magneto:
//            Magnetable

public class MagnetableRelation
    implements Magnetable
{

    public LatticeRelation lr;
    public int difNbNiveau;
    public int rang;
    public boolean isMagnetable;

    public MagnetableRelation(LatticeRelation lr, int difNbNiveau, int rang)
    {
        isMagnetable = true;
        this.lr = lr;
        this.difNbNiveau = difNbNiveau;
        this.rang = rang;
    }

    public int xCoord()
    {
        int x = 0;
        int xOrigine = lr.origine().x();
        int xExtremite = lr.extremite().x();
        if(xOrigine > xExtremite)
            return xExtremite + (rang * (xOrigine - xExtremite)) / difNbNiveau;
        else
            return xOrigine + (rang * (xExtremite - xOrigine)) / difNbNiveau;
    }

    public int yCoord()
    {
        int y = 0;
        int yOrigine = lr.origine().y();
        int yExtremite = lr.extremite().y();
        if(yOrigine < yExtremite)
            return yOrigine + (rang * (yExtremite - yOrigine)) / difNbNiveau;
        else
            return 0;
    }

    public int zCoord()
    {
        int z = 0;
        int zOrigine = lr.origine().z();
        int zExtremite = lr.extremite().z();
        if(zOrigine < zExtremite)
            return zOrigine + (rang * (zExtremite - zOrigine)) / difNbNiveau;
        else
            return 0;
    }

    public Vector getParents()
    {
        return null;
    }

    public Vector getChildren()
    {
        return null;
    }

    public boolean isMagnetable()
    {
        return isMagnetable;
    }

    public void setIsMagnetable(boolean b)
    {
        isMagnetable = b;
    }

    public double tensionX(boolean b)
    {
        return 0.0D;
    }

    public double tensionY(boolean b)
    {
        return 0.0D;
    }

    public double tensionZ(boolean b)
    {
        return 0.0D;
    }

    public double repulsion()
    {
        return 0.40000000000000002D;
    }

    public boolean goRight()
    {
        return true;
    }

    public void setGoRight(boolean flag)
    {
    }

    public void move(int dx, int dy)
    {
        int dx2 = dx - (rang * dx) / difNbNiveau / 4;
        int dx3 = (rang * dx) / difNbNiveau / 4;
        LatticeNodeGraph lng = (LatticeNodeGraph)lr.extremite();
        if(lng.isMagnetable())
            lng.move(dx2, dy);
        lng = (LatticeNodeGraph)lr.origine();
        if(lng.isMagnetable())
            lng.move(dx3, dy);
    }

    public void move(int i, int j, int k)
    {
    }

    public int getDifNbNiveau()
    {
        return difNbNiveau;
    }

    public void setDifNbNiveau(int difNbNiveau)
    {
        this.difNbNiveau = difNbNiveau;
    }

    public boolean isIsMagnetable()
    {
        return isMagnetable;
    }
}
