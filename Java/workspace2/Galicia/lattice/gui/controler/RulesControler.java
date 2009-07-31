// Decompiled by Jad v1.5.7g. Copyright 2000 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/SiliconValley/Bridge/8617/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi 
// Source File Name:   RulesControler.java

package lattice.gui.controler;

import java.awt.event.ActionEvent;
import java.util.EventObject;
import java.util.Vector;
import javax.swing.*;
import lattice.gui.RelationalContextEditor;
import lattice.gui.tooltask.AbstractTask;
import lattice.rules.task.*;
import lattice.util.*;

// Referenced classes of package lattice.gui.controler:
//            AbstractControler

public class RulesControler extends AbstractControler
{

    JMenu menuRules;
    JMenuItem luxemburgerAlgo;
    JMenuItem diattaAlgo;
    JMenuItem genBaseAlgo;
    JMenuItem pasquierAlgo;
    JMenuItem duquenneAlgo;
    private ruleBaseInformativeTask taskBaseInformative;
    private rulePasquierTask taskPasquier;
    private ruleLuxembourgTask taskLuxembourg;
    private ruleDiattaTask taskDiatta;

    public RulesControler(RelationalContextEditor rce)
    {
        super(rce);
        taskBaseInformative = null;
        taskPasquier = null;
        taskLuxembourg = null;
        taskDiatta = null;
        taskBaseInformative = new ruleBaseInformativeTask(rce);
        taskPasquier = new rulePasquierTask(rce);
        taskLuxembourg = new ruleLuxembourgTask(rce);
        taskDiatta = new ruleDiattaTask(rce);
        initMenuRules();
    }

    private JMenu initMenuRules()
    {
        menuRules = new JMenu("Rules Generation");
        menuRules.setMnemonic('R');
        JMenu menuExactRules = new JMenu("Generate Exacts Rules");
        menuExactRules.setMnemonic('G');
        JMenu menuApproxRules = new JMenu("Approximative Rules");
        menuApproxRules.setMnemonic('A');
        luxemburgerAlgo = new JMenuItem("Luxemburger Algo.");
        luxemburgerAlgo.addActionListener(this);
        luxemburgerAlgo.setAccelerator(KeyStroke.getKeyStroke(112, 2, false));
        menuApproxRules.add(luxemburgerAlgo);
        diattaAlgo = new JMenuItem("Diatta Algo.");
        diattaAlgo.addActionListener(this);
        diattaAlgo.setAccelerator(KeyStroke.getKeyStroke(115, 2, false));
        menuExactRules.add(diattaAlgo);
        genBaseAlgo = new JMenuItem("Informative Base Algo.");
        genBaseAlgo.addActionListener(this);
        genBaseAlgo.setAccelerator(KeyStroke.getKeyStroke(113, 2, false));
        menuApproxRules.add(genBaseAlgo);
        duquenneAlgo = new JMenuItem("Duquenne Guiges Algo.");
        duquenneAlgo.addActionListener(this);
        duquenneAlgo.setAccelerator(KeyStroke.getKeyStroke(114, 2, false));
        menuExactRules.add(duquenneAlgo);
        pasquierAlgo = new JMenuItem("Generic Base Algo.");
        pasquierAlgo.addActionListener(this);
        pasquierAlgo.setAccelerator(KeyStroke.getKeyStroke(116, 2, false));
        menuExactRules.add(pasquierAlgo);
        menuRules.add(menuExactRules);
        menuRules.add(menuApproxRules);
        return menuRules;
    }

    public JMenu getMainMenu()
    {
        return menuRules;
    }

    public void actionPerformed(ActionEvent evt)
    {
        if(evt.getSource() == luxemburgerAlgo)
        {
            rce.setWorkOnRelation((BinaryRelation)rce.getSelectedRelation());
            Vector binRelOnUse = new Vector();
            binRelOnUse.add((BinaryRelation)rce.getSelectedRelation());
            taskLuxembourg.setBinRelOnUse(binRelOnUse);
            taskLuxembourg.exec();
        }
        if(evt.getSource() == diattaAlgo)
        {
            rce.setWorkOnRelation((BinaryRelation)rce.getSelectedRelation());
            Vector binRelOnUse = new Vector();
            binRelOnUse.add((BinaryRelation)rce.getSelectedRelation());
            taskDiatta.setBinRelOnUse(binRelOnUse);
            taskDiatta.exec();
        }
        if(evt.getSource() == genBaseAlgo)
        {
            rce.setWorkOnRelation((BinaryRelation)rce.getSelectedRelation());
            Vector binRelOnUse = new Vector();
            binRelOnUse.add((BinaryRelation)rce.getSelectedRelation());
            taskBaseInformative.setBinRelOnUse(binRelOnUse);
            taskBaseInformative.exec();
        }
        if(evt.getSource() == pasquierAlgo)
        {
            rce.setWorkOnRelation((BinaryRelation)rce.getSelectedRelation());
            Vector binRelOnUse = new Vector();
            binRelOnUse.add((BinaryRelation)rce.getSelectedRelation());
            taskPasquier.setBinRelOnUse(binRelOnUse);
            taskPasquier.exec();
        }
        if(evt.getSource() == duquenneAlgo)
            addMessage("Duquenne-Guiges Algorithm not implemented !\n");
        rce.checkPossibleActions();
    }

    public void checkPossibleActions()
    {
        if(rce.getFamilyContexts().getNumberOfRelation() == 0)
        {
            menuRules.setEnabled(false);
            return;
        }
        lattice.util.AbstractRelation absRel = rce.getSelectedRelation();
        if(absRel instanceof MultiValuedRelation)
        {
            menuRules.setEnabled(false);
            return;
        }
        if(absRel instanceof BinaryRelation)
        {
            menuRules.setEnabled(true);
            if(rce.isOnWork(absRel))
                menuRules.setEnabled(false);
            return;
        }
        if(absRel instanceof ScalingBinaryRelation)
        {
            menuRules.setEnabled(false);
            return;
        }
        if(absRel instanceof InterObjectBinaryRelation)
        {
            menuRules.setEnabled(false);
            return;
        } else
        {
            return;
        }
    }
}
