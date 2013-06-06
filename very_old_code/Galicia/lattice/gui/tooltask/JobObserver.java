// Decompiled by Jad v1.5.7g. Copyright 2000 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/SiliconValley/Bridge/8617/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi 
// Source File Name:   JobObserver.java

package lattice.gui.tooltask;


// Referenced classes of package lattice.gui.tooltask:
//            JobObservable

public interface JobObserver
{

    public abstract void setTitle(String s);

    public abstract void setPercentageOfWork(int i);

    public abstract void sendMessage(String s);

    public abstract void jobEnd(boolean flag);

    public abstract void setJobObservable(JobObservable jobobservable);
}
