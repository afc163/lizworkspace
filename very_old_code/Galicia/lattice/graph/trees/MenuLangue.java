// Decompiled by Jad v1.5.7g. Copyright 2000 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/SiliconValley/Bridge/8617/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi 
// Source File Name:   MenuLangue.java

package lattice.graph.trees;

import java.awt.*;
import lattice.graph.trees.event.MenuLangueAdapter;

// Referenced classes of package lattice.graph.trees:
//            LangueManager

public class MenuLangue extends Menu
{

    LangueManager langueManager;

    public MenuLangue(LangueManager langueManager, String nom)
    {
        super(nom);
        this.langueManager = langueManager;
        init();
    }

    protected void init()
    {
        CheckboxMenuItem francais = new CheckboxMenuItem("Fran?ais");
        francais.setState(true);
        add(francais);
        CheckboxMenuItem anglais = new CheckboxMenuItem("Anglais");
        add(anglais);
        MenuLangueAdapter mla = new MenuLangueAdapter(langueManager, francais, anglais);
        francais.addActionListener(mla);
        anglais.addActionListener(mla);
    }
}
