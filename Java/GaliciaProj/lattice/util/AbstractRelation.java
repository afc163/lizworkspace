// Decompiled by Jad v1.5.7g. Copyright 2000 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/SiliconValley/Bridge/8617/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi 
// Source File Name:   AbstractRelation.java

package lattice.util;

import java.util.Vector;

// Referenced classes of package lattice.util:
//            DefaultFormalObject, DefaultFormalAttribute, BadInputDataException, FormalObject, 
//            FormalAttribute, ConceptLattice, AbstractFormalAttributeValue

public abstract class AbstractRelation
{

    public static String DEFAULT_NAME = "Default Name";
    protected String nomRelation;
    protected Vector lesObjets;  //对象名称集合
    protected Vector lesAtributs;  //属性名称集合
    protected Vector aPourProp;
    protected ConceptLattice conceptLattice;

    public AbstractRelation(int nbObjs, int nbAtts)
    {
        nomRelation = null;
        lesObjets = null;
        lesAtributs = null;
        aPourProp = null;
        nomRelation = DEFAULT_NAME;
        
        //确定对象的名称
        lesObjets = new Vector(nbObjs, 1);
        for(int i = 0; i < nbObjs; i++)
            lesObjets.addElement(new DefaultFormalObject("obj_" + i));
      
        //确定属性的名称	
        lesAtributs = new Vector(nbAtts, 1);
        for(int i = 0; i < nbAtts; i++)
            lesAtributs.addElement(new DefaultFormalAttribute("att_" + i));

        //构造存储值的二维空间表 （实际存放的二元关系）
        aPourProp = new Vector(nbObjs, 1);
        for(int i = 0; i < nbObjs; i++)
            aPourProp.addElement(new Vector(nbAtts, 1));

    }

    public void addObject()
    {
        String oName = "obj_";
        int i = lesObjets.size();
        FormalObject anObj;
        for(anObj = new DefaultFormalObject(oName + i); lesObjets.contains(anObj); anObj = new DefaultFormalObject(oName + i))
            i++;

        lesObjets.addElement(anObj);
        aPourProp.addElement(new Vector(getAttributesNumber(), 1));
    }

    public void addObject(FormalObject fo)
    {
        if(!lesObjets.contains(fo))
        {
            addObject();
            lesObjets.setElementAt(fo, lesObjets.size() - 1);
        }
    }

    public void removeObject(FormalObject fo)
    {
        int idxO = lesObjets.indexOf(fo);
        lesObjets.remove(idxO);
        aPourProp.remove(idxO);
    }

    public void addAttribute()
    {
        String aName = "att_";
        int i = lesAtributs.size();
        FormalAttribute anAtt;
        for(anAtt = new DefaultFormalAttribute(aName + i); lesAtributs.contains(anAtt); anAtt = new DefaultFormalAttribute(aName + i))
            i++;

        lesAtributs.addElement(anAtt);
    }

    public void addAttribute(FormalAttribute fa)
    {
        if(!lesAtributs.contains(fa))
        {
            addAttribute();
            lesAtributs.setElementAt(fa, lesAtributs.size() - 1);
        }
    }

    public void removeAttribute(FormalAttribute fa)
    {
        int idxA = lesAtributs.indexOf(fa);
        lesAtributs.remove(idxA);
        for(int i = 0; i < lesObjets.size(); i++)
            ((Vector)aPourProp.elementAt(i)).remove(idxA);

    }

    public int getObjectsNumber()
    {
        return lesObjets.size();
    }

    public int getAttributesNumber()
    {
        return lesAtributs.size();
    }

    public String getRelationName()
    {
        return nomRelation;
    }

    public FormalObject getFormalObject(int idxO)
        throws BadInputDataException
    {
        if(idxO < 0 || idxO >= lesObjets.size())
            throw new BadInputDataException("The indice parameter is not valid");
        else
            return (FormalObject)lesObjets.elementAt(idxO);
    }

    public Vector getObjects()
    {
        return (Vector)lesObjets.clone();
    }

    //方法返回的是FormalObject类型的数组
    public FormalObject[] getFormalObjects()
    {
    	//开辟了lesObject.size个FormatObject接口
        FormalObject lesObjs[] = new FormalObject[lesObjets.size()];
        
        //意思不明
        for(int i = 0; i < lesObjets.size(); i++)
            lesObjs[i] = (FormalObject)lesObjets.elementAt(i);

        return lesObjs;
    }

