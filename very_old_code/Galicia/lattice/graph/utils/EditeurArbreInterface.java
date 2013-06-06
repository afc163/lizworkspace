// Decompiled by Jad v1.5.7g. Copyright 2000 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/SiliconValley/Bridge/8617/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi 
// Source File Name:   EditeurArbreInterface.java

package lattice.graph.utils;

import lattice.graph.trees.ActionGraphViewer;

public interface EditeurArbreInterface
{

    public abstract ActionGraphViewer getCanvas();

    public abstract void changeAffZoomViewer();

    public abstract void changeMode();

    public abstract void affAttributs();

    public abstract void loadLocal();

    public abstract void loadDistant();

    public abstract void sauverLocal();

    public abstract void showDocument();

    public abstract void affAttributs2();

    public abstract void changeMode2();

    public abstract void changeAffZoomViewer2();

    public abstract void changeLangue(int i);
}
