// Decompiled by Jad v1.5.7g. Copyright 2000 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/SiliconValley/Bridge/8617/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi 
// Source File Name:   Attribut.java

package lattice.graph.trees;

import java.awt.Color;
import java.util.Observable;
import java.util.Observer;

// Referenced classes of package lattice.graph.trees:
//            Selectable, Noeud

public class Attribut
    implements Observer, Cloneable, Selectable
{

    protected static int num = 0;
    protected boolean selected;
    protected boolean clicked;
    protected boolean cible;
    protected String label;
    protected Noeud pere;

    public Attribut(Noeud unNoeud)
    {
        selected = false;
        clicked = false;
        cible = false;
        pere = unNoeud;
        num++;
        label = new String("attribut n\241" + num);
    }

    public Attribut(Noeud unNoeud, String libelle)
    {
        selected = false;
        clicked = false;
        cible = false;
        label = libelle;
        pere = unNoeud;
        num++;
    }

    public boolean getSelected()
    {
        return selected;
    }

    public void setSelected(boolean selected)
    {
        this.selected = selected;
    }

    public boolean getClicked()
    {
        return clicked;
    }

    public void setClicked(boolean clicked)
    {
        this.clicked = clicked;
    }

    public boolean getCible()
    {
        return cible;
    }

    public void setCible(boolean cible)
    {
        this.cible = cible;
    }

    public String getLabel()
    {
        return label;
    }

    public void setLabel(String libelle)
    {
        label = libelle;
    }

    public void setPere(Noeud pere)
    {
        this.pere = pere;
    }

    public Noeud getPere()
    {
        return pere;
    }

    public Color getColorType()
    {
        return Color.black;
    }

    public void initNewPere(Noeud pere)
    {
        setPere(pere);
    }

    public boolean isThereIllustration()
    {
        return false;
    }

    public int nbIllustration()
    {
        return 0;
    }

    public void update(Observable o, Object args)
    {
        if(pere != null)
            pere.update(o, args);
    }

    public Object clone()
    {
        Attribut newAtt = new Attribut(pere);
        newAtt.label = label;
        return newAtt;
    }

    public String getInfo()
    {
        return getLabel();
    }

}
