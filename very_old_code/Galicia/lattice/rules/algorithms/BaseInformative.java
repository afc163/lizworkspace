// Decompiled by Jad v1.5.7g. Copyright 2000 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/SiliconValley/Bridge/8617/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi 
// Source File Name:   BaseInformative.java

package lattice.rules.algorithms;

import java.util.*;
import lattice.gui.tooltask.AbstractJob;
import lattice.gui.tooltask.JobObserver;
import lattice.rules.util.Regle;
import lattice.rules.util.RuleSet;
import lattice.util.*;

// Referenced classes of package lattice.rules.algorithms:
//            AbstractAlgorithm

public class BaseInformative extends AbstractAlgorithm
{

    public BaseInformative(ConceptLattice treillis, double minConfiance)
    {
        super(treillis, minConfiance, 1.0D);
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

    public void generationRegleNoeud(Node noeudTreillis, float nombreObjets)
    {
        float supportAntecedent = (float)noeudTreillis.getConcept().getExtent().size() / nombreObjets;
        for(Iterator generateurNoeud = noeudTreillis.getConcept().getGenerator().iterator(); generateurNoeud.hasNext();)
        {
            Intent generateurCourant = (Intent)generateurNoeud.next();
            List predecesseursImmediats = noeudTreillis.getChildren();
            if(predecesseursImmediats.size() != 0 && noeudTreillis.getConcept().getIntent().size() != 0)
            {
                for(Iterator it1 = predecesseursImmediats.iterator(); it1.hasNext();)
                {
                    Node pred = (Node)it1.next();
                    float supportConsequence = (float)pred.getConcept().getExtent().size() / nombreObjets;
                    SortedSet consequencePotentielle = reductionConsequence(pred.getConcept().getIntent(), generateurCourant);
                    double confianceRegle = supportConsequence / supportAntecedent;
                    if(consequencePotentielle.size() != 0 && confianceRegle >= minConfiance)
                    {
                        Regle nouvelleRegle = new Regle(generateurCourant, consequencePotentielle, supportConsequence, confianceRegle);
                        ruleSet.add(nouvelleRegle);
                    }
                }

            }
        }

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
            generationRegleNoeud(noeudCourant, nombreObjets);
            numNodeCurrent++;
        }

        resultat.append(ruleSet.toString() + "\n");
    }

    public String getDescription()
    {
        return new String("Base informative");
    }

    public void run()
    {
        doAlgorithm();
        if(jobObserv != null)
            jobObserv.jobEnd(true);
    }
}
