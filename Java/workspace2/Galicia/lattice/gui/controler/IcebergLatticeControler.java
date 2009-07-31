// Decompiled by Jad v1.5.7g. Copyright 2000 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/SiliconValley/Bridge/8617/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi 
// Source File Name:   IcebergLatticeControler.java

package lattice.gui.controler;

import java.awt.event.ActionEvent;
import java.util.EventObject;
import java.util.Vector;
import javax.swing.*;
import lattice.algorithm.LatticeAlgorithm;
import lattice.algorithm.task.latticeAlgorithmTask;
import lattice.gui.RelationalContextEditor;
import lattice.gui.tooltask.AbstractTask;
import lattice.iceberg.algorithm.BordatIceberg;
import lattice.util.*;

// Referenced classes of package lattice.gui.controler:
//            AbstractControler

public class IcebergLatticeControler extends AbstractControler
{

    private JMenu menuIceberg;
    private JMenuItem algoMagalice;
    private JMenuItem algoBordatIceberg;
    private latticeAlgorithmTask theTask;

    public IcebergLatticeControler(RelationalContextEditor rce)
    {
        super(rce);
        menuIceberg = null;
        algoMagalice = null;
        algoBordatIceberg = null;
        theTask = null;
        initMenu();
        theTask = new latticeAlgorithmTask(rce);
    }

    public void initMenu()
    {
        menuIceberg = new JMenu("Iceberg Lattice");
        menuIceberg.setMnemonic('I');
        algoBordatIceberg = new JMenuItem("Bordat Iceberg");
        algoBordatIceberg.setMnemonic('B');
        algoBordatIceberg.addActionListener(this);
        menuIceberg.add(algoBordatIceberg);
        algoMagalice = new JMenuItem("Magalice");
        algoMagalice.setMnemonic('M');
        algoMagalice.addActionListener(this);
        menuIceberg.add(algoMagalice);
    }

    public JMenu getMainMenu()
    {
        return menuIceberg;
    }

    public void actionPerformed(ActionEvent arg0)
    {
        if(arg0.getSource() == algoMagalice)
            addMessage("Magalice Algorithm not implemented !\n");
        if(arg0.getSource() == algoBordatIceberg)
        {
            String numString = null;
            double minsup = -1D;
            do
            {
                numString = JOptionPane.showInputDialog(rce, "Give a minimum support number");
                if(numString != null)
                {
                    if(!numString.equals(""))
                        try
                        {
                            minsup = Double.parseDouble(numString);
                        }
                        catch(NumberFormatException nfe)
                        {
                            minsup = -1D;
                        }
                    if(numString.equals("") || minsup < 0.0D || minsup > 1.0D)
                        JOptionPane.showMessageDialog(rce, "The input should be : 0 <= minsup <= 1");
                }
            } while(numString != null && (numString.equals("") || minsup < 0.0D || minsup > 1.0D));
            if(numString == null)
            {
                addMessage("Algorithm \"BordatIceberg\" canceled !\n");
            } else
            {
                LatticeAlgorithm algo = new BordatIceberg((BinaryRelation)rce.getSelectedRelation(), minsup);
                execAlgorithm(algo);
            }
        }
        rce.checkPossibleActions();
    }

    protected void execAlgorithm(LatticeAlgorithm algo)
    {
        rce.setWorkOnRelation(algo.getBinaryRelation());
        Vector binRelOnUse = new Vector();
        binRelOnUse.add(algo.getBinaryRelation());
        theTask.setBinRelOnUse(binRelOnUse);
        theTask.setAlgo(algo);
        theTask.exec();
    }

    public void checkPossibleActions()
    {
        if(rce.getFamilyContexts().getNumberOfRelation() == 0)
        {
            menuIceberg.setEnabled(false);
            return;
        }
        lattice.util.AbstractRelation absRel = rce.getSelectedRelation();
        if(absRel instanceof MultiValuedRelation)
        {
            menuIceberg.setEnabled(false);
            return;
        }
        if(absRel instanceof BinaryRelation)
        {
            menuIceberg.setEnabled(true);
            if(rce.isOnWork(absRel))
                menuIceberg.setEnabled(false);
            return;
        }
        if(absRel instanceof ScalingBinaryRelation)
        {
            menuIceberg.setEnabled(false);
            return;
        }
        if(absRel instanceof InterObjectBinaryRelation)
        {
            menuIceberg.setEnabled(false);
            return;
        } else
        {
            return;
        }
    }
}
