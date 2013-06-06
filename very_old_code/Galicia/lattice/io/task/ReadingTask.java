// Decompiled by Jad v1.5.7g. Copyright 2000 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/SiliconValley/Bridge/8617/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi 
// Source File Name:   ReadingTask.java

package lattice.io.task;

import java.awt.Frame;
import lattice.gui.ConsoleFrame;
import lattice.gui.RelationalContextEditor;
import lattice.gui.tooltask.*;
import lattice.io.AbstractReader;

public class ReadingTask extends AbstractTask
{

    private RelationalContextEditor rce;
    private AbstractReader ar;
    private Object dataResult;
    private String defaultNameForData;

    public ReadingTask(RelationalContextEditor rce)
    {
        this.rce = null;
        ar = null;
        dataResult = null;
        defaultNameForData = "Default Name";
        this.rce = rce;
    }

    public void exec()
    {
        goodEnded = false;
        ReadingTask newTask = (ReadingTask)clone();
        newTask.taskObserver = new WaitingSetpTaskFrame(newTask, 1, rce.getConsole());
    }

    public String getDescription()
    {
        return "Reading Task :";
    }

    public void run()
    {
        ar.setJobObserver(taskObserver);
        taskObserver.setTitle(getDescription() + " Processing ...");
        ar.run();
        taskObserver.jobEnd(true);
        taskObserver.taskEnd();
        goodEnded = true;
        dataResult = ar.getData();
        rce.showTaskResult(this);
    }

    public Object getData()
    {
        return dataResult;
    }

    public String getDefaultNameForData()
    {
        return defaultNameForData;
    }

    public void setDataReader(AbstractReader ar)
    {
        this.ar = ar;
        defaultNameForData = ar.getDefaultNameForData();
    }

    public Object clone()
    {
        ReadingTask rt = new ReadingTask(rce);
        rt.setDataReader(ar);
        rt.goodEnded = goodEnded;
        return rt;
    }
}
