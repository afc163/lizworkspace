// Decompiled by Jad v1.5.7g. Copyright 2000 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/SiliconValley/Bridge/8617/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi 
// Source File Name:   ActionComponent.java

package lattice.graph.onglet;

import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;

public class ActionComponent extends Panel
{

    protected static int margin = 5;
    protected boolean state;
    protected ActionListener actionListener;
    private Image offscreen;

    public ActionComponent()
    {
        state = false;
        enableEvents(16L);
    }

    public void select()
    {
        state = true;
        repaint();
    }

    public void unselect()
    {
        state = false;
        repaint();
    }

    public void addActionListener(ActionListener listener)
    {
        actionListener = AWTEventMulticaster.add(actionListener, listener);
        enableEvents(16L);
    }

    public void removeActionListener(ActionListener listener)
    {
        actionListener = AWTEventMulticaster.remove(actionListener, listener);
    }

    public void processMouseEvent(MouseEvent e)
    {
        switch(e.getID())
        {
        case 501: 
            select();
            break;
        }
        super.processMouseEvent(e);
    }

}
