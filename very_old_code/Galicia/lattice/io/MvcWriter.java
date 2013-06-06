// Decompiled by Jad v1.5.7g. Copyright 2000 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/SiliconValley/Bridge/8617/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi 
// Source File Name:   MvcWriter.java

package lattice.io;

import java.io.*;
import java.util.Iterator;
import lattice.gui.tooltask.AbstractJob;
import lattice.gui.tooltask.JobObserver;
import lattice.util.*;

// Referenced classes of package lattice.io:
//            AbstractWriter, MultiValuedRelationWriter

public class MvcWriter extends AbstractWriter
    implements MultiValuedRelationWriter
{

    public MvcWriter(Writer w)
    {
        super(w);
    }

    public void writeMultiValuedRelation(MultiValuedRelation mvRel)
        throws IOException
    {
        getStream().write("[Multi-Valued Relation]\n");
        getStream().write(mvRel.getRelationName() + "\n");
        FormalObject lesObjs[] = mvRel.getFormalObjects();
        FormalAttribute lesAtts[] = mvRel.getFormalAttributes();
        String line = "";
        for(int i = 0; i < lesObjs.length; i++)
            line = line + lesObjs[i].toString() + " | ";

        getStream().write(line + "\n");
        line = "";
        for(int j = 0; j < lesAtts.length; j++)
            line = line + lesAtts[j].toString() + " | ";

        getStream().write(line + "\n");
        for(int i = 0; i < lesObjs.length; i++)
        {
            line = "";
            for(int j = 0; j < lesAtts.length; j++)
                if(mvRel.getRelation(lesObjs[i], lesAtts[j]).isEmpty())
                {
                    line = line + "0 | ";
                } else
                {
                    FormalAttributeValueSet favs = (FormalAttributeValueSet)mvRel.getRelation(lesObjs[i], lesAtts[j]);
                    for(Iterator it = favs.getIterator(); it.hasNext();)
                        line = line + it.next().toString() + " @ ";

                    line = line + " | ";
                }

            getStream().write(line + "\n");
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
            writeMultiValuedRelation((MultiValuedRelation)data);
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
