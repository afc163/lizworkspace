// Decompiled by Jad v1.5.7g. Copyright 2000 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/SiliconValley/Bridge/8617/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi 
// Source File Name:   Regle.java

package lattice.rules.util;

import java.util.*;

// Referenced classes of package lattice.rules.util:
//            Motif

public class Regle
{

    SortedSet antecedent;
    SortedSet consequence;
    double support;
    double confiance;

    public Regle(SortedSet antecedent, SortedSet consequence, double support, double confiance)
    {
        this.support = 1.0D;
        this.confiance = 1.0D;
        this.antecedent = antecedent;
        this.consequence = consequence;
        this.support = support;
        this.confiance = confiance;
    }

    public Regle(SortedSet antecedent, SortedSet consequence)
    {
        support = 1.0D;
        confiance = 1.0D;
        this.antecedent = antecedent;
        this.consequence = consequence;
    }

    public Regle(Motif antecedent, SortedSet consequence, double support)
    {
        this.support = 1.0D;
        confiance = 1.0D;
        this.antecedent = antecedent.getItems();
        this.consequence = consequence;
        this.support = support;
        confiance = 1.0D;
    }

    public Regle(Motif antecedent, SortedSet consequence, double support, double confiance)
    {
        this.support = 1.0D;
        this.confiance = 1.0D;
        this.antecedent = antecedent.getItems();
        this.consequence = consequence;
        this.support = support;
        this.confiance = confiance;
    }

    public double getConfiance()
    {
        return confiance;
    }

    public void setConfiance(double confiance)
    {
        this.confiance = confiance;
    }

    public double getSupport()
    {
        return support;
    }

    public void setSupport(double support)
    {
        this.support = support;
    }

    public boolean equals(Regle r)
    {
        return antecedent.equals(r.antecedent) && consequence.equals(r.consequence);
    }

    public SortedSet getAntecedent()
    {
        return antecedent;
    }

    public void setAntecedent(SortedSet antecedent)
    {
        this.antecedent = antecedent;
    }

    public SortedSet getConsequence()
    {
        return consequence;
    }

    public void setConsequence(SortedSet consequence)
    {
        this.consequence = consequence;
    }

    public String toString()
    {
        String s = new String();
        SortedSet antecedent = getAntecedent();
        Iterator it2 = antecedent.iterator();
        SortedSet consequence = getConsequence();
        Iterator it3 = consequence.iterator();
        while(it2.hasNext()) 
        {
            String itemCourantant = it2.next().toString();
            s = s + (itemCourantant = itemCourantant + " ");
        }
        String itemCourantcons;
        for(s = s + " --> "; it3.hasNext(); s = s + itemCourantcons + " ")
            itemCourantcons = it3.next().toString();

        s = s + "\t\t";
        s = s + "(S = ";
        String sup = Double.toString((double)(int)(getSupport() * 100D) / 100D);
        s = s + sup;
        s = s + "% ; ";
        s = s + "C = ";
        String c = Double.toString((double)(int)(getConfiance() * 100D) / 100D);
        s = s + c;
        s = s + ")";
        return s;
    }
}
