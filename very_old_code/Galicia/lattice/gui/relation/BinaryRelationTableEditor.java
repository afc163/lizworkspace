// Decompiled by Jad v1.5.7g. Copyright 2000 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/SiliconValley/Bridge/8617/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi 
// Source File Name:   BinaryRelationTableEditor.java

package lattice.gui.relation;

import java.awt.Component;
import javax.swing.JTable;
import lattice.util.AbstractRelation;
import lattice.util.BinaryRelation;

// Referenced classes of package lattice.gui.relation:
//            AbstractRelationTableEditor, BinaryRelationTableModel

public class BinaryRelationTableEditor extends AbstractRelationTableEditor
{

    public BinaryRelationTableEditor(BinaryRelation binRel)
    {
        super(new BinaryRelationTableModel(binRel));
    }

    public void setModelFromRelation(AbstractRelation absRel)
    {
        setModel(new BinaryRelationTableModel((BinaryRelation)absRel));
    }

    //该方法为AbstractRelationTableEditor中的抽象方法
    public void queryNewInputRelationValue(int rowIdx, int colIdx)
    {
    	//revertRelation是核心的核心
        ((BinaryRelationTableModel)getModel()).revertRelation(rowIdx, colIdx);
        repaint();
    }
}
