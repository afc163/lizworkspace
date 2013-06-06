// Decompiled by Jad v1.5.7g. Copyright 2000 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/SiliconValley/Bridge/8617/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi 
// Source File Name:   GagciRelationalAttribute.java

package lattice.util;


// Referenced classes of package lattice.util:
//            DefaultFormalAttribute, AbstractRelation, Concept, BinaryRelation

public class GagciRelationalAttribute extends DefaultFormalAttribute
{

    private BinaryRelation binRel;
    private Concept theLinkedConcept;

    public GagciRelationalAttribute(String aName)
    {
        super(aName);
    }

    public GagciRelationalAttribute(BinaryRelation abinRel, Concept aConcept)
    {
        super(abinRel.getRelationName() + " = < " + aConcept.getExtent().toString() + " ; " + aConcept.getIntent().toString() + " >");
        binRel = abinRel;
        theLinkedConcept = aConcept;
    }
}
