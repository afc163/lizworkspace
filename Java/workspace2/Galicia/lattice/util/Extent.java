// Decompiled by Jad v1.5.7g. Copyright 2000 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/SiliconValley/Bridge/8617/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi 
// Source File Name:   Extent.java

package lattice.util;

import java.util.SortedSet;

public interface Extent
    extends SortedSet
{

	//外延的并集
    public abstract Extent union(Extent extent);

    //外延的交集
    public abstract Extent intersection(Extent extent);

    //复制操作
    public abstract Object clone();
}
