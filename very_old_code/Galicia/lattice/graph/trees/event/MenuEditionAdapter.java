// Decompiled by Jad v1.5.7g. Copyright 2000 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/SiliconValley/Bridge/8617/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi 
// Source File Name:   MenuEditionAdapter.java

package lattice.graph.trees.event;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import lattice.graph.trees.TreeEditor;

public class MenuEditionAdapter
    implements ActionListener
{

    public static final int EDITION = 1;
    TreeEditor editeur;
    int choix;

    public MenuEditionAdapter(int choix, TreeEditor editeur)
    {
        this.choix = choix;
        this.editeur = editeur;
    }

    public void actionPerformed(ActionEvent e)
    {
        switch(choix)
        {
        case 1: // '\001'
            editeur.changeMode2();
            break;
        }
    }
}
