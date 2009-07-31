// Decompiled by Jad v1.5.7g. Copyright 2000 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/SiliconValley/Bridge/8617/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi 
// Source File Name:   LGControlPanel.java

package lattice.gui.graph.control;

import java.awt.*;
import java.awt.event.*;
import java.io.PrintStream;
import java.util.EventObject;
import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.text.JTextComponent;
import lattice.graph.trees.GraphViewer;
import lattice.graph.utils.BorderedPanel;
import lattice.gui.graph.LatticeGraphViewer;
import lattice.gui.graph.magneto.Magneto;

public class LGControlPanel extends JPanel
{
    class Controler
        implements ActionListener, ItemListener, ChangeListener
    {

        int controlValue;
        JComponent component;
        double defaultValueDouble;
        long defaultValueLong;
        int defaultValueInt;

        public void actionPerformed(ActionEvent e)
        {
            switch(controlValue)
            {
            case 1: // '\001'
                lgv.initFormatterHBLattice(fitScreen, optimizer);
                break;

            case 4: // '\004'
                lgv.ZP();
                break;

            case 5: // '\005'
                lgv.ZM();
                break;

            case 15: // '\017'
                modTension();
                break;

            case 13: // '\r'
                modRepulsion();
                break;

            case 11: // '\013'
                modTimeSleep();
                break;
            }
        }

        public void itemStateChanged(ItemEvent e)
        {
            switch(controlValue)
            {
            case 2: // '\002'
                fitScreen = !fitScreen;
                break;

            case 3: // '\003'
                optimizer = !optimizer;
                break;

            case 10: // '\n'
                getMagneto().setMagnet(!getMagneto().getMagnet());
                break;

            case 18: // '\022'
                getMagneto().setFixFirstLevel(!getMagneto().fixFirstLevel());
                break;

            case 19: // '\023'
                getMagneto().setFixLastLevel(!getMagneto().fixLastLevel());
                break;

            case 21: // '\025'
                getMagneto().setMagnetRelation(!getMagneto().magnetRelation());
                break;

            case 22: // '\026'
                getMagneto().setMagnetMouse(!getMagneto().magnetMouse());
                break;

            case 30: // '\036'
                lgv.setThreeD(!lgv.getThreeD());
                break;
            }
        }

        public void modTension()
        {
            String rep = ((JTextField)component).getText();
            double d = 0.0D;
            try
            {
                d = (new Double(rep)).doubleValue();
                getMagneto().setTensionFactor(d);
                st.setValue((int)(d * 10D));
            }
            catch(Exception e)
            {
                System.out.println("Valeur de r?pulsion non valide : " + rep);
                ((JTextField)component).setText((new Double(defaultValueDouble)).toString());
                st.setValue((int)defaultValueDouble);
            }
        }

        public void modSlideTension(int val)
        {
            ((JTextField)component).setText((new Integer(val / 10)).toString());
            modTension();
        }

        public void modRepulsion()
        {
            String rep = ((JTextField)component).getText();
            double d = 0.0D;
            try
            {
                d = (new Double(rep)).doubleValue();
                getMagneto().setRepulsionFactor(d / 10D);
                sr.setValue((int)(d * 10D));
            }
            catch(Exception e)
            {
                System.out.println("Valeur de r?pulsion non valide : " + rep);
                Double dou = new Double(defaultValueDouble * 10D);
                ((JTextField)component).setText(dou.toString());
                sr.setValue((int)(defaultValueDouble * 10D));
            }
        }

        public void modSlideRepulsion(int val)
        {
            ((JTextField)component).setText((new Integer(val / 10)).toString());
            modRepulsion();
        }

        public void modTimeSleep()
        {
            String rep = ((JTextField)component).getText();
            try
            {
                long d = (new Long(rep)).longValue();
                getMagneto().setTimeSleep(d);
            }
            catch(Exception e)
            {
                System.out.println("Valeur de r?pulsion non valide : " + rep);
                ((JTextField)component).setText((new Long(defaultValueLong)).toString());
            }
        }

        public void modeSlideTime(int val)
        {
            ((JTextField)component).setText((new Integer(val)).toString());
            modTimeSleep();
        }

        public void modeSlideRotation(int val)
        {
            getMagneto().setRotation((float)(val - 10) / 2.0F);
        }

        public void stateChanged(ChangeEvent e)
        {
            int v = ((JSlider)e.getSource()).getValue();
            switch(controlValue)
            {
            case 14: // '\016'
                modSlideRepulsion(v);
                break;

            case 16: // '\020'
                modSlideTension(v);
                break;

            case 12: // '\f'
                modeSlideTime(v);
                break;

            case 31: // '\037'
                modeSlideRotation(v);
                break;
            }
        }

        Controler(int controlValue)
        {
            this.controlValue = 0;
            this.controlValue = controlValue;
        }

        Controler(int controlValue, JComponent c, int defaultValueInt)
        {
            this(controlValue);
            component = c;
            this.defaultValueInt = defaultValueInt;
        }

        Controler(int controlValue, JComponent c, double defaultValueDouble)
        {
            this(controlValue);
            component = c;
            this.defaultValueDouble = defaultValueDouble;
        }

        Controler(int controlValue, JComponent c, long defaultValueLong)
        {
            this(controlValue);
            component = c;
            this.defaultValueLong = defaultValueLong;
        }
    }


