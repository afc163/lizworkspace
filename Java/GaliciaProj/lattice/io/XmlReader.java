// Decompiled by Jad v1.5.7g. Copyright 2000 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/SiliconValley/Bridge/8617/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi 
// Source File Name:   XmlReader.java

package lattice.io;

import java.io.IOException;
import java.io.PrintStream;
import java.io.Reader;
import java.util.Hashtable;
import java.util.Set;
import java.util.Vector;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import lattice.gui.OpenFileFrame;
import lattice.gui.tooltask.AbstractJob;
import lattice.gui.tooltask.JobObserver;
import lattice.util.AbstractRelation;
import lattice.util.BadInputDataException;
import lattice.util.BinaryRelation;
import lattice.util.Concept;
import lattice.util.ConceptLattice;
import lattice.util.DefaultFormalAttribute;
import lattice.util.DefaultFormalObject;
import lattice.util.FormalAttribute;
import lattice.util.FormalAttributeValue;
import lattice.util.InterObjectBinaryRelation;
import lattice.util.InterObjectBinaryRelationAttribute;
import lattice.util.LatticeGraph;
import lattice.util.LatticeNode;
import lattice.util.LinkedConceptLattice;
import lattice.util.MultiValuedRelation;
import lattice.util.RelationalContext;
import lattice.util.ScalingBinaryRelation;
import lattice.util.ScalingFormalAttribute;
import lattice.util.ScalingFormalObject;
import lattice.util.SetExtent;
import lattice.util.SetIntent;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.dom.Text;
import org.xml.sax.InputSource;

// Referenced classes of package lattice.io:
//            AbstractReader, RelationalContextReader, LatticeReader

