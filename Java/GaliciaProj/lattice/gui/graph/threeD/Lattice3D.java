// Decompiled by Jad v1.5.7g. Copyright 2000 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/SiliconValley/Bridge/8617/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi 
// Source File Name:   Lattice3D.java

package lattice.gui.graph.threeD;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.io.*;
import java.net.URL;
import java.util.Vector;
import lattice.graph.trees.GraphViewer;
import lattice.gui.graph.LatticeGraphViewer;
import lattice.gui.graph.LatticeNodeGraph;

// Referenced classes of package lattice.gui.graph.threeD:
//            Matrix3D, XYZLatModel

public class Lattice3D
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
    Graphics backGC;
    Dimension backSize;
    String param;
    Color bgcolor;
    Color bondcolor;
    Color boxcolor;
    LatticeGraphViewer lgv;
    int decalX;
    int decalY;

    public Lattice3D(LatticeGraphViewer lgv)
    {
        painted = true;
        scalefudge = 1.0F;
        amat = new Matrix3D();
        tmat = new Matrix3D();
        mdname = null;
        message = null;
        label = true;
        box = false;
        bonds = true;
        bgcolor = Color.white;
        bondcolor = Color.black;
        boxcolor = Color.darkGray;
        decalX = 0;
        decalY = 0;
        this.lgv = lgv;
        init();
    }

    public Dimension getSize()
    {
        return lgv.getSize();
    }

    public void init()
    {
        amat.yrot(20D);
        amat.xrot(20D);
        if(mdname == null)
            mdname = "model.obj";
    }

    public void run()
    {
        buildLattice3D(lgv.getNiveau());
    }

    public void buildLattice3D(Vector vNiveau)
    {
        XYZLatModel m = null;
        try
        {
            m = new XYZLatModel(lgv, vNiveau, label, box, bonds, Color.white, bondcolor, boxcolor);
        }
        catch(Exception e)
        {
            System.out.println("Unexpected error to build 3D lattice");
        }
        initXYZLatModel(m);
    }

    public void initXYZLatModel(XYZLatModel m)
    {
        LatticeNodeGraph.setApplet(lgv);
        md = m;
        m.findBB();
        initScale();
        lgv.repaint();
    }

    public void initScale()
    {
        float xw = md.xmax - md.xmin;
        float yw = md.ymax - md.ymin;
        float zw = md.zmax - md.zmin;
        if(yw > xw)
            xw = yw;
        if(zw > xw)
            xw = zw;
        float f1 = (float)getSize().width / xw;
        float f2 = (float)getSize().height / xw;
        xfac = 0.9F * (f1 >= f2 ? f2 : f1) * scalefudge * ((float)lgv.getZoom() / 9F);
    }

    public void loadLattice()
    {
        InputStream is = null;
        try
        {
            Thread.currentThread().setPriority(1);
            URL url = (new File("exempleLat3D/buckminsterfullerine.lat")).toURL();
            System.out.println(url);
            is = url.openStream();
            XYZLatModel m = new XYZLatModel(is, label, box, bonds, bgcolor, bondcolor, boxcolor);
            initXYZLatModel(m);
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
    }

    public void start()
    {
        if(md == null && message == null)
            (new Thread(this)).start();
    }

    public void stop()
    {
    }

    public void mouseDown(MouseEvent e)
    {
        int x = e.getX();
        int y = e.getY();
        prevx = x;
        prevy = y;
    }

    public void mouseDragged(MouseEvent e)
    {
        int x = e.getX();
        int y = e.getY();
        tmat.unit();
        float xtheta = (float)(prevy - y) * (360F / (float)getSize().width);
        float ytheta = (float)(x - prevx) * (360F / (float)getSize().height);
        tmat.xrot(xtheta);
        tmat.yrot(ytheta);
        amat.mult(tmat);
        if(painted)
        {
            painted = false;
            lgv.repaint();
        }
        prevx = x;
        prevy = y;
    }

    public void rotation(float val)
    {
        tmat.unit();
        float ytheta = val;
        tmat.yrot(ytheta);
        amat.mult(tmat);
        if(painted)
            painted = false;
    }

    public void update(Graphics g)
    {
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
            initScale();
            md.mat.scale(xfac, -xfac, (16F * xfac) / (float)getSize().width);
            md.mat.translate(getSize().width / 2, getSize().height / 2, 8F);
            md.transformed = false;
            g.setColor(Color.white);
            g.fillRect(0, 0, getSize().width, getSize().height);
            md.paint(g);
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
