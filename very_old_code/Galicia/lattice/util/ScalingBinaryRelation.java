// Decompiled by Jad v1.5.7g. Copyright 2000 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/SiliconValley/Bridge/8617/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi 
// Source File Name:   ScalingBinaryRelation.java

package lattice.util;

import java.util.Vector;

// Referenced classes of package lattice.util:
//            BinaryRelation, AbstractRelation, AbstractFormalAttributeValue, FormalObject, 
//            FormalAttribute

public class ScalingBinaryRelation extends BinaryRelation
{

    public ScalingBinaryRelation(int nbObjs, int nbAtts)
    {
        super(nbObjs, nbAtts);
    }

    public void revertRelation(FormalObject fo, FormalAttribute fa)
    {
        if(lesObjets.indexOf(fo) == lesAtributs.indexOf(fa))
        {
            return;
        } else
        {
            super.revertRelation(fo, fa);
            return;
        }
    }

    public Object clone()
    {
        ScalingBinaryRelation binRel = new ScalingBinaryRelation(lesObjets.size(), lesAtributs.size());
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