    public static Color back = new Color(70, 70, 100);
    public static final int FORMATTER = 1;
    public static final int FIT_SCREEN = 2;
    public static final int OPTIMIZE = 3;
    public static final int ZOOM_PLUS = 4;
    public static final int ZOOM_MOINS = 5;
    public static final int MAGNET = 10;
    public static final int TIME_SLEEP = 11;
    public static final int TIME_SLIDE = 12;
    public static final int REPULSION = 13;
    public static final int REPULSION_SLIDE = 14;
    public static final int TENSION = 15;
    public static final int TENSION_SLIDE = 16;
    public static final int STEP = 17;
    public static final int FIX_FIRST_LEVEL = 18;
    public static final int FIX_LAST_LEVEL = 19;
    public static final int FIX_BOTTOM = 20;
    public static final int MAGNET_RELATIONS = 21;
    public static final int MAGNET_MOUSE = 22;
    public static final int THREE_D = 30;
    public static final int ROTATION = 31;
    public boolean fitScreen;
    public boolean optimizer;
    public boolean magnet;
    public JSlider sr;
    public JSlider st;
    public JSlider stime;
    public JSlider srot;
    public LatticeGraphViewer lgv;

    public LGControlPanel(LatticeGraphViewer lgv)
    {
        fitScreen = false;
        optimizer = false;
        magnet = false;
        this.lgv = lgv;
        try
        {
            jbInit();
        }
        catch(Exception exception) { }
    }

    public Magneto getMagneto()
    {
        return lgv.getMagneto();
    }

    private void jbInit()
        throws Exception
    {
        setLayout(new FlowLayout(0));
        JPanel c = new JPanel();
        c.setLayout(new BorderLayout(10, 0));
        c.setOpaque(false);
        add(c);
        setOpaque(false);
        c.add(initFormat(), "North");
        c.add(initMagnet(), "Center");
        c.add(init3D(), "South");
        validate();
    }

    public JPanel init3D()
    {
        BorderedPanel p3D = new BorderedPanel("           ", Color.white);
        p3D.setLayout(new GridBagLayout());
        p3D.initGridBagConstraint();
        p3D.c.insets = new Insets(3, 5, 3, 4);
        JCheckBox c3D = new JCheckBox("3D", false);
        c3D.setForeground(Color.white);
        c3D.setOpaque(false);
        p3D.xyPosition(p3D, c3D, 0, 1, 1);
        c3D.addItemListener(new Controler(30));
        JLabel lRot = new JLabel("Rotation   ");
        lRot.setForeground(Color.white);
        lRot.setToolTipText("Rotation the 3D lattice model left or right");
        p3D.xyPosition(p3D, lRot, 0, 2, 1);
        srot = new JSlider(0, 0, 20, 10) {

            public Dimension getPreferredSize()
            {
                return new Dimension(70, 25);
            }

        };
        srot.addChangeListener(new Controler(31, null, 10));
        srot.setMajorTickSpacing(1);
        srot.setOpaque(false);
        srot.setBackground(back);
        p3D.xyPosition(p3D, srot, 1, 2, 1, 0.0D);
        return p3D;
    }

