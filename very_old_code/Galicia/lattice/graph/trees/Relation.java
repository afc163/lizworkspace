// Decompiled by Jad v1.5.7g. Copyright 2000 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/SiliconValley/Bridge/8617/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi 
// Source File Name:   Relation.java

package lattice.graph.trees;

import java.awt.*;

// Referenced classes of package lattice.graph.trees:
//            Composant, Selectable, Noeud, AttributsList

public class Relation extends Composant
    implements Selectable
{

    public static final int FORME_ESCALIER = 0;
    public static final int FORME_DROITE = 1;
    public static boolean decalage2dPoint = true;
    public static boolean doubleTrait = false;
    protected static Color backgroundColor;
    protected static int numRel = 0;
    protected static int widthArrow = 24;
    protected static double angleArrow = 0.31415926535897931D;
    protected Noeud origine;
    protected Noeud extremite;
    protected int posLien;
    protected boolean showArrow;
    protected boolean selected;
    protected int forme;
    public Color normal_color;

    public Relation()
    {
        normal_color = Color.black;
        numRel++;
        setLabel("relation " + String.valueOf(numRel));
    }

    public Relation(Noeud noeud1, Noeud noeud2)
    {
        normal_color = Color.black;
        origine = noeud1;
        extremite = noeud2;
        numRel++;
        setLabel("relation " + String.valueOf(numRel));
        init();
    }

    public Relation(Noeud noeud1, Noeud noeud2, String nom)
    {
        normal_color = Color.black;
        origine = noeud1;
        extremite = noeud2;
        numRel++;
        setLabel(nom);
        init();
    }

    public Relation(Noeud noeud1, Noeud noeud2, String nom, FontMetrics fm)
    {
        normal_color = Color.black;
        origine = noeud1;
        extremite = noeud2;
        numRel++;
        setLabel(nom);
        setWidthLabel(fm.stringWidth(getLabel()));
        setHeightLabel(fm.getHeight());
        init();
    }

    protected void init()
    {
        if(getLabel() == null)
            setLabel("");
        posLien = 0;
        origine.addRelationDepart(this);
        extremite.addRelationArrive(this);
        hideLabel();
        showArrow = false;
        forme = 0;
        selected = false;
    }

    public static void setBackgroundColor(Color c)
    {
        backgroundColor = c;
    }

    public boolean getSelected()
    {
        return selected;
    }

    public void setSelected(boolean b)
    {
        selected = b;
    }

    public boolean getClicked()
    {
        return false;
    }

    public void setClicked(boolean flag)
    {
    }

    public Noeud origine()
    {
        return origine;
    }

    public Noeud extremite()
    {
        return extremite;
    }

    public void setOrigine(Noeud unNoeud)
    {
        origine = unNoeud;
    }

    public void setExtremite(Noeud unNoeud)
    {
        extremite = unNoeud;
    }

    public String labelOrigine()
    {
        return origine().getLabel();
    }

    public String labelExtremite()
    {
        return extremite().getLabel();
    }

    public int widthLabel()
    {
        if(show && getLabel() != null)
            return super.widthLabel();
        else
            return 0;
    }

    public void setShowArrow(boolean b)
    {
        showArrow = b;
    }

    public void calculDimension(FontMetrics fm)
    {
        if(fm != null)
        {
            setWidthLabel(fm.stringWidth(getLabel()));
            setHeightLabel(fm.getHeight());
            widthArrow = fm.getHeight() * 2;
        }
    }

    public void setForme(int i)
    {
        forme = i;
    }

    public int posLien()
    {
        return posLien;
    }

    public void setPosLien(int i)
    {
        posLien = i;
    }

    public boolean lieA(Noeud unNoeud)
    {
        return origine() == unNoeud || extremite() == unNoeud;
    }

    public int supGaucheX()
    {
        return 1 + Math.min(origine().supGaucheX(), extremite().supGaucheX());
    }

    public int supGaucheY()
    {
        return 1 + Math.min(origine().supGaucheY(), extremite().supGaucheY());
    }

    public Point supGauche()
    {
        return new Point(supGaucheX(), supGaucheY());
    }

    public int infDroitX()
    {
        return 1 + Math.max(origine().infDroitX(), extremite().infDroitX());
    }

    public int infDroitY()
    {
        return 1 + Math.max(origine().infDroitY(), extremite().infDroitY());
    }

    public Point infDroit()
    {
        return new Point(infDroitX(), infDroitY());
    }

    public int width()
    {
        return infDroitX() - supGaucheX();
    }

    public int height()
    {
        return infDroitY() - supGaucheY();
    }

    public Rectangle rect()
    {
        if(!showed() && getLabel() != null)
            return new Rectangle(supGaucheX() - widthArrow / 2, supGaucheY() - widthArrow / 2, width() + 1 + widthArrow / 2, height() + 1 + widthArrow / 2);
        else
            return new Rectangle(supGaucheX() - super.widthLabel(), supGaucheY() - heightLabel(), width() + 2 * super.widthLabel(), height() + 2 * heightLabel());
    }

    public void paintArrow(Graphics g, int x1, int y1, int x2, int y2)
    {
        int l = widthArrow;
        double xd1 = (double)x1 - (double)x2;
        double yd1 = (double)y1 - (double)y2;
        double tempo = Math.sqrt(2D) / 2D;
        double d = Math.sqrt(xd1 * xd1 + yd1 * yd1);
        int x = (int)((double)x2 + ((double)l * tempo * (xd1 * Math.cos(angleArrow) - yd1 * Math.sin(angleArrow))) / d);
        int y = (int)((double)y2 + ((double)l * tempo * (yd1 * Math.cos(angleArrow) + xd1 * Math.sin(angleArrow))) / d);
        int x3 = (int)((double)x2 + ((double)l * tempo * (xd1 * Math.cos(angleArrow) + yd1 * Math.sin(angleArrow))) / d);
        int y3 = (int)((double)y2 + ((double)l * tempo * (yd1 * Math.cos(angleArrow) - xd1 * Math.sin(angleArrow))) / d);
        int arrowhead_x[] = {
            x2, x, x3, x2
        };
        int arrowhead_y[] = {
            y2, y, y3, y2
        };
        Color c = g.getColor();
        g.setColor(Color.white);
        g.fillPolygon(arrowhead_x, arrowhead_y, 4);
        g.setColor(c);
        g.drawPolygon(arrowhead_x, arrowhead_y, 4);
    }

    public void paint(Graphics g, int xRel, int yRel)
    {
        if(forme == 1)
            paint1(g, xRel, yRel);
        if(forme == 0)
            paint2(g, xRel, yRel);
    }

    protected int getDecalage2dPoint()
    {
        Rectangle r = origine.rect2();
        if(origine.affAttributs())
            r.add(origine.attributs().rect());
        return origine.supGaucheX() + r.width + 2;
    }

    public void paint1(Graphics g, int xRel, int yRel)
    {
        FontMetrics fm = g.getFontMetrics();
        int x1;
        int y1;
        int x2;
        int y2;
        if(posLien == 0)
        {
            x1 = origine().infDroitX();
            y1 = origine.infDroitY() - origine.height() / 2;
            x2 = extremite().supGaucheX();
            y2 = extremite().supGaucheY() + origine.height() / 2;
        } else
        {
            x1 = origine().supGaucheX() + origine().width() / 2;
            y1 = origine.infDroitY();
            x2 = extremite().supGaucheX() + extremite().width() / 2;
            y2 = extremite().supGaucheY();
        }
        g.setColor(Color.lightGray);
        if(getSelected())
            g.setColor(Composant.shadow_color.brighter());
        g.drawLine(x1 + xRel, y1 + yRel + 1, x2 + xRel, y2 + yRel + 1);
        if(getSelected())
            g.setColor(Composant.shadow_color);
        else
        if(bgColor() == null)
            g.setColor(normal_color);
        else
            g.setColor(bgColor());
        g.drawLine(x1 + xRel, y1 + yRel, x2 + xRel, y2 + yRel);
        if(getSelected())
            g.setColor(Color.black);
        if(show && getLabel() != null)
            g.drawString(getLabel(), (xRel + (x1 + x2) / 2) - super.widthLabel() / 2, (yRel + (y1 + y2) / 2) - heightLabel() / 2);
        if(showArrow)
            paintArrow(g, xRel + x1, yRel + y1, xRel + x2, yRel + y2);
    }

    public void paint2(Graphics g, int xRel, int yRel)
    {
        if(bgColor() == null)
            g.setColor(normal_color);
        else
            g.setColor(bgColor());
        FontMetrics fm = g.getFontMetrics();
        if(getSelected())
            g.setColor(Composant.shadow_color);
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
            if(decalage2dPoint)
                x2 = getDecalage2dPoint();
            else
                x2 = x1 + (extremite().supGaucheX() - x1) / 4;
            y2 = y1;
            x3 = x2;
            y3 = extremite().supGaucheY() + origine.height() / 2;
            x4 = extremite().supGaucheX();
            y4 = y3;
        } else
        {
            x1 = origine().supGaucheX() + origine().rect2().width / 2;
            y1 = origine().infDroitY();
            x2 = x1;
            y2 = y1 + (extremite().supGaucheY() - y1) / 4;
            x3 = extremite().supGaucheX() + extremite.rect2().width / 2;
            y3 = y2;
            x4 = x3;
            y4 = extremite().supGaucheY();
        }
        if(doubleTrait)
        {
            if(getSelected())
                g.setColor(Composant.shadow_color.brighter());
            else
                g.setColor(Color.lightGray);
            g.drawLine(x1 + xRel, y1 + yRel + 1, x2 + xRel, y2 + yRel + 1);
            g.drawLine(x2 + xRel + 1, y2 + yRel, x3 + xRel + 1, y3 + yRel);
            g.drawLine(x3 + xRel, y3 + yRel + 1, x4 + xRel, y4 + yRel + 1);
            if(getSelected())
                g.setColor(Composant.shadow_color);
            else
            if(bgColor() == null)
                g.setColor(normal_color);
            else
                g.setColor(bgColor());
        }
        g.drawLine(x1 + xRel, y1 + yRel, x2 + xRel, y2 + yRel);
        g.drawLine(x2 + xRel, y2 + yRel, x3 + xRel, y3 + yRel);
        g.drawLine(x3 + xRel, y3 + yRel, x4 + xRel, y4 + yRel);
        if(showArrow)
            paintArrow(g, xRel + x3, yRel + y3, xRel + x4, yRel + y4);
        g.setColor(Color.black);
        if(showed() && getLabel() != null)
            if(x3 < x4)
                g.drawString(getLabel(), xRel + x3 + 3, (yRel + y3) - 2);
            else
                g.drawString(getLabel(), (xRel + x3) - super.widthLabel() - 3, (yRel + y3) - 2);
    }

    static 
    {
        backgroundColor = Color.black;
    }
}
