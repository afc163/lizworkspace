// Decompiled by Jad v1.5.7g. Copyright 2000 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/SiliconValley/Bridge/8617/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi 
// Source File Name:   ShgControler.java

package lattice.gui.controler;

import java.awt.Container;
import java.awt.event.ActionEvent;
import java.io.PrintStream;
import java.util.AbstractList;
import java.util.Collection;
import java.util.EventObject;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Set;
import java.util.StringTokenizer;
import java.util.Vector;
import javax.swing.AbstractButton;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import lattice.algorithm.LatticeAlgorithm;
import lattice.algorithm.task.latticeAlgorithmTask;
import lattice.gui.ConsoleFrame;
import lattice.gui.RelationalContextEditor;
import lattice.gui.dialog.Gagci_NoInc_Param;
import lattice.gui.relation.AbstractRelationTableEditor;
import lattice.gui.tooltask.AbstractTask;
import lattice.shg.algorithm.ARES;
import lattice.shg.algorithm.ARESConstruction;
import lattice.shg.algorithm.ARESDual;
import lattice.shg.algorithm.CERES;
import lattice.shg.algorithm.Gagci_NoInc;
import lattice.util.AbstractRelation;
import lattice.util.BadInputDataException;
import lattice.util.BinaryRelation;
import lattice.util.Concept;
import lattice.util.ConceptLattice;
import lattice.util.DefaultFormalAttribute;
import lattice.util.DefaultFormalObject;
import lattice.util.FormalAttribute;
import lattice.util.FormalObject;
import lattice.util.InterObjectBinaryRelation;
import lattice.util.LatticeGraph;
import lattice.util.LatticeNode;
import lattice.util.MultiValuedRelation;
import lattice.util.RelationalContext;
import lattice.util.ScalingBinaryRelation;
import lattice.util.SetExtent;
import lattice.util.SetIntent;

// Referenced classes of package lattice.gui.controler:
//            AbstractControler

public class ShgControler extends AbstractControler
{

    private JMenu menuShg;
    private JMenuItem algoCeres;
    private JMenuItem algoAresCons;
    private JMenuItem algoGodinShg;
    private JMenu menuOp;
    private JMenuItem algoAres;
    private JMenuItem algoAresDual;
    private JMenu menuIcg;
    private JMenuItem createRelTableGagci;
    private JMenuItem algoGagci;
    private JMenuItem umlApplication;
    private JMenuItem algoGagciNoInc;
    private latticeAlgorithmTask theTask;

    public ShgControler(RelationalContextEditor rce)
    {
        super(rce);
        menuShg = null;
        algoCeres = null;
        algoAresCons = null;
        algoGodinShg = null;
        algoAres = null;
        algoAresDual = null;
        createRelTableGagci = null;
        algoGagci = null;
        umlApplication = null;
        algoGagciNoInc = null;
        theTask = null;
        initMenu();
        theTask = new latticeAlgorithmTask(rce);
    }

    public void initMenu()
    {
        menuShg = new JMenu("Galois Sub-Hierarchy");
        menuShg.setMnemonic('G');
        algoCeres = new JMenuItem("CERES");
        algoCeres.setMnemonic('C');
        algoCeres.addActionListener(this);
        menuShg.add(algoCeres);
        algoAresCons = new JMenuItem("ARES Iterative Construction");
        algoAresCons.setMnemonic('A');
        algoAresCons.addActionListener(this);
        menuShg.add(algoAresCons);
        algoGodinShg = new JMenuItem("Godin SHG");
        algoGodinShg.setMnemonic('G');
        algoGodinShg.addActionListener(this);
        menuShg.add(algoGodinShg);
        menuOp = new JMenu("Maintaining SHG");
        menuOp.setMnemonic('M');
        algoAres = new JMenuItem("ARES: Add a Formal Object");
        algoAres.setMnemonic('O');
        algoAres.addActionListener(this);
        menuOp.add(algoAres);
        algoAresDual = new JMenuItem("ARES dual: Add a Formal Attribute");
        algoAresDual.setMnemonic('A');
        algoAresDual.addActionListener(this);
        menuOp.add(algoAresDual);
        menuShg.add(menuOp);
        menuIcg = new JMenu("Iterative Cross Generalization");
        menuIcg.setMnemonic('I');
        createRelTableGagci = new JMenuItem("Create a relation table");
        createRelTableGagci.setMnemonic('C');
        createRelTableGagci.addActionListener(this);
        menuIcg.add(createRelTableGagci);
        menuIcg.addSeparator();
        algoGagci = new JMenuItem("GAGCI");
        algoGagci.setMnemonic('G');
        algoGagci.addActionListener(this);
        menuIcg.add(algoGagci);
        umlApplication = new JMenuItem("UML application: batch ");
        umlApplication.setMnemonic('U');
        umlApplication.addActionListener(this);
        menuIcg.add(umlApplication);
        menuShg.add(menuIcg);
    }

