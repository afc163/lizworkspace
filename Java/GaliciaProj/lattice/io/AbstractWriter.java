// Decompiled by Jad v1.5.7g. Copyright 2000 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/SiliconValley/Bridge/8617/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi 
// Source File Name:   AbstractWriter.java

package lattice.io;

import java.io.BufferedWriter;
import java.io.Writer;
import lattice.gui.tooltask.AbstractJob;

public abstract class AbstractWriter extends AbstractJob
{

	//dataŒ™private¿‡–Õ
    Object data;
    protected BufferedWriter buff;

    public AbstractWriter(Writer w)
    {
        data = null;
        buff = new BufferedWriter(w);
    }

    public BufferedWriter getStream()
    {
        return buff;
    }

    public String getDescription()
    {
        return "Writing Job";
    }

    public void setData(Object dataToWrite)
    {
        data = dataToWrite;
    }

    public Object getData()
    {
        return data;
    }
}
