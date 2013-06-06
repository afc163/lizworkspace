// Decompiled by Jad v1.5.7g. Copyright 2000 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/SiliconValley/Bridge/8617/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi 
// Source File Name:   FormalAttributeValue.java

package lattice.util;


// Referenced classes of package lattice.util:
//            AbstractFormalAttributeValue

public class FormalAttributeValue extends AbstractFormalAttributeValue
{

    private static String emptyValue = "0";
    private String theValue;

    public FormalAttributeValue()
    {
    	//赋予二维空间初值,用0表示
        theValue = emptyValue;
    }

    public FormalAttributeValue(String aValue)
    {
        theValue = aValue;
    }

    public int size()
    {
        return !theValue.equals(emptyValue) ? 1 : 0;
    }

    public boolean equals(Object o)
    {
        return theValue.equals(((FormalAttributeValue)o).theValue);
    }

    public String toString()
    {
        return theValue.toString();
    }

    public int compareTo(Object o)
    {
        return theValue.compareTo(((FormalAttributeValue)o).theValue);
    }

}
