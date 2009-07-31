// Decompiled by Jad v1.5.7g. Copyright 2000 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/SiliconValley/Bridge/8617/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi 
// Source File Name:   Selectable.java

package lattice.graph.trees;


public interface Selectable
{

    public abstract String getLabel();

    public abstract void setSelected(boolean flag);

    public abstract boolean getSelected();

    public abstract void setClicked(boolean flag);

    public abstract boolean getClicked();
}
