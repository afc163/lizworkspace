// Decompiled by Jad v1.5.7g. Copyright 2000 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/SiliconValley/Bridge/8617/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi 
// Source File Name:   SauvegardeRegles.java

package lattice.rules.util;

import java.io.*;
import java.util.*;

// Referenced classes of package lattice.rules.util:
//            Regle

public class SauvegardeRegles
{

    public SauvegardeRegles()
    {
    }

    public void sauvegardeReglesExactesFichierXML(String dbFileName, Vector ensembleRegles, String nomFichierEntree, double confianceMinimale, double supportMinimal)
    {
        try
        {
            FileWriter out = new FileWriter(dbFileName);
            out.write("<?xml version = '1.0' encoding = 'ISO-8859-1'?>");
            out.write("<!DOCTYPE base_couverture SYSTEM 'regle.dtd'> ");
            out.write("<?xml-stylesheet type='text/xsl' href='regle.xsl'?>");
            out.write("<base_couverture>");
            out.write("<caracteristiques>");
            out.write("<base_donnees>");
            out.write(nomFichierEntree);
            out.write("</base_donnees>");
            out.write("<support_minimal>");
            String supportUsager = Double.toString(supportMinimal);
            out.write(supportUsager);
            out.write("</support_minimal>");
            out.write("<confiance_minimale>");
            String confianceUsager = Double.toString(confianceMinimale);
            out.write(confianceUsager);
            out.write("</confiance_minimale>");
            out.write("</caracteristiques>");
            Iterator it = ensembleRegles.iterator();
            out.write("<ensemble_regles>");
            out.write("<nombre_regles>");
            out.write((new Integer(ensembleRegles.size())).toString());
            out.write("</nombre_regles>");
            for(; it.hasNext(); out.write("</regle>"))
            {
                Regle regleCourante = (Regle)it.next();
                java.util.SortedSet antecedent = regleCourante.getAntecedent();
                Iterator it2 = antecedent.iterator();
                java.util.SortedSet consequence = regleCourante.getConsequence();
                Iterator it3 = consequence.iterator();
                out.write("<regle>");
                out.write("<antecedent>");
                for(; it2.hasNext(); out.write(" "))
                {
                    String itemCourantant = it2.next().toString();
                    out.write(itemCourantant);
                }

                out.write("</antecedent>");
                out.write("<consequence>");
                for(; it3.hasNext(); out.write(" "))
                {
                    String itemCourantcons = it3.next().toString();
                    out.write(itemCourantcons);
                }

                out.write("</consequence>");
                out.write("<support>");
                String s = Double.toString((double)(int)(regleCourante.getSupport() * 100D) / 100D);
                out.write(s);
                out.write("</support>");
                out.write("<confiance>");
                String c = Double.toString((double)(int)(regleCourante.getConfiance() * 100D) / 100D);
                out.write(c);
                out.write("</confiance>");
            }

            out.write("</ensemble_regles>");
            out.write("</base_couverture>");
            out.flush();
            out.close();
        }
        catch(IOException ioe)
        {
            System.out.println("Impossible d'enregistrer " + dbFileName);
            ioe.printStackTrace();
        }
    }

    public void sauvegardeReglesApproximativesFichierXML(String dbFileName, Vector ensembleRegles, String nomFichierEntree, double confianceMinimale, double supportMinimal)
    {
        try
        {
            FileWriter out = new FileWriter(dbFileName);
            out.write("<?xml version = '1.0' encoding = 'ISO-8859-1'?>");
            out.write("<!DOCTYPE base_couverture SYSTEM 'regle.dtd'> ");
            out.write("<?xml-stylesheet type='text/xsl' href='regle2.xsl'?>");
            out.write("<base_couverture>");
            out.write("<caracteristiques>");
            out.write("<base_donnees>");
            out.write(nomFichierEntree);
            out.write("</base_donnees>");
            out.write("<support_minimal>");
            String supportUsager = Double.toString(supportMinimal);
            out.write(supportUsager);
            out.write("</support_minimal>");
            out.write("<confiance_minimale>");
            String confianceUsager = Double.toString(confianceMinimale);
            out.write(confianceUsager);
            out.write("</confiance_minimale>");
            out.write("</caracteristiques>");
            Iterator it = ensembleRegles.iterator();
            out.write("<ensemble_regles>");
            out.write("<nombre_regles>");
            out.write((new Integer(ensembleRegles.size())).toString());
            out.write("</nombre_regles>");
            for(; it.hasNext(); out.write("</regle>"))
            {
                Regle regleCourante = (Regle)it.next();
                java.util.SortedSet antecedent = regleCourante.getAntecedent();
                Iterator it2 = antecedent.iterator();
                java.util.SortedSet consequence = regleCourante.getConsequence();
                Iterator it3 = consequence.iterator();
                out.write("<regle>");
                out.write("<antecedent>");
                for(; it2.hasNext(); out.write(" "))
                {
                    String itemCourantant = it2.next().toString();
                    out.write(itemCourantant);
                }

                out.write("</antecedent>");
                out.write("<consequence>");
                for(; it3.hasNext(); out.write(" "))
                {
                    String itemCourantcons = it3.next().toString();
                    out.write(itemCourantcons);
                }

                out.write("</consequence>");
                out.write("<support>");
                String s = Double.toString((double)(int)(regleCourante.getSupport() * 100D) / 100D);
                out.write(s);
                out.write("</support>");
                out.write("<confiance>");
                String c = Double.toString((double)(int)(regleCourante.getConfiance() * 100D) / 100D);
                out.write(c);
                out.write("</confiance>");
            }

            out.write("</ensemble_regles>");
            out.write("</base_couverture>");
            out.flush();
            out.close();
        }
        catch(IOException ioe)
        {
            System.out.println("Impossible d'enregistrer " + dbFileName);
            ioe.printStackTrace();
        }
    }
}
