// Decompiled by Jad v1.5.7g. Copyright 2000 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/SiliconValley/Bridge/8617/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi 
// Source File Name:   MenuAffichage.java

package lattice.graph.trees.menu;

import java.awt.*;
import java.awt.event.ActionListener;
import lattice.graph.trees.TreeEditor;
import lattice.graph.trees.event.MenuAffichageAdapter;
import lattice.graph.trees.event.MenuColorAdapter;
import lattice.graph.utils.Ressources;

// Referenced classes of package lattice.graph.trees.menu:
//            MenuItemChange, MenuItemColor

public class MenuAffichage extends Menu
{

    TreeEditor editeur;
    public MenuItemChange flechesRel;
    public MenuItemChange relationForme;
    public MenuItemChange textRelations;
    public MenuItemChange posLien;
    public MenuItemChange affZoomViewer;
    public MenuItemChange affichAttributs;
    public MenuItemChange affInfo;

    public MenuAffichage(TreeEditor editeur, String nom)
    {
        super(nom);
        flechesRel = new MenuItemChange("Afficher fl?ches F7", "Masquer fl?ches F8");
        relationForme = new MenuItemChange("Lignes droites F5", "Lignes bris?es F6");
        textRelations = new MenuItemChange("Afficher texte relations", "Masquer texte relations");
        posLien = new MenuItemChange("Verticale", "Horizontale");
        affZoomViewer = new MenuItemChange("Afficher fen?tre de zoom", "Masquer fen?tre de zoom");
        affichAttributs = new MenuItemChange("Afficher", "Masquer");
        affInfo = new MenuItemChange("Afficher informations", "Masquer informations");
        this.editeur = editeur;
        init();
    }

    protected void init()
    {
        affZoomViewer.addActionListener(new MenuAffichageAdapter(1, editeur));
        add(affZoomViewer);
        add(affInfo);
        affInfo.addActionListener(new MenuAffichageAdapter(21, editeur));
        addSeparator();
        MenuItem format1 = new MenuItem("Arbre simple\t\tF1");
        add(format1);
        format1.addActionListener(new MenuAffichageAdapter(2, editeur));
        MenuItem format2 = new MenuItem("Arbre ?quilibr? horizontal F2");
        add(format2);
        format2.addActionListener(new MenuAffichageAdapter(3, editeur));
        MenuItem format3 = new MenuItem("Arbre ?quilibr? vertical \tF3");
        add(format3);
        format3.addActionListener(new MenuAffichageAdapter(4, editeur));
        MenuItem format4 = new MenuItem("Graphe \tF4");
        add(format4);
        format4.addActionListener(new MenuAffichageAdapter(5, editeur));
        addSeparator();
        MenuItem zoomMoins = new MenuItem("Zoom - \tF9");
        add(zoomMoins);
        zoomMoins.addActionListener(new MenuAffichageAdapter(6, editeur));
        MenuItem zoomPlus = new MenuItem("Zoom + \tF10");
        add(zoomPlus);
        zoomPlus.addActionListener(new MenuAffichageAdapter(7, editeur));
        String polices[] = Ressources.getToolkit().getFontList();
        Menu objets = new Menu("Objets");
        Menu police = createPoliceMenu(new MenuAffichageAdapter(13, editeur));
        objets.add(police);
        Menu style = createStyleMenu(new MenuAffichageAdapter(16, editeur));
        objets.add(style);
        Menu couleur = new Menu("Couleur");
        MenuColorAdapter colorTextAdapter = new MenuColorAdapter(1, editeur);
        Menu couleurText = createColorMenu(colorTextAdapter);
        couleurText.setLabel("Texte");
        couleur.add(couleurText);
        MenuColorAdapter colorFondAdapter = new MenuColorAdapter(2, editeur);
        Menu couleurFond = createColorMenu(colorFondAdapter);
        couleurFond.setLabel("Fond");
        couleur.add(couleurFond);
        objets.add(couleur);
        addSeparator();
        add(objets);
        Menu attributs = new Menu("Attributs");
        attributs.add(affichAttributs);
        affichAttributs.addActionListener(new MenuAffichageAdapter(11, editeur));
        attributs.addSeparator();
        Menu policeAtt = createPoliceMenu(new MenuAffichageAdapter(14, editeur));
        attributs.add(policeAtt);
        Menu styleAtt = createStyleMenu(new MenuAffichageAdapter(17, editeur));
        attributs.add(styleAtt);
        Menu couleurAtt = new Menu("Couleur");
        MenuColorAdapter colorTextAttAdapter = new MenuColorAdapter(3, editeur);
        Menu couleurTextAtt = createColorMenu(colorTextAttAdapter);
        couleurTextAtt.setLabel("Texte");
        couleurAtt.add(couleurTextAtt);
        MenuColorAdapter colorFondAttAdapter = new MenuColorAdapter(4, editeur);
        Menu couleurFondAtt = createColorMenu(colorFondAttAdapter);
        couleurFondAtt.setLabel("Fond");
        couleurAtt.add(couleurFondAtt);
        attributs.add(couleurAtt);
        add(attributs);
        Menu relations = new Menu("Relations");
        relations.add(relationForme);
        relationForme.addActionListener(new MenuAffichageAdapter(8, editeur));
        relations.add(flechesRel);
        flechesRel.addActionListener(new MenuAffichageAdapter(9, editeur));
        relations.add(textRelations);
        textRelations.addActionListener(new MenuAffichageAdapter(12, editeur));
        relations.add(posLien);
        posLien.addActionListener(new MenuAffichageAdapter(10, editeur));
        relations.addSeparator();
        Menu policeRel = createPoliceMenu(new MenuAffichageAdapter(15, editeur));
        relations.add(policeRel);
        Menu styleRel = createStyleMenu(new MenuAffichageAdapter(18, editeur));
        relations.add(styleRel);
        add(relations);
        Menu fond = new Menu("Fond");
        MenuItem motif = new MenuItem("Ouvrir image");
        motif.addActionListener(new MenuAffichageAdapter(19, editeur));
        fond.add(motif);
        Menu alignement = createMenuAlignement(new MenuAffichageAdapter(20, editeur));
        fond.add(alignement);
        add(fond);
    }

