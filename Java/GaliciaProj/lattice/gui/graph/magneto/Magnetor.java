// Decompiled by Jad v1.5.7g. Copyright 2000 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/SiliconValley/Bridge/8617/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi 
// Source File Name:   Magnetor.java

package lattice.gui.graph.magneto;

import java.util.Vector;

// Referenced classes of package lattice.gui.graph.magneto:
//            ActionObject, Magnetable, Magneto, MagnetableRelation

class Magnetor extends ActionObject
{

    Vector magnetableRelations;
    int ordonneLevel;

    Magnetor(Magneto magneto, int level, int nbLevel, Vector magnetables, Vector magnetablesRelation)
    {
        super(magneto, level, nbLevel);
        init(magnetables, magnetablesRelation);
    }

    void init(Vector v1, Vector v2)
    {
        magnetables = new Vector();
        nbMagnetables = v1.size() + v2.size();
        for(int i = 0; i < v1.size(); i++)
            magnetables.add(v1.elementAt(i));

        for(int j = 0; j < v2.size(); j++)
            magnetables.add(v2.elementAt(j));

        if(magnetables.size() > 0)
            ordonneLevel = getMagnetable(0).yCoord();
    }

    protected boolean doAction(boolean threeDMode)
    {
        if(!magneto.getMagnet())
            return false;
        boolean deplacement = false;
        if(magnetables.size() > 0)
            ordonneLevel = getMagnetable(0).yCoord();
        for(int i = 0; i < nbMagnetables; i++)
        {
            Magnetable n = getMagnetable(i);
            if((!(n instanceof MagnetableRelation) || magneto.magnetRelation()) && n.isMagnetable())
            {
                int decalX = 0;
                int decalZ = 0;
                if(Math.abs(ordonneLevel - n.yCoord()) < 10)
                {
                    decalX = repulsionX(n);
                    if(threeDMode)
                        decalZ = repulsionZ(n);
                }
                double tensionX = (int)(magneto.getTensionFactor() * n.tensionX(true));
                double tensionZ = 0.0D;
                if(threeDMode)
                    tensionZ = (int)(magneto.getTensionFactor() * n.tensionZ(true));
                int decalY = decalY(n);
                decalX += (int)tensionX;
                if(threeDMode)
                    decalZ += (int)tensionZ;
                n.move(decalX, decalY, decalZ);
                if(decalX != 0 || decalY != 0 || decalZ != 0)
                    deplacement = true;
            }
        }

        return deplacement;
    }

    int decalY(Magnetable n)
    {
        if(n.yCoord() != ordonneLevel)
        {
            int decalY = (ordonneLevel - n.yCoord()) / 5;
            if(Math.abs(decalY) <= 1)
                decalY = ordonneLevel - n.yCoord();
            return decalY;
        } else
        {
            return 0;
        }
    }

    int repulsionX(Magnetable n)
    {
        int pression = pressionX(n);
        int decal = (int)Math.round((double)pression * magneto.getRepulsionFactor());
        return decal;
    }

    public int pressionX(Magnetable n)
    {
        int pression = 0;
        Magnetable noeud = null;
        int cl = (int)((double)magneto.getcLargeur(nbMagnetables) * 1.3D);
        int ch = magneto.getcHauteur() / 3;
        int unlink = cl / 3;
        int decal = 0;
        if(magneto.magnetMouse && Math.abs(ordonneLevel - y) < ch)
        {
            if(x >= n.xCoord())
            {
                decal = x - n.xCoord() - cl;
                if(n.xCoord() + cl > x)
                    pression += decal - (decal * Math.abs(ordonneLevel - y)) / ch;
            }
            if(x < n.xCoord())
            {
                decal = n.xCoord() - x - cl;
                if(n.xCoord() - cl < x)
                    pression -= decal - (decal * Math.abs(ordonneLevel - y)) / ch;
            }
        }
        for(int i = 0; i < nbMagnetables; i++)
        {
            noeud = getMagnetable(i);
            double dz = Math.abs(noeud.zCoord() - n.zCoord());
            double dx = Math.abs(noeud.xCoord() - n.xCoord());
            decal = (int)dx - cl;
            if(active(noeud) && dz < (double)cl)
            {
                if(noeud.xCoord() >= n.xCoord() && noeud != n)
                {
                    int pressionInverse = pressionInverseX(n, noeud, cl);
                    if(pressionInverse != 0)
                        pression -= pressionInverse;
                    else
                    if(n.xCoord() + cl > noeud.xCoord())
                        pression = (int)((double)pression + (double)decal * noeud.repulsion());
                }
                if(noeud.xCoord() < n.xCoord())
                {
                    int pressionInverse = pressionInverseX(noeud, n, cl);
                    if(pressionInverse == 0 && n.xCoord() - cl < noeud.xCoord())
                        pression = (int)((double)pression - (double)decal * noeud.repulsion());
                }
            }
        }

        return pression;
    }

    boolean active(Magnetable m)
    {
        if(m instanceof MagnetableRelation)
        {
            if(!magneto.magnetRelation())
                return false;
        } else
        if(Math.abs(ordonneLevel - m.yCoord()) > 10)
            return false;
        return true;
    }

    public int pressionInverseX(Magnetable n, Magnetable noeud, int cl)
    {
        return 0;
    }

    int repulsionZ(Magnetable n)
    {
        int pression = pressionZ(n);
        int decal = (int)Math.round((double)pression * magneto.getRepulsionFactor());
        return decal;
    }

    public int pressionZ(Magnetable n)
    {
        int pression = 0;
        Magnetable noeud = null;
        int cl = (int)((double)magneto.getcLargeur(nbMagnetables) * 1.3D);
        int unlink = cl / 3;
        int decal = 0;
        for(int i = 0; i < nbMagnetables; i++)
        {
            noeud = getMagnetable(i);
            double dx = Math.abs(noeud.xCoord() - n.xCoord());
            double dz = Math.abs(noeud.zCoord() - n.zCoord());
            decal = (int)dz - cl;
            if(active(noeud) && dx < (double)cl)
            {
                if(noeud.zCoord() >= n.zCoord() && noeud != n)
                {
                    int pressionInverse = pressionInverseZ(n, noeud, cl);
                    if(pressionInverse != 0)
                        pression -= pressionInverse;
                    else
                    if(n.zCoord() + cl > noeud.zCoord())
                        pression = (int)((double)pression + (double)decal * noeud.repulsion());
                }
                if(noeud.zCoord() < n.zCoord())
                {
                    int pressionInverse = pressionInverseZ(noeud, n, cl);
                    if(pressionInverse == 0 && n.zCoord() - cl < noeud.zCoord())
                        pression = (int)((double)pression - (double)decal * noeud.repulsion());
                }
            }
        }

        return pression;
    }

    public int pressionInverseZ(Magnetable n, Magnetable noeud, int cl)
    {
        return 0;
    }
}
