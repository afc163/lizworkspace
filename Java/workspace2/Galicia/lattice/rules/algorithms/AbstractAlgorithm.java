// Decompiled by Jad v1.5.7g. Copyright 2000 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/SiliconValley/Bridge/8617/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi 
// Source File Name:   AbstractAlgorithm.java

package lattice.rules.algorithms;

import java.util.Vector;
import lattice.gui.tooltask.AbstractJob;
import lattice.rules.util.RuleSet;
import lattice.util.*;

public abstract class AbstractAlgorithm extends AbstractJob
{

    protected StringBuffer resultat;
    protected BinaryRelation binRel;
    protected ConceptLattice lattice;
    protected double minConfiance;
    protected double minSupport;
    protected RuleSet ruleSet;

    public AbstractAlgorithm(BinaryRelation binRel, double minConfiance, double minSupport)
    {
        resultat = new StringBuffer();
        this.minConfiance = 0.5D;
        this.minSupport = 0.29999999999999999D;
        ruleSet = new RuleSet();
        this.binRel = binRel;
        this.minSupport = minSupport;
        this.minConfiance = minConfiance;
    }

    public AbstractAlgorithm(ConceptLattice lattice, double minConfiance, double minSupport)
    {
        resultat = new StringBuffer();
        this.minConfiance = 0.5D;
        this.minSupport = 0.29999999999999999D;
        ruleSet = new RuleSet();
        this.lattice = lattice;
        this.minSupport = minSupport;
        this.minConfiance = minConfiance;
    }

    public double getMinConfiance()
    {
        return minConfiance;
    }

    public void setMinConfiance(double minConfiance)
    {
        this.minConfiance = minConfiance;
    }

    public double getMinSupport()
    {
        return minSupport;
    }

    public void setMinSupport(double minSupport)
    {
        this.minSupport = minSupport;
    }

    public Vector getBase()
    {
        return ruleSet.getRuleSet();
    }

    public int size()
    {
        return ruleSet.size();
    }

    public void doAlgorithm()
    {
        if(binRel != null)
        {
            resultat.append("Processing " + getDescription() + " on the binary relation \"" + binRel.getRelationName() + "\"" + "\n");
            resultat.append("Min Confiance = " + minConfiance + " ; Min Support = " + minSupport + " ; Nb Objects = " + binRel.getObjectsNumber() + "\n");
        } else
        {
            resultat.append("Processing " + getDescription() + "\n");
            resultat.append("Min Confiance = " + minConfiance + " ; Min Support = " + minSupport + "\n");
        }
    }

    public String getResultat()
    {
        return resultat.toString();
    }
}
