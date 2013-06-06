// Decompiled by Jad v1.5.7g. Copyright 2000 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/SiliconValley/Bridge/8617/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi 
// Source File Name:   MultiValuedRelation.java

package lattice.util;

import java.util.Iterator;
import java.util.Vector;

// Referenced classes of package lattice.util:
//            AbstractRelation, FormalAttributeValueSet, BadInputDataException, ScalingBinaryRelation, 
//            FormalAttributeValue, DefaultFormalObject, DefaultFormalAttribute, BinaryRelation, 
//            FormalObject, FormalAttribute, AbstractFormalAttributeValue

public class MultiValuedRelation extends AbstractRelation
{

    Vector setOfValues;

    public MultiValuedRelation(int nbObjs, int nbAtts)
    {
        super(nbObjs, nbAtts);
        setOfValues = new Vector(nbAtts, 1);
        for(int i = 0; i < nbAtts; i++)
            setOfValues.add(new FormalAttributeValueSet());

        for(int i = 0; i < nbObjs; i++)
        {
            for(int j = 0; j < nbAtts; j++)
                ((Vector)aPourProp.elementAt(i)).addElement(new FormalAttributeValueSet());

        }

    }

    public void addObject()
    {
        super.addObject();
        for(int i = 0; i < aPourProp.size(); i++)
        {
            for(int j = 0; j < lesAtributs.size(); j++)
                ((Vector)aPourProp.elementAt(i)).addElement(new FormalAttributeValueSet());

        }

    }

    public void addAttribute()
    {
        super.addAttribute();
        for(int i = 0; i < aPourProp.size(); i++)
            ((Vector)aPourProp.elementAt(i)).addElement(new FormalAttributeValueSet());

        setOfValues.add(new FormalAttributeValueSet());
    }

    public void addValue(FormalAttributeValue val, FormalAttribute att)
    {
        if(lesAtributs.contains(att))
            ((FormalAttributeValueSet)setOfValues.elementAt(lesAtributs.indexOf(att))).addValue(val);
    }

    public void addValue(FormalAttributeValue val, int idxAtt)
    {
        if(idxAtt >= 0 && idxAtt < lesAtributs.size())
            ((FormalAttributeValueSet)setOfValues.elementAt(idxAtt)).addValue(val);
    }

    public void removeAttribute(FormalAttribute fa)
    {
        int idxA = lesAtributs.indexOf(fa);
        setOfValues.remove(idxA);
        super.removeAttribute(fa);
    }

    public Vector getValues()
    {
        return (Vector)setOfValues.clone();
    }

    public AbstractFormalAttributeValue getRelation(FormalObject Obj, FormalAttribute Att)
    {
        int idxO = lesObjets.indexOf(Obj);
        int idxA = lesAtributs.indexOf(Att);
        return (FormalAttributeValueSet)((Vector)aPourProp.elementAt(idxO)).elementAt(idxA);
    }

    public void setRelation(FormalObject Obj, FormalAttribute Att, AbstractFormalAttributeValue rel)
        throws BadInputDataException
    {
        setRelation(Obj, Att, rel);
    }

    public void addValuedRelation(FormalObject Obj, FormalAttribute Att, FormalAttributeValue rel)
    {
        if(lesObjets.contains(Obj) && lesAtributs.contains(Att))
        {
            int idxO = lesObjets.indexOf(Obj);
            int idxA = lesAtributs.indexOf(Att);
            FormalAttributeValueSet lesAttFormalValues = (FormalAttributeValueSet)setOfValues.elementAt(idxA);
            if(!lesAttFormalValues.contains(rel))
                lesAttFormalValues.addValue(rel);
            ((FormalAttributeValueSet)((Vector)aPourProp.elementAt(idxO)).elementAt(idxA)).addValue(rel);
        }
    }

    public void addValuedRelation(int idxO, int idxA, FormalAttributeValue rel)
    {
        if(idxO < lesObjets.size() && idxA < lesAtributs.size() && idxO >= 0 && idxA >= 0)
        {
            FormalAttributeValueSet lesAttFormalValues = (FormalAttributeValueSet)setOfValues.elementAt(idxA);
            if(!lesAttFormalValues.contains(rel))
                lesAttFormalValues.addValue(rel);
            ((FormalAttributeValueSet)((Vector)aPourProp.elementAt(idxO)).elementAt(idxA)).addValue(rel);
        }
    }

