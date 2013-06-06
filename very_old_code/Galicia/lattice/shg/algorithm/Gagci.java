// Decompiled by Jad v1.5.7g. Copyright 2000 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/SiliconValley/Bridge/8617/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi 
// Source File Name:   Gagci.java

package lattice.shg.algorithm;

import java.io.PrintStream;
import java.util.*;
import lattice.algorithm.LatticeAlgorithm;
import lattice.gui.tooltask.AbstractJob;
import lattice.gui.tooltask.JobObserver;
import lattice.util.*;

// Referenced classes of package lattice.shg.algorithm:
//            CERES

public class Gagci extends LatticeAlgorithm
{

    BinaryRelation classRel;
    BinaryRelation assocRel;
    InterObjectBinaryRelation lesIORel[];
    LatticeGraph classLG;
    boolean isClassLGModif;
    LatticeGraph assocLG;
    boolean isAssocLGModif;

    public Gagci()
    {
        isClassLGModif = false;
        isAssocLGModif = false;
    }

    public Gagci(BinaryRelation cRel, BinaryRelation aRel, InterObjectBinaryRelation iobr[])
    {
        super(cRel);
        isClassLGModif = false;
        isAssocLGModif = false;
        classRel = cRel;
        assocRel = aRel;
        lesIORel = iobr;
    }

    public void doAlgorithm()
    {
        execAlgo();
        jobObserv.jobEnd(true);
    }

    public String getDescription()
    {
        return "GAGCI algo.";
    }

    public void execAlgo()
    {
        LatticeAlgorithm algo = new CERES(classRel);
        if(jobObserv != null)
            jobObserv.setTitle(getDescription() + " - 1st step CERES on Class");
        algo.setJobObserver(jobObserv);
        algo.doAlgorithm();
        if(jobObserv != null)
            jobObserv.setPercentageOfWork(100);
        classLG = (LatticeGraph)algo.getLattice();
        algo = new CERES(assocRel);
        if(jobObserv != null)
            jobObserv.setPercentageOfWork(0);
        if(jobObserv != null)
            jobObserv.setTitle(getDescription() + " - 1st step CERES on Assocation");
        algo.setJobObserver(jobObserv);
        algo.doAlgorithm();
        if(jobObserv != null)
            jobObserv.setPercentageOfWork(100);
        assocLG = (LatticeGraph)algo.getLattice();
        Vector newClassNodes = new Vector();
        newClassNodes = (Vector)((Vector)classLG.getAllNodes()).clone();
        newClassNodes.remove(classLG.getTop());
        newClassNodes.remove(classLG.getBottom());
        Vector newAssocNodes = new Vector();
        newAssocNodes = (Vector)((Vector)assocLG.getAllNodes()).clone();
        newAssocNodes.remove(assocLG.getTop());
        newAssocNodes.remove(assocLG.getBottom());
        FormalObject classObjs[] = classRel.getFormalObjects();
        FormalObject assocObjs[] = assocRel.getFormalObjects();
        Iterator it = null;
        Iterator it2 = null;
        Vector Q = null;
        Node N = null;
        LatticeNode child = null;
        Extent newE[] = new SetExtent[lesIORel.length];
        lattice.util.Intent singleInt = null;
        Vector lesPreconcepts = new Vector();
        boolean premierTour = true;
        int nbIter = 0;
        for(; newClassNodes.size() != 0 || newAssocNodes.size() != 0; System.out.println("nbIter=" + nbIter))
        {
            lesPreconcepts.removeAllElements();
            for(int i = 0; i < newAssocNodes.size(); i++)
            {
                N = (Node)newAssocNodes.elementAt(i);
                for(int j = 0; j < lesIORel.length; j++)
                    newE[j] = new SetExtent();

                for(it = N.getConcept().getExtent().iterator(); it.hasNext();)
                {
                    FormalObject objA = (FormalObject)it.next();
                    for(int j = 0; j < lesIORel.length; j++)
                    {
                        Extent lesObjRel = lesIORel[j].getG(new InterObjectBinaryRelationAttribute(objA));
                        newE[j] = newE[j].union(lesObjRel);
                    }

                }

                for(int j = 0; j < lesIORel.length; j++)
                    if(newE[j].size() != 0)
                    {
                        singleInt = new SetIntent();
                        singleInt.add(new GagciRelationalAttribute(lesIORel[j], N.getConcept()));
                        Concept newC = new Concept(newE[j], singleInt);
                        for(it = singleInt.iterator(); it.hasNext(); newC.getSimplifyIntent().add(it.next()));
                        lesPreconcepts.add(newC);
                    }

            }

            if(premierTour)
            {
                newClassNodes.addAll(incremente(classLG, lesPreconcepts));
                premierTour = false;
            } else
            {
                newClassNodes = incremente(classLG, lesPreconcepts);
            }
            lesPreconcepts.removeAllElements();
            for(int i = 0; i < newClassNodes.size(); i++)
            {
                N = (Node)newClassNodes.elementAt(i);
                for(int j = 0; j < lesIORel.length; j++)
                    newE[j] = new SetExtent();

                for(it = N.getConcept().getExtent().iterator(); it.hasNext();)
                {
                    FormalObject objA = (FormalObject)it.next();
                    for(int j = 0; j < lesIORel.length; j++)
                    {
                        lattice.util.Intent lesAttRel = lesIORel[j].getF(objA);
                        Extent lesObjRel = new SetExtent();
                        for(it2 = lesAttRel.iterator(); it2.hasNext(); lesObjRel.add(((InterObjectBinaryRelationAttribute)it2.next()).getObject()));
                        newE[j] = newE[j].union(lesObjRel);
                    }

                }

                for(int j = 0; j < lesIORel.length; j++)
                    if(newE[j].size() != 0)
                    {
                        singleInt = new SetIntent();
                        singleInt.add(new GagciRelationalAttribute(lesIORel[j], N.getConcept()));
                        lesPreconcepts.add(new Concept(newE[j], singleInt));
                    }

            }

            newAssocNodes = incremente(assocLG, lesPreconcepts);
            nbIter++;
        }

        lcl = classLG;
    }

