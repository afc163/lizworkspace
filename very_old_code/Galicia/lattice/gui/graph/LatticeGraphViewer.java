// Decompiled by Jad v1.5.7g. Copyright 2000 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/SiliconValley/Bridge/8617/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi 
// Source File Name:   LatticeGraphViewer.java

package lattice.gui.graph;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.util.*;
import javax.swing.JComponent;
import lattice.graph.trees.*;
import lattice.graph.trees.formatter.Formatter;
import lattice.graph.zoom.ZoomViewer;
import lattice.gui.graph.magneto.Magnetable;
import lattice.gui.graph.magneto.Magneto;
import lattice.gui.graph.threeD.Lattice3D;
import lattice.util.Node;

// Referenced classes of package lattice.gui.graph:
//            LatticeGraphViewerAdapter, FormatKeyLatticeListener, LatticeToGraph, LatticeRelation, 
//            LatticeNodeGraph, FormatterHBLattice, Formatter3DLattice

public class LatticeGraphViewer extends ActionGraphViewer
{

    public static final int FORMATTER_HB_LATTICE = 6;
    public static final int FORMATTER_HB_LATTICE2 = 7;
    public static final int FORMATTER_HB_LATTICE3 = 8;
    protected Node top;
    public Magneto magneto;
    public boolean threeD;
    public Lattice3D l3D;

    public LatticeGraphViewer()
    {
        threeD = false;
    	
        //by cjj 200504019
    	//threeD=true;  如果这样该将显示主界面,但不会显示图形
    }

    public void init()
    {
        setX(0);
        setY(0);
        setZoom(9);
        initFont(getZoom());
        vitesse = ActionGraphViewer.NORMAL;
        ActionGraphViewer.anim = true;
        pos = new Point(0, 0);
        select = false;
        relationMode = false;
        setBackground(Color.white);
        noeudRacine = null;
        shiftPressed = false;
        super.affAttributs = true;
        showArrow = false;
        drag = false;
        showLabelRelation = false;
        formeRelation = 1;
        setEdition(true);
        addMouseListener(new LatticeGraphViewerAdapter(this));
        addMouseMotionListener(this);
        addKeyListener(new FormatKeyLatticeListener(this));
    }

    //该函数关系到图形显示部分,比较底层了
    public void initLatticeGraphViewer(Node top,String title)
    {
        this.top = top;
        setFormatter(6);
        asGraphicFromTop(top,title);
        initFormatterHBLattice(false, false);
        magneto = new Magneto(this);
        magneto.setPriority(2);
        magneto.start();
        addMouseMotionListener(magneto);
    }

    public Magneto getMagneto()
    {
        return magneto;
    }

    public void asGraphicFromTop(Node top,String title)
    {
        LatticeToGraph ltg = new LatticeToGraph(this);
        NodeGraph topGraph = ltg.asGraphicFromTop(top,title);
        setRacine(topGraph);
    }

    public void initRelation(NodeGraph unNoeud, NodeGraph unFils)
    {
        super.initRelation(new LatticeRelation((LatticeNodeGraph)unNoeud, (LatticeNodeGraph)unFils));
    }

    public LatticeNodeGraph creerNoeud(Node n, int x, int y,String title)
    {
        LatticeNodeGraph unNoeud = new LatticeNodeGraph(n, x, y,title);
        unNoeud.setBordered(false);
        unNoeud.setActiveNode(active);
        calculDimension(unNoeud);
        rect = null;
        return unNoeud;
    }

    public FormatterHBLattice getFormatter()
    {
        return (FormatterHBLattice)formatter;
    }

    public void setFormatter(int i)
    {
        posLienRelations(1);
        switch(i)
        {
        case 6: // '\006'
            initFormatterHBLattice(false, false);
            break;

        case 7: // '\007'
            initFormatterHBLattice(true, false);
            break;

        case 8: // '\b'
            initFormatterHBLattice(true, true);
            break;
        }
    }

