// Decompiled by Jad v1.5.7g. Copyright 2000 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/SiliconValley/Bridge/8617/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi 
// Source File Name:   Rectangle3D.java

package lattice.graph.utils;

import java.awt.Color;
import java.awt.Graphics;

public class Rectangle3D
{

    public static final int IN = 0;
    public static final int OUT = 1;
    public static final int BORDER_IN = 2;
    public static final int BORDER_OUT = 3;
    int mode;
    Color color;
    int w;
    int h;
    int x;
    int y;

    public Rectangle3D(Color c, int X, int Y, int width, int height)
    {
        mode = 0;
        x = X;
        y = Y;
        w = width;
        h = height;
        color = c;
    }

    public void setDrawingMode(int m)
    {
        mode = m;
    }

    public void setColor(Color c)
    {
        color = c;
    }

    public static int borderWidthOfMode(int m)
    {
        return 2;
    }

    public void setWidth(int w)
    {
        this.w = w;
    }

    public void setHeight(int h)
    {
        this.h = h;
    }

    public void setX(int x)
    {
        this.x = x;
    }

    public void setY(int y)
    {
        this.y = y;
    }

    public synchronized void paint(Graphics g)
    {
        Color c = color;
        Color darker = c.darker();
        Color brighter = c.brighter();
        Color c1;
        Color c2;
        Color c3;
        Color c4;
        Color c5;
        if(mode == 0)
        {
            c1 = darker;
            c2 = brighter;
            c3 = c;
            c4 = Color.black;
            c5 = darker;
        } else
        if(mode == 1)
        {
            c3 = darker;
            c2 = darker;
            c4 = c;
            c1 = brighter;
            c5 = c;
        } else
        if(mode == 3)
        {
            c4 = darker;
            c2 = darker;
            c1 = brighter;
            c3 = brighter;
            c5 = c;
        } else
        {
            c1 = darker;
            c2 = brighter;
            c3 = c1;
            c4 = brighter;
            c5 = c;
        }
        g.setColor(c1);
        g.drawLine(x, y, (x + w) - 1, y);
        g.drawLine(x, y, x, (y + h) - 1);
        g.setColor(c4);
        g.drawLine(x + 1, y + 1, (x + w) - 2, y + 1);
        g.drawLine(x + 1, y + 1, x + 1, (y + h) - 2);
        g.setColor(c2);
        g.drawLine(x, (y + h) - 1, (x + w) - 1, (y + h) - 1);
        g.drawLine((x + w) - 1, y, (x + w) - 1, (y + h) - 1);
        g.setColor(c3);
        g.drawLine(x + 1, (y + h) - 2, (x + w) - 2, (y + h) - 2);
        g.drawLine((x + w) - 2, y + 1, (x + w) - 2, (y + h) - 2);
        g.setColor(c5);
        g.fillRect(x + 2, y + 2, w - 4, h - 4);
    }
}
