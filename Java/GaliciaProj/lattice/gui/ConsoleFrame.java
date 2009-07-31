// Decompiled by Jad v1.5.7g. Copyright 2000 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/SiliconValley/Bridge/8617/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi 
// Source File Name:   ConsoleFrame.java

package lattice.gui;

import java.awt.*;
import java.awt.event.*;
import java.util.EventObject;
import javax.swing.*;
import lattice.command.RuleCommand;

// Referenced classes of package lattice.gui:
//            Console, MainDesktop

public abstract class ConsoleFrame extends JFrame
    implements ActionListener, WindowListener
{

    protected JMenuBar maBar;
    protected JMenu consoleMenu;
    protected JMenuItem showItem;
    protected JMenuItem hideItem;
    protected JMenuItem clearItem;
    protected JSplitPane splitPanel;
    protected JPanel topPanel;
    protected JPanel bottomPanel;
    private MainDesktop mainDesktop;
    protected Console console;
    protected boolean showConsole;
    private JLabel statutLabel;
    protected double DIVIDER_LOC;
    protected int FIX_LOC;

    public ConsoleFrame()
    {
        showConsole = true;
        statutLabel = null;
        DIVIDER_LOC = 0.69999999999999996D;
        FIX_LOC = -1;
        initComponent();
    }

    public ConsoleFrame(double div_loc)
    {
        showConsole = true;
        statutLabel = null;
        DIVIDER_LOC = 0.69999999999999996D;
        FIX_LOC = -1;
        DIVIDER_LOC = div_loc;
        initComponent();
    }

    public Console getConsole()
    {
        return console;
    }

    public void initComponent()
    {
        initConsoleMenu();
        initStatusBar();
        initView();
    }

    private void setLookNFeel()
    {
        try
        {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }

    private void initConsoleMenu()
    {
        consoleMenu = new JMenu("Console");
        consoleMenu.setMnemonic('C');
        showItem = new JMenuItem("Show Console");
        showItem.setMnemonic('S');
        showItem.setAccelerator(KeyStroke.getKeyStroke(83, 2, false));
        showItem.addActionListener(this);
        hideItem = new JMenuItem("Hide Console");
        hideItem.setAccelerator(KeyStroke.getKeyStroke(72, 2, false));
        hideItem.setMnemonic('H');
        hideItem.addActionListener(this);
        clearItem = new JMenuItem("Clear Console");
        clearItem.setMnemonic('C');
        clearItem.setAccelerator(KeyStroke.getKeyStroke(67, 2, false));
        clearItem.addActionListener(this);
        consoleMenu.add(showItem);
        consoleMenu.add(hideItem);
        consoleMenu.add(clearItem);
    }

    private void initStatusBar()
    {
    }

    private void initView()
    {
        console = new Console(new RuleCommand());
        mainDesktop = new MainDesktop();
        topPanel = new JPanel(new BorderLayout());
        topPanel.add(mainDesktop);
        bottomPanel = new JPanel(new BorderLayout());
        bottomPanel.add(console, "Center");
        splitPanel = new JSplitPane(0, topPanel, bottomPanel);
        getContentPane().add(splitPanel, "Center");
    }

    public void actionPerformed(ActionEvent ae)
    {
        if(ae.getSource() == showItem)
        {
            showConsole = true;
            repaint();
        }
        if(ae.getSource() == hideItem)
        {
            showConsole = false;
            repaint();
        }
        if(ae.getSource() == clearItem)
            console.clear();
    }

    public void setTopPanel(JComponent cmp)
    {
        topPanel.removeAll();
        topPanel.add(cmp, "Center");
    }

    public void setBottomPanel(JComponent cmp)
    {
        bottomPanel.removeAll();
        bottomPanel.add(cmp, "Center");
    }

    public void paint(Graphics g)
    {
        super.paint(g);
        if(showConsole)
        {
            if(FIX_LOC != -1)
                splitPanel.setDividerLocation(FIX_LOC);
            else
                splitPanel.setDividerLocation(DIVIDER_LOC);
        } else
        {
            splitPanel.setDividerLocation(1.0D);
        }
    }

    public void addMessage(String newMess)
    {
        console.addMessage(newMess);
    }

    public abstract void windowActivated(WindowEvent windowevent);

    public abstract void windowClosed(WindowEvent windowevent);

    public abstract void windowClosing(WindowEvent windowevent);

    public abstract void windowDeactivated(WindowEvent windowevent);

    public abstract void windowDeiconified(WindowEvent windowevent);

    public abstract void windowIconified(WindowEvent windowevent);

    public abstract void windowOpened(WindowEvent windowevent);
}
