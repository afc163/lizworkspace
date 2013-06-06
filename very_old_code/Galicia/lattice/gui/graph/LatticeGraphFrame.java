// Decompiled by Jad v1.5.7g. Copyright 2000 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/SiliconValley/Bridge/8617/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi 
// Source File Name:   LatticeGraphFrame.java

package lattice.gui.graph;

import java.awt.*;
import javax.swing.*;
import lattice.graph.trees.GraphViewer;
import lattice.graph.zoom.ZoomViewer;
import lattice.gui.graph.control.LatticeToolBar;
import lattice.gui.graph.control.ZoomLatticeEditor;
import lattice.util.Node;

// Referenced classes of package lattice.gui.graph:
//            LatticeGraphViewer

public class LatticeGraphFrame extends JFrame
{

	//by cjj 2005-4-10 
	public String title;  //用于记录算法名称
	
    protected JMenuBar menuBar;
    protected LatticeGraphViewer lgv;
    protected JToolBar toolBar;
    protected ZoomLatticeEditor fZoom;
    protected Container content;

    public LatticeGraphFrame(String title, Node top)
    {
        super(title);
        this.title=title;
        init(top);
    }

    public void init(Node top)
    {
        content = getContentPane();
        content.setLayout(new BorderLayout(0, 0));
        lgv = new LatticeGraphViewer();
        content.add(lgv, "Center");
        lgv.initLatticeGraphViewer(top,title);
        toolBar = new LatticeToolBar(lgv);
        content.add(toolBar, "East");
        setSize(getPreferredSize());
    }

    public Dimension getPreferredSize()
    {
        Dimension d = lgv.getPreferredSize();
        int h = d.height + 20;
        int w = d.width;
        Dimension d2 = toolBar.getPreferredSize();
        int rw = w + d2.width;
        int rh = Math.max(h, d.height);
        if(rw > 850)
            rw = 850;
        if(rh > 720)
            rh = 720;
        return new Dimension(rw, rh);
    }

    public void changeAffZoomViewer()
    {
        if(fZoom != null)
        {
            fZoom = null;
            lgv.setZoomViewer(null);
        } else
        {
            fZoom = new ZoomLatticeEditor("Zoom : " + getTitle(), lgv);
            lgv.setZoomViewer(fZoom.zoomViewer());
            fZoom.setLocation(getLocationOnScreen().x + getSize().width + 10, getLocationOnScreen().y);
            fZoom.zoomViewer().refresh1();
            fZoom.zoomViewer().setRect(new Rectangle(0, 0, lgv.getSize().width, lgv.getSize().height));
        }
    }

    public void generatePdf()
    {
    }
}
