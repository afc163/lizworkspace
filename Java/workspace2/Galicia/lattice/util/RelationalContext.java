// Decompiled by Jad v1.5.7g. Copyright 2000 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/SiliconValley/Bridge/8617/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi 
// Source File Name:   RelationalContext.java

package lattice.util;

import java.io.File;
import java.util.Vector;

// Referenced classes of package lattice.util:
//            AbstractRelation, BinaryRelation

public class RelationalContext
{

    public static String DEFAULT_NAME = "Default Name";
    private String name;
    private File inputFile;
    private File outputFile;
    private Vector setOfRelations;

    public RelationalContext()
    {
        name = null;
        inputFile = null;
        outputFile = null;
        setOfRelations = null;
        name = DEFAULT_NAME;
        //Vector初始为0，每次增加1个元素
        setOfRelations = new Vector(0, 1);
    }

    public RelationalContext(String leNom)
    {
        name = null;
        inputFile = null;
        outputFile = null;
        setOfRelations = null;
        name = leNom;
        setOfRelations = new Vector(0, 1);
    }

    public void addRelation(AbstractRelation absRel)
    {
        setOfRelations.addElement(absRel);
    }

    public void removeRelation(int idxRel)
    {
        setOfRelations.remove(idxRel);
    }

    public String getName()
    {
        return name;
    }

    public void setName(String nom)
    {
        name = nom;
        if(setOfRelations.size() == 1 && ((AbstractRelation)setOfRelations.elementAt(0)).getRelationName().equals(AbstractRelation.DEFAULT_NAME))
            ((BinaryRelation)setOfRelations.elementAt(0)).setRelationName(nom);
    }

    public int getNumberOfRelation()
    {
        return setOfRelations.size();
    }

    public AbstractRelation getRelation(int i)
    {
        return (AbstractRelation)setOfRelations.elementAt(i);
    }

}
