// Decompiled by Jad v1.5.7g. Copyright 2000 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/SiliconValley/Bridge/8617/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi 
// Source File Name:   InterObjectBinaryRelationAttribute.java

package lattice.util;


// Referenced classes of package lattice.util:
//            DefaultFormalAttribute, FormalObject

public class InterObjectBinaryRelationAttribute extends DefaultFormalAttribute
{

    private FormalObject Obj;

    public InterObjectBinaryRelationAttribute(FormalObject fo)
    {
        super(fo.getName());
        Obj = fo;
    }

    public void setName(String s)
    {
    }

    public FormalObject getObject()
    {
        return Obj;
    }
}