    public JMenu getMainMenu()
    {
        return menuShg;
    }

    public void actionPerformed(ActionEvent arg0)
    {
        if(arg0.getSource() == algoCeres)
            execAlgorithm(new CERES((BinaryRelation)rce.getSelectedRelation()));
        if(arg0.getSource() == algoAresCons)
            execAlgorithm(new ARESConstruction((BinaryRelation)rce.getSelectedRelation()));
        if(arg0.getSource() == algoGodinShg)
            rce.addMessage("This Algorithm is not yet imlemented !\n");
        if(arg0.getSource() == algoAres)
            execAlgoARES((BinaryRelation)rce.getSelectedRelation());
        if(arg0.getSource() == algoAresDual)
            execAlgoARESDual((BinaryRelation)rce.getSelectedRelation());
        if(arg0.getSource() == createRelTableGagci)
            rce.createInterObjectBinaryRelationTable();
        if(arg0.getSource() == algoGagci)
            execAlgoGagciNoInc();
        if(arg0.getSource() == umlApplication)
            execGagciUmlApplication();
        rce.checkPossibleActions();
    }

    protected void execAlgorithm(LatticeAlgorithm algo)
    {
        rce.setWorkOnRelation(algo.getBinaryRelation());
        Vector binRelOnUse = new Vector();
        if(algo instanceof Gagci_NoInc)
        {
            binRelOnUse.addAll(((Gagci_NoInc)algo).getSetOfEnrichingRelations());
            for(int i = 0; i < binRelOnUse.size(); i++)
                rce.setWorkOnRelation((AbstractRelation)binRelOnUse.elementAt(i));

        } else
        {
            binRelOnUse.add(algo.getBinaryRelation());
        }
        theTask.setBinRelOnUse(binRelOnUse);
        theTask.setAlgo(algo);
        theTask.exec();
    }

