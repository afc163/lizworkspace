// Decompiled by Jad v1.5.7g. Copyright 2000 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/SiliconValley/Bridge/8617/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi 
// Source File Name:   LatticeAlgorithm.java

package lattice.algorithm;

import lattice.gui.tooltask.AbstractJob;
import lattice.gui.tooltask.JobObserver;
import lattice.util.*;

public abstract class LatticeAlgorithm extends AbstractJob
{

    protected ConceptLattice lcl;
    protected BinaryRelation binRel;

    public LatticeAlgorithm()
    {
        lcl = null;
        binRel = null;
        lcl = new LinkedConceptLattice();
    }

    public LatticeAlgorithm(BinaryRelation bRel)
    {
        lcl = null;
        binRel = null;
        lcl = new LinkedConceptLattice();
        binRel = bRel;
    }

    public ConceptLattice getLattice()
    {
    	//lcl中已经有东西
        return lcl;
    }

    public BinaryRelation getBinaryRelation()
    {
        return binRel;
    }

    public void setLattice(ConceptLattice lcl)
    {
        this.lcl = lcl;
    }

    public abstract void doAlgorithm();

    public abstract String getDescription();

    public void run()
    {
        if(jobObserv != null)
            jobObserv.sendMessage("Start : " + getDescription() + " on relation named " + binRel.getRelationName());
        doAlgorithm();
        lcl.setDescription(getDescription() + " - " + binRel.getRelationName() + " - #OfNodes = " + lcl.size());
        binRel.setLattice(lcl);
        if(jobObserv != null)
        {
            jobObserv.sendMessage("End : " + getDescription() + " on relation named " + binRel.getRelationName() + "\n");
            jobObserv.jobEnd(true);
        }
    }
}
