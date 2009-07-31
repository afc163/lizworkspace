// Decompiled by Jad v1.5.7g. Copyright 2000 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/SiliconValley/Bridge/8617/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi 
// Source File Name:   ZoomInterface.java

package lattice.graph.zoom;

import java.awt.*;

// Referenced classes of package lattice.graph.zoom:
//            ZoomViewer

public interface ZoomInterface
{

    public abstract Color getBackground();

    public abstract Rectangle dimension();

    public abstract int getX();

    public abstract int getY();

    public abstract void setX(int i);

    public abstract void setY(int i);

    public abstract void setPosX(int i);

    public abstract void setPosY(int i);

    public abstract int getPosX();

    public abstract int getPosY();

    public abstract Dimension getSize();

    public abstract void paintOffscreen(Graphics g, Rectangle rectangle);

    public abstract void paintOffscreen(Graphics g, int i, int j, float f);

    public abstract void dragMode();

    public abstract void recentre(int i, int j);

    public abstract void deplacer(int i, int j);

    public abstract boolean mouseUp(int i, int j);

    public abstract void setZoomViewer(ZoomViewer zoomviewer);
}
