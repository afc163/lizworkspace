// Decompiled by Jad v1.5.7g. Copyright 2000 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/SiliconValley/Bridge/8617/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi 
// Source File Name:   ChoiceChoix.java

package lattice.graph.utils;

import java.awt.Choice;
import java.awt.event.*;

// Referenced classes of package lattice.graph.utils:
//            ChoixComponent, InfoListener

public class ChoiceChoix extends Choice
    implements ChoixComponent, MouseListener
{

    int choix;
    String info;
    InfoListener ibm;

    public ChoiceChoix()
    {
    }

    public ChoiceChoix(ItemListener listener, int choix)
    {
        setChoix(choix);
        addItemListener(listener);
        if(listener instanceof InfoListener)
            ibm = (InfoListener)listener;
    }

    public void setChoix(int c)
    {
        choix = c;
    }

    public int getChoix()
    {
        return choix;
    }

    public InfoListener getListener()
    {
        return ibm;
    }

    public String getInfo()
    {
        return info;
    }

    public void setInfo(String s)
    {
        info = s;
    }

    protected void afficherInfo()
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
