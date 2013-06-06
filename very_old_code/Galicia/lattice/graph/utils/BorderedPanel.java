// Decompiled by Jad v1.5.7g. Copyright 2000 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/SiliconValley/Bridge/8617/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi 
// Source File Name:   BorderedPanel.java

package lattice.graph.utils;

import java.awt.*;
import javax.swing.JComponent;
import javax.swing.JPanel;

public class BorderedPanel extends JPanel
{

    protected String label;
    protected Color fgColor;
    public GridBagConstraints c;

    public BorderedPanel(String label)
    {
        this.label = label;
        setOpaque(false);
        setLayout(new FlowLayout(1, 15, 15));
    }

    public BorderedPanel(String label, Color c)
    {
        this(label);
        setOpaque(false);
        fgColor = c;
    }

    public BorderedPanel(String label, Color c, int w, int h)
    {
        setOpaque(false);
        this.label = label;
        fgColor = c;
        setLayout(new FlowLayout(1, w, h));
    }

    public synchronized void paint(Graphics g)
    {
        int w = getSize().width;
        int h = getSize().height;
        if(fgColor != null)
            g.setColor(fgColor);
        else
            g.setColor(getBackground().brighter());
        if(label == null || label.equals(""))
        {
            g.drawLine(0, 0, w - 1, 0);
            g.drawLine(0, 0, 0, h - 1);
            g.drawLine(w - 1, 0, w - 1, h - 1);
            g.drawLine(0, h - 1, w - 1, h - 1);
        } else
        {
            FontMetrics fm = g.getFontMetrics();
            int decalX = 10;
            int decalY = fm.getAscent() + 3;
            g.drawString(label, decalX, decalY);
            decalY = decalY / 2 + 2;
            g.drawLine(0, decalY, decalX - 2, decalY);
            g.drawLine(fm.stringWidth(label) + 12, decalY, w - 1, decalY);
            g.drawLine(0, decalY, 0, h - 1);
            g.drawLine(w - 1, decalY, w - 1, h - 1);
            g.drawLine(0, h - 1, w - 1, h - 1);
        }
        super.paint(g);
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
}
