// Decompiled by Jad v1.5.7g. Copyright 2000 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/SiliconValley/Bridge/8617/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi 
// Source File Name:   latticeAlgorithmTask.java

package lattice.algorithm.task;

import java.awt.Frame;
import lattice.algorithm.LatticeAlgorithm;
import lattice.gui.ConsoleFrame;
import lattice.gui.RelationalContextEditor;
import lattice.gui.tooltask.*;

public class latticeAlgorithmTask extends AbstractTask
{

    LatticeAlgorithm algo;
    private RelationalContextEditor rce;
    

    public latticeAlgorithmTask(RelationalContextEditor rce)
    {
        algo = null;
        this.rce = null;
        this.rce = rce;
    }

    public void setAlgo(LatticeAlgorithm algo)
    {
        this.algo = algo;
        
    }

    public LatticeAlgorithm getAlgo()
    {
        return algo;
    }

    public void exec()
    {
        goodEnded = false;
        latticeAlgorithmTask newTask = (latticeAlgorithmTask)clone();
        newTask.taskObserver = new WaitingSetpTaskFrame(newTask, 1, rce.getConsole());
    }

    public String getDescription()
    {
        return "Algorithm Execution :";
    }

    public void run()
    {
        algo.setJobObserver(taskObserver);
        //提示面板
        taskObserver.setTitle(getDescription() + " Processing " + algo.getDescription());
        algo.run();
        taskObserver.jobEnd(true);
        taskObserver.taskEnd();  //关闭进度条显示板
        goodEnded = true;
        rce.showTaskResult(this);  //显示格图的函数
    }

    public Object clone()
    {
        latticeAlgorithmTask newTask = new latticeAlgorithmTask(rce);
        newTask.algo = algo;
        newTask.theBinRelOnUse = theBinRelOnUse;
        newTask.goodEnded = goodEnded;
        newTask.taskObserver = taskObserver;
        return newTask;
    }
}
