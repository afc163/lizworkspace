// Decompiled by Jad v1.5.7g. Copyright 2000 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/SiliconValley/Bridge/8617/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi 
// Source File Name:   AbstractJob.java

package lattice.gui.tooltask;


// Referenced classes of package lattice.gui.tooltask:
//            JobObservable, JobObserver

public abstract class AbstractJob
    implements JobObservable
{

    protected JobObserver jobObserv;

    public AbstractJob()
    {
    }

    public void setJobObserver(JobObserver jO)
    {
        jobObserv = jO;
    }

    public void sendMessage(String aMess)
    {
        if(jobObserv != null)
            jobObserv.sendMessage(aMess);
    }

    public void sendJobPercentage(int i)
    {
        if(jobObserv != null)
            jobObserv.setPercentageOfWork(i);
    }

    public abstract void run();

    public abstract String getDescription();
}
