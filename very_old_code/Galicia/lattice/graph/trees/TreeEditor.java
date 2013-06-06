// Decompiled by Jad v1.5.7g. Copyright 2000 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/SiliconValley/Bridge/8617/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi 
// Source File Name:   TreeEditor.java

package lattice.graph.trees;

import java.awt.*;
import java.io.PrintStream;
import lattice.graph.trees.event.FormatKeyListener;
import lattice.graph.trees.menu.MenuAffichage;
import lattice.graph.trees.menu.MenuItemChange;
import lattice.graph.utils.Ressources;

// Referenced classes of package lattice.graph.trees:
//            Editor, ActionGraphViewer, GraphViewer

public abstract class TreeEditor extends Editor
{

    protected Color defaultColor;
    protected ActionGraphViewer idc;
    protected Menu editer;
    protected Menu affichage;
    protected boolean affInfo;
    protected boolean formeRel;
    protected boolean textRel;
    protected boolean arrow;
    protected int posLien;
    protected boolean affAtt;
    protected boolean dynamique;
    protected boolean editionMode;
    protected boolean bufferDrag;

    public TreeEditor(String nom)
    {
        super(nom);
        defaultColor = Color.white;
        affInfo = false;
        formeRel = false;
        textRel = false;
        arrow = false;
        posLien = 1;
        affAtt = false;
        dynamique = false;
        editionMode = false;
        bufferDrag = false;
    }

    public void initEditor()
    {
        Panel p2 = new Panel();
        idc = new ActionGraphViewer();
        p2.add("Center", idc);
        add("Center", p2);
        setSize(730, 326);
        addKeyListener(new FormatKeyListener(idc));
    }

    public ActionGraphViewer getCanvas()
    {
        return idc;
    }

    public Color getDefaultColor()
    {
        return defaultColor;
    }

    public void setDefaultColor(Color rvb)
    {
        defaultColor = rvb;
    }

    public void loadBackgroundPicture()
    {
        String nomFich = null;
        try
        {
            FileDialog fd = new FileDialog(this, "Image de fond");
            fd.show();
            nomFich = fd.getDirectory() + fd.getFile();
            System.out.println(nomFich);
        }
        catch(Exception e)
        {
            System.out.println("Pb d'acc?s aux ressources");
        }
        if(nomFich != null)
        {
            Ressources rl = new Ressources(this);
            rl.setAcces(Ressources.SANS_URL);
            try
            {
                rl.init(nomFich);
                getCanvas();
                GraphViewer.setBackgroundPicture(rl.getImage(nomFich));
                getCanvas().setBgAlignment("Centrer");
            }
            catch(Exception e)
            {
                System.out.println("Fichier image non valide : " + nomFich);
            }
        }
    }

    public void dispose()
    {
    }

    public void changeAffZoomViewer()
    {
    }

    public void changeAffZoomViewer2()
    {
    }

    public void changeFormeRelation()
    {
        formeRel = !formeRel;
        if(formeRel)
            idc.changeFormeRelation(1);
        else
            idc.changeFormeRelation(0);
        if(affichage != null)
            ((MenuAffichage)affichage).relationForme.changeLabel();
    }

    public void changeTextRelation()
    {
        textRel = !textRel;
        if(textRel)
            idc.activeTextRelations();
        else
            idc.desactiveTextRelations();
        if(affichage != null)
            ((MenuAffichage)affichage).textRelations.changeLabel();
    }

    public void changeAffInfo()
    {
        affInfo = !affInfo;
        idc.afficherInfo(affInfo);
    }

    public void changeFleches()
    {
        arrow = !arrow;
        idc.setShowArrow(arrow);
        if(affichage != null)
            ((MenuAffichage)affichage).flechesRel.changeLabel();
    }

    public void posLiens()
    {
        if(posLien == 1)
            posLien = 2;
        else
            posLien = 1;
        idc.posLienRelations(posLien);
        if(affichage != null)
            ((MenuAffichage)affichage).posLien.changeLabel();
    }

    public abstract void afficher();

    public void affAttributs()
    {
        affAtt = !affAtt;
        idc.affAttributs(affAtt);
        if(affichage != null)
            ((MenuAffichage)affichage).affichAttributs.changeLabel();
    }

    public void affAttributs2()
    {
        affAttributs();
    }

    public void changeMode()
    {
        editionMode = !editionMode;
        idc.setEdition(editionMode);
    }

    public boolean getMode()
    {
        return editionMode;
    }

    public void changeMode2()
    {
        changeMode();
    }
}
