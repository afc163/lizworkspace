// Decompiled by Jad v1.5.7g. Copyright 2000 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/SiliconValley/Bridge/8617/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi 
// Source File Name:   ARESDual.java

package lattice.shg.algorithm;

import java.util.*;
import lattice.algorithm.LatticeAlgorithm;
import lattice.gui.tooltask.AbstractJob;
import lattice.util.*;

public class ARESDual extends LatticeAlgorithm
{

    protected FormalAttribute A;
    protected Vector gA;

    public ARESDual()
    {
    }

    public ARESDual(BinaryRelation bRel)
    {
        super(bRel);
        lcl = binRel.getLattice();
    }

    public ARESDual(BinaryRelation bRel, FormalAttribute A, Vector gA)
    {
        super(bRel);
        lcl = binRel.getLattice();
        this.A = A;
        this.gA = new Vector(gA);
    }

    public void doAlgorithm()
    {
        algoPrincipal();
        if(lcl.getTop().getChildren().size() == 1 && lcl.getTop().getConcept().getSimplifyExtent().size() == 0 && lcl.getTop().getConcept().getSimplifyIntent().size() == 0)
        {
            Node nouvTop = (Node)lcl.getTop().getChildren().get(0);
            Node ancTop = lcl.getTop();
            nouvTop.removeParent(ancTop);
            ancTop.removeChild(nouvTop);
            lcl.setTop(nouvTop);
        }
        ((LatticeGraph)lcl).setBottom(null);
        ((LatticeGraph)lcl).findNSetBottom();
        ((LatticeGraph)lcl).set_nbr_concept(((LatticeGraph)lcl).getAllNodes().size());
        binRel.setLattice(lcl);
    }

    public String getDescription()
    {
        return "ARESDual Algo.";
    }

    private void algoPrincipal()
    {
        Vector vides = new Vector();
        Vector sousConceptsA = new Vector();
        boolean ADefini = false;
        Concept conceptCA = new Concept(new SetExtent(), new SetIntent());
        LatticeNode noeudCA = new LatticeNode(conceptCA);
        Vector propreA = (Vector)gA.clone();
        Vector extL = genereExtensionDual((LatticeGraph)lcl);
        boolean fin = false;
        int ex = 0;
        int tailleExtL = extL.size();
        for(; ex < extL.size() && !fin; sendJobPercentage((ex * 100) / tailleExtL))
        {
            LatticeNode noeudC = (LatticeNode)extL.elementAt(ex);
            Concept conceptC = noeudC.getConcept();
            Vector cc = CC(conceptC, gA);
            if(!cc.isEmpty())
            {
                Vector ec = EC(conceptC, gA);
                Vector rc = RC(conceptC, gA);
                Vector ra = RA(conceptC, gA);
                if(rc.isEmpty() && ra.isEmpty())
                {
                    aresCas1(conceptC, A);
                    fin = true;
                    ADefini = true;
                }
                if(rc.isEmpty() && !ra.isEmpty())
                    aresCas2(noeudC, A, sousConceptsA, propreA);
                if(!rc.isEmpty() && ra.isEmpty())
                {
                    if(!ADefini)
                    {
                        ADefini = true;
                        ((LatticeGraph)lcl).addNode(noeudCA);
                        conceptCA.getIntent().add(A);
                        conceptCA.getExtent().addAll(gA);
                        conceptCA.getSimplifyIntent().add(A);
                        conceptCA.getSimplifyExtent().addAll(propreA);
                        if(!ec.isEmpty())
                        {
                            conceptC.getSimplifyExtent().removeAll(gA);
                            if(conceptC.getSimplifyIntent().isEmpty() && conceptC.getSimplifyExtent().isEmpty())
                                vides.addElement(noeudC);
                        }
                        Vector maxSousConceptsA = maxNoeuds(sousConceptsA);
                        LatticeNode n;
                        for(Iterator it = maxSousConceptsA.iterator(); it.hasNext(); n.getParents().remove(noeudC))
                        {
                            n = (LatticeNode)it.next();
                            noeudCA.addChild(n);
                            n.addParent(noeudCA);
                        }

                    }
                    noeudCA.addParent(noeudC);
                    conceptCA.getIntent().addAll(conceptC.getIntent());
                    noeudC.addChild(noeudCA);
                    for(Iterator it = noeudCA.getChildren().iterator(); it.hasNext(); noeudC.removeChild((LatticeNode)it.next()));
                    eliminePereExtLin(noeudC, extL);
                    if(noeudCA.getParents().size() == 0)
                        ((LatticeGraph)lcl).setTop(noeudCA);
                }
                if(!rc.isEmpty() && !ra.isEmpty() && !ec.isEmpty())
                {
                    SetExtent extC2 = new SetExtent();
                    extC2.addAll(cc);
                    SetIntent intC2 = (SetIntent)conceptC.getIntent().clone();
                    intC2.add(A);
                    Concept conceptC2 = new Concept(extC2, intC2);
                    conceptC2.getSimplifyExtent().addAll(ec);
                    LatticeNode noeudC2 = new LatticeNode(conceptC2);
                    ((LatticeGraph)lcl).addNode(noeudC2);
                    noeudC2.getParents().add(noeudC);
                    Vector maxInter = maxNoeuds(interNoeuds(sousNoeuds(noeudC), sousConceptsA));
                    LatticeNode n;
                    for(Iterator it = maxInter.iterator(); it.hasNext(); n.getParents().remove(noeudC))
                    {
                        n = (LatticeNode)it.next();
                        noeudC2.addChild(n);
                        n.addParent(noeudC2);
                    }

                    sousConceptsA.add(noeudC2);
                    noeudC.getChildren().add(noeudC2);
                    noeudC.getChildren().removeAll(noeudC2.getChildren());
                    conceptC.getSimplifyExtent().removeAll(conceptC2.getSimplifyExtent());
                    if(conceptC.getSimplifyExtent().isEmpty() && conceptC.getSimplifyIntent().isEmpty())
                        vides.addElement(noeudC);
                    propreA.removeAll(conceptC2.getSimplifyExtent());
                    if(noeudC2.getParents().size() == 0)
                        ((LatticeGraph)lcl).setTop(noeudC2);
                }
            }
            ex++;
        }

        if(!ADefini)
        {
            if(noeudCA.getParents().isEmpty())
            {
                LatticeNode topBidon = new LatticeNode(new Concept(new SetExtent(), new SetIntent()));
                ((LatticeGraph)lcl).addNode(topBidon);
                if(!sousConceptsA.contains(lcl.getTop()))
                {
                    topBidon.addChild(lcl.getTop());
                    lcl.getTop().addParent(topBidon);
                }
                topBidon.addChild(noeudCA);
                noeudCA.addParent(topBidon);
                ((LatticeGraph)lcl).setTop(topBidon);
            }
            ((LatticeGraph)lcl).addNode(noeudCA);
            conceptCA.getSimplifyIntent().add(A);
            conceptCA.getIntent().add(A);
            conceptCA.getSimplifyExtent().addAll(propreA);
            conceptCA.getExtent().addAll(gA);
            noeudCA.getChildren().addAll(maxNoeuds(sousConceptsA));
            for(Iterator it = maxNoeuds(sousConceptsA).iterator(); it.hasNext(); ((LatticeNode)it.next()).getParents().add(noeudCA));
        }
        supprimerVides(vides);
    }

