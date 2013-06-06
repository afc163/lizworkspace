// Decompiled by Jad v1.5.7g. Copyright 2000 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/SiliconValley/Bridge/8617/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi 
// Source File Name:   RelationalContextEditor.java

package lattice.gui;


import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowEvent;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.util.Hashtable;
import java.util.StringTokenizer;
import java.util.Vector;
import handledata.connectDB;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.KeyStroke;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import lattice.algorithm.LatticeAlgorithm;
import lattice.algorithm.task.latticeAlgorithmTask;
import lattice.gui.controler.IcebergLatticeControler;
import lattice.gui.controler.LatticeControler;
import lattice.gui.controler.RulesControler;
import lattice.gui.controler.ShgControler;
import lattice.gui.dialog.ExportDialogSelection;
import lattice.gui.graph.LatticeGraphFrame;
import lattice.gui.relation.AbstractRelationTableEditor;
import lattice.gui.relation.BinaryRelationTableEditor;
import lattice.gui.relation.MultiValuedRelationTableEditor;
import lattice.gui.tooltask.AbstractTask;
import lattice.io.ConsoleWriter;
import lattice.io.task.ReadingTask;
import lattice.io.task.WritingTask;
import lattice.rules.task.ruleAbstractTask;
import lattice.shg.algorithm.Gagci_NoInc;
import lattice.util.AbstractRelation;
import lattice.util.BinaryRelation;
import lattice.util.ConceptLattice;
import lattice.util.FormalAttribute;
import lattice.util.FormalObject;
import lattice.util.InterObjectBinaryRelation;
import lattice.util.MultiValuedRelation;
import lattice.util.RelationalContext;
import lattice.util.ScalingBinaryRelation;

// Referenced classes of package lattice.gui:
//            OpenFileFrame, ConsoleFrame, Console

