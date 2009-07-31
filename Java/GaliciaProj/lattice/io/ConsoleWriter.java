// Decompiled by Jad v1.5.7g. Copyright 2000 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/SiliconValley/Bridge/8617/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi 
// Source File Name:   ConsoleWriter.java

package lattice.io;

import java.io.*;
import lattice.gui.Console;
import lattice.gui.tooltask.AbstractJob;
import lattice.gui.tooltask.JobObserver;

// Referenced classes of package lattice.io:
//            AbstractWriter

public class ConsoleWriter extends AbstractWriter
{

    private Object data;

    public ConsoleWriter(BufferedWriter writer)
    {
        super(writer);
    }

    public String getDescription()
    {
        return "Console File Writer";
    }

    public void writeConsole(Console theCons)
        throws IOException
    {
        getStream().write(theCons.getText());
        getStream().flush();
    }

    public void run()
    {
        try
        {
            writeConsole((Console)data);
            getStream().close();
        }
        catch(IOException e)
        {
            e.printStackTrace();
            if(jobObserv != null)
            {
                sendMessage("Console Writing Error !");
                jobObserv.jobEnd(false);
            }
        }
        if(jobObserv != null)
            jobObserv.jobEnd(true);
    }

    public void setData(Object data)
    {
        this.data = data;
    }

    public Object getData()
    {
        return data;
    }
}
