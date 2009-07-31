// Decompiled by Jad v1.5.7g. Copyright 2000 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/SiliconValley/Bridge/8617/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi 
// Source File Name:   rulePasquierTask.java

package lattice.rules.task;

import java.awt.Frame;
import lattice.algorithm.Godin;
import lattice.algorithm.LatticeAlgorithm;
import lattice.gui.ConsoleFrame;
import lattice.gui.RelationalContextEditor;
import lattice.gui.tooltask.*;
import lattice.rules.algorithms.AbstractAlgorithm;
import lattice.rules.algorithms.BaseGenerique;
import lattice.rules.generator.Jen;
import lattice.util.BinaryRelation;

// Referenced classes of package lattice.rules.task:
//            ruleAbstractTask

public class rulePasquierTask extends ruleAbstractTask
{

    private double minConfiance;

    public rulePasquierTask(RelationalContextEditor rce)
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
        rulePasquierTask newTask = (rulePasquierTask)clone();
        newTask.taskObserver = new WaitingSetpTaskFrame(newTask, 3, rce.getConsole());
    }

    public void run()
    {
        LatticeAlgorithm algo = new Godin((BinaryRelation)rce.getSelectedRelation());
        algo.setJobObserver(taskObserver);
        taskObserver.setTitle(getDescription() + " Processing Godin Algo.");
        algo.run();
        taskObserver.jobEnd(true);
        Jen algoJen = new Jen(algo.getLattice());
        taskObserver.setTitle(getDescription() + " Processing Jen Algo.");
        algoJen.calculGenerateurs();
        taskObserver.jobEnd(true);
        BaseGenerique baseGenerique = new BaseGenerique(algo.getLattice(), 0.29999999999999999D);
        baseGenerique.setJobObserver(taskObserver);
        taskObserver.setTitle(getDescription() + " Processing Base Generique Algo.");
        baseGenerique.run();
        taskObserver.jobEnd(true);
        taskObserver.taskEnd();
        goodEnded = true;
        stringResult = baseGenerique.getResultat();
        rce.showTaskResult(this);
    }

    public Object clone()
    {
        rulePasquierTask newTask = new rulePasquierTask(rce);
        newTask.theBinRelOnUse = theBinRelOnUse;
        newTask.goodEnded = goodEnded;
        newTask.taskObserver = taskObserver;
        return newTask;
    }
}
