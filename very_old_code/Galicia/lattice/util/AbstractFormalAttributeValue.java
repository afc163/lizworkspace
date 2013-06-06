// Decompiled by Jad v1.5.7g. Copyright 2000 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/SiliconValley/Bridge/8617/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi 
// Source File Name:   AbstractFormalAttributeValue.java

package lattice.util;


public abstract class AbstractFormalAttributeValue
    implements Comparable
{

    public AbstractFormalAttributeValue()
    {
    }

    public abstract int size();

    public abstract boolean equals(Object obj);

    public int hashCode()
    {
        return toString().hashCode();
    }

    public boolean isEmpty()
    {
        return size() == 0;
    }

    public abstract String toString();

    public abstract int compareTo(Object obj);
}