    protected void execAlgoARES(BinaryRelation binRel)
    {
        FormalObject A = new DefaultFormalObject("A");
        boolean objetValide = false;
        FormalObject objetsInit[] = binRel.getFormalObjects();
        int kobj = 0;
        do
        {
            String nomA = JOptionPane.showInputDialog("Give a name to the class you want to add");
            if(nomA != null && !nomA.trim().equals(""))
            {
                A = new DefaultFormalObject(nomA.trim());
                kobj = 0;
                for(objetValide = true; kobj < objetsInit.length && objetValide; kobj++)
                    objetValide = !objetsInit[kobj].equals(A);

            }
            if(!objetValide)
                JOptionPane.showMessageDialog(null, "This name is not valid", "Error", 0);
        } while(!objetValide);
        Vector fA = new Vector();
        String val = JOptionPane.showInputDialog("Give a set of formal attributes");
        if(val != null && !val.trim().equals(""))
        {
            for(StringTokenizer st = new StringTokenizer(val, ",;"); st.hasMoreTokens(); fA.addElement(new DefaultFormalAttribute(st.nextToken().trim())));
        }
        binRel.addObject();
        FormalObject objetsFinaux[] = binRel.getFormalObjects();
        boolean boolObj = false;
        int kObjI = 0;
        int kObjF = 0;
        FormalObject leNouvObj = new DefaultFormalObject("bidon");
        for(; !boolObj && kObjF < objetsFinaux.length; kObjF++)
        {
            for(boolObj = false; !boolObj && kObjI < objetsInit.length; kObjI++)
                boolObj = objetsInit[kObjI].equals(objetsFinaux[kObjF]);

            if(!boolObj)
            {
                leNouvObj = objetsFinaux[kObjF];
                boolObj = true;
            } else
            {
                boolObj = false;
            }
        }

        try
        {
            binRel.replaceObject(leNouvObj, A);
        }
        catch(BadInputDataException bide)
        {
            System.out.println(bide.toString());
        }
        for(Iterator itAtt = fA.iterator(); itAtt.hasNext();)
        {
            FormalAttribute attInit[] = binRel.getFormalAttributes();
            FormalAttribute attCourant = (FormalAttribute)itAtt.next();
            boolean trouve = false;
            for(int i = 0; i < attInit.length && !trouve; i++)
                trouve = attInit[i].equals(attCourant);

            if(!trouve)
            {
                binRel.addAttribute();
                FormalAttribute attFinaux[] = binRel.getFormalAttributes();
                boolean boolAtt = false;
                int kAttI = 0;
                int kAttF = 0;
                FormalAttribute leNouvAtt = new DefaultFormalAttribute("bidon");
                for(; !boolAtt && kAttF < attFinaux.length; kAttF++)
                {
                    for(boolAtt = false; !boolAtt && kAttI < attInit.length; kAttI++)
                        boolAtt = attInit[kAttI].equals(attFinaux[kAttF]);

                    if(!boolAtt)
                    {
                        leNouvAtt = attFinaux[kAttF];
                        boolAtt = true;
                    } else
                    {
                        boolAtt = false;
                    }
                }

                try
                {
                    binRel.replaceAttribute(leNouvAtt, attCourant);
                }
                catch(BadInputDataException bide)
                {
                    System.out.println(bide.toString());
                }
            }
        }

        for(Iterator itAtt = fA.iterator(); itAtt.hasNext(); binRel.revertRelation(A, (FormalAttribute)itAtt.next()));
        rce.getSelectedTableEditor().setModelFromRelation(binRel);
        rce.validate();
        LatticeAlgorithm algo = new ARES(binRel, A, fA);
        execAlgorithm(algo);
    }

