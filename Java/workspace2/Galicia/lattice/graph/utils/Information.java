// Decompiled by Jad v1.5.7g. Copyright 2000 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/SiliconValley/Bridge/8617/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi 
// Source File Name:   Information.java

package lattice.graph.utils;

import java.awt.*;
import java.util.StringTokenizer;
import java.util.Vector;

public class Information
{

    public static final int LEFT = 0;
    public static final int RIGHT = 1;
    public static final int CENTER = 2;
    public static final int UP = 0;
    public static final int BOTTOM = 1;
    public Color bgColor;
    public Color fgColor;
    protected String info;
    protected Vector vInfo;
    protected int indexMax;
    protected int position;
    protected int vAlign;
    protected int alignType;
    protected boolean cadre;
    protected boolean affInfo;
    protected boolean firstLineBold;
    protected boolean outlined;

    public Information(String info)
    {
        bgColor = Color.black;
        fgColor = Color.white;
        position = 0;
        vAlign = 1;
        alignType = 0;
        cadre = true;
        affInfo = false;
        firstLineBold = false;
        outlined = true;
        this.info = info;
        initVInfo();
    }

    public Information(String info, int align)
    {
        this(info);
        alignType = align;
    }

    public Information(String info, int align, int vAlign)
    {
        this(info);
        alignType = align;
        this.vAlign = vAlign;
    }

    public Information(String info, int align, int vAlign, Color bg, Color fg)
    {
        this(info, align, vAlign);
        bgColor = bg;
        fgColor = fg;
    }

    public Information(String info, int align, int vAlign, Color bg, Color fg, boolean outlined)
    {
        this(info, align, vAlign, bg, fg);
        this.outlined = outlined;
    }

    public void setInfo(String s)
    {
        info = s;
        if(s.equals(""))
            vInfo = null;
        else
            initVInfo();
    }

    public void setAlignType(int a)
    {
        alignType = a;
    }

    public void setPosition(int a)
    {
        position = a;
    }

    public void setvAlign(int a)
    {
        vAlign = a;
    }

    public void setFirstLineBold(boolean b)
    {
        firstLineBold = b;
    }

    protected void initVInfo()
    {
        int w = 0;
        indexMax = 0;
        vInfo = new Vector();
        StringTokenizer st = new StringTokenizer(info, "\n");
        int i = 0;
        String s = null;
        while(st.hasMoreTokens()) 
        {
            s = st.nextToken();
            if(s != null)
            {
                vInfo.addElement(s);
                int wCourant = s.length();
                if(wCourant > w)
                {
                    w = wCourant;
                    indexMax = i;
                }
                i++;
            }
        }
        if(i == 0)
            vInfo = null;
    }

    public String getInfo()
    {
        return info;
    }

    public void setAffInfo(boolean b)
    {
        affInfo = b;
    }

    public boolean getOutlined()
    {
        return outlined;
    }

    public void setOutlined(boolean b)
    {
        outlined = b;
    }

    public boolean getAffInfo()
    {
        return affInfo;
    }

    public void paint(Graphics g, Dimension d, Font f)
    {
        if(affInfo && info != null && vInfo != null)
        {
            f = new Font("Geneva", 0, 12);
            FontMetrics fm = g.getFontMetrics(f);
            int wMax = fm.stringWidth((String)vInfo.elementAt(indexMax));
            int h = fm.getHeight();
            g.setColor(bgColor);
            g.setFont(f);
            int posX = 0;
            int posY;
            if(vAlign == 0)
                posY = 5;
            else
                posY = d.height - h * (vInfo.size() + 1);
            for(int i = 0; i < vInfo.size(); i++)
            {
                if(firstLineBold)
                    if(i == 0)
                    {
                        f = new Font("Geneva", 1, 14);
                        fm = g.getFontMetrics(f);
                        g.setFont(f);
                    } else
                    if(i == 1)
                    {
                        f = new Font("Geneva", 0, 12);
                        fm = g.getFontMetrics(f);
                        g.setFont(f);
                    }
                int w = fm.stringWidth((String)vInfo.elementAt(i));
                switch(position)
                {
                case 0: // '\0'
                    posX = 10;
                    break;

                case 1: // '\001'
                    posX = d.width - wMax - 10;
                    break;

                case 2: // '\002'
                    posX = (d.width - w) / 2;
                    break;

                default:
                    posX = 10;
                    break;
                }
                switch(alignType)
                {
                case 1: // '\001'
                    posX = d.width - 10 - w;
                    break;

                case 2: // '\002'
                    posX += (wMax - w) / 2;
                    break;
                }
                if(outlined)
                {
                    g.setColor(bgColor);
                    g.drawString((String)vInfo.elementAt(i), posX + 1, posY + h * (i + 1));
                    g.drawString((String)vInfo.elementAt(i), posX - 1, posY + h * (i + 1));
                    g.drawString((String)vInfo.elementAt(i), posX, posY + h * (i + 1) + 1);
                    g.drawString((String)vInfo.elementAt(i), posX, (posY + h * (i + 1)) - 1);
                }
                g.setColor(fgColor);
                g.drawString((String)vInfo.elementAt(i), posX, posY + h * (i + 1));
            }

        }
    }
}
