// Decompiled by Jad v1.5.7g. Copyright 2000 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/SiliconValley/Bridge/8617/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi 
// Source File Name:   RuleSet.java

package lattice.rules.util;

import java.util.Vector;

// Referenced classes of package lattice.rules.util:
//            Regle

public class RuleSet
{

    protected Vector ruleSet;

    public RuleSet()
    {
        ruleSet = new Vector();
    }

    public RuleSet(Vector v)
    {
        ruleSet = v;
    }

    public Vector getRuleSet()
    {
        return ruleSet;
    }

    public void setRuleSet(Vector v)
    {
        ruleSet = v;
    }

    public void add(Regle r)
    {
        if(!contains(r))
            ruleSet.add(r);
    }

    public boolean contains(Regle r)
    {
        for(int i = 0; i < ruleSet.size(); i++)
            if(r.equals((Regle)ruleSet.get(i)))
                return true;

        return false;
    }

    public Regle get(int i)
    {
        return (Regle)ruleSet.get(i);
    }

    public int size()
    {
        return ruleSet.size();
    }

    public String toString()
    {
        String result = new String();
        for(int i = 0; i < ruleSet.size(); i++)
        {
            Regle r = (Regle)ruleSet.get(i);
            result = result + "R" + i + " : " + r.toString() + "\n";
        }

        return result;
    }
}
