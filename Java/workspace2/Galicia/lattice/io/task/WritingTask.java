// Decompiled by Jad v1.5.7g. Copyright 2000 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/SiliconValley/Bridge/8617/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi 
// Source File Name:   WritingTask.java

package lattice.io.task;

import java.awt.Frame;
import lattice.gui.ConsoleFrame;
import lattice.gui.RelationalContextEditor;
import lattice.gui.tooltask.*;
import lattice.io.AbstractWriter;
import lattice.io.ConsoleWriter;
import lattice.util.RelationalContext;

public class WritingTask extends AbstractTask
{

    private RelationalContextEditor rce;
    private AbstractWriter aw;
    private Object data;

    public WritingTask(RelationalContextEditor rce)
    {
        this.rce = null;
        aw = null;
        data = null;
        this.rce = rce;
    }

    public void exec()
    {
        goodEnded = false;
        WritingTask newTask = (WritingTask)clone();
        if(newTask.getData() instanceof RelationalContext)
            newTask.taskObserver = new WaitingSetpTaskFrame(newTask, 1, rce.getConsole());
        else
            newTask.taskObserver = new WaitingSetpTaskFrame(newTask, 1, rce.getConsole());
    }

    public String getDescription()
    {
        return "Writing Task :";
    }

    public void run()
    {
        aw.setJobObserver(taskObserver);
        taskObserver.setTitle(getDescription() + " Processing ...");
        aw.run();
        taskObserver.jobEnd(true);
        taskObserver.taskEnd();
        goodEnded = true;
        rce.showTaskResult("Writing done !\n");
    }

    public void setDataWriter(AbstractWriter aw)
    {
        this.aw = aw;
        data = aw.getData();
    }

    public void setConsoleWriter(ConsoleWriter aw)
    {
        this.aw = aw;
        data = aw.getData();
    }

    public void setData(Object data)
    {
        this.data = data;
    }

    public Object getData()
    {
        return data;
    }

    public Object clone()
    {
        WritingTask wt = new WritingTask(rce);
        wt.setDataWriter(aw);
        wt.setData(data);
        wt.goodEnded = goodEnded;
        return wt;
    }
}
