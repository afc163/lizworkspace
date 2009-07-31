// Decompiled by Jad v1.5.7g. Copyright 2000 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/SiliconValley/Bridge/8617/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi 
// Source File Name:   Composant.java

package lattice.graph.trees;

import java.awt.*;
import java.util.Observable;

public abstract class Composant extends Observable
{

    protected static Dimension shadowSize = new Dimension(2, 2);
    protected static Color cible_color;
    protected static Color shadow_color = new Color(102, 102, 204);
    protected String label;
    protected boolean show;
    protected int widthLabel;
    protected int heightLabel;
    protected Color labelColor;
    protected Color bgColor;
    protected double x;
    protected double y;
    protected double z;
    protected Dimension dimension;

    Composant()
    {
        show = true;
        dimension = new Dimension(100, 100);
    }

    Composant(String nom)
    {
        show = true;
        dimension = new Dimension(100, 100);
        label = nom;
    }

    public String getLabel()
    {
        return label;
    }

    public void setLabel(String l)
    {
        label = l;
    }

    public boolean showed()
    {
        return show;
    }

    public void showLabel()
    {
        show = true;
    }

    public void hideLabel()
    {
        show = false;
    }

    public int widthLabel()
    {
        return widthLabel;
    }

    public int heightLabel()
    {
        return heightLabel;
    }

    public void setWidthLabel(int w)
    {
        widthLabel = w;
    }

    public void setHeightLabel(int h)
    {
        heightLabel = h;
    }

    public Color labelColor()
    {
        return labelColor;
    }

    public void setLabelColor(Color c)
    {
        labelColor = c;
    }

    public Color bgColor()
    {
        return bgColor;
    }

    public void setBgColor(Color c)
    {
        bgColor = c;
    }

    public Point pos()
    {
        return new Point((int)x, (int)y);
    }

    public int x()
    {
        return (int)x;
    }

    public void setX(int x)
    {
        this.x = x;
    }

    public int y()
    {
        return (int)y;
    }

    public void setY(int y)
    {
        this.y = y;
    }

    public void setX(double x)
    {
        this.x = x;
    }

    public void setY(double y)
    {
        this.y = y;
    }

    public double xd()
    {
        return x;
    }

    public double yd()
    {
        return y;
    }

    public int z()
    {
        return (int)z;
    }

    public double zd()
    {
        return z;
    }

    public void setZ(double z)
    {
        this.z = z;
    }

    public void setZ(int z)
    {
        this.z = z;
    }

    public void setPos(Point p)
    {
        x = p.x;
        y = p.y;
    }

    public void setPos(int x, int y)
    {
        this.x = x;
        this.y = y;
    }

    public void setPos(double x, double y)
    {
        this.x = x;
        this.y = y;
    }

    public Dimension dimension()
    {
        return dimension;
    }

    public void setDimension(Dimension dim)
    {
        dimension = dim;
    }

    public int height()
    {
        return dimension.height;
    }

    public void setHeight(int h)
    {
        dimension.height = h;
    }

    public int width()
    {
        return dimension.width;
    }

    public void setWidth(int w)
    {
        dimension.width = w;
    }

    public abstract Rectangle rect();

    public boolean sourisDans(int x, int y)
    {
        return rect().contains(x, y);
    }

    public boolean dansRect(Rectangle r)
    {
        return rect().intersects(r);
    }

    public String getInfo()
    {
        return getLabel();
    }

    public String toString()
    {
        return getLabel();
    }

    public void paintShadow(Graphics g1, int i, int j)
    {
    }

    static 
    {
        cible_color = Color.red;
    }
}
