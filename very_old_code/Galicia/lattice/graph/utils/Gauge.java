// Decompiled by Jad v1.5.7g. Copyright 2000 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/SiliconValley/Bridge/8617/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi 
// Source File Name:   Gauge.java

package lattice.graph.utils;

import java.awt.*;

// Referenced classes of package lattice.graph.utils:
//            Rectangle3D

public class Gauge extends Component
{

    int current;
    int total;
    int Height;
    int Width;
    protected Rectangle3D r3D;
    protected Color gaugeColor;

    public Gauge()
    {
        this(Color.blue);
    }

    public Gauge(Color gaugeColor)
    {
        current = 0;
        total = 100;
        Height = 18;
        Width = 250;
        setBackground(Color.white);
        this.gaugeColor = gaugeColor;
    }

    public void paint(Graphics g)
    {
        r3D = new Rectangle3D(Color.lightGray, 0, 0, getSize().width, getSize().height);
        r3D.setDrawingMode(0);
        r3D.paint(g);
        int barWidth = (int)(((float)current / (float)total) * (float)getSize().width);
        r3D.setDrawingMode(1);
        r3D.setColor(gaugeColor);
        r3D.setWidth(barWidth - 3);
        r3D.setHeight(getSize().height - 4);
        r3D.setX(1);
        r3D.setY(2);
        r3D.setDrawingMode(1);
        r3D.paint(g);
    }

    public void setCurrentAmount(int Amount)
    {
        current = Amount;
        if(current > 100)
            current = 100;
        repaint();
    }

    public void setColor(Color c)
    {
        gaugeColor = c;
    }

    public int getCurrentAmount()
    {
        return current;
    }

    public int getTotalAmount()
    {
        return total;
    }

    public Dimension getPreferredSize()
    {
        return new Dimension(Width, Height);
    }

    public Dimension getMinimumSize()
    {
        return new Dimension(Width, Height);
    }
}
