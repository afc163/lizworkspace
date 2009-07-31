// Decompiled by Jad v1.5.7g. Copyright 2000 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/SiliconValley/Bridge/8617/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi 
// Source File Name:   SlfWriter.java

package lattice.io;

import java.io.*;
import lattice.gui.tooltask.AbstractJob;
import lattice.gui.tooltask.JobObserver;
import lattice.util.*;

// Referenced classes of package lattice.io:
//            AbstractWriter, BinaryRelationWriter

public class SlfWriter extends AbstractWriter
    implements BinaryRelationWriter
{

    public SlfWriter(Writer w)
    {
        super(w);
    }

    public void writeBinaryRelation(BinaryRelation binRel)
        throws IOException
    {
        getStream().write("[Lattice]\n");
        getStream().write("" + binRel.getObjectsNumber() + "\n");
        getStream().write("" + binRel.getAttributesNumber() + "\n");
        getStream().write("[Objects]\n");
        FormalObject lesObjs[] = binRel.getFormalObjects();
        for(int i = 0; i < lesObjs.length; i++)
            getStream().write(lesObjs[i].toString() + "\n");

        FormalAttribute lesAtts[] = binRel.getFormalAttributes();
        getStream().write("[Attributes]\n");
        for(int i = 0; i < lesAtts.length; i++)
            getStream().write(lesAtts[i].toString() + "\n");

        getStream().write("[relation]\n");
        for(int i = 0; i < lesObjs.length; i++)
        {
            String ligne = "";
            for(int j = 0; j < lesAtts.length; j++)
                if(binRel.getRelation(lesObjs[i], lesAtts[j]).isEmpty())
                    ligne = ligne + "0 ";
                else
                    ligne = ligne + "1 ";

            getStream().write(ligne + "\n");
            sendJobPercentage((i * 100) / lesObjs.length);
        }

        getStream().flush();
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
