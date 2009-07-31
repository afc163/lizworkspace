// Decompiled by Jad v1.5.7g. Copyright 2000 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/SiliconValley/Bridge/8617/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi 
// Source File Name:   ruleAbstractTask.java

package lattice.rules.task;

import lattice.gui.RelationalContextEditor;
import lattice.gui.tooltask.AbstractTask;

public abstract class ruleAbstractTask extends AbstractTask
{

    protected RelationalContextEditor rce;
    protected String stringResult;

    public ruleAbstractTask()
    {
        rce = null;
    }

    public String getResultat()
    {
        return stringResult;
    }
}
