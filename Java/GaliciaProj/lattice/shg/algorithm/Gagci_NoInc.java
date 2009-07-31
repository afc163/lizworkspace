// Decompiled by Jad v1.5.7g. Copyright 2000 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/SiliconValley/Bridge/8617/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi 
// Source File Name:   Gagci_NoInc.java

package lattice.shg.algorithm;

import java.util.*;
import lattice.algorithm.LatticeAlgorithm;
import lattice.gui.RelationalContextEditor;
import lattice.gui.tooltask.AbstractJob;
import lattice.gui.tooltask.JobObserver;
import lattice.util.*;

// Referenced classes of package lattice.shg.algorithm:
//            CERES

public class Gagci_NoInc extends LatticeAlgorithm
{

    private RelationalContextEditor editor;
    private Vector setOfEnrichingRelations;
    private Vector SetOfRelationalAttributes;
    private Hashtable setOfSHG;

    public Gagci_NoInc()
    {
        editor = null;
        setOfEnrichingRelations = null;
        SetOfRelationalAttributes = null;
        setOfSHG = null;
    }

    public Gagci_NoInc(BinaryRelation bRel)
    {
        super(bRel);
        editor = null;
        setOfEnrichingRelations = null;
        SetOfRelationalAttributes = null;
        setOfSHG = null;
    }

    public Gagci_NoInc(BinaryRelation bRel, Vector setOfEnrichingRelations, Vector SetOfRelationalAttributes, Hashtable setOfInitGraph, RelationalContextEditor editor)
    {
        super(bRel);
        this.editor = null;
        this.setOfEnrichingRelations = null;
        this.SetOfRelationalAttributes = null;
        setOfSHG = null;
        this.setOfEnrichingRelations = setOfEnrichingRelations;
        this.SetOfRelationalAttributes = SetOfRelationalAttributes;
        setOfSHG = setOfInitGraph;
        this.editor = editor;
    }

    public void doAlgorithm()
    {
        doGagciNoInc();
    }

    public void doGagciNoInc()
    {
        boolean fixPoint = false;
        for(int nbIter = 1; !fixPoint; nbIter++)
        {
            sendJobPercentage(0);
            BinaryRelation Ki = null;
            InterObjectBinaryRelation aRelj_Ki = null;
            Vector theRelj_Ki = null;
            boolean shgModif = false;
            for(int i = 0; i < setOfEnrichingRelations.size(); i++)
            {
                Ki = (BinaryRelation)((BinaryRelation)setOfEnrichingRelations.elementAt(i)).clone();
                if(jobObserv != null)
                    jobObserv.sendMessage(Ki.getRelationName() + " is Considered\n");
                for(Enumeration enum = ((Hashtable)SetOfRelationalAttributes.elementAt(i)).keys(); enum.hasMoreElements();)
                {
                    String theKj_Name = enum.nextElement().toString();
                    theRelj_Ki = (Vector)((Hashtable)SetOfRelationalAttributes.elementAt(i)).get(theKj_Name);
                    for(int j = 0; j < theRelj_Ki.size(); j++)
                    {
                        aRelj_Ki = (InterObjectBinaryRelation)theRelj_Ki.elementAt(j);
                        complete(Ki, aRelj_Ki, (LatticeGraph)setOfSHG.get(theKj_Name));
                    }

                    if(jobObserv != null)
                        jobObserv.sendMessage(Ki.getRelationName() + "=>" + theKj_Name + " Relational Attributes is Completed\n");
                }

                CERES anAlgo = new CERES(Ki);
                anAlgo.doAlgorithm();
                if(jobObserv != null)
                    jobObserv.sendMessage(Ki.getRelationName() + " CERES is done\n");
                if(setOfSHG.get(Ki.getRelationName()) == null)
                {
                    shgModif = true;
                    setOfSHG.put(Ki.getRelationName(), anAlgo.getLattice());
                } else
                {
                    shgModif = isModified((LatticeGraph)setOfSHG.get(Ki.getRelationName()), (LatticeGraph)anAlgo.getLattice());
                    setOfSHG.put(Ki.getRelationName(), anAlgo.getLattice());
                }
                Ki.setRelationName(Ki.getRelationName() + "_" + nbIter);
                if(editor != null)
                    editor.addBinaryRelation(Ki);
                sendJobPercentage(((i + 1) * 100) / setOfEnrichingRelations.size());
            }

            if(!shgModif)
                fixPoint = true;
            if(jobObserv != null)
                jobObserv.sendMessage("Gagci_NoInc Num iteration = " + nbIter + "\n");
        }

        lcl = (LatticeGraph)setOfSHG.get(binRel.getRelationName());
        binRel.setLattice((LatticeGraph)setOfSHG.get(binRel.getRelationName()));
        for(int i = 0; i < setOfEnrichingRelations.size(); i++)
            ((BinaryRelation)setOfEnrichingRelations.elementAt(i)).setLattice((LatticeGraph)setOfSHG.get(((BinaryRelation)setOfEnrichingRelations.elementAt(i)).getRelationName()));

    }

