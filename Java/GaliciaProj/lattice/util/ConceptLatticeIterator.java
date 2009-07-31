// Decompiled by Jad v1.5.7g. Copyright 2000 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/SiliconValley/Bridge/8617/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi 
// Source File Name:   ConceptLatticeIterator.java

package lattice.util;

import java.util.*;

public class ConceptLatticeIterator
    implements Iterator
{

    private Iterator it;
    private Iterator iter;

    ConceptLatticeIterator(Vector index_level)
    {
        it = index_level.iterator();
        if(it.hasNext())
            iter = ((Vector)it.next()).iterator();
    }

    public boolean hasNext()
    {
        return it.hasNext() || iter != null && iter.hasNext();
    }

    public Object next()
    {
        if(hasNext())
        {
            if(iter.hasNext())
                return iter.next();
            for(iter = ((Vector)it.next()).iterator(); !iter.hasNext();)
                if(it.hasNext())
                    iter = ((Vector)it.next()).iterator();
                else
                    throw new NoSuchElementException();

            return iter.next();
        } else
        {
            throw new NoSuchElementException();
        }
    }

    public void remove()
    {
        throw new UnsupportedOperationException();
    }
}
