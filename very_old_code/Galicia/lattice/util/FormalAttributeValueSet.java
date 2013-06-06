// Decompiled by Jad v1.5.7g. Copyright 2000 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/SiliconValley/Bridge/8617/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi 
// Source File Name:   FormalAttributeValueSet.java

package lattice.util;

import java.util.*;

// Referenced classes of package lattice.util:
//            AbstractFormalAttributeValue, FormalAttributeValue

public class FormalAttributeValueSet extends AbstractFormalAttributeValue
{

    private TreeSet theSet;

    public FormalAttributeValueSet(Collection c)
    {
        theSet = new TreeSet(c);
    }

    public FormalAttributeValueSet()
    {
        theSet = new TreeSet();
    }

    public void addValue(FormalAttributeValue fav)
    {
        theSet.add(fav);
    }

    public void removeValue(FormalAttributeValue fav)
    {
        theSet.remove(fav);
    }

    public int size()
    {
        return theSet.size();
    }

    public boolean equals(Object o)
    {
        return theSet.equals(((FormalAttributeValueSet)o).theSet);
    }

    public String toString()
    {
        if(size() == 0)
            return "0";
        else
            return theSet.toString();
    }

    public int compareTo(Object o)
    {
        Iterator it1 = theSet.iterator();
        Iterator it2 = ((FormalAttributeValueSet)o).getIterator();
        int comp;
        FormalAttributeValue aVal1;
        FormalAttributeValue aVal2;
        for(comp = 0; it1.hasNext() && it2.hasNext() && comp == 0; comp = aVal1.compareTo(aVal2))
        {
            aVal1 = (FormalAttributeValue)it1.next();
            aVal2 = (FormalAttributeValue)it2.next();
        }

        if(!it1.hasNext() && it2.hasNext())
            comp = -1;
        if(it1.hasNext() && !it2.hasNext())
            comp = 1;
        return comp;
    }

    public Iterator getIterator()
    {
        return theSet.iterator();
    }

    public boolean contains(Object o)
    {
        return theSet.contains(o);
    }

    public Object clone()
    {
        FormalAttributeValueSet theClone = new FormalAttributeValueSet();
        theClone.theSet = (TreeSet)theSet.clone();
        return theClone;
    }

    public void removeAllValues()
    {
        theSet.clear();
    }
}
