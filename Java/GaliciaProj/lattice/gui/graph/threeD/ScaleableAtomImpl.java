// Decompiled by Jad v1.5.7g. Copyright 2000 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/SiliconValley/Bridge/8617/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi 
// Source File Name:   ScaleableAtomImpl.java

package lattice.gui.graph.threeD;

import java.awt.*;
import java.awt.image.IndexColorModel;
import java.awt.image.MemoryImageSource;

// Referenced classes of package lattice.gui.graph.threeD:
//            ScaleableAtom

class ScaleableAtomImpl
    implements ScaleableAtom
{

    private static Component applet;
    private static byte data[];
    private static final int R = 40;
    private static final int hx = 15;
    private static final int hy = 15;
    private static final int bgGrey = 192;
    private static int maxr;
    private int Rl;
    private int Gl;
    private int Bl;
    private double Sf;
    private Image balls[];

    static void setApplet(Component app)
    {
        applet = app;
    }

    ScaleableAtomImpl(int Rl, int Gl, int Bl, double Sf)
    {
        this.Rl = Rl;
        this.Gl = Gl;
        this.Bl = Bl;
        this.Sf = Sf;
    }

    public boolean Exist()
    {
        return Sf != 0.0D;
    }

    public final int blend(int fg, int bg, float fgfactor)
    {
        return (int)((float)bg + (float)(fg - bg) * fgfactor);
    }

    public void Setup(int nBalls)
    {
        balls = new Image[nBalls];
        byte red[] = new byte[256];
        red[0] = -64;
        byte green[] = new byte[256];
        green[0] = -64;
        byte blue[] = new byte[256];
        blue[0] = -64;
        for(int r = 0; r < nBalls; r++)
        {
            float b = (float)(r + 1) / (float)nBalls;
            for(int i = maxr; i >= 1; i--)
            {
                float d = (float)i / (float)maxr;
                red[i] = (byte)blend(blend(Rl, 255, d), 192, b);
                green[i] = (byte)blend(blend(Gl, 255, d), 192, b);
                blue[i] = (byte)blend(blend(Bl, 255, d), 192, b);
            }

            IndexColorModel model = new IndexColorModel(8, maxr + 1, red, green, blue, 0);
            balls[r] = applet.createImage(new MemoryImageSource(80, 80, model, data, 0, 80));
        }

    }

    public void paint(Graphics gc, int x, int y, int r)
    {
        Image ba[] = balls;
        if(ba == null)
        {
            Setup((int)(16D * Sf));
            ba = balls;
        }
        r = (int)((double)r * Sf);
        Image i = ba[r];
        int size = 10 + r;
        gc.drawImage(i, x - (size >> 1), y - (size >> 1), size, size, applet);
    }

    static 
    {
        data = new byte[6400];
        int mr = 0;
        for(int Y = 80; --Y >= 0;)
        {
            int x0 = (int)(Math.sqrt(1600 - (Y - 40) * (Y - 40)) + 0.5D);
            int p = (Y * 80 + 40) - x0;
            for(int X = -x0; X < x0; X++)
            {
                int x = X + 15;
                int y = (Y - 40) + 15;
                int r = (int)(Math.sqrt(x * x + y * y) + 0.5D);
                if(r > mr)
                    mr = r;
                data[p++] = r > 0 ? (byte)r : 1;
            }

        }

        maxr = mr;
    }
}