    private Vector CC(Concept C, Vector gA)
    {
        Vector res = new Vector(gA);
        res.retainAll(C.getExtent());
        return res;
    }

    private Vector EC(Concept C, Vector gA)
    {
        Vector res = new Vector(gA);
        res.retainAll(C.getSimplifyExtent());
        return res;
    }

    private Vector RC(Concept C, Vector gA)
    {
        Vector res = new Vector(C.getExtent());
        res.removeAll(CC(C, gA));
        return res;
    }

    private Vector RA(Concept C, Vector gA)
    {
        Vector res = new Vector(gA);
        res.removeAll(CC(C, gA));
        return res;
    }

    protected Vector genereExtensionDual(LatticeGraph lcl)
    {
        Vector lesNoeuds = new Vector();
        Stack pile = new Stack();
        LatticeNode courant;
        for(Iterator it = lcl.getAllNodes().iterator(); it.hasNext(); courant.setVisited(false))
        {
            courant = (LatticeNode)it.next();
            courant.setDegre(courant.getChildren().size());
        }

        pile.push(lcl.getBottom());
        while(!pile.empty()) 
        {
            LatticeNode courant = (LatticeNode)pile.peek();
            if(!courant.getVisited())
            {
                lesNoeuds.addElement(courant);
                LatticeNode pere;
                for(Iterator it = courant.getParents().iterator(); it.hasNext(); pere.setDegre(pere.getDegre() - 1))
                    pere = (LatticeNode)it.next();

                courant.setVisited(true);
            }
            boolean trouve = false;
            for(Iterator it = courant.getParents().iterator(); it.hasNext() && !trouve;)
            {
                LatticeNode pere = (LatticeNode)it.next();
                trouve = pere.getDegre() == 0 && !pere.getVisited();
                if(trouve)
                    pile.push(pere);
            }

            if(!trouve)
                pile.pop();
        }
        if(lcl.getBottom().getConcept().getExtent().size() == 0 && lcl.getBottom().getConcept().getIntent().size() == 0)
        {
            for(Iterator it = lcl.getBottom().getParents().iterator(); it.hasNext(); ((LatticeNode)it.next()).removeChild(lcl.getBottom()));
            lcl.getBottom().getParents().removeAll(lcl.getBottom().getParents());
        }
        return lesNoeuds;
    }

