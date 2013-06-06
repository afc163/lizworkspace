// Decompiled by Jad v1.5.7g. Copyright 2000 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/SiliconValley/Bridge/8617/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi 
// Source File Name:   IkbsPopupMenuListener.java

package lattice.graph.trees.event;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import lattice.graph.trees.ActionGraphViewer;

public class IkbsPopupMenuListener
    implements ActionListener
{

    public static final int CREER_OBJET = 0;
    public static final int EFFACER_OBJET = 1;
    public static final int EFFACER_SOUS_ARBRE = 2;
    public static final int EDITER_OBJET = 3;
    public static final int RACINE_OBJET = 4;
    public static final int CREER_ATTRIBUT = 5;
    public static final int COLLER = 6;
    public static final int COPIER_OBJET = 7;
    public static final int COPIER_SOUS_ARBRE = 8;
    public static final int TOUT_EFFACER = 9;
    public static final int EDITER_ATTRIBUT = 10;
    public static final int CIBLE_ATTRIBUT = 11;
    public static final int EFFACER_ATTRIBUT = 12;
    public static final int COPIER_ATTRIBUT = 13;
    public static final int AFFICHER_ATT = 14;
    public static final int AFFICHER_FILS = 15;
    protected ActionGraphViewer graphEditor;
    protected int choix;

    public IkbsPopupMenuListener(int choix, ActionGraphViewer unCanvas)
    {
        graphEditor = unCanvas;
        this.choix = choix;
    }

    public void actionPerformed(ActionEvent e)
    {
        switch(choix)
        {
        case 0: // '\0'
            graphEditor.createNode();
            break;

        case 1: // '\001'
            graphEditor.eraseNode();
            break;

        case 2: // '\002'
            graphEditor.eraseTree();
            break;

        case 3: // '\003'
            graphEditor.editNode();
            break;

        case 4: // '\004'
            graphEditor.rootOnNode();
            break;

        case 9: // '\t'
            graphEditor.eraseAll();
            break;

        case 7: // '\007'
            graphEditor.copyNode();
            break;

        case 8: // '\b'
            graphEditor.copyTree();
            break;

        case 11: // '\013'
            graphEditor.setAttributCible();
            break;

        case 10: // '\n'
            graphEditor.editerAttribut();
            break;

        case 12: // '\f'
            graphEditor.eraseAttribut();
            break;

        case 13: // '\r'
            graphEditor.copyAttribut();
            break;

        case 14: // '\016'
            graphEditor.changeAffAttributs();
            break;

        case 15: // '\017'
            graphEditor.affSousArbre();
            break;
        }
    }
}