    private boolean isModified(LatticeGraph firstG, LatticeGraph secondG)
    {
        return firstG.getAllNodes().size() != secondG.getAllNodes().size() || isModifiedRec(firstG.getTop(), secondG.getTop());
    }

    private boolean isModifiedRec(Node firstN, Node secondN)
    {
        boolean res = true;
        if(firstN.getConcept().getSimplifyIntent().size() == secondN.getConcept().getSimplifyIntent().size())
        {
            res = false;
            if(firstN.getChildren().size() == secondN.getChildren().size())
            {
                Iterator it = firstN.getChildren().iterator();
                Iterator it2 = secondN.getChildren().iterator();
                while(it.hasNext()) 
                {
                    Node N1 = (Node)it.next();
                    Node N2 = (Node)it2.next();
                    res = res || isModifiedRec(N1, N2);
                }
            }
        }
        return res;
    }

    private void complete(BinaryRelation Ki, InterObjectBinaryRelation Relj_Ki, LatticeGraph aShg)
    {
        for(int i = 0; i < Ki.getFormalObjects().length; i++)
        {
            FormalObject foKi = Ki.getFormalObjects()[i];
            for(Iterator it = Relj_Ki.getF(Ki.getFormalObjects()[i]).iterator(); it.hasNext();)
            {
                FormalObject foa = ((InterObjectBinaryRelationAttribute)it.next()).getObject();
                if(aShg != null)
                {
                    for(Iterator it2 = aShg.getAllNodes().iterator(); it2.hasNext();)
                    {
                        Node N = (Node)it2.next();
                        if(N.getConcept().getExtent().contains(foa) && (aShg.getTop() != N || N.getConcept().getSimplifyExtent().size() != 0 || N.getConcept().getSimplifyIntent().size() != 0))
                        {
                            lattice.util.FormalAttribute fa = new DefaultFormalAttribute(Relj_Ki.getRelationName() + "=Node_" + N.getId());
                            if(!Ki.containsFormalAttribute(fa))
                                Ki.addAttribute(fa);
                            Ki.setRelation(Ki.indexOfFormalObject(foKi), Ki.indexOfFormalAttribute(fa), true);
                        }
                    }

                } else
                {
                    lattice.util.FormalAttribute fa = new DefaultFormalAttribute(Relj_Ki.getRelationName() + "=" + foa.toString());
                    if(!Ki.containsFormalAttribute(fa))
                        Ki.addAttribute(fa);
                    Ki.setRelation(Ki.indexOfFormalObject(foKi), Ki.indexOfFormalAttribute(fa), true);
                }
            }

        }

    }

    public String getDescription()
    {
        return "Algorithm GAGCI no Incremental";
    }

    public Vector getSetOfEnrichingRelations()
    {
        return setOfEnrichingRelations;
    }

    public Vector getSetOfRelationalAttributes()
    {
        return SetOfRelationalAttributes;
    }

    public void setSetOfEnrichingRelations(Vector setOfEnrichingRelations)
    {
        this.setOfEnrichingRelations = setOfEnrichingRelations;
    }

    public void setSetOfRelationalAttributes(Vector setOfRelationalAttributes)
    {
        SetOfRelationalAttributes = setOfRelationalAttributes;
    }
}
