// Decompiled by Jad v1.5.7g. Copyright 2000 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/SiliconValley/Bridge/8617/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi 
// Source File Name:   AbstractReader.java

package lattice.io;

import java.io.BufferedReader;
import java.io.Reader;
import lattice.gui.tooltask.AbstractJob;

public abstract class AbstractReader extends AbstractJob
{

    Object data;
    protected String defaultNameForData;
    private BufferedReader buff;
    protected int nbObjToRead;
    protected int nbAttToRead;

    public AbstractReader(Reader r)
    {
        data = null;
        defaultNameForData = "Default Name";
        buff = new BufferedReader(r);
    }

    public BufferedReader getStream()
    {
        return buff;
    }

    public String getDescription()
    {
        return "Reading Job";
    }

    public Object getData()
    {
        return data;
    }

    public void setDefaultNameForData(String aName)
    {
        defaultNameForData = aName;
    }

    public String getDefaultNameForData()
    {
        return defaultNameForData;
    }
}
