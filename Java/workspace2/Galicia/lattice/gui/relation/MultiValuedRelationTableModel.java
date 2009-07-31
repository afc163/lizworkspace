// Decompiled by Jad v1.5.7g. Copyright 2000 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/SiliconValley/Bridge/8617/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi 
// Source File Name:   MultiValuedRelationTableModel.java

package lattice.gui.relation;

import javax.swing.event.TableModelEvent;
import javax.swing.table.AbstractTableModel;
import lattice.util.*;

// Referenced classes of package lattice.gui.relation:
//            AbstractRelationTableModel

public class MultiValuedRelationTableModel extends AbstractRelationTableModel
{

    public MultiValuedRelationTableModel(MultiValuedRelation mvr)
    {
        super(mvr);
    }

    public void setValueAt(Object aValue, int rowIdx, int colIdx)
    {
        try
        {
            if(rowIdx == 0 && colIdx == 0)
                absRel.setRelationName((String)aValue);
            if(rowIdx == 0 && colIdx > 0)
                absRel.replaceAttribute((FormalAttribute)getValueAt(rowIdx, colIdx), new DefaultFormalAttribute(aValue.toString()));
            if(rowIdx > 0 && colIdx == 0)
                absRel.replaceObject((FormalObject)getValueAt(rowIdx, colIdx), new DefaultFormalObject(aValue.toString()));
        }
        catch(BadInputDataException bide)
        {
            message = bide.getMessage();
            fireTableChanged(new TableModelEvent(this));
        }
    }

    public FormalAttributeValueSet getValuesOfAttribute(int colIdx)
    {
        return ((MultiValuedRelation)absRel).getValues((FormalAttribute)getValueAt(0, colIdx));
    }

    public void addRelationValueAt(FormalAttributeValue aValue, int rowIdx, int colIdx)
    {
        ((MultiValuedRelation)absRel).addValuedRelation((FormalObject)getValueAt(rowIdx, 0), (FormalAttribute)getValueAt(0, colIdx), aValue);
    }

    public void removeRelationValueAt(FormalAttributeValue aValue, int rowIdx, int colIdx)
    {
        ((MultiValuedRelation)absRel).removeValuedRelation((FormalObject)getValueAt(rowIdx, 0), (FormalAttribute)getValueAt(0, colIdx), aValue);
    }
}
