// Decompiled by Jad v1.5.7g. Copyright 2000 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/SiliconValley/Bridge/8617/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi 
// Source File Name:   Editor.java

package lattice.graph.trees;

import java.awt.*;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.PrintStream;
import java.util.Observable;
import java.util.Observer;
import lattice.graph.imageButton.ImageButton;
import lattice.graph.trees.event.EditorAdapter;
import lattice.graph.utils.Ressources;

public class Editor extends Frame
    implements WindowListener, Observer
{

    public static String adresse_manuel = "aide/IKBSMan.html";
    public MenuBar menuBar;
    public GridBagConstraints c;

    public Editor(String nom)
    {
        super(nom);
        initBarreMenu();
        initHelpMenu();
        init();
    }

    public void init()
    {
        addWindowListener(this);
    }

    protected void initBarreMenu()
    {
        menuBar = new MenuBar();
    }

    public void initMenu()
    {
    }

    public void initEditor()
    {
    }

    void initHelpMenu()
    {
        Menu about = new Menu("A propos");
        about.add(new MenuItem("A propos d'IKBS"));
        about.addActionListener(new EditorAdapter(1, this));
        menuBar.setHelpMenu(about);
    }

    public void windowClosing(WindowEvent event)
    {
        dispose();
    }

    public void windowActivated(WindowEvent windowevent)
    {
    }

    public void windowClosed(WindowEvent windowevent)
    {
    }

    public void windowDeactivated(WindowEvent windowevent)
    {
    }

    public void windowDeiconified(WindowEvent windowevent)
    {
    }

    public void windowIconified(WindowEvent windowevent)
    {
    }

    public void windowOpened(WindowEvent windowevent)
    {
    }

    public Frame getFrame()
    {
        return this;
    }

    public void afficherAPropos()
    {
        Ressources rl = new Ressources(this);
        rl.setAcces(Ressources.FROM_JAR);
        try
        {
            rl.init("aPropos.gif");
        }
        catch(Exception exc)
        {
            System.out.println("Erreur de chargement des images");
        }
        ImageButton buttonImage = new ImageButton(rl.getImage("aPropos.gif"), new EditorAdapter(0, this), 0);
        buttonImage.setShowBorder(false);
        Button lienAide = new Button("Aide : Manuel de l'utilisateur");
        lienAide.addActionListener(new EditorAdapter(2, this));
        Editor f = new Editor("A propos d'IKBS...");
        f.setLayout(new BorderLayout(0, 0));
        f.add("Center", buttonImage);
        f.add("South", lienAide);
        f.pack();
        f.show();
    }

    public void afficherAide()
    {
        Ressources.showDocument(adresse_manuel);
    }

    protected void initGridBagConstraint()
    {
        c = new GridBagConstraints();
        c.ipadx = 0;
        c.ipady = 0;
        c.gridheight = 1;
        c.fill = 1;
        c.weightx = 0.5D;
        c.weighty = 0.5D;
        c.anchor = 10;
        c.insets = new Insets(2, 2, 2, 2);
    }

    public void xyPosition(Container conteneur, Component element, int x, int y, int gridwidth)
    {
        if(c == null)
            initGridBagConstraint();
        c.gridx = x;
        c.gridy = y;
        c.gridwidth = gridwidth;
        ((GridBagLayout)conteneur.getLayout()).setConstraints(element, c);
        conteneur.add(element);
    }

    public void xyPosition(Container conteneur, Component element, int x, int y, int gridwidth, double weightx)
    {
        if(c == null)
            initGridBagConstraint();
        c.gridx = x;
        c.gridy = y;
        c.gridwidth = gridwidth;
        c.weightx = weightx;
        ((GridBagLayout)conteneur.getLayout()).setConstraints(element, c);
        conteneur.add(element);
    }

    public void centrer(Frame f)
    {
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int posX = (screenSize.width - f.getSize().width) / 2;
        int posY = (screenSize.height - f.getSize().height) / 2;
        f.setLocation(posX, posY);
    }

    public void update(Observable observable, Object obj)
    {
    }

}
