// Decompiled by Jad v1.5.7g. Copyright 2000 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/SiliconValley/Bridge/8617/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi 
// Source File Name:   RelationSelect.java

package lattice.graph.trees;

import java.awt.*;

// Referenced classes of package lattice.graph.trees:
//            Relation, Noeud

public class RelationSelect extends Relation
{

    int x;
    int y;

    public RelationSelect(Noeud origine, int x, int y)
    {
        super.origine = origine;
        this.x = x;
        this.y = y;
    }

    public int supGaucheX()
    {
        int o = origine().supGaucheX();
        if(o > x)
            return x;
        else
            return o;
    }

    public int supGaucheY()
    {
        int o = origine().supGaucheY();
        if(o > y)
            return y;
        else
            return o;
    }

    public int infDroitX()
    {
        int o = origine().infDroitX();
        if(o > x)
            return o;
        else
            return x;
    }

    public int infDroitY()
    {
        int o = origine().infDroitY();
        if(o > y)
            return o;
        else
            return y;
    }

    public int width()
    {
        return infDroitX() - supGaucheX();
    }

    public int height()
    {
        return infDroitY() - supGaucheY();
    }

    public void setPos(int a, int b)
    {
        x = a;
        y = b;
    }

    public Rectangle rect()
    {
        return new Rectangle(supGaucheX() - Relation.widthArrow, supGaucheY() - Relation.widthArrow, width() + 2 * Relation.widthArrow, height() + 2 * Relation.widthArrow);
    }

    public void paint1(Graphics g, int xRel, int yRel)
    {
        g.setColor(Color.red);
        int x1;
        int y1;
        if(posLien == 0)
        {
            x1 = origine().infDroitX();
            y1 = origine.infDroitY() - origine.height() / 2;
        } else
        {
            x1 = origine().supGaucheX() + origine().width() / 2;
            y1 = origine.infDroitY();
        }
        g.drawLine(x1, y1, x, y);
        if(showArrow)
            paintArrow(g, x1, y1, x, y);
    }

    public void paint2(Graphics g, int xRel, int yRel)
    {
        g.setColor(Color.red);
        int x1;
        int y1;
        int x2;
        int y2;
        int x3;
        int y3;
        int x4;
        int y4;
        if(posLien == 0)
        {
            x1 = origine().infDroitX();
            y1 = origine().infDroitY() - origine().height() / 2;
            if(Relation.decalage2dPoint)
                x2 = getDecalage2dPoint();
            else
                x2 = x1 + (x - x1) / 4;
            y2 = y1;
            x3 = x2;
            y3 = y + origine.height() / 2;
            x4 = x;
            y4 = y3;
        } else
        {
            x1 = origine().supGaucheX() + origine().rect2().width / 2;
            y1 = origine().infDroitY();
            x2 = x1;
            y2 = y1 + (y - y1) / 4;
            x3 = x;
            y3 = y2;
            x4 = x3;
            y4 = y;
        }
        g.drawLine(x1, y1, x2, y2);
        g.drawLine(x2, y2, x3, y3);
        g.drawLine(x3, y3, x4, y4);
        if(showArrow)
            paintArrow(g, x3, y3, x4, y4);
    }
}
