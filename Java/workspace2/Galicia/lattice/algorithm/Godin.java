// Decompiled by Jad v1.5.7g. Copyright 2000 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/SiliconValley/Bridge/8617/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi 
// Source File Name:   Godin.java

package lattice.algorithm;

import java.util.Set;
import java.util.Vector;
import lattice.util.*;

// Referenced classes of package lattice.algorithm:
//            AbstractGodinAlgorithm, LatticeAlgorithm

public class Godin extends AbstractGodinAlgorithm
{

	private BinaryRelation br;
	
    public Godin(BinaryRelation br)
    {
        super(br);
        this.br=br;
    }

    public void addConcept(Concept concept)
    {
    	//getBottom��LinkedConceptLattice��ʵ�֣�����node����
        if(getLattice().getBottom() == null)
        {  //�����û�нڵ�
        	//2005-4-8 �޸Ĺ���,����slit�ĳ�ʼ��
            initFirst(concept);  
        } else
        { // Bottom�ײ���ڸ�ڵ�
            getLattice().adjustBottom(concept);
            int max_intent_card = getLattice().get_intent_level_index().size();
            
            ///�൱��CIn ͬʱCIn�ĳ��Ȳ��ᳬ���½�concept��intent�ĳ���
            Vector temp[] = new Vector[concept.getIntent().size() + 1]; 
            
            //ȫ����ʼ��Ϊ��
            getLattice().initialiseArray(temp);
            for(int i = 0; i < max_intent_card; i++)
            { //ɨ���
            	//��øò��ϵ�concept node ����,�����nbr_element
                Vector vector = (Vector)getLattice().get_intent_level_index().elementAt(i);
                int nbr_element = vector.size();
                
                //ɨ��һ�������ÿ���ڵ�
                for(int j = 0; j < nbr_element; j++)
                { 
                    LatticeNode current_node = (LatticeNode)vector.elementAt(j); //��LatticeNode�Ѿ���Ϊ���϶���
                    
                    //�ں��Ľ��� �������������ڵ�
                    Intent new_intent = current_node.concept.getIntent().intersection(concept.getIntent());
                    
                    if(new_intent.size() == current_node.concept.getIntent().size())
                    {//������ȣ���ν���Ӽ���ϵ,ɨ�赽�Ľڵ��intent������conetp.intent���Ӽ�
                        current_node.concept.getExtent().addAll(concept.getExtent());
                        temp[i].add(current_node); //��i��CIn�м����modified node
                        if(current_node.concept.getIntent().equals(concept.getIntent()))
                        {
                            getLattice().setTop(getLattice().findTop());
                            return;
                        }
                    } else
                    if(getLattice().isAGenerator(new_intent, temp))
                    {//�ں�ѡ��temp�в����ڵĻ� IF CIn�в�����Ckʹ��Itemset(Ck)=Y
                    	
                    	//by cjj 2005-4-8  ������slit
                        Concept new_concept = new Concept(current_node.concept.getExtent().union(concept.getExtent()), new_intent,new SetSlit());
                        
                        //������ڵ�
                        LatticeNode new_node = new LatticeNode(new_concept);
                        
                        //�Ѹýڵ���뵽��Ӧ�Ĳ���ȥ
                        getLattice().addNodeToIntentLevelIndex(new_node);
                        
                        //�Ѹ�new node ���뵽��Ӧ��CIn����ȥ
                        temp[new_intent.size()].add(new_node);
                        
                        //�޸ıߵĹ�ϵ
                        getLattice().modifyEdges(current_node, new_node, temp);
                        
                        //��ڵ�ı��+1
                        getLattice().inc_nbr_concept();
                        
                        
                        //�²����ľ������Լ����������һ������ڣ���concept��intent����ͬ��Ϊ�Ѵ��ڣ������������������(�ų�)
                        
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

    public void doAlgorithm()
    {
        long time = System.currentTimeMillis();
        int nbObjets = br.getObjectsNumber();
        //��ν�ĳ�ʼ�����ɴ�treilis�б仯�ˣ���Ϊ��
        //��ΪgetLattice�Ǹ����е�public����
       
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
            doGodin(o, br.getF(o));
           
            //�ͽ���������ʾ�й�
            sendJobPercentage((i * 100) / nbObjets);
        }
        System.out.println(getLattice().getnbr_concept());
        System.out.println("FIN " + (System.currentTimeMillis() - time) + " mSec.");
    }
    
    public void doGodin(FormalObject objet, Intent intent)
    {
    	Extent extent = new SetExtent();
        extent.add(objet);
        Concept concept = new Concept(extent, intent,new SetSlit());
        addConcept(concept);
    }
    
    public String getDescription()
    {
        return "Godin incremental Lattice Algorithm";
    }
}
