// Decompiled by Jad v1.5.7g. Copyright 2000 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/SiliconValley/Bridge/8617/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi 
// Source File Name:   ActionGraphViewerAdapter.java

package lattice.graph.trees.event;

import java.awt.*;
import java.awt.event.*;
import javax.swing.JComponent;
import lattice.graph.trees.*;

public class ActionGraphViewerAdapter extends MouseAdapter
{

    public static final int META = 20;
    public static final int CTRL = 18;
    public static final int ALT = 24;
    public static final int SHIFT = 17;
    public static final int CTRLSHIFT = 19;
    public static final int NORMAL = 16;
    public static final int SHIFTALT = 25;
    protected ActionGraphViewer graphEditor;
    protected int index;
    protected Attribut a;
    protected int x;
    protected int y;

    public ActionGraphViewerAdapter(ActionGraphViewer ac)
    {
        graphEditor = ac;
    }

    public void mousePressed(MouseEvent e)
    {
        graphEditor.requestFocus();
        graphEditor.setCursor(new Cursor(13));
        x = e.getX();
        y = e.getY();
        graphEditor.setPosClic(x, y);
        index = graphEditor.rechNoeud(x, y);  //index指向我当前选中节点在noeuds向量中的编号
        if(index != -1)
        {
            graphEditor.setIndex(index);
            a = graphEditor.attributAt(index, x, y); //attention!!!!!!!!!
            if(a != null)
            {
                attributClicked(x, y, e);
            } else
            {
                Rectangle rect3 = graphEditor.noeuds(index).rect3();
                if(rect3 != null && rect3.contains(x, y))
                    graphEditor.affSousArbre();
                else
                if(graphEditor.noeuds(index).rect2().contains(x, y))
                    noeudClicked(x, y, e);
                else
                    canvasClicked(x, y, e);
            }
        } else
        {
            canvasClicked(x, y, e);
        }
    }

    public int index()
    {
        return index;
    }

    public Attribut attribut()
    {
        return a;
    }

    public void mouseReleased(MouseEvent e)
    {
        graphEditor.mouseUp(e.getX(), e.getY());
    }

    protected void noeudClicked(int x, int y, MouseEvent e)
    {
        graphEditor.doSelected((Selectable)graphEditor.noeuds(index));
        if(e.getClickCount() >= 2)
        {
            if(e.getModifiers() == 20)
                graphEditor.createRelation(index());
            else
                graphEditor.editNode(index());
        } else
        {
            switch(e.getModifiers())
            {
            case 20: // '\024'
                graphEditor.createRelation(index());
                break;

            case 8: // '\b'
                graphEditor.createRelation(index());
                break;

            case 17: // '\021'
                graphEditor.moveTree(index());
                break;

            case 1: // '\001'
                graphEditor.moveTree(index());
                break;

            case 18: // '\022'
                affPopUp();
                break;

            case 25: // '\031'
                Noeud copy = graphEditor.copyTree(index());
                graphEditor.moveTree(copy);
                break;

            case 4: // '\004'
                graphEditor.getComponentPopUp().show(graphEditor, x, y);
                break;

            case 19: // '\023'
                graphEditor.eraseTree(index());
                break;

            case 24: // '\030'
                graphEditor.copyNode(index());
                break;

            default:
                graphEditor.selectNode(index());
                break;
            }
        }
    }

    protected void affPopUp()
    {
        ComponentPopUp cp = graphEditor.getComponentPopUp();
        cp.setLabelAtt(!graphEditor.noeuds(index()).affAttributs());
        cp.setLabelFils(!graphEditor.noeuds(index()).isFilsVisible());
        cp.show(graphEditor, x, y);
    }

    protected void attributClicked(int x, int y, MouseEvent e)
    {
        graphEditor.doSelected(a);
        if(e.getClickCount() == 2)
            graphEditor.editerAttribut();
        else
            switch(e.getModifiers())
            {
            case 20: // '\024'
                graphEditor.createAttribute(index);
                break;

            case 24: // '\030'
                graphEditor.setCopyMode(true);
                graphEditor.attributClicked(a, index);
                break;

            case 8: // '\b'
                graphEditor.setCopyMode(true);
                graphEditor.attributClicked(a, index);
                break;

            case 18: // '\022'
                graphEditor.getAttributPopUp().show(graphEditor, x, y);
                break;

            case 2: // '\002'
                graphEditor.getAttributPopUp().show(graphEditor, x, y);
                break;

            case 4: // '\004'
                graphEditor.getAttributPopUp().show(graphEditor, x, y);
                break;

            default:
                graphEditor.attributClicked(a, index);
                break;
            }
    }

    protected void canvasClicked(int x, int y, MouseEvent e)
    {
        switch(e.getModifiers())
        {
        case 20: // '\024'
            graphEditor.createNode(x, y);
            break;

        case 8: // '\b'
            graphEditor.createNode(x, y);
            break;

        case 18: // '\022'
            graphEditor.getCanvasPopUp().show(graphEditor, x, y);
            break;

        case 2: // '\002'
            graphEditor.getCanvasPopUp().show(graphEditor, x, y);
            break;

        case 4: // '\004'
            graphEditor.getCanvasPopUp().show(graphEditor, x, y);
            break;

        default:
            graphEditor.dragMode();
            break;
        }
    }

    public void mouseEntered(MouseEvent mouseevent)
    {
    }
}
