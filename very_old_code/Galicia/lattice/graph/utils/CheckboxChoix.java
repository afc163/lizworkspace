// Decompiled by Jad v1.5.7g. Copyright 2000 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/SiliconValley/Bridge/8617/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi 
// Source File Name:   CheckboxChoix.java

package lattice.graph.utils;

import java.awt.*;
import java.awt.event.*;

// Referenced classes of package lattice.graph.utils:
//            ChoixComponent, InfoListener

public class CheckboxChoix extends Checkbox
    implements ChoixComponent, MouseListener
{

    int choix;
    InfoListener ibm;
    String info;
    String infoSelect;

    public CheckboxChoix(String label, ItemListener listener, int choix)
    {
        super(label);
        this.choix = choix;
        addItemListener(listener);
        addMouseListener(this);
        if(listener instanceof InfoListener)
            ibm = (InfoListener)listener;
    }

    public CheckboxChoix(String label, CheckboxGroup group, boolean state, ItemListener listener, int choix)
    {
        super(label, group, state);
        this.choix = choix;
        addItemListener(listener);
        addMouseListener(this);
        if(listener instanceof InfoListener)
            ibm = (InfoListener)listener;
    }

    public InfoListener getListener()
    {
        return ibm;
    }

    public int getChoix()
    {
        return choix;
    }

    public String getInfo()
    {
        return info;
    }

    public String getInfoSelect()
    {
        return infoSelect;
    }

    public void setInfo(String s)
    {
        info = s;
    }

    public void setInfoSelect(String s)
    {
        infoSelect = s;
    }

    protected void afficherInfo()
    {
        if(!getState())
        {
            if(getInfo() != null && getListener() != null)
                getListener().setInfo(getInfo());
        } else
        if(getInfoSelect() != null && getListener() != null)
            getListener().setInfo(getInfoSelect());
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
