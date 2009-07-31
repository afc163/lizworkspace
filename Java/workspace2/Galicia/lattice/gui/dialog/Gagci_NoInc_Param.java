// Decompiled by Jad v1.5.7g. Copyright 2000 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/SiliconValley/Bridge/8617/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi 
// Source File Name:   Gagci_NoInc_Param.java

package lattice.gui.dialog;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.*;
import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import lattice.gui.RelationalContextEditor;
import lattice.util.*;

public class Gagci_NoInc_Param extends JDialog
    implements ActionListener, ListSelectionListener
{

    private static int BACK = 0;
    private static int NEXT = 1;
    private static int TERMINATED = 2;
    private static int CANCELLED = -1;
    private static int Status = 0;
    private Vector lesBinRel;
    private Vector lesInterObjectBinRel;
    private static Vector setOfKi = new Vector();
    private static Vector setOfKiRelation = new Vector();
    private RelationalContext relCtx;
    private JButton nextButton;
    private JButton backButton;
    private JButton cancelButton;
    private JButton terminatedButton;
    private JList allBinaryRelations;
    private JList listOfKi1;
    private JList listOfKi2;
    private JList allInterObjectBinaryRelations;
    private JPanel kiSeletionPanel;
    private JPanel kiRelationSeletionPanel;
    private Hashtable graphSelection;
    private static Hashtable lesGraphInit = new Hashtable();
    private JList lesKiFin;
    private Vector lesGraph;
    private JList shgGraph;
    private JPanel initGraphPanel;

    private Gagci_NoInc_Param(RelationalContext relCtx)
    {
        lesBinRel = null;
        lesInterObjectBinRel = null;
        this.relCtx = null;
        nextButton = new JButton("Next >");
        backButton = new JButton("< Back");
        cancelButton = new JButton("Cancel");
        terminatedButton = new JButton("Terminated");
        allBinaryRelations = null;
        listOfKi1 = null;
        listOfKi2 = null;
        allInterObjectBinaryRelations = null;
        kiSeletionPanel = new JPanel(new BorderLayout());
        kiRelationSeletionPanel = new JPanel(new BorderLayout());
        graphSelection = null;
        lesKiFin = null;
        lesGraph = null;
        shgGraph = null;
        initGraphPanel = new JPanel(new BorderLayout());
        this.relCtx = relCtx;
        nextButton.addActionListener(this);
        backButton.addActionListener(this);
        cancelButton.addActionListener(this);
        terminatedButton.addActionListener(this);
        lesBinRel = new Vector();
        for(int i = 0; i < relCtx.getNumberOfRelation(); i++)
            if((relCtx.getRelation(i) instanceof BinaryRelation) && !(relCtx.getRelation(i) instanceof InterObjectBinaryRelation))
                lesBinRel.add(relCtx.getRelation(i));

        allBinaryRelations = new JList(lesBinRel);
        allBinaryRelations.setSelectionMode(2);
        allBinaryRelations.addListSelectionListener(this);
        lesInterObjectBinRel = new Vector();
        for(int i = 0; i < relCtx.getNumberOfRelation(); i++)
            if(relCtx.getRelation(i) instanceof InterObjectBinaryRelation)
                lesInterObjectBinRel.add(relCtx.getRelation(i));

        allInterObjectBinaryRelations = new JList(lesInterObjectBinRel);
        allInterObjectBinaryRelations.setSelectionMode(2);
        allInterObjectBinaryRelations.addListSelectionListener(this);
        initKiSelectionPanel();
        lesGraph = new Vector();
        for(int i = 0; i < relCtx.getNumberOfRelation(); i++)
            if((relCtx.getRelation(i) instanceof BinaryRelation) && relCtx.getRelation(i).getLattice() != null && (relCtx.getRelation(i).getLattice() instanceof ConceptLattice))
                lesGraph.add(relCtx.getRelation(i));

        shgGraph = new JList(lesGraph);
        shgGraph.addListSelectionListener(this);
        shgGraph.setSelectionMode(0);
        setResizable(false);
        setSize(300, 150);
        setContentPane(kiSeletionPanel);
        setTitle("Select Binary Relation ...");
        setVisible(false);
        setModal(true);
    }

    public static Vector getSetOfKi()
    {
        return setOfKi;
    }

    public static Vector getSetOfKiRelation()
    {
        return setOfKiRelation;
    }

    public static Hashtable getLesGraphInit()
    {
        return lesGraphInit;
    }

    private void initKiSelectionPanel()
    {
        kiSeletionPanel = new JPanel(new BorderLayout());
        JScrollPane scp = new JScrollPane(allBinaryRelations, 20, 30);
        JPanel panel = new JPanel(new FlowLayout());
        panel.add(nextButton);
        panel.add(cancelButton);
        kiSeletionPanel.add(scp, "Center");
        kiSeletionPanel.add(panel, "South");
    }

    private void initRelationSelectionPanel()
    {
        listOfKi1 = new JList((Vector)setOfKi.clone());
        listOfKi1.addListSelectionListener(this);
        listOfKi1.setSelectionMode(0);
        listOfKi2 = new JList((Vector)setOfKi.clone());
        listOfKi2.addListSelectionListener(this);
        listOfKi2.setSelectionMode(0);
        setOfKiRelation.removeAllElements();
        for(int i = 0; i < setOfKi.size(); i++)
            setOfKiRelation.add(new Hashtable());

        JScrollPane scp1 = new JScrollPane(listOfKi1, 20, 30);
        JScrollPane scp2 = new JScrollPane(listOfKi2, 20, 30);
        JScrollPane scp3 = new JScrollPane(allInterObjectBinaryRelations, 20, 30);
        JLabel lab1 = new JLabel("Select Ki:");
        JLabel lab2 = new JLabel("Select Kj:");
        JLabel lab3 = new JLabel("Select O(Ki) => O(Kj) Relational Attributes");
        GridBagLayout gridbag = new GridBagLayout();
        kiRelationSeletionPanel = new JPanel(gridbag);
        GridBagConstraints c = new GridBagConstraints();
        c.fill = 1;
        c.gridwidth = 2;
        c.gridheight = 1;
        c.gridx = 0;
        c.gridy = 0;
        c.weighty = 1.0D;
        kiRelationSeletionPanel.add(lab1, c);
        c.gridx = 2;
        c.gridy = 0;
        c.weighty = 1.0D;
        kiRelationSeletionPanel.add(lab2, c);
        c.gridx = 0;
        c.gridy = 1;
        c.weighty = 4D;
        kiRelationSeletionPanel.add(scp1, c);
        c.gridx = 2;
        c.gridy = 1;
        c.weighty = 4D;
        kiRelationSeletionPanel.add(scp2, c);
        c.gridx = 0;
        c.gridy = 2;
        c.weighty = 1.0D;
        c.gridwidth = 4;
        kiRelationSeletionPanel.add(lab3, c);
        c.gridx = 0;
        c.gridy = 3;
        c.weighty = 4D;
        c.gridwidth = 4;
        kiRelationSeletionPanel.add(scp3, c);
        c.weighty = 1.0D;
        c.gridy = 4;
        c.gridwidth = 1;
        kiRelationSeletionPanel.add(backButton, c);
        c.gridx++;
        kiRelationSeletionPanel.add(nextButton, c);
        c.weightx = 1.0D;
        c.gridx++;
        c.gridwidth = 2;
        kiRelationSeletionPanel.add(cancelButton, c);
    }

    private void initGraphPanel()
    {
        graphSelection = new Hashtable();
        lesKiFin = new JList((Vector)setOfKi.clone());
        lesKiFin.addListSelectionListener(this);
        lesKiFin.setSelectionMode(0);
        JScrollPane scp1 = new JScrollPane(lesKiFin, 20, 30);
        JScrollPane scp2 = new JScrollPane(shgGraph, 20, 30);
        JLabel lab1 = new JLabel("Select Ki:");
        JLabel lab2 = new JLabel("Select Graph Init. for Ki");
        GridBagLayout gridbag = new GridBagLayout();
        initGraphPanel = new JPanel(gridbag);
        GridBagConstraints c = new GridBagConstraints();
        c.fill = 1;
        c.gridwidth = 2;
        c.gridheight = 1;
        c.gridx = 0;
        c.gridy = 0;
        c.weighty = 1.0D;
        initGraphPanel.add(lab1, c);
        c.gridx = 2;
        c.gridy = 0;
        c.weighty = 1.0D;
        initGraphPanel.add(lab2, c);
        c.gridx = 0;
        c.gridy = 1;
        c.weighty = 4D;
        initGraphPanel.add(scp1, c);
        c.gridx = 2;
        c.gridy = 1;
        c.weighty = 4D;
        initGraphPanel.add(scp2, c);
        c.weighty = 1.0D;
        c.gridy = 2;
        c.gridx = 0;
        c.gridwidth = 1;
        initGraphPanel.add(backButton, c);
        c.gridx++;
        initGraphPanel.add(cancelButton, c);
        c.weightx = 1.0D;
        c.gridx++;
        c.gridwidth = 2;
        initGraphPanel.add(terminatedButton, c);
    }

    public void valueChanged(ListSelectionEvent arg0)
    {
        if(arg0.getSource() == allBinaryRelations)
        {
            setOfKi.removeAllElements();
            if(allBinaryRelations.getSelectedIndex() != -1)
            {
                for(int i = 0; i < allBinaryRelations.getSelectedValues().length; i++)
                    setOfKi.add(allBinaryRelations.getSelectedValues()[i]);

            }
        }
        if((arg0.getSource() == listOfKi1 || arg0.getSource() == listOfKi2) && listOfKi1.getSelectedIndex() != -1 && listOfKi2.getSelectedIndex() != -1)
        {
            BinaryRelation theRel1 = (BinaryRelation)listOfKi1.getSelectedValue();
            BinaryRelation theRel2 = (BinaryRelation)listOfKi2.getSelectedValue();
            Vector theRels = (Vector)((Hashtable)setOfKiRelation.elementAt(listOfKi1.getSelectedIndex())).get(theRel2.getRelationName());
            allInterObjectBinaryRelations.clearSelection();
            if(theRels != null)
            {
                int selection[] = new int[theRels.size()];
                for(int i = 0; i < theRels.size(); i++)
                    selection[i] = lesInterObjectBinRel.indexOf(theRels.elementAt(i));

                allInterObjectBinaryRelations.setSelectedIndices(selection);
            }
        }
        if(arg0.getSource() == allInterObjectBinaryRelations)
            if(listOfKi1.getSelectedIndex() != -1 && listOfKi2.getSelectedIndex() != -1)
            {
                BinaryRelation theRel1 = (BinaryRelation)listOfKi1.getSelectedValue();
                BinaryRelation theRel2 = (BinaryRelation)listOfKi2.getSelectedValue();
                Vector theRels = new Vector();
                for(int i = 0; i < allInterObjectBinaryRelations.getSelectedValues().length; i++)
                    theRels.add(allInterObjectBinaryRelations.getSelectedValues()[i]);

                ((Hashtable)setOfKiRelation.elementAt(listOfKi1.getSelectedIndex())).put(theRel2.getRelationName(), theRels);
            } else
            if(allInterObjectBinaryRelations.getSelectedIndex() != -1)
                allInterObjectBinaryRelations.clearSelection();
        if(arg0.getSource() == lesKiFin && graphSelection.get(lesKiFin.getSelectedValue().toString()) != null)
        {
            shgGraph.clearSelection();
            shgGraph.setSelectedIndex(((Integer)graphSelection.get(lesKiFin.getSelectedValue().toString())).intValue());
        }
        if(arg0.getSource() == shgGraph)
            if(lesKiFin.getSelectedIndex() == -1)
                shgGraph.clearSelection();
            else
                graphSelection.put(lesKiFin.getSelectedValue().toString(), new Integer(shgGraph.getSelectedIndex()));
    }

    public void actionPerformed(ActionEvent arg0)
    {
        if(arg0.getSource() == nextButton && getContentPane() == kiRelationSeletionPanel && setOfKi.size() > 0)
        {
            Status = NEXT;
            setVisible(false);
            initGraphPanel();
            setSize(300, 250);
            remove(getContentPane());
            setContentPane(initGraphPanel);
            setTitle("Select Graph Init ...");
            show();
        }
        if((arg0.getSource() == nextButton && getContentPane() == kiSeletionPanel || arg0.getSource() == backButton && getContentPane() == initGraphPanel) && setOfKi.size() > 0)
        {
            Status = NEXT;
            setVisible(false);
            initRelationSelectionPanel();
            setSize(300, 400);
            remove(getContentPane());
            setContentPane(kiRelationSeletionPanel);
            setTitle("Select Relational Attributes ...");
            show();
        }
        if(arg0.getSource() == backButton && getContentPane() == kiRelationSeletionPanel)
        {
            Status = BACK;
            setVisible(false);
            initKiSelectionPanel();
            setSize(300, 150);
            allInterObjectBinaryRelations.clearSelection();
            remove(getContentPane());
            setContentPane(kiSeletionPanel);
            setTitle("Select Binary Relation ...");
            show();
        }
        if(arg0.getSource() == terminatedButton)
        {
            String Ki_name;
            for(Enumeration enum = graphSelection.keys(); enum.hasMoreElements(); lesGraphInit.put(Ki_name, ((AbstractRelation)lesGraph.elementAt(((Integer)graphSelection.get(Ki_name)).intValue())).getLattice()))
                Ki_name = (String)enum.nextElement();

            Status = TERMINATED;
            dispose();
        }
        if(arg0.getSource() == cancelButton)
        {
            Status = CANCELLED;
            dispose();
        }
    }

    public static boolean askParameter(RelationalContext relCtx, RelationalContextEditor controler)
    {
        if(controler != null)
            controler.setEnabled(false);
        Gagci_NoInc_Param fen = new Gagci_NoInc_Param(relCtx);
        fen.show();
        if(controler != null)
            controler.setEnabled(true);
        return Status == TERMINATED;
    }

}