    public JPanel initFormat()
    {
        BorderedPanel pFormat = new BorderedPanel("Format", Color.white);
        pFormat.setLayout(new GridBagLayout());
        pFormat.initGridBagConstraint();
        pFormat.c.insets = new Insets(3, 5, 3, 4);
        JLabel test = new JLabel("      ");
        pFormat.xyPosition(pFormat, test, 0, 0, 1);
        test.setOpaque(false);
        JCheckBox cFitScreen = new JCheckBox("Fit      ", false);
        cFitScreen.setForeground(Color.white);
        cFitScreen.setOpaque(false);
        pFormat.xyPosition(pFormat, cFitScreen, 0, 1, 1);
        cFitScreen.addItemListener(new Controler(2));
        cFitScreen.setToolTipText("Format lattice in order to fit screen size");
        JCheckBox cOptimize = new JCheckBox("Optimize", false);
        cOptimize.setForeground(Color.white);
        cOptimize.setOpaque(false);
        pFormat.xyPosition(pFormat, cOptimize, 1, 1, 1);
        cOptimize.addItemListener(new Controler(3));
        cOptimize.setToolTipText("Optimize arrangement of nodes in each level");
        JButton bFormat1 = new JButton("Format");
        pFormat.xyPosition(pFormat, bFormat1, 0, 3, 2, 0.0D);
        bFormat1.addActionListener(new Controler(1));
        JButton bZoomP = new JButton("+");
        pFormat.xyPosition(pFormat, bZoomP, 0, 4, 1);
        bZoomP.addActionListener(new Controler(4));
        JButton bZoomM = new JButton("-");
        pFormat.xyPosition(pFormat, bZoomM, 1, 4, 1);
        bZoomM.addActionListener(new Controler(5));
        return pFormat;
    }

