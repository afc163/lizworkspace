// Decompiled by Jad v1.5.7g. Copyright 2000 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/SiliconValley/Bridge/8617/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi 
// Source File Name:   MvcReader.java

package lattice.io;

import java.io.*;
import java.util.StringTokenizer;
import java.util.Vector;
import lattice.gui.tooltask.AbstractJob;
import lattice.gui.tooltask.JobObserver;
import lattice.util.*;

// Referenced classes of package lattice.io:
//            AbstractReader, MultiValuedRelationReader

public class MvcReader extends AbstractReader
    implements MultiValuedRelationReader
{

    public MvcReader(Reader r)
    {
        super(r);
    }

    public MultiValuedRelation readMultiValuedRelation()
        throws BadInputDataException, IOException
    {
        if(!getStream().readLine().trim().equals("[Multi-Valued Relation]"))
            throw new IOException("The MVC file does not start with : [Multi-Valued Relation]\n");
        MultiValuedRelation mvRel = null;
        String nomRel = getStream().readLine().trim();
        StringTokenizer sk = null;
        sk = new StringTokenizer(getStream().readLine().trim(), "|");
        Vector lesObjs = new Vector();
        for(; sk.hasMoreElements(); lesObjs.add(new DefaultFormalObject(sk.nextElement().toString())));
        sk = new StringTokenizer(getStream().readLine().trim(), "|");
        Vector lesAtts = new Vector();
        for(; sk.hasMoreElements(); lesAtts.add(new DefaultFormalAttribute(sk.nextElement().toString())));
        mvRel = new MultiValuedRelation(lesObjs.size(), lesAtts.size());
        mvRel.setRelationName(nomRel);
        for(int i = 0; i < lesObjs.size(); i++)
            mvRel.replaceObject(i, (FormalObject)lesObjs.elementAt(i));

        for(int j = 0; j < lesAtts.size(); j++)
            mvRel.replaceAttribute(j, (FormalAttribute)lesAtts.elementAt(j));

        for(int i = 0; i < lesObjs.size(); i++)
        {
            sk = new StringTokenizer(getStream().readLine().trim(), "|");
            if(sk == null || sk.countTokens() < lesAtts.size())
                throw new BadInputDataException("Some relationships are missing in the MVC file.\n");
            for(int j = 0; sk.hasMoreElements(); j++)
            {
                String vTmp = sk.nextToken().trim();
                for(StringTokenizer st2 = new StringTokenizer(vTmp, "@"); st2.hasMoreElements();)
                {
                    String val = st2.nextToken().trim();
                    if(!val.equals("0"))
                        mvRel.addValuedRelation((FormalObject)lesObjs.elementAt(i), (FormalAttribute)lesAtts.elementAt(j), new FormalAttributeValue(val));
                }

            }

            sendJobPercentage((i * 100) / lesObjs.size());
        }

        sendJobPercentage(100);
        return mvRel;
    }

    public void run()
    {
        try
        {
            data = readMultiValuedRelation();
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
