// Decompiled by Jad v1.5.7g. Copyright 2000 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/SiliconValley/Bridge/8617/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi 
// Source File Name:   AbstractARES.java

package lattice.algorithm;

import java.io.PrintStream;
import java.util.*;
import lattice.gui.tooltask.AbstractJob;
import lattice.util.*;

// Referenced classes of package lattice.algorithm:
//            LatticeAlgorithm

public abstract class AbstractARES extends LatticeAlgorithm
{

    protected FormalObject A;
    protected Vector fA;

    public AbstractARES()
    {
    }

    public AbstractARES(BinaryRelation bRel)
    {
        super(bRel);
        lcl = binRel.getLattice();
    }

    public AbstractARES(BinaryRelation bRel, FormalObject objectToAdd, Vector setOfAttributes)
    {
        super(bRel);
        lcl = binRel.getLattice();
        A = objectToAdd;
        fA = new Vector(setOfAttributes);
    }

    protected void algoPrincipal()
    {
        Vector vides = new Vector();
        Vector superConceptsA = new Vector();
        boolean ADefini = false;
        Concept conceptCA = new Concept(new SetExtent(), new SetIntent());
        LatticeNode noeudCA = new LatticeNode(conceptCA);
        Vector propreA = (Vector)fA.clone();
        Vector extL = genereExtension((LatticeGraph)lcl);
        boolean fin = false;
        int ex = 0;
        int tailleExtL = extL.size();
        for(; ex < extL.size() && !fin; sendJobPercentage((ex * 100) / tailleExtL))
        {
            LatticeNode noeudC = (LatticeNode)extL.elementAt(ex);
            Concept conceptC = noeudC.getConcept();
            Vector cp = CP(conceptC, fA);
            if(!cp.isEmpty())
            {
                Vector ep = EP(conceptC, fA);
                Vector rc = RC(conceptC, fA);
                Vector ra = RA(conceptC, fA);
                if(rc.isEmpty() && ra.isEmpty())
                {
                    aresCas1(conceptC, A);
                    fin = true;
                    ADefini = true;
                }
                if(rc.isEmpty() && !ra.isEmpty())
                    aresCas2(noeudC, A, superConceptsA, propreA);
                if(!rc.isEmpty() && ra.isEmpty())
                {
                    if(!ADefini)
                    {
                        ADefini = true;
                        ((LatticeGraph)lcl).addNode(noeudCA);
                        conceptCA.getExtent().add(A);
                        conceptCA.getIntent().addAll(fA);
                        conceptCA.getSimplifyExtent().add(A);
                        conceptCA.getSimplifyIntent().addAll(propreA);
                        if(!ep.isEmpty())
                        {
                            conceptC.getSimplifyIntent().removeAll(propreA);
                            if(conceptC.getSimplifyIntent().isEmpty() && conceptC.getSimplifyExtent().isEmpty())
                                vides.addElement(noeudC);
                        }
                        Vector minSuperConceptsA = minNoeuds(superConceptsA);
                        LatticeNode n;
                        for(Iterator it = minSuperConceptsA.iterator(); it.hasNext(); n.removeChild(noeudC))
                        {
                            n = (LatticeNode)it.next();
                            noeudCA.addParent(n);
                            n.addChild(noeudCA);
                        }

                    }
                    noeudCA.addChild(noeudC);
                    conceptCA.getExtent().addAll(conceptC.getExtent());
                    noeudC.addParent(noeudCA);
                    for(Iterator it = noeudCA.getParents().iterator(); it.hasNext(); noeudC.removeParent((LatticeNode)it.next()));
                    elimineFilsExtLin(noeudC, extL);
                }
                if(!rc.isEmpty() && !ra.isEmpty() && !ep.isEmpty())
                {
                    SetIntent intC2 = new SetIntent();
                    intC2.addAll(cp);
                    SetExtent extC2 = (SetExtent)conceptC.getExtent().clone();
                    extC2.add(A);
                    Concept conceptC2 = new Concept(extC2, intC2);
                    conceptC2.getSimplifyIntent().addAll(ep);
                    LatticeNode noeudC2 = new LatticeNode(conceptC2);
                    ((LatticeGraph)lcl).addNode(noeudC2);
                    noeudC2.addChild(noeudC);
                    Vector minInter = minNoeuds(interNoeuds(superNoeuds(noeudC), superConceptsA));
                    LatticeNode n;
                    for(Iterator it = minInter.iterator(); it.hasNext(); n.removeChild(noeudC))
                    {
                        n = (LatticeNode)it.next();
                        noeudC2.addParent(n);
                        n.addChild(noeudC2);
                    }

                    superConceptsA.add(noeudC2);
                    noeudC.addParent(noeudC2);
                    noeudC.getParents().removeAll(noeudC2.getParents());
                    conceptC.getSimplifyIntent().removeAll(conceptC2.getSimplifyIntent());
                    if(conceptC.getSimplifyIntent().isEmpty() && conceptC.getSimplifyExtent().isEmpty())
                        vides.addElement(noeudC);
                    propreA.removeAll(conceptC2.getSimplifyIntent());
                }
            }
            ex++;
        }

        if(!ADefini)
        {
            ((LatticeGraph)lcl).addNode(noeudCA);
            conceptCA.getSimplifyExtent().add(A);
            conceptCA.getExtent().add(A);
            conceptCA.getSimplifyIntent().addAll(propreA);
            conceptCA.getIntent().addAll(fA);
            noeudCA.getParents().addAll(minNoeuds(superConceptsA));
            LatticeNode unNoeud;
            for(Iterator it = minNoeuds(superConceptsA).iterator(); it.hasNext(); unNoeud.addChild(noeudCA))
                unNoeud = (LatticeNode)it.next();

        }
        supprimerVides(vides);
        MAJTop();
        if(((LatticeGraph)lcl).getBottom() != null)
            ((LatticeGraph)lcl).findNSetBottom();
    }

