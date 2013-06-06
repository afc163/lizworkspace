// Decompiled by Jad v1.5.7g. Copyright 2000 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/SiliconValley/Bridge/8617/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi 
// Source File Name:   InfoListener.java

package lattice.graph.utils;

import java.awt.event.ActionListener;

public interface InfoListener
    extends ActionListener
{

    public abstract void setInfo(String s);

    public abstract void removeInfo();
}