    public void removeValuedRelation(FormalObject Obj, FormalAttribute Att, FormalAttributeValue rel)
    {
        int idxO = lesObjets.indexOf(Obj);
        int idxA = lesAtributs.indexOf(Att);
        ((FormalAttributeValueSet)((Vector)aPourProp.elementAt(idxO)).elementAt(idxA)).removeValue(rel);
    }

    public void setRelation(FormalObject Obj, FormalAttribute Att, FormalAttributeValueSet rel)
        throws BadInputDataException
    {
        int idxO = lesObjets.indexOf(Obj);
        int idxA = lesAtributs.indexOf(Att);
        if(idxO < lesObjets.size() && idxA < lesAtributs.size() && idxO > -1 && idxA > -1)
            ((Vector)aPourProp.elementAt(idxO)).set(idxA, rel);
    }

    public void setRelation(int idxO, int idxA, FormalAttributeValueSet rel)
        throws BadInputDataException
    {
        if(idxO < lesObjets.size() && idxA < lesAtributs.size() && idxO > -1 && idxA > -1)
            ((Vector)aPourProp.elementAt(idxO)).set(idxA, rel);
    }

    public FormalAttributeValueSet getRelation(int idxO, int idxA)
    {
        if(idxO < lesObjets.size() && idxA < lesAtributs.size() && idxO > -1 && idxA > -1)
            return (FormalAttributeValueSet)((Vector)aPourProp.elementAt(idxO)).elementAt(idxA);
        else
            return null;
    }

    public FormalAttributeValueSet getValues(Object Att)
    {
        return (FormalAttributeValueSet)setOfValues.elementAt(lesAtributs.indexOf(Att));
    }

    public BinaryRelation getEmptyScalingBinaryRelation()
    {
        int nbRowCol = 0;
        for(int i = 0; i < setOfValues.size(); i++)
            nbRowCol += ((FormalAttributeValueSet)setOfValues.elementAt(i)).size();

        BinaryRelation binRel = new ScalingBinaryRelation(nbRowCol, nbRowCol);
        binRel.setRelationName("Spec. Scaling Relation : " + getRelationName());
        int numAjout = 0;
        for(int i = 0; i < lesAtributs.size(); i++)
        {
            FormalAttributeValueSet favs = (FormalAttributeValueSet)setOfValues.elementAt(i);
            if(favs.size() != 0)
            {
                for(Iterator it = favs.getIterator(); it.hasNext();)
                {
                    FormalAttributeValue fav = (FormalAttributeValue)it.next();
                    FormalObject obj = new DefaultFormalObject(lesAtributs.elementAt(i).toString() + " = " + fav.toString());
                    FormalAttribute att = new DefaultFormalAttribute(lesAtributs.elementAt(i).toString() + " = " + fav.toString());
                    try
                    {
                        binRel.replaceObject(binRel.getFormalObjects()[numAjout], obj);
                        binRel.replaceAttribute(binRel.getFormalAttributes()[numAjout], att);
                        FormalAttributeValue val = new FormalAttributeValue("X");
                        binRel.setRelation(obj, att, val);
                    }
                    catch(Exception e)
                    {
                        e.printStackTrace();
                    }
                    numAjout++;
                }

            }
        }

        return binRel;
    }

    public BinaryRelation nominaleScale()
    {
        return null;
    }

    public BinaryRelation dichotomicScale()
    {
        return null;
    }

    public Object clone()
    {
        MultiValuedRelation mvr = new MultiValuedRelation(lesObjets.size(), lesAtributs.size());
        mvr.setRelationName(getRelationName());
        mvr.lesObjets = (Vector)lesObjets.clone();
        mvr.lesAtributs = (Vector)lesAtributs.clone();
        mvr.setOfValues = (Vector)setOfValues.clone();
        for(int i = 0; i < lesObjets.size(); i++)
        {
            for(int j = 0; j < lesAtributs.size(); j++)
                try
                {
                    mvr.setRelation((FormalObject)lesObjets.elementAt(i), (FormalAttribute)lesAtributs.elementAt(j), getRelation((FormalObject)lesObjets.elementAt(i), (FormalAttribute)lesAtributs.elementAt(j)));
                }
                catch(Exception exception) { }

        }

        return mvr;
    }

    public void emptyRelation()
    {
        setOfValues.removeAllElements();
        for(int i = 0; i < lesObjets.size(); i++)
        {
            for(int j = 0; j < lesAtributs.size(); j++)
                ((FormalAttributeValueSet)((Vector)aPourProp.elementAt(i)).elementAt(j)).removeAllValues();

        }

    }
}
