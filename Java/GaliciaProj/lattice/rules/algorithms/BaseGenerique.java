// Decompiled by Jad v1.5.7g. Copyright 2000 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/SiliconValley/Bridge/8617/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi 
// Source File Name:   BaseGenerique.java

package lattice.rules.algorithms;

import java.util.*;
import lattice.gui.tooltask.AbstractJob;
import lattice.gui.tooltask.JobObserver;
import lattice.rules.util.Regle;
import lattice.rules.util.RuleSet;
import lattice.util.*;

// Referenced classes of package lattice.rules.algorithms:
//            AbstractAlgorithm

public class BaseGenerique extends AbstractAlgorithm
{

    public BaseGenerique(ConceptLattice treillis, double minConfiance)
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

    public Vector getBase()
    {
        return ruleSet.getRuleSet();
    }

    public void generationbaseGeneriqueNoeud(Node noeudTreillis, float nombreObjets)
    {
        if(noeudTreillis.getConcept().getIntent().size() > 1)
        {
            for(Iterator generateurNoeud = noeudTreillis.getConcept().getGenerator().iterator(); generateurNoeud.hasNext();)
            {
                Intent generateurCourant = (Intent)generateurNoeud.next();
                double supportRegle = (double)noeudTreillis.getConcept().getExtent().size() / (double)nombreObjets;
                SortedSet consequencePotentielle = reductionConsequence(noeudTreillis.getConcept().getIntent(), generateurCourant);
                if(consequencePotentielle.size() != 0)
                {
                    Regle nouvelleRegle = new Regle(generateurCourant, consequencePotentielle, supportRegle, 1.0D);
                    ruleSet.add(nouvelleRegle);
                }
            }

        }
    }

    public int size()
    {
        return ruleSet.size();
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
            generationbaseGeneriqueNoeud(noeudCourant, nombreObjets);
            numNodeCurrent++;
        }

        resultat.append(ruleSet.toString() + "\n");
    }

    public String getDescription()
    {
        return new String("Generic Base Algorithm");
    }

    public void run()
    {
        doAlgorithm();
        if(jobObserv != null)
            jobObserv.jobEnd(true);
    }
}
