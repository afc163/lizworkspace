// Decompiled by Jad v1.5.7g. Copyright 2000 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/SiliconValley/Bridge/8617/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi 
// Source File Name:   SlfReader.java

package lattice.io;

import java.io.*;
import java.util.StringTokenizer;
import lattice.gui.tooltask.AbstractJob;
import lattice.gui.tooltask.JobObserver;
import lattice.util.*;

// Referenced classes of package lattice.io:
//            AbstractReader, BinaryRelationReader, ReadingFormatException

public class SlfReader extends AbstractReader
    implements BinaryRelationReader
{

    public SlfReader(Reader r)
    {
        super(r);
    }

    public BinaryRelation readBinaryRelation()
        throws BadInputDataException, ReadingFormatException, IOException
    {
        BinaryRelation binRel = null;
        int nbObj = 0;
        int nbAtt = 0;
        String rel = "";
        if(!getStream().readLine().trim().equals("[Lattice]"))
            throw new ReadingFormatException("Le fichier SLF ne commence pas avec la mention : [Lattice]\n");
        try
        {
            nbObj = Integer.parseInt(getStream().readLine().trim());
        }
        catch(NumberFormatException nfe)
        {
            throw new ReadingFormatException("Le fichier SLF ne commence pas un nombre d'Objets lisible\n");
        }
        try
        {
            nbAtt = Integer.parseInt(getStream().readLine().trim());
        }
        catch(NumberFormatException nfe)
        {
            throw new ReadingFormatException("Le fichier SLF ne commence pas un nombre d'Atributs lisible\n");
        }
        binRel = new BinaryRelation(nbObj, nbAtt);
        if(!getStream().readLine().trim().equals("[Objects]"))
            throw new ReadingFormatException("The file SLF does not contain [Objects] clause\n");
        String nomObj = null;
        for(int i = 0; i < nbObj; i++)
        {
            nomObj = getStream().readLine().trim();
            if(nomObj == null || nomObj.equals("[Attributes]"))
                throw new ReadingFormatException("Le fichier SLF est incomplet : Nom Objet Manquant !\n");
            binRel.replaceObject(binRel.getFormalObjects()[i], new DefaultFormalObject(nomObj));
        }

        if(!getStream().readLine().trim().equals("[Attributes]"))
            throw new ReadingFormatException("The file SLF does not contain [Attributes] clause\n");
        String nomAtt = null;
        for(int i = 0; i < nbAtt; i++)
        {
            nomAtt = getStream().readLine().trim();
            if(nomAtt == null || nomAtt.equals("[relation]"))
                throw new ReadingFormatException("Le fichier SLF est incomplet : Nom Atributs Manquant !\n");
            binRel.replaceAttribute(binRel.getFormalAttributes()[i], new DefaultFormalAttribute(nomAtt));
        }

        if(!getStream().readLine().trim().equals("[relation]"))
            throw new ReadingFormatException("The file SLF does not contain [relation] clause\n");
        StringTokenizer ligneRel = null;
        for(int i = 0; i < nbObj; i++)
        {
            ligneRel = new StringTokenizer(getStream().readLine().trim(), " ");
            if(ligneRel == null || ligneRel.countTokens() < nbAtt)
                throw new ReadingFormatException("Le fichier SLF est incomplet : Des Relations sont Manquantes !\n");
            for(int j = 0; ligneRel.hasMoreElements(); j++)
            {
                rel = ligneRel.nextToken();
                if(rel.equals("1"))
                    rel = "X";
                lattice.util.FormalObject obj = binRel.getFormalObjects()[i];
                lattice.util.FormalAttribute att = binRel.getFormalAttributes()[j];
                binRel.setRelation(obj, att, new FormalAttributeValue(rel));
            }

            sendJobPercentage((i * 100) / nbObj);
        }

        sendJobPercentage(100);
        return binRel;
    }

    public String getDescription()
    {
        return "SLF Binary Relation Reader";
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