    protected Vector CP(Concept C, Vector fA)
    {
        Vector res = new Vector(fA);
        res.retainAll(C.getIntent());
        return res;
    }

    protected Vector EP(Concept C, Vector fA)
    {
        Vector res = new Vector(fA);
        res.retainAll(C.getSimplifyIntent());
        return res;
    }

    protected Vector RC(Concept C, Vector fA)
    {
        Vector res = new Vector(C.getIntent());
        res.removeAll(CP(C, fA));
        return res;
    }

    protected Vector RA(Concept C, Vector fA)
    {
        Vector res = new Vector(fA);
        res.removeAll(CP(C, fA));
        return res;
    }

    protected Vector genereExtension(LatticeGraph lcl)
    {
        Vector lesNoeuds = new Vector();
        Stack pile = new Stack();
        LatticeNode courant;
        for(Iterator it = lcl.getAllNodes().iterator(); it.hasNext(); courant.setVisited(false))
        {
            courant = (LatticeNode)it.next();
            courant.setDegre(courant.getParents().size());
        }

        pile.push(lcl.getTop());
        while(!pile.empty()) 
        {
            LatticeNode courant = (LatticeNode)pile.peek();
            if(!courant.getVisited())
            {
                lesNoeuds.addElement(courant);
                LatticeNode fils;
                for(Iterator it = courant.getChildren().iterator(); it.hasNext(); fils.setDegre(fils.getDegre() - 1))
                    fils = (LatticeNode)it.next();

                courant.setVisited(true);
            }
            boolean trouve = false;
            for(Iterator it = courant.getChildren().iterator(); it.hasNext() && !trouve;)
            {
                LatticeNode fils = (LatticeNode)it.next();
                trouve = fils.getDegre() == 0 && !fils.getVisited();
                if(trouve)
                    pile.push(fils);
            }

            if(!trouve)
                pile.pop();
        }
        return lesNoeuds;
    }

    protected Vector minNoeuds(Vector v)
    {
        Vector res = new Vector();
        for(Iterator itn = v.iterator(); itn.hasNext();)
        {
            LatticeNode n = (LatticeNode)itn.next();
            boolean trouve = false;
            for(Iterator itf = n.getChildren().iterator(); itf.hasNext() && !trouve; trouve = v.contains(itf.next()));
            if(!trouve)
                res.add(n);
        }

        return res;
    }

