// Decompiled by Jad v1.5.7g. Copyright 2000 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/SiliconValley/Bridge/8617/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi 
// Source File Name:   DefaultFormalAttribute.java

package lattice.util;

import java.util.*;

// Referenced classes of package lattice.util:
//            FormalAttribute, FormalAttributeValue, AbstractRelation, Extent, 
//            BinaryRelation

public class DefaultFormalAttribute
    implements FormalAttribute
{

    protected String name;
    protected TreeSet extension;

    public DefaultFormalAttribute(String aName)
    {
        name = aName;
    }

    public String toString()
    {
        return name.toString();
    }

    public boolean equals(Object o)
    {
        return name.equals(((DefaultFormalAttribute)o).name);
    }

    public int compareTo(Object o)
    {
        return name.compareTo(((DefaultFormalAttribute)o).name);
    }

    public String getName()
    {
        return name;
    }

    public void setName(String aName)
    {
        name = aName;
    }

    public int hashCode()
    {
        return toString().hashCode();
    }

    public void initExtension(Vector v)
    {
        extension = new TreeSet();
        for(int i = 0; i < v.size(); i++)
            if(((FormalAttributeValue)v.elementAt(i)).size() == 1)
                extension.add(new Integer(i));

    }

    public void initExtension(Extent E, BinaryRelation bR)
    {
        extension = new TreeSet();
        for(Iterator it = E.iterator(); it.hasNext(); extension.add(new Integer(bR.getObjects().indexOf(it.next()))));
    }

    public int calculSupport()
    {
        return extension.size();
    }

    public TreeSet getExtension()
    {
        return extension;
    }

    public TreeSet intersection(TreeSet m)
    {
        TreeSet t = new TreeSet();
        Integer i = null;
        for(Iterator it = m.iterator(); it.hasNext();)
        {
            i = (Integer)it.next();
            if(extension.contains(i))
                t.add(i);
        }

        return t;
    }
}
