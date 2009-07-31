// Decompiled by Jad v1.5.7g. Copyright 2000 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/SiliconValley/Bridge/8617/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi 
// Source File Name:   RcfWriter.java

package lattice.io;

import java.io.*;
import java.util.Iterator;
import lattice.gui.tooltask.AbstractJob;
import lattice.gui.tooltask.JobObserver;
import lattice.util.*;

// Referenced classes of package lattice.io:
//            AbstractWriter, RelationalContextWriter

public class RcfWriter extends AbstractWriter
    implements RelationalContextWriter
{

    public RcfWriter(Writer w)
    {
        super(w);
    }

    public void writeRelationalContext(RelationalContext relCtx)
        throws IOException
    {
        getStream().write("[Relational Context]\n");
        getStream().write(relCtx.getName() + "\n");
        for(int i = 0; i < relCtx.getNumberOfRelation(); i++)
        {
            if(relCtx.getRelation(i) instanceof InterObjectBinaryRelation)
                writeInterObjectBinaryRelation((InterObjectBinaryRelation)relCtx.getRelation(i));
            else
            if(relCtx.getRelation(i) instanceof ScalingBinaryRelation)
                writeScalingBinaryRelation((ScalingBinaryRelation)relCtx.getRelation(i));
            else
            if(relCtx.getRelation(i) instanceof BinaryRelation)
                writeBinaryRelation((BinaryRelation)relCtx.getRelation(i));
            else
            if(relCtx.getRelation(i) instanceof MultiValuedRelation)
                writeMultiValuedRelation((MultiValuedRelation)relCtx.getRelation(i));
            sendJobPercentage((i * 100) / relCtx.getNumberOfRelation());
        }

        getStream().write("[END Relational Context]\n");
        getStream().close();
        sendJobPercentage(100);
    }

    public void writeBinaryRelation(BinaryRelation binRel)
        throws IOException
    {
        getStream().write("[Binary Relation]\n");
        getStream().write(binRel.getRelationName() + "\n");
        FormalObject lesObjs[] = binRel.getFormalObjects();
        FormalAttribute lesAtts[] = binRel.getFormalAttributes();
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
                if(binRel.getRelation(lesObjs[i], lesAtts[j]).isEmpty())
                    line = line + "0 ";
                else
                    line = line + "1 ";

            getStream().write(line + "\n");
        }

        getStream().flush();
    }

    public void writeInterObjectBinaryRelation(InterObjectBinaryRelation ioBinRel)
        throws IOException
    {
        getStream().write("[Inter Object Binary Relation]\n");
        getStream().write(ioBinRel.getRelationName() + "\n");
        FormalObject lesObjs[] = ioBinRel.getFormalObjects();
        FormalAttribute lesAtts[] = ioBinRel.getFormalAttributes();
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
                if(ioBinRel.getRelation(lesObjs[i], lesAtts[j]).isEmpty())
                    line = line + "0 ";
                else
                    line = line + "1 ";

            getStream().write(line + "\n");
        }

        getStream().flush();
    }

    public void writeScalingBinaryRelation(ScalingBinaryRelation scBinRel)
        throws IOException
    {
        getStream().write("[Scaling Binary Relation]\n");
        getStream().write(scBinRel.getRelationName() + "\n");
        FormalObject lesObjs[] = scBinRel.getFormalObjects();
        FormalAttribute lesAtts[] = scBinRel.getFormalAttributes();
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
                if(scBinRel.getRelation(lesObjs[i], lesAtts[j]).isEmpty())
                    line = line + "0 ";
                else
                    line = line + "1 ";

            getStream().write(line + "\n");
        }

        getStream().flush();
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
        }

        getStream().flush();
    }

    public void run()
    {
        try
        {
            writeRelationalContext((RelationalContext)data);
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
