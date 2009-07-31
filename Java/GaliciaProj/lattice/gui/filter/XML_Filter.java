// Decompiled by Jad v1.5.7g. Copyright 2000 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/SiliconValley/Bridge/8617/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi 
// Source File Name:   XML_Filter.java

package lattice.gui.filter;


// Referenced classes of package lattice.gui.filter:
//            AbstractFilter

public class XML_Filter extends AbstractFilter
{

    private String prefix;

    public XML_Filter(String prefix)
    {
        this.prefix = null;
        this.prefix = prefix;
    }

    public String getDescription()
    {
        return "XML file : *" + getFileExtension();
    }

    public String getFileExtension()
    {
        return prefix + ".xml";
    }
}
