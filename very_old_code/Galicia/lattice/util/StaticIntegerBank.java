// Decompiled by Jad v1.5.7g. Copyright 2000 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/SiliconValley/Bridge/8617/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi 
// Source File Name:   StaticIntegerBank.java

package lattice.util;

import java.util.Vector;

public class StaticIntegerBank
{

    private static Vector integerBank = new Vector();

    public StaticIntegerBank()
    {
    }

    public static void creerReferences(int referenceMax)
    {
        for(int i = 0; i < referenceMax; i++)
        {
            Integer referenceEntier = new Integer(i);
            integerBank.add(referenceEntier);
        }

    }

    public static Integer getInteger(int value)
    {
        return (Integer)integerBank.elementAt(value);
    }

}
