// Decompiled by Jad v1.5.7g. Copyright 2000 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/SiliconValley/Bridge/8617/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi 
// Source File Name:   ruleLuxembourgTask.java

package lattice.rules.task;

import java.awt.Frame;
import lattice.algorithm.Godin;
import lattice.algorithm.LatticeAlgorithm;
import lattice.gui.ConsoleFrame;
import lattice.gui.RelationalContextEditor;
import lattice.gui.tooltask.*;
import lattice.rules.algorithms.AbstractAlgorithm;
import lattice.rules.algorithms.Luxemburger;
import lattice.util.AbstractRelation;
import lattice.util.BinaryRelation;

// Referenced classes of package lattice.rules.task:
//            ruleAbstractTask

public class ruleLuxembourgTask extends ruleAbstractTask
{

    private double minSupport;
    private double minConfiance;

    public ruleLuxembourgTask(RelationalContextEditor rce)
    {
        minSupport = 0.29999999999999999D;
        minConfiance = 0.5D;
        super.rce = rce;
    }

    public String getDescription()
    {
        return "Rule Generation (Luxembourg Algo.):";
    }

    public void exec()
    {
        goodEnded = false;
        ruleLuxembourgTask newTask = (ruleLuxembourgTask)clone();
        newTask.taskObserver = new WaitingSetpTaskFrame(newTask, 2, rce.getConsole());
    }

    public void run()
    {
        rce.addMessage("Generating lattice with Godin algorithm on the binary relation \"" + rce.getSelectedRelation().getRelationName() + "\"");
        LatticeAlgorithm algo = new Godin((BinaryRelation)rce.getSelectedRelation());
        algo.setJobObserver(taskObserver);
        taskObserver.setTitle(getDescription() + " Processing Godin Algo.");
        algo.run();
        Luxemburger baseCouv = new Luxemburger(algo.getLattice(), minConfiance, minSupport);
        baseCouv.setJobObserver(taskObserver);
        taskObserver.setTitle(getDescription() + " Processing Luxembourg Algo.");
        baseCouv.run();
        taskObserver.taskEnd();
        goodEnded = true;
        stringResult = baseCouv.getResultat();
        rce.showTaskResult(this);
    }

    public Object clone()
    {
        ruleLuxembourgTask newTask = new ruleLuxembourgTask(rce);
        newTask.theBinRelOnUse = theBinRelOnUse;
        newTask.goodEnded = goodEnded;
        newTask.taskObserver = taskObserver;
        return newTask;
    }
}
