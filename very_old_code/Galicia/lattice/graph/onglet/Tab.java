// Decompiled by Jad v1.5.7g. Copyright 2000 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/SiliconValley/Bridge/8617/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi 
// Source File Name:   Tab.java

package lattice.graph.onglet;

import java.awt.*;
import java.awt.event.MouseEvent;

// Referenced classes of package lattice.graph.onglet:
//            ActionComponent, TabGroup, CardPanel

public class Tab extends ActionComponent
{

    private String label;
    private TabGroup group;
    private int rank;

    public Tab(String s, TabGroup g, int n)
    {
        ActionComponent.margin = TabGroup.margin;
        label = s;
        group = g;
        rank = n;
        setForeground(Color.green);
    }

    String getLabel()
    {
        return label;
    }

    public Dimension getPreferredSize()
    {
        FontMetrics fontMet = getFontMetrics(getFont());
        int width = fontMet.stringWidth(label);
        int height = fontMet.getHeight();
        return new Dimension(ActionComponent.margin * 2 + width + 2 * height, 3 * ActionComponent.margin + 2 * height + 3);
    }

    public void paint(Graphics g)
    {
        paintBackground(g);
        paintLabel(g);
    }

    private void paintBackground(Graphics g)
    {
        int w = getSize().width;
        int h = getSize().height;
        FontMetrics fontMet = getFontMetrics(getFont());
        int lw = fontMet.stringWidth(label);
        int lh = fontMet.getHeight();
        int th = 2 * ActionComponent.margin + lh;
        g.setColor(CardPanel.backgroundColor.darker());
        g.fillRect(1, 1, w, th);
        g.setColor(Color.black);
        g.drawLine(w - 1, 0, w - 1, th);
        g.drawLine(0, th, w - 1, th);
        g.drawLine(0, th + 1, w - 1, th + 1);
        g.setColor(CardPanel.backgroundColor);
        g.drawLine(0, th + 2, w - 1, th + 2);
        g.setColor(CardPanel.backgroundColor.brighter());
        g.drawLine(0, th + 3, w - 1, th + 3);
        g.drawLine(0, th + 4, w - 1, th + 4);
        if(CardPanel.paintDownBar)
            paintDownBar(g, w, h);
        if(state)
        {
            int x[] = {
                0, (ActionComponent.margin + lh) - 1, ActionComponent.margin + lh + lw, w - 1
            };
            int y[] = {
                th, (th + ActionComponent.margin + lh) - 1, (th + ActionComponent.margin + lh) - 1, th
            };
            g.setColor(CardPanel.backgroundColor.darker());
            g.fillPolygon(x, y, 4);
            g.setColor(group.getBgColor());
            g.drawLine(x[0], y[0], x[1], y[1]);
            g.setColor(Color.black);
            g.drawLine(x[0], y[0] + 1, x[1], y[1] + 1);
            g.drawLine(x[1], y[1], x[2], y[2]);
            g.drawLine(x[1], y[1] + 1, x[2], y[2] + 1);
            g.drawLine(x[2], y[2], x[3], y[3]);
            g.drawLine(x[2], y[2] + 1, x[3], y[3] + 1);
            g.setColor(CardPanel.backgroundColor);
            g.drawLine(x[1] + 5, y[1] + 2, x[2], y[2] + 2);
            g.drawLine(x[2], y[2] + 2, x[3], y[3] + 2);
            g.setColor(CardPanel.backgroundColor.brighter());
            g.drawLine(x[1] + 6, y[1] + 3, x[2], y[2] + 3);
            g.drawLine(x[2], y[2] + 3, x[3], y[3] + 3);
            g.drawLine(x[1] + 7, y[1] + 4, x[2], y[2] + 4);
            g.drawLine(x[2], y[2] + 4, x[3], y[3] + 4);
        }
    }

    protected void paintDownBar(Graphics g, int w, int h)
    {
        g.setColor(CardPanel.backgroundColor.brighter());
        g.drawLine(0, h - 4, w - 1, h - 4);
        g.drawLine(0, h - 3, w - 1, h - 3);
        g.setColor(Color.gray);
        g.drawLine(0, h - 2, w - 1, h - 2);
        g.setColor(Color.black);
        g.drawLine(0, h - 1, w - 1, h - 1);
    }

    protected void paintLabel(Graphics g)
    {
        FontMetrics fontMet = getFontMetrics(getFont());
        int lh = fontMet.getHeight();
        int trans = state ? (lh + ActionComponent.margin) - 2 : 0;
        int width = getSize().width;
        g.setColor(state ? getForeground() : group.getBgColor());
        g.drawString(label, (width - fontMet.stringWidth(label)) / 2, (trans + (lh + ActionComponent.margin)) - fontMet.getMaxDescent());
    }

    public void processMouseEvent(MouseEvent e)
    {
        switch(e.getID())
        {
        case 501: 
            group.select(rank);
            break;
        }
        super.processMouseEvent(e);
    }
}
