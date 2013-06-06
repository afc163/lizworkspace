// Decompiled by Jad v1.5.7g. Copyright 2000 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/SiliconValley/Bridge/8617/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi 
// Source File Name:   NouReynaud.java

package lattice.algorithm;

import java.io.PrintStream;
import java.util.Set;
import java.util.Vector;
import lattice.gui.tooltask.AbstractJob;
import lattice.util.*;

// Referenced classes of package lattice.algorithm:
//            LatticeAlgorithm

public class NouReynaud extends LatticeAlgorithm
{
    private class ExtentIndex
    {

        private Vector ei[];

        protected void ajouterNoeud(LatticeNodeNR n)
        {
            ei[n.getConcept().getExtent().size()].add(n);
        }

        protected LatticeNodeNR trouverNoeudCandidat(Extent e)
        {
            for(int i = 0; i < ei[e.size()].size(); i++)
            {
                LatticeNodeNR lnr = (LatticeNodeNR)ei[e.size()].elementAt(i);
                if(lnr.getConcept().getExtent().equals(e))
                    return lnr;
            }

            return null;
        }

        protected int size()
        {
            return ei.length;
        }

        protected Vector getAt(int i)
        {
            return ei[i];
        }

        protected void effacerNoeud(LatticeNodeNR n)
        {
            ei[n.getConcept().getExtent().size()].remove(n);
        }

        protected ExtentIndex()
        {
            ei = new Vector[br.getObjectsNumber() + 1];
            for(int i = 0; i < ei.length; i++)
                ei[i] = new Vector();

        }
    }


    private BinaryRelation br;
    private FormalObject objets[];
    private FormalAttribute attributs[];
    private ConceptLattice treillis;
    private Vector li1;
    private ExtentIndex extentIndex;
    private Extent concAttributs[];

    public NouReynaud(BinaryRelation br)
    {
        super(br);
        treillis = getLattice();
        li1 = treillis.get_intent_level_index();
        this.br = br;
    }

    public String getDescription()
    {
        return "Nourine & Reynaud Lattice Algorithm";
    }

    public void doAlgorithm()
    {
        long time = System.currentTimeMillis();
        extentIndex = new ExtentIndex();
        objets = br.getFormalObjects();
        for(int i = 0; i < objets.length; i++)
        {
            FormalObject o = objets[i];
            doGodin(o, br.getF(o));
            sendJobPercentage((i * 50) / objets.length);
        }

        System.out.println("FIN Nourrine & Reynaud  - " + (System.currentTimeMillis() - time) + " mSec.");
        doNRalgo();
        System.out.println("FIN " + (System.currentTimeMillis() - time) + " mSec.");
    }

    public void doNRalgo()
    {
        attributs = br.getFormalAttributes();
        concAttributs = new Extent[attributs.length];
        for(int i = 0; i < attributs.length; i++)
            concAttributs[i] = br.getG(attributs[i]);

        long total = 0L;
        for(int i = 0; i < extentIndex.size(); i++)
        {
            for(int j = 0; j < extentIndex.getAt(i).size(); j++)
            {
                Node noeud = (LatticeNodeNR)extentIndex.getAt(i).elementAt(j);
                Vector listNoeudsCandidats = new Vector();
                for(int k = 0; k < attributs.length; k++)
                    if(!noeud.getConcept().getIntent().contains(attributs[k]))
                    {
                        Extent e = concAttributs[k].intersection(noeud.getConcept().getExtent());
                        long tmp = System.currentTimeMillis();
                        LatticeNodeNR candidat = extentIndex.trouverNoeudCandidat(e);
                        total += System.currentTimeMillis() - tmp;
                        candidat.setCompteur(candidat.getCompteur() + 1);
                        if(candidat.getCompteur() == 1)
                            listNoeudsCandidats.add(candidat);
                        if(candidat.getCompteur() + noeud.getConcept().getIntent().size() == candidat.getConcept().getIntent().size())
                            treillis.linkNodeToUpperCover(noeud, candidat);
                    }

                for(int l = 0; l < listNoeudsCandidats.size(); l++)
                    ((LatticeNodeNR)listNoeudsCandidats.elementAt(l)).setCompteur(0);

            }

            sendJobPercentage(50 + (i * 50) / extentIndex.size());
        }

        System.out.println("TOTAL CANDIDATS - " + total + " mSec.");
    }

    private void doGodin(FormalObject objet, Intent intent)
    {
        Extent extent = new SetExtent();
        extent.add(objet);
        Concept concept = new Concept(extent, intent);
        if(treillis.getBottom() == null)
        {
            LatticeNodeNR noeud = new LatticeNodeNR(concept);
            extentIndex.ajouterNoeud(noeud);
            treillis.setBottom(noeud);
            treillis.setTop(noeud);
            treillis.initialiseIntentLevelIndex(intent.size() + 1);
            treillis.set_max_transaction_size(intent.size());
            treillis.addNodeToIntentLevelIndex(noeud);
            treillis.inc_nbr_concept();
        } else
        {
            LatticeNodeNR bottom = (LatticeNodeNR)treillis.getBottom();
            if(!bottom.getConcept().getIntent().containsAll(concept.getIntent()))
                if(bottom.getConcept().getExtent().isEmpty())
                {
                    bottom.getConcept().getIntent().addAll(concept.getIntent());
                    ((LinkedConceptLattice)treillis).adjustMaxIntentLevelIndex(concept);
                } else
                {
                    bottom = new LatticeNodeNR(new Concept(new SetExtent(), bottom.getConcept().getIntent().union(concept.getIntent())));
                    treillis.setBottom(bottom);
                    treillis.getBottom().setVisited(true);
                    li1.add(new Vector());
                    ((Vector)li1.lastElement()).add(treillis.getBottom());
                    treillis.inc_nbr_concept();
                    extentIndex.ajouterNoeud(bottom);
                }
            Vector li2[] = new Vector[intent.size() + 1];
            for(int i = 0; i < li2.length; i++)
                li2[i] = new Vector();

            for(int i = 0; i < li1.size(); i++)
            {
                for(int m = 0; m < ((Vector)li1.elementAt(i)).size(); m++)
                {
                    LatticeNodeNR nd = (LatticeNodeNR)((Vector)li1.elementAt(i)).elementAt(m);
                    if(intent.containsAll(nd.getConcept().getIntent()))
                    {
                        extentIndex.effacerNoeud(nd);
                        nd.getConcept().getExtent().add(objet);
                        li2[i].add(nd);
                        extentIndex.ajouterNoeud(nd);
                        if(nd.getConcept().getIntent().equals(intent))
                        {
                            treillis.setTop(treillis.findTop());
                            return;
                        }
                    } else
                    {
                        Intent itn = nd.getConcept().getIntent().intersection(intent);
                        if(treillis.isAGenerator(itn, li2))
                        {
                            LatticeNodeNR newNoeud = new LatticeNodeNR(new Concept(nd.getConcept().getExtent().union(extent), itn));
                            newNoeud.getConcept().getExtent().add(objet);
                            extentIndex.ajouterNoeud(newNoeud);
                            treillis.addNodeToIntentLevelIndex(newNoeud);
                            treillis.inc_nbr_concept();
                            li2[itn.size()].add(newNoeud);
                            if(itn.equals(intent))
                            {
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

    private boolean trouver(Vector v, Extent e)
    {
        return v.contains(e);
    }

}
