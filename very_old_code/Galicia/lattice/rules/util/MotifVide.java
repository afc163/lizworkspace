// Decompiled by Jad v1.5.7g. Copyright 2000 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/SiliconValley/Bridge/8617/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi 
// Source File Name:   MotifVide.java

package lattice.rules.util;

import java.util.TreeSet;

// Referenced classes of package lattice.rules.util:
//            Motif

public class MotifVide extends Motif
{

    public double nbObj;

    public MotifVide(double nbObj)
    {
        items = new TreeSet();
        this.nbObj = nbObj;
    }

    public boolean isEmpty()
    {
        return true;
    }

    public int size()
    {
        return 0;
    }

    public int getSupport()
    {
        return (int)nbObj;
    }
}
