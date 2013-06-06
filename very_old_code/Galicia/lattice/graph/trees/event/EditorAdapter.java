// Decompiled by Jad v1.5.7g. Copyright 2000 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/SiliconValley/Bridge/8617/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi 
// Source File Name:   EditorAdapter.java

package lattice.graph.trees.event;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import lattice.graph.trees.Editor;

public class EditorAdapter
    implements ActionListener
{

    public static final int NULL = 0;
    public static final int A_PROPOS = 1;
    public static final int MANUEL = 2;
    Editor editeur;
    int choix;

    public EditorAdapter(int choix, Editor editeur)
    {
        this.choix = choix;
        this.editeur = editeur;
    }

    public void actionPerformed(ActionEvent e)
    {
        switch(choix)
        {
        case 1: // '\001'
            editeur.afficherAPropos();
            break;

        case 2: // '\002'
            editeur.afficherAide();
            break;
        }
    }
}
