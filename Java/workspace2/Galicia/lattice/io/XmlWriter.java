// Decompiled by Jad v1.5.7g. Copyright 2000 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/SiliconValley/Bridge/8617/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi 
// Source File Name:   XmlWriter.java

package lattice.io;

import java.io.*;
import java.util.*;
import lattice.gui.OpenFileFrame;
import lattice.gui.tooltask.AbstractJob;
import lattice.gui.tooltask.JobObserver;
import lattice.util.*;

// Referenced classes of package lattice.io:
//            AbstractWriter, RelationalContextWriter, LatticeWriter

public class XmlWriter extends AbstractWriter
    implements RelationalContextWriter, LatticeWriter
{

    private int typeOfWriting;

    public XmlWriter(Writer w)
    {
        super(w);
        typeOfWriting = -1;
    }

    public void writeBinaryRelation(BinaryRelation binRel)
        throws IOException
    {
        getStream().write("<BIN name=\"" + binRel.getRelationName() + "\" nbObj=\"" + binRel.getObjectsNumber() + "\" nbAtt=\"" + binRel.getAttributesNumber() + "\" type=\"BinaryRelation\">\n");
        getStream().write("<OBJS>\n");
        for(int i = 0; i < binRel.getObjectsNumber(); i++)
        {
            getStream().write("<OBJ id=\"" + i + "\">");
            try
            {
                getStream().write(binRel.getFormalObject(i).toString());
            }
            catch(BadInputDataException bide)
            {
                System.out.println("XmlWriter.writeBinaryRelation():" + bide.getMessage());
            }
            getStream().write("</OBJ>\n");
        }

        getStream().write("</OBJS>\n");
        getStream().flush();
        getStream().write("<ATTS>\n");
        for(int i = 0; i < binRel.getAttributesNumber(); i++)
        {
            getStream().write("<ATT id=\"" + i + "\">");
            try
            {
                getStream().write(binRel.getFormalAttribute(i).toString());
            }
            catch(BadInputDataException bide)
            {
                System.out.println("XmlWriter.writeBinaryRelation():" + bide.getMessage());
            }
            getStream().write("</ATT>\n");
        }

        getStream().write("</ATTS>\n");
        getStream().flush();
        getStream().write("<RELS>\n");
        for(int i = 0; i < binRel.getObjectsNumber(); i++)
        {
            for(int j = 0; j < binRel.getAttributesNumber(); j++)
                if(!binRel.getRelation(i, j).isEmpty())
                {
                    getStream().write("<REL idObj=\"" + i + "\" idAtt=\"" + j + "\">");
                    getStream().write("</REL>\n");
                }

            getStream().flush();
        }

        getStream().write("</RELS>\n");
        getStream().write("</BIN>\n");
        getStream().flush();
    }

    public void writeInterObjectBinaryRelation(InterObjectBinaryRelation ioBinRel)
        throws IOException
    {
        getStream().write("<BIN name=\"" + ioBinRel.getRelationName() + "\" nbObj=\"" + ioBinRel.getObjectsNumber() + "\" nbAtt=\"" + ioBinRel.getAttributesNumber() + "\" type=\"InterObjectBinaryRelation\">\n");
        getStream().write("<OBJS>\n");
        for(int i = 0; i < ioBinRel.getObjectsNumber(); i++)
        {
            getStream().write("<OBJ id=\"" + i + "\">");
            try
            {
                getStream().write(ioBinRel.getFormalObject(i).toString());
            }
            catch(BadInputDataException bide)
            {
                System.out.println("XmlWriter.writeBinaryRelation():" + bide.getMessage());
            }
            getStream().write("</OBJ>\n");
        }

        getStream().write("</OBJS>\n");
        getStream().flush();
        getStream().write("<ATTS>\n");
        for(int i = 0; i < ioBinRel.getAttributesNumber(); i++)
        {
            getStream().write("<ATT id=\"" + i + "\">");
            try
            {
                getStream().write(ioBinRel.getFormalAttribute(i).toString());
            }
            catch(BadInputDataException bide)
            {
                System.out.println("XmlWriter.writeInterObjectBinaryRelation():" + bide.getMessage());
            }
            getStream().write("</ATT>\n");
        }

        getStream().write("</ATTS>\n");
        getStream().flush();
        getStream().write("<RELS>\n");
        for(int i = 0; i < ioBinRel.getObjectsNumber(); i++)
        {
            for(int j = 0; j < ioBinRel.getAttributesNumber(); j++)
                if(!ioBinRel.getRelation(i, j).isEmpty())
                {
                    getStream().write("<REL idObj=\"" + i + "\" idAtt=\"" + j + "\">");
                    getStream().write("</REL>\n");
                }

            getStream().flush();
        }

        getStream().write("</RELS>\n");
        getStream().write("</BIN>\n");
        getStream().flush();
    }

    public void writeScalingBinaryRelation(ScalingBinaryRelation scBinRel)
        throws IOException
    {
        getStream().write("<BIN name=\"" + scBinRel.getRelationName() + "\" nbObj=\"" + scBinRel.getObjectsNumber() + "\" nbAtt=\"" + scBinRel.getAttributesNumber() + "\" type=\"ScallingBinaryRelation\">\n");
        getStream().write("<ATTS>\n");
        for(int i = 0; i < scBinRel.getAttributesNumber(); i++)
            try
            {
                getStream().write("<ATT id=\"" + i + "\" name=\"" + ((ScalingFormalAttribute)scBinRel.getFormalAttribute(i)).getAttribute().toString() + "\" value=\"" + ((ScalingFormalAttribute)scBinRel.getFormalAttribute(i)).getValue().toString() + "\">");
                getStream().write("</ATT>\n");
            }
            catch(BadInputDataException bide)
            {
                System.out.println("XmlWriter.writeScallingBinaryRelation():" + bide.getMessage());
            }

        getStream().write("</ATTS>\n");
        getStream().flush();
        getStream().write("<RELS>\n");
        for(int i = 0; i < scBinRel.getObjectsNumber(); i++)
        {
            for(int j = 0; j < scBinRel.getAttributesNumber(); j++)
                if(!scBinRel.getRelation(i, j).isEmpty())
                {
                    getStream().write("<REL idObj=\"" + i + "\" idAtt=\"" + j + "\">");
                    getStream().write("</REL>\n");
                }

            getStream().flush();
        }

        getStream().write("</RELS>\n");
        getStream().write("</BIN>\n");
        getStream().flush();
    }

    public void writeConceptLattice(ConceptLattice lattice)
        throws BadInputDataException, IOException
    {
        int nextIdObj = 0;
        Hashtable lesObjs = new Hashtable();
        for(Iterator it = lattice.getTop().getConcept().getExtent().iterator(); it.hasNext();)
        {
            FormalObject fo = (FormalObject)it.next();
            if(lesObjs.get(fo) == null)
            {
                lesObjs.put(fo, new Integer(nextIdObj));
                nextIdObj++;
            }
        }

        int nextIdAtt = 0;
        Hashtable lesAtts = new Hashtable();
        lattice.fillSimplify();
        lattice.getTop().resetDegre();
        Vector Q = new Vector();
        Q.add(lattice.getTop());
        while(Q.size() != 0) 
        {
            Node nodeToTest = (Node)Q.remove(0);
            for(Iterator it = nodeToTest.getChildren().iterator(); it.hasNext();)
            {
                Node P = (Node)it.next();
                if(P.getDegre() == -1)
                    P.setDegre(P.getParents().size());
                P.setDegre(P.getDegre() - 1);
                if(P.getDegre() == 0)
                    Q.add(P);
            }

            for(Iterator it = nodeToTest.getConcept().getSimplifyIntent().iterator(); it.hasNext();)
            {
                lesAtts.put(it.next(), new Integer(nextIdAtt));
                nextIdAtt++;
            }

        }
        getStream().write("<LAT Desc=\"" + lattice.getDescription() + "\" ");
        if(lattice instanceof LatticeGraph)
            getStream().write("type=\"LatticeGraph\"");
        else
        if(lattice instanceof LinkedConceptLattice)
            getStream().write("type=\"LinkedConceptLattice\"");
        getStream().write(">\n");
        getStream().write("<OBJS>\n");
        for(Enumeration enum = lesObjs.keys(); enum.hasMoreElements(); getStream().write("</OBJ>\n"))
        {
            FormalObject fo = (FormalObject)enum.nextElement();
            getStream().write("<OBJ id=\"" + ((Integer)lesObjs.get(fo)).intValue() + "\">");
            getStream().write(fo.toString());
        }

        getStream().write("</OBJS>\n");
        getStream().flush();
        getStream().write("<ATTS>\n");
        for(Enumeration enum = lesAtts.keys(); enum.hasMoreElements(); getStream().write("</ATT>\n"))
        {
            FormalAttribute fa = (FormalAttribute)enum.nextElement();
            getStream().write("<ATT id=\"" + ((Integer)lesAtts.get(fa)).intValue() + "\">");
            getStream().write(fa.toString());
        }

        getStream().write("</ATTS>\n");
        getStream().flush();
        getStream().write("<NODS>\n");
        lattice.getTop().resetDegre();
        int nextIdNode = 0;
        Hashtable correspId = new Hashtable();
        Q = new Vector();
        Q.add(lattice.getTop());
        for(; Q.size() != 0; getStream().flush())
        {
            Node nodeToWrite = (Node)Q.remove(0);
            getStream().write("<NOD id=\"" + nextIdNode + "\">\n");
            correspId.put(nodeToWrite.getId(), new Integer(nextIdNode));
            nextIdNode++;
            getStream().write("<EXT>\n");
            for(Iterator it = nodeToWrite.getConcept().getExtent().iterator(); it.hasNext(); getStream().write("</OBJ>\n"))
                getStream().write("<OBJ id=\"" + ((Integer)lesObjs.get((FormalObject)it.next())).intValue() + "\">");

            getStream().write("</EXT>\n");
            getStream().write("<INT>\n");
            for(Iterator it = nodeToWrite.getConcept().getIntent().iterator(); it.hasNext(); getStream().write("</ATT>\n"))
                getStream().write("<ATT id=\"" + ((Integer)lesAtts.get((FormalAttribute)it.next())).intValue() + "\">");

            getStream().write("</INT>\n");
            getStream().write("<SUP_NOD>\n");
            for(Iterator it = nodeToWrite.getParents().iterator(); it.hasNext(); getStream().write("</PARENT>\n"))
                getStream().write("<PARENT id=\"" + ((Integer)correspId.get(((Node)it.next()).getId())).intValue() + "\">");

            getStream().write("</SUP_NOD>\n");
            getStream().write("</NOD>\n");
            for(Iterator it = nodeToWrite.getChildren().iterator(); it.hasNext();)
            {
                Node P = (Node)it.next();
                if(P.getDegre() == -1)
                    P.setDegre(P.getParents().size());
                P.setDegre(P.getDegre() - 1);
                if(P.getDegre() == 0)
                    Q.add(P);
            }

        }

        getStream().write("</NODS>\n");
        getStream().write("</LAT>\n");
        getStream().flush();
    }

    public void writeMultiValuedRelation(MultiValuedRelation mvr)
        throws IOException
    {
        getStream().write("<MUL name=\"" + mvr.getRelationName() + "\" nbObj=\"" + mvr.getObjectsNumber() + "\" nbAtt=\"" + mvr.getAttributesNumber() + "\" type=\"MultiValuedRelation\">\n");
        getStream().write("<OBJS>\n");
        for(int i = 0; i < mvr.getObjectsNumber(); i++)
        {
            getStream().write("<OBJ id=\"" + i + "\">");
            try
            {
                getStream().write(mvr.getFormalObject(i).toString());
            }
            catch(BadInputDataException bide)
            {
                System.out.println("XmlWriter.writeBinaryRelation():" + bide.getMessage());
            }
            getStream().write("</OBJ>\n");
        }

        getStream().write("</OBJS>\n");
        getStream().flush();
        getStream().write("<ATTS>\n");
        for(int i = 0; i < mvr.getAttributesNumber(); i++)
        {
            getStream().write("<ATT id=\"" + i + "\">");
            try
            {
                getStream().write(mvr.getFormalAttribute(i).toString());
            }
            catch(BadInputDataException bide)
            {
                System.out.println("XmlWriter.writeBinaryRelation():" + bide.getMessage());
            }
            getStream().write("</ATT>\n");
        }

        getStream().write("</ATTS>\n");
        getStream().flush();
        getStream().write("<RELS>\n");
        for(int i = 0; i < mvr.getObjectsNumber(); i++)
        {
            for(int j = 0; j < mvr.getAttributesNumber(); j++)
                if(!mvr.getRelation(i, j).isEmpty())
                {
                    for(Iterator it = ((FormalAttributeValueSet)mvr.getValues().elementAt(i)).getIterator(); it.hasNext(); getStream().write("</REL>\n"))
                    {
                        getStream().write("<REL idObj=\"" + i + "\" idAtt=\"" + j + "\">");
                        getStream().write(it.next().toString());
                    }

                }

            getStream().flush();
        }

        getStream().write("</RELS>\n");
        getStream().write("</MUL>\n");
        getStream().flush();
    }

    public void writeRelationalContext(RelationalContext relCtx)
        throws IOException
    {
        getStream().write("<FAM name=\"" + relCtx.getName() + "\">\n");
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
            getStream().flush();
            sendJobPercentage((i * 100) / relCtx.getNumberOfRelation());
        }

        getStream().write("</FAM>\n");
        getStream().flush();
        sendJobPercentage(100);
    }

    public void run()
    {
        try
        {
            if(typeOfWriting == OpenFileFrame.BINARY_DATA)
                writeBinaryRelation((BinaryRelation)data);
            if(typeOfWriting == OpenFileFrame.MULTI_VALUE_DATA)
                writeMultiValuedRelation((MultiValuedRelation)data);
            if(typeOfWriting == OpenFileFrame.FAMILLY_DATA)
                writeRelationalContext((RelationalContext)data);
            if(typeOfWriting == OpenFileFrame.LATTICE_DATA)
                writeConceptLattice((ConceptLattice)data);
            getStream().close();
        }
        catch(Exception e)
        {
            e.printStackTrace();
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

    public void setTypeOfWriting(int tw)
    {
        typeOfWriting = tw;
    }

    public int getTypeOfWriting()
    {
        return typeOfWriting;
    }
}
