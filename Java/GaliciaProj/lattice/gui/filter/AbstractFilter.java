// Decompiled by Jad v1.5.7g. Copyright 2000 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/SiliconValley/Bridge/8617/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi 
// Source File Name:   AbstractFilter.java

package lattice.gui.filter;

import java.io.File;
import javax.swing.filechooser.FileFilter;

public abstract class AbstractFilter extends FileFilter
{

    public AbstractFilter()
    {
    }

    public abstract String getDescription();

    public boolean accept(File f)
    {
        return f != null && (f.isDirectory() || f.getName().endsWith(getFileExtension()));
    }

    public abstract String getFileExtension();
}
