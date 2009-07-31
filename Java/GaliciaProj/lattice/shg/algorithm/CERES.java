// Decompiled by Jad v1.5.7g. Copyright 2000 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/SiliconValley/Bridge/8617/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi 
// Source File Name:   CERES.java

package lattice.shg.algorithm;

import java.util.*;
import lattice.algorithm.LatticeAlgorithm;
import lattice.gui.tooltask.AbstractJob;
import lattice.util.*;

public class CERES extends LatticeAlgorithm
{

    public CERES()
    {
    }

    public CERES(BinaryRelation bRel)
    {
        super(bRel);
    }

    public void doAlgorithm()
    {
        AlgoPrincipale();
        if(lcl.getTop().getChildren().size() == 1 && lcl.getTop().getConcept().getSimplifyExtent().size() == 0 && lcl.getTop().getConcept().getSimplifyIntent().size() == 0)
        {
            Node nouvTop = (Node)lcl.getTop().getChildren().get(0);
            Node ancTop = lcl.getTop();
            nouvTop.removeParent(ancTop);
            ancTop.removeChild(nouvTop);
            lcl.setTop(nouvTop);
        }
        ((LatticeGraph)lcl).findNSetBottom();
        binRel.setLattice(lcl);
    }

    public String getDescription()
    {
        return "CERES Algo.";
    }

    private void AlgoPrincipale()
    {
        lcl = new LatticeGraph();
        Extent ext = new SetExtent();
        for(int i = 0; i < binRel.getFormalObjects().length; i++)
            ext.add(binRel.getFormalObjects()[i]);

        lcl.setTop(new LatticeNode(new Concept(ext, new SetIntent())));
        Vector lesPreConcepts = new Vector(binRel.getInitialAttributePreConcept(BinaryRelation.DS_EXTENT_SORTED));
        SetIntent SOP = new SetIntent();
        for(int i = 0; i < lesPreConcepts.size(); i++)
        {
            Vector PLN = new Vector();
            Concept c = (Concept)lesPreConcepts.elementAt(i);
            int extentSize = c.getExtent().size();
            int k = i;
            do
            {
                boolean trouve = false;
                for(int j = 0; !trouve && j < PLN.size(); j++)
                {
                    Node N = (Node)PLN.elementAt(j);
                    if(N.getConcept().getExtent().equals(c.getExtent()))
                    {
                        N.getConcept().getSimplifyIntent().addAll(c.getIntent());
                        trouve = true;
                    }
                }

                if(!trouve)
                {
                    c.getSimplifyIntent().addAll(c.getIntent());
                    Node N = new LatticeNode(c);
                    PLN.add(N);
                }
            } while(++k < lesPreConcepts.size() && (c = (Concept)lesPreConcepts.elementAt(k)).getExtent().size() == extentSize);
            i = k - 1;
            for(int j = 0; j < PLN.size(); j++)
            {
                Node N = (LatticeNode)PLN.elementAt(j);
                N.getConcept().getIntent().addAll(N.getConcept().getSimplifyIntent());
                Classify((LatticeNode)N, SOP, true);
                SOP.addAll(N.getConcept().getSimplifyIntent());
                for(Iterator it = N.getConcept().getExtent().iterator(); it.hasNext();)
                {
                    FormalObject o = (FormalObject)it.next();
                    if(binRel.getF(o).equals(N.getConcept().getIntent()))
                        N.getConcept().getSimplifyExtent().add(o);
                }

                WorkOnLeftPart((LatticeNode)N, SOP);
            }

            sendJobPercentage((i * 100) / lesPreConcepts.size());
        }

    }

    private void Classify(LatticeNode N, SetIntent SOP, boolean nodeOfOp)
    {
        Vector Q = new Vector();
        Vector DSC = new Vector();
        Q.add(lcl.getTop());
        LatticeNode CSC = null;
        while(Q.size() != 0) 
        {
            CSC = (LatticeNode)Q.remove(0);
            DSC.add(CSC);
            for(Iterator it = CSC.getParents().iterator(); it.hasNext(); DSC.remove(it.next()));
            for(Iterator it = CSC.getChildren().iterator(); it.hasNext();)
            {
                LatticeNode P = (LatticeNode)it.next();
                if(P.getDegre() == -1)
                    P.setDegre(P.getParents().size());
                P.setDegre(P.getDegre() - 1);
                if(P.getDegre() == 0)
                {
                    P.setDegre(-1);
                    if(N.getConcept().getExtent().union(P.getConcept().getExtent()).equals(P.getConcept().getExtent()))
                    {
                        Q.add(P);
                        if(nodeOfOp)
                        {
                            for(Iterator it2 = P.getConcept().getSimplifyIntent().iterator(); it2.hasNext(); N.getConcept().getIntent().add(it2.next()));
                        }
                    }
                }
            }

        }
        ((LatticeGraph)lcl).addNode(N);
        for(int i = 0; i < DSC.size(); i++)
        {
            N.addParent((Node)DSC.elementAt(i));
            ((Node)DSC.elementAt(i)).addChild(N);
        }

    }

    private void WorkOnLeftPart(LatticeNode Nod, SetIntent SOP)
    {
        Extent CC = (Extent)Nod.getConcept().getExtent().clone();
        for(Iterator it = Nod.getConcept().getSimplifyExtent().iterator(); it.hasNext(); CC.remove(it.next()));
        Vector lesObjsTrie = new Vector();
        for(Iterator it = CC.iterator(); it.hasNext();)
        {
            FormalObject e = (FormalObject)it.next();
            int tailleFe = binRel.getF(e).size();
            boolean trouve = false;
            for(int i = 0; i < lesObjsTrie.size() && !trouve; i++)
            {
                FormalObject o = (FormalObject)lesObjsTrie.elementAt(i);
                int tailleFo = binRel.getF(o).size();
                if(tailleFe <= tailleFo)
                {
                    lesObjsTrie.insertElementAt(e, i);
                    trouve = true;
                }
            }

            if(!trouve)
                lesObjsTrie.add(e);
        }

        for(int i = 0; i < lesObjsTrie.size(); i++)
        {
            FormalObject e = (FormalObject)lesObjsTrie.elementAt(i);
            if(binRel.getF(e).union(SOP).equals(SOP))
            {
                Extent LP = new SetExtent();
                LP.add(e);
                Intent RP = new SetIntent();
                RP.addAll(binRel.getF(e));
                Concept c = new Concept(LP, RP);
                c.getSimplifyExtent().add(e);
                LatticeNode N = new LatticeNode(c);
                for(int j = i + 1; j < lesObjsTrie.size(); j++)
                {
                    FormalObject cc = (FormalObject)lesObjsTrie.elementAt(j);
                    if(binRel.getF(cc).size() == binRel.getF(e).size())
                    {
                        if(binRel.getF(cc).equals(binRel.getF(e)))
                        {
                            N.getConcept().getExtent().add(cc);
                            N.getConcept().getSimplifyExtent().add(cc);
                            lesObjsTrie.remove(cc);
                            j--;
                        }
                    } else
                    if(binRel.getF(cc).union(binRel.getF(e)).equals(binRel.getF(cc)))
                        N.getConcept().getExtent().add(cc);
                }

                Classify(N, SOP, false);
            }
        }

    }
}
