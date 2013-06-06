// Decompiled by Jad v1.5.7g. Copyright 2000 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/SiliconValley/Bridge/8617/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi 
// Source File Name:   ruleBaseInformativeTask.java

package lattice.rules.task;

import java.awt.Frame;
import lattice.algorithm.Godin;
import lattice.algorithm.LatticeAlgorithm;
import lattice.gui.ConsoleFrame;
import lattice.gui.RelationalContextEditor;
import lattice.gui.tooltask.*;
import lattice.rules.algorithms.AbstractAlgorithm;
import lattice.rules.algorithms.BaseInformative;
import lattice.rules.generator.Jen;
import lattice.util.AbstractRelation;
import lattice.util.BinaryRelation;

// Referenced classes of package lattice.rules.task:
//            ruleAbstractTask

public class ruleBaseInformativeTask extends ruleAbstractTask
{

    private double minConfiance;

    public ruleBaseInformativeTask(RelationalContextEditor rce)
    {
        minConfiance = 0.5D;
        super.rce = rce;
    }

    public String getDescription()
    {
        return "Rule Generation (Base Informative):";
    }

    public void exec()
    {
        goodEnded = false;
        ruleBaseInformativeTask newTask = (ruleBaseInformativeTask)clone();
        newTask.taskObserver = new WaitingSetpTaskFrame(newTask, 3, rce.getConsole());
    }

    public void run()
    {
        LatticeAlgorithm algo = new Godin((BinaryRelation)rce.getSelectedRelation());
        rce.addMessage("Processing " + algo.getDescription() + " on the binary relation \"" + ((BinaryRelation)rce.getSelectedRelation()).getRelationName() + "\"");
        algo.setJobObserver(taskObserver);
        taskObserver.setTitle(getDescription() + " Processing Godin Algo.");
        algo.run();
        taskObserver.jobEnd(true);
        Jen algoJen = new Jen(algo.getLattice());
        algoJen.setJobObserver(taskObserver);
        taskObserver.setTitle(getDescription() + " Processing Jen Algo.");
        algoJen.run();
        taskObserver.jobEnd(true);
        BaseInformative baseInformative = new BaseInformative(algo.getLattice(), minConfiance);
        baseInformative.setJobObserver(taskObserver);
        taskObserver.setTitle(getDescription() + " Processing Base Informative Algo.");
        baseInformative.run();
        taskObserver.jobEnd(true);
        taskObserver.taskEnd();
        goodEnded = true;
        stringResult = baseInformative.getResultat();
        rce.showTaskResult(this);
    }

    public Object clone()
    {
        ruleBaseInformativeTask newTask = new ruleBaseInformativeTask(rce);
        newTask.theBinRelOnUse = theBinRelOnUse;
        newTask.goodEnded = goodEnded;
        newTask.taskObserver = taskObserver;
        return newTask;
    }
}
