// Decompiled by Jad v1.5.7g. Copyright 2000 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/SiliconValley/Bridge/8617/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi 
// Source File Name:   DiattaAlgorithm.java

package lattice.rules.algorithms;

import java.io.PrintStream;
import java.util.TreeSet;
import java.util.Vector;
import lattice.gui.tooltask.AbstractJob;
import lattice.gui.tooltask.JobObserver;
import lattice.rules.util.*;
import lattice.util.*;

// Referenced classes of package lattice.rules.algorithms:
//            AbstractAlgorithm

public class DiattaAlgorithm extends AbstractAlgorithm
{

    public static final int EXACT_RULES = 0;
    public static final int APPROXIMATIVE_RULES = 1;
    public static final int BOTH = 2;
    protected double alpha;
    protected Vector cl0;
    protected Vector cl0items;
    protected Vector CG1;
    protected RuleSet ARB;

    public DiattaAlgorithm(BinaryRelation br, double minConfiance, double minSupport)
    {
        super(br, minConfiance, minSupport);
        cl0 = new Vector();
        cl0items = new Vector();
        ARB = new RuleSet();
        alpha = minSupport * (double)br.getObjectsNumber();
    }

    public DiattaAlgorithm(BinaryRelation br, double minConfiance, double minSupport, int choice)
    {
        super(br, minConfiance, minSupport);
        cl0 = new Vector();
        cl0items = new Vector();
        ARB = new RuleSet();
        alpha = minSupport * (double)br.getObjectsNumber();
    }

    public BinaryRelation getBinaryRelation()
    {
        return binRel;
    }

    public void setBinaryRelation(BinaryRelation br)
    {
        binRel = br;
        alpha = minSupport * (double)br.getObjectsNumber();
    }

    public void doAlgorithm()
    {
        super.doAlgorithm();
        System.out.println("alpha = " + alpha);
        CG1 = genererCG1();
        processLuxemburger(new Motif());
        if(CG1.size() > 0)
        {
            if(cl0.size() > 0)
                processCl0();
            genererERB(CG1);
            Vector CGn = CG1;
            for(int n = 2; CGn.size() > 0; n++)
            {
                genererARB(CGn);
                CGn = genererNmotifsGenerateur(CGn);
                genererERB(CGn);
            }

            affResults();
        }
    }

    public void affResults()
    {
        resultat.append("Approximative rule Base (Luxemburger):\n");
        resultat.append(ARB.toString() + "\n");
        resultat.append("Exact rule Base (Guiges-Duquenne):\n");
        resultat.append(ruleSet.toString() + "\n");
    }

    public void genererERB(Vector CGn)
    {
        for(int i = 0; i < CGn.size(); i++)
        {
            Motif m = (Motif)CGn.get(i);
            Motif ferme = m.fermeture(FI1());
            TreeSet t = ferme.difference(m);
            if(t.size() != 0 && critique(m))
                ruleSet.add(new Regle(m, t, (double)m.getSupport() / (double)binRel.getObjectsNumber()));
        }

    }

    public boolean critique(Motif m)
    {
        for(int i = 0; i < ruleSet.size(); i++)
        {
            Regle r = ruleSet.get(i);
            if(m.containsAll(r.getAntecedent()) && !m.containsAll(r.getConsequence()))
                return false;
        }

        return true;
    }

    public Vector ensFermes(Vector ensMotifs)
    {
        Vector t = new Vector();
        for(int i = 0; i < ensMotifs.size(); i++)
            t.add(((Motif)ensMotifs.get(i)).fermeture(FI1()));

        return t;
    }

    public Vector genererCG1()
    {
        Vector CG1 = new Vector();
        Vector v = binRel.getAttributes();
        for(int i = 0; i < binRel.getAttributesNumber(); i++)
        {
            FormalAttribute f = (FormalAttribute)v.elementAt(i);
            f.initExtension(binRel.getG(f), binRel);
            Motif m = new Motif(f);
            m.initExtension();
            int support = m.getSupport();
            if(support == binRel.getObjectsNumber())
            {
                cl0items.add(f);
                cl0.add(m);
            }
            if((double)support >= alpha)
                CG1.add(m);
        }

        return CG1;
    }

    public void processCl0()
    {
        Regle r = new Regle(new TreeSet(), new TreeSet(cl0items));
        ruleSet.add(r);
    }

