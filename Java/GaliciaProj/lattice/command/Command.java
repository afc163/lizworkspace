// Decompiled by Jad v1.5.7g. Copyright 2000 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/SiliconValley/Bridge/8617/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi 
// Source File Name:   Command.java

package lattice.command;

import java.util.StringTokenizer;
import java.util.Vector;

public abstract class Command
{

    String function;
    Vector args;

    public Command()
    {
        args = new Vector();
    }

    public abstract void eval(String s);

    public void parsing(String s)
    {
        StringTokenizer st = new StringTokenizer(s, " ");
        if(st.hasMoreTokens())
        {
            String tempo = st.nextToken();
            if(tempo.equals(">"))
                st.nextToken();
            for(; st.hasMoreTokens(); args.add(st.nextToken()));
        }
    }
}
