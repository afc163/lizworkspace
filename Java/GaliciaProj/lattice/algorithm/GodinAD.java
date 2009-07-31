// Decompiled by Jad v1.5.7g. Copyright 2000 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/SiliconValley/Bridge/8617/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi 
// Source File Name:   GodinAD.java

package lattice.algorithm;

import java.io.PrintStream;
import java.util.*;
import lattice.gui.tooltask.AbstractJob;
import lattice.util.*;

// Referenced classes of package lattice.algorithm:
//            LatticeAlgorithm

public class GodinAD extends LatticeAlgorithm
{

    private BinaryRelation br;
    ConceptLattice treillis;
    Vector li1;

    public GodinAD(BinaryRelation br)
    {
        super(br);
        this.br = br;
    }

    public String getDescription()
    {
        return "Godin Lattice Algorithm";
    }

    public void doAlgorithm()
    {
        long time = System.currentTimeMillis();
        int nbObjets = br.getObjectsNumber();
        //��ν�ĳ�ʼ�����ɴ�treilis�б仯�ˣ���Ϊ��
        //��ΪgetLattice�Ǹ����е�public����
        treillis = getLattice();
        li1 = treillis.get_intent_level_index();
        for(int i = 0; i < nbObjets; i++)
        {
            FormalObject o = new DefaultFormalObject(null);
            try
            {
                o = br.getFormalObject(i);
            }
            catch(BadInputDataException bide)
            {
                System.out.println("The indice parameter is not valid");
            }
            doGodin1(o, br.getF(o));
            //�ͽ���������ʾ�й�
            sendJobPercentage((i * 100) / nbObjets);
        }

        System.out.println("FIN " + (System.currentTimeMillis() - time) + " mSec.");
    }

    public void doGodin1(FormalObject objet, Intent intent)
    {
        Extent extent = new SetExtent();
        extent.add(objet);
        Concept concept = new Concept(extent, intent);
        if(treillis.getBottom() == null)
        { //�൱��һ��ʼû��conceptʱ
            treillis.setBottom(new LatticeNode(concept));
            treillis.setTop(treillis.getBottom());
            treillis.initialiseIntentLevelIndex(intent.size() + 1);
            treillis.set_max_transaction_size(intent.size());
            treillis.addNodeToIntentLevelIndex(treillis.getBottom());
            treillis.updateIndexWithNewIntents(intent);
            treillis.inc_nbr_concept();
        } else
        { //���Ѵ��ڸ�ڵ�����
            LatticeNode bottom = (LatticeNode)treillis.getBottom();
            
            if(!bottom.getConcept().getIntent().containsAll(concept.getIntent()))//�Ѵ��ڵĲ������¼����
                if(bottom.getConcept().getExtent().isEmpty())
                {//������ͬ��Godin�㷨
                    bottom.getConcept().getIntent().addAll(concept.getIntent());
                    ((LinkedConceptLattice)treillis).adjustMaxIntentLevelIndex(concept);
                } else
                {
                	//��ͬ��Ϊʲô���²����ں��Ĳ����أ�
                    LatticeNode node = new LatticeNode(new Concept(new SetExtent(), bottom.getConcept().getIntent().union(concept.getIntent())));
                    node.parents.add(bottom);
                    bottom.getChildren().add(node);
                    treillis.setBottom(node);
                    treillis.getBottom().setVisited(true);
                    li1.add(new Vector());  //ûŪ���li1��ʲôʱ���ʼ����
                    ((Vector)li1.lastElement()).add(treillis.getBottom());
                    treillis.inc_nbr_concept();
                }
            Vector li2[] = new Vector[intent.size() + 1];
            for(int i = 0; i < li2.length; i++)
                li2[i] = new Vector();

            for(int i = 0; i < li1.size(); i++)
            {
                for(int m = 0; m < ((Vector)li1.elementAt(i)).size(); m++)
                {
                    Node nd = (Node)((Vector)li1.elementAt(i)).elementAt(m);
                    if(intent.containsAll(nd.getConcept().getIntent()))
                    {//�޸Ĳ���
                        nd.getConcept().getExtent().add(objet);
                        li2[i].add(nd);  //li2����������CIn
                        if(nd.getConcept().getIntent().equals(intent))
                        {
                            treillis.setTop(treillis.findTop());
                            return;
                        }
                    } else
                    {
                        Intent itn = nd.getConcept().getIntent().intersection(intent);
                        if(treillis.isAGenerator(itn, li2))
                        {//�ж���׼��������concept�Ƿ��Ѿ��ڸ��д�����
                            Node newNoeud = new LatticeNode(new Concept(nd.getConcept().getExtent().union(extent), itn));
                            newNoeud.getConcept().getExtent().add(objet);  //����ʡ��
                            treillis.addNodeToIntentLevelIndex(newNoeud);
                            li2[itn.size()].add(newNoeud);
                            treillis.modifyEdgesAD(nd, newNoeud, li2);  //�޸ıߵĹ�ϵ
                            treillis.inc_nbr_concept();
                            if(itn.equals(intent))
                            {  //������������ģ�ֻ�������²���Ķ������ײ��bottom��intersection
                                treillis.setTop(treillis.findTop());
                                return;
                            }
                        }
                    }
                }

            }

            treillis.setTop(treillis.findTop());
        }
    }
}
