// Decompiled by Jad v1.5.7g. Copyright 2000 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/SiliconValley/Bridge/8617/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi 
// Source File Name:   AbstractExempleLattice.java

package lattice.gui.graph.Exemple;

import java.util.*;
import lattice.util.Node;

// Referenced classes of package lattice.gui.graph.Exemple:
//            ExempleNode

public abstract class AbstractExempleLattice
{

    private Hashtable nodeSet;

    public AbstractExempleLattice()
    {
        nodeSet = new Hashtable();
    }

    public abstract Node creer();

    public Node creer(Vector v)
    {
        for(int i = 0; i < v.size(); i++)
        {
            String s = (String)v.elementAt(i);
            StringTokenizer st = new StringTokenizer(s, ",");
            String s2 = (String)st.nextElement();
            ExempleNode n = (ExempleNode)nodeSet.get(s2);
            if(n == null)
            {
                n = new ExempleNode(s2);
                nodeSet.put(s2, n);
            }
            while(st.hasMoreTokens()) 
            {
                s2 = (String)st.nextElement();
                ExempleNode n2 = (ExempleNode)nodeSet.get(s2);
                if(n2 == null)
                {
                    n2 = new ExempleNode(s2);
                    nodeSet.put(s2, n2);
                    n.addChild(n2);
                } else
                {
                    n.addChild(n2);
                }
            }
        }

        return (ExempleNode)nodeSet.get("top");
    }
}
