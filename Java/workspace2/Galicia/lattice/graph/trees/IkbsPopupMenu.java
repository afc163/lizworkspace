// Decompiled by Jad v1.5.7g. Copyright 2000 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/SiliconValley/Bridge/8617/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi 
// Source File Name:   IkbsPopupMenu.java

package lattice.graph.trees;

import java.awt.*;
import lattice.graph.trees.event.IkbsPopupMenuListener;

// Referenced classes of package lattice.graph.trees:
//            ActionGraphViewer

public class IkbsPopupMenu extends PopupMenu
{

    public IkbsPopupMenu(String titre, ActionGraphViewer unCanvas)
    {
        super(titre);
        MenuItem mi = new MenuItem("Cr?er objet");
        mi.addActionListener(new IkbsPopupMenuListener(0, unCanvas));
        add(mi);
        addSeparator();
        mi = new MenuItem("Tout effacer");
        mi.addActionListener(new IkbsPopupMenuListener(9, unCanvas));
        add(mi);
    }
}
