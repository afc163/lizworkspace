// Decompiled by Jad v1.5.7g. Copyright 2000 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/SiliconValley/Bridge/8617/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi 
// Source File Name:   AbstractRelationTableEditor.java

package lattice.gui.relation;

import java.awt.Color;
import java.awt.event.ActionEvent;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.event.TableModelEvent;
import javax.swing.table.*;
import lattice.util.AbstractRelation;

// Referenced classes of package lattice.gui.relation:
//            AbstractRelationTableModel, BinaryRelationTableModel

public abstract class AbstractRelationTableEditor extends JTable
{

    protected Color bGroundCell;

    public AbstractRelationTableEditor(TableModel arg0)
    {
        super(arg0);
        bGroundCell = new Color(200, 200, 220);
        setAutoResizeMode(0);
        setCellSelectionEnabled(true);
        setColumnSelectionAllowed(false);
        setRowSelectionAllowed(false);
    }

    public abstract void setModelFromRelation(AbstractRelation abstractrelation);

    public TableCellRenderer getCellRenderer(int rowId, int colId)
    {
        DefaultTableCellRenderer dtcr = (DefaultTableCellRenderer)super.getCellRenderer(rowId, colId);
        if(rowId == 0 && colId > 0 || rowId > 0 && colId == 0 || rowId > 0 && colId > 0 && !getValueAt(rowId, colId).toString().equals("0"))
            dtcr.setBackground(bGroundCell);
        else
            dtcr.setBackground(Color.white);
        return dtcr;
    }

    public void tableChanged(TableModelEvent tme)
    {
        super.tableChanged(tme);
        if(!((AbstractRelationTableModel)getModel()).getMessage().equals("Ouverture"))
            JOptionPane.showMessageDialog(this, ((BinaryRelationTableModel)getModel()).getMessage());
    }

    public abstract void queryNewInputRelationValue(int i, int j);

    public void actionPerformed(ActionEvent ae)
    {
        int rowIdx = getSelectedRow();
        int colIdx = getSelectedColumn();
    }
}
