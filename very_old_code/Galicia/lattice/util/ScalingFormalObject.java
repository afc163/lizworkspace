// Decompiled by Jad v1.5.7g. Copyright 2000 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/SiliconValley/Bridge/8617/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi 
// Source File Name:   ScalingFormalObject.java

package lattice.util;


// Referenced classes of package lattice.util:
//            DefaultFormalObject, FormalAttribute, FormalAttributeValue

public class ScalingFormalObject extends DefaultFormalObject
{

    private FormalAttribute att;
    private FormalAttributeValue val;

    public ScalingFormalObject(FormalAttribute fa, FormalAttributeValue fav)
    {
        super(fa.getName() + " = " + fav.toString());
        att = fa;
        val = fav;
    }

    public void setName(String s)
    {
    }

    public FormalAttribute getAttribute()
    {
        return att;
    }

    public FormalAttributeValue getValue()
    {
        return val;
    }
}
