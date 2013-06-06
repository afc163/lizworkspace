// Decompiled by Jad v1.5.7g. Copyright 2000 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/SiliconValley/Bridge/8617/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi 
// Source File Name:   Noeud.java

package lattice.graph.trees;

import java.awt.*;
import java.util.Observer;
import java.util.Vector;

// Referenced classes of package lattice.graph.trees:
//            Attribut, Relation, AttributsList

public interface Noeud
    extends Observer
{

    public abstract void addObserver(Observer observer);

    public abstract String getLabel();

    public abstract void setLabel(String s);

    public abstract boolean visible();

    public abstract void calculDimension(FontMetrics fontmetrics, FontMetrics fontmetrics1, FontMetrics fontmetrics2);

    public abstract void calculDimensionObj(FontMetrics fontmetrics);

    public abstract void calculDimensionAtt(FontMetrics fontmetrics);

    public abstract void calculDimensionRel(FontMetrics fontmetrics);

    public abstract void setRacine(boolean flag);

    public abstract int nbFils();

    public abstract Noeud fils(int i);

    public abstract Vector fils();

    public abstract boolean isFilsVisible();

    public abstract boolean sourisDans(int i, int j);

    public abstract void setMarque(boolean flag);

    public abstract boolean getMarque();

    public abstract void setMarque2(boolean flag);

    public abstract boolean getMarque2();

    public abstract void bouge(int i, int j);

    public abstract Rectangle rect();

    public abstract Rectangle rectRels();

    public abstract int height();

    public abstract int width();

    public abstract int infDroitY();

    public abstract int infDroitX();

    public abstract int supGaucheX();

    public abstract int supGaucheY();

    public abstract boolean getSelected();

    public abstract void setSelected(boolean flag);

    public abstract void initColor();

    public abstract void showLabelRelations(boolean flag);

    public abstract void setVisible(boolean flag);

    public abstract void setActiveNode(boolean flag);

    public abstract void setAffAttributs(boolean flag);

    public abstract void setBgColor(Color color);

    public abstract void setBgColorAtt(Color color);

    public abstract Color bgColor();

    public abstract void setLabelColor(Color color);

    public abstract void setLabelColorAtt(Color color);

    public abstract int x();

    public abstract int y();

    public abstract double xd();

    public abstract double yd();

    public abstract int z();

    public abstract double zd();

    public abstract boolean dansRect(Rectangle rectangle);

    public abstract int nbRelationArrive();

    public abstract void setPosSup(Point point);

    public abstract void setPos(Point point);

    public abstract int maxLargeur();

    public abstract int maxHauteur();

    public abstract int largeur(Noeud noeud);

    public abstract Rectangle rect2();

    public abstract Rectangle rect3();

    public abstract Attribut dansAttributs(int i, int j);

    public abstract int find(Attribut attribut);

    public abstract String getInfo();

    public abstract void addRelationDepart(Relation relation);

    public abstract void addRelationArrive(Relation relation);

    public abstract Relation rechRelationArrive(Noeud noeud);

    public abstract void changeFormeRelation(int i);

    public abstract Vector relationArrive();

    public abstract Relation relationArrive(int i);

    public abstract Vector relationDepart();

    public abstract Relation relationDepart(int i);

    public abstract void removeRelationDepart(Relation relation);

    public abstract void removeRelationArrive(Relation relation);

    public abstract void removeRelations();

    public abstract void removeAttribut(String s);

    public abstract void setShowArrow(boolean flag);

    public abstract void setPosLien(int i);

    public abstract void addAttribut(Attribut attribut);

    public abstract Attribut createAttribute();

    public abstract boolean affAttributs();

    public abstract Attribut rechAttribut(String s);

    public abstract Attribut rechAttSuivant(String s);

    public abstract Attribut rechAttPrecedent(String s);

    public abstract AttributsList attributs();

    public abstract void paint(Graphics g, int i, int j);

    public abstract void paintShadow(Graphics g, int i, int j);

    public abstract void paintAtt(Graphics g, int i, int j);

    public abstract void paintAttShadow(Graphics g, int i, int j);

    public abstract void paintRelations(Graphics g, int i, int j);

    public abstract Object clone();
}