public class RelationalContextEditor extends OpenFileFrame
    implements ActionListener, MouseListener, ChangeListener
{
	private connectDB toDB;
    private RelationalContext relCtx;
    private JMenu menuFile;
    private JMenuItem changeCtxNameItem;
    private JMenuItem importItem;
    private JMenuItem exportItem;
    private JMenu saveAllItem;
    
    //by cjj 2006-6-3
    private JMenuItem addToDB;
    private JMenuItem addToFile;
    
    private JMenuItem closeItem;
    private JMenu menuEdit;
    private JMenuItem showGraphItem;
    private JMenuItem changeRelNameItem;
    private JMenuItem addRelItemB;
    private JMenuItem addRelItemM;
    private JMenuItem deleteRelItem;
    private JMenuItem addObjItem;
    private JMenuItem addAttItem;
    private JMenuItem deleteObjItem;
    private JMenuItem deleteAttItem;
    private JMenuItem razItem;
    private JMenu scaleMenu;
    private JMenuItem nomiScaleItem;
    private JMenuItem dichoScaleItem;
    private JMenuItem specScaleItem;
    JMenu menuAlgo;
    protected JMenuItem savConsItem;
    private JTabbedPane ongletPanel;
    private Vector setOfAbsRelTableEditor;
    Hashtable relationOnWork;
    protected RulesControler ruleC;
    protected LatticeControler latticeC;
    protected IcebergLatticeControler latticeIcebergC;
    protected ShgControler shgC;

    public RelationalContextEditor(RelationalContext rc)
    {
        super(0.80000000000000004D);
        relCtx = null;
        menuFile = null;
        changeCtxNameItem = null;
        importItem = null;
        exportItem = null;
        saveAllItem = null;
        closeItem = null;
        showGraphItem = null;
        changeRelNameItem = null;
        addRelItemB = null;
        addRelItemM = null;
        deleteRelItem = null;
        addObjItem = null;
        addAttItem = null;
        deleteObjItem = null;
        deleteAttItem = null;
        razItem = null;
        ongletPanel = null;
        setOfAbsRelTableEditor = null;
        relationOnWork = new Hashtable();
        relCtx = rc;
        setTitle("Contexts Family Name : " + relCtx.getName());
        setSize(800, 600);
        setLocation(150, 70);
        setOfAbsRelTableEditor = new Vector();
        initComponents();
        toDB=new connectDB();
    }

    private void initComponents()
    {
        readTask = new ReadingTask(this);
        writeTask = new WritingTask(this);
        ruleC = new RulesControler(this);
        latticeC = new LatticeControler(this);
        latticeIcebergC = new IcebergLatticeControler(this);
        shgC = new ShgControler(this);
        initMenuBar();
        initView();
        initPopup();
        checkPossibleActions();
    }

    private void initMenuBar()
    {
        initMenuFile();
        initMenuEdit();
        menuAlgo = new JMenu("Algorithms");
        menuAlgo.setMnemonic('A');
        menuAlgo.add(latticeC.getMainMenu()); //Construct the Lattice
        menuAlgo.add(latticeIcebergC.getMainMenu());
        menuAlgo.add(shgC.getMainMenu());
        maBar = new JMenuBar();
        maBar.add(menuFile);
        maBar.add(menuEdit);
        maBar.add(ruleC.getMainMenu());
        maBar.add(menuAlgo);
        savConsItem = new JMenuItem("Save Console");
        savConsItem.addActionListener(this);
        consoleMenu.addSeparator();
        consoleMenu.add(savConsItem);
        maBar.add(consoleMenu);
        setJMenuBar(maBar);
    }

    private void initMenuFile()
    {
        menuFile = new JMenu("File");
        menuFile.setMnemonic('F');
        changeCtxNameItem = new JMenuItem("Change Contexts Family Name");
        changeCtxNameItem.addActionListener(this);
        changeCtxNameItem.setMnemonic('N');
        changeCtxNameItem.setAccelerator(KeyStroke.getKeyStroke(78, 2, false));
        importItem = new JMenuItem("Import ...");
        importItem.addActionListener(this);
        importItem.setMnemonic('I');
        importItem.setAccelerator(KeyStroke.getKeyStroke(73, 2, false));
        exportItem = new JMenuItem("Export ...");
        exportItem.addActionListener(this);
        exportItem.setMnemonic('E');
        exportItem.setAccelerator(KeyStroke.getKeyStroke(69, 2, false));
        saveAllItem =(JMenu)menuFile.add(new JMenu("Save All ..."));
        //saveAllItem = new JMenuItem("Save All ...");
        saveAllItem.addActionListener(this);
        saveAllItem.setMnemonic('S');
        //saveAllItem.setAccelerator(KeyStroke.getKeyStroke(83, 3, false));
        //by cjj 2006-6-3
        addToDB = new JMenuItem("Add to DB ...");
        addToDB.addActionListener(this);
        saveAllItem.add(addToDB);
      
        addToFile = new JMenuItem("Add to File ...");
        addToFile.addActionListener(this);
        saveAllItem.add(addToFile);
        
        
        closeItem = new JMenuItem("Close the Contexts Family");
        closeItem.addActionListener(this);
        closeItem.setMnemonic('Q');
        closeItem.setAccelerator(KeyStroke.getKeyStroke(81, 2, false));
        menuFile.add(changeCtxNameItem);
        menuFile.addSeparator();
        menuFile.add(importItem);
        menuFile.add(exportItem);
        menuFile.addSeparator();
     //   menuFile.add(saveAllItem);
        menuFile.add(closeItem);
    }

    private void initMenuEdit()
    {
        menuEdit = new JMenu("Edit");
        menuEdit.setMnemonic('E');
        showGraphItem = new JMenuItem("Show Associated Lattice");
        showGraphItem.addActionListener(this);
        showGraphItem.setMnemonic('S');
        changeRelNameItem = new JMenuItem("Change Context Name");
        changeRelNameItem.addActionListener(this);
        changeRelNameItem.setMnemonic('N');
        changeRelNameItem.setAccelerator(KeyStroke.getKeyStroke(78, 3, false));
        addRelItemB = new JMenuItem("Add a new Binary Context");
        addRelItemB.addActionListener(this);
        addRelItemB.setMnemonic('B');
        addRelItemB.setAccelerator(KeyStroke.getKeyStroke(66, 2, false));
        addRelItemM = new JMenuItem("Add a new Multi-Valued Context");
        addRelItemM.addActionListener(this);
        addRelItemM.setMnemonic('M');
        addRelItemM.setAccelerator(KeyStroke.getKeyStroke(77, 2, false));
        deleteRelItem = new JMenuItem("Remove the Selected Context");
        deleteRelItem.addActionListener(this);
        deleteRelItem.setMnemonic('R');
        deleteRelItem.setAccelerator(KeyStroke.getKeyStroke(82, 2, false));
        addObjItem = new JMenuItem("Add an Object to Selected Context");
        addObjItem.addActionListener(this);
        addObjItem.setMnemonic('O');
        addObjItem.setAccelerator(KeyStroke.getKeyStroke(79, 2, false));
        addAttItem = new JMenuItem("Add an Attribut to Selected Context");
        addAttItem.addActionListener(this);
        addAttItem.setMnemonic('A');
        addAttItem.setAccelerator(KeyStroke.getKeyStroke(65, 2, false));
        deleteObjItem = new JMenuItem("Delete an Object to Selected Context");
        deleteObjItem.addActionListener(this);
        deleteObjItem.setMnemonic('O');
        deleteObjItem.setAccelerator(KeyStroke.getKeyStroke(79, 3, false));
        deleteAttItem = new JMenuItem("Delete an Attribute to Selected Context");
        deleteAttItem.addActionListener(this);
        deleteAttItem.setMnemonic('A');
        deleteAttItem.setAccelerator(KeyStroke.getKeyStroke(65, 3, false));
        razItem = new JMenuItem("Reset Relations to Selected Context");
        razItem.addActionListener(this);
        razItem.setMnemonic('Z');
        razItem.setAccelerator(KeyStroke.getKeyStroke(90, 3, false));
        scaleMenu = new JMenu("Scalling");
        nomiScaleItem = new JMenuItem("Nominale Scale");
        nomiScaleItem.addActionListener(this);
        dichoScaleItem = new JMenuItem("Dichotomic Scale");
        dichoScaleItem.addActionListener(this);
        specScaleItem = new JMenuItem("Specify a Scale Relation");
        specScaleItem.addActionListener(this);
        menuEdit.add(showGraphItem);
        menuEdit.addSeparator();
        menuEdit.add(changeRelNameItem);
        menuEdit.addSeparator();
        menuEdit.add(addRelItemB);
        menuEdit.add(addRelItemM);
        menuEdit.add(deleteRelItem);
        menuEdit.addSeparator();
        menuEdit.add(addObjItem);
        menuEdit.add(addAttItem);
        menuEdit.add(deleteObjItem);
        menuEdit.add(deleteAttItem);
        menuEdit.add(razItem);
        menuEdit.addSeparator();
        menuEdit.add(scaleMenu);
        scaleMenu.add(nomiScaleItem);
        scaleMenu.add(dichoScaleItem);
        scaleMenu.add(specScaleItem);
    }

    private void initView()
    {
        ongletPanel = new JTabbedPane(1);
        ongletPanel.addChangeListener(this);
        AbstractRelation absRel = null;
        AbstractRelationTableEditor absRelTE = null;
        lattice.gui.relation.AbstractRelationTableModel absRelTM = null;
        for(int i = 0; i < relCtx.getNumberOfRelation(); i++)
        {
            absRel = relCtx.getRelation(i);
            if(absRel instanceof BinaryRelation)
                showBinaryRelation((BinaryRelation)absRel);
            if(absRel instanceof MultiValuedRelation)
                showMultiValuedRelation((MultiValuedRelation)absRel);
        }

        setTopPanel(ongletPanel);
    }

    private void initPopup()
    {
    }

    //设置按钮状态
    public void checkPossibleActions()
    {
        if(relCtx.getNumberOfRelation() == 0)
        {
            saveAllItem.setEnabled(false);
            showGraphItem.setEnabled(false);
            exportItem.setEnabled(false);
            changeRelNameItem.setEnabled(false);
            addAttItem.setEnabled(false);
            addObjItem.setEnabled(false);
            deleteAttItem.setEnabled(false);
            deleteObjItem.setEnabled(false);
            deleteRelItem.setEnabled(false);
            razItem.setEnabled(false);
            scaleMenu.setEnabled(false);
            menuAlgo.setEnabled(false);
            ruleC.checkPossibleActions();
            latticeC.checkPossibleActions();
            latticeIcebergC.checkPossibleActions();
            shgC.checkPossibleActions();
            return;
        }
        AbstractRelation absRel = relCtx.getRelation(ongletPanel.getSelectedIndex());
        if(absRel instanceof MultiValuedRelation)
        {
            saveAllItem.setEnabled(true);
            showGraphItem.setEnabled(false);
            exportItem.setEnabled(true);
            changeRelNameItem.setEnabled(true);
            addAttItem.setEnabled(true);
            addObjItem.setEnabled(true);
            deleteAttItem.setEnabled(true);
            deleteObjItem.setEnabled(true);
            deleteRelItem.setEnabled(true);
            scaleMenu.setEnabled(true);
            menuAlgo.setEnabled(false);
        }
        if(absRel instanceof BinaryRelation)
        {
            saveAllItem.setEnabled(true);
            showGraphItem.setEnabled(true);
            exportItem.setEnabled(true);
            changeRelNameItem.setEnabled(true);
            addAttItem.setEnabled(true);
            addObjItem.setEnabled(true);
            deleteAttItem.setEnabled(true);
            deleteObjItem.setEnabled(true);
            deleteRelItem.setEnabled(true);
            scaleMenu.setEnabled(false);
            menuAlgo.setEnabled(true);
        }
        if(absRel instanceof ScalingBinaryRelation)
        {
            saveAllItem.setEnabled(true);
            showGraphItem.setEnabled(false);
            exportItem.setEnabled(true);
            changeRelNameItem.setEnabled(true);
            addAttItem.setEnabled(false);
            addObjItem.setEnabled(false);
            deleteAttItem.setEnabled(false);
            deleteObjItem.setEnabled(false);
            deleteRelItem.setEnabled(true);
            scaleMenu.setEnabled(false);
            menuAlgo.setEnabled(false);
        }
        if(absRel instanceof InterObjectBinaryRelation)
        {
            saveAllItem.setEnabled(true);
            showGraphItem.setEnabled(false);
            exportItem.setEnabled(true);
            changeRelNameItem.setEnabled(true);
            addAttItem.setEnabled(false);
            addObjItem.setEnabled(false);
            deleteAttItem.setEnabled(false);
            deleteObjItem.setEnabled(false);
            deleteRelItem.setEnabled(true);
            scaleMenu.setEnabled(false);
            menuAlgo.setEnabled(false);
        }
        ruleC.checkPossibleActions();
        latticeC.checkPossibleActions();
        latticeIcebergC.checkPossibleActions();
        shgC.checkPossibleActions();
    }

    public void actionPerformed(ActionEvent ae)
    {
        super.actionPerformed(ae);
        String notAvailableStr = new String("Sorry, this algorithm is not available in this version.\n");
        if(ae.getSource() == changeCtxNameItem)
            renameRelationalContext();
        if(ae.getSource() == importItem)
            importData();
        if(ae.getSource() == exportItem)
            exportData();
        
        //by cjj 2006-6-9
        if(ae.getSource() == addToDB )
        	saveToDB(relCtx);
        	
        	
        if(ae.getSource() == addToFile)   //by cjj 2006-6-3
            saveAllData(relCtx);
        
        
        if(ae.getSource() == closeItem)
            closeRelationalContextEditor();
        if(ae.getSource() == showGraphItem)
            if(isOnWork(getSelectedRelation()))
                JOptionPane.showMessageDialog(this, "You can not modify this context, it is already used through an algorithm.");
            else
                showAssociatedGraph();
        if(ae.getSource() == changeRelNameItem)
            if(isOnWork(getSelectedRelation()))
                JOptionPane.showMessageDialog(this, "You can not modify this context, it is already used through an algorithm.");
            else
                renameRelation();
        if(ae.getSource() == addRelItemB)
            createNewBinaryRelation(); //增加新的二元关系
        if(ae.getSource() == addRelItemM)
            createNewMultiValuedRelation();
        if(ae.getSource() == deleteRelItem)
            if(isOnWork(getSelectedRelation()))
                JOptionPane.showMessageDialog(this, "You can not modify this context, it is already used through an algorithm.");
            else
                removeRelation();
        if(ae.getSource() == addObjItem)
            if(isOnWork(getSelectedRelation()))
                JOptionPane.showMessageDialog(this, "You can not modify this context, it is already used through an algorithm.");
            else
                addFormalObject();
        if(ae.getSource() == addAttItem)
            if(isOnWork(getSelectedRelation()))
                JOptionPane.showMessageDialog(this, "You can not modify this context, it is already used through an algorithm.");
            else
                addFormalAttribute();
        if(ae.getSource() == deleteObjItem)
            if(isOnWork(getSelectedRelation()))
                JOptionPane.showMessageDialog(this, "You can not modify this context, it is already used through an algorithm.");
            else
                removeFormalObject();
        if(ae.getSource() == deleteAttItem)
            if(isOnWork(getSelectedRelation()))
                JOptionPane.showMessageDialog(this, "You can not modify this context, it is already used through an algorithm.");
            else
                removeFormalAttribute();
        if(ae.getSource() == razItem)
            if(isOnWork(getSelectedRelation()))
                JOptionPane.showMessageDialog(this, "You can not modify this context, it is already used through an algorithm.");
            else
                razRelationValues();
        if(ae.getSource() == nomiScaleItem)
            execNominaleSaling();
        if(ae.getSource() == dichoScaleItem)
            execDichotomicSaling();
        if(ae.getSource() == specScaleItem)
            execOtherSaling();
        if(ae.getSource() == savConsItem)
        {
            addMessage("Write Console ...");
            java.io.File f = chooseAnyFile();
            if(f != null)
                try
                {
                    ConsoleWriter cs = new ConsoleWriter(new BufferedWriter(new FileWriter(f)));
                    cs.setData(console);
                    writeTask.setDataWriter(cs);
                    writeTask.exec();
                }
                catch(Exception e)
                {
                    e.printStackTrace();
                    addMessage("Write Console stopped (can not create Writer) !\n");
                }
            else
                addMessage("Writing Console canceled !\n");
        }
        
        //执行相关的动作,查看
        checkPossibleActions();
    }

    public void mouseClicked(MouseEvent me)
    {
        if(setOfAbsRelTableEditor.contains(me.getSource()) && me.getModifiers() == 16 && me.getClickCount() == 2)
            if(isOnWork(getSelectedRelation()))
                JOptionPane.showMessageDialog(this, "You can not modify this context, it is already used through an algorithm.");
            else
                doubleClickOnTableCell();
    }

    
    public void mouseEntered(MouseEvent mouseevent)
    {
    }

    public void mouseExited(MouseEvent mouseevent)
    {
    }

    public void mousePressed(MouseEvent mouseevent)
    {
    }

    public void mouseReleased(MouseEvent mouseevent)
    {
    }

    public void addBinaryRelation(BinaryRelation binRel)
    {
        relCtx.addRelation(binRel);  //二维空间作为一个对象压入relCtx中
        //使setOfAbsRelTableEditor向量里面压入了BinaryRelationTableEditor对象
        showBinaryRelation(binRel);  //该函数用来在界面上显示二元关系
    }

    protected void showBinaryRelation(BinaryRelation binRel)
    {
        BinaryRelationTableEditor brte = new BinaryRelationTableEditor(binRel);
        brte.addMouseListener(this);
        setOfAbsRelTableEditor.addElement(brte);
        ongletPanel.add(binRel.getRelationName(), new JScrollPane(brte));
        ongletPanel.setSelectedIndex(ongletPanel.getTabCount() - 1);
        ongletPanel.repaint();
    }

    public void addMultiValuedRelation(MultiValuedRelation mvRel)
    {
        relCtx.addRelation(mvRel);
        showMultiValuedRelation(mvRel);
    }

    protected void showMultiValuedRelation(MultiValuedRelation mvRel)
    {
        MultiValuedRelationTableEditor mvrte = new MultiValuedRelationTableEditor(mvRel);
        mvrte.addMouseListener(this);
        setOfAbsRelTableEditor.addElement(mvrte);
        ongletPanel.add(mvRel.getRelationName(), new JScrollPane(mvrte));
        ongletPanel.setSelectedIndex(ongletPanel.getTabCount() - 1);
        ongletPanel.repaint();
    }

    public void addConceptLattice(ConceptLattice cl)
    {
        BinaryRelation binRel = BinaryRelation.getInstanceFromLattice(cl);
        binRel.setLattice(cl);
        addBinaryRelation(binRel);
        showAssociatedGraph();
    }

    public void stateChanged(ChangeEvent ce)
    {
        if(ce.getSource() == ongletPanel)
            checkPossibleActions();
    }

    public RelationalContext getFamilyContexts()
    {
        return relCtx;
    }

    public AbstractRelation getSelectedRelation()
    {
    	//返回可视化界面上的二元关系
        return relCtx.getRelation(ongletPanel.getSelectedIndex());
    }

    public AbstractRelationTableEditor getSelectedTableEditor()
    {
        return (AbstractRelationTableEditor)setOfAbsRelTableEditor.elementAt(ongletPanel.getSelectedIndex());
    }

    protected void renameRelationalContext()
    {
        String newName = JOptionPane.showInputDialog(this, "Give a new name for the relational context :");
        if(newName != null && !newName.equals(""))
        {
            relCtx.setName(newName);
            setTitle("Contexts Familly name : " + newName);
        }
    }

    protected void exportData()
    {
        addMessage("Export ...");
        ExportDialogSelection eds = new ExportDialogSelection(relCtx, this);
        eds.askParameter();
        if(eds.getAction() == ExportDialogSelection.CANCELLED)
        {
            addMessage("Export Cancelled !\n");
            return;
        }
        if(eds.getData() == null)
        {
            addMessage("No data to export !");
            addMessage("Export Cancelled !\n");
            return;
        } else
        {
            writeAction(eds.getData(), "Export", eds.getTypeOfExport());
            return;
        }
    }

    protected void closeRelationalContextEditor()
    {
        processWindowEvent(new WindowEvent(this, 201));
    }

    public void showAssociatedGraph()
    {
        AbstractRelation absr = relCtx.getRelation(ongletPanel.getSelectedIndex());
        ConceptLattice lat = absr.getLattice();
        if(lat == null)
        {
            JOptionPane.showMessageDialog(this, "This context has no associated lattice graph.");
            return;
        } else
        {
            LatticeGraphFrame f = new LatticeGraphFrame(lat.getDescription(), lat.getTop());
            f.show();
            return;
        }
    }

    protected void renameRelation()
    {
        String newName = JOptionPane.showInputDialog(this, "Give a new name for the relation :");
        if(newName != null && !newName.equals(""))
        {
            ongletPanel.setTitleAt(ongletPanel.getSelectedIndex(), newName);
            relCtx.getRelation(ongletPanel.getSelectedIndex()).setRelationName(newName);
            ongletPanel.getSelectedComponent().validate();
        } else
        {
            addMessage("Changing relation name canceled !\n");
        }
    }

    //增加二元关系
    protected void createNewBinaryRelation()
    {
        addMessage("Add a new binary relation ");
        String numObjS = null;
        int numObj = 0;
        //增加对象个数
        do
        {
            numObjS = JOptionPane.showInputDialog(this, "Give a number of objects :");
            if(numObjS != null)
            {
                if(!numObjS.equals(""))
                    try
                    {
                        numObj = Integer.parseInt(numObjS);
                    }
                    catch(NumberFormatException nfe)
                    {
                        numObj = 0;
                    }
                if(numObjS.equals("") || numObj <= 0)
                    JOptionPane.showMessageDialog(this, "The input should be an integer >0");
            }
        } while(numObjS != null && (numObjS.equals("") || numObj <= 0));
        if(numObjS == null)
        {
            addMessage("Add a new binary relation canceled...\n");
            return;
        }
        String numAttS = null;
        int numAtt = 0;
        //增加属性个数
        do
        {
            numAttS = JOptionPane.showInputDialog(this, "Give a number of attributes :");
            if(numAttS != null)
            {
                if(!numAttS.equals(""))
                    try
                    {
                        numAtt = Integer.parseInt(numAttS);
                    }
                    catch(NumberFormatException nfe)
                    {
                        numAtt = 0;
                    }
                if(numAttS.equals("") || numAtt <= 0)
                    JOptionPane.showMessageDialog(this, "Give a number of attributes :");
            }
        } while(numAttS != null && (numAttS.equals("") || numAtt <= 0));
        if(numAttS == null)
        {
            addMessage("Add a new binary relation canceled...\n");
            return;
        } else
        {
        	//够造一个二元关系
            BinaryRelation binRel = new BinaryRelation(numObj, numAtt);
            addBinaryRelation(binRel);
            addMessage("Add a new binary relation completed\n");
            return;
        }
    }

    protected void createNewMultiValuedRelation()
    {
        addMessage("Add a new multi-valued relation ");
        String numObjS = null;
        int numObj = 0;
        do
        {
            numObjS = JOptionPane.showInputDialog(this, "Give a number of objects :");
            if(numObjS != null)
            {
                if(!numObjS.equals(""))
                    try
                    {
                        numObj = Integer.parseInt(numObjS);
                    }
                    catch(NumberFormatException nfe)
                    {
                        numObj = 0;
                    }
                if(numObjS.equals("") || numObj <= 0)
                    JOptionPane.showMessageDialog(this, "The input should be an integer >0");
            }
        } while(numObjS != null && (numObjS.equals("") || numObj <= 0));
        if(numObjS == null)
        {
            addMessage("Add a new multi-valued relation canceled...\n");
            return;
        }
        String numAttS = null;
        int numAtt = 0;
        do
        {
            numAttS = JOptionPane.showInputDialog(this, "Give a number of attributes :");
            if(numAttS != null)
            {
                if(!numAttS.equals(""))
                    try
                    {
                        numAtt = Integer.parseInt(numAttS);
                    }
                    catch(NumberFormatException nfe)
                    {
                        numAtt = 0;
                    }
                if(numAttS.equals("") || numAtt <= 0)
                    JOptionPane.showMessageDialog(this, "Give a number of attributes :");
            }
        } while(numAttS != null && (numAttS.equals("") || numAtt <= 0));
        if(numAttS == null)
        {
            addMessage("Add a new multi-valued relation canceled...\n");
            return;
        } else
        {
            MultiValuedRelation mvRel = new MultiValuedRelation(numObj, numAtt);
            addMultiValuedRelation(mvRel);
            addMessage("Add a new multi-valued relation completed\n");
            return;
        }
    }

    protected void removeRelation()
    {
        if(relCtx.getNumberOfRelation() == 0)
        {
            return;
        } else
        {
            addMessage("Remove a relation: started");
            int idx = ongletPanel.getSelectedIndex();
            relCtx.removeRelation(idx);
            setOfAbsRelTableEditor.remove(idx);
            ongletPanel.remove(idx);
            checkPossibleActions();
            addMessage("Remove a relation: completed\n");
            return;
        }
    }

    protected void addFormalObject()
    {
        int relIdx = ongletPanel.getSelectedIndex();
        AbstractRelation absRel = relCtx.getRelation(relIdx);
        absRel.addObject();
        ((AbstractRelationTableEditor)setOfAbsRelTableEditor.elementAt(ongletPanel.getSelectedIndex())).setModelFromRelation(absRel);
        ongletPanel.getSelectedComponent().validate();
    }

    protected void addFormalAttribute()
    {
        int relIdx = ongletPanel.getSelectedIndex();
        AbstractRelation absRel = relCtx.getRelation(relIdx);
        absRel.addAttribute();
        ((AbstractRelationTableEditor)setOfAbsRelTableEditor.elementAt(ongletPanel.getSelectedIndex())).setModelFromRelation(absRel);
        ongletPanel.getSelectedComponent().validate();
    }

    protected void removeFormalObject()
    {
        int relIdx = ongletPanel.getSelectedIndex();
        AbstractRelation absRel = relCtx.getRelation(relIdx);
        FormalObject lesVals[] = absRel.getFormalObjects();
        FormalObject fo = (FormalObject)JOptionPane.showInputDialog(this, "Remove an object", "Choose an object to remove :", 3, null, lesVals, lesVals[0]);
        if(fo != null)
            absRel.removeObject(fo);
        ((AbstractRelationTableEditor)setOfAbsRelTableEditor.elementAt(ongletPanel.getSelectedIndex())).setModelFromRelation(absRel);
        ongletPanel.getSelectedComponent().validate();
    }

    protected void removeFormalAttribute()
    {
        int relIdx = ongletPanel.getSelectedIndex();
        AbstractRelation absRel = relCtx.getRelation(relIdx);
        FormalAttribute lesVals[] = absRel.getFormalAttributes();
        if(lesVals == null)
            System.out.println("lesVals == null");
        FormalAttribute fa = (FormalAttribute)JOptionPane.showInputDialog(this, "Remove an attribute", "Choose an attribute to remove :", 3, null, lesVals, lesVals[0]);
        if(fa != null)
            absRel.removeAttribute(fa);
        ((AbstractRelationTableEditor)setOfAbsRelTableEditor.elementAt(ongletPanel.getSelectedIndex())).setModelFromRelation(absRel);
        ongletPanel.getSelectedComponent().validate();
    }

    //处理界面上的双击事件
    protected void doubleClickOnTableCell()
    {
    	//构建一个扩展的JTable出来arte,可以简化
        AbstractRelationTableEditor arte = (AbstractRelationTableEditor)setOfAbsRelTableEditor.elementAt(ongletPanel.getSelectedIndex());
        int rowIdx = arte.getSelectedRow();
        int colIdx = arte.getSelectedColumn();
        if(rowIdx == 0 && colIdx == 0)
            renameRelation();
        if(rowIdx == 0 && colIdx > 0 && !(relCtx.getRelation(ongletPanel.getSelectedIndex()) instanceof ScalingBinaryRelation) && !(relCtx.getRelation(ongletPanel.getSelectedIndex()) instanceof InterObjectBinaryRelation))
        {
            String val = null;
            do
            {
                val = JOptionPane.showInputDialog(this, "Give a name to formal attribut" + arte.getValueAt(rowIdx, colIdx));
                if(val != null)
                    if(val.equals("") || val.indexOf("|") != -1)
                        JOptionPane.showMessageDialog(this, "The name shouldn't be empty and shouldn't contains any '|' character.");
                    else
                        arte.getModel().setValueAt(val, rowIdx, colIdx);
            } while(val != null && val.equals("") | (val.indexOf("|") != -1));
            if(val == null)
                addMessage("Changing formal attribute name canceled !\n");
        }
        if(rowIdx > 0 && colIdx == 0 && !(relCtx.getRelation(ongletPanel.getSelectedIndex()) instanceof ScalingBinaryRelation) && !(relCtx.getRelation(ongletPanel.getSelectedIndex()) instanceof InterObjectBinaryRelation))
        {
            String val = null;
            do
            {
                val = JOptionPane.showInputDialog(this, "Give a name to formal object" + arte.getValueAt(rowIdx, colIdx));
                if(val != null)
                    if(val.equals("") || val.indexOf("|") != -1)
                        JOptionPane.showMessageDialog(this, "The name shouldn't be empty and shouldn't contains any '|' character.");
                    else
                        arte.getModel().setValueAt(val, rowIdx, colIdx);
            } while(val != null && val.equals("") | (val.indexOf("|") != -1));
            if(val == null)
                addMessage("Changing formal object name canceled !\n");
        }
        
        //在内存中存放数据的核心操作－－－－－－－－－－－－－－－－－
        if(rowIdx > 0 && colIdx > 0)
            arte.queryNewInputRelationValue(rowIdx, colIdx); 
    }

    protected void razRelationValues()
    {
        addMessage("Reset Relation");
        relCtx.getRelation(ongletPanel.getSelectedIndex()).emptyRelation();
        addMessage("Done !\n");
    }

    protected void execNominaleSaling()
    {
        addMessage("This scalling is not yet imlemented !\n");
    }

    protected void execDichotomicSaling()
    {
        addMessage("This scalling is not yet imlemented !\n");
    }

    protected void execOtherSaling()
    {
        BinaryRelation binRel = ((MultiValuedRelation)relCtx.getRelation(ongletPanel.getSelectedIndex())).getEmptyScalingBinaryRelation();
        addBinaryRelation(binRel);
    }

    public void createInterObjectBinaryRelationTable()
    {
        Vector lesRels = new Vector();
        int k = 0;
        for(int i = 0; i < relCtx.getNumberOfRelation(); i++)
            if(!(relCtx.getRelation(i) instanceof InterObjectBinaryRelation) && !(relCtx.getRelation(i) instanceof ScalingBinaryRelation) && (relCtx.getRelation(i) instanceof BinaryRelation))
            {
                lesRels.addElement("" + i + " - " + relCtx.getRelation(i).getRelationName());
                k++;
            }

        String val = (String)JOptionPane.showInputDialog(this, "Create an inter-object relation with ...", "Choose a binary relation :", 3, null, lesRels.toArray(), lesRels.elementAt(0));
        if(val != null)
        {
            int idxMe = ongletPanel.getSelectedIndex();
            StringTokenizer tk = new StringTokenizer(val, " ");
            int idxOt = Integer.parseInt(tk.nextToken());
            addBinaryRelation(new InterObjectBinaryRelation(relCtx.getRelation(idxMe), relCtx.getRelation(idxOt)));
        }
    }

    public void setWorkOnRelation(AbstractRelation absr)
    {
        relationOnWork.put(absr, new Integer(1));
    }

    public void releaseWorkOnRelation(AbstractRelation absr)
    {
        relationOnWork.remove(absr);
    }

    public boolean isOnWork(AbstractRelation absr)
    {
        return relationOnWork.get(absr) != null;
    }

    public void windowActivated(WindowEvent windowevent)
    {
    }

    public void windowClosing(WindowEvent windowevent)
    {
    }

    public void windowClosed(WindowEvent windowevent)
    {
    }

    public void windowDeactivated(WindowEvent windowevent)
    {
    }

    public void windowDeiconified(WindowEvent windowevent)
    {
    }

    public void windowIconified(WindowEvent windowevent)
    {
    }

    public void windowOpened(WindowEvent windowevent)
    {
    }

    public void showTaskResult(Object theResult)
    {
        for(int i = 0; (theResult instanceof AbstractTask) && i < ((AbstractTask)theResult).getBinRelOnUse().size(); i++)
            releaseWorkOnRelation((AbstractRelation)((AbstractTask)theResult).getBinRelOnUse().elementAt(i));

        if(theResult instanceof latticeAlgorithmTask)
        {
            LatticeAlgorithm algo = ((latticeAlgorithmTask)theResult).getAlgo();
            if(algo instanceof Gagci_NoInc)
            {
                for(int i = 0; i < ((Gagci_NoInc)algo).getSetOfEnrichingRelations().size(); i++)
                {
                    String title = ((BinaryRelation)((Gagci_NoInc)algo).getSetOfEnrichingRelations().elementAt(i)).getRelationName();
                    LatticeGraphFrame f = new LatticeGraphFrame(title, ((BinaryRelation)((Gagci_NoInc)algo).getSetOfEnrichingRelations().elementAt(i)).getLattice().getTop());
                    f.show();
                }

            } else
            {
                LatticeGraphFrame f = new LatticeGraphFrame(algo.getDescription(), algo.getLattice().getTop());
                f.show();
            }
        }
        if(theResult instanceof ruleAbstractTask)
            console.addMessage(((ruleAbstractTask)theResult).getResultat());
        if(theResult instanceof ReadingTask)
        {
            Object theData = ((ReadingTask)theResult).getData();
            if(theData instanceof BinaryRelation)
            {
                if(((BinaryRelation)theData).getRelationName().equals(AbstractRelation.DEFAULT_NAME))
                    ((BinaryRelation)theData).setRelationName(((ReadingTask)theResult).getDefaultNameForData());
                if(relCtx.getName().equals(RelationalContext.DEFAULT_NAME))
                {
                    relCtx.setName(((ReadingTask)theResult).getDefaultNameForData());
                    setTitle("Contexts Familly name : " + ((ReadingTask)theResult).getDefaultNameForData());
                }
                addBinaryRelation((BinaryRelation)theData);
                addMessage("Reading done !\n");
            }
            if(theData instanceof MultiValuedRelation)
            {
                if(((MultiValuedRelation)theData).getRelationName().equals(AbstractRelation.DEFAULT_NAME))
                    ((MultiValuedRelation)theData).setRelationName(((ReadingTask)theResult).getDefaultNameForData());
                if(relCtx.getName().equals(RelationalContext.DEFAULT_NAME))
                {
                    relCtx.setName(((ReadingTask)theResult).getDefaultNameForData());
                    setTitle("Contexts Familly name : " + ((ReadingTask)theResult).getDefaultNameForData());
                }
                addMultiValuedRelation((MultiValuedRelation)theData);
                addMessage("Reading done !\n");
            }
            if(theData instanceof RelationalContext)
            {
                RelationalContext rc = (RelationalContext)theData;
                if(relCtx.getName().equals(RelationalContext.DEFAULT_NAME))
                {
                    relCtx.setName(((ReadingTask)theResult).getDefaultNameForData());
                    setTitle("Contexts Familly name : " + ((ReadingTask)theResult).getDefaultNameForData());
                }
                for(int i = 0; i < rc.getNumberOfRelation(); i++)
                {
                    if(rc.getRelation(i) instanceof BinaryRelation)
                        addBinaryRelation((BinaryRelation)rc.getRelation(i));
                    if(rc.getRelation(i) instanceof MultiValuedRelation)
                        addMultiValuedRelation((MultiValuedRelation)rc.getRelation(i));
                }

                addMessage("Reading done !\n");
            }
            if(theData instanceof ConceptLattice)
            {
                addConceptLattice((ConceptLattice)theData);
                if(getSelectedRelation().getRelationName().equals(AbstractRelation.DEFAULT_NAME))
                {
                    getSelectedRelation().setRelationName(((ReadingTask)theResult).getDefaultNameForData());
                    ongletPanel.setTitleAt(ongletPanel.getSelectedIndex(), ((ReadingTask)theResult).getDefaultNameForData());
                }
                if(relCtx.getName().equals(RelationalContext.DEFAULT_NAME))
                {
                    relCtx.setName(((ReadingTask)theResult).getDefaultNameForData());
                    setTitle("Contexts Familly name : " + ((ReadingTask)theResult).getDefaultNameForData());
                }
                addMessage("Reading done !\n");
            }
        }
        if(theResult instanceof String)
            addMessage(theResult.toString());
        checkPossibleActions();
    }
    
    //by cjj 2006-6-9
    private void saveToDB(RelationalContext relCtx)
    {
    	//System.out.println(relCtx.getRelation(0).getObjectsNumber());
    	String tablename = relCtx.getRelation(0).getRelationName();
    	tablename=tablename.replaceAll(" ","");
    	System.out.println(tablename);
    	Vector att=relCtx.getRelation(0).getAttributes();
    	String sql_col="";
    	for(int i=0;i<att.size()-1;i++)
    	{
    		sql_col=sql_col+att.get(i)+" int ,";
    	}
    	sql_col=sql_col+att.get(att.size()-1)+" int";
    	String sql="create table "+tablename+" ( "+sql_col+" )";
    	String sql_del="drop table "+tablename;
    	//执行sql语句 ,为了防止同名数据表出现，再创建之前删除一次
    	toDB.createTable(sql_del,sql);
    	FormalObject lesObjs[] = relCtx.getRelation(0).getFormalObjects();
        FormalAttribute lesAtts[] = relCtx.getRelation(0).getFormalAttributes();
        for(int i = 0; i < lesObjs.length; i++)
        {
            String line = "";
            for(int j = 0; j < lesAtts.length; j++)
                if(relCtx.getRelation(0).getRelation(lesObjs[i], lesAtts[j]).isEmpty())
                    line = line + "0,";
                else
                    line = line + "1,";
            line=line.substring(0,line.length()-1);
            String sql_ins="insert into "+tablename+" values("+line+")";
            toDB.executeSql(sql_ins);
        }
        
    	
    	
    	
    	
    }
}
