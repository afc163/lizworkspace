// Decompiled by Jad v1.5.7g. Copyright 2000 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/SiliconValley/Bridge/8617/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi 
// Source File Name:   FormalAttribute.java

package lattice.util;

import java.util.TreeSet;
import java.util.Vector;

// Referenced classes of package lattice.util:
//            Extent, BinaryRelation

public interface FormalAttribute
    extends Comparable
{

    public abstract String toString();

    public abstract boolean equals(Object obj);

    public abstract String getName();

    public abstract void initExtension(Vector vector);

    public abstract void initExtension(Extent extent, BinaryRelation binaryrelation);

    public abstract TreeSet getExtension();

    public abstract TreeSet intersection(TreeSet treeset);
}
