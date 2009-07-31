// Decompiled by Jad v1.5.7g. Copyright 2000 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/SiliconValley/Bridge/8617/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi 
// Source File Name:   AbstractTask.java

package lattice.gui.tooltask;

import java.awt.event.WindowEvent;
import java.util.Vector;

// Referenced classes of package lattice.gui.tooltask:
//            StepTaskObservable, WaitingSetpTaskFrame, JobObserver

public abstract class AbstractTask
    implements StepTaskObservable
{

    protected WaitingSetpTaskFrame taskObserver;
    protected boolean goodEnded;
    protected Vector theBinRelOnUse;

    public AbstractTask()
    {
        taskObserver = null;
        goodEnded = false;
        theBinRelOnUse = new Vector();
    }

    public void setBinRelOnUse(Vector theBinRelOnUse)
    {
        this.theBinRelOnUse = theBinRelOnUse;
    }

    public Vector getBinRelOnUse()
    {
        return theBinRelOnUse;
    }

    public boolean isGoodEnded()
    {
        return goodEnded;
    }

    public void setJobObserver(JobObserver jobobserver)
    {
    }

    public void sendJobPercentage(int j)
    {
    }

    public void sendMessage(String aMess)
    {
        if(taskObserver != null)
            taskObserver.sendMessage(aMess);
    }

    public void windowOpened(WindowEvent windowevent)
    {
    }

    public void windowClosing(WindowEvent windowevent)
    {
    }

    public void windowClosed(WindowEvent windowevent)
    {
    }

    public void windowIconified(WindowEvent windowevent)
    {
    }

    public void windowDeiconified(WindowEvent windowevent)
    {
    }

    public void windowActivated(WindowEvent windowevent)
    {
    }

    public void windowDeactivated(WindowEvent windowevent)
    {
    }

    public abstract void run();

    public abstract String getDescription();

    public abstract void exec();
}
