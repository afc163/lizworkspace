// Decompiled by Jad v1.5.7g. Copyright 2000 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/SiliconValley/Bridge/8617/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi 
// Source File Name:   ruleDiattaTask.java

package lattice.rules.task;

import java.awt.Frame;
import lattice.gui.ConsoleFrame;
import lattice.gui.RelationalContextEditor;
import lattice.gui.tooltask.*;
import lattice.rules.algorithms.AbstractAlgorithm;
import lattice.rules.algorithms.DiattaAlgorithm;
import lattice.util.BinaryRelation;

// Referenced classes of package lattice.rules.task:
//            ruleAbstractTask

public class ruleDiattaTask extends ruleAbstractTask
{

    private double minSupport;
    private double minConfiance;

    public ruleDiattaTask(RelationalContextEditor rce)
    {
        minSupport = 0.29999999999999999D;
        minConfiance = 0.5D;
        super.rce = rce;
    }

    public String getDescription()
    {
        return "Diatta Algorithm";
    }

    public void exec()
    {
        goodEnded = false;
        ruleDiattaTask newTask = (ruleDiattaTask)clone();
        newTask.taskObserver = new WaitingSetpTaskFrame(newTask, 1, rce.getConsole());
    }

    public void run()
    {
        DiattaAlgorithm algo = new DiattaAlgorithm((BinaryRelation)rce.getSelectedRelation(), minConfiance, minSupport);
        algo.setJobObserver(taskObserver);
        taskObserver.setTitle(getDescription() + " Processing Diatta Algo.");
        algo.run();
        taskObserver.jobEnd(true);
        taskObserver.taskEnd();
        goodEnded = true;
        stringResult = algo.getResultat();
        rce.showTaskResult(this);
    }

    public Object clone()
    {
        ruleDiattaTask newTask = new ruleDiattaTask(rce);
        newTask.theBinRelOnUse = theBinRelOnUse;
        newTask.goodEnded = goodEnded;
        newTask.taskObserver = taskObserver;
        return newTask;
    }
}
