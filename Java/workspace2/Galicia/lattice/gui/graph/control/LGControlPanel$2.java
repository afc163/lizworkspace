// Decompiled by Jad v1.5.7g. Copyright 2000 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/SiliconValley/Bridge/8617/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi 
// Source File Name:   LGControlPanel.java

package lattice.gui.graph.control;

import java.awt.Dimension;
import javax.swing.JSlider;

// Referenced classes of package lattice.gui.graph.control:
//            LGControlPanel

final class _cls2 extends JSlider
{

    public Dimension getPreferredSize()
    {
        return new Dimension(40, 25);
    }

    _cls2(int $anonymous0, int $anonymous1, int $anonymous2, int $anonymous3)
    {
        super($anonymous0, $anonymous1, $anonymous2, $anonymous3);
    }
}
