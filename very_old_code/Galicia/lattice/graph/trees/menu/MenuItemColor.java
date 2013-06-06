// Decompiled by Jad v1.5.7g. Copyright 2000 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/SiliconValley/Bridge/8617/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi 
// Source File Name:   MenuItemColor.java

package lattice.graph.trees.menu;

import java.awt.Color;
import java.awt.MenuItem;

public class MenuItemColor extends MenuItem
{

    public Color color;

    public MenuItemColor(String label, Color c)
    {
        super(label);
        color = c;
    }

    public Color getColor()
    {
        return color;
    }
}
