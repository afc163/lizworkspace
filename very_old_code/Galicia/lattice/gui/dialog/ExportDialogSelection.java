// Decompiled by Jad v1.5.7g. Copyright 2000 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/SiliconValley/Bridge/8617/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi 
// Source File Name:   ExportDialogSelection.java

package lattice.gui.dialog;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.EventObject;
import java.util.Vector;
import javax.swing.*;
import lattice.gui.OpenFileFrame;
import lattice.gui.RelationalContextEditor;
import lattice.util.AbstractRelation;
import lattice.util.RelationalContext;

public class ExportDialogSelection extends JDialog
    implements ActionListener
{

    public static int EXPORT = 1;
    public static int CANCELLED = -1;
    private int Status;
    private JList theList;
    Vector listAll;
    Vector listBin;
    Vector listMvc;
    Vector listLat;
    private JButton cancelButton;
    private JButton exportButton;
    private JComboBox combo;
    private Object data;
    private int typeOfExport;
    private RelationalContextEditor controler;

    public ExportDialogSelection(RelationalContext relCtx, RelationalContextEditor controler)
    {
        Status = 0;
        theList = new JList();
        listAll = new Vector();
        listBin = new Vector();
        listMvc = new Vector();
        listLat = new Vector();
        cancelButton = new JButton("Cancel");
        exportButton = new JButton("Export");
        combo = null;
        data = null;
        typeOfExport = OpenFileFrame.FAMILLY_DATA;
        this.controler = null;
        this.controler = controler;
        for(int i = 0; i < relCtx.getNumberOfRelation(); i++)
        {
            listAll.add(relCtx.getRelation(i));
            if(relCtx.getRelation(i).getClass().getName().endsWith("BinaryRelation"))
                listBin.add(relCtx.getRelation(i));
            if(relCtx.getRelation(i).getClass().getName().endsWith("MultiValuedRelation"))
                listMvc.add(relCtx.getRelation(i));
            if(relCtx.getRelation(i).getLattice() != null)
                listLat.add(relCtx.getRelation(i));
        }

        theList.setListData(listAll);
        theList.setSelectionMode(2);
        JPanel panelGlob = new JPanel(new BorderLayout());
        JPanel panelNorth = new JPanel(new FlowLayout());
        JLabel lab1 = new JLabel("Select type of export :");
        panelNorth.add(lab1);
        combo = new JComboBox();     
        combo.addItem("Relational Contexts Familly");
        combo.addItem("Binary Relation");
        combo.addItem("Multi-Valued Relation");
        combo.addItem("Lattice");
        combo.addActionListener(this);
        panelNorth.add(combo);
        panelGlob.add(panelNorth, "North");
        JPanel panelCenter = new JPanel(new BorderLayout());
        JLabel lab2 = new JLabel("Then select data to export :");
        panelCenter.add(lab2, "North");
        JScrollPane scp = new JScrollPane(theList, 20, 30);
        panelCenter.add(scp, "Center");
        panelGlob.add(panelCenter, "Center");
        JPanel panelSud = new JPanel(new FlowLayout());
        panelSud.add(exportButton);
        panelSud.add(cancelButton);
        exportButton.addActionListener(this);
        cancelButton.addActionListener(this);
        panelGlob.add(panelSud, "South");
        setContentPane(panelGlob);
        setTitle("Data Export Selection");
        setSize(400, 400);
        setResizable(false);
        setModal(true);
    }

    public void actionPerformed(ActionEvent arg0)
    {
        if(arg0.getSource() == combo)
        {
            switch(combo.getSelectedIndex())
            {
            case 0: // '\0'
                theList.setListData(listAll);
                theList.setSelectionMode(2);
                typeOfExport = OpenFileFrame.FAMILLY_DATA;
                break;

            case 1: // '\001'
                theList.setListData(listBin);
                theList.setSelectionMode(0);
                typeOfExport = OpenFileFrame.BINARY_DATA;
                break;

            case 2: // '\002'
                theList.setListData(listMvc);
                theList.setSelectionMode(0);
                typeOfExport = OpenFileFrame.MULTI_VALUE_DATA;
                break;

            case 3: // '\003'
                theList.setListData(listLat);
                theList.setSelectionMode(0);
                typeOfExport = OpenFileFrame.LATTICE_DATA;
                break;
            }
            theList.repaint();
        }
        if(arg0.getSource() == exportButton)
        {
            if(getTypeOfExport() == OpenFileFrame.FAMILLY_DATA)
            {
                RelationalContext rc = new RelationalContext();
                for(int i = 0; i < theList.getSelectedValues().length; i++)
                    rc.addRelation((AbstractRelation)theList.getSelectedValues()[i]);

                data = rc;
            } else
            if(getTypeOfExport() == OpenFileFrame.LATTICE_DATA)
                data = ((AbstractRelation)theList.getSelectedValue()).getLattice();
            else
                data = theList.getSelectedValue();
            Status = EXPORT;
            dispose();
        }
        if(arg0.getSource() == cancelButton)
        {
            Status = CANCELLED;
            dispose();
        }
    }

    public void askParameter()
    {
        if(controler != null)
            controler.setEnabled(false);
        show();
        if(controler != null)
            controler.setEnabled(true);
    }

    public int getTypeOfExport()
    {
        return typeOfExport;
    }

    public Object getData()
    {
        return data;
    }

    public int getAction()
    {
        return Status;
    }

}