    public FormalAttribute getFormalAttribute(int idxA)
        throws BadInputDataException
    {
        if(idxA < 0 || idxA >= lesAtributs.size())
            throw new BadInputDataException("The indice parameter is not valid");
        else
            return (FormalAttribute)lesAtributs.elementAt(idxA);
    }

    public Vector getAttributes()
    {
        return (Vector)lesAtributs.clone();
    }

    public FormalAttribute[] getFormalAttributes()
    {
        FormalAttribute lesAtts[] = new FormalAttribute[lesAtributs.size()];
        for(int i = 0; i < lesAtributs.size(); i++)
            lesAtts[i] = (FormalAttribute)lesAtributs.elementAt(i);

        return lesAtts;
    }

    public ConceptLattice getLattice()
    {
        return conceptLattice;
    }

    public void replaceAttribute(FormalAttribute prevName, FormalAttribute newName)
        throws BadInputDataException
    {
        if(prevName.equals(newName))
            return;
        int idx1 = lesAtributs.indexOf(prevName);
        int idx2 = lesAtributs.indexOf(newName);
        if(idx1 == -1)
            throw new BadInputDataException("The name to replace is missing");
        if(idx2 > -1 && idx2 != idx1)
        {
            throw new BadInputDataException("The Given name is already existing in the relation");
        } else
        {
            lesAtributs.set(idx1, newName);
            return;
        }
    }

    public void replaceAttribute(int idxA, FormalAttribute newName)
        throws BadInputDataException
    {
        int idx2 = lesAtributs.indexOf(newName);
        if(idxA < 0 || idxA >= lesAtributs.size())
            throw new BadInputDataException("The indice parameter is not valid");
        if(idx2 > -1 && idx2 != idxA)
        {
            throw new BadInputDataException("The Given name is already existing in the relation");
        } else
        {
            lesAtributs.set(idxA, newName);
            return;
        }
    }

    public void replaceObject(FormalObject prevName, FormalObject newName)
        throws BadInputDataException
    {
        if(prevName.equals(newName))
            return;
        int idx1 = lesObjets.indexOf(prevName);
        int idx2 = lesObjets.indexOf(newName);
        if(idx1 == -1)
            throw new BadInputDataException("The name to replace is missing");
        if(idx2 > -1 && idx2 != idx1)
        {
            throw new BadInputDataException("The Given name is already existing in the relation");
        } else
        {
            lesObjets.set(idx1, newName);
            return;
        }
    }

    public void replaceObject(int idxO, FormalObject newName)
        throws BadInputDataException
    {
        int idx2 = lesObjets.indexOf(newName);
        if(idxO < 0 || idxO >= lesObjets.size())
            throw new BadInputDataException("The indice parameter is not valid");
        if(idx2 > -1 && idx2 != idxO)
        {
            throw new BadInputDataException("The Given name is already existing in the relation");
        } else
        {
            lesObjets.set(idxO, newName);
            return;
        }
    }

    public abstract AbstractFormalAttributeValue getRelation(FormalObject formalobject, FormalAttribute formalattribute);

    public abstract void setRelation(FormalObject formalobject, FormalAttribute formalattribute, AbstractFormalAttributeValue abstractformalattributevalue)
        throws BadInputDataException;

    public void setRelationName(String nom)
    {
        nomRelation = nom;
    }

    public void setLattice(ConceptLattice cl)
    {
        conceptLattice = cl;
    }

    public boolean containsFormalAttribute(FormalAttribute fa)
    {
        return lesAtributs.contains(fa);
    }

    public boolean containsFormalObject(FormalObject fo)
    {
        return lesObjets.contains(fo);
    }

    public int indexOfFormalAttribute(FormalAttribute fa)
    {
        return lesAtributs.indexOf(fa);
    }

    public int indexOfFormalObject(FormalObject fo)
    {
        return lesObjets.indexOf(fo);
    }

    public abstract Object clone();

    public String toString()
    {
        return getRelationName();
    }

    public int hashCode()
    {
        return toString().hashCode();
    }

    public abstract void emptyRelation();

}