    protected Vector superNoeuds(LatticeNode n)
    {
        Vector res = new Vector();
        String aff = "\nsuperNoeuds(" + n.getId() + "):";
        for(Iterator it = n.getParents().iterator(); it.hasNext();)
        {
            LatticeNode p = (LatticeNode)it.next();
            res.addElement(p);
            aff = aff + " " + p.getId();
            Vector temp = superNoeuds(p);
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

    protected void elimineFilsExtLin(Node n, Vector extL)
    {
        LatticeNode f;
        for(Iterator it = n.getChildren().iterator(); it.hasNext(); elimineFilsExtLin(((Node) (f)), extL))
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
        System.out.println(s);
        s = "exts ";
        for(Iterator it = c.getSimplifyExtent().iterator(); it.hasNext();)
            s = s + ((FormalObject)it.next()).toString() + " ";

        System.out.println(s);
        s = "ints ";
        for(Iterator it = c.getSimplifyIntent().iterator(); it.hasNext();)
            s = s + ((FormalAttribute)it.next()).toString() + " ";

        System.out.println(s);
        s = "pred ";
        for(Iterator it = n.getParents().iterator(); it.hasNext();)
            s = s + ((LatticeNode)it.next()).getId() + " ";

        System.out.println(s);
        s = "succ ";
        for(Iterator it = n.getChildren().iterator(); it.hasNext();)
            s = s + ((LatticeNode)it.next()).getId() + " ";

        System.out.println(s);
    }

    protected void aresCas1(Concept C, FormalObject A)
    {
        C.getExtent().add(A);
        C.getSimplifyExtent().add(A);
    }

    protected void aresCas2(LatticeNode C, FormalObject A, Vector superConceptsA, Vector propreA)
    {
        superConceptsA.addElement(C);
        C.getConcept().getExtent().add(A);
        propreA.removeAll(C.getConcept().getSimplifyIntent());
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

    public void setA(FormalObject A)
    {
        this.A = A;
    }

    public void setfA(Vector fA)
    {
        this.fA = (Vector)fA.clone();
    }

    protected void MAJTop()
    {
        ((LatticeGraph)lcl).findNSetTop();
        if(((LatticeGraph)lcl).getTop() == null)
        {
            Vector lesRacines = new Vector();
            Vector lesNoeuds = new Vector(((LatticeGraph)lcl).getAllNodes());
            for(int i = 0; i < lesNoeuds.size(); i++)
                if(((Node)lesNoeuds.elementAt(i)).getParents().size() == 0)
                    lesRacines.addElement((Node)lesNoeuds.elementAt(i));

            LatticeNode topBidon = (LatticeNode)lcl.getTop();
            boolean bidonTrouve = false;
            for(int i = 0; i < lesRacines.size() && !bidonTrouve; i++)
            {
                Node uneRacine = (Node)lesRacines.elementAt(i);
                if(uneRacine.getConcept().getIntent().isEmpty())
                {
                    topBidon = (LatticeNode)uneRacine;
                    bidonTrouve = true;
                }
            }

            if(!bidonTrouve)
            {
                topBidon = new LatticeNode(new Concept(new SetExtent(), new SetIntent()));
                ((LatticeGraph)lcl).addNode(topBidon);
            }
            for(int i = 0; i < lesRacines.size(); i++)
            {
                Node uneRacine = (Node)lesRacines.elementAt(i);
                if(uneRacine.getId().intValue() != topBidon.getId().intValue())
                {
                    topBidon.addChild(uneRacine);
                    uneRacine.addParent(topBidon);
                }
                topBidon.getConcept().getExtent().addAll(uneRacine.getConcept().getExtent());
            }

            Vector lesFils = new Vector(topBidon.getChildren());
            for(int i = 0; i < lesFils.size(); i++)
            {
                LatticeNode unFils = (LatticeNode)lesFils.elementAt(i);
                if(unFils.getParents().size() > 1)
                {
                    unFils.removeParent(topBidon);
                    topBidon.removeChild(unFils);
                }
            }

            ((LatticeGraph)lcl).setTop(topBidon);
        }
        if(lcl.getTop().getConcept().getIntent().isEmpty())
        {
            Vector lesFils = new Vector(lcl.getTop().getChildren());
            for(int i = 0; i < lesFils.size(); i++)
            {
                LatticeNode unFils = (LatticeNode)lesFils.elementAt(i);
                lcl.getTop().getConcept().getExtent().addAll(unFils.getConcept().getExtent());
            }

        }
    }
}
