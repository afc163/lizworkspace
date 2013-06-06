// Decompiled by Jad v1.5.7g. Copyright 2000 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/SiliconValley/Bridge/8617/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi 
// Source File Name:   JobObservable.java

package lattice.gui.tooltask;


// Referenced classes of package lattice.gui.tooltask:
//            JobObserver

public interface JobObservable
    extends Runnable
{

    public abstract String getDescription();

    public abstract void setJobObserver(JobObserver jobobserver);

    public abstract void sendMessage(String s);

    public abstract void sendJobPercentage(int i);
}
