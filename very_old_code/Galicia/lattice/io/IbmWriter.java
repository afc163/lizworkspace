// Decompiled by Jad v1.5.7g. Copyright 2000 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/SiliconValley/Bridge/8617/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi 
// Source File Name:   IbmWriter.java

package lattice.io;

import java.io.*;
import lattice.gui.tooltask.AbstractJob;
import lattice.gui.tooltask.JobObserver;
import lattice.util.*;

// Referenced classes of package lattice.io:
//            AbstractWriter, BinaryRelationWriter

public class IbmWriter extends AbstractWriter
    implements BinaryRelationWriter
{

    public IbmWriter(Writer w)
    {
        super(w);
    }

    public void writeBinaryRelation(BinaryRelation binRel)
        throws IOException
    {
        lattice.util.FormalObject lesObjs[] = binRel.getFormalObjects();
        lattice.util.FormalAttribute lesAtts[] = binRel.getFormalAttributes();
        String line = "";
        for(int i = 0; i < lesObjs.length; i++)
        {
            line = "";
            int nbElem = 0;
            for(int j = 0; j < lesAtts.length; j++)
                if(!binRel.getRelation(i, j).isEmpty())
                {
                    line = line + (j + 1) + " ";
                    nbElem++;
                }

            line = nbElem + " " + line;
            getStream().write(line + "\n");
            getStream().write("\n");
            sendJobPercentage((i * 100) / lesObjs.length);
        }

        getStream().close();
        sendJobPercentage(100);
    }

    public void run()
    {
        try
        {
            writeBinaryRelation((BinaryRelation)data);
        }
        catch(Exception e)
        {
            if(jobObserv != null)
            {
                jobObserv.sendMessage(e.getMessage());
                jobObserv.jobEnd(false);
            }
            return;
        }
        if(jobObserv != null)
            jobObserv.jobEnd(true);
    }
}
