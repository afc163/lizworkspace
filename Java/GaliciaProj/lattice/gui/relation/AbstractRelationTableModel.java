// Decompiled by Jad v1.5.7g. Copyright 2000 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/SiliconValley/Bridge/8617/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi 
// Source File Name:   AbstractRelationTableModel.java

package lattice.gui.relation;

import javax.swing.table.AbstractTableModel;
import lattice.util.*;

public abstract class AbstractRelationTableModel extends AbstractTableModel
{

    protected AbstractRelation absRel;
    protected String message;

    public AbstractRelationTableModel(AbstractRelation as)
    {
    	System.out.println("我调用到了AbstractRelationTableModel");
        message = "Ouverture";
        absRel = as;
    }

    public int getRowCount()
    {
        return absRel.getObjectsNumber() + 1;
    }

    public int getColumnCount()
    {
        return absRel.getAttributesNumber() + 1;
    }

    public String getMessage()
    {
        return message;
    }

    public void ajouteRow()
    {
        absRel.addObject();
    }

    public void ajouteCol()
    {
        absRel.addAttribute();
    }

    public Object getValueAt(int rowIdx, int colIdx)
    {
        if(rowIdx == 0 && colIdx == 0)
            return absRel.getRelationName();
        if(rowIdx == 0 && colIdx > 0)
            return absRel.getFormalAttributes()[colIdx - 1];
        
        //返回选中的第几个对象
        if(rowIdx > 0 && colIdx == 0)
            return absRel.getFormalObjects()[rowIdx - 1];
        else  //这句话没有必要
            return absRel.getRelation((FormalObject)getValueAt(rowIdx, 0), (FormalAttribute)getValueAt(0, colIdx));
    }

    public boolean isCellEditable(int rowIdx, int colIdx)
    {
        return false;
    }
}
