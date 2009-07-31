// Decompiled by Jad v1.5.7g. Copyright 2000 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/SiliconValley/Bridge/8617/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi 
// Source File Name:   ExempleNode.java

package lattice.gui.graph.Exemple;

import java.util.*;
import lattice.util.Concept;
import lattice.util.Node;

public class ExempleNode
    implements Node
{

    List children;
    Set parents;
    Integer id;
    String label;
    private int degre;

    public ExempleNode(String label)
    {
        children = new Vector();
        parents = new TreeSet();
        id = null;
        this.label = null;
        degre = -1;
        this.label = label;
    }

    public ExempleNode(Integer id)
    {
        children = new Vector();
        parents = new TreeSet();
        this.id = null;
        label = null;
        degre = -1;
        this.id = id;
    }

    public ExempleNode(int id)
    {
        children = new Vector();
        parents = new TreeSet();
        this.id = null;
        label = null;
        degre = -1;
        this.id = new Integer(id);
    }

    public Concept getConcept()
    {
        return null;
    }

    public void removeParent(Node node)
    {
    }

    public void removeChild(Node node)
    {
    }

    public Integer getId()
    {
        return id;
    }

    public List getChildren()
    {
        return children;
    }

    public Set getParents()
    {
        return parents;
    }

    public String toString()
    {
        if(getId() != null)
            return getId().toString();
        else
            return label;
    }

    public boolean getVisited()
    {
        return false;
    }

    public void setVisited(boolean flag)
    {
    }

    public void addChild(Node n)
    {
        children.add(n);
    }

    public void addParent(Node n)
    {
        parents.add(n);
    }

    public void setDegre(int d)
    {
        degre = d;
    }

    public int getDegre()
    {
        return degre;
    }

    public void resetDegre()
    {
        degre = -1;
        for(Iterator it = getChildren().iterator(); it.hasNext(); ((Node)it.next()).resetDegre());
    }
}
