// Decompiled by Jad v1.5.7g. Copyright 2000 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/SiliconValley/Bridge/8617/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi 
// Source File Name:   Intent.java

package lattice.util;

import java.util.SortedSet;

public interface Intent
    extends SortedSet
{
    //�ں��Ĳ���
    public abstract Intent union(Intent intent);

    //�ں��Ľ���
    public abstract Intent intersection(Intent intent);
    
    //�ں��Ľ��� used in GodinDB 
    public abstract Intent intersection(Intent intent,int a);

    //���Ʋ���
    public abstract Object clone();
}
