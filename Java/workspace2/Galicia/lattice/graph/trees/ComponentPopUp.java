// Decompiled by Jad v1.5.7g. Copyright 2000 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/SiliconValley/Bridge/8617/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi 
// Source File Name:   ComponentPopUp.java

package lattice.graph.trees;

import java.awt.*;
import lattice.graph.trees.event.IkbsPopupMenuListener;
import lattice.graph.trees.menu.MenuItemChange;

// Referenced classes of package lattice.graph.trees:
//            ActionGraphViewer

public class ComponentPopUp extends PopupMenu
{

    protected MenuItemChange menuAtt;
    protected MenuItemChange menuFils;

    public ComponentPopUp(String titre, ActionGraphViewer action)
    {
        super(titre);
        init(action);
    }

    public void init(ActionGraphViewer unCanvas)
    {
        MenuItem mi = new MenuItem("Editer objet");
        mi.addActionListener(new IkbsPopupMenuListener(3, unCanvas));
        add(mi);
        addSeparator();
        menuAtt = new MenuItemChange("Afficher attributs", "Masquer attributs");
        menuAtt.addActionListener(new IkbsPopupMenuListener(14, unCanvas));
        add(menuAtt);
        menuFils = new MenuItemChange("Afficher fils", "Masquer fils");
        menuFils.addActionListener(new IkbsPopupMenuListener(15, unCanvas));
        add(menuFils);
        addSeparator();
        mi = new MenuItem("Effacer objet");
        mi.addActionListener(new IkbsPopupMenuListener(1, unCanvas));
        add(mi);
        mi = new MenuItem("Effacer sous arbre");
        mi.addActionListener(new IkbsPopupMenuListener(2, unCanvas));
        add(mi);
        addSeparator();
        mi = new MenuItem("Dupliquer objet");
        mi.addActionListener(new IkbsPopupMenuListener(7, unCanvas));
        add(mi);
        mi = new MenuItem("Dupliquer sous arbre");
        mi.addActionListener(new IkbsPopupMenuListener(8, unCanvas));
        add(mi);
    }

    public void setLabelAtt(boolean b)
    {
        menuAtt.setState(b);
    }

    public void setLabelFils(boolean b)
    {
        menuFils.setState(b);
    }
}
