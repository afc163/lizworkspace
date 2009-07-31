// Decompiled by Jad v1.5.7g. Copyright 2000 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/SiliconValley/Bridge/8617/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi 
// Source File Name:   GodinFilter.java

package lattice.algorithm;

import java.util.*;
import lattice.util.*;

// Referenced classes of package lattice.algorithm:
//            AbstractGodinAlgorithm, LatticeAlgorithm

public class GodinFilter extends AbstractGodinAlgorithm
{

    public GodinFilter(BinaryRelation br)
    {
        super(br);
    }

    protected Vector selectAndClassify(Concept concept)
    {
        Vector selected_nodes = new Vector();
        getLattice().initialiseVector(selected_nodes, getLattice().get_max_transaction_size() + 2);
        adjustTop(concept, selected_nodes);
        LatticeNode node_item;
        for(Iterator it = concept.getIntent().iterator(); it.hasNext(); search(node_item, selected_nodes))
            node_item = (LatticeNode)getLattice().get_item_index().get(it.next());

        ((Vector)selected_nodes.lastElement()).add(getLattice().getBottom());
        cleanNodes(selected_nodes);
        return selected_nodes;
    }

    protected void adjustTop(Concept concept, Vector selected_nodes)
    {
        if(!getLattice().getTop().getConcept().getIntent().isEmpty())
        {
            Intent top_intent_intersection = getLattice().getTop().getConcept().getIntent().intersection(concept.getIntent());
            if(top_intent_intersection.isEmpty())
            {
                Node temp_node = getLattice().getTop();
                getLattice().setTop(new LatticeNode(new Concept(concept.getExtent().union(temp_node.getConcept().getExtent()), top_intent_intersection)));
                temp_node.getParents().add(getLattice().getTop());
                getLattice().addNodeToIntentLevelIndex(getLattice().getTop());
                getLattice().inc_nbr_concept();
                ((Vector)selected_nodes.firstElement()).add(getLattice().getTop());
            }
        } else
        {
            ((Vector)selected_nodes.firstElement()).add(getLattice().getTop());
        }
    }

    protected void search(LatticeNode node, Vector selected_nodes)
    {
        if(!node.visited)
        {
            ((Vector)selected_nodes.elementAt(node.concept.getIntent().size())).add(node);
            node.visited = true;
        }
        for(Iterator it = node.children.iterator(); it.hasNext();)
        {
            LatticeNode node_temp = (LatticeNode)it.next();
            if(!node_temp.visited)
                search(node_temp, selected_nodes);
        }

    }

    protected void cleanNodes(Vector selected_nodes)
    {
        for(int i = 0; i < selected_nodes.size(); i++)
        {
            Vector vector = (Vector)selected_nodes.elementAt(i);
            for(int j = 0; j < vector.size(); j++)
                ((LatticeNode)vector.elementAt(j)).visited = false;

        }

        getLattice().getBottom().setVisited(true);
    }

    public void addConcept(Concept concept)
    {
        if(getLattice().getBottom() == null)
        {
            initFirst(concept);
        } else
        {
            getLattice().adjustBottom(concept);
            Vector selected_nodes = selectAndClassify(concept);
            Intent intent_index = (Intent)concept.getIntent().clone();
            Vector temp[] = new Vector[concept.getIntent().size() + 1];
            getLattice().initialiseArray(temp);
            for(int i = 0; i < selected_nodes.size(); i++)
            {
                Vector vector = (Vector)selected_nodes.elementAt(i);
                for(int j = 0; j < vector.size(); j++)
                {
                    LatticeNode current_node = (LatticeNode)vector.elementAt(j);
                    Intent new_intent = current_node.concept.getIntent().intersection(concept.getIntent());
                    if(new_intent.size() == current_node.concept.getIntent().size())
                    {
                        current_node.concept.getExtent().addAll(concept.getExtent());
                        temp[i].add(current_node);
                        if(!intent_index.isEmpty())
                            intent_index.removeAll(new_intent);
                        if(current_node.concept.getIntent().equals(concept.getIntent()))
                        {
                            getLattice().setTop(getLattice().findTop());
                            return;
                        }
                    } else
                    if(getLattice().isAGenerator(new_intent, temp))
                    {
                        Concept new_concept = new Concept(current_node.concept.getExtent().union(concept.getExtent()), new_intent);
                        LatticeNode new_node = new LatticeNode(new_concept);
                        getLattice().addNodeToIntentLevelIndex(new_node);
                        temp[new_intent.size()].add(new_node);
                        getLattice().modifyEdges(current_node, new_node, temp);
                        getLattice().inc_nbr_concept();
                        if(!intent_index.isEmpty())
                        {
                            Intent intent = new_intent.intersection(intent_index);
                            for(Iterator it = intent.iterator(); it.hasNext(); getLattice().get_item_index().put(it.next(), new_node));
                            intent_index.removeAll(intent);
                        }
                        if(new_intent.equals(concept.getIntent()))
                        {
                            getLattice().setTop(getLattice().findTop());
                            return;
                        }
                    }
                }

            }

            getLattice().setTop(getLattice().findTop());
        }
    }

    public String getDescription()
    {
        return "Godin Filter Lattice Alg.";
    }
}
