// Decompiled by Jad v1.5.7g. Copyright 2000 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/SiliconValley/Bridge/8617/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi 
// Source File Name:   CardPanel.java

package lattice.graph.onglet;

import java.awt.*;

// Referenced classes of package lattice.graph.onglet:
//            TabGroup

public class CardPanel extends Panel
{

    TabGroup tabs;
    public static final int LEFT = 0;
    public static final int CENTER = 1;
    public static final int RIGHT = 2;
    public static int align = 1;
    public static Color backgroundColor = new Color(160, 160, 180);
    public static boolean paintDownBar = false;

    public CardPanel(String labels[], Component components[])
    {
        super(new BorderLayout());
        if(labels.length == components.length)
        {
            Panel cards = new Panel(new CardLayout());
            for(int i = 0; i < labels.length; i++)
                cards.add(components[i], labels[i]);

            tabs = new TabGroup(labels, cards);
            add("North", tabs);
            add("Center", cards);
        }
    }

    public void setInfo(String s)
    {
        tabs.setInfo(s);
    }

    public void setBgColor(Color c)
    {
        tabs.setBgColor(c);
    }

}