    public JPanel initMagnet()
    {
        BorderedPanel pMagnet = new BorderedPanel("                       ");
        pMagnet.setLayout(new GridBagLayout());
        pMagnet.initGridBagConstraint();
        pMagnet.c.insets = new Insets(3, 5, 3, 4);
        JLabel test2 = new JLabel("   ");
        test2.setOpaque(false);
        JCheckBox cMagnet = new JCheckBox("Magnetism", false);
        cMagnet.setForeground(Color.white);
        cMagnet.setOpaque(false);
        pMagnet.xyPosition(pMagnet, cMagnet, 0, 1, 2, 0.0D);
        cMagnet.addItemListener(new Controler(10));
        cMagnet.setToolTipText("Magnet the distance beetween nodes");
        JLabel lTime = new JLabel("Time sleep");
        lTime.setForeground(Color.white);
        lTime.setToolTipText("Period beetween two iterations");
        pMagnet.xyPosition(pMagnet, lTime, 0, 2, 1);
        long defaultTimeSleep = getMagneto().getTimeSleep();
        JTextField TtimeSleep = new JTextField((new Long(defaultTimeSleep)).toString());
        TtimeSleep.addActionListener(new Controler(11, TtimeSleep, defaultTimeSleep));
        stime = new JSlider(0, 0, 800, (int)defaultTimeSleep) {

            public Dimension getPreferredSize()
            {
                return new Dimension(40, 25);
            }

        };
        stime.addChangeListener(new Controler(12, TtimeSleep, defaultTimeSleep));
        stime.setMajorTickSpacing(10);
        stime.setOpaque(false);
        stime.setBackground(back);
        stime.setMinorTickSpacing(1);
        pMagnet.xyPosition(pMagnet, stime, 1, 2, 2, 0.0D);
        JLabel lTension = new JLabel("Tension");
        lTension.setForeground(Color.white);
        lTension.setToolTipText("Tension of the links beetween nodes");
        pMagnet.xyPosition(pMagnet, lTension, 0, 3, 1);
        double defaultTension = getMagneto().getTensionFactor();
        JTextField Ttension = new JTextField((new Double(defaultTension)).toString());
        Ttension.addActionListener(new Controler(15, Ttension, defaultTension));
        st = new JSlider(0, 0, 300, (int)defaultTension * 10) {

            public Dimension getPreferredSize()
            {
                return new Dimension(60, 25);
            }

        };
        st.setSize(40, 25);
        st.addChangeListener(new Controler(16, Ttension, defaultTension));
        st.setMajorTickSpacing(1);
        st.setOpaque(false);
        st.setBackground(back);
        pMagnet.xyPosition(pMagnet, st, 1, 3, 2, 0.0D);
        JLabel jRepulsion = new JLabel("Repulsion");
        jRepulsion.setForeground(Color.white);
        jRepulsion.setToolTipText("Repulsion force beetween nodes");
        pMagnet.xyPosition(pMagnet, jRepulsion, 0, 5, 1);
        double defaultRepulsion = getMagneto().getRepulsionFactor();
        Double dou = new Double(defaultRepulsion * 10D);
        JTextField Trepulsion = new JTextField(dou.toString());
        Trepulsion.addActionListener(new Controler(13, Trepulsion, defaultRepulsion));
        int t = (int)(100D * defaultRepulsion);
        sr = new JSlider(0, 0, 80, t) {

            public Dimension getPreferredSize()
            {
                return new Dimension(60, 25);
            }

        };
        sr.addChangeListener(new Controler(14, Trepulsion, defaultRepulsion));
        sr.createStandardLabels(1);
        sr.setOpaque(false);
        sr.setBackground(back);
        pMagnet.xyPosition(pMagnet, sr, 1, 5, 2, 0.0D);
        JCheckBox cfixFirstLevel = new JCheckBox("Fix first", getMagneto().fixFirstLevel());
        cfixFirstLevel.setForeground(Color.white);
        cfixFirstLevel.setOpaque(false);
        pMagnet.xyPosition(pMagnet, cfixFirstLevel, 0, 7, 1);
        cfixFirstLevel.addItemListener(new Controler(18));
        cfixFirstLevel.setToolTipText("Fix first level nodes position");
        JCheckBox cfixLastLevel = new JCheckBox("Fix last", getMagneto().fixLastLevel());
        cfixLastLevel.setForeground(Color.white);
        cfixLastLevel.setOpaque(false);
        pMagnet.xyPosition(pMagnet, cfixLastLevel, 1, 7, 1);
        cfixLastLevel.addItemListener(new Controler(19));
        cfixLastLevel.setToolTipText("Fix last level nodes position");
        JCheckBox cMagnetRelation = new JCheckBox("Relation", getMagneto().magnetRelation());
        cMagnetRelation.setForeground(Color.white);
        cMagnetRelation.setOpaque(false);
        pMagnet.xyPosition(pMagnet, cMagnetRelation, 0, 9, 1);
        cMagnetRelation.addItemListener(new Controler(21));
        cMagnetRelation.setToolTipText("Magnet cross relations");
        JCheckBox cMagnetMouse = new JCheckBox("Mouse", getMagneto().magnetMouse());
        cMagnetMouse.setForeground(Color.white);
        cMagnetMouse.setOpaque(false);
        pMagnet.xyPosition(pMagnet, cMagnetMouse, 1, 9, 1);
        cMagnetMouse.addItemListener(new Controler(22));
        cMagnetMouse.setToolTipText("Magnet mouse pointer");
        return pMagnet;
    }

}
