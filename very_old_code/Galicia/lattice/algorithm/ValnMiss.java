// Decompiled by Jad v1.5.7g. Copyright 2000 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/SiliconValley/Bridge/8617/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi 
// Source File Name:   ValnMiss.java

package lattice.algorithm;

import java.util.*;
import lattice.util.*;

// Referenced classes of package lattice.algorithm:
//            LatticeAlgorithm

public class ValnMiss extends LatticeAlgorithm
{

    public ValnMiss(BinaryRelation br)
    {
        super(br);
    }

    protected LatticeNode isAGenerator2(Vector candidates[], Intent new_intent)
    {
        if(candidates[new_intent.size()].isEmpty())
            return null;
        else
            return (LatticeNode)candidates[new_intent.size()].elementAt(0);
    }

    protected LatticeNode isAGenerator3(LatticeNode psi[], Intent new_intent, LatticeNode current_node)
    {
        int size = new_intent.size();
        for(Iterator i = current_node.parents.iterator(); i.hasNext();)
        {
            LatticeNode candidate = psi[((LatticeNode)i.next()).id.intValue()];
            if(candidate.concept.getIntent().size() == size)
                return candidate;
        }

        return null;
    }

    protected void findUpperCovers(Vector candidates[], LatticeNode current_node, LatticeNode new_node)
    {
        Vector true_covers = minima(candidates, new_node, current_node);
        for(Enumeration e = true_covers.elements(); e.hasMoreElements();)
        {
            LatticeNode parent = (LatticeNode)e.nextElement();
            new_node.parents.add(parent);
            parent.children.add(new_node);
            if(current_node.parents.contains(parent))
            {
                current_node.parents.remove(parent);
                parent.children.remove(current_node);
            }
        }

        current_node.parents.add(new_node);
        new_node.children.add(current_node);
    }

    protected void findUpperCovers3(Vector candidates[], LatticeNode current_node, LatticeNode new_node)
    {
        Vector true_covers = minima3(candidates, new_node, current_node);
        int size = true_covers.size();
        for(int i = 0; i < size; i++)
        {
            LatticeNode parent = (LatticeNode)true_covers.elementAt(i);
            new_node.parents.add(parent);
            parent.children.add(new_node);
            if(current_node.parents.contains(parent))
            {
                current_node.parents.remove(parent);
                parent.children.remove(current_node);
            }
        }

        current_node.parents.add(new_node);
        new_node.children.add(current_node);
    }

    protected Vector minima(Vector candidates[], LatticeNode new_node, LatticeNode current_node)
    {
        Vector trueCovers = new Vector();
        Extent knownExtent = current_node.concept.getExtent().union(new_node.concept.getExtent());
        for(int i = candidates.length - 1; i >= 0; i--)
        {
            for(int j = 0; j < candidates[i].size(); j++)
                if(((LatticeNode)candidates[i].elementAt(j)).concept.getExtent().intersection(knownExtent).equals(new_node.concept.getExtent()))
                {
                    knownExtent.addAll(((LatticeNode)candidates[i].elementAt(j)).concept.getExtent());
                    trueCovers.add(candidates[i].elementAt(j));
                }

        }

        return trueCovers;
    }

    protected Vector minima3(Vector candidates[], LatticeNode new_node, LatticeNode current_node)
    {
        Vector trueCovers = new Vector();
        int i;
        for(i = candidates.length - 1; i >= 0; i--)
        {
            if(candidates[i].isEmpty())
                continue;
            trueCovers.addAll(candidates[i]);
            break;
        }

        for(i--; i >= 0; i--)
        {
            for(int j = 0; j < candidates[i].size(); j++)
            {
                LatticeNode node = (LatticeNode)candidates[i].elementAt(j);
                if(isAMinima(trueCovers, node))
                    trueCovers.add(node);
            }

        }

        return trueCovers;
    }

    protected boolean isAMinima(Vector trueCovers, LatticeNode node)
    {
        for(int i = 0; i < trueCovers.size(); i++)
            if(((LatticeNode)trueCovers.elementAt(i)).concept.getIntent().containsAll(node.concept.getIntent()))
                return false;

        return true;
    }

    public void doAlgorithm()
    {
        addAll_VM_Ext(binRel.getInitialObjectPreConcept(BinaryRelation.NO_SORT));
        binRel.setLattice(getLattice());
    }

    public void addAll_VM_Ext(Collection coll)
    {
        int maxClass = coll.size();
        int nbClass = 0;
        for(Iterator iter = coll.iterator(); iter.hasNext();)
        {
            addObjectExt((Concept)iter.next());
            nbClass++;
        }

    }

