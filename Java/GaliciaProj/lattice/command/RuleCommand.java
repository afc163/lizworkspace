// Decompiled by Jad v1.5.7g. Copyright 2000 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/SiliconValley/Bridge/8617/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi 
// Source File Name:   RuleCommand.java

package lattice.command;


// Referenced classes of package lattice.command:
//            Command

public class RuleCommand extends Command
{

    public RuleCommand()
    {
    }

    public void eval(String s)
    {
        parsing(s);
    }
}
