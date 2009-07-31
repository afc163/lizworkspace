// Decompiled by Jad v1.5.7g. Copyright 2000 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/SiliconValley/Bridge/8617/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi 
// Source File Name:   ThumbReceiver.java

package lattice.graph.utils;

import java.awt.Image;

public interface ThumbReceiver
{

    public abstract void imageReady(Image image, String s, int i, boolean flag);
}
