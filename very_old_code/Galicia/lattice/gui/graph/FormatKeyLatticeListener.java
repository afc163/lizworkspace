// Decompiled by Jad v1.5.7g. Copyright 2000 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/SiliconValley/Bridge/8617/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi 
// Source File Name:   FormatKeyLatticeListener.java

package lattice.gui.graph;

import java.awt.Component;
import java.awt.event.KeyEvent;
import lattice.graph.trees.GraphViewer;
import lattice.graph.trees.event.FormatKeyListener;

// Referenced classes of package lattice.gui.graph:
//            LatticeNodeGraph, LatticeGraphViewer

public class FormatKeyLatticeListener extends FormatKeyListener
{

    public FormatKeyLatticeListener(LatticeGraphViewer lgv)
    {
        super(lgv);
    }

    public void keyPressed(KeyEvent e)
    {
        switch(e.getKeyCode())
        {
        case 112: // 'p'
            canvas.setFormatter(6);
            break;

        case 113: // 'q'
            canvas.setFormatter(7);
            break;

        case 114: // 'r'
            canvas.setFormatter(8);
            break;

        case 116: // 't'
            canvas.changeFormeRelation(1);
            break;

        case 117: // 'u'
            canvas.changeFormeRelation(0);
            break;

        case 118: // 'v'
            canvas.setShowArrow(!canvas.getShowArrow());
            break;

        case 119: // 'w'
            LatticeNodeGraph.IS_COLORED = !LatticeNodeGraph.IS_COLORED;
            canvas.repaint();
            break;

        case 120: // 'x'
            canvas.ZM();
            break;

        case 121: // 'y'
            canvas.ZP();
            break;

        case 122: // 'z'
            canvas.activeTextRelations();
            break;

        case 123: // '{'
            canvas.desactiveTextRelations();
            break;
        }
    }
}
