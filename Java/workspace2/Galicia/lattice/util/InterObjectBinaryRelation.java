// Decompiled by Jad v1.5.7g. Copyright 2000 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/SiliconValley/Bridge/8617/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi 
// Source File Name:   InterObjectBinaryRelation.java

package lattice.util;

import java.util.Vector;

// Referenced classes of package lattice.util:
//            BinaryRelation, AbstractRelation, InterObjectBinaryRelationAttribute, AbstractFormalAttributeValue

public class InterObjectBinaryRelation extends BinaryRelation
{

    public InterObjectBinaryRelation(int nbObjs, int nbAtts)
    {
        super(nbObjs, nbAtts);
    }

    public InterObjectBinaryRelation(AbstractRelation binRel1, AbstractRelation binRel2)
    {
        super(binRel1.getObjectsNumber(), binRel2.getObjectsNumber());
        for(int i = 0; i < lesObjets.size(); i++)
            try
            {
                lesObjets.set(i, binRel1.getFormalObject(i));
            }
            catch(Exception e)
            {
                e.printStackTrace();
            }

        for(int i = 0; i < lesAtributs.size(); i++)
            try
            {
                lesAtributs.set(i, new InterObjectBinaryRelationAttribute(binRel2.getFormalObject(i)));
            }
            catch(Exception e)
            {
                e.printStackTrace();
            }

        setRelationName("Inter Object Relation : " + binRel1.getRelationName() + " / " + binRel2.getRelationName());
    }

    public Object clone()
    {
        InterObjectBinaryRelation binRel = new InterObjectBinaryRelation(lesObjets.size(), lesAtributs.size());
        binRel.setRelationName(getRelationName());
        binRel.lesObjets = (Vector)lesObjets.clone();
        binRel.lesAtributs = (Vector)lesAtributs.clone();
        for(int i = 0; i < lesObjets.size(); i++)
        {
            for(int j = 0; j < lesAtributs.size(); j++)
                binRel.setRelation(i, j, !getRelation(i, j).isEmpty());

        }

        return binRel;
    }
}
