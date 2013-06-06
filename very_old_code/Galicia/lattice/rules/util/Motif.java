// Decompiled by Jad v1.5.7g. Copyright 2000 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/SiliconValley/Bridge/8617/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi 
// Source File Name:   Motif.java

package lattice.rules.util;

import java.util.*;
import lattice.util.FormalAttribute;

public class Motif
{

    protected TreeSet items;
    protected TreeSet extension;

    public Motif()
    {
        items = new TreeSet();
    }

    public Motif(FormalAttribute f)
    {
        this();
        items.add(f);
    }

    public Motif(Vector items)
    {
        this.items = new TreeSet(items);
    }

    public Motif(TreeSet items)
    {
        this.items = items;
    }

    public TreeSet getItems()
    {
        return items;
    }

    public void setItems(Vector v)
    {
        items = new TreeSet(v);
    }

    public void addItem(FormalAttribute f)
    {
        items.add(f);
    }

    public void addItems(Vector v)
    {
        items.addAll(v);
    }

    public FormalAttribute first()
    {
        return (FormalAttribute)items.first();
    }

    public void addItems(TreeSet t)
    {
        items.addAll(t);
    }

    public void addItems(Motif m)
    {
        items.addAll(m.getItems());
    }

    public TreeSet getExtension()
    {
        return extension;
    }

    public void setExtension(TreeSet t)
    {
        extension = t;
    }

    public int getSupport()
    {
        return extension.size();
    }

    public String toString()
    {
        return new String(items + "\t" + getExtension() + "\n");
    }

    public boolean contains(Object o)
    {
        return items.contains(o);
    }

    public boolean containsAll(Motif f)
    {
        for(Iterator it = f.getItems().iterator(); it.hasNext();)
            if(!items.contains(it.next()))
                return false;

        return true;
    }

    public boolean containsAll(SortedSet f)
    {
        for(Iterator it = f.iterator(); it.hasNext();)
            if(!items.contains(it.next()))
                return false;

        return true;
    }

    public Object clone()
    {
        Motif m = new Motif(new TreeSet(items));
        m.setExtension(new TreeSet(getExtension()));
        return m;
    }

    public boolean equals(Object o)
    {
        return getItems().equals(((Motif)o).getItems());
    }

    public boolean addMotif(Motif m)
    {
        boolean b = items.addAll(m.getItems());
        if(b)
            extension = intersection(m.getExtension());
        return b;
    }

    public void initExtension()
    {
        TreeSet t = null;
        FormalAttribute f = null;
        for(Iterator i = items.iterator(); i.hasNext();)
        {
            f = (FormalAttribute)i.next();
            if(t == null)
                t = new TreeSet(f.getExtension());
            else
                t = f.intersection(t);
        }

        setExtension(t);
    }

    protected TreeSet intersection(TreeSet m)
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

    public Motif union(Motif m)
    {
        if(isEmpty())
        {
            return (Motif)m.clone();
        } else
        {
            Motif union = (Motif)clone();
            union.addMotif(m);
            return union;
        }
    }

    public boolean isEmpty()
    {
        return getItems().size() == 0;
    }

    public Motif fermeture(Vector FI1)
    {
        Motif result = (Motif)clone();
        for(int i = 0; i < FI1.size(); i++)
        {
            Motif m1 = (Motif)FI1.get(i);
            if(!contains(m1.first()))
            {
                Motif m2 = (Motif)clone();
                m2.addMotif(m1);
                if(m2.getSupport() == getSupport())
                    result.addMotif(m1);
            }
        }

        return result;
    }

    public int size()
    {
        return items.size();
    }

    public TreeSet difference(Motif m)
    {
        TreeSet diff = (TreeSet)getItems().clone();
        diff.removeAll(m.getItems());
        return diff;
    }
}
