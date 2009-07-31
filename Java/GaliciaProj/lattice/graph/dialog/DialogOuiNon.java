// Decompiled by Jad v1.5.7g. Copyright 2000 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/SiliconValley/Bridge/8617/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi 
// Source File Name:   DialogOuiNon.java

package lattice.graph.dialog;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.EventObject;
import lattice.graph.utils.*;

// Referenced classes of package lattice.graph.dialog:
//            DialogListener

public class DialogOuiNon extends Dialog
    implements InfoListener
{

    protected static final int ANNULER = 0;
    protected static final int VALIDER = 1;
    protected String texte;
    protected String bGauche;
    protected String bDroit;
    protected DialogListener dl;

    public DialogOuiNon(Frame owner, String title, boolean modal)
    {
        super(owner, title, modal);
        bGauche = new String("Annuler");
        bDroit = new String("Valider");
        if(owner instanceof DialogListener)
            dl = (DialogListener)owner;
        init();
        pack();
    }

    public DialogOuiNon(Frame owner, String title, boolean modal, DialogListener dl, String gauche, String droit)
    {
        super(owner, title, modal);
        bGauche = new String("Annuler");
        bDroit = new String("Valider");
        this.dl = dl;
        bGauche = gauche;
        bDroit = droit;
        init();
        pack();
    }

    public DialogOuiNon(Frame owner, String title, boolean modal, DialogListener dl, String s, String gauche, String droit)
    {
        super(owner, title, modal);
        bGauche = new String("Annuler");
        bDroit = new String("Valider");
        texte = s;
        this.dl = dl;
        bGauche = gauche;
        bDroit = droit;
        init();
        pack();
    }

    public void init()
    {
        setLayout(new BorderLayout());
        if(texte != null)
            add("North", initTexte());
        add("Center", initPButton());
    }

    public Panel initTexte()
    {
        Panel pTexte = new Panel();
        pTexte.add(new Label(texte));
        return pTexte;
    }

    protected Panel initPButton()
    {
        Panel pButton = new Panel();
        pButton.setLayout(new FlowLayout());
        ButtonChoix cancel = new ButtonChoix(bGauche, this, 0);
        cancel.setInfo("Annuler les modifications et fermer la fen?tre");
        pButton.add(cancel);
        ButtonChoix ok = new ButtonChoix(bDroit, this, 1);
        ok.setInfo("Valider les modifications");
        pButton.add(ok);
        return pButton;
    }

    public void setInfo(String s)
    {
    }

    public void removeInfo()
    {
    }

    public void actionPerformed(ActionEvent e)
    {
        switch(((ChoixComponent)e.getSource()).getChoix())
        {
        case 1: // '\001'
            valider();
            dispose();
            break;

        case 0: // '\0'
            annuler();
            dispose();
            break;
        }
    }

    public void valider()
    {
        if(dl != null)
            dl.valider();
    }

    public void annuler()
    {
        if(dl != null)
            dl.annuler();
    }
}
