// Decompiled by Jad v1.5.7g. Copyright 2000 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/SiliconValley/Bridge/8617/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi 
// Source File Name:   MenuColorAdapter.java

package lattice.graph.trees.event;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.EventObject;
import lattice.graph.trees.GraphViewer;
import lattice.graph.trees.TreeEditor;
import lattice.graph.trees.menu.MenuItemColor;

public class MenuColorAdapter
    implements ActionListener
{

    public static final int TEXT_OBJET = 1;
    public static final int FOND_OBJET = 2;
    public static final int TEXT_ATT = 3;
    public static final int FOND_ATT = 4;
    int choix;
    TreeEditor editeur;

    public MenuColorAdapter(int choix, TreeEditor editeur)
    {
        this.choix = choix;
        this.editeur = editeur;
    }

    public void actionPerformed(ActionEvent e)
    {
        MenuItemColor mic = (MenuItemColor)e.getSource();
        switch(choix)
        {
        case 1: // '\001'
            editeur.getCanvas().changeLabelColor(mic.getColor());
            break;

        case 2: // '\002'
            editeur.getCanvas().changeBgColor(mic.getColor());
            break;

        case 3: // '\003'
            editeur.getCanvas().changeLabelColorAtt(mic.getColor());
            break;

        case 4: // '\004'
            editeur.getCanvas().changeBgColorAtt(mic.getColor());
            break;
        }
    }
}
