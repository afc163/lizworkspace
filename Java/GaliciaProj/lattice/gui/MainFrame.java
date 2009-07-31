// Decompiled by Jad v1.5.7g. Copyright 2000 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/SiliconValley/Bridge/8617/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi 
// Source File Name:   MainFrame.java

package lattice.gui;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.WindowEvent;
import java.io.PrintStream;
import java.util.EventObject;
import java.util.Vector;
import javax.swing.*;
import lattice.gui.tooltask.WaitingFrame;
import lattice.io.*;
import lattice.util.*;

// Referenced classes of package lattice.gui:
//            OpenFileFrame, ConsoleFrame, RelationalContextEditor

public class MainFrame extends OpenFileFrame
{

    public static final String RESSOURCES = "ressources/";
    private JMenu fileMenu;
    private JMenuItem editItem;
    private JMenuItem openItem;
    private JMenuItem quitItem;
    private JMenu helpMenu;
    private JMenuItem aboutItem;
    private JToolBar jToolBar;
    private JButton openButton;
    private JButton saveButton;
    private JButton helpButton;
    private JButton closeButton;
    private Vector setOfRelationalContextEditor;

    //构造函数
    public MainFrame()
    {
        fileMenu = null;
        editItem = null;
        openItem = null;
        quitItem = null;
        helpMenu = null;
        aboutItem = null;
        jToolBar = null;
        openButton = null;
        saveButton = null;
        helpButton = null;
        closeButton = null;
        setOfRelationalContextEditor = null;
        setOfRelationalContextEditor = new Vector();
        setTitle("Galicia v1.0s1, Stable Distribution - July 2003");
        addWindowListener(this);
        setFrameSize();  
        initComponents(); //可视化部分
        addMessage("> Hello folks !");  //Console窗口中输出字符串
        addMessage("> Welcome to Galicia\n");
    }

    private void setFrameSize()
    {
        setSize(640, 400);
        setLocation(100, 50);
    }

    public void initComponents()
    {
        initMenuBar();
        initToolBar();
    }

    private void initMenuBar()
    {
        initFileMenu();
        initHelpMenu();
        maBar = new JMenuBar();
        maBar.add(fileMenu);
        maBar.add(consoleMenu);
        maBar.add(helpMenu);
        setJMenuBar(maBar);
        validate();
    }

    private void initFileMenu()
    {
    	//第一个界面上的File菜单
        fileMenu = new JMenu("File");
        fileMenu.setMnemonic('F');
        editItem = new JMenuItem("New ... (Context or Family Contexts)");
        editItem.setAccelerator(KeyStroke.getKeyStroke(78, 2, false));
        editItem.setMnemonic('N');
        editItem.addActionListener(this);
        openItem = new JMenuItem("Open");
        openItem.setAccelerator(KeyStroke.getKeyStroke(79, 2, false));
        openItem.setMnemonic('O');
        openItem.addActionListener(this);
        quitItem = new JMenuItem("Close");
        quitItem.setAccelerator(KeyStroke.getKeyStroke(81, 2, false));
        quitItem.setMnemonic('Q');
        quitItem.addActionListener(this);
        fileMenu.add(editItem);
        fileMenu.addSeparator();
        fileMenu.add(openItem);
        fileMenu.addSeparator();
        fileMenu.add(quitItem);
    }

    private void initHelpMenu()
    {
    	//第一个界面的Help菜单
        helpMenu = new JMenu("Help");
        helpMenu.setMnemonic('H');
        aboutItem = new JMenuItem("About");
        aboutItem.setMnemonic('A');
        aboutItem.addActionListener(this);
        helpMenu.add(aboutItem);
    }

    private void initToolBar()
    {
        openButton = new JButton(new ImageIcon("ressources/open.gif"));
        openButton.setToolTipText("Open");
        openButton.addActionListener(this);
        saveButton = new JButton(new ImageIcon("ressources/save.gif"));
        saveButton.setToolTipText("Save");
        saveButton.setEnabled(false);
        saveButton.addActionListener(this);
        closeButton = new JButton(new ImageIcon("ressources/close.gif"));
        closeButton.setToolTipText("Close");
        closeButton.addActionListener(this);
        helpButton = new JButton(new ImageIcon("ressources/help.gif"));
        helpButton.setToolTipText("About");
        helpButton.addActionListener(this);
        jToolBar = new JToolBar();
        jToolBar.add(openButton, null);
        jToolBar.addSeparator();
        jToolBar.add(closeButton, null);
        jToolBar.addSeparator();
        jToolBar.add(saveButton, null);
        jToolBar.addSeparator();
        jToolBar.add(helpButton, null);
        getContentPane().add(jToolBar, "North");
    }

