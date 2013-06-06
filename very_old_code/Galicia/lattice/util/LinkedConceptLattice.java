// Decompiled by Jad v1.5.7g. Copyright 2000 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/SiliconValley/Bridge/8617/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi 
// Source File Name:   LinkedConceptLattice.java

package lattice.util;

import java.util.*;

// Referenced classes of package lattice.util:
//            AbstractConceptLattice, ConceptLattice, LevelIndex, Concept, 
//            Node, LatticeNode, SetExtent, SetIntent, 
//            StaticIntegerBank, ConceptLatticeIterator, Intent

//by cjj 2005-4-8 
public class LinkedConceptLattice extends AbstractConceptLattice
    implements ConceptLattice
{
	
    public Node top;
    public Node bottom;
    public Map item_index;
    public LevelIndex intent_level_index;
    protected int max_transaction_size;
    
    protected int nbr_concept;
    
//  by cjj 2005-4-14
    public int getnbr_concept()
    {
    	return nbr_concept;
    }

    public LinkedConceptLattice()
    {
        top = null;
        bottom = null;
        intent_level_index = new LevelIndex();
        item_index = new Hashtable();
        max_transaction_size = 0;
        set_nbr_concept(0);
    }

    public int get_nbr_concept()
    {
        return nbr_concept;
    }

    public void set_nbr_concept(int n)
    {
        nbr_concept = n;
    }

    public void inc_nbr_concept()
    {
        nbr_concept++;
    }

    public int get_max_transaction_size()
    {
        return max_transaction_size;
    }

    public void set_max_transaction_size(int m)
    {
        max_transaction_size = m;
    }

    public Map get_item_index()
    {
        return item_index;
    }

    public void setBottom(Node b)
    {
        bottom = b;
    }

    public Node getBottom()
    {
        return bottom;
    }

    public boolean isEmpty()
    {
        return nbr_concept == 0;
    }

    public Vector get_intent_level_index()
    {
        return intent_level_index.get_intent_level_index();
    }

    public boolean isAMofiedNode(Concept concept, Node current_node)
    {
        boolean isModifiedNode = concept.getIntent().containsAll(current_node.getConcept().getIntent());
        return isModifiedNode;
    }

    public void initialiseBottom(int nbr_items)
    {
        bottom = new LatticeNode(new Concept(new SetExtent(), new SetIntent()));
        top = bottom;
        for(int i = 0; i < nbr_items; i++)
            bottom.getConcept().getIntent().add(StaticIntegerBank.getInteger(i));

        addNodeToIntentLevelIndex(bottom);
        bottom.setVisited(true);
    }

    public int size()
    {
        return nbr_concept;
    }

    public Node getTop()
    {
        return top;
    }

    public void setTop(Node n)
    {
        top = n;
    }

    public Iterator iterator()
    {
        return new ConceptLatticeIterator(get_intent_level_index());
    }

    public Iterator parents(Node node)
    {
        return ((LatticeNode)node).parents.iterator();
    }

    public Iterator children(Node node)
    {
        return ((LatticeNode)node).children.iterator();
    }

    public void adjustMaxIntentLevelIndex(Concept concept)
    {
    	//by cjj 2005-4-13 >��Ϊ>= �������
       /* if(concept.getIntent().size() >= max_transaction_size)
        {
            intent_level_index.lastElement().removeAllElements();
         
            //initialiseIntentLevelIndex((concept.getIntent().size() - max_transaction_size) + 1);
            
            //by cjj 2005-4-12
            initialiseIntentLevelIndex(concept.getIntent().size()-max_transaction_size +1);
            intent_level_index.lastElement().add(bottom);
            max_transaction_size = concept.getIntent().size();
        } */
    	intent_level_index.lastElement().removeAllElements();
    	//if(concept.getIntent().size()>1)
    	Intent ntn=new SetIntent();
    	ntn=(Intent)concept.getIntent().clone();
    	ntn.removeAll(bottom.getConcept().getIntent().intersection(concept.getIntent()));
    		initialiseIntentLevelIndex(ntn.size());
    	intent_level_index.lastElement().add(bottom);
    }

    public void initialiseVector(Vector vector, int size)
    {
        for(int i = 0; i < size; i++)
            vector.add(new Vector());

    }

    public void updateIndexWithNewIntents(Intent new_intents)
    {
        for(Iterator it = new_intents.iterator(); it.hasNext(); item_index.put(it.next(), bottom));
    }

    public void adjustBottom(Concept concept)
    {
        if(!bottom.getConcept().getIntent().containsAll(concept.getIntent()))
        {//�ж�bottom�е�concept�Ƿ����concept.intent,��û��
            Intent new_intents = (Intent)concept.getIntent().clone();
            //�൱������������ new_intents����bottom.getConcept().getIntent
            new_intents.removeAll(bottom.getConcept().getIntent()); 
            if(bottom.getConcept().getExtent().isEmpty())
            {//�ж�Extent���Ƿ�Ϊ��,����,���ϵ�addAll�������൱�ڲ�����
                bottom.getConcept().getIntent().addAll(concept.getIntent());
                
                for(int i=0;i<concept.getIntent().size();i++)
                	intent_level_index.add(new Vector());
                	
                	//  intent_level_index.lastElement().add(bottom);
                addBottomToIntentLevelIndex(bottom);    //�˴���Ҫע��
                
                //by cjj 2005-4-12  �˴��Ĵ������ڸ���bottom�ڵ�����Ӧ��slit
                bottom.getConcept().getSlit().clear();
                for(Iterator iter_parrent=bottom.getParents().iterator();iter_parrent.hasNext();)
                {
                	LatticeNode parrentnode=(LatticeNode)iter_parrent.next();
                	add_chd(bottom,parrentnode);
                }
                
                adjustMaxIntentLevelIndex(concept);
            } else
            {
            	//by cjj 2005-4-8 ����slitΪnull
                //LatticeNode new_temp_node=new LatticeNode(concept);
            	LatticeNode node = new LatticeNode(new Concept(new SetExtent(), bottom.getConcept().getIntent().union(concept.getIntent()),new SetSlit()));
                
                node.parents.add(bottom);  
               
                add_chd(node,bottom);
                bottom.getChildren().add(node);  //�ײ�ڵ�����һ�����ӽڵ�
                //add_chd(node,new_temp_node);
				bottom = node;
                bottom.setVisited(true);
                intent_level_index.add(new Vector());
                intent_level_index.lastElement().add(bottom);
                addBottomToIntentLevelIndex(bottom);    //�˴���Ҫע��
                nbr_concept++;
            }
            updateIndexWithNewIntents(new_intents);
        }
        //by cjj 2005-4-13
        //adjustMaxIntentLevelIndex(concept);
    }

    public void initialiseIntentLevelIndex(int size)
    {
        intent_level_index.initialiseIntentLevelIndex(size);
    }

    public void addNodeToIntentLevelIndex(Node node)
    {
        intent_level_index.addNodeToIntentLevelIndex(node);
    }

    public void addBottomToIntentLevelIndex(Node node)
    {
        intent_level_index.addBottomToIntentLevelIndex(node);
    }

    public void removeNodeFromIntentLevelIndex(Node node)
    {
        intent_level_index.removeNodeFromIntentLevelIndex(node);
    }

    public void removeBottomFromIntentLevelIndex(Node node)
    {
        intent_level_index.removeBottomFromIntentLevelIndex(node);
    }

    public boolean isAGenerator(Intent new_intent, Vector temp[])
    {
        int cardinality = new_intent.size();
        int nbr_element = temp[cardinality].size();
        for(int i = 0; i < nbr_element; i++)
            if(new_intent.equals(((LatticeNode)temp[cardinality].elementAt(i)).concept.getIntent()))
                return false;

        return true;
    }

    //���㷨�к���Ҫ�������add_chd() ��del_chd()
    public void modifyEdges(Node current_node, Node new_node, Vector temp[])
    {
    	//���Ը���С��Ϊ˫��
        current_node.getParents().add(new_node);
        new_node.getChildren().add(current_node);
        
        //�����˲���slit�ĺ���
        //add_chd(current_node,new_node);
        //ÿ����һ�αߵ��޸Ĳ���,��Ҫִ��һ��add_chd()����del_chd
        
        
        for(int k = 0; k < new_node.getConcept().getIntent().size(); k++)
        {
            int nbr_elem = temp[k].size();
            for(int l = 0; l < nbr_elem; l++)
            {
                LatticeNode parent_node = (LatticeNode)temp[k].elementAt(l);
                if(new_node.getConcept().getIntent().containsAll(parent_node.concept.getIntent()))
                { //IF Itemset(Ca) ����Y
                    boolean parent = true;
                    for(Iterator iter_children = parent_node.children.iterator(); iter_children.hasNext();)
                    {
                        LatticeNode child = (LatticeNode)iter_children.next();
                        if(new_node.getConcept().getIntent().containsAll(child.concept.getIntent()))
                        {
                            parent = false;
                            break;
                        }
                    }

                    if(parent)
                    {
                    	//set����������ʹ��contains����
                        if(current_node.getParents().contains(parent_node))
                        {
                            current_node.getParents().remove(parent_node);
                            parent_node.children.remove(current_node);
                            //by cjj 2005-4-8 ����ɾ��ɨ��
                            del_chd(parent_node,current_node);
                        }
                        
                        parent_node.getChildren().add(new_node);
                        new_node.getParents().add(parent_node);
                       
                        //by cjj 2005-4-8
                        add_chd(new_node,parent_node);
                    }
                }
            }

        }
        
        add_chd(current_node,new_node);
    }
    
    public void modifyEdgesAD(Node current_node, Node new_node, Vector temp[])
    {
    	//���Ը���С��Ϊ˫��
        current_node.getParents().add(new_node);
        new_node.getChildren().add(current_node);
        
        //�����˲���slit�ĺ���
        //add_chd(current_node,new_node);
        //ÿ����һ�αߵ��޸Ĳ���,��Ҫִ��һ��add_chd()����del_chd
        
        
        for(int k = 0; k < new_node.getConcept().getIntent().size(); k++)
        {
            int nbr_elem = temp[k].size();
            for(int l = 0; l < nbr_elem; l++)
            {
                LatticeNode parent_node = (LatticeNode)temp[k].elementAt(l);
                if(new_node.getConcept().getIntent().containsAll(parent_node.concept.getIntent()))
                { //IF Itemset(Ca) ����Y
                    boolean parent = true;
                    for(Iterator iter_children = parent_node.children.iterator(); iter_children.hasNext();)
                    {
                        LatticeNode child = (LatticeNode)iter_children.next();
                        if(new_node.getConcept().getIntent().containsAll(child.concept.getIntent()))
                        {
                            parent = false;
                            break;
                        }
                    }

                    if(parent)
                    {
                    	//set����������ʹ��contains����
                        if(current_node.getParents().contains(parent_node))
                        {
                            current_node.getParents().remove(parent_node);
                            parent_node.children.remove(current_node);
                          
                        }
                        
                        parent_node.getChildren().add(new_node);
                        new_node.getParents().add(parent_node);
                       
                     
                    }
                }
            }

        }
        

    }

    public Node findTop()
    {
        for(int i = 0; i < intent_level_index.size(); i++)
            if(!intent_level_index.isEmpty(i))
                return intent_level_index.first(i);

        return null;
    }

    public Node findBottom()
    {
        return (LatticeNode)intent_level_index.lastElement().firstElement();
    }

    public void initialiseArray(Vector temp[])
    {
        for(int i = 0; i < temp.length; i++)
            temp[i] = new Vector(8);    //�˴��ĸı������øı�

    }

    public Vector[] candidates(Node current_node, Node psi[])
    {
        Vector candidates[] = new Vector[current_node.getConcept().getIntent().size()];
        initialiseArray(candidates);
        for(Iterator i = current_node.getParents().iterator(); i.hasNext();)
        {
            Node candidate = psi[((LatticeNode)i.next()).id.intValue()];
            if(!candidates[candidate.getConcept().getIntent().size()].contains(candidate))
                candidates[candidate.getConcept().getIntent().size()].add(candidate);
        }

        return candidates;
    }

    public void linkNodeToUpperCover(Node node, Node enfant)
    {
        node.getChildren().add(enfant);
        enfant.getParents().add(node);
    }
    
    //by cjj 2005.1.10
    //���ҵ�ǰ�ڵ��SLIT
/*    public void findSLIT(Node node)
    {
    	//����ֵ����
    	node.getSLIT().clear();
    	//ɨ��ÿ��˫�׽ڵ�
    	for(Iterator iter_parrent=node.getParents().iterator();iter_parrent.hasNext();)
    	{
    		LatticeNode parrent=(LatticeNode)iter_parrent.next();
    		
    		Intent diff=node.getConcept().getIntent();
    		diff.remove(parrent.getConcept().getIntent());
    		
    		Vector slit_new=new Vector();
    		Vector slit_keep=new Vector();
    		
    		for(int i=0;i<node.getSLIT().size();i++)
    		{
    			//Vectorû�н�������
    			if(node.getSLIT().elementAt(i).i)
    		}
    		
    	}
    	
    }  */
    
    //by cjj 2005-4-8
    public void del_chd(Node Ipt,Node Icd)
    { //IcdΪ�ӽڵ�  IptΪ���׽ڵ�
    	Slit SLITn=new SetSlit();  //�²������   
    	Intent Diff=(Intent)Icd.getConcept().getIntent().clone();
    	Diff.removeAll(Ipt.getConcept().getIntent());  //�
    	Slit SLIT0=(Slit)Icd.getConcept().getSlit().clone();
    	for(Iterator iter_intent=Icd.getConcept().getSlit().iterator();iter_intent.hasNext();)
    	{ //����SLIT�е�ÿ��Item
    		Intent d=(Intent)((Intent)iter_intent.next()).clone();
    		
    		if(Diff.size()==1)
    		{
    			//Slit s=new SetSlit();
    			Intent temp_d=(Intent)d.clone();
    			temp_d.removeAll(Diff);
    			//s.add(d);
    			//SLITn=SLITn.union(s);
    			SLITn.add(temp_d);
    			
    		}
    		else
    		{//|Diff|<>1
    			
    			Intent Temp=d.intersection(Diff);
    			Intent Genitem=(Intent)d.clone();
    			SLITn=(Slit)SLIT0.clone();
    			
    			if(Temp.size()==1)
    			{
    				boolean Flg=true;
    				for(Iterator iter_parent=Icd.getParents().iterator();iter_parent.hasNext();)
    				{
    					Intent Chd=(Intent)((LatticeNode)(iter_parent.next())).getConcept().getIntent().clone();
    					Intent temp_Ipt=(Intent)Icd.getConcept().getIntent().clone();
    					temp_Ipt.removeAll(Chd);
    					if(temp_Ipt.containsAll(Temp))
    					{
    						Flg=false;
    						break;
    					}
    				}
    				if(Flg)
    				{
    					Intent temp_d=(Intent)d.clone();
    					temp_d.removeAll(Temp);
    					Genitem=(Intent)temp_d.clone();
    				}
    				
    			}
    			
    			//SLITn=SLITn\Item
    			//Slit s=new SetSlit();
    			//s.add(d);
    			//SLITn=SLITn.difference(s);
    			
    			//by cjj 2005-4-9
    			SLITn.remove(d);
    			
    			if(!SLITn.isEmpty())
    			{
    				boolean Flg=true;
    				for(Iterator iter_it=SLITn.iterator();iter_it.hasNext();)
    				{
    					Intent Init=(Intent)iter_it.next();
    					if(Genitem.containsAll(Init))
    					{
    						Flg=false;
    						break;
    					}
    				}
    				if(Flg)
    				{
    				//	Slit ss=new SetSlit();
    				//	ss.add(Genitem);
    				//	SLITn=SLITn.union(ss);
    					SLITn.add(Genitem);
    				}
    			}
    			
    		}//end if
    		SLIT0=(Slit)SLITn.clone();
    		
    	}
    	Icd.getConcept().setSlit(SLITn);
    }
    
    public void add_chd(Node Icd,Node Ipt)
    { //��������ʱ,new_node=>Ipt   parent_node=>Icd
    	Slit SLITn=new SetSlit();  //�²������   
    	Slit SLITk=new SetSlit();  //���ֲ������С�
    	//��ΪIcd��Ipt��,������ҪIcd-Ipt
    	Intent Diff=(Intent)Icd.getConcept().getIntent().clone();
    	Diff.removeAll(Ipt.getConcept().getIntent());
    	
    	if(!Icd.getConcept().getSlit().isEmpty())
    	{
    		for(Iterator iter_slit=Icd.getConcept().getSlit().iterator();iter_slit.hasNext();)
    		{//ɨ��Slit�е�ÿ���item,���ڰ��������Intent��
    			
    			
    			//------------------------------------����������!!!!!!!!!!!!!!!!!!!!!!!!!
    			
    		//	Intent Item=new SetIntent();
    		//	Item.add(iter_slit.next());
    			Intent Item=(Intent)iter_slit.next();
    			Intent Temp=new SetIntent();
    			
    			
    			
    			
    			Temp=Item.intersection(Diff);
    			if(!Temp.isEmpty())
    			{
    				//Slit s=new SetSlit();  //��ʱ���
    				//s.add(Item);
    				//SLITk=SLITk.union(s);
    				SLITk.add(Item);
    			}
    		}
    	}
    	
    	Slit Diff_SLIT=Icd.getConcept().getSlit().difference(SLITk);
    	
    	if(Diff_SLIT.isEmpty())
    	{
    		for(Iterator iter_intent=Diff.iterator();iter_intent.hasNext();)
    		{
    			//Intent Genitem=new SetIntent();
    			//Genitem.add(iter_intent.next());
    			Object Genitem=iter_intent.next();
    			Intent temp_Genitem=new SetIntent();
				temp_Genitem.add(Genitem);
    			if(!SLITk.isEmpty())
    			{
    				boolean Flg=true;
    				for(Iterator iter_it=SLITk.iterator();iter_it.hasNext();)
    				{
    					
    					if(temp_Genitem.contains(iter_it.next()))
    					{
    						Flg=false;
    						break;
    					}
    				}
    				if(Flg)
    				{
    					//SLITn=SLITn.union(Genitem);
    					SLITn.add(temp_Genitem);
    				}
    			}
    			else
    			{
    				SLITn.add(temp_Genitem);
    			}
    			
    		}
    	}
    	else
    	{
    		
    		for(Iterator iter_slit=Diff_SLIT.iterator();iter_slit.hasNext();)
        	{  //Diff_SLIT����Intent
    			
    			//Object oslit=iter_slit.next();
    			Intent temp_Item=(Intent)iter_slit.next();
        		for(Iterator iter_intent=Diff.iterator();iter_intent.hasNext();)
        		{ //Diff����Object
        			//Slit s=new SetSlit();
        			//s.add((Object)iter_intent.next());
        			//Slit Genitem=((Slit)iter_slit.next()).union(s);
        			Intent Item1=new SetIntent();
        			Item1=(Intent)temp_Item.clone();
        			//Intent Item1=(Intent)((Intent)iter_slit.next()).clone();
        			//Intent Item1=new SetIntent();
        			//Item1.add(oslit);
        			Object Element=(Object)iter_intent.next();
        			Item1.add(Element);
        			Intent Genitem=(Intent)Item1.clone();
        			if(!SLITk.isEmpty())
        			{
        				boolean Flg=true;
        				for(Iterator iter_it=SLITk.iterator();iter_it.hasNext();)
        				{
        					Intent Init=(Intent)iter_it.next();
        					
        					if(Genitem.containsAll(Init))
        					{
        						Flg=false;
        						break;
        					}
        				}
        				if(Flg)
        				{
        					//SLITn=SLITn.union(Genitem);
        					SLITn.add(Genitem);
        				}
        			}
        			else
        			{
        				SLITn.add(Genitem);
        			}
        		}
        	}
    	}
    	
    	SLITn=SLITn.union(SLITk);
    	Icd.getConcept().setSlit(SLITn);
    }
    //---
}
