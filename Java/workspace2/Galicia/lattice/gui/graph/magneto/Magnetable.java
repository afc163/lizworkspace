// Decompiled by Jad v1.5.7g. Copyright 2000 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/SiliconValley/Bridge/8617/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi 
// Source File Name:   Magnetable.java

package lattice.gui.graph.magneto;

import java.util.Vector;

public interface Magnetable
{

    public abstract int xCoord();

    public abstract int yCoord();

    public abstract int zCoord();

    public abstract Vector getParents();

    public abstract Vector getChildren();

    public abstract boolean isMagnetable();

    public abstract void setIsMagnetable(boolean flag);

    public abstract double tensionX(boolean flag);

    public abstract double tensionY(boolean flag);

    public abstract double tensionZ(boolean flag);

    public abstract double repulsion();

    public abstract void move(int i, int j);

    public abstract void move(int i, int j, int k);

    public abstract boolean goRight();

    public abstract void setGoRight(boolean flag);
}