    protected Menu createColorMenu(ActionListener mca)
    {
        Menu colorMenu = new Menu("Couleur");
        String labelColorTable[] = {
            "Noir", "Bleu", "Cyan", "Gris", "Vert", "Magenta", "Orange", "Rose", "Blanc", "Jaune"
        };
        Color colorTable[] = {
            Color.black, Color.blue, Color.cyan, Color.gray, Color.green, Color.magenta, Color.orange, Color.pink, Color.white, Color.yellow
        };
        for(int i = 0; i < 10; i++)
        {
            MenuItemColor menuItem = new MenuItemColor(labelColorTable[i], colorTable[i]);
            colorMenu.add(menuItem);
            menuItem.addActionListener(mca);
        }

        return colorMenu;
    }

    protected Menu createPoliceMenu(ActionListener listener)
    {
        String polices[] = Ressources.getToolkit().getFontList();
        Menu police = new Menu("Polices");
        for(int i = 0; i < polices.length; i++)
        {
            MenuItem m = new MenuItem(polices[i]);
            m.addActionListener(listener);
            police.add(m);
        }

        return police;
    }

    protected Menu createMenuAlignement(ActionListener listener)
    {
        String align[] = {
            "Aucun", "Centrer", "Gauche", "Bas", "Motif", "Adapter", "Grille"
        };
        Menu malign = new Menu("Alignement");
        for(int i = 0; i < align.length; i++)
        {
            MenuItem m = new MenuItem(align[i]);
            m.addActionListener(listener);
            malign.add(m);
        }

        return malign;
    }

    protected Menu createStyleMenu(ActionListener listener)
    {
        String style[] = {
            "Normal", "Italique", "Gras", "Gras italique"
        };
        Menu mStyle = new Menu("Styles");
        for(int i = 0; i < style.length; i++)
        {
            MenuItem m = new MenuItem(style[i]);
            m.addActionListener(listener);
            mStyle.add(m);
        }

        return mStyle;
    }
}
