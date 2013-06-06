// Decompiled by Jad v1.5.7g. Copyright 2000 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/SiliconValley/Bridge/8617/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi 
// Source File Name:   MainDesktop.java

package lattice.gui;

import java.awt.*;
import java.io.PrintStream;
import javax.swing.JComponent;
import javax.swing.JScrollPane;

public class MainDesktop extends JScrollPane
{

    public static final String bgPicturePath = "ressources/Galicia.png";
    public Color bgColor;
    public Image backgroundPicture;

    public MainDesktop()
    {
        bgColor = new Color(200, 200, 220);
        setBackground(bgColor);
        initBgPicture();
    }

    public void initBgPicture()
    {
        try
        {
            backgroundPicture = getToolkit().getImage("ressources/Galicia.png");
        }
        catch(Exception e)
        {
            System.out.println(e);
        }
    }

    public void paint(Graphics g)
    {
        super.paint(g);
        if(backgroundPicture != null)
        {
            Dimension d = getSize();
            int x = (d.width - backgroundPicture.getWidth(this)) / 2;
            int y = (d.height - backgroundPicture.getHeight(this)) / 2;
            g.drawImage(backgroundPicture, x, y, this);
        }
    }
}
