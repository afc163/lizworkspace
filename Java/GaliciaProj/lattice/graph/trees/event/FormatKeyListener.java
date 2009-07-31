// Decompiled by Jad v1.5.7g. Copyright 2000 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/SiliconValley/Bridge/8617/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi 
// Source File Name:   FormatKeyListener.java

package lattice.graph.trees.event;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import lattice.graph.trees.ActionGraphViewer;
import lattice.graph.trees.GraphViewer;

public class FormatKeyListener extends KeyAdapter
{

    protected ActionGraphViewer canvas;

    public FormatKeyListener(ActionGraphViewer ac)
    {
        canvas = ac;
    }

    public void keyPressed(KeyEvent e)
    {
        switch(e.getKeyCode())
        {
        case 112: // 'p'
            canvas.setFormatter(1);
            break;

        case 113: // 'q'
            canvas.setFormatter(3);
            break;

        case 114: // 'r'
            canvas.setFormatter(5);
            break;

        case 115: // 's'
            canvas.setFormatter(4);
            break;

        case 116: // 't'
            canvas.changeFormeRelation(1);
            break;

        case 117: // 'u'
            canvas.changeFormeRelation(0);
            break;

        case 118: // 'v'
            canvas.setShowArrow(true);
            break;

        case 119: // 'w'
            canvas.setShowArrow(false);
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
