// Decompiled by Jad v1.5.7g. Copyright 2000 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/SiliconValley/Bridge/8617/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi 
// Source File Name:   Build3DLattice.java

package lattice.gui.graph.threeD;

import java.io.PrintStream;
import java.util.Vector;
import lattice.graph.trees.Composant;
import lattice.gui.graph.LatticeNodeGraph;

public class Build3DLattice
{

    public Build3DLattice(Vector niveau)
    {
        buildModel(niveau);
    }

    public void buildModel(Vector niveau)
    {
        int nbNiveau = niveau.size();
        double beta = 3.1415926535897931D / (double)(nbNiveau - 1);
        for(int i = 0; i < nbNiveau; i++)
        {
            Vector vNiv = (Vector)niveau.elementAt(i);
            System.out.println("Level n " + i);
            if(i == 0 || i == nbNiveau - 1)
                buildNiveauModel(vNiv, Math.sin((double)i * beta), true, 0.0D);
            else
                buildNiveauModel(vNiv, Math.sin((double)i * beta), false);
        }

    }

    public void buildNiveauModel(Vector niveau, double larg, boolean b)
    {
        boolean stop = false;
        double gamma = 0.0D;
        buildNiveauModel(niveau, larg, b, gamma);
        double tension = calcTension(niveau);
        double dGamma = 0.26179938779914941D;
        int i = 0;
        boolean right = false;
        while(!stop && i < 100) 
        {
            i++;
            gamma += dGamma;
            buildNiveauModel(niveau, larg, b, gamma);
            double tension2 = calcTension(niveau);
            System.out.println("tension = " + tension + " tension2 = " + tension2);
            if(tension <= tension2)
            {
                if(dGamma < 0.0D)
                {
                    gamma -= dGamma;
                    buildNiveauModel(niveau, larg, b, gamma);
                    stop = true;
                } else
                {
                    dGamma = -dGamma;
                    gamma += dGamma;
                    buildNiveauModel(niveau, larg, b, gamma);
                    if(right)
                        stop = true;
                    tension = calcTension(niveau);
                }
            } else
            {
                right = true;
                tension = tension2;
            }
        }
    }

    public void buildNiveauModel(Vector niveau, double larg, boolean b, double gamma)
    {
        int nbNode = niveau.size();
        double alpha = 6.2831853071795862D / (double)nbNode;
        for(int i = 0; i < nbNode; i++)
        {
            LatticeNodeGraph unNoeud = (LatticeNodeGraph)niveau.elementAt(i);
            double x = larg * 200D * Math.cos((double)i * alpha + gamma);
            double z = larg * 200D * Math.sin((double)i * alpha + gamma);
            if(b)
                unNoeud.init3D(255, 0, 0, 1.5D);
            else
                unNoeud.init3D(180, 180, 180, 1.5D);
            unNoeud.setX(x);
            unNoeud.setZ(z);
        }

    }

    private double calcTension(Vector niveau)
    {
        double sommeTension = 0.0D;
        for(int i = 0; i < niveau.size(); i++)
        {
            LatticeNodeGraph unNoeud = (LatticeNodeGraph)niveau.elementAt(i);
            unNoeud.tensionX = 4.9406564584124654E-324D;
            unNoeud.tensionZ = 4.9406564584124654E-324D;
            sommeTension += Math.abs(unNoeud.tensionX(false));
            sommeTension += Math.abs(unNoeud.tensionZ(false));
        }

        return sommeTension;
    }
}
