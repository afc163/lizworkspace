// Decompiled by Jad v1.5.7g. Copyright 2000 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/SiliconValley/Bridge/8617/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi 
// Source File Name:   Extent.java

package lattice.util;

import java.util.SortedSet;

public interface Extent
    extends SortedSet
{

	//���ӵĲ���
    public abstract Extent union(Extent extent);

    //���ӵĽ���
    public abstract Extent intersection(Extent extent);

    //���Ʋ���
    public abstract Object clone();
}
