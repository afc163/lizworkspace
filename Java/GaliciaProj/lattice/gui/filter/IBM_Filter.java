// Decompiled by Jad v1.5.7g. Copyright 2000 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/SiliconValley/Bridge/8617/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi 
// Source File Name:   IBM_Filter.java

package lattice.gui.filter;


// Referenced classes of package lattice.gui.filter:
//            AbstractFilter

public class IBM_Filter extends AbstractFilter
{

    public IBM_Filter()
    {
    }

    public String getDescription()
    {
        return "IBM (ascii) raw data : *" + getFileExtension();
    }

    public String getFileExtension()
    {
        return ".txt";
    }
}