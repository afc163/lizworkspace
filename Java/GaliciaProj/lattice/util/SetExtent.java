// Decompiled by Jad v1.5.7g. Copyright 2000 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/SiliconValley/Bridge/8617/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi 
// Source File Name:   SetExtent.java

package lattice.util;

import java.util.*;

// Referenced classes of package lattice.util:
//            Extent

public class SetExtent extends TreeSet
    implements Extent
{

    public SetExtent()
    {
    }

    
    //没有看明白
    public Extent union(Extent extent)
    {
        Extent result;
        if(extent.size() > size())
        {
            result = (Extent)extent.clone();
            result.addAll(this);
        } else
        {
            result = (Extent)clone();
            result.addAll(extent);
        }
        return result;
    }

    
    public Extent intersection(Extent extent)
    {
        Extent result = new SetExtent();
        if(!isEmpty() && !extent.isEmpty())
        {
            
            Iterator it2 = extent.iterator();
            
            while(it2.hasNext()) 
            {
                Object c1;
                Object c2 = it2.next();
                Iterator it1 = iterator();
                for(c1 = it1.next(); it2.hasNext() && ((Comparable)c1).compareTo(c2) > 0; c2 = it2.next());
                if(c1.equals(c2))
                    result.add(c1);
                if(!it2.hasNext() && ((Comparable)c1).compareTo(c2) >= 0)
                    break;
            }
        }
        return result;
    }

    public Object clone()
    {
        SetExtent newObj = new SetExtent();
        newObj.addAll(this);
        return newObj;
    }
}