public class XmlReader extends AbstractReader
    implements RelationalContextReader, LatticeReader
{

    private int typeOfReading;
    private Element currentTag;

    public XmlReader(Reader r)
    {
        super(r);
        typeOfReading = -1;
        currentTag = null;
    }

    public Vector read()
    {
        return null;
    }

    public BinaryRelation readBinaryRelation()
        throws BadInputDataException, IOException
    {
        lattice.util.FormalObject fo = null;
        FormalAttribute fa = null;
        if(currentTag != null && currentTag.getTagName().equals("BIN") && Integer.parseInt(currentTag.getAttribute("nbObj")) > 0 && Integer.parseInt(currentTag.getAttribute("nbAtt")) > 0 && currentTag.getAttribute("type").equals("BinaryRelation"))
        {
            BinaryRelation binRel = new BinaryRelation(Integer.parseInt(currentTag.getAttribute("nbObj")), Integer.parseInt(currentTag.getAttribute("nbAtt")));
            binRel.setRelationName(currentTag.getAttribute("name"));
            NodeList nl = ((Element)currentTag.getElementsByTagName("OBJS").item(0)).getElementsByTagName("OBJ");
            for(int i = 0; i < nl.getLength(); i++)
            {
                Element obj = (Element)nl.item(i);
                fo = new DefaultFormalObject(((Text)obj.getChildNodes().item(0)).getNodeValue());
                binRel.replaceObject(Integer.parseInt(obj.getAttribute("id")), fo);
            }

            nl = ((Element)currentTag.getElementsByTagName("ATTS").item(0)).getElementsByTagName("ATT");
            for(int i = 0; i < nl.getLength(); i++)
            {
                Element att = (Element)nl.item(i);
                fa = new DefaultFormalAttribute(((Text)att.getChildNodes().item(0)).getNodeValue());
                binRel.replaceAttribute(Integer.parseInt(att.getAttribute("id")), fa);
            }

            nl = ((Element)currentTag.getElementsByTagName("RELS").item(0)).getElementsByTagName("REL");
            for(int i = 0; i < nl.getLength(); i++)
            {
                Element rel = (Element)nl.item(i);
                binRel.setRelation(Integer.parseInt(rel.getAttribute("idObj")), Integer.parseInt(rel.getAttribute("idAtt")), true);
            }

            return binRel;
        }
        if(currentTag == null)
            System.out.println("null");
        throw new BadInputDataException("Reading XML BinaryRelation wrong format !");
    }

    public InterObjectBinaryRelation readInterObjectBinaryRelation()
        throws BadInputDataException, IOException
    {
        lattice.util.FormalObject fo = null;
        FormalAttribute fa = null;
        if(currentTag != null && currentTag.getTagName().equals("BIN") && Integer.parseInt(currentTag.getAttribute("nbObj")) > 0 && Integer.parseInt(currentTag.getAttribute("nbAtt")) > 0 && currentTag.getAttribute("type").equals("InterObjectBinaryRelation"))
        {
            BinaryRelation binRel = new InterObjectBinaryRelation(Integer.parseInt(currentTag.getAttribute("nbObj")), Integer.parseInt(currentTag.getAttribute("nbAtt")));
            binRel.setRelationName(currentTag.getAttribute("name"));
            NodeList nl = ((Element)currentTag.getElementsByTagName("OBJS").item(0)).getElementsByTagName("OBJ");
            for(int i = 0; i < nl.getLength(); i++)
            {
                Element obj = (Element)nl.item(i);
                fo = new DefaultFormalObject(((Text)obj.getChildNodes().item(0)).getNodeValue());
                binRel.replaceObject(Integer.parseInt(obj.getAttribute("id")), fo);
            }

            nl = ((Element)currentTag.getElementsByTagName("ATTS").item(0)).getElementsByTagName("ATT");
            for(int i = 0; i < nl.getLength(); i++)
            {
                Element att = (Element)nl.item(i);
                fa = new InterObjectBinaryRelationAttribute(new DefaultFormalObject(((Text)att.getChildNodes().item(0)).getNodeValue()));
                binRel.replaceAttribute(Integer.parseInt(att.getAttribute("id")), fa);
            }

            nl = ((Element)currentTag.getElementsByTagName("RELS").item(0)).getElementsByTagName("REL");
            for(int i = 0; i < nl.getLength(); i++)
            {
                Element rel = (Element)nl.item(i);
                binRel.setRelation(Integer.parseInt(rel.getAttribute("idObj")), Integer.parseInt(rel.getAttribute("idAtt")), true);
            }

            return (InterObjectBinaryRelation)binRel;
        } else
        {
            throw new BadInputDataException("Reading XML BinaryRelation wrong format !");
        }
    }

    public ScalingBinaryRelation readScalingBinaryRelation()
        throws BadInputDataException, IOException
    {
        FormalAttribute fa = null;
        FormalAttributeValue fav = null;
        if(currentTag != null && currentTag.getTagName().equals("BIN") && Integer.parseInt(currentTag.getAttribute("nbObj")) > 0 && Integer.parseInt(currentTag.getAttribute("nbAtt")) > 0 && currentTag.getAttribute("type").equals("ScallingBinaryRelation"))
        {
            BinaryRelation binRel = new ScalingBinaryRelation(Integer.parseInt(currentTag.getAttribute("nbObj")), Integer.parseInt(currentTag.getAttribute("nbAtt")));
            binRel.setRelationName(currentTag.getAttribute("name"));
            Hashtable currentAtt = new Hashtable();
            Hashtable currentVal = new Hashtable();
            NodeList nl = ((Element)currentTag.getElementsByTagName("ATTS").item(0)).getElementsByTagName("ATT");
            for(int i = 0; i < nl.getLength(); i++)
            {
                Element att = (Element)nl.item(i);
                fa = (FormalAttribute)currentAtt.get(att.getAttribute("name"));
                if(fa == null)
                {
                    fa = new DefaultFormalAttribute(att.getAttribute("name"));
                    currentAtt.put(att.getAttribute("name"), fa);
                }
                fav = (FormalAttributeValue)currentVal.get(att.getAttribute("value"));
                if(fav == null)
                {
                    fav = new FormalAttributeValue(att.getAttribute("value"));
                    currentVal.put(att.getAttribute("value"), fav);
                }
                binRel.replaceAttribute(i, new ScalingFormalAttribute(fa, fav));
                binRel.replaceObject(i, new ScalingFormalObject(fa, fav));
            }

            nl = ((Element)currentTag.getElementsByTagName("RELS").item(0)).getElementsByTagName("REL");
            for(int i = 0; i < nl.getLength(); i++)
            {
                Element rel = (Element)nl.item(i);
                binRel.setRelation(Integer.parseInt(rel.getAttribute("idObj")), Integer.parseInt(rel.getAttribute("idAtt")), true);
            }

            return (ScalingBinaryRelation)binRel;
        } else
        {
            return null;
        }
    }

    public MultiValuedRelation readMultiValuedRelation()
        throws BadInputDataException, IOException
    {
        lattice.util.FormalObject fo = null;
        FormalAttribute fa = null;
        if(currentTag != null && currentTag.getTagName().equals("MUL") && Integer.parseInt(currentTag.getAttribute("nbObj")) > 0 && Integer.parseInt(currentTag.getAttribute("nbAtt")) > 0 && currentTag.getAttribute("type").equals("MultiValuedRelation"))
        {
            MultiValuedRelation mvRel = new MultiValuedRelation(Integer.parseInt(currentTag.getAttribute("nbObj")), Integer.parseInt(currentTag.getAttribute("nbAtt")));
            mvRel.setRelationName(currentTag.getAttribute("name"));
            NodeList nl = ((Element)currentTag.getElementsByTagName("OBJS").item(0)).getElementsByTagName("OBJ");
            for(int i = 0; i < nl.getLength(); i++)
            {
                Element obj = (Element)nl.item(i);
                fo = new DefaultFormalObject(((Text)obj.getChildNodes().item(0)).getNodeValue());
                mvRel.replaceObject(Integer.parseInt(obj.getAttribute("id")), fo);
            }

            nl = ((Element)currentTag.getElementsByTagName("ATTS").item(0)).getElementsByTagName("ATT");
            for(int i = 0; i < nl.getLength(); i++)
            {
                Element att = (Element)nl.item(i);
                fa = new DefaultFormalAttribute(((Text)att.getChildNodes().item(0)).getNodeValue());
                mvRel.replaceAttribute(Integer.parseInt(att.getAttribute("id")), fa);
            }

            nl = ((Element)currentTag.getElementsByTagName("RELS").item(0)).getElementsByTagName("REL");
            for(int i = 0; i < nl.getLength(); i++)
            {
                Element rel = (Element)nl.item(i);
                FormalAttributeValue val = new FormalAttributeValue(((Text)rel.getChildNodes().item(0)).getNodeValue());
                mvRel.addValuedRelation(Integer.parseInt(rel.getAttribute("idObj")), Integer.parseInt(rel.getAttribute("idAtt")), val);
            }

            return mvRel;
        } else
        {
            throw new BadInputDataException("Reading XML BinaryRelation wrong format !");
        }
    }

    public RelationalContext readRelationalContext()
        throws BadInputDataException, IOException
    {
        if(currentTag != null && currentTag.getTagName().equals("FAM"))
        {
            RelationalContext relCtx = new RelationalContext(currentTag.getAttribute("name"));
            for(int i = 0; i < currentTag.getChildNodes().getLength(); i++)
                if(currentTag.getChildNodes().item(i).getNodeType() == 1)
                {
                    currentTag = (Element)currentTag.getChildNodes().item(i);
                    if(currentTag.getTagName().equals("BIN") && currentTag.getAttribute("type").equals("BinaryRelation"))
                    {
                        relCtx.addRelation(readBinaryRelation());
                        currentTag = (Element)currentTag.getParentNode();
                    } else
                    if(currentTag.getTagName().equals("BIN") && currentTag.getAttribute("type").equals("ScallingBinaryRelation"))
                    {
                        relCtx.addRelation(readScalingBinaryRelation());
                        currentTag = (Element)currentTag.getParentNode();
                    } else
                    if(currentTag.getTagName().equals("BIN") && currentTag.getAttribute("type").equals("InterObjectBinaryRelation"))
                    {
                        relCtx.addRelation(readInterObjectBinaryRelation());
                        currentTag = (Element)currentTag.getParentNode();
                    } else
                    if(currentTag.getTagName().equals("MUL"))
                    {
                        relCtx.addRelation(readMultiValuedRelation());
                        currentTag = (Element)currentTag.getParentNode();
                    }
                }

            return relCtx;
        } else
        {
            throw new BadInputDataException("Reading XML BinaryRelation wrong format !");
        }
    }

    public ConceptLattice readConceptLattice()
        throws BadInputDataException, IOException
    {
        if(currentTag != null && currentTag.getTagName().equals("LAT"))
        {
            ConceptLattice lcl = null;
            if(currentTag.getAttribute("type").equals("LinkedConceptLattice"))
                lcl = new LinkedConceptLattice();
            if(currentTag.getAttribute("type").equals("LatticeGraph"))
                lcl = new LatticeGraph();
            Hashtable idObj = new Hashtable();
            Hashtable idAtt = new Hashtable();
            Hashtable idNode = new Hashtable();
            NodeList nl = ((Element)currentTag.getElementsByTagName("OBJS").item(0)).getElementsByTagName("OBJ");
            for(int i = 0; i < nl.getLength(); i++)
            {
                Element obj = (Element)nl.item(i);
                idObj.put(obj.getAttribute("id"), new DefaultFormalObject(((Text)obj.getChildNodes().item(0)).getNodeValue()));
            }

            nl = ((Element)currentTag.getElementsByTagName("ATTS").item(0)).getElementsByTagName("ATT");
            for(int i = 0; i < nl.getLength(); i++)
            {
                Element att = (Element)nl.item(i);
                idAtt.put(att.getAttribute("id"), new DefaultFormalAttribute(((Text)att.getChildNodes().item(0)).getNodeValue()));
            }

            nl = ((Element)currentTag.getElementsByTagName("NODS").item(0)).getElementsByTagName("NOD");
            boolean firstNode = true;
            for(int i = 0; i < nl.getLength(); i++)
            {
                Element nod = (Element)nl.item(i);
                lattice.util.Extent extent = new SetExtent();
                lattice.util.Intent intent = new SetIntent();
                NodeList nl2 = ((Element)nod.getElementsByTagName("EXT").item(0)).getElementsByTagName("OBJ");
                for(int j = 0; j < nl2.getLength(); j++)
                {
                    Element obj = (Element)nl2.item(j);
                    extent.add(idObj.get(obj.getAttribute("id")));
                }

                nl2 = ((Element)nod.getElementsByTagName("INT").item(0)).getElementsByTagName("ATT");
                for(int j = 0; j < nl2.getLength(); j++)
                {
                    Element att = (Element)nl2.item(j);
                    intent.add(idAtt.get(att.getAttribute("id")));
                }

                lattice.util.Node newNode = new LatticeNode(new Concept(extent, intent));
                if(firstNode)
                {
                    lcl.setTop(newNode);
                    firstNode = false;
                    if(lcl instanceof LinkedConceptLattice)
                        ((LinkedConceptLattice)lcl).inc_nbr_concept();
                } else
                {
                    nl2 = ((Element)nod.getElementsByTagName("SUP_NOD").item(0)).getElementsByTagName("PARENT");
                    for(int j = 0; j < nl2.getLength(); j++)
                    {
                        Element parent = (Element)nl2.item(j);
                        lattice.util.Node supNode = (lattice.util.Node)idNode.get(parent.getAttribute("id"));
                        newNode.addParent(supNode);
                        supNode.addChild(newNode);
                    }

                    if(lcl instanceof LatticeGraph)
                        ((LatticeGraph)lcl).addNode(newNode);
                    if(lcl instanceof LinkedConceptLattice)
                        ((LinkedConceptLattice)lcl).inc_nbr_concept();
                }
                idNode.put(nod.getAttribute("id"), newNode);
            }

            return lcl;
        } else
        {
            throw new BadInputDataException("Reading XML BinaryRelation wrong format !");
        }
    }

    public void run()
    {
        try
        {
            DocumentBuilder docBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
            InputSource is = new InputSource(getStream());
            Document doc = docBuilder.parse(is);
            currentTag = doc.getDocumentElement();
            if(typeOfReading == OpenFileFrame.BINARY_DATA)
                data = readBinaryRelation();
            if(typeOfReading == OpenFileFrame.MULTI_VALUE_DATA)
                data = readMultiValuedRelation();
            if(typeOfReading == OpenFileFrame.FAMILLY_DATA)
                data = readRelationalContext();
            if(typeOfReading == OpenFileFrame.LATTICE_DATA)
                data = readConceptLattice();
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

    public void setTypeOfReading(int tr)
    {
        typeOfReading = tr;
    }

    public int getTypeOfReading()
    {
        return typeOfReading;
    }
}
