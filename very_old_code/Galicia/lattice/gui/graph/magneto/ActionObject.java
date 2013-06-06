// Decompiled by Jad v1.5.7g. Copyright 2000 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/SiliconValley/Bridge/8617/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi 
// Source File Name:   ActionObject.java

package lattice.gui.graph.magneto;

import java.util.Vector;

// Referenced classes of package lattice.gui.graph.magneto:
//            Magnetable, Magneto

public abstract class ActionObject
{

    protected Vector magnetables;
    protected int level;
    protected int nbNiveau;
    protected Magneto magneto;
    protected int x;
    protected int y;
    protected int nbMagnetables;

    public ActionObject(Magneto magneto, int level, int nbNiveau)
    {
        x = 0;
        y = 0;
        this.magneto = magneto;
        this.level = level;
        this.nbNiveau = nbNiveau;
    }

    protected abstract boolean doAction(boolean flag);

    public void fix(boolean b)
    {
        for(int i = 0; i < nbMagnetables; i++)
            getMagnetable(i).setIsMagnetable(!b);

    }

    Magnetable getMagnetable(int i)
    {
        return (Magnetable)magnetables.elementAt(i);
    }

    protected void setMousePosition(int x, int y)
    {
        this.x = x;
        this.y = y;
    }

    protected boolean isFixedLevel()
    {
        if(level == 0)
            return true;
        if(magneto.fixFirstLevel() && level == 1)
            return true;
        if(magneto.fixLastLevel() && level == nbNiveau - 2)
            return true;
        return level == nbNiveau - 1;
    }
}
