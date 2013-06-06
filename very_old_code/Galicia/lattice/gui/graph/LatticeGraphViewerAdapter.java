// Decompiled by Jad v1.5.7g. Copyright 2000 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/SiliconValley/Bridge/8617/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi 
// Source File Name:   LatticeGraphViewerAdapter.java

package lattice.gui.graph;

import java.awt.event.InputEvent;
import java.awt.event.MouseEvent;
import lattice.graph.trees.*;
import lattice.graph.trees.event.ActionGraphViewerAdapter;
import lattice.gui.graph.threeD.Lattice3D;

// Referenced classes of package lattice.gui.graph:
//            LatticeGraphViewer

public class LatticeGraphViewerAdapter extends ActionGraphViewerAdapter
{

    public LatticeGraphViewerAdapter(LatticeGraphViewer graphEditor)
    {
        super(graphEditor);
    }

    protected void canvasClicked(int x, int y, MouseEvent e)
    {
        graphEditor.doSelected(null);
        graphEditor.dragMode();
    }

    public void mousePressed(MouseEvent e)
    {
        if(!((LatticeGraphViewer)graphEditor).getThreeD())
            super.mousePressed(e);
        else
            ((LatticeGraphViewer)graphEditor).l3D.mouseDown(e);
    }

    protected void noeudClicked(int x, int y, MouseEvent e)
    {
        switch(e.getModifiers())
        {
        case 17: // '\021'
            graphEditor.moveTree(index());
            break;

        case 1: // '\001'
            graphEditor.moveTree(index());
            break;

        case 20: // '\024'
            graphEditor.changeAffAttributs();
            break;

        case 4: // '\004'
            graphEditor.changeAffAttributs();
            break;

        default:
            graphEditor.doSelected((Selectable)graphEditor.noeuds(index));
            graphEditor.selectNode(index());
            break;
        }
    }
}
