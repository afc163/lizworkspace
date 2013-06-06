// Decompiled by Jad v1.5.7g. Copyright 2000 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/SiliconValley/Bridge/8617/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi 
// Source File Name:   StepTaskObservable.java

package lattice.gui.tooltask;

import java.awt.event.WindowListener;

// Referenced classes of package lattice.gui.tooltask:
//            JobObservable

public interface StepTaskObservable
    extends JobObservable, WindowListener
{

    public abstract void exec();

    public abstract boolean isGoodEnded();
}