    public void actionPerformed(ActionEvent ae)
    {
        super.actionPerformed(ae);
        if(ae.getSource() == editItem)
        {//形式背景编辑状态
            addMessage("Action Edition\n");
            RelationalContext rc = new RelationalContext();
            RelationalContextEditor rce = new RelationalContextEditor(rc);
            rce.show();
            rce.addWindowListener(this);
            setOfRelationalContextEditor.add(rce);
        }
        if(ae.getSource() == openItem || ae.getSource() == openButton)
        {
            RelationalContext rc = new RelationalContext();
            RelationalContextEditor rce = new RelationalContextEditor(rc);
            rce.show();
            rce.addWindowListener(this);
            setOfRelationalContextEditor.add(rce);
            rce.openData();
        }
        if(ae.getSource() == quitItem || ae.getSource() == closeButton)
            System.exit(0);
        if(ae.getSource() == aboutItem || ae.getSource() == helpButton)
        {
            addMessage("About Galicia\n");
            String about = new String("Galicia Platform\nVersion: v1.0s1\n Stable Distribution \n\n(c) Copyrights University of Montreal (Canada), LIRMM (France),2002, 2003. All rights reserved.\n\nThe Galicia team acknowledges the contribution of all the designers of the algorithms used by the platform:\nCERES: Herv\351 Leblanc, Marianne Huchard (research supported by M. Dao, France Telecom R&D)\nARES:  Marianne Huchard, Herv\351 Dicky, Th\351r\350se Libourel, Jean Villerd\nARES Dual: Jean Villerd (research supported by M. Dao, France Telecom R&D)\nGAGCI: Marianne Huchard, Cyril Roume, Petko Valtchev\n\n");
            JOptionPane.showMessageDialog(this, about, "About Galicia", 1);
        }
    }

    public void windowActivated(WindowEvent windowevent)
    {
    }

    public void windowClosed(WindowEvent e)
    {
        if(e.getSource() == this)
            System.exit(0);
        if(e.getSource() instanceof WaitingFrame)
        {
            if((((WaitingFrame)e.getSource()).getJob() instanceof AbstractReader) && !((WaitingFrame)e.getSource()).goodWork())
                addMessage("Reading didn't work properly !\n");
            if((((WaitingFrame)e.getSource()).getJob() instanceof AbstractWriter) && ((WaitingFrame)e.getSource()).goodWork())
                addMessage("Writing done !\n");
            if((((WaitingFrame)e.getSource()).getJob() instanceof AbstractWriter) && !((WaitingFrame)e.getSource()).goodWork())
                addMessage("Writing didn't work properly !\n");
            if((((WaitingFrame)e.getSource()).getJob() instanceof ConsoleWriter) && ((WaitingFrame)e.getSource()).goodWork())
                addMessage("Writing done !\n");
            if((((WaitingFrame)e.getSource()).getJob() instanceof ConsoleWriter) && !((WaitingFrame)e.getSource()).goodWork())
                addMessage("Writing didn't work properly !\n");
            lattice.gui.tooltask.JobObservable job = ((WaitingFrame)e.getSource()).getJob();
            if((job instanceof AbstractReader) && ((AbstractReader)job).getData() != null)
            {
                RelationalContext rc = null;
                if(((AbstractReader)job).getData() instanceof BinaryRelation)
                {
                    rc = new RelationalContext();
                    rc.setName(((AbstractReader)job).getDefaultNameForData());
                    BinaryRelation binRel = (BinaryRelation)((AbstractReader)job).getData();
                    if(binRel.getRelationName().equals(AbstractRelation.DEFAULT_NAME))
                        binRel.setRelationName(((AbstractReader)job).getDefaultNameForData());
                    rc.addRelation(binRel);
                }
                if(((AbstractReader)job).getData() instanceof MultiValuedRelation)
                {
                    rc = new RelationalContext();
                    rc.setName(((AbstractReader)job).getDefaultNameForData());
                    MultiValuedRelation mvr = (MultiValuedRelation)((AbstractReader)job).getData();
                    if(mvr.getRelationName().equals(AbstractRelation.DEFAULT_NAME))
                        mvr.setRelationName(((AbstractReader)job).getDefaultNameForData());
                    rc.addRelation((MultiValuedRelation)((AbstractReader)job).getData());
                }
                if(((AbstractReader)job).getData() instanceof RelationalContext)
                {
                    rc = (RelationalContext)((AbstractReader)job).getData();
                    if(rc.getName().equals(RelationalContext.DEFAULT_NAME))
                        rc.setName(((AbstractReader)job).getDefaultNameForData());
                }
                if(((AbstractReader)job).getData() instanceof ConceptLattice)
                {
                    ConceptLattice cl = (ConceptLattice)((AbstractReader)job).getData();
                    rc = new RelationalContext();
                    rc.setName(((AbstractReader)job).getDefaultNameForData());
                    BinaryRelation binRel = BinaryRelation.getInstanceFromLattice(cl);
                    if(binRel.getRelationName().equals(AbstractRelation.DEFAULT_NAME))
                        binRel.setRelationName(((AbstractReader)job).getDefaultNameForData());
                    binRel.setLattice(cl);
                    rc.addRelation(binRel);
                }
                RelationalContextEditor rce = new RelationalContextEditor(rc);
                rce.addWindowListener(this);
                rce.show();
                if(((AbstractReader)job).getData() instanceof ConceptLattice)
                    rce.showAssociatedGraph();
                setOfRelationalContextEditor.add(rce);
                addMessage("Opening done ...\n");
            }
        } else
        {
            System.out.println("closed required !!");
        }
    }

    public void windowClosing(WindowEvent e)
    {
        if(e.getSource() == this)
        {
            System.exit(0);
        } else
        {
            System.out.println("closing done");
            ((JFrame)e.getSource()).dispose();
        }
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
}
