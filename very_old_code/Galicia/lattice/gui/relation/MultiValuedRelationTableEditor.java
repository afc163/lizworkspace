// Decompiled by Jad v1.5.7g. Copyright 2000 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/SiliconValley/Bridge/8617/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi 
// Source File Name:   MultiValuedRelationTableEditor.java

package lattice.gui.relation;

import java.util.Iterator;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import lattice.util.*;

// Referenced classes of package lattice.gui.relation:
//            AbstractRelationTableEditor, MultiValuedRelationTableModel

public class MultiValuedRelationTableEditor extends AbstractRelationTableEditor
{

    public MultiValuedRelationTableEditor(MultiValuedRelation mvRel)
    {
        super(new MultiValuedRelationTableModel(mvRel));
    }

    public void setModelFromRelation(AbstractRelation absRel)
    {
        setModel(new MultiValuedRelationTableModel((MultiValuedRelation)absRel));
    }

    public void queryAddNewValueToRelation(int rowIdx, int colIdx)
    {
        String val = null;
        do
        {
            val = JOptionPane.showInputDialog(this, "Give a New Value for the Relation");
            if(val != null)
                if(val.equals("") || val.indexOf("|") != -1 || val.indexOf("@") != -1 || val.indexOf("=") != -1)
                    JOptionPane.showMessageDialog(this, "The name shouldn't be empty and shouldn't contains any '|', '@', '=' character.");
                else
                    ((MultiValuedRelationTableModel)getModel()).addRelationValueAt(new FormalAttributeValue(val.toString()), rowIdx, colIdx);
        } while(val != null && (val.equals("") || val.indexOf("|") != -1 || val.indexOf("@") != -1 || val.indexOf("=") != -1));
    }

    public void queryAddExistingValueToRelation(int rowIdx, int colIdx)
    {
        FormalAttributeValueSet favs = ((MultiValuedRelationTableModel)getModel()).getValuesOfAttribute(colIdx);
        if(favs.isEmpty())
            return;
        Iterator it = favs.getIterator();
        Object lesVals[] = new Object[favs.size()];
        for(int i = 0; it.hasNext(); i++)
            lesVals[i] = it.next();

        Object val = JOptionPane.showInputDialog(this, "Add Existing Value :", "Choose a value to Add :", 3, null, lesVals, lesVals[0]);
        if(val != null)
            ((MultiValuedRelationTableModel)getModel()).addRelationValueAt((FormalAttributeValue)val, rowIdx, colIdx);
    }

    public void queryRemoveValueToRelation(int rowIdx, int colIdx)
    {
        if(((FormalAttributeValueSet)getValueAt(rowIdx, colIdx)).size() == 0)
            return;
        Iterator it = ((FormalAttributeValueSet)getValueAt(rowIdx, colIdx)).getIterator();
        Object lesVals[] = new Object[((FormalAttributeValueSet)getValueAt(rowIdx, colIdx)).size()];
        for(int i = 0; it.hasNext(); i++)
            lesVals[i] = it.next();

        Object val = JOptionPane.showInputDialog(this, "Remove a Value", "Choose a value to remove :", 3, null, lesVals, lesVals[0]);
        if(val != null)
            ((MultiValuedRelationTableModel)getModel()).removeRelationValueAt((FormalAttributeValue)val, rowIdx, colIdx);
    }

    public void queryNewInputRelationValue(int rowIdx, int colIdx)
    {
        String action1 = "Add a New Value for the Attribute";
        String action2 = "Add an Existing Value of the Attribute";
        String action3 = "Remove a Value ...";
        Object lesVals[] = {
            action1, action2, action3
        };
        String val = (String)JOptionPane.showInputDialog(this, "Change the Value", "Choose a possible action :", 3, null, lesVals, action1);
        if(val != null && !val.equals(""))
        {
            if(val.equals(action1))
                queryAddNewValueToRelation(rowIdx, colIdx);
            if(val.equals(action2))
                queryAddExistingValueToRelation(rowIdx, colIdx);
            if(val.equals(action3))
                queryRemoveValueToRelation(rowIdx, colIdx);
        }
    }
}