    protected void aresCas1(Concept C, FormalAttribute A)
    {
        C.getIntent().add(A);
        C.getSimplifyIntent().add(A);
    }

    protected void aresCas2(LatticeNode C, FormalAttribute A, Vector sousConceptsA, Vector propreA)
    {
        sousConceptsA.addElement(C);
        C.getConcept().getIntent().add(A);
        propreA.removeAll(C.getConcept().getSimplifyExtent());
    }

    protected Vector maxNoeuds(Vector v)
    {
        Vector res = new Vector();
        for(Iterator itn = v.iterator(); itn.hasNext();)
        {
            LatticeNode n = (LatticeNode)itn.next();
            boolean trouve = false;
            for(Iterator itf = n.getParents().iterator(); itf.hasNext() && !trouve; trouve = v.contains(itf.next()));
            if(!trouve)
                res.add(n);
        }

        return res;
    }

    protected Vector sousNoeuds(LatticeNode n)
    {
        Vector res = new Vector();
        String aff = "\nsousNoeuds(" + n.getId() + "):";
        for(Iterator it = n.getChildren().iterator(); it.hasNext();)
        {
            LatticeNode p = (LatticeNode)it.next();
            res.addElement(p);
            aff = aff + " " + p.getId();
            Vector temp = sousNoeuds(p);
            for(Iterator it2 = temp.iterator(); it2.hasNext();)
            {
                LatticeNode pp = (LatticeNode)it2.next();
                if(!res.contains(pp))
                {
                    res.addElement(pp);
                    aff = aff + " " + pp.getId();
                }
            }

        }

        return res;
    }

    protected Vector interNoeuds(Vector v1, Vector v2)
    {
        Vector res = new Vector();
        String aff = "\ninterNoeuds(" + v1.toString() + "," + v2.toString() + "):";
        for(Iterator it = v1.iterator(); it.hasNext();)
        {
            LatticeNode n = (LatticeNode)it.next();
            if(v2.contains(n))
            {
                res.addElement(n);
                aff = aff + " " + n.getId();
            }
        }

        return res;
    }

    protected void eliminePereExtLin(Node n, Vector extL)
    {
        LatticeNode f;
        for(Iterator it = n.getParents().iterator(); it.hasNext(); eliminePereExtLin(((Node) (f)), extL))
        {
            f = (LatticeNode)it.next();
            if(extL.contains(f))
                extL.removeElementAt(extL.indexOf(f));
        }

    }

    protected void afficheNoeud(LatticeNode n)
    {
        Concept c = n.getConcept();
        String s = "\nAfficheNoeud " + n.getId() + c.toString();
        s = "exts ";
        for(Iterator it = c.getSimplifyExtent().iterator(); it.hasNext();)
            s = s + ((FormalObject)it.next()).toString() + " ";

        s = "ints ";
        for(Iterator it = c.getSimplifyIntent().iterator(); it.hasNext();)
            s = s + ((FormalAttribute)it.next()).toString() + " ";

        s = "pred ";
        for(Iterator it = n.getParents().iterator(); it.hasNext();)
            s = s + ((LatticeNode)it.next()).getId() + " ";

        s = "succ ";
        for(Iterator it = n.getChildren().iterator(); it.hasNext();)
            s = s + ((LatticeNode)it.next()).getId() + " ";

    }

    protected void supprimerVides(Vector v)
    {
        String aff = "\nNoeuds vides :";
        LatticeNode n;
        for(Iterator it = v.iterator(); it.hasNext(); ((LatticeGraph)lcl).getAllNodes().remove(n))
        {
            n = (LatticeNode)it.next();
            aff = aff + " " + n.getId();
            LatticeNode suc;
            for(Iterator itSuc = n.getChildren().iterator(); itSuc.hasNext(); suc.getParents().remove(n))
            {
                suc = (LatticeNode)itSuc.next();
                suc.getParents().addAll(n.getParents());
            }

            LatticeNode pred;
            for(Iterator itPred = n.getParents().iterator(); itPred.hasNext(); pred.getChildren().remove(n))
            {
                pred = (LatticeNode)itPred.next();
                pred.getChildren().addAll(n.getChildren());
            }

        }

    }

    public void setA(FormalAttribute A)
    {
        this.A = A;
    }

    public void setgA(Vector gA)
    {
        this.gA = (Vector)gA.clone();
    }
}
