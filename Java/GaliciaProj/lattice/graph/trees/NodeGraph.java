// Decompiled by Jad v1.5.7g. Copyright 2000 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/SiliconValley/Bridge/8617/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi 
// Source File Name:   NodeGraph.java

package lattice.graph.trees;

import java.awt.*;
import java.util.*;
import lattice.graph.utils.Rectangle3D;

// Referenced classes of package lattice.graph.trees:
//            Composant, Noeud, Selectable, AttributsList, 
//            Relation, Attribut, ComposantList

public class NodeGraph extends Composant
    implements Noeud, Selectable
{

    public static Color defaultColor = new Color(10, 210, 240);
    public static Color racineColor = new Color(217, 92, 118);
    public static double angleArrow = 0.39269908169872414D;
    public boolean activeNode;
    protected static int num = 0;
    protected boolean selected;
    protected boolean clicked;
    protected boolean racine;
    protected boolean marque;
    protected boolean marque2;
    protected Vector relationDepart;
    protected Vector relationArrive;
    protected AttributsList attributs;
    protected boolean affAttributs;
    protected boolean visible;
    protected boolean affMenu;
    protected boolean bordered;
    protected Rectangle rect3;
    public int widthArrow;
    public boolean rounded;

    public NodeGraph()
    {
        activeNode = false;
        rect3 = null;
        widthArrow = 12;
        rounded = false;
        setLabel("objet n\241" + String.valueOf(num));
        setPos(0, 0);
        init();
    }

    public NodeGraph(String nom)
    {
        super(nom);
        activeNode = false;
        rect3 = null;
        widthArrow = 12;
        rounded = false;
        setLabel(nom);
        setPos(0, 0);
        init();
    }

    public NodeGraph(String nom, Point position)
    {
        super(nom);
        activeNode = false;
        rect3 = null;
        widthArrow = 12;
        rounded = false;
        setLabel(nom);
        setPos(position);
        init();
    }

    public NodeGraph(Point position)
    {
        activeNode = false;
        rect3 = null;
        widthArrow = 12;
        rounded = false;
        setLabel("objet n\241" + String.valueOf(num));
        setPos(position);
        init();
    }

    public boolean getActiveNode()
    {
        return activeNode;
    }

    public void setActiveNode(boolean b)
    {
        activeNode = b;
    }

    public static void setDefaultColor(Color c)
    {
        defaultColor = c;
    }

    public void setAttributs(AttributsList attributs)
    {
        this.attributs = attributs;
    }

    public static void setRacineColor(Color c)
    {
        racineColor = c;
    }

    protected void init()
    {
        num++;
        labelColor = Color.black;
        bgColor = defaultColor;
        selected = false;
        racine = false;
        marque = false;
        marque2 = false;
        relationDepart = new Vector();
        relationArrive = new Vector();
        attributs = new AttributsList(this);
        affAttributs = false;
        visible = true;
        affMenu = false;
        bordered = true;
    }

    public void initColor()
    {
        labelColor = Color.black;
        bgColor = defaultColor;
    }

    public void addObserver(Observer o)
    {
        super.addObserver(o);
    }

    public void setRacine(boolean b)
    {
        racine = b;
        if(b)
            setBgColor(racineColor);
        else
            setBgColor(defaultColor);
    }

    public boolean getRacine()
    {
        return racine;
    }

    public boolean affMenu()
    {
        return affMenu;
    }

    public void setAffMenu(boolean b)
    {
        affMenu = b;
    }

    public void setBordered(boolean b)
    {
        bordered = b;
    }

    public void setRounded(boolean b)
    {
        rounded = b;
    }

    public void update(Observable o, Object args)
    {
        setChanged();
        notifyObservers(args);
    }

    public void bouge(int dx, int dy)
    {
        x += dx;
        y += dy;
    }

    public void setPosSup(Point p)
    {
        setPos(p.x + width() / 2, p.y + height() / 2);
    }

    public Vector relations()
    {
        Vector rel = new Vector();
        for(int i = 0; i < nbRelationDepart(); i++)
            rel.addElement(relationDepart(i));

        for(int j = 0; j < nbRelationArrive(); j++)
            rel.addElement(relationArrive(j));

        return rel;
    }

    public Vector relationDepart()
    {
        return relationDepart;
    }

    public Vector relationArrive()
    {
        return relationArrive;
    }

    public int maxLargeur()
    {
        int largeur = 0;
        for(int i = 0; i < nbRelationDepart(); i++)
            if(relationDepart(i).widthLabel() > largeur)
                largeur = relationDepart(i).widthLabel();

        return largeur;
    }

    public int largeur(Noeud n)
    {
        Relation r = rechRelationDepart(n);
        if(r != null)
            return r.widthLabel();
        else
            return 0;
    }

    public int maxHauteur()
    {
        int hauteur = 0;
        for(int i = 0; i < nbRelationDepart(); i++)
            if(relationDepart(i).heightLabel() > hauteur)
                hauteur = relationDepart(i).heightLabel();

        return hauteur;
    }

    public Relation relationDepart(int i)
    {
        return (Relation)relationDepart.elementAt(i);
    }

    public Relation relationArrive(int i)
    {
        return (Relation)relationArrive.elementAt(i);
    }

    public void setRelationDepart(int i, Relation rel)
    {
        relationDepart.setElementAt(rel, i);
    }

    public void setRelationArrive(int i, Relation rel)
    {
        relationArrive.setElementAt(rel, i);
    }

    public void addRelationDepart(Relation uneRelation)
    {
        relationDepart.addElement(uneRelation);
    }

    public void addRelationArrive(Relation uneRelation)
    {
        relationArrive.addElement(uneRelation);
    }

    public int nbRelationDepart()
    {
        return relationDepart.size();
    }

    public int nbRelationArrive()
    {
        return relationArrive.size();
    }

    public int rechIndiceRelDepart(Noeud n)
    {
        for(int i = 0; i < nbRelationDepart(); i++)
            if(relationDepart(i).extremite() == n)
                return i;

        return -1;
    }

    public Relation rechRelationDepart(Noeud n)
    {
        for(int i = 0; i < nbRelationDepart(); i++)
            if(relationDepart(i).extremite() == n)
                return relationDepart(i);

        return null;
    }

    public Relation rechRelationDepart(String s)
    {
        for(int i = 0; i < nbRelationDepart(); i++)
            if(relationDepart(i).extremite().getLabel().equals(s))
                return relationDepart(i);

        return null;
    }

    public int rechIndiceRelArrive(Noeud n)
    {
        for(int i = 0; i < nbRelationArrive(); i++)
            if(relationArrive(i).origine() == n)
                return i;

        return -1;
    }

    public Relation rechRelationArrive(Noeud n)
    {
        for(int i = 0; i < nbRelationArrive(); i++)
            if(relationArrive(i).origine() == n)
                return relationArrive(i);

        return null;
    }

    public void removeRelation(Relation uneRelation)
    {
        removeRelationDepart(uneRelation);
        removeRelationArrive(uneRelation);
    }

    public void removeAttribut(String s)
    {
        attributs.remove(s);
    }

    public void removeRelations()
    {
        for(int i = 0; i < nbRelationArrive(); i++)
        {
            Relation r = relationArrive(i);
            r.origine().removeRelationDepart(r);
        }

        for(int j = 0; j < nbRelationDepart(); j++)
        {
            Relation r = relationDepart(j);
            r.extremite().removeRelationArrive(r);
        }

    }

    public void removeRelationDepart(Relation uneRelation)
    {
        for(int i = 0; i < nbRelationDepart();)
            if(uneRelation == relationDepart(i))
                relationDepart.removeElementAt(i);
            else
                i++;

    }

    public void removeRelationDepart(Noeud unNoeud)
    {
        for(int i = 0; i < nbRelationDepart();)
            if(relationDepart(i).extremite() == unNoeud)
                removeRelationDepart(relationDepart(i));
            else
                i++;

    }

    public void removeRelationArrive(Relation uneRelation)
    {
        for(int i = 0; i < nbRelationArrive();)
            if(uneRelation == relationArrive(i))
                relationArrive.removeElementAt(i);
            else
                i++;

    }

    public void removeRelationArrive(Noeud unNoeud)
    {
        for(int i = 0; i < nbRelationArrive();)
            if(relationArrive(i).origine() == unNoeud)
                removeRelationArrive(relationArrive(i));
            else
                i++;

    }

    public Vector peres()
    {
        Vector fils = new Vector();
        for(int i = 0; i < nbRelationArrive(); i++)
            fils.addElement(relationArrive(i).origine());

        return fils;
    }

    public Vector fils()
    {
        Vector fils = new Vector();
        for(int i = 0; i < nbRelationDepart(); i++)
            fils.addElement(relationDepart(i).extremite());

        return fils;
    }

    public Noeud fils(int i)
    {
        return relationDepart(i).extremite();
    }

    public int nbFils()
    {
        return nbRelationDepart();
    }

    public boolean isFilsVisible()
    {
        for(int i = 0; i < nbRelationDepart(); i++)
            if(!relationDepart(i).extremite().visible())
                return false;

        return true;
    }

    public boolean racine()
    {
        return racine;
    }

    public void setMarque(boolean b)
    {
        marque = b;
    }

    public boolean getMarque()
    {
        return marque;
    }

    public void setMarque2(boolean b)
    {
        marque2 = b;
    }

    public boolean getMarque2()
    {
        return marque2;
    }

    public boolean affAttributs()
    {
        return affAttributs;
    }

    public boolean visible()
    {
        return visible;
    }

    public void setVisible(boolean v)
    {
        visible = v;
    }

    public void setAffAttributs(boolean b)
    {
        affAttributs = b;
    }

    public AttributsList attributs()
    {
        return attributs;
    }

    public Attribut attribut(String nomAtt)
    {
        return attributs.elementName(nomAtt);
    }

    public Attribut attribut(int i)
    {
        return attributs.attribut(i);
    }

    public Attribut rechAttribut(String label)
    {
        for(int i = 0; i < nbAtt(); i++)
            if(attribut(i).getLabel().equals(label))
                return attribut(i);

        return null;
    }

    public Attribut rechAttSuivant(String label)
    {
        for(int i = 0; i < nbAtt(); i++)
            if(attribut(i).getLabel().equals(label))
                if(i < nbAtt() - 1)
                    return attribut(i + 1);
                else
                    return attribut(0);

        return null;
    }

    public Attribut rechAttPrecedent(String label)
    {
        for(int i = 0; i < nbAtt(); i++)
            if(attribut(i).getLabel().equals(label))
                if(i > 0)
                    return attribut(i - 1);
                else
                    return attribut(nbAtt() - 1);

        return null;
    }

    public void calculDimensionObj(FontMetrics fm)
    {
        setWidthLabel(fm.stringWidth(getLabel()));
        setHeightLabel(fm.getHeight());
        setWidth(widthLabel() + 3 * fm.charWidth(' '));
        setHeight(2 * fm.getMaxDescent() + fm.getMaxAscent() + 2);
        if(activeNode)
            calculDimensionMButton(fm);
    }

    public void calculDimensionMButton(FontMetrics fm)
    {
        if(nbFils() > 0)
        {
            widthArrow = fm.getHeight();
            setWidth(width() + widthArrow);
            rect3 = new Rectangle(infDroitX() - widthArrow, supGaucheY(), widthArrow, height());
        }
    }

    public void calculDimension(FontMetrics fmObj, FontMetrics fmAtt, FontMetrics fmRel)
    {
        calculDimensionObj(fmObj);
        attributs.calculDimension(fmAtt);
        calculDimensionRel(fmRel);
    }

    public void calculDimensionAtt(FontMetrics fm)
    {
        attributs.calculDimension(fm);
    }

    public void calculDimensionRel(FontMetrics fm)
    {
        Vector rel = relations();
        for(int i = 0; i < rel.size(); i++)
            ((Relation)rel.elementAt(i)).calculDimension(fm);

    }

    public Rectangle rect()
    {
        Rectangle r = rect2();
        if(affAttributs())
            r.add(attributs.rect());
        return r;
    }

    public Rectangle rect2()
    {
    	//设定弹出方框的大小
        return new Rectangle(supGaucheX(), supGaucheY(), width() + 2, height() + 2);
    }

    public Rectangle rect3()
    {
        return rect3;
    }

    public Rectangle rectRels()
    {
        Rectangle r = rect();
        for(int i = 0; i < nbRelationDepart(); i++)
            r.add(relationDepart(i).rect());

        for(int i = 0; i < nbRelationArrive(); i++)
            r.add(relationArrive(i).rect());

        return r;
    }

    public void setSelected(boolean b)
    {
        selected = b;
    }

    public boolean getSelected()
    {
        return selected;
    }

    public void setClicked(boolean b)
    {
        clicked = b;
    }

    public boolean getClicked()
    {
        return clicked;
    }

    public int supGaucheX()
    {
        return x() - width() / 2;
    }

    public int supGaucheY()
    {
        return y() - height() / 2;
    }

    public Point supGauche()
    {
        return new Point(supGaucheX(), supGaucheY());
    }

    public int infDroitX()
    {
        return x() + width() / 2;
    }

    public int infDroitY()
    {
        return y() + height() / 2;
    }

    public Point infDroit()
    {
        return new Point(infDroitX(), infDroitY());
    }

    public void addAttribut(Attribut unAttribut)
    {
        attributs.add(unAttribut);
    }

    public Attribut createAttribute()
    {
        Attribut a = new Attribut(this);
        addAttribut(a);
        return a;
    }

    public void updateAttribut(int index, Attribut newAttribut)
    {
        if(index != -1)
            attributs.setElementAt(newAttribut, index);
    }

    public int find(Attribut att)
    {
        return attributs.find(att.getLabel());
    }

    public int nbAtt()
    {
        return attributs.nbElement();
    }

    public void setBgColor(Color c)
    {
        if(!racine())
            super.setBgColor(c);
        else
            super.setBgColor(racineColor);
    }

    public void setBgColorAtt(Color c)
    {
        attributs.setBgColor(c);
    }

    public void setLabelColorAtt(Color c)
    {
        attributs.setLabelColor(c);
    }

    public Attribut dansAttributs(int x, int y)
    {
        return attributs.dansAttributs(x, y);
    }

    public Attribut selectAttributs(int x, int y)
    {
        return attributs.dansAttributs(x, y);
    }

    public void changeFormeRelation(int forme)
    {
        for(int i = 0; i < nbRelationDepart(); i++)
            relationDepart(i).setForme(forme);

        for(int j = 0; j < nbRelationArrive(); j++)
            relationArrive(j).setForme(forme);

    }

    public void showLabelRelations(boolean b)
    {
        for(int i = 0; i < nbRelationDepart(); i++)
            if(b)
                relationDepart(i).showLabel();
            else
                relationDepart(i).hideLabel();

        for(int j = 0; j < nbRelationArrive(); j++)
            if(b)
                relationArrive(j).showLabel();
            else
                relationArrive(j).hideLabel();

    }

    public void setPosLien(int pos)
    {
        for(int j = 0; j < nbRelationArrive(); j++)
            relationArrive(j).setPosLien(pos);

    }

    public void setShowArrow(boolean b)
    {
        for(int j = 0; j < nbRelationArrive(); j++)
            relationArrive(j).setShowArrow(b);

    }

    public void paintShadow(Graphics g, int xRel, int yRel)
    {
        if(visible)
        {
            g.fillRect((supGaucheX() + xRel + Composant.shadowSize.width) - 4, (supGaucheY() + yRel + Composant.shadowSize.height) - 4, width() + 4, height() + 4);
            if(!bordered)
            {
                g.setColor(Color.white);
                g.fillRect((supGaucheX() + xRel + Composant.shadowSize.width) - 2, (supGaucheY() + yRel + Composant.shadowSize.height) - 2, width(), height());
            }
        }
    }

    public void paintShadowRounded(Graphics g, int xRel, int yRel)
    {
        if(visible)
            g.fillOval((supGaucheX() + xRel + Composant.shadowSize.width) - 4, (supGaucheY() + yRel + Composant.shadowSize.height) - 5, width() + 4, height() + 4);
    }

    public void paint(Graphics g, int xRel, int yRel)
    {
        if(visible)
        {
            FontMetrics fm = g.getFontMetrics();
            if(racine())
            {
                g.setColor(Composant.cible_color);
                if(!rounded)
                    paintShadow(g, xRel, yRel);
                else
                    paintShadowRounded(g, xRel, yRel);
                paintAttShadow(g, xRel, yRel);
            }
            if(getSelected())
            {
                g.setColor(Composant.shadow_color);
                if(!rounded)
                    paintShadow(g, xRel, yRel);
                else
                    paintShadowRounded(g, xRel, yRel);
                paintAttShadow(g, xRel, yRel);
            }
            if(bordered)
            {
                Rectangle3D r = new Rectangle3D(bgColor(), supGaucheX() + xRel, supGaucheY() + yRel, width(), height());
                if(getClicked())
                    r.setDrawingMode(0);
                else
                    r.setDrawingMode(1);
                r.paint(g);
            }
            if(rounded)
            {
                g.setColor(bgColor());
                g.fillOval(supGaucheX() + xRel, (supGaucheY() + yRel) - 1, width(), height());
                g.setColor(bgColor().darker());
                g.drawOval(supGaucheX() + xRel, (supGaucheY() + yRel) - 1, width(), height());
            }
            g.setColor(labelColor);
            paintLabel(g, fm, xRel, yRel);
            if(activeNode)
                paintActive(g, xRel, yRel);
        }
    }

    public void paintAttShadow(Graphics g, int xRel, int yRel)
    {
        if(affAttributs && nbAtt() > 0)
            attributs.paintShadow(g, xRel, yRel);
    }

    public void paintAtt(Graphics g, int xRel, int yRel)
    {
        if(affAttributs && nbAtt() > 0)
            attributs.paint(g, xRel, yRel);
    }

    protected void paintLabel(Graphics g, FontMetrics fm, int xRel, int yRel)
    {
        if(getClicked())
            g.drawString(getLabel(), xRel + 1 + supGaucheX() + (3 * fm.charWidth(' ')) / 2, (yRel + infDroitY()) - fm.getMaxDescent() - fm.getMaxDescent() / 2);
        else
            g.drawString(getLabel(), xRel + supGaucheX() + (3 * fm.charWidth(' ')) / 2, (yRel + infDroitY()) - 1 - fm.getMaxDescent() - fm.getMaxDescent() / 2);
    }

    public void paintRelations(Graphics g, int xRel, int yRel)
    {
        for(int j = 0; j < nbRelationDepart(); j++)
            if(visible() && relationDepart(j).extremite().visible())
                relationDepart(j).paint(g, xRel, yRel);

    }

    public void paintActive(Graphics g, int xRel, int yRel)
    {
        if(nbFils() > 0)
            if(isFilsVisible())
            {
                if(getClicked())
                    paintArrow(g, x() + xRel + 1, y() + yRel + 1, ((infDroitX() + 1) - widthArrow / 4) + xRel + 1, y() + yRel + 1);
                else
                    paintArrow(g, x() + xRel, y() + yRel, (infDroitX() - widthArrow / 4) + xRel, y() + yRel);
            } else
            if(getClicked())
                paintArrow(g, ((infDroitX() + 1) - widthArrow / 2) + xRel, (infDroitY() - height()) + 1 + yRel, (infDroitX() - widthArrow / 2) + xRel + 1, (infDroitY() - widthArrow / 4) + yRel + 1);
            else
                paintArrow(g, (infDroitX() - widthArrow / 2) + xRel, (infDroitY() - height()) + yRel, (infDroitX() - widthArrow / 2) + xRel, (infDroitY() - widthArrow / 4) + yRel);
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
        g.fillPolygon(arrowhead_x, arrowhead_y, 4);
    }

    public Object clone()
    {
        NodeGraph newNode = new NodeGraph(getLabel(), pos());
        newNode.setActiveNode(getActiveNode());
        AttributsList newList = (AttributsList)attributs.clone(newNode);
        newList.setNoeud(newNode);
        newNode.attributs = newList;
        newNode.affAttributs = affAttributs;
        return newNode;
    }

}
