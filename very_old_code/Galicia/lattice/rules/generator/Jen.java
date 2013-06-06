// Decompiled by Jad v1.5.7g. Copyright 2000 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/SiliconValley/Bridge/8617/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi 
// Source File Name:   Jen.java

package lattice.rules.generator;

import java.util.*;
import lattice.gui.tooltask.AbstractJob;
import lattice.gui.tooltask.JobObserver;
import lattice.rules.util.Difference;
import lattice.util.*;

public class Jen extends AbstractJob
{

    public int nombreGenerateurs;
    public Difference dif;
    public ConceptLattice instanceTreillis;

    public Jen(ConceptLattice treillis)
    {
        dif = new Difference();
        nombreGenerateurs = 0;
        instanceTreillis = treillis;
    }

    public Vector retourneItemItemset(Intent itemset)
    {
        Iterator it = itemset.iterator();
        Vector resultat = new Vector();
        Intent tempo;
        for(; it.hasNext(); resultat.add(tempo))
        {
            Object courant = it.next();
            tempo = new SetIntent();
            tempo.add(courant);
        }

        return resultat;
    }

    public Intent retournePremierFace(Vector ensembleFace)
    {
        Iterator it = ensembleFace.iterator();
        Intent premiereFace = (Intent)it.next();
        return premiereFace;
    }

    public Vector calculFace(Node fci)
    {
        Vector ensembleFaces = new Vector();
        Set parents = fci.getParents();
        for(Iterator it = parents.iterator(); it.hasNext(); ensembleFaces.add(dif.difference))
        {
            Node parentCourant = (Node)it.next();
            dif.calculDifference(fci, parentCourant);
        }

        return ensembleFaces;
    }

    public Intent intersectionGenerateurFaceVide(Intent generateur, Intent face)
    {
        Intent inter = generateur.intersection(face);
        if(inter.size() == 0)
            return new SetIntent();
        else
            return inter;
    }

    public Intent calculNouveauGenerateur(Intent generateur, Intent face)
    {
        Intent union = generateur.union(face);
        return union;
    }

    public void eliminePremiereFace(Vector ensembleFace)
    {
        ensembleFace.remove(ensembleFace.firstElement());
    }

    public Vector modificationGenerateurs(Intent faceCourante, Vector ensembleGenerateur, Vector resultatBloqueueurMinimaux, Vector resultatBloqueur)
    {
        for(Iterator it = ensembleGenerateur.iterator(); it.hasNext();)
        {
            Intent generateurCourant = (Intent)it.next();
            Intent intersection = intersectionGenerateurFaceVide(generateurCourant, faceCourante);
            if(intersection.size() == 0)
            {
                Vector nouvelEnsembleGenerateur = parcoursItemsFace(faceCourante, generateurCourant);
                ajouteGenerateurs(resultatBloqueur, nouvelEnsembleGenerateur);
            } else
            {
                resultatBloqueueurMinimaux.add(generateurCourant);
            }
        }

        if(resultatBloqueur.size() == 0)
            return resultatBloqueueurMinimaux;
        if(resultatBloqueueurMinimaux.size() == 0)
        {
            return resultatBloqueur;
        } else
        {
            Vector resultat = new Vector();
            estDejaGenerateur(resultatBloqueur, resultat, resultatBloqueueurMinimaux);
            resultatBloqueur.removeAll(resultat);
            ajouteGenerateurs(resultatBloqueueurMinimaux, resultatBloqueur);
            return resultatBloqueueurMinimaux;
        }
    }

    public Vector parcoursItemsFace(Intent faceCourante, Intent generateurCourant)
    {
        Vector resultat = new Vector();
        Intent bloqueur;
        for(Iterator it2 = faceCourante.iterator(); it2.hasNext(); resultat.add(bloqueur))
        {
            Object itemFace = it2.next();
            Intent itemsetFace = new SetIntent();
            itemsetFace.add(itemFace);
            bloqueur = calculNouveauGenerateur(generateurCourant, itemsetFace);
        }

        return resultat;
    }

    public Vector calculGenerateursNoeud(Node fci)
    {
        if(fci.getConcept().getIntent().size() != 0)
        {
            Vector ensembleFaces = calculFace(fci);
            if(ensembleFaces.size() != 0)
            {
                Intent premiereFace = retournePremierFace(ensembleFaces);
                Vector ensembleGenerateur = retourneItemItemset(premiereFace);
                eliminePremiereFace(ensembleFaces);
                Vector temporaire = new Vector();
                if(ensembleFaces.size() != 0)
                {
                    for(Iterator it = ensembleFaces.iterator(); it.hasNext(); fci.getConcept().setGenerator(ensembleGenerateur))
                    {
                        Intent faceCourante = (Intent)it.next();
                        Vector bloqueursMinimaux = new Vector();
                        Vector bloqueurs = new Vector();
                        Vector res = modificationGenerateurs(faceCourante, ensembleGenerateur, bloqueursMinimaux, bloqueurs);
                        ensembleGenerateur = res;
                    }

                } else
                {
                    fci.getConcept().setGenerator(ensembleGenerateur);
                }
            } else
            {
                fci.getConcept().setGenerator(retourneItemItemset(fci.getConcept().getIntent()));
            }
        }
        return fci.getConcept().getGenerator();
    }

    public void calculGenerateurs()
    {
        int nbMaxNode = instanceTreillis.get_nbr_concept();
        int numNodeCurrent = 0;
        Iterator it = instanceTreillis.iterator();
        Vector resultat = new Vector();
        int i = 0;
        for(; it.hasNext(); sendJobPercentage((numNodeCurrent * 100) / nbMaxNode))
        {
            Node noeudCourant = (Node)it.next();
            boolean nb = false;
            resultat = calculGenerateursNoeud(noeudCourant);
            nombreGenerateurs = nombreGenerateurs + resultat.size();
            numNodeCurrent++;
        }

    }

    public void estDejaGenerateur(Vector ensembleGenerateurs, Vector resultat, Vector generateurs)
    {
        for(Iterator it1 = ensembleGenerateurs.iterator(); it1.hasNext();)
        {
            Intent generateurCourant = (Intent)it1.next();
            Iterator it2 = generateurs.iterator();
            for(boolean reponse = false; it2.hasNext() && !reponse;)
            {
                Intent generateur = (Intent)it2.next();
                if(generateurCourant.containsAll(generateur))
                {
                    resultat.add(generateurCourant);
                    reponse = true;
                }
            }

        }

    }

    public void ajouteGenerateurs(Vector vector1, Vector Vector2)
    {
        Intent courant;
        for(Iterator it1 = Vector2.iterator(); it1.hasNext(); vector1.add(courant))
            courant = (Intent)it1.next();

    }

    public String getDescription()
    {
        return "Jen (Generator algo.)";
    }

    public void run()
    {
        calculGenerateurs();
        if(jobObserv != null)
            jobObserv.jobEnd(true);
    }
}
