// Decompiled by Jad v1.5.7g. Copyright 2000 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/SiliconValley/Bridge/8617/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi 
// Source File Name:   TextFieldChoix.java

package lattice.graph.utils;

import java.awt.Component;
import java.awt.TextField;
import java.awt.event.*;

// Referenced classes of package lattice.graph.utils:
//            ChoixComponent, InfoListener

public class TextFieldChoix extends TextField
    implements ChoixComponent, MouseListener
{

    protected int choix;
    protected String info;
    protected InfoListener ibm;

    public TextFieldChoix(String label, int largeur, ActionListener listener, int choix)
    {
        super(label, largeur);
        this.choix = choix;
        addActionListener(listener);
        addMouseListener(this);
        if(listener instanceof InfoListener)
            ibm = (InfoListener)listener;
    }

    public InfoListener getListener()
    {
        return ibm;
    }

    public void setInfoListener(InfoListener il)
    {
        ibm = il;
    }

    public int getChoix()
    {
        return choix;
    }

    public void setChoix(int i)
    {
        choix = i;
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
