// Decompiled by Jad v1.5.7g. Copyright 2000 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/SiliconValley/Bridge/8617/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi 
// Source File Name:   Luxemburger.java

package lattice.rules.algorithms;

import java.util.*;
import lattice.gui.tooltask.AbstractJob;
import lattice.gui.tooltask.JobObserver;
import lattice.rules.util.Regle;
import lattice.rules.util.RuleSet;
import lattice.util.*;

// Referenced classes of package lattice.rules.algorithms:
//            AbstractAlgorithm

public class Luxemburger extends AbstractAlgorithm
{

    public Luxemburger(ConceptLattice treillis, double minConfiance, double minSupport)
    {
        super(treillis, minConfiance, minSupport);
    }

    public SortedSet reductionConsequence(SortedSet consequenceNonReduite, SortedSet antecedent)
    {
        SortedSet consequenceReduit = new TreeSet();
        for(Iterator it = consequenceNonReduite.iterator(); it.hasNext();)
        {
            Object item = it.next();
            if(!antecedent.contains(item))
                consequenceReduit.add(item);
        }

        return consequenceReduit;
    }

    public Vector generationBaseCouvertureNoeud(Node noeudTreillis, float nombreObjets)
    {
        List predecesseursImmediats = noeudTreillis.getChildren();
        if(predecesseursImmediats.size() != 0 && noeudTreillis.getConcept().getIntent().size() != 0)
        {
            float supportAntecedent = (float)noeudTreillis.getConcept().getExtent().size() / nombreObjets;
            for(Iterator it1 = predecesseursImmediats.iterator(); it1.hasNext();)
            {
                Node consequenceRegle = (Node)it1.next();
                float supportConsequence = (float)consequenceRegle.getConcept().getExtent().size() / nombreObjets;
                double confianceRegle = supportConsequence / supportAntecedent;
                if(confianceRegle >= minConfiance)
                {
                    Regle nouvelleRegle = new Regle(noeudTreillis.getConcept().getIntent(), reductionConsequence(consequenceRegle.getConcept().getIntent(), noeudTreillis.getConcept().getIntent()), supportConsequence, confianceRegle);
                    ruleSet.add(nouvelleRegle);
                }
            }

        }
        return ruleSet.getRuleSet();
    }

    public void doAlgorithm()
    {
        super.doAlgorithm();
        int nombreObjets = lattice.getTop().getConcept().getExtent().size();
        int nbMaxNode = lattice.get_nbr_concept();
        int numNodeCurrent = 0;
        for(Iterator it1 = lattice.iterator(); it1.hasNext(); sendJobPercentage((numNodeCurrent * 100) / nbMaxNode))
        {
            Node noeudCourant = (Node)it1.next();
            generationBaseCouvertureNoeud(noeudCourant, nombreObjets);
            numNodeCurrent++;
        }

        resultat.append(ruleSet.toString() + "\n");
    }

    public String getDescription()
    {
        return new String("Luxemburger Base");
    }

    public void run()
    {
        doAlgorithm();
        if(jobObserv != null)
            jobObserv.jobEnd(true);
    }
}
