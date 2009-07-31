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
    	//getBottom在LinkedConceptLattice中实现，返回node类型
        if(getLattice().getBottom() == null)
        {  //如果还没有节点
        	//2005-4-8 修改过得,带有slit的初始化
            initFirst(concept);  
        } else
        { // Bottom底层存在格节点
            getLattice().adjustBottom(concept);
            int max_intent_card = getLattice().get_intent_level_index().size();
            
            ///相当于CIn 同时CIn的长度不会超过新进concept的intent的长度
            Vector temp[] = new Vector[concept.getIntent().size() + 1]; 
            
            //全部初始化为空
            getLattice().initialiseArray(temp);
            for(int i = 0; i < max_intent_card; i++)
            { //扫描层
            	//获得该层上的concept node 个数,存放在nbr_element
                Vector vector = (Vector)getLattice().get_intent_level_index().elementAt(i);
                int nbr_element = vector.size();
                
                //扫描一层上面的每个节点
                for(int j = 0; j < nbr_element; j++)
                { 
                    LatticeNode current_node = (LatticeNode)vector.elementAt(j); //把LatticeNode已经作为集合对象
                    
                    //内涵的交集 －－－－新增节点
                    Intent new_intent = current_node.concept.getIntent().intersection(concept.getIntent());
                    
                    if(new_intent.size() == current_node.concept.getIntent().size())
                    {//长度相等，意谓着子集关系,扫描到的节点的intent是新入conetp.intent的子集
                        current_node.concept.getExtent().addAll(concept.getExtent());
                        temp[i].add(current_node); //第i层CIn中加入该modified node
                        if(current_node.concept.getIntent().equals(concept.getIntent()))
                        {
                            getLattice().setTop(getLattice().findTop());
                            return;
                        }
                    } else
                    if(getLattice().isAGenerator(new_intent, temp))
                    {//在候选的temp中不存在的话 IF CIn中不存在Ck使得Itemset(Ck)=Y
                    	
                    	//by cjj 2005-4-8  加入了slit
                        Concept new_concept = new Concept(current_node.concept.getExtent().union(concept.getExtent()), new_intent,new SetSlit());
                        
                        //新增格节点
                        LatticeNode new_node = new LatticeNode(new_concept);
                        
                        //把该节点加入到相应的层上去
                        getLattice().addNodeToIntentLevelIndex(new_node);
                        
                        //把改new node 加入到相应的CIn层上去
                        temp[new_intent.size()].add(new_node);
                        
                        //修改边的关系
                        getLattice().modifyEdges(current_node, new_node, temp);
                        
                        //格节点的编号+1
                        getLattice().inc_nbr_concept();
                        
                        
                        //新产生的就是它自己。这种情况一般出现在：和concept的intent势相同即为已存在，或者它被包含的情况(排除)
                        
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
        //所谓的初始化。由此treilis中变化了，不为空
        //因为getLattice是父类中的public方法
       
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
           
            //和进度条得显示有关
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
