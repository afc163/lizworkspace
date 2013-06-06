// Decompiled by Jad v1.5.7g. Copyright 2000 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/SiliconValley/Bridge/8617/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi 
// Source File Name:   ValtchevEtAl.java

package lattice.algorithm;

import java.io.PrintStream;
import java.util.*;
import lattice.gui.tooltask.AbstractJob;
import lattice.gui.tooltask.JobObserver;
import lattice.util.*;

// Referenced classes of package lattice.algorithm:
//            LatticeAlgorithm

public class ValtchevEtAl extends LatticeAlgorithm
{

    private Vector col;

    public ValtchevEtAl(BinaryRelation br)
    {
        super(br);
        Date date = new Date();
        System.out.println("Begin Construct ini concept from " + date.toString());
        col = (Vector)br.getInitialObjectPreConcept(BinaryRelation.NO_SORT);
        date = new Date();
        System.out.println("End Construct ini concept at " + date.toString());
        if(!col.isEmpty())
        {
            date = new Date();
            System.out.println("Begin initialization from " + date.toString());
            iniLattice(col, br);
            date = new Date();
            System.out.println("End initialization at " + date.toString());
        }
    }

    public void doAlgorithm()
    {
        jobObserv.sendMessage("Incremental Algorithm is in progress !\n");
        doIncre(col);
    }

    public String getDescription()
    {
        return "Valtchev & al.";
    }

    public void doIncre(Vector col)
    {
        int size = col.size();
        int j = 1;
        Date date = new Date();
        System.out.println("Add the first object at " + date.toString() + "\n");
        for(int i = 0; i < size; i++)
        {
            if(i == j * 100)
            {
                System.out.println("Add the " + i + "th object" + " at ");
                j++;
                date = new Date();
                System.out.println(date.toString() + "\n");
            }
            addObject((Concept)col.get(i));
        }

        date = new Date();
        System.out.println("Finish the algorithm at " + date.toString());
    }

    private void iniLattice(Vector col, BinaryRelation br)
    {
        Node topNode = new LatticeNode((Concept)col.get(0));
        getLattice().setTop(topNode);
        getLattice().inc_nbr_concept();
        getLattice().initialiseIntentLevelIndex(br.getAttributesNumber() + 1);
        int sizeIntent = ((Concept)col.get(0)).getIntent().size();
        int size = br.getAttributesNumber();
        if(size > sizeIntent)
        {
            Concept bottom = null;
            Intent bottomIntent = new SetIntent();
            Extent bottomExtent = new SetExtent();
            lattice.util.FormalAttribute fa[] = br.getFormalAttributes();
            for(int i = 0; i < size; i++)
                bottomIntent.add(fa[i]);

            bottom = new Concept(bottomExtent, bottomIntent);
            Node bottomNode = new LatticeNode(bottom);
            getLattice().linkNodeToUpperCover(topNode, bottomNode);
            getLattice().inc_nbr_concept();
        }
        col.remove(0);
    }

    private Iterator preProcess()
    {
        TreeMap sliceLattice = new TreeMap();
        Vector candidate = new Vector();
        Hashtable dealedConcept = new Hashtable();
        Node topNode = getLattice().getTop();
        candidate.add(topNode);
        while(!candidate.isEmpty()) 
        {
            Node cand = (Node)candidate.get(0);
            if(dealedConcept.get(cand) != null)
            {
                candidate.remove(0);
            } else
            {
                Integer intentLen = new Integer(cand.getConcept().getIntent().size());
                Vector intentNode = new Vector();
                if((intentNode = (Vector)sliceLattice.get(intentLen)) != null)
                {
                    intentNode.add(cand);
                } else
                {
                    intentNode = new Vector();
                    intentNode.add(cand);
                    sliceLattice.put(intentLen, intentNode);
                }
                dealedConcept.put(cand, "");
                java.util.List children = cand.getChildren();
                candidate.addAll(children);
                candidate.remove(0);
            }
        }
        Iterator allConcepts = sliceLattice.values().iterator();
        return allConcepts;
    }

