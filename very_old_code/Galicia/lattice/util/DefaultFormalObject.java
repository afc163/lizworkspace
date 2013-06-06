// Decompiled by Jad v1.5.7g. Copyright 2000 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/SiliconValley/Bridge/8617/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi 
// Source File Name:   DefaultFormalObject.java

package lattice.util;


// Referenced classes of package lattice.util:
//            FormalObject(½Ó¿Ú)

public class DefaultFormalObject
    implements FormalObject
{

    protected String name;

    public DefaultFormalObject(String aName)
    {
        name = aName;
    }

    public String toString()
    {
        return name;
    }

    public boolean equals(Object o)
    {
        return name.equals(((DefaultFormalObject)o).name);
    }

    public int compareTo(Object o)
    {
        return name.compareTo(((DefaultFormalObject)o).name);
    }

    public void setName(String aName)
    {
        name = aName;
    }

    public String getName()
    {
        return name;
    }

    public int hashCode()
    {
        return toString().hashCode();
    }
}