    public void processLuxemburger(Motif m)
    {
        Motif premisse = null;
        if(m.isEmpty())
            premisse = new Motif(cl0items);
        else
            premisse = m.fermeture(FI1());
        TreeSet consequent = null;
        for(int i = 0; i < CG1.size(); i++)
        {
            Motif mi = (Motif)CG1.get(i);
            if(!premisse.containsAll(mi))
            {
                Motif mUmi = m.union(mi);
                double support = (double)mUmi.getSupport() / (double)binRel.getObjectsNumber();
                if(support >= minSupport)
                {
                    double confiance;
                    if(m.isEmpty())
                        confiance = (double)mi.getSupport() / (double)binRel.getObjectsNumber();
                    else
                        confiance = (double)mUmi.getSupport() / (double)m.getSupport();
                    if(confiance >= minConfiance && testCouverture(m, mi))
                    {
                        if(m.isEmpty())
                            consequent = mi.fermeture(FI1()).difference(premisse);
                        else
                            consequent = mUmi.fermeture(FI1()).difference(premisse);
                        ARB.add(new Regle(premisse, consequent, support, confiance));
                    }
                }
            }
        }

    }

    public boolean testCouverture(Motif p, Motif x)
    {
        Motif pUx = p.union(x);
        for(int j = 0; j < CG1.size(); j++)
        {
            Motif y = (Motif)CG1.get(j);
            if(!x.equals(y))
            {
                Motif pUy = p.union(y);
                Motif pUxUy = pUx.union(y);
                if(p.isEmpty())
                    return true;
                if(!p.fermeture(FI1()).containsAll(y) && pUx.fermeture(FI1()).containsAll(y) && !pUy.fermeture(FI1()).containsAll(x))
                    return false;
            }
        }

        return true;
    }

    public void genererARB(Vector CGn)
    {
        for(int i = 0; i < CGn.size(); i++)
        {
            Motif m = (Motif)CGn.get(i);
            processLuxemburger(m);
        }

    }

    public Vector generer2motifsFrequent()
    {
        Vector ens2motifs = new Vector();
        System.out.println("*** Ensemble des fermes dont les generateurs sont les 1-motifs frequents ***");
        for(int i = 0; i < CG1.size(); i++)
        {
            for(int j = i + 1; j < CG1.size(); j++)
            {
                Motif m = (Motif)((Motif)CG1.get(i)).clone();
                m.addMotif((Motif)CG1.get(j));
                if((double)m.getSupport() >= alpha)
                {
                    System.out.println(m.fermeture(FI1()));
                    ens2motifs.add(m);
                }
            }

        }

        return ens2motifs;
    }

    public Vector FI1()
    {
        if(cl0.size() == 0)
        {
            return CG1;
        } else
        {
            Vector FI1 = (Vector)CG1.clone();
            FI1.addAll(cl0);
            return FI1;
        }
    }

    public Vector genererNmotifsGenerateur(Vector CGn)
    {
        Vector result = new Vector();
        for(int i = 0; i < CGn.size(); i++)
        {
            for(int j = 0; j < CG1.size(); j++)
            {
                Motif mni = (Motif)CGn.get(i);
                Motif m1j = (Motif)CG1.get(j);
                if(!mni.containsAll(m1j))
                {
                    Motif m = (Motif)mni.clone();
                    m.addMotif(m1j);
                    if((double)m.getSupport() >= alpha && !result.contains(m) && estGenerateur(m, CGn))
                        result.add(m);
                }
            }

        }

        return result;
    }

    public boolean estGenerateur(Motif m, Vector CGn)
    {
        int k = 0;
        for(int i = 0; i < CGn.size() && k < m.size(); i++)
        {
            Motif mi = (Motif)CGn.get(i);
            if(m.containsAll(mi))
            {
                k++;
                if(m.getSupport() >= mi.getSupport())
                    return false;
            }
        }

        return true;
    }

    public int spc(FormalAttribute x)
    {
        return 1;
    }

    public String getDescription()
    {
        String desc = new String("Diatta algorithm (J. Diatta & D. Grosser - IREMIA, 2003) ");
        return desc;
    }

    public void run()
    {
        doAlgorithm();
        if(jobObserv != null)
            jobObserv.jobEnd(true);
    }
}
