// Decompiled by Jad v1.5.7g. Copyright 2000 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/SiliconValley/Bridge/8617/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi 
// Source File Name:   Difference.java

package lattice.rules.util;

import java.util.Iterator;
import java.util.Set;
import lattice.util.*;

public class Difference
{

    public Intent difference;

    public Difference()
    {
        difference = new SetIntent();
    }

    public void calculDifference(Node v1, Node v2)
    {
        difference = ajouteNoeud(v1);
        difference.removeAll(v2.getConcept().getIntent());
    }

    public Intent ajouteNoeud(Node v1)
    {
        Intent resultat = new SetIntent();
        Object item;
        for(Iterator it = v1.getConcept().getIntent().iterator(); it.hasNext(); resultat.add(item))
            item = it.next();

        return resultat;
    }
}