    private boolean fixePoint()
    {
        return !isClassLGModif && !isAssocLGModif;
    }

    private Vector incremente(LatticeGraph aGraph, Vector lesPreconcepts)
    {
        if(jobObserv != null)
            jobObserv.setTitle(getDescription() + " - Increment");
        sendJobPercentage(0);
        Vector newNodes = new Vector(lesPreconcepts.size(), 0);
        Vector lesSupC = new Vector(aGraph.getAllNodes().size(), 0);
        Vector lesInfC = new Vector(aGraph.getAllNodes().size(), 0);
        Vector Q = new Vector();
        for(int i = 0; i < lesPreconcepts.size(); i++)
        {
            Concept C = (Concept)lesPreconcepts.elementAt(i);
            System.out.println("Insertion de : " + C.toString());
            lesSupC.removeAllElements();
            Q.removeAllElements();
            Q.add(aGraph.getTop());
            LatticeNode CSC = null;
            LatticeNode child = null;
            ((LatticeNode)aGraph.getTop()).resetDegre();
            while(Q.size() != 0) 
            {
                CSC = (LatticeNode)Q.remove(0);
                lesSupC.add(CSC);
                for(Iterator it = CSC.getParents().iterator(); it.hasNext(); lesSupC.remove(it.next()));
                for(Iterator it = CSC.getChildren().iterator(); it.hasNext();)
                {
                    child = (LatticeNode)it.next();
                    if(child.getDegre() == -1)
                        child.setDegre(child.getParents().size());
                    child.setDegre(child.getDegre() - 1);
                    if(child.getDegre() == 0)
                    {
                        child.setDegre(-1);
                        if(child != aGraph.getBottom() && child.getConcept().getExtent().union(C.getExtent()).equals(child.getConcept().getExtent()))
                            Q.addElement(child);
                    }
                }

            }
            System.out.println("LesSupC = " + lesSupC + "\n");
            lesInfC.removeAllElements();
            Q.removeAllElements();
            Q.add(aGraph.getBottom());
            CSC = null;
            LatticeNode pere = null;
            ((LatticeNode)aGraph.getTop()).resetDegre();
            while(Q.size() != 0) 
            {
                CSC = (LatticeNode)Q.remove(0);
                lesInfC.add(CSC);
                for(Iterator it = CSC.getChildren().iterator(); it.hasNext(); lesInfC.remove(it.next()));
                for(Iterator it = C.getIntent().iterator(); it.hasNext(); CSC.getConcept().getIntent().add(it.next()));
                for(Iterator it = CSC.getParents().iterator(); it.hasNext();)
                {
                    pere = (LatticeNode)it.next();
                    if(pere.getDegre() == -1)
                        pere.setDegre(pere.getChildren().size());
                    pere.setDegre(pere.getDegre() - 1);
                    if(pere.getDegre() == 0)
                    {
                        pere.setDegre(-1);
                        if(pere != aGraph.getTop() && C.getExtent().union(pere.getConcept().getExtent()).equals(C.getExtent()))
                            Q.addElement(pere);
                    }
                }

            }
            System.out.println("LesInfC = " + lesInfC + "\n");
            if(lesSupC.size() == 1 && lesInfC.size() == 1 && lesSupC.elementAt(0) == lesInfC.elementAt(0))
            {
                FormalAttribute fa;
                for(Iterator it = C.getIntent().iterator(); it.hasNext(); ((LatticeNode)lesSupC.elementAt(0)).getConcept().getSimplifyIntent().add(fa))
                {
                    fa = (FormalAttribute)it.next();
                    ((LatticeNode)lesSupC.elementAt(0)).getConcept().getIntent().add(fa);
                }

            } else
            {
                for(int j = 0; j < lesSupC.size(); j++)
                {
                    pere = (LatticeNode)lesSupC.elementAt(j);
                    for(int k = 0; k < lesInfC.size(); k++)
                    {
                        child = (LatticeNode)lesInfC.elementAt(k);
                        child.removeParent(pere);
                        pere.removeChild(child);
                    }

                }

                Node N = new LatticeNode(C);
                for(int j = 0; j < lesSupC.size(); j++)
                {
                    pere = (LatticeNode)lesSupC.elementAt(j);
                    N.addParent(pere);
                    pere.addChild(N);
                    for(Iterator it = pere.getConcept().getSimplifyExtent().iterator(); it.hasNext();)
                    {
                        FormalObject fo = (FormalObject)it.next();
                        if(C.getExtent().contains(fo))
                            C.getSimplifyExtent().add(fo);
                    }

                    FormalObject fo;
                    for(Iterator it = C.getSimplifyExtent().iterator(); it.hasNext(); pere.getConcept().getSimplifyExtent().remove(fo))
                        fo = (FormalObject)it.next();

                    FormalAttribute fa;
                    for(Iterator it = pere.getConcept().getIntent().iterator(); it.hasNext(); C.getIntent().add(fa))
                        fa = (FormalAttribute)it.next();

                }

                for(int j = 0; j < lesInfC.size(); j++)
                {
                    child = (LatticeNode)lesInfC.elementAt(j);
                    child.addParent(N);
                    N.addChild(child);
                }

                newNodes.add(N);
            }
            sendJobPercentage((i * 100) / lesPreconcepts.size());
        }

        sendJobPercentage(100);
        return newNodes;
    }
}