    public void addAll_VM_Int(Collection coll)
    {
        for(Iterator iter = coll.iterator(); iter.hasNext(); addObjectInt((Concept)iter.next()));
    }

    public void addObjectExt(Concept element)
    {
        if(getLattice().getBottom() == null)
        {
            lattice.util.Node n = new LatticeNode(element);
            getLattice().setTop(n);
            getLattice().setBottom(n);
            getLattice().initialiseIntentLevelIndex(element.getIntent().size() + 1);
            getLattice().set_max_transaction_size(element.getIntent().size());
            getLattice().addBottomToIntentLevelIndex(getLattice().getBottom());
            getLattice().inc_nbr_concept();
        } else
        {
            getLattice().adjustBottom(element);
            int max_intent_card = getLattice().get_intent_level_index().size();
            LatticeNode psi[] = new LatticeNode[LatticeNode.next_id];
            for(int i = 0; i < max_intent_card; i++)
            {
                int nbr_element = ((Vector)getLattice().get_intent_level_index().elementAt(i)).size();
                for(int j = 0; j < nbr_element; j++)
                {
                    LatticeNode current_node = (LatticeNode)((Vector)getLattice().get_intent_level_index().elementAt(i)).elementAt(j);
                    if(getLattice().isAMofiedNode(element, current_node))
                    {
                        current_node.concept.getExtent().addAll(element.getExtent());
                        psi[current_node.id.intValue()] = current_node;
                        if(current_node.concept.getIntent().equals(element.getIntent()))
                        {
                            getLattice().setTop(getLattice().findTop());
                            return;
                        }
                    } else
                    {
                        Intent new_intent = current_node.concept.getIntent().intersection(element.getIntent());
                        Vector candidates[] = getLattice().candidates(current_node, psi);
                        LatticeNode new_node;
                        if((new_node = isAGenerator2(candidates, new_intent)) == null)
                        {
                            Concept new_concept = new Concept(current_node.concept.getExtent().union(element.getExtent()), new_intent);
                            new_node = new LatticeNode(new_concept);
                            getLattice().addNodeToIntentLevelIndex(new_node);
                            findUpperCovers(candidates, current_node, new_node);
                            getLattice().inc_nbr_concept();
                            if(new_intent.equals(element.getIntent()))
                            {
                                getLattice().setTop(getLattice().findTop());
                                return;
                            }
                        }
                        psi[current_node.id.intValue()] = new_node;
                    }
                }

            }

            getLattice().setTop(getLattice().findTop());
        }
    }

    public void addObjectInt(Concept element)
    {
        if(getLattice().getBottom() == null)
        {
            lattice.util.Node n = new LatticeNode(element);
            getLattice().setTop(n);
            getLattice().setBottom(n);
            getLattice().addNodeToIntentLevelIndex(getLattice().getBottom());
            getLattice().inc_nbr_concept();
        } else
        {
            getLattice().adjustBottom(element);
            int max_intent_card = getLattice().get_intent_level_index().size();
            LatticeNode psi[] = new LatticeNode[LatticeNode.next_id];
            for(int i = 0; i < max_intent_card; i++)
            {
                int nbr_element = ((Vector)getLattice().get_intent_level_index().elementAt(i)).size();
                for(int j = 0; j < nbr_element; j++)
                {
                    LatticeNode current_node = (LatticeNode)((Vector)getLattice().get_intent_level_index().elementAt(i)).elementAt(j);
                    Intent new_intent = current_node.concept.getIntent().intersection(element.getIntent());
                    if(new_intent.size() == current_node.concept.getIntent().size())
                    {
                        current_node.concept.getExtent().addAll(element.getExtent());
                        psi[current_node.id.intValue()] = current_node;
                        if(current_node.concept.getIntent().equals(element.getIntent()))
                        {
                            getLattice().setTop(getLattice().findTop());
                            return;
                        }
                    } else
                    {
                        LatticeNode new_node;
                        if((new_node = isAGenerator3(psi, new_intent, current_node)) == null)
                        {
                            Concept new_concept = new Concept(current_node.concept.getExtent().union(element.getExtent()), new_intent);
                            new_node = new LatticeNode(new_concept);
                            getLattice().addNodeToIntentLevelIndex(new_node);
                            Vector candidates[] = getLattice().candidates(current_node, psi);
                            findUpperCovers3(candidates, current_node, new_node);
                            getLattice().inc_nbr_concept();
                            if(new_intent.equals(element.getIntent()))
                            {
                                getLattice().setTop(getLattice().findTop());
                                return;
                            }
                        }
                        psi[current_node.id.intValue()] = new_node;
                    }
                }

            }

            getLattice().setTop(getLattice().findTop());
        }
    }

    public String getDescription()
    {
        return "ValnMiss Lattice Alg.";
    }
}
