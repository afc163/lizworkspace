// Decompiled by Jad v1.5.7g. Copyright 2000 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/SiliconValley/Bridge/8617/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi 
// Source File Name:   AbstractControler.java

package lattice.gui.controler;

import java.awt.event.*;
import javax.swing.JMenu;
import lattice.gui.ConsoleFrame;
import lattice.gui.RelationalContextEditor;

public abstract class AbstractControler
    implements ActionListener, WindowListener
{

    protected RelationalContextEditor rce;

    public AbstractControler(RelationalContextEditor rce)
    {
        this.rce = rce;
    }

    public abstract void checkPossibleActions();

    public void addMessage(String s)
    {
        rce.addMessage(s);
    }

    public abstract JMenu getMainMenu();

    public void windowActivated(WindowEvent windowevent)
    {
    }

    public void windowClosed(WindowEvent windowevent)
    {
    }

    public void windowClosing(WindowEvent windowevent)
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

    public abstract void actionPerformed(ActionEvent actionevent);
}
