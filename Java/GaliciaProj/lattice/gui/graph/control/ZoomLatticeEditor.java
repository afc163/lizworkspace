// Decompiled by Jad v1.5.7g. Copyright 2000 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/SiliconValley/Bridge/8617/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi 
// Source File Name:   ZoomLatticeEditor.java

package lattice.gui.graph.control;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;
import javax.swing.text.JTextComponent;
import lattice.graph.zoom.*;

public class ZoomLatticeEditor extends JPanel
    implements ZoomEditorInterface
{
    class C
        implements ActionListener
    {

        static final int QUALITE = 0;
        static final int UPDATE = 1;
        static final int FACTOR_CHANGE = 2;
        int action;

        public void actionPerformed(ActionEvent e)
        {
            switch(action)
            {
            case 0: // '\0'
                zoomViewer.setQualite(!zoomViewer.qualite());
                zoomViewer.refresh1();
                updateZoomFactor(zoomViewer.getFactor());
                break;

            case 1: // '\001'
                zoomViewer.clearGraphics();
                zoomViewer.refresh1();
                updateZoomFactor(zoomViewer.getFactor());
                break;

            case 2: // '\002'
                Float f = Float.valueOf(zoomFactor.getText());
                zoomViewer.setFactor(f.floatValue());
                zoomViewer.refresh1();
                break;
            }
        }

        C(int action)
        {
            this.action = action;
        }
    }


    static final int QUALITE = 0;
    static final int UPDATE = 1;
    static final int FACTOR_CHANGE = 2;
    protected ZoomViewer zoomViewer;
    JPanel panelButton;
    JTextField zoomFactor;

    public ZoomLatticeEditor(String nom, ZoomInterface ac)
    {
        zoomViewer = new ZoomViewer(ac, this);
        initAffichage();
    }

    public Dimension getPreferredSize()
    {
        return new Dimension(170, 165);
    }

    public void initAffichage()
    {
        setLayout(new BorderLayout(0, 0));
        setOpaque(false);
        add(initPanelButton(), "North");
        JPanel p = new JPanel();
        p.setOpaque(false);
        p.add(zoomViewer);
        add(p, "Center");
    }

    protected JPanel initPanelButton()
    {
        panelButton = new JPanel();
        JCheckBox bQualite = new JCheckBox("Quality", zoomViewer.qualite());
        bQualite.setForeground(Color.white);
        bQualite.addActionListener(new C(0));
        bQualite.setOpaque(false);
        JButton bUpdate = new JButton(" Update ");
        bUpdate.addActionListener(new C(1));
        zoomFactor = new JTextField("");
        panelButton.setOpaque(false);
        panelButton.setLayout(new FlowLayout(1, 2, 2));
        panelButton.add(bQualite);
        panelButton.add(bUpdate);
        updateZoomFactor(zoomViewer.getFactor());
        return panelButton;
    }

    public void setColorPanelButton(Color c)
    {
        panelButton.setBackground(c);
    }

    public void setZoomViewer(ZoomViewer zc)
    {
        zoomViewer = zc;
    }

    public ZoomViewer zoomViewer()
    {
        return zoomViewer;
    }

    public void updateZoomFactor(float f)
    {
        if(zoomFactor != null)
        {
            String s = Float.toString(f);
            if(s.length() > 5)
                s = new String(s.substring(0, 5));
            zoomFactor.setText(s);
        }
    }
}