    protected void execAlgoARESDual(BinaryRelation binRel)
    {
        FormalAttribute A = new DefaultFormalAttribute("bidon");
        Vector gA = new Vector();
        for(boolean boolValide = false; !boolValide;)
        {
            String val = JOptionPane.showInputDialog("Give a formal attribute to add");
            if(val != null && !val.trim().equals(""))
            {
                StringTokenizer st = new StringTokenizer(val, ",;");
                A = new DefaultFormalAttribute(st.nextToken().trim());
                boolValide = true;
            }
        }

        for(boolean boolValide = false; !boolValide;)
        {
            String val = JOptionPane.showInputDialog("Give a set of formal objects");
            if(val != null && !val.trim().equals(""))
            {
                for(StringTokenizer st = new StringTokenizer(val, ",;"); st.hasMoreTokens(); gA.addElement(new DefaultFormalObject(st.nextToken().trim())));
                boolValide = true;
            }
        }

        FormalAttribute attInit[] = binRel.getFormalAttributes();
        boolean existe = false;
        int i;
        for(i = 0; !existe && i < attInit.length; i++)
            existe = attInit[i].equals(A);

        if(existe)
        {
            i--;
            gA.addAll(binRel.getG(attInit[i]));
            binRel.removeAttribute(attInit[i]);
        }
        binRel.getLattice().setBottom(null);
        ((LatticeGraph)binRel.getLattice()).findNSetBottom();
        if(binRel.getLattice().getBottom() == null)
        {
            Vector feuilles = new Vector();
            for(Iterator itBot = ((LatticeGraph)binRel.getLattice()).getAllNodes().iterator(); itBot.hasNext();)
            {
                LatticeNode noeudTemp = (LatticeNode)itBot.next();
                if(noeudTemp.getChildren().size() == 0)
                    feuilles.addElement(noeudTemp);
            }

            if(feuilles.size() != 1)
            {
                Concept conBot = new Concept(new SetExtent(), new SetIntent());
                LatticeNode noeudBot = new LatticeNode(conBot);
                for(Iterator itBot = feuilles.iterator(); itBot.hasNext(); ((LatticeNode)itBot.next()).addChild(noeudBot));
                noeudBot.getParents().addAll(feuilles);
                ((LatticeGraph)binRel.getLattice()).addNode(noeudBot);
                ((LatticeGraph)binRel.getLattice()).setBottom(noeudBot);
            }
        }
        binRel.addAttribute();
        FormalAttribute attFinaux[] = binRel.getFormalAttributes();
        boolean boolAtt = false;
        int kAttI = 0;
        int kAttF = 0;
        FormalAttribute leNouvAtt = new DefaultFormalAttribute("bidon");
        for(; !boolAtt && kAttF < attFinaux.length; kAttF++)
        {
            for(boolAtt = false; !boolAtt && kAttI < attInit.length; kAttI++)
                boolAtt = attInit[kAttI].equals(attFinaux[kAttF]);

            if(!boolAtt)
            {
                leNouvAtt = attFinaux[kAttF];
                boolAtt = true;
            } else
            {
                boolAtt = false;
            }
        }

        try
        {
            binRel.replaceAttribute(leNouvAtt, A);
        }
        catch(BadInputDataException bide)
        {
            System.out.println(bide.toString());
        }
        for(Iterator itObj = gA.iterator(); itObj.hasNext();)
        {
            FormalObject objInit[] = binRel.getFormalObjects();
            FormalObject objCourant = (FormalObject)itObj.next();
            boolean trouve = false;
            for(int m = 0; m < objInit.length && !trouve; m++)
                trouve = objInit[m].equals(objCourant);

            if(!trouve)
            {
                binRel.addObject();
                FormalObject objFinaux[] = binRel.getFormalObjects();
                boolean boolObj = false;
                int kObjI = 0;
                int kObjF = 0;
                FormalObject leNouvObj = new DefaultFormalObject("bidon");
                for(; !boolObj && kObjF < objFinaux.length; kObjF++)
                {
                    for(boolObj = false; !boolObj && kObjI < objInit.length; kObjI++)
                        boolObj = objInit[kObjI].equals(objFinaux[kObjF]);

                    if(!boolObj)
                    {
                        leNouvObj = objFinaux[kObjF];
                        boolObj = true;
                    } else
                    {
                        boolObj = false;
                    }
                }

                try
                {
                    binRel.replaceObject(leNouvObj, objCourant);
                }
                catch(BadInputDataException bide)
                {
                    System.out.println(bide.toString());
                }
            }
        }

        for(Iterator itObj = gA.iterator(); itObj.hasNext(); binRel.revertRelation((FormalObject)itObj.next(), A));
        rce.getSelectedTableEditor().setModelFromRelation(binRel);
        rce.validate();
        LatticeAlgorithm algo = new ARESDual(binRel, A, gA);
        execAlgorithm(algo);
    }

    protected void execAlgoGagciNoInc()
    {
        if(Gagci_NoInc_Param.askParameter(rce.getFamilyContexts(), rce))
        {
            LatticeAlgorithm algo = new Gagci_NoInc((BinaryRelation)Gagci_NoInc_Param.getSetOfKi().elementAt(0), Gagci_NoInc_Param.getSetOfKi(), Gagci_NoInc_Param.getSetOfKiRelation(), Gagci_NoInc_Param.getLesGraphInit(), null);
            execAlgorithm(algo);
        }
    }

