// Decompiled by Jad v1.5.7g. Copyright 2000 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/SiliconValley/Bridge/8617/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi 
// Source File Name:   BordatIceberg.java

package lattice.iceberg.algorithm;

import java.io.PrintStream;
import java.util.*;
import lattice.algorithm.LatticeAlgorithm;
import lattice.gui.tooltask.AbstractJob;
import lattice.gui.tooltask.JobObserver;
import lattice.util.*;

public class BordatIceberg extends LatticeAlgorithm
{

    private Vector col;
    private int nbrAttrs;
    private int nbrObjs;
    private double minSupp;
    private Concept bottom;
    private Hashtable objs;

    public BordatIceberg(BinaryRelation br, double minSupp)
    {
        super(br);
        Date date = new Date();
        System.out.println("Begin object construction from " + date.toString());
        this.minSupp = minSupp;
        nbrAttrs = br.getAttributesNumber();
        nbrObjs = br.getObjectsNumber();
        Concept topConcept = null;
        Concept bottomConcept = null;
        col = (Vector)br.getInitialObjectPreConcept(BinaryRelation.NO_SORT);
        objs = new Hashtable(nbrObjs);
        int size = col.size();
        for(int i = 0; i < size; i++)
            objs.put(((FormalObject)((Concept)col.get(i)).getExtent().first()).getName(), new Integer(i));

        topConcept = findTopConcept(col);
        bottom = findBottomConcept(br);
        getLattice().inc_nbr_concept();
        getLattice().setTop(new LatticeNode(topConcept));
        getLattice().initialiseIntentLevelIndex(br.getAttributesNumber() + 1);
        date = new Date();
        System.out.println("End object construction from " + date.toString());
    }

    public BordatIceberg(BinaryRelation br)
    {
        this(br, 0.0D);
    }

    public void doAlgorithm()
    {
        jobObserv.sendMessage("Bordat Algorithm is in progress !\n");
        Node top = lcl.getTop();
        doBordat(top);
    }

    public String getDescription()
    {
        return "Bordat Lattice(Iceberg) Algorithm";
    }

    public Concept findTopConcept(Collection col)
    {
        Concept top = null;
        Intent topIntent = new SetIntent();
        Extent topExtent = new SetExtent();
        Iterator iter = col.iterator();
        if(iter.hasNext())
        {
            Concept first = (Concept)iter.next();
            topIntent = first.getIntent();
            topExtent = first.getExtent();
        }
        while(iter.hasNext()) 
        {
            Concept next = (Concept)iter.next();
            topIntent = next.getIntent().intersection(topIntent);
            topExtent = next.getExtent().union(topExtent);
        }
        top = new Concept(topExtent, topIntent);
        return top;
    }

    public Concept findBottomConcept(BinaryRelation br)
    {
        Concept bottom = null;
        Intent bottomIntent = new SetIntent();
        Extent bottomExtent = new SetExtent();
        lattice.util.FormalAttribute fa[] = br.getFormalAttributes();
        int size = fa.length;
        for(int i = 0; i < size; i++)
            bottomIntent.add(fa[i]);

        bottom = new Concept(bottomExtent, bottomIntent);
        return bottom;
    }

    public void doBordat(Node top)
    {
        process(top);
    }

    public Concept getTop()
    {
        Concept top = null;
        top = lcl.getTop().getConcept();
        return top;
    }

    public void process(Node node)
    {
        Concept concept = node.getConcept();
        Intent intent = concept.getIntent();
        LinkedList candidate = new LinkedList();
        Hashtable nodeHash = new Hashtable();
        nodeHash.put(concept.toString(), node);
        candidate.addLast(node);
        long nodeProcessed = 0L;
        int j = 1;
        Date date = new Date();
        System.out.println("Begin Calculation from " + date.toString());
        do
        {
            nodeProcessed++;
            if(nodeProcessed == (long)(1000 * j))
            {
                date = new Date();
                System.out.println("Process the " + nodeProcessed + "th node at " + date.toString());
                j++;
            }
            node = (Node)candidate.getFirst();
            concept = node.getConcept();
            Vector lowerCoverFirst = findLowerCover(concept.getExtent(), concept.getIntent());
            int size = lowerCoverFirst.size();
            for(int i = 0; i < size; i++)
            {
                Concept lowerConcept = (Concept)lowerCoverFirst.get(i);
                if((double)lowerConcept.getExtent().size() / (double)nbrObjs >= minSupp)
                {
                    Node childNode;
                    if((childNode = (Node)nodeHash.get(lowerConcept.toString())) == null)
                    {
                        childNode = new LatticeNode(lowerConcept);
                        nodeHash.put(lowerConcept.toString(), childNode);
                        candidate.addLast(childNode);
                        getLattice().inc_nbr_concept();
                    }
                    getLattice().linkNodeToUpperCover(node, childNode);
                }
            }

            getLattice().addNodeToIntentLevelIndex(node);
            candidate.removeFirst();
        } while(!candidate.isEmpty());
        date = new Date();
        System.out.println("The " + nodeProcessed + "th node is the last one, it was processed at " + date.toString());
    }

