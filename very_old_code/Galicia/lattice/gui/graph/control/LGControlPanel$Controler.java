// Decompiled by Jad v1.5.7g. Copyright 2000 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/SiliconValley/Bridge/8617/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi 
// Source File Name:   LGControlPanel.java

package lattice.gui.graph.control;

import java.awt.event.*;
import java.io.PrintStream;
import java.util.EventObject;
import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.text.JTextComponent;
import lattice.graph.trees.GraphViewer;
import lattice.gui.graph.LatticeGraphViewer;
import lattice.gui.graph.magneto.Magneto;

// Referenced classes of package lattice.gui.graph.control:
//            LGControlPanel

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
