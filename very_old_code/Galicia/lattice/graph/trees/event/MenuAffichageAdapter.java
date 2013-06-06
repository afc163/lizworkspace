// Decompiled by Jad v1.5.7g. Copyright 2000 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/SiliconValley/Bridge/8617/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi 
// Source File Name:   MenuAffichageAdapter.java

package lattice.graph.trees.event;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import lattice.graph.trees.GraphViewer;
import lattice.graph.trees.TreeEditor;

public class MenuAffichageAdapter
    implements ActionListener
{

    public static final int AFF_ZOOM = 1;
    public static final int AFF_INFO = 21;
    public static final int FORMAT_1 = 2;
    public static final int FORMAT_2 = 3;
    public static final int FORMAT_3 = 4;
    public static final int FORMAT_4 = 5;
    public static final int ZOOM_MOINS = 6;
    public static final int ZOOM_PLUS = 7;
    public static final int CHANGE_FORMEREL = 8;
    public static final int CHANGE_FLECHES = 9;
    public static final int POS_LIENS = 10;
    public static final int AFF_ATTRIB = 11;
    public static final int TEXT_RELATIONS = 12;
    public static final int CHANGE_POLICE_OBJ = 13;
    public static final int CHANGE_POLICE_ATT = 14;
    public static final int CHANGE_POLICE_REL = 15;
    public static final int CHANGE_STYLE_OBJ = 16;
    public static final int CHANGE_STYLE_ATT = 17;
    public static final int CHANGE_STYLE_REL = 18;
    public static final int SET_IMAGE = 19;
    public static final int SET_ALIGNEMENT = 20;
    int choix;
    TreeEditor editeur;

    public MenuAffichageAdapter(int choix, TreeEditor editeur)
    {
        this.choix = choix;
        this.editeur = editeur;
    }

    public void actionPerformed(ActionEvent e)
    {
        switch(choix)
        {
        case 13: // '\r'
            editeur.getCanvas().setPoliceObj(e.getActionCommand());
            break;

        case 14: // '\016'
            editeur.getCanvas().setPoliceAtt(e.getActionCommand());
            break;

        case 15: // '\017'
            editeur.getCanvas().setPoliceRel(e.getActionCommand());
            break;

        case 16: // '\020'
            editeur.getCanvas().setStyleObj(style(e.getActionCommand()));
            break;

        case 17: // '\021'
            editeur.getCanvas().setStyleAtt(style(e.getActionCommand()));
            break;

        case 18: // '\022'
            editeur.getCanvas().setStyleRel(style(e.getActionCommand()));
            break;

        case 1: // '\001'
            editeur.changeAffZoomViewer2();
            break;

        case 2: // '\002'
            editeur.getCanvas().setFormatter(1);
            break;

        case 3: // '\003'
            editeur.getCanvas().setFormatter(3);
            break;

        case 4: // '\004'
            editeur.getCanvas().setFormatter(5);
            break;

        case 5: // '\005'
            editeur.getCanvas().setFormatter(4);
            break;

        case 6: // '\006'
            editeur.getCanvas().ZM();
            break;

        case 7: // '\007'
            editeur.getCanvas().ZP();
            break;

        case 8: // '\b'
            editeur.changeFormeRelation();
            break;

        case 9: // '\t'
            editeur.changeFleches();
            break;

        case 10: // '\n'
            editeur.posLiens();
            break;

        case 11: // '\013'
            editeur.affAttributs2();
            break;

        case 12: // '\f'
            editeur.changeTextRelation();
            break;

        case 19: // '\023'
            editeur.loadBackgroundPicture();
            break;

        case 20: // '\024'
            editeur.getCanvas().setBgAlignment(e.getActionCommand());
            break;

        case 21: // '\025'
            editeur.changeAffInfo();
            break;
        }
    }

    public int style(String s)
    {
        if(s.equals("Italique"))
            return 2;
        if(s.equals("Gras"))
            return 1;
        return !s.equals("Gras italique") ? 0 : 3;
    }
}
