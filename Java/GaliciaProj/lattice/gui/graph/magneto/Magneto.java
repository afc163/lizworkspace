// Decompiled by Jad v1.5.7g. Copyright 2000 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/SiliconValley/Bridge/8617/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi 
// Source File Name:   Magneto.java

package lattice.gui.graph.magneto;

import java.awt.Component;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.io.PrintStream;
import java.util.*;
import lattice.gui.graph.LatticeGraphViewer;

// Referenced classes of package lattice.gui.graph.magneto:
//            ActionObject, Magnetable, Magnetor

public class Magneto extends Thread
    implements MouseMotionListener
{

    public LatticeGraphViewer lgv;
    public long timeSleep;
    public boolean active;
    public boolean magnet;
    public double tensionFactor;
    public double repulsionFactor;
    public boolean fixFirstLevel;
    public boolean fixLastLevel;
    public boolean magnetRelation;
    public boolean magnetMouse;
    public float rotation;
    protected Vector vAction;
    protected Magnetable top;
    protected Magnetable bottom;

    public Magneto(LatticeGraphViewer lgv)
    {
        timeSleep = 50L;
        active = false;
        magnet = false;
        tensionFactor = 10D;
        repulsionFactor = 0.5D;
        fixFirstLevel = false;
        fixLastLevel = false;
        magnetRelation = false;
        magnetMouse = false;
        rotation = 0.0F;
        this.lgv = lgv;
    }

    public boolean getMagnet()
    {
        return magnet;
    }

    public void setMagnet(boolean b)
    {
        magnet = b;
        if(b)
            active = true;
    }

    public double getRepulsionFactor()
    {
        return repulsionFactor;
    }

    public void setRepulsionFactor(double d)
    {
        repulsionFactor = d;
    }

    public double getTensionFactor()
    {
        return tensionFactor;
    }

    public void setTensionFactor(double d)
    {
        tensionFactor = d;
    }

    public long getTimeSleep()
    {
        return timeSleep;
    }

    public void setTimeSleep(long l)
    {
        timeSleep = l;
    }

    public boolean fixFirstLevel()
    {
        return fixFirstLevel;
    }

    public void setFixFirstLevel(boolean b)
    {
        ((ActionObject)vAction.get(2)).fix(b);
        fixFirstLevel = b;
    }

    public boolean fixLastLevel()
    {
        return fixLastLevel;
    }

    public void setFixLastLevel(boolean b)
    {
        ((ActionObject)vAction.get(vAction.size() - 3)).fix(b);
        fixLastLevel = b;
    }

    public boolean magnetRelation()
    {
        return magnetRelation;
    }

    public void setMagnetRelation(boolean b)
    {
        magnetRelation = b;
    }

    public boolean magnetMouse()
    {
        return magnetMouse;
    }

    public void setMagnetMouse(boolean b)
    {
        magnetMouse = b;
    }

    public float getRotation()
    {
        return rotation;
    }

    public void setRotation(float val)
    {
        rotation = val;
        if(rotation != 0.0F)
            active = true;
    }

    public void run()
    {
        do
        {
            boolean repaint;
            do
            {
                do
                    try
                    {
                        Thread.sleep(timeSleep);
                    }
                    catch(InterruptedException e)
                    {
                        System.out.println("Magneto Interruption");
                    }
                while(!active);
                if(vAction == null)
                    buildvNiveau();
                repaint = false;
                if(lgv.getThreeD() && rotation != 0.0F)
                {
                    lgv.rotation(rotation);
                    repaint = true;
                }
                synchronized(lgv)
                {
                    if(incAction())
                        repaint = true;
                }
            } while(!repaint);
            synchronized(lgv)
            {
                lgv.repaint();
            }
        } while(true);
    }

    public void stopper()
    {
        active = false;
    }

    public boolean incAction()
    {
        boolean deplacement = false;
        if(lgv.getNbNiveau() > 2)
        {
            for(Iterator i = vAction.iterator(); i.hasNext();)
            {
                ActionObject ao = (ActionObject)i.next();
                if(ao.doAction(lgv.getThreeD()))
                    deplacement = true;
            }

        }
        return deplacement;
    }

    public ActionObject getNiveau(int i)
    {
        return (ActionObject)vAction.elementAt(i);
    }

    public int getcLargeur(int i)
    {
        return lgv.getcLargeur(i);
    }

    public int getcHauteur()
    {
        return lgv.getcHauteur();
    }

    public void buildvNiveau()
    {
        vAction = new Vector();
        Vector v1 = lgv.getNiveau();
        Vector v2 = lgv.getNiveauRelation();
        top = (Magnetable)((Vector)v1.elementAt(0)).elementAt(0);
        top.setIsMagnetable(false);
        bottom = (Magnetable)((Vector)v1.elementAt(v1.size() - 1)).elementAt(0);
        bottom.setIsMagnetable(false);
        Magnetor ml = null;
        int nbLevel = v1.size();
        for(int i = 0; i < nbLevel; i++)
            vAction.add(new Magnetor(this, i, nbLevel, (Vector)v1.elementAt(i), (Vector)v2.elementAt(i)));

    }

    public void mouseMoved(MouseEvent e)
    {
        if(vAction != null)
        {
            for(int i = 0; i < vAction.size(); i++)
                ((ActionObject)vAction.elementAt(i)).setMousePosition(e.getX(), e.getY());

        }
    }

    public void mouseDragged(MouseEvent mouseevent)
    {
    }
}