    protected void execGagciUmlApplication()
    {
        BinaryRelation mainKi = null;
        Hashtable mainKiRel = new Hashtable();
        Vector setOfKi = new Vector();
        Vector setOfRel = new Vector();
        Hashtable setOfInitGraph = new Hashtable();
        for(int i = 0; i < rce.getFamilyContexts().getNumberOfRelation(); i++)
        {
            if((rce.getFamilyContexts().getRelation(i) instanceof BinaryRelation) && rce.getFamilyContexts().getRelation(i).getRelationName().equals("Class"))
            {
                mainKi = (BinaryRelation)rce.getFamilyContexts().getRelation(i);
                for(int j = 0; j < rce.getFamilyContexts().getNumberOfRelation(); j++)
                    if(rce.getFamilyContexts().getRelation(j) instanceof InterObjectBinaryRelation)
                    {
                        if(rce.getFamilyContexts().getRelation(j).getRelationName().equals("isType_at") || rce.getFamilyContexts().getRelation(j).getRelationName().equals("isTypeDecl_at") || rce.getFamilyContexts().getRelation(j).getRelationName().equals("Own_at"))
                        {
                            Vector v = (Vector)mainKiRel.get("Attribute");
                            if(v == null)
                                v = new Vector();
                            v.addElement(rce.getFamilyContexts().getRelation(j));
                            mainKiRel.put("Attribute", v);
                        }
                        if(rce.getFamilyContexts().getRelation(j).getRelationName().equals("isTypeRet_op") || rce.getFamilyContexts().getRelation(j).getRelationName().equals("isTypeDecl_op") || rce.getFamilyContexts().getRelation(j).getRelationName().equals("Own_op") || rce.getFamilyContexts().getRelation(j).getRelationName().startsWith("isTypeParam"))
                        {
                            Vector v = (Vector)mainKiRel.get("Operation");
                            if(v == null)
                                v = new Vector();
                            v.addElement(rce.getFamilyContexts().getRelation(j));
                            mainKiRel.put("Operation", v);
                        }
                        if(rce.getFamilyContexts().getRelation(j).getRelationName().equals("isTypeOrig_as") || rce.getFamilyContexts().getRelation(j).getRelationName().equals("isTypeDest_as") || rce.getFamilyContexts().getRelation(j).getRelationName().equals("isTypeAssoc_as") || rce.getFamilyContexts().getRelation(j).getRelationName().equals("OwnDest_as") || rce.getFamilyContexts().getRelation(j).getRelationName().equals("OwnOrig_as"))
                        {
                            Vector v = (Vector)mainKiRel.get("Association");
                            if(v == null)
                                v = new Vector();
                            v.addElement(rce.getFamilyContexts().getRelation(j));
                            mainKiRel.put("Association", v);
                        }
                    }

            }
            if((rce.getFamilyContexts().getRelation(i) instanceof BinaryRelation) && rce.getFamilyContexts().getRelation(i).getRelationName().equals("Attribute"))
            {
                setOfKi.addElement(rce.getFamilyContexts().getRelation(i));
                Hashtable lesRels = new Hashtable();
                setOfRel.addElement(lesRels);
                for(int j = 0; j < rce.getFamilyContexts().getNumberOfRelation(); j++)
                    if((rce.getFamilyContexts().getRelation(j) instanceof InterObjectBinaryRelation) && (rce.getFamilyContexts().getRelation(j).getRelationName().equals("hasType_at") || rce.getFamilyContexts().getRelation(j).getRelationName().equals("hasTypeDecl_at")))
                    {
                        Vector v = (Vector)lesRels.get("Class");
                        if(v == null)
                            v = new Vector();
                        v.addElement(rce.getFamilyContexts().getRelation(j));
                        lesRels.put("Class", v);
                    }

            }
            if((rce.getFamilyContexts().getRelation(i) instanceof BinaryRelation) && rce.getFamilyContexts().getRelation(i).getRelationName().equals("Operation"))
            {
                setOfKi.addElement(rce.getFamilyContexts().getRelation(i));
                Hashtable lesRels = new Hashtable();
                setOfRel.addElement(lesRels);
                for(int j = 0; j < rce.getFamilyContexts().getNumberOfRelation(); j++)
                    if((rce.getFamilyContexts().getRelation(j) instanceof InterObjectBinaryRelation) && (rce.getFamilyContexts().getRelation(j).getRelationName().equals("hasTypeRet_op") || rce.getFamilyContexts().getRelation(j).getRelationName().equals("hasTypeDecl_op") || rce.getFamilyContexts().getRelation(j).getRelationName().startsWith("hasTypeParam")))
                    {
                        Vector v = (Vector)lesRels.get("Class");
                        if(v == null)
                            v = new Vector();
                        v.addElement(rce.getFamilyContexts().getRelation(j));
                        lesRels.put("Class", v);
                    }

            }
            if((rce.getFamilyContexts().getRelation(i) instanceof BinaryRelation) && rce.getFamilyContexts().getRelation(i).getRelationName().equals("Association"))
            {
                setOfKi.addElement(rce.getFamilyContexts().getRelation(i));
                Hashtable lesRels = new Hashtable();
                setOfRel.addElement(lesRels);
                for(int j = 0; j < rce.getFamilyContexts().getNumberOfRelation(); j++)
                    if((rce.getFamilyContexts().getRelation(j) instanceof InterObjectBinaryRelation) && (rce.getFamilyContexts().getRelation(j).getRelationName().equals("hasTypeOrig_as") || rce.getFamilyContexts().getRelation(j).getRelationName().equals("hasTypeDest_as") || rce.getFamilyContexts().getRelation(j).getRelationName().equals("hasTypeAssoc_as")))
                    {
                        Vector v = (Vector)lesRels.get("Class");
                        if(v == null)
                            v = new Vector();
                        v.addElement(rce.getFamilyContexts().getRelation(j));
                        lesRels.put("Class", v);
                    }

            }
            if((rce.getFamilyContexts().getRelation(i) instanceof BinaryRelation) && rce.getFamilyContexts().getRelation(i).getRelationName().equals("Class_Inheritance"))
            {
                CERES anAlgo = new CERES((BinaryRelation)rce.getFamilyContexts().getRelation(i));
                anAlgo.doAlgorithm();
                setOfInitGraph.put("Class", rce.getFamilyContexts().getRelation(i).getLattice());
            }
        }

        setOfKi.addElement(mainKi);
        setOfRel.addElement(mainKiRel);
        LatticeAlgorithm algo = new Gagci_NoInc(mainKi, setOfKi, setOfRel, setOfInitGraph, null);
        execAlgorithm(algo);
    }

    public void checkPossibleActions()
    {
        if(rce.getFamilyContexts().getNumberOfRelation() == 0)
        {
            menuShg.setEnabled(false);
            return;
        }
        AbstractRelation absRel = rce.getSelectedRelation();
        if(absRel instanceof MultiValuedRelation)
            menuShg.setEnabled(false);
        if(absRel instanceof BinaryRelation)
            if(rce.isOnWork(absRel))
            {
                menuShg.setEnabled(false);
            } else
            {
                menuShg.setEnabled(true);
                if(absRel.getLattice() != null && (absRel.getLattice() instanceof LatticeGraph))
                {
                    algoAres.setEnabled(true);
                    algoAresDual.setEnabled(true);
                } else
                {
                    algoAres.setEnabled(false);
                    algoAresDual.setEnabled(false);
                }
            }
        if(absRel instanceof ScalingBinaryRelation)
            menuShg.setEnabled(false);
        if(absRel instanceof InterObjectBinaryRelation)
            menuShg.setEnabled(false);
    }
}
