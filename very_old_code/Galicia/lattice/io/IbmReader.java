// Decompiled by Jad v1.5.7g. Copyright 2000 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/SiliconValley/Bridge/8617/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi 
// Source File Name:   IbmReader.java

package lattice.io;

import java.io.*;
import java.util.StringTokenizer;
import java.util.Vector;
import lattice.gui.tooltask.AbstractJob;
import lattice.gui.tooltask.JobObserver;
import lattice.util.BadInputDataException;
import lattice.util.BinaryRelation;

// Referenced classes of package lattice.io:
//            AbstractReader, BinaryRelationReader, ReadingFormatException

public class IbmReader extends AbstractReader
    implements BinaryRelationReader
{

    BinaryRelation binRel;

    public IbmReader(Reader r)
    {
        super(r);
    }

    public BinaryRelation readBinaryRelation()
        throws BadInputDataException, ReadingFormatException, IOException
    {
        int nbO = 0;
        int nbA = 0;
        Vector lesRels = new Vector();
        StringTokenizer st = null;
        String ligne = null;
        int numTok = 0;
        Integer numAtt = null;
        int nbAttLigne = 0;
        while((ligne = getStream().readLine()) != null) 
            if(!ligne.equals("") && !ligne.equals("\n"))
            {
                st = new StringTokenizer(ligne);
                numTok = 0;
                Vector uneRelObj = new Vector();
                lesRels.add(uneRelObj);
                nbO++;
                while(st.hasMoreElements()) 
                {
                    if(numTok == 0)
                    {
                        nbAttLigne = Integer.parseInt(st.nextElement().toString());
                    } else
                    {
                        numAtt = new Integer(st.nextElement().toString());
                        uneRelObj.add(numAtt);
                        if(numAtt.intValue() > nbA)
                            nbA = numAtt.intValue();
                    }
                    numTok++;
                }
            }
        sendJobPercentage(50);
        System.out.println("nbO=" + nbO);
        System.out.println("nbA=" + nbA);
        binRel = new BinaryRelation(nbO, nbA);
        for(int i = 0; i < nbO; i++)
        {
            Vector uneRelObj = (Vector)lesRels.elementAt(i);
            for(int j = 0; j < uneRelObj.size(); j++)
                binRel.setRelation(i, ((Integer)uneRelObj.elementAt(j)).intValue() - 1, true);

            sendJobPercentage(50 + (i * 100) / (2 * nbO));
        }

        sendJobPercentage(100);
        return binRel;
    }

    public String getDescription()
    {
        return "IBM Binary Relation Reader";
    }

    public void run()
    {
        try
        {
            data = readBinaryRelation();
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
