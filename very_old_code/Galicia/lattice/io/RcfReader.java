// Decompiled by Jad v1.5.7g. Copyright 2000 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/SiliconValley/Bridge/8617/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi 
// Source File Name:   RcfReader.java

package lattice.io;

import java.io.*;
import java.util.StringTokenizer;
import java.util.Vector;
import lattice.gui.tooltask.AbstractJob;
import lattice.gui.tooltask.JobObserver;
import lattice.util.*;

// Referenced classes of package lattice.io:
//            AbstractReader, RelationalContextReader, ReadingFormatException

public class RcfReader extends AbstractReader
    implements RelationalContextReader
{

    public RcfReader(Reader r)
    {
        super(r);
    }

    public RelationalContext readRelationalContext()
        throws NammingException, BadInputDataException, ReadingFormatException, IOException
    {
        RelationalContext relCtx = new RelationalContext();
        String line;
        while((line = getStream().readLine()) != null && !line.equals("[END Relational Context]")) 
        {
            if(line.equals("[Relational Context]"))
                relCtx.setName(getStream().readLine());
            if(line.equals("[Binary Relation]"))
                relCtx.addRelation(readBinaryRelation());
            if(line.equals("[Inter Object Binary Relation]"))
                relCtx.addRelation(readInterObjectBinaryRelation());
            if(line.equals("[Scaling Binary Relation]"))
                relCtx.addRelation(readScalingBinaryRelation());
            if(line.equals("[Multi-Valued Relation]"))
                relCtx.addRelation(readMultiValuedRelation());
        }
        return relCtx;
    }

    public BinaryRelation readBinaryRelation()
        throws BadInputDataException, IOException
    {
        BinaryRelation binRel = null;
        String nomRel = getStream().readLine();
        StringTokenizer sk = null;
        sk = new StringTokenizer(getStream().readLine().trim(), "|");
        Vector lesObjs = new Vector();
        for(; sk.hasMoreElements(); lesObjs.add(new DefaultFormalObject(sk.nextElement().toString().trim())));
        sk = new StringTokenizer(getStream().readLine().trim(), "|");
        Vector lesAtts = new Vector();
        for(; sk.hasMoreElements(); lesAtts.add(new DefaultFormalAttribute(sk.nextElement().toString().trim())));
        binRel = new BinaryRelation(lesObjs.size(), lesAtts.size());
        binRel.setRelationName(nomRel);
        for(int i = 0; i < lesObjs.size(); i++)
            binRel.replaceObject(i, (FormalObject)lesObjs.elementAt(i));

        for(int j = 0; j < lesAtts.size(); j++)
            binRel.replaceAttribute(j, (FormalAttribute)lesAtts.elementAt(j));

        for(int i = 0; i < lesObjs.size(); i++)
        {
            sk = new StringTokenizer(getStream().readLine().trim(), " ");
            if(sk == null || sk.countTokens() < lesAtts.size())
                throw new BadInputDataException("Some relationship is missing in the RCF file (" + nomRel + ":<BinaryRelation>)\n");
            for(int j = 0; sk.hasMoreElements(); j++)
                binRel.setRelation(i, j, sk.nextToken().trim().equals("1"));

        }

        return binRel;
    }

    public InterObjectBinaryRelation readInterObjectBinaryRelation()
        throws BadInputDataException, IOException
    {
        InterObjectBinaryRelation ioBinRel = null;
        String nomRel = getStream().readLine();
        StringTokenizer sk = null;
        sk = new StringTokenizer(getStream().readLine().trim(), "|");
        Vector lesObjs = new Vector();
        for(; sk.hasMoreElements(); lesObjs.add(new DefaultFormalObject(sk.nextElement().toString().trim())));
        sk = new StringTokenizer(getStream().readLine().trim(), "|");
        Vector lesAtts = new Vector();
        for(; sk.hasMoreElements(); lesAtts.add(new InterObjectBinaryRelationAttribute(new DefaultFormalObject(sk.nextElement().toString().trim()))));
        ioBinRel = new InterObjectBinaryRelation(lesObjs.size(), lesAtts.size());
        ioBinRel.setRelationName(nomRel);
        for(int i = 0; i < lesObjs.size(); i++)
            ioBinRel.replaceObject(i, (FormalObject)lesObjs.elementAt(i));

        for(int j = 0; j < lesAtts.size(); j++)
            ioBinRel.replaceAttribute(j, (FormalAttribute)lesAtts.elementAt(j));

        for(int i = 0; i < lesObjs.size(); i++)
        {
            sk = new StringTokenizer(getStream().readLine().trim(), " ");
            if(sk == null || sk.countTokens() < lesAtts.size())
                throw new BadInputDataException("Some relationship is missing in the RCF file (" + nomRel + ":<InterObjectBinaryRelation>)\n");
            for(int j = 0; sk.hasMoreElements(); j++)
                ioBinRel.setRelation(i, j, sk.nextToken().trim().equals("1"));

        }

        return ioBinRel;
    }

    public ScalingBinaryRelation readScalingBinaryRelation()
        throws BadInputDataException, IOException
    {
        ScalingBinaryRelation scBinRel = null;
        String nomRel = getStream().readLine();
        StringTokenizer sk = null;
        sk = new StringTokenizer(getStream().readLine().trim(), "|");
        Vector lesObjs = new Vector();
        for(; sk.hasMoreElements(); lesObjs.add(new DefaultFormalObject(sk.nextElement().toString().trim())));
        sk = new StringTokenizer(getStream().readLine().trim(), "|");
        Vector lesAtts = new Vector();
        FormalAttribute fa;
        FormalAttributeValue fav;
        for(; sk.hasMoreElements(); lesAtts.add(new ScalingFormalAttribute(fa, fav)))
        {
            StringTokenizer st2 = new StringTokenizer(sk.nextElement().toString(), "=");
            fa = new DefaultFormalAttribute(st2.nextElement().toString().trim());
            fav = new FormalAttributeValue(st2.nextElement().toString().trim());
        }

        scBinRel = new ScalingBinaryRelation(lesObjs.size(), lesAtts.size());
        scBinRel.setRelationName(nomRel);
        for(int i = 0; i < lesObjs.size(); i++)
            scBinRel.replaceObject(i, (FormalObject)lesObjs.elementAt(i));

        for(int j = 0; j < lesAtts.size(); j++)
            scBinRel.replaceAttribute(j, (FormalAttribute)lesAtts.elementAt(j));

        for(int i = 0; i < lesObjs.size(); i++)
        {
            sk = new StringTokenizer(getStream().readLine().trim(), " ");
            if(sk == null || sk.countTokens() < lesAtts.size())
                throw new BadInputDataException("Some relationship is missing in the RCF file (" + nomRel + ":<ScallingBinaryRelation>)\n");
            for(int j = 0; sk.hasMoreElements(); j++)
                scBinRel.setRelation(i, j, sk.nextToken().trim().equals("1"));

        }

        return scBinRel;
    }

    public MultiValuedRelation readMultiValuedRelation()
        throws BadInputDataException, IOException
    {
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
                throw new BadInputDataException("Some relationship is missing in the RCF file (" + nomRel + ":<MultiValuedRelation>)\n");
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

        }

        return mvRel;
    }

    public void run()
    {
        try
        {
            data = readRelationalContext();
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
