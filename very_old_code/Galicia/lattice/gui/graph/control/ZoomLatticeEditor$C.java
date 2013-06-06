// Decompiled by Jad v1.5.7g. Copyright 2000 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/SiliconValley/Bridge/8617/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi 
// Source File Name:   ZoomLatticeEditor.java

package lattice.gui.graph.control;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.text.JTextComponent;
import lattice.graph.zoom.ZoomViewer;

// Referenced classes of package lattice.gui.graph.control:
//            ZoomLatticeEditor

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
