// Decompiled by Jad v1.5.7g. Copyright 2000 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/SiliconValley/Bridge/8617/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi 
// Source File Name:   FormalObject.java

package lattice.util;


public interface FormalObject
    extends Comparable
{

    public abstract String toString();

    public abstract boolean equals(Object obj);

    public abstract String getName();
}
