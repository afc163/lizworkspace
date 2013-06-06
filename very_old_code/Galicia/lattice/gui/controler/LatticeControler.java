// Decompiled by Jad v1.5.7g. Copyright 2000 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/SiliconValley/Bridge/8617/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi 
// Source File Name:   LatticeControler.java

package lattice.gui.controler;

import java.awt.event.ActionEvent;
import java.util.Vector;

import javax.swing.JMenu;
import javax.swing.JMenuItem;

import lattice.algorithm.Godin;
import lattice.algorithm.GodinAD;
import lattice.algorithm.GodinDB;
import lattice.algorithm.LatticeAlgorithm;
import lattice.algorithm.NouReynaud;
import lattice.algorithm.ValtchevEtAl;
import lattice.algorithm.task.latticeAlgorithmTask;
import lattice.gui.RelationalContextEditor;
import lattice.iceberg.algorithm.BordatIceberg;
import lattice.util.BinaryRelation;
import lattice.util.InterObjectBinaryRelation;
import lattice.util.MultiValuedRelation;
import lattice.util.ScalingBinaryRelation;

// Referenced classes of package lattice.gui.controler:
//            AbstractControler

public class LatticeControler extends AbstractControler
{

    private JMenu menuTrellit;
    private JMenuItem algoBordat;
    private JMenuItem algoGodinAD;
    private JMenuItem algoGodin;  //by cjj 2005.1.9
    private JMenuItem algoGodinDB; //by cjj 2006.6.14  概念格存放数据库
    private JMenuItem algoNouReynaud;
    private JMenuItem valNmissAlgo;
    private latticeAlgorithmTask theTask;

    public LatticeControler(RelationalContextEditor rce)
    {
        super(rce);
        menuTrellit = null;
        algoBordat = null;
        algoGodinAD = null;
        algoGodin=null;
        algoNouReynaud = null;
        valNmissAlgo = null;
        theTask = null;
        initMenu();
        theTask = new latticeAlgorithmTask(rce);
    }

    public void initMenu()
    {
        menuTrellit = new JMenu("Construct the lattice");
        menuTrellit.setMnemonic('T');
        algoBordat = new JMenuItem("Bordat");
        algoBordat.setMnemonic('B');
        algoBordat.addActionListener(this);
        menuTrellit.add(algoBordat);
        valNmissAlgo = new JMenuItem("Valtchev & al.");
        valNmissAlgo.setMnemonic('V');
        valNmissAlgo.addActionListener(this);
        menuTrellit.add(valNmissAlgo);
        
        //by cjj 2005.1.9
        algoGodin=new JMenuItem("Godin algorithm");
        algoGodin.setMnemonic('G');
        algoGodin.addActionListener(this);
        menuTrellit.add(algoGodin);
        
        //by cjj 2006-6-14
        algoGodinDB=new JMenuItem("Godin algorithm (DB)");
        algoGodinDB.addActionListener(this);
        menuTrellit.add(algoGodinDB);
        
        algoGodinAD = new JMenuItem("GodinAD (unstable)");
        algoGodinAD.setMnemonic('A');
        algoGodinAD.addActionListener(this);
        menuTrellit.add(algoGodinAD);
        algoNouReynaud = new JMenuItem("Nourine Reynaud (unstable)");
        algoNouReynaud.setMnemonic('N');
        algoNouReynaud.addActionListener(this);
        menuTrellit.add(algoNouReynaud);
    }

    public JMenu getMainMenu()
    {
        return menuTrellit;
    }

    public void actionPerformed(ActionEvent arg0)
    {
    	//execAlgorithm方法直接在本文件中
    	if(arg0.getSource()==algoGodin)
    		execAlgorithm(new Godin((BinaryRelation)rce.getSelectedRelation()));
      
    	//by cjj 2006.6.14   处理概念存放数据库问题
    	if(arg0.getSource()==algoGodinDB)
    	{
    		GodinDB gdb=new GodinDB((BinaryRelation)rce.getSelectedRelation());
    		gdb.doAlgorithm();
    	}
    	
    	if(arg0.getSource() == algoGodinAD)
            execAlgorithm(new GodinAD((BinaryRelation)rce.getSelectedRelation()));
        if(arg0.getSource() == algoBordat)
            execAlgorithm(new BordatIceberg((BinaryRelation)rce.getSelectedRelation()));
        if(arg0.getSource() == algoNouReynaud)
            execAlgorithm(new NouReynaud((BinaryRelation)rce.getSelectedRelation()));
        if(arg0.getSource() == valNmissAlgo)
            execAlgorithm(new ValtchevEtAl((BinaryRelation)rce.getSelectedRelation()));
       
        rce.checkPossibleActions();
    }

    protected void execAlgorithm(LatticeAlgorithm algo)
    {
        rce.setWorkOnRelation(algo.getBinaryRelation());
        Vector binRelOnUse = new Vector();
        //binRekIbUse中压入了二元关系对象
        binRelOnUse.add(algo.getBinaryRelation());
        
        //theTask对象可能是封装算法执行过程的
        theTask.setBinRelOnUse(binRelOnUse);
        theTask.setAlgo(algo);
        theTask.exec();
    }

    //by cjj 2006.6.15  执行算法后，不需要显示hasse图
  
    
    
    public void checkPossibleActions()
    {
        if(rce.getFamilyContexts().getNumberOfRelation() == 0)
        {
            menuTrellit.setEnabled(false);
            return;
        }
        lattice.util.AbstractRelation absRel = rce.getSelectedRelation();
        if(absRel instanceof MultiValuedRelation)
        {
            menuTrellit.setEnabled(false);
            return;
        }
        if(absRel instanceof BinaryRelation)
        {
            menuTrellit.setEnabled(true);
            if(rce.isOnWork(absRel))
                menuTrellit.setEnabled(false);
            return;
        }
        if(absRel instanceof ScalingBinaryRelation)
        {
            menuTrellit.setEnabled(false);
            return;
        }
        if(absRel instanceof InterObjectBinaryRelation)
        {
            menuTrellit.setEnabled(false);
            return;
        } else
        {
            return;
        }
    }
}
