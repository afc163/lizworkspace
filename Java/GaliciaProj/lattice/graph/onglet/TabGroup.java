// Decompiled by Jad v1.5.7g. Copyright 2000 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/SiliconValley/Bridge/8617/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi 
// Source File Name:   TabGroup.java

package lattice.graph.onglet;

import java.awt.*;

// Referenced classes of package lattice.graph.onglet:
//            Tab, ActionComponent, CardPanel

public class TabGroup extends Panel
{

    public static int margin = 2;
    private Panel target;
    private int selected;
    private String label;
    private Color bgColor;

    public TabGroup(String labels[], Panel p)
    {
        super(new FlowLayout(0, 0, 0));
        bgColor = Color.white;
        setBackground(Color.white);
        target = p;
        for(int i = 0; i < labels.length; i++)
            add(new Tab(labels[i], this, i));

        label = new String("");
        selected = 0;
        getTab(selected).select();
    }

    private Tab getTab(int n)
    {
        return (Tab)getComponent(n);
    }

    void select(int newSelected)
    {
        getTab(selected).unselect();
        selected = newSelected;
        getTab(selected).select();
        ((CardLayout)target.getLayout()).show(target, getTab(selected).getLabel());
    }

    public void paint(Graphics g)
    {
        paintBackground(g);
        super.paint(g);
    }

    private void paintBackground(Graphics g)
    {
        int w = getSize().width;
        int h = getSize().height;
        FontMetrics fontMet = getFontMetrics(getFont());
        int lh = fontMet.getHeight();
        int th = 2 * margin + lh;
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
        int xEnd = 0;
        g.setColor(getBackground());
        g.drawLine(xEnd, 0, xEnd, th - 1);
        if(CardPanel.paintDownBar)
            paintDownBar(g, w, h);
        afficheLabel(g);
    }

    public void setBgColor(Color c)
    {
        bgColor = c;
        setBackground(c);
    }

    public Color getBgColor()
    {
        return bgColor;
    }

    public void afficheLabel(Graphics g)
    {
        try
        {
            g.setColor(bgColor);
            FontMetrics fontMet = getFontMetrics(getFont());
            int lh = fontMet.getHeight();
            g.drawString(label, comLargeur() + 4, (lh + margin) - fontMet.getMaxDescent());
        }
        catch(NullPointerException nullpointerexception) { }
    }

    public void effacerLabel(Graphics g)
    {
        try
        {
            label = "";
            paintBackground(g);
        }
        catch(NullPointerException nullpointerexception) { }
    }

    public void setInfo(String s)
    {
        effacerLabel(getGraphics());
        label = s;
        afficheLabel(getGraphics());
    }

    public int comLargeur()
    {
        int l = 0;
        Component components[] = getComponents();
        for(int i = 0; i < components.length; i++)
            l += components[i].getPreferredSize().width;

        return l;
    }

    private void paintDownBar(Graphics g, int w, int h)
    {
        g.setColor(CardPanel.backgroundColor.brighter());
        g.drawLine(0, h - 4, w - 1, h - 4);
        g.drawLine(0, h - 3, w - 1, h - 3);
        g.setColor(Color.gray);
        g.drawLine(0, h - 2, w - 1, h - 2);
        g.setColor(Color.black);
        g.drawLine(0, h - 1, w - 1, h - 1);
    }

}
