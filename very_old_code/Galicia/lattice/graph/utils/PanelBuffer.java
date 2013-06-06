// Decompiled by Jad v1.5.7g. Copyright 2000 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/SiliconValley/Bridge/8617/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi 
// Source File Name:   PanelBuffer.java

package lattice.graph.utils;

import java.awt.*;
import java.io.PrintStream;
import javax.swing.JComponent;

// Referenced classes of package lattice.graph.utils:
//            IkbsPanel

public class PanelBuffer extends IkbsPanel
{

    protected Image offscreen;
    protected Dimension offscreensize;
    protected Graphics offgraphics;
    protected boolean doubleBuffer;

    public PanelBuffer()
    {
        doubleBuffer = true;
    }

    public Image offscreen()
    {
        return offscreen;
    }

    public boolean getDoubleBuffer()
    {
        return doubleBuffer;
    }

    public void setDoubleBuffer(boolean b)
    {
        doubleBuffer = b;
    }

    protected void initOffGraphics(Graphics g, Dimension d)
    {
        if(offscreen == null || d.width != offscreensize.width || d.height != offscreensize.height)
        {
            offscreen = createImage(d.width, d.height);
            offscreensize = d;
            offgraphics = offscreen.getGraphics();
            offgraphics.setFont(getFont());
        }
    }

    public synchronized void update(Graphics g)
    {
        if(doubleBuffer)
        {
            Dimension d = getSize();
            drawZoom(g, d);
            initOffGraphics(g, d);
            if(g == null)
                System.out.println("g est null");
            paint(offgraphics);
            g.drawImage(offscreen, 0, 0, this);
        } else
        {
            super.update(g);
        }
    }

    public void drawZoom(Graphics g1, Dimension dimension)
    {
    }

    public void dispose()
    {
        if(offscreen != null)
            offscreen.flush();
        if(offgraphics != null)
            offgraphics.dispose();
        System.gc();
    }

    public void setBounds(int x, int y, int w, int h)
    {
        offgraphics = null;
        super.setBounds(x, y, w, h);
    }
}
