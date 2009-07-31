// Decompiled by Jad v1.5.7g. Copyright 2000 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/SiliconValley/Bridge/8617/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi 
// Source File Name:   PanelButtonArbre.java

package lattice.graph.utils;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.PrintStream;
import javax.swing.JComponent;
import lattice.graph.imageButton.ImageButton;

// Referenced classes of package lattice.graph.utils:
//            IkbsPanel, InfoListener, Ressources, ArbreAdapter, 
//            EditeurArbreInterface

public class PanelButtonArbre extends IkbsPanel
    implements InfoListener
{

    static final String nomImages[] = {
        "regard.gif", "save.gif", "load.gif", "loadDist.gif", "formSimpl.gif", "formEqui.gif", "graphe.gif", "formVert.gif", "zoom+.gif", "zoom-.gif", 
        "zoom.gif", "attribut.gif", "edition.gif", "frflag.gif", "usgbflag.gif"
    };
    Ressources ressources;
    ImageButton buttonzoom;
    ImageButton buttonAttribut;
    ImageButton buttonEdition;
    ImageButton buttonFrancais;
    ImageButton buttonAnglais;
    EditeurArbreInterface parent;
    ArbreAdapter pba;
    private Label comm;
    InfoListener il;

    public PanelButtonArbre(EditeurArbreInterface parent)
    {
        comm = new Label("");
        this.parent = parent;
        init();
    }

    public PanelButtonArbre(EditeurArbreInterface parent, InfoListener il)
    {
        comm = new Label("");
        this.parent = parent;
        setInfoListener(il);
        init();
    }

    public void setInfoListener(InfoListener il)
    {
        this.il = il;
    }

    protected void initRessources()
    {
        ressources = new Ressources(this);
        ressources.defautJarDirectory = "ikbs/ressources";
        ressources.setAcces(Ressources.FROM_JAR);
        ressources.setLocal(false);
        ressources.wait = true;
        try
        {
            ressources.init(nomImages);
        }
        catch(Exception e)
        {
            System.out.println("Probl?me d'acc?s aux ressources (PanelButonIllu)");
        }
    }

    public void init()
    {
        initRessources();
        initGridBagConstraint();
        c.insets = new Insets(0, 0, 0, 0);
        setBackground(new Color(230, 230, 230));
        pba = new ArbreAdapter(0, parent);
        Panel pVisu = new Panel();
        pVisu.setLayout(new GridBagLayout());
        ImageButton iReg = new ImageButton(ressources.getImage("regard.gif"), this, 14);
        iReg.setInfo("Lien Web");
        xyPosition(pVisu, iReg, 0, 0, 1);
        Panel pFich = new Panel();
        pFich.setLayout(new GridBagLayout());
        ImageButton iLoad = new ImageButton(ressources.getImage("load.gif"), this, 3);
        iLoad.setInfo("Charger un mod?le");
        xyPosition(pFich, iLoad, 0, 0, 1);
        ImageButton iSave = new ImageButton(ressources.getImage("save.gif"), this, 2);
        iSave.setInfo("Sauver le mod?le");
        xyPosition(pFich, iSave, 0, 1, 1);
        ImageButton iLoadDist = new ImageButton(ressources.getImage("loadDist.gif"), this, 4);
        iLoadDist.setInfo("Charger un mod?le via un URL");
        xyPosition(pFich, iLoadDist, 0, 2, 1);
        Panel pForm = new Panel();
        pForm.setLayout(new GridBagLayout());
        ImageButton iSimple = new ImageButton(ressources.getImage("formSimpl.gif"), this, 5);
        iSimple.setInfo("Affichage simple de l'arbre");
        xyPosition(pForm, iSimple, 0, 0, 1);
        ImageButton iFormEqui = new ImageButton(ressources.getImage("formEqui.gif"), this, 6);
        iFormEqui.setInfo("Affichage ?quilibr?e");
        xyPosition(pForm, iFormEqui, 0, 1, 1);
        ImageButton iFormVert = new ImageButton(ressources.getImage("formVert.gif"), this, 8);
        iFormVert.setInfo("Affichage vertical");
        xyPosition(pForm, iFormVert, 1, 0, 1);
        ImageButton iGraphe = new ImageButton(ressources.getImage("graphe.gif"), this, 7);
        iGraphe.setInfo("Affichage de graphe");
        xyPosition(pForm, iGraphe, 1, 1, 1);
        Panel pZoom = new Panel();
        pZoom.setLayout(new GridBagLayout());
        ImageButton iZoomMoins = new ImageButton(ressources.getImage("zoom-.gif"), this, 10);
        iZoomMoins.setInfo("Affichage plus petit");
        xyPosition(pZoom, iZoomMoins, 0, 0, 1);
        ImageButton iZoomPlus = new ImageButton(ressources.getImage("zoom+.gif"), this, 9);
        iZoomPlus.setInfo("Affichage plus grand");
        xyPosition(pZoom, iZoomPlus, 1, 0, 1);
        buttonzoom = new ImageButton(ressources.getImage("zoom.gif"), true, this, 11);
        buttonzoom.setInfo("Ouvrir fen?tre de zoom");
        xyPosition(pZoom, buttonzoom, 0, 1, 1);
        Panel pAttribut = new Panel();
        pAttribut.setLayout(new GridBagLayout());
        buttonAttribut = new ImageButton(ressources.getImage("attribut.gif"), true, this, 12);
        buttonAttribut.setInfo("Afficher les attributs");
        buttonAttribut.setInfoSelect("Masquer les attributs");
        xyPosition(pAttribut, buttonAttribut, 0, 0, 1);
        buttonEdition = new ImageButton(ressources.getImage("edition.gif"), true, this, 13);
        buttonEdition.setInfo("Passer en mode ?dition");
        buttonEdition.setInfoSelect("Passer en mode visualisation");
        xyPosition(pAttribut, buttonEdition, 1, 0, 1);
        Panel pLangue = new Panel();
        pLangue.setLayout(new GridBagLayout());
        buttonFrancais = new ImageButton(ressources.getImage("frflag.gif"), false, this, 0);
        buttonFrancais.setShowBorder(false);
        buttonFrancais.setInfo("fran?ais choisit");
        xyPosition(pLangue, buttonFrancais, 0, 0, 1);
        buttonAnglais = new ImageButton(ressources.getImage("usgbflag.gif"), false, this, 1);
        buttonAnglais.setInfo("Anglais choisit");
        buttonAnglais.setShowBorder(false);
        xyPosition(pLangue, buttonAnglais, 0, 1, 1);
        c.insets = new Insets(3, 2, 1, 3);
        setLayout(new GridBagLayout());
        xyPosition(this, pVisu, 0, 0, 1);
        xyPosition(this, pFich, 0, 1, 1);
        xyPosition(this, pZoom, 0, 2, 1);
        xyPosition(this, pForm, 0, 3, 1);
        xyPosition(this, pAttribut, 0, 4, 1);
        xyPosition(this, comm, 0, 6, 1);
    }

    public void upddateButtonAttribut(boolean b)
    {
        buttonAttribut.setSelected(b);
    }

    public void upddateButtonMode(boolean b)
    {
        buttonEdition.setSelected(b);
    }

    public void updateButtonZoomViewer(boolean b)
    {
        buttonzoom.setSelected(b);
    }

    public void setInfo(String info)
    {
        if(il != null)
            il.setInfo(info);
        else
            comm.setText(info);
    }

    public void removeInfo()
    {
        if(il != null)
            il.removeInfo();
        else
            comm.setText("");
    }

    public void actionPerformed(ActionEvent e)
    {
        pba.actionPerformed(e);
    }

}