    private Vector findLowerCover(Extent extent, Intent intent)
    {
        Vector lowerCover = new Vector();
        Intent allAttrs = (Intent)intent.clone();
        Concept firstObj;
        for(Iterator it = extent.iterator(); (firstObj = findFirstObj(allAttrs, it)) != null; it = extent.iterator())
        {
            Intent objIntent = firstObj.getIntent();
            Extent objExtent = firstObj.getExtent();
            while(it.hasNext()) 
            {
                Concept nextObj = nextObj(it);
                if(!allAttrs.containsAll(nextObj.getIntent().intersection(objIntent)))
                {
                    objExtent = objExtent.union(nextObj.getExtent());
                    objIntent = objIntent.intersection(nextObj.getIntent());
                }
            }
            if(objIntent.intersection(allAttrs).equals(intent))
                lowerCover.add(new Concept(objExtent, objIntent));
            allAttrs = allAttrs.union(objIntent);
        }

        if(lowerCover.size() == 0 && intent.size() != nbrAttrs)
            lowerCover.add(bottom);
        return lowerCover;
    }

    private Concept findFirstObj(Intent intent, Iterator it)
    {
        Concept first = null;
        Concept firstConcept;
        for(firstConcept = null; it.hasNext(); firstConcept = null)
        {
            FormalObject next = (FormalObject)it.next();
            String name = next.getName();
            int nobj = ((Integer)objs.get(name)).intValue();
            firstConcept = (Concept)col.elementAt(nobj);
            if(!intent.containsAll(firstConcept.getIntent()))
                break;
        }

        if(firstConcept != null)
            first = new Concept((Extent)firstConcept.getExtent().clone(), (Intent)firstConcept.getIntent().clone());
        return first;
    }

    private Concept nextObj(Iterator it)
    {
        Concept next = new Concept(new SetExtent(), new SetIntent());
        Concept nextConcept = null;
        if(it.hasNext())
        {
            FormalObject nextObj = (FormalObject)it.next();
            String name = nextObj.getName();
            int nobj = ((Integer)objs.get(name)).intValue();
            nextConcept = (Concept)col.elementAt(nobj);
            next.setExtent((Extent)nextConcept.getExtent().clone());
            next.setIntent((Intent)nextConcept.getIntent().clone());
        }
        return next;
    }

    private Concept findFirstObj1(Intent intent, Iterator it)
    {
        Concept first = null;
        Concept firstConcept;
        for(firstConcept = null; it.hasNext(); firstConcept = null)
        {
            FormalObject next = (FormalObject)it.next();
            String name = next.getName();
            StringTokenizer st = new StringTokenizer(name, "_");
            String noObj = "";
            int nobj = -1;
            while(st.hasMoreTokens()) 
                noObj = st.nextToken();
            try
            {
                nobj = Integer.parseInt(noObj);
            }
            catch(NumberFormatException numberformatexception) { }
            firstConcept = (Concept)col.elementAt(nobj);
            if(!intent.containsAll(firstConcept.getIntent()))
                break;
        }

        if(firstConcept != null)
            first = new Concept((Extent)firstConcept.getExtent().clone(), (Intent)firstConcept.getIntent().clone());
        return first;
    }

    private Concept nextObj1(Iterator it)
    {
        Concept next = new Concept(new SetExtent(), new SetIntent());
        Concept nextConcept = null;
        if(it.hasNext())
        {
            FormalObject nextObj = (FormalObject)it.next();
            String name = nextObj.getName();
            StringTokenizer st = new StringTokenizer(name, "_");
            String noObj = "";
            int nobj = -1;
            while(st.hasMoreTokens()) 
                noObj = st.nextToken();
            try
            {
                nobj = Integer.parseInt(noObj);
            }
            catch(NumberFormatException numberformatexception) { }
            nextConcept = (Concept)col.elementAt(nobj);
            next.setExtent((Extent)nextConcept.getExtent().clone());
            next.setIntent((Intent)nextConcept.getIntent().clone());
        }
        return next;
    }
}
