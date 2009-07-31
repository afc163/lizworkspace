// Decompiled by Jad v1.5.7g. Copyright 2000 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/SiliconValley/Bridge/8617/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi 
// Source File Name:   LatticeToGraph.java

package lattice.gui.graph;

import java.awt.Color;
import java.io.PrintStream;
import java.util.Hashtable;
import java.util.Iterator;
import lattice.graph.trees.Composant;
import lattice.graph.trees.GraphViewer;
import lattice.graph.trees.NodeGraph;
import lattice.util.Node;

// Referenced classes of package lattice.gui.graph:
//            LatticeGraphViewer

public class LatticeToGraph
{

    protected LatticeGraphViewer agv;
    private Hashtable nodeSet;

    public LatticeToGraph(LatticeGraphViewer agv)
    {
        nodeSet = new Hashtable();
        this.agv = agv;
    }

    public void initNodeGraph(NodeGraph n)
    {
        n.setBgColor(new Color(200, 200, 200));
        n.setLabelColor(Color.black);
        n.setRounded(true);
        n.setAffAttributs(false);
        n.setActiveNode(false);
        agv.calculDimension(n);
    }

    public NodeGraph asGraphicFromTop(Node n,String title)
    {
        if(n == null)
            return null;
        NodeGraph unNoeud = (NodeGraph)nodeSet.get(n);
        if(unNoeud == null)
        {
            unNoeud = agv.creerNoeud(n, 10, 10,title);
            nodeSet.put(n, unNoeud);
            agv.ajouterNoeud(unNoeud);
            for(Iterator i = n.getChildren().iterator(); i.hasNext();)
            {
                NodeGraph unFils = asGraphicFromTop((Node)i.next(),title);
                if(unFils != null)
                    agv.initRelation(unNoeud, unFils);
                else
                    System.out.println("Warning, one node is null with parent " + n);
            }

            initNodeGraph(unNoeud);
        }
        return unNoeud;
    }
}
