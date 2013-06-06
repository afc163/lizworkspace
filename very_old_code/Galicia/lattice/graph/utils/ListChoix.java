// Decompiled by Jad v1.5.7g. Copyright 2000 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/SiliconValley/Bridge/8617/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi 
// Source File Name:   ListChoix.java

package lattice.graph.utils;

import java.awt.Component;
import java.awt.List;
import java.awt.event.*;

// Referenced classes of package lattice.graph.utils:
//            ChoixComponent, InfoListener

public class ListChoix extends List
    implements ChoixComponent, MouseListener
{

    int choix;
    InfoListener ibm;
    String info;

    public ListChoix(ActionListener listener, int choix)
    {
        this.choix = choix;
        addActionListener(listener);
        addMouseListener(this);
        if(listener instanceof InfoListener)
            setListener((InfoListener)listener);
    }

    public ListChoix(int choix, ItemListener listener)
    {
        this.choix = choix;
        addItemListener(listener);
        addMouseListener(this);
        if(listener instanceof InfoListener)
            setListener((InfoListener)listener);
    }

    public ListChoix(int rows, boolean mul, ActionListener listener, int choix)
    {
        super(rows, mul);
        this.choix = choix;
        addActionListener(listener);
        addMouseListener(this);
        if(listener instanceof InfoListener)
            setListener((InfoListener)listener);
    }

    public ListChoix(int rows, boolean mul, int choix, ItemListener listener)
    {
        super(rows, mul);
        this.choix = choix;
        addItemListener(listener);
        addMouseListener(this);
        if(listener instanceof InfoListener)
            setListener((InfoListener)listener);
    }

    public void setChoix(int choix)
    {
        this.choix = choix;
    }

    public int getChoix()
    {
        return choix;
    }

    public InfoListener getListener()
    {
        return ibm;
    }

    public void setListener(InfoListener listener)
    {
        ibm = listener;
    }

    public String getInfo()
    {
        return info;
    }

    public void setInfo(String info)
    {
        this.info = info;
    }

    public void afficherInfo()
    {
        if(getInfo() != null && getListener() != null)
            getListener().setInfo(getInfo());
    }

    public void mouseEntered(MouseEvent e)
    {
        afficherInfo();
    }

    public void mouseExited(MouseEvent e)
    {
        if(getInfo() != null && getListener() != null)
            getListener().removeInfo();
    }

    public void mousePressed(MouseEvent mouseevent)
    {
    }

    public void mouseReleased(MouseEvent mouseevent)
    {
    }

    public void mouseClicked(MouseEvent e)
    {
        afficherInfo();
    }
}
