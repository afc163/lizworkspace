// Decompiled by Jad v1.5.7g. Copyright 2000 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/SiliconValley/Bridge/8617/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi 
// Source File Name:   LatticeViewer.java

package lattice.gui.graph.threeD;

import java.applet.Applet;
import java.awt.*;
import java.io.InputStream;
import java.net.URL;

// Referenced classes of package lattice.gui.graph.threeD:
//            Matrix3D, XYZLatModel

public class LatticeViewer extends Applet
    implements Runnable
{

    XYZLatModel md;
    boolean painted;
    float xfac;
    int prevx;
    int prevy;
    float xtheta;
    float ytheta;
    float scalefudge;
    Matrix3D amat;
    Matrix3D tmat;
    String mdname;
    String message;
    boolean label;
    boolean box;
    boolean bonds;
    Image backBuffer;
    Graphics backGC;
    Dimension backSize;
    String param;
    Color bgcolor;
    Color bondcolor;
    Color boxcolor;

    public LatticeViewer()
    {
        painted = true;
        scalefudge = 1.0F;
        amat = new Matrix3D();
        tmat = new Matrix3D();
        mdname = null;
        message = null;
        label = false;
        box = false;
        bonds = false;
    }

    private synchronized void newBackBuffer()
    {
        backBuffer = createImage(getSize().width, getSize().height);
        backGC = backBuffer.getGraphics();
        backSize = getSize();
    }

    public void init()
    {
        mdname = getParameter("model");
        try
        {
            scalefudge = Float.valueOf(getParameter("scale")).floatValue();
        }
        catch(Exception exception) { }
        try
        {
            label = Boolean.valueOf(getParameter("label")).booleanValue();
        }
        catch(Exception exception1) { }
        try
        {
            box = Boolean.valueOf(getParameter("box")).booleanValue();
        }
        catch(Exception exception2) { }
        try
        {
            bonds = Boolean.valueOf(getParameter("bonds")).booleanValue();
        }
        catch(Exception exception3) { }
        try
        {
            param = getParameter("bgcolor");
        }
        catch(Exception exception4) { }
        try
        {
            Integer i = Integer.valueOf(param, 16);
            bgcolor = new Color(i.intValue());
        }
        catch(NumberFormatException e)
        {
            bgcolor = Color.white;
        }
        try
        {
            param = getParameter("bondcolor");
        }
        catch(Exception exception5) { }
        try
        {
            Integer i = Integer.valueOf(param, 16);
            bondcolor = new Color(i.intValue());
        }
        catch(NumberFormatException e)
        {
            bondcolor = Color.black;
        }
        try
        {
            param = getParameter("boxcolor");
        }
        catch(Exception exception6) { }
        try
        {
            Integer i = Integer.valueOf(param, 16);
            boxcolor = new Color(i.intValue());
        }
        catch(NumberFormatException e)
        {
            boxcolor = Color.red;
        }
        amat.yrot(20D);
        amat.xrot(20D);
        if(mdname == null)
            mdname = "model.obj";
        resize(getSize().width > 20 ? getSize().width : 400, getSize().height > 20 ? getSize().height : 400);
        newBackBuffer();
    }

    public void run()
    {
        InputStream is = null;
        try
        {
            Thread.currentThread().setPriority(1);
            is = (new URL(getDocumentBase(), mdname)).openStream();
            XYZLatModel m = new XYZLatModel(is, label, box, bonds, bgcolor, bondcolor, boxcolor);
            md = m;
            m.findBB();
            float xw = m.xmax - m.xmin;
            float yw = m.ymax - m.ymin;
            float zw = m.zmax - m.zmin;
            if(yw > xw)
                xw = yw;
            if(zw > xw)
                xw = zw;
            float f1 = (float)getSize().width / xw;
            float f2 = (float)getSize().height / xw;
            xfac = 0.7F * (f1 >= f2 ? f2 : f1) * scalefudge;
        }
        catch(Exception e)
        {
            e.printStackTrace();
            md = null;
            message = e.toString();
        }
        try
        {
            if(is != null)
                is.close();
        }
        catch(Exception exception) { }
        repaint();
    }

    public void start()
    {
        if(md == null && message == null)
            (new Thread(this)).start();
    }

    public void stop()
    {
    }

    public boolean mouseDown(Event e, int x, int y)
    {
        prevx = x;
        prevy = y;
        return true;
    }

    public boolean mouseDrag(Event e, int x, int y)
    {
        tmat.unit();
        float xtheta = (float)(prevy - y) * (360F / (float)getSize().width);
        float ytheta = (float)(x - prevx) * (360F / (float)getSize().height);
        tmat.xrot(xtheta);
        tmat.yrot(ytheta);
        amat.mult(tmat);
        if(painted)
        {
            painted = false;
            repaint();
        }
        prevx = x;
        prevy = y;
        return true;
    }

    public void update(Graphics g)
    {
        if(backBuffer == null)
            g.clearRect(0, 0, getSize().width, getSize().height);
        paint(g);
    }

    public void paint(Graphics g)
    {
        if(md != null)
        {
            md.mat.unit();
            md.mat.translate(-(md.xmin + md.xmax) / 2.0F, -(md.ymin + md.ymax) / 2.0F, -(md.zmin + md.zmax) / 2.0F);
            md.mat.mult(amat);
            md.mat.scale(xfac, -xfac, (16F * xfac) / (float)getSize().width);
            md.mat.translate(getSize().width / 2, getSize().height / 2, 8F);
            md.transformed = false;
            if(backBuffer != null)
            {
                if(!backSize.equals(getSize()))
                    newBackBuffer();
                setBackground(Color.white);
                backGC.setColor(bgcolor);
                backGC.fillRect(0, 0, getSize().width, getSize().height);
                md.paint(backGC);
                g.drawImage(backBuffer, 0, 0, this);
            } else
            {
                md.paint(g);
            }
            setPainted();
        } else
        if(message != null)
        {
            g.drawString("Error in model:", 3, 20);
            g.drawString(message, 10, 40);
        }
    }

    private synchronized void setPainted()
    {
        painted = true;
        notifyAll();
    }

    private synchronized void waitPainted()
    {
        while(!painted) 
            try
            {
                wait();
            }
            catch(InterruptedException interruptedexception) { }
        painted = false;
    }
}
