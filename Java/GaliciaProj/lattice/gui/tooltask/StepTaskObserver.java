// Decompiled by Jad v1.5.7g. Copyright 2000 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/SiliconValley/Bridge/8617/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi 
// Source File Name:   StepTaskObserver.java

package lattice.gui.tooltask;


// Referenced classes of package lattice.gui.tooltask:
//            JobObserver

public interface StepTaskObserver
    extends JobObserver
{

    public abstract void setPercentageOfStep(int i);

    public abstract void setMaxStep(int i);

    public abstract void taskEnd();
}
