// Decompiled by Jad v1.5.7g. Copyright 2000 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/SiliconValley/Bridge/8617/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi 
// Source File Name:   Node.java

package lattice.util;


import java.util.List;
import java.util.Set;
import java.util.Vector; //by cjj 2005.1.10

// Referenced classes of package lattice.util:
//            Concept

public interface Node
{

    public abstract Concept getConcept();

    public abstract Integer getId();

    public abstract List getChildren();

    public abstract void addChild(Node node);

    public abstract void removeChild(Node node);

    public abstract Set getParents();

    public abstract void addParent(Node node);

    public abstract void removeParent(Node node);

    public abstract String toString();

    public abstract boolean getVisited();

    public abstract void setVisited(boolean flag);

    public abstract boolean equals(Object obj);

    public abstract void setDegre(int i);

    public abstract int getDegre();

    public abstract void resetDegre();

}
