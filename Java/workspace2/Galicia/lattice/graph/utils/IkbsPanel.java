// Decompiled by Jad v1.5.7g. Copyright 2000 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/SiliconValley/Bridge/8617/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi 
// Source File Name:   IkbsPanel.java

package lattice.graph.utils;

import java.awt.*;
import javax.swing.JPanel;

// Referenced classes of package lattice.graph.utils:
//            GetFrame

public class IkbsPanel extends JPanel
    implements GetFrame
{

    public GridBagConstraints c;

    public IkbsPanel()
    {
    }

    public void initGridBagConstraint()
    {
        c = new GridBagConstraints();
        c.ipadx = 0;
        c.ipady = 0;
        c.gridheight = 1;
        c.fill = 1;
        c.weightx = 0.0D;
        c.weighty = 0.0D;
        c.anchor = 10;
        c.insets = new Insets(2, 2, 2, 2);
    }

    public Dimension adaptedSize()
    {
        return new Dimension(100, 100);
    }

    public void xyPosition(Container conteneur, Component element, int x, int y, int gridwidth)
    {
        if(c == null)
            initGridBagConstraint();
        c.gridx = x;
        c.gridy = y;
        c.gridwidth = gridwidth;
        ((GridBagLayout)conteneur.getLayout()).setConstraints(element, c);
        conteneur.add(element);
    }

    public void xyPosition(Container conteneur, Component element, int x, int y, int gridwidth, double weightx)
    {
        if(c == null)
            initGridBagConstraint();
        c.gridx = x;
        c.gridy = y;
        c.gridwidth = gridwidth;
        c.weightx = weightx;
        ((GridBagLayout)conteneur.getLayout()).setConstraints(element, c);
        conteneur.add(element);
    }

    public Frame getFrame()
    {
        return ((GetFrame)getParent()).getFrame();
    }
}
