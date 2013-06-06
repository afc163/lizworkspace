// Decompiled by Jad v1.5.7g. Copyright 2000 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/SiliconValley/Bridge/8617/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi 
// Source File Name:   ArbreAdapter.java

package lattice.graph.utils;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.EventObject;
import lattice.graph.trees.GraphViewer;

// Referenced classes of package lattice.graph.utils:
//            ChoixComponent, EditeurArbreInterface

public class ArbreAdapter
    implements ActionListener
{

    public static final int FRANCAIS = 0;
    public static final int ANGLAIS = 1;
    public static final int SAVE = 2;
    public static final int LOAD = 3;
    public static final int LOAD_DIST = 4;
    public static final int FORME_SIMPLE = 5;
    public static final int FORME_EQUI = 6;
    public static final int GRAPHE = 7;
    public static final int FORME_VERT = 8;
    public static final int ZOOM_PLUS = 9;
    public static final int ZOOM_MOINS = 10;
    public static final int ZOOM = 11;
    public static final int AFF_ATT = 12;
    public static final int EDITION = 13;
    public static final int AFFICHER_WEB = 14;
    EditeurArbreInterface editeur;
    int choix;

    public ArbreAdapter(int choix, EditeurArbreInterface editeur)
    {
        this.choix = choix;
        this.editeur = editeur;
    }

    public void actionPerformed(ActionEvent e)
    {
        int choix = this.choix;
        if(e.getSource() instanceof ChoixComponent)
            choix = ((ChoixComponent)e.getSource()).getChoix();
        switch(choix)
        {
        case 2: // '\002'
            editeur.sauverLocal();
            break;

        case 3: // '\003'
            editeur.loadLocal();
            break;

        case 4: // '\004'
            editeur.loadDistant();
            break;

        case 5: // '\005'
            editeur.getCanvas().setFormatter(1);
            break;

        case 6: // '\006'
            editeur.getCanvas().setFormatter(3);
            break;

        case 7: // '\007'
            editeur.getCanvas().setFormatter(4);
            break;

        case 8: // '\b'
            editeur.getCanvas().setFormatter(5);
            break;

        case 9: // '\t'
            editeur.getCanvas().ZP();
            break;

        case 10: // '\n'
            editeur.getCanvas().ZM();
            break;

        case 11: // '\013'
            editeur.changeAffZoomViewer();
            break;

        case 12: // '\f'
            editeur.affAttributs();
            break;

        case 13: // '\r'
            editeur.changeMode();
            break;

        case 14: // '\016'
            editeur.showDocument();
            break;

        case 0: // '\0'
            editeur.changeLangue(0);
            break;

        case 1: // '\001'
            editeur.changeLangue(1);
            break;
        }
    }
}
