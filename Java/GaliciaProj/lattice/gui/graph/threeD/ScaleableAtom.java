// Decompiled by Jad v1.5.7g. Copyright 2000 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/SiliconValley/Bridge/8617/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi 
// Source File Name:   ScaleableAtom.java

package lattice.gui.graph.threeD;

import java.awt.Graphics;

public interface ScaleableAtom
{

    public abstract boolean Exist();

    public abstract int blend(int i, int j, float f);

    public abstract void Setup(int i);

    public abstract void paint(Graphics g, int i, int j, int k);
}
