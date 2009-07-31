// Decompiled by Jad v1.5.7g. Copyright 2000 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/SiliconValley/Bridge/8617/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi 
// Source File Name:   GraphMenuBar.java

package lattice.gui.graph.menu;

import java.awt.Component;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;
import lattice.gui.graph.LatticeGraphFrame;

public class GraphMenuBar extends JMenuBar
    implements ActionListener
{

    LatticeGraphFrame lgf;

    public GraphMenuBar(LatticeGraphFrame lgf)
    {
        this.lgf = lgf;
        add(initMenuFile());
        add(initMenuDisplay());
    }

    public JMenu initMenuFile()
    {
        JMenu file = new JMenu("File");
        JMenuItem open = new JMenuItem("Open");
        file.add(open);
        JMenuItem save = new JMenuItem("Save");
        file.add(save);
        JMenuItem pdf = new JMenuItem("Save as PDF");
        pdf.addActionListener(this);
        file.add(pdf);
        file.addSeparator();
        JMenuItem close = new JMenuItem("Close");
        file.add(close);
        return file;
    }

    public JMenu initMenuDisplay()
    {
        JMenu display = new JMenu("Display");
        display.add(createPoliceMenu(this));
        return display;
    }

    protected JMenu createPoliceMenu(ActionListener listener)
    {
        String polices[] = getToolkit().getFontList();
        JMenu police = new JMenu("Fonts");
        for(int i = 0; i < polices.length; i++)
        {
            JMenuItem m = new JMenuItem(polices[i]);
            m.addActionListener(listener);
            police.add(m);
        }

        return police;
    }

    public void actionPerformed(ActionEvent e)
    {
        lgf.generatePdf();
    }
}