    private void addObject(Concept newConcept)
    {
        Vector allConcepts = new Vector();
        Hashtable modifier = new Hashtable();
        Hashtable chiPlus = new Hashtable();
        for(Iterator iterNode = preProcess(); iterNode.hasNext();)
        {
            Vector intentNode = (Vector)iterNode.next();
            int nodeSize = intentNode.size();
            for(int j = 0; j < nodeSize; j++)
            {
                Node firstNode = (Node)intentNode.get(j);
                Concept firstConcept = firstNode.getConcept();
                Vector upp = uppCover(firstNode);
                Node newMaxNodeTemp = argMax(upp, newConcept);
                Node newMaxNode = null;
                if(newMaxNodeTemp != null)
                    newMaxNode = (Node)chiPlus.get(newMaxNodeTemp);
                int lengNew;
                if(newMaxNode == null)
                {
                    lengNew = -1;
                } else
                {
                    Concept newMaxConcept = newMaxNode.getConcept();
                    lengNew = funcQ(newMaxConcept, newConcept).size();
                }
                int lengC = funcQ(firstConcept, newConcept).size();
                if(lengC != lengNew)
                    if(lengC == firstConcept.getIntent().size())
                    {
                        Extent extentC = firstConcept.getExtent();
                        extentC = extentC.union(newConcept.getExtent());
                        firstConcept.setExtent(extentC);
                        ((LatticeNode)firstNode).concept = firstConcept;
                        modifier.put(extentC, firstNode);
                        newMaxNode = firstNode;
                    } else
                    {
                        Extent extGenC = (Extent)firstConcept.getExtent().union(newConcept.getExtent()).clone();
                        Concept genC = new Concept(extGenC, (Intent)funcQ(firstConcept, newConcept).clone());
                        Node genNode = new LatticeNode(genC);
                        for(int n = 0; n < upp.size(); n++);
                        Vector minClo = minClosed(genC, minCandidate(upp, chiPlus));
                        int size = minClo.size();
                        for(int i = 0; i < size; i++)
                        {
                            Node coverNode = (Node)minClo.get(i);
                            newLink(coverNode, genNode);
                            if(modifier.get(coverNode.getConcept().getExtent()) != null)
                                dropLink(coverNode, firstNode);
                        }

                        newLink(genNode, firstNode);
                        if(firstNode == getLattice().getTop())
                            getLattice().setTop(genNode);
                        newMaxNode = genNode;
                        getLattice().addNodeToIntentLevelIndex(genNode);
                        getLattice().inc_nbr_concept();
                    }
                chiPlus.put(firstNode, newMaxNode);
            }

        }

    }

    public Vector uppCover(Node node)
    {
        Vector upp = new Vector();
        upp.addAll(node.getParents());
        return upp;
    }

    public Node argMax(Vector upp, Concept newConcept)
    {
        Node max = null;
        if(upp.isEmpty())
            return max;
        int intentLength = funcQ(((Node)upp.get(0)).getConcept(), newConcept).size();
        max = (Node)upp.get(0);
        int size = upp.size();
        for(int i = 1; i < size; i++)
        {
            int uppLength = funcQ(((Node)upp.get(i)).getConcept(), newConcept).size();
            if(uppLength > intentLength)
            {
                intentLength = uppLength;
                max = (Node)upp.get(i);
            }
        }

        return max;
    }

    public Intent funcQ(Concept firstConcept, Concept newConcept)
    {
        Intent cBar = firstConcept.getIntent().intersection(newConcept.getIntent());
        return cBar;
    }

    public Vector minCandidate(Vector upp, Hashtable chiPlus)
    {
        Vector candidate = new Vector();
        TreeMap sliceCand = new TreeMap();
        int size = upp.size();
        for(int i = 0; i < size; i++)
        {
            Node node = (Node)chiPlus.get((Node)upp.get(i));
            if(node != null)
            {
                Integer intentLen = new Integer(node.getConcept().getExtent().size());
                Vector sortCand = new Vector();
                if((sortCand = (Vector)sliceCand.get(intentLen)) != null)
                {
                    sortCand.add(node);
                } else
                {
                    sortCand = new Vector();
                    sortCand.add(node);
                    sliceCand.put(intentLen, sortCand);
                }
            }
        }

        for(Iterator allCand = sliceCand.values().iterator(); allCand.hasNext();)
        {
            Vector sortCand = (Vector)allCand.next();
            int nodeSize = sortCand.size();
            for(int j = 0; j < nodeSize; j++)
                candidate.add((Node)sortCand.get(j));

        }

        return candidate;
    }

    public Vector minClosed(Concept first, Vector candidate)
    {
        Vector minClo = new Vector();
        Extent firstExtent = first.getExtent();
        Extent face = (Extent)firstExtent.clone();
        int size = candidate.size();
        for(int i = 0; i < size; i++)
        {
            Concept conCBar = ((Node)candidate.get(i)).getConcept();
            Extent extCBar = conCBar.getExtent();
            if(firstExtent.toString().equals(face.intersection(extCBar).toString()))
            {
                minClo.add((Node)candidate.get(i));
                face = face.union(extCBar);
            }
        }

        return minClo;
    }

    public void newLink(Node node, Node childNode)
    {
        getLattice().linkNodeToUpperCover(node, childNode);
    }

    public void dropLink(Node node, Node childNode)
    {
        childNode.removeParent(node);
        node.removeChild(childNode);
    }
}