    public void initFormatterHBLattice(boolean fitScreen, boolean optimizer)
    {
        if(formatter == null || !(formatter instanceof FormatterHBLattice))
        {
            formatter = new FormatterHBLattice(noeuds, getBounds(), zoom, fitScreen);
        } else
        {
            ((FormatterHBLattice)formatter).fitScreen = fitScreen;
            ((FormatterHBLattice)formatter).optimizerOrdre = optimizer;
            ((FormatterHBLattice)formatter).zoom = zoom;
            ((FormatterHBLattice)formatter).rectParent = getBounds();
        }
        formatter();
    }

    public void affAttributs(boolean aff)
    {
        super.affAttributs = aff;
        for(int i = 0; i < noeuds.size(); i++)
            noeuds(i).setAffAttributs(aff);

        repaint();
    }

    public void changeAffAttributs(Noeud n, boolean aff)
    {
        n.setAffAttributs(aff);
        repaint();
    }

    public void doSelected(Selectable s)
    {
        super.doSelected(s);
        if(noeudSelect != null)
            refreshNoeud(noeudSelect);
    }

    public void moveTree(Noeud n)
    {
        if(!n.getSelected())
        {
            select = true;
            shiftPressed = true;
            n.setSelected(true);
            setNoeudSelect(n);
        } else
        {
            select = false;
            shiftPressed = false;
            n.setSelected(false);
        }
        refreshNoeud(noeudSelect);
    }

    public Dimension getPreferredSize()
    {
        Rectangle r = dimension();
        bougeNoeudRec(noeudRacine(), r.width / 2, 10);
        return new Dimension(r.width, r.height + 30);
    }

    public void recentre(Noeud unNoeud)
    {
        doSelected((Selectable)unNoeud);
        super.recentre(unNoeud);
    }

    public Vector getNiveau()
    {
        return getFormatter().getvNiveau();
    }

    public Vector getNiveauRelation()
    {
        return getFormatter().getvNiveauRelation();
    }

    public Vector getNiveau(int i)
    {
        return getFormatter().getNiveau(i);
    }

    public int getcLargeur(int nbElement)
    {
        return getFormatter().calcRel(nbElement);
    }

    public int getcHauteur()
    {
        return getFormatter().getcHauteurRel();
    }

    public int getNbNiveau()
    {
        return getFormatter().getNbNiveau();
    }

    public void putLastPos(Magnetable n)
    {
        putLastPosition((Noeud)n);
    }

    public boolean getThreeD()
    {
        return threeD;
    }

    public void setThreeD(boolean b)
    {
        threeD = b;
        if(b)
        {
            initThreeDMode();
        } else
        {
            initTwoDMode();
            repaint();
        }
    }

    public void initTwoDMode()
    {
        Vector v = noeuds();
        zoomCanvas.setDecalX(0);
        zoomCanvas.setDecalY(0);
        LatticeNodeGraph n;
        for(Iterator i = v.iterator(); i.hasNext(); n.setZ(0))
        {
            n = (LatticeNodeGraph)i.next();
            n.move(getSize().width / 2, 0);
        }

        if(zoomCanvas != null)
        {
            zoomCanvas.clearRect();
            zoomCanvas.refresh1();
        }
    }

    public void initThreeDMode()
    {
        new Formatter3DLattice(getNiveau());
        l3D = new Lattice3D(this);
        setX(1);
        l3D.start();
        if(zoomCanvas != null)
        {
            zoomCanvas.clearRect();
            zoomCanvas.refresh1();
        }
    }

    public void mouseDragged(MouseEvent e)
    {
        if(!threeD)
            super.mouseDragged(e);
        else
            l3D.mouseDragged(e);
    }

    public void rotation(float val)
    {
        l3D.rotation(val);
    }

    public void paint(Graphics g)
    {
        if(!threeD)
        {
            super.paint(g);
        } else
        {
            l3D.paint(g);
            if(zoomCanvas != null && !zoomCanvas.getQualite())
                zoomCanvas.repaint();
        }
    }
}
