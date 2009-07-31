// Decompiled by Jad v1.5.7g. Copyright 2000 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/SiliconValley/Bridge/8617/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi 
// Source File Name:   LatticeToolBar.java

package lattice.gui.graph.control;

import java.awt.*;
import javax.swing.*;
import lattice.graph.utils.IkbsPanel;
import lattice.gui.graph.LatticeGraphViewer;

// Referenced classes of package lattice.gui.graph.control:
//            ZoomLatticeEditor, LGControlPanel

public class LatticeToolBar extends JToolBar
{

    protected LatticeGraphViewer lgv;
    protected ZoomLatticeEditor zle;
    protected LGControlPanel lgcp;

    public LatticeToolBar(LatticeGraphViewer lgv)
    {
        super("Control", 1);
        this.lgv = lgv;
        try
        {
            jbInit();
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }

    private void jbInit2()
        throws Exception
    {
        setLayout(new FlowLayout(0));
        setOpaque(false);
        setBackground(new Color(50, 50, 70));
        setBorderPainted(true);
        setFloatable(true);
        setOrientation(1);
        zle = new ZoomLatticeEditor("Control", lgv);
        add(zle);
        lgcp = new LGControlPanel(lgv);
        add(lgcp);
        java.awt.Image i = getToolkit().createImage("ressources/GaliciaPetit.png");
        JLabel jGalicia = new JLabel(new ImageIcon(i));
        jGalicia.setOpaque(false);
        jGalicia.setToolTipText("Galicia Lattice Viewer Developped by David Grosser");
        add(jGalicia);
    }

    private void jbInit()
        throws Exception
    {
        setLayout(new BorderLayout(0, 0));
        IkbsPanel p = new IkbsPanel();
        p.setOpaque(false);
        p.setLayout(new GridBagLayout());
        p.initGridBagConstraint();
        p.c.insets = new Insets(0, 0, 0, 0);
        setBackground(new Color(50, 50, 70));
        setBorderPainted(true);
        setFloatable(true);
        setOrientation(1);
        zle = new ZoomLatticeEditor("Control", lgv);
        p.xyPosition(p, zle, 0, 0, 1);
        lgcp = new LGControlPanel(lgv);
        p.xyPosition(p, lgcp, 0, 1, 1);
        add(p, "North");
        JLabel jGalicia = new JLabel(new ImageIcon("ressources/GaliciaPetit.png"));
        jGalicia.setOpaque(false);
        jGalicia.setToolTipText("Galicia Lattice Viewer Developped by David Grosser");
        add(jGalicia, "South");
    }

    public Dimension getPreferredSize()
    {
        setOrientation(1);
        return new Dimension(180, 600);
    }
}
