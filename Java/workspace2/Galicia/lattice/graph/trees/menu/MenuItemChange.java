// Decompiled by Jad v1.5.7g. Copyright 2000 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/SiliconValley/Bridge/8617/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi 
// Source File Name:   MenuItemChange.java

package lattice.graph.trees.menu;

import java.awt.MenuItem;

public class MenuItemChange extends MenuItem
{

    String label1;
    String label2;
    boolean state;

    public MenuItemChange(String label1, String label2)
    {
        super(label1);
        state = true;
        this.label1 = label1;
        this.label2 = label2;
    }

    public void changeLabel()
    {
        state = !state;
        if(state)
            setLabel(label1);
        else
            setLabel(label2);
    }

    public boolean getState()
    {
        return state;
    }

    public void setState(boolean b)
    {
        state = b;
        if(state)
            setLabel(label1);
        else
            setLabel(label2);
    }
}
