// Decompiled by Jad v1.5.7g. Copyright 2000 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/SiliconValley/Bridge/8617/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi 
// Source File Name:   AttributPopUp.java

package lattice.graph.trees;

import java.awt.*;
import lattice.graph.trees.event.IkbsPopupMenuListener;

// Referenced classes of package lattice.graph.trees:
//            ActionGraphViewer

public class AttributPopUp extends PopupMenu
{

    public AttributPopUp(String titre, ActionGraphViewer unCanvas)
    {
        super(titre);
        MenuItem mi = new MenuItem("Editer attribut");
        mi.addActionListener(new IkbsPopupMenuListener(10, unCanvas));
        add(mi);
        addSeparator();
        mi = new MenuItem("Affecter cible");
        mi.addActionListener(new IkbsPopupMenuListener(11, unCanvas));
        add(mi);
        addSeparator();
        mi = new MenuItem("Effacer attribut");
        mi.addActionListener(new IkbsPopupMenuListener(12, unCanvas));
        add(mi);
        addSeparator();
        mi = new MenuItem("Copier attribut");
        mi.addActionListener(new IkbsPopupMenuListener(13, unCanvas));
        add(mi);
    }
}
