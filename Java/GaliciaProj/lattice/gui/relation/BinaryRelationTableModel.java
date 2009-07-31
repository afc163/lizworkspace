// Decompiled by Jad v1.5.7g. Copyright 2000 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/SiliconValley/Bridge/8617/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi 
// Source File Name:   BinaryRelationTableModel.java

package lattice.gui.relation;

import javax.swing.event.TableModelEvent;
import javax.swing.table.AbstractTableModel;
import lattice.util.*;

// Referenced classes of package lattice.gui.relation:
//            AbstractRelationTableModel

public class BinaryRelationTableModel extends AbstractRelationTableModel
{

    public BinaryRelationTableModel(BinaryRelation br)
    {
        super(br);
    }

    public void setValueAt(Object aValue, int rowIdx, int colIdx)
    {
        try
        {
            if(rowIdx == 0 && colIdx == 0)
                absRel.setRelationName((String)aValue);
            if(rowIdx == 0 && colIdx > 0)
                absRel.replaceAttribute((FormalAttribute)getValueAt(rowIdx, colIdx), new DefaultFormalAttribute((String)aValue));
            if(rowIdx > 0 && colIdx == 0)
                absRel.replaceObject((FormalObject)getValueAt(rowIdx, colIdx), new DefaultFormalObject((String)aValue));
            if(rowIdx > 0 && colIdx > 0)
            {
                FormalAttributeValue aVal = new FormalAttributeValue((String)aValue);
                absRel.setRelation((FormalObject)getValueAt(rowIdx, 0), (FormalAttribute)getValueAt(0, colIdx), aVal);
            }
        }
        catch(BadInputDataException bide)
        {
            message = bide.getMessage();
            fireTableChanged(new TableModelEvent(this));
        }
    }

    public void revertRelation(int rowIdx, int colIdx)
    {
    	//修改二元空间的关系
        if(rowIdx > 0 && colIdx > 0)
            ((BinaryRelation)absRel).revertRelation((FormalObject)getValueAt(rowIdx, 0), (FormalAttribute)getValueAt(0, colIdx));
    }
}
