// Decompiled by Jad v1.5.7g. Copyright 2000 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/SiliconValley/Bridge/8617/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi 
// Source File Name:   DemoGraphApplet.java

package lattice;

import java.applet.Applet;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import lattice.gui.graph.Exemple.BigLattice;
import lattice.gui.graph.Exemple.NestedLattice;
import lattice.gui.graph.Exemple.SmallLattice1;
import lattice.gui.graph.Exemple.SmallLattice2;
import lattice.gui.graph.LatticeGraphFrame;

public class DemoGraphApplet extends Applet
    implements ActionListener
{

    private boolean isStandalone;
    private Choice l;

    public String getParameter(String key, String def)
    {
        return isStandalone ? System.getProperty(key, def) : getParameter(key) == null ? def : getParameter(key);
    }

    public DemoGraphApplet()
    {
        isStandalone = false;
    }

    public void init()
    {
        try
        {
            jbInit();
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }

    private void jbInit()
        throws Exception
    {
        Label label1 = new Label("Lattice Viewer v1.0");
        label1.setForeground(Color.white);
        setBackground(new Color(70, 70, 100));
        Button jb = new Button("Clic to start");
        jb.addActionListener(this);
        add(label1);
        add(jb);
        Label label2 = new Label("developped by D. Grosser");
        label2.setForeground(Color.white);
        add(label2);
        l = new Choice();
        l.add("Normal lattice");
        l.add("Planar lattice");
        l.add("Big lattice");
        l.add("Nested Line Diagram");
        add(l);
    }

    public void start()
    {
    }

    public void stop()
    {
    }

    public void destroy()
    {
    }

    public String getAppletInfo()
    {
        return "Galicia v1.0b1 - Galois Lattice Interactive Constructor - Copyright University of Montreal";
    }

    public String[][] getParameterInfo()
    {
        return null;
    }

    public void actionPerformed(ActionEvent e)
    {
        showStatus("Loading classes, please wait ...");
        lattice.util.Node top = null;
        String s = l.getSelectedItem();
        if(s.equals("Normal lattice"))
            top = (new SmallLattice1()).creer();
        if(s.equals("Planar lattice"))
            top = (new SmallLattice2()).creer();
        if(s.equals("Big lattice"))
            top = (new BigLattice()).creer();
        if(s.equals("Nested Line Diagram"))
            top = (new NestedLattice()).creer();
        showStatus("Generating lattice, please wait ...");
        LatticeGraphFrame f = new LatticeGraphFrame(s, top);
        showStatus("Opening lattice viewer");
        f.setLocation(150, 50);
        f.show();
    }
}
