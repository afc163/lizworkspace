// Decompiled by Jad v1.5.7g. Copyright 2000 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/SiliconValley/Bridge/8617/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi 
// Source File Name:   MenuLangueAdapter.java

package lattice.graph.trees.event;

import java.awt.CheckboxMenuItem;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.EventObject;
import lattice.graph.trees.LangueManager;

public class MenuLangueAdapter
    implements ActionListener
{

    public static final int FRANCAIS = 0;
    public static final int ANGLAIS = 1;
    CheckboxMenuItem francaisMenu;
    CheckboxMenuItem anglaisMenu;
    private LangueManager langueManager;

    public MenuLangueAdapter(LangueManager langueManager, CheckboxMenuItem francaisMenu, CheckboxMenuItem anglaisMenu)
    {
        this.langueManager = langueManager;
        this.francaisMenu = francaisMenu;
        this.anglaisMenu = anglaisMenu;
    }

    public void actionPerformed(ActionEvent e)
    {
        CheckboxMenuItem cbmi = (CheckboxMenuItem)e.getSource();
        if(cbmi == francaisMenu)
        {
            langueManager.changeLangue(0);
            francaisMenu.setState(true);
            anglaisMenu.setState(false);
        }
        if(cbmi == anglaisMenu)
        {
            langueManager.changeLangue(1);
            anglaisMenu.setState(true);
            francaisMenu.setState(false);
        }
    }
}
