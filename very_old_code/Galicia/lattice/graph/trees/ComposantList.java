// Decompiled by Jad v1.5.7g. Copyright 2000 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/SiliconValley/Bridge/8617/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi 
// Source File Name:   ComposantList.java

package lattice.graph.trees;

import java.util.Vector;

// Referenced classes of package lattice.graph.trees:
//            Composant

public abstract class ComposantList extends Composant
{

    static int num = 0;
    Vector liste;

    public ComposantList()
    {
        num++;
        init();
    }

    public ComposantList(Vector uneListe)
    {
        num++;
        liste = uneListe;
        init();
    }

    protected void init()
    {
        liste = new Vector();
    }

    public void add(Object objet)
    {
        liste.addElement(objet);
    }

    public void remove(Object objet)
    {
        liste.removeElement(objet);
    }

    public int nbElement()
    {
        return liste.size();
    }

}
