// Decompiled by Jad v1.5.7g. Copyright 2000 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/SiliconValley/Bridge/8617/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi 
// Source File Name:   GraphViewer.java

package lattice.graph.trees;

import java.awt.*;
import java.io.PrintStream;
import java.util.*;
import javax.swing.JComponent;
import javax.swing.JPanel;
import lattice.graph.trees.formatter.Formatter;
import lattice.graph.trees.formatter.FormatterGD;
import lattice.graph.trees.formatter.FormatterGD2;
import lattice.graph.trees.formatter.FormatterGD3;
import lattice.graph.trees.formatter.FormatterGD4;
import lattice.graph.trees.formatter.FormatterGD5;
import lattice.graph.trees.formatter.FormatterHB;
import lattice.graph.utils.GetFrame;
import lattice.graph.utils.Information;
import lattice.graph.zoom.ZoomViewer;

// Referenced classes of package lattice.graph.trees:
//            Noeud, NodeGraph, Relation, Composant, 
//            RelationSelect, Attribut

public class GraphViewer extends JPanel
    implements GetFrame, Observer
{

    public static final int FORMATTER_GD = 0;
    public static final int FORMATTER_GD2 = 1;
    public static final int FORMATTER_GD3 = 2;
    public static final int FORMATTER_GD4 = 3;
    public static final int FORMATTER_GD5 = 4;
    public static final int FORMATTER_HB = 5;
    public static final int NORMAL = 0;
    public static final int OFFSCREEN = 1;
    public static Image backgroundPicture = null;
    public static final int BG_REPEAT = 0;
    public static final int BG_CENTER = 1;
    public static final int BG_FITTED = 2;
    public static final int BG_LEFT = 3;
    public static final int BG_NONE = 4;
    public static final int BG_BOTTOM = 5;
    public static final int BG_GRILLE = 6;
    protected int x;
    protected int y;
    protected Point pos;
    public Vector noeuds;
    protected Noeud noeudSelect;
    public Noeud noeudRacine;
    protected RelationSelect relationSelect;
    protected boolean select;
    protected boolean relationMode;
    protected boolean shiftPressed;
    protected int zoom;
    protected int formeRelation;
    protected String policeObj;
    protected String policeAtt;
    protected String policeRel;
    protected int styleObj;
    protected int styleAtt;
    protected int styleRel;
    protected Font fontObj;
    protected Font fontAtt;
    protected Font fontRel;
    protected Rectangle rect;
    protected int bgAlignment;
    protected boolean drag;
    protected boolean painted;
    protected boolean shadow;
    protected boolean paintInfo;
    protected boolean active;
    protected boolean showArrow;
    protected boolean affAttributs;
    protected boolean showLabelRelation;
    protected int posLien;
    protected Formatter formatter;
    protected ZoomViewer zoomCanvas;
    protected Information info;

    public GraphViewer()
    {
        noeuds = new Vector();
        policeObj = "Geneva";
        policeAtt = "Geneva";
        policeRel = "Geneva";
        styleObj = 0;
        styleAtt = 0;
        styleRel = 2;
        bgAlignment = 4;
        drag = false;
        painted = true;
        shadow = false;
        paintInfo = true;
        active = true;
        showArrow = false;
        posLien = 0;
        info = new Information("", 0, 0, Color.white, Color.black, false);
        init();
    }

    public GraphViewer(Vector n)
    {
        noeuds = new Vector();
        policeObj = "Geneva";
        policeAtt = "Geneva";
        policeRel = "Geneva";
        styleObj = 0;
        styleAtt = 0;
        styleRel = 2;
        bgAlignment = 4;
        drag = false;
        painted = true;
        shadow = false;
        paintInfo = true;
        active = true;
        showArrow = false;
        posLien = 0;
        info = new Information("", 0, 0, Color.white, Color.black, false);
        init();
        setNoeuds(n);
    }

    public void init()
    {
        formatter = new FormatterGD4(noeuds);
        setX(0);
        setY(0);
        setZoom(9);
        initFont(getZoom());
        pos = new Point(0, 0);
        select = false;
        relationMode = false;
        setBackground(Color.white);
        noeudRacine = null;
        shiftPressed = false;
        affAttributs = false;
        showArrow = false;
        drag = false;
        showLabelRelation = false;
        formeRelation = 0;
    }

    public void setPosClic(int x, int y)
    {
        pos.x = x;
        pos.y = y;
    }

    public int getX()
    {
        return x;
    }

    public void setX(int x)
    {
        this.x = x;
    }

    public int getY()
    {
        return y;
    }

    public void setY(int y)
    {
        this.y = y;
    }

    public Rectangle rect()
    {
        return rect;
    }

    public void setRect(Rectangle r)
    {
        rect = r;
    }

    public void addRect(Rectangle r)
    {
        if(rect != null)
            rect.add(r);
        else
            setRect(r);
    }

    public void setZoom(int zoom)
    {
        this.zoom = zoom;
        if(formatter != null)
            formatter.setZoom(zoom);
    }

    public int getZoom()
    {
        return zoom;
    }

    public int getPosX()
    {
        return pos.x;
    }

    public int getPosY()
    {
        return pos.y;
    }

    public void setPosX(int x)
    {
        pos.x = x;
    }

    public void setPosY(int y)
    {
        pos.y = y;
    }

    public Frame getFrame()
    {
        return ((GetFrame)getParent()).getFrame();
    }

    public void setZoomViewer(ZoomViewer zc)
    {
        zoomCanvas = zc;
    }

    public ZoomViewer zoomCanvas()
    {
        return zoomCanvas;
    }

    public void setInfo(String s)
    {
        info.setInfo(s);
    }

    public String getInfo()
    {
        return info.getInfo();
    }

    public void setNoeudSelect(Noeud s)
    {
        noeudSelect = s;
    }

    public Noeud getNoeudSelect()
    {
        return noeudSelect;
    }

    protected int nbNoeuds()
    {
        return noeuds.size();
    }

    protected Vector noeuds()
    {
        return noeuds;
    }

    protected void setNoeuds(Vector n)
    {
        noeuds = n;
    }

    public Noeud noeuds(int i)
    {
        return (Noeud)noeuds.elementAt(i);
    }

    public boolean getShowArrow()
    {
        return showArrow;
    }

    public void setShowArrow(boolean b)
    {
        showArrow = b;
        for(int i = 0; i < noeuds().size(); i++)
            noeuds(i).setShowArrow(b);

        repaint();
    }

    public int ajouterNoeud(Noeud unNoeud)
    {
        noeuds.addElement(unNoeud);
        unNoeud.addObserver((Observer)this);
        return noeuds.size();
    }

    public Noeud creerNoeud(int x, int y)
    {
        Noeud unNoeud = new NodeGraph(new Point(x, y));
        calculDimension(unNoeud);
        unNoeud.setActiveNode(active);
        return unNoeud;
    }

    public Noeud creerNoeud(String s, int x, int y)
    {
        Noeud unNoeud = new NodeGraph(s, new Point(x, y));
        calculDimension(unNoeud);
        unNoeud.setActiveNode(active);
        return unNoeud;
    }

    public Noeud creerNoeud(String s)
    {
        int rech = rechNoeud(s);
        if(rech == -1)
        {
            Noeud unNoeud = creerNoeud(s, 30, 20);
            ajouterNoeud(unNoeud);
            return unNoeud;
        } else
        {
            return noeuds(rech);
        }
    }

    public void editer(Noeud noeud)
    {
    }

    public void editer(Attribut attribut, Noeud noeud)
    {
    }

    public void calculDimension(Noeud n)
    {
        n.calculDimension(getFontMetrics(new Font(policeObj, styleObj, getZoom())), getFontMetrics(new Font(policeAtt, styleAtt, getZoom())), getFontMetrics(new Font(policeRel, styleRel, getZoom())));
    }

    public void calculDimensionObj(Noeud n)
    {
        n.calculDimensionObj(getFontMetrics(new Font(policeObj, styleObj, getZoom())));
    }

    public void calculDimensionAtt(Noeud n)
    {
        n.calculDimensionAtt(getFontMetrics(fontAtt));
    }

    public void calculDimensionRel(Noeud n)
    {
        n.calculDimensionRel(getFontMetrics(fontRel));
    }

    public void calculDimension(Relation r)
    {
        r.calculDimension(getFontMetrics(fontRel));
    }

    public void reCalculDimension()
    {
        for(int i = 0; i < noeuds.size(); i++)
        {
            if(fontObj == null)
                System.out.println("fontObj est null");
            if(fontAtt == null)
                System.out.println("fontAtt est null");
            if(fontRel == null)
                System.out.println("fontRel est null");
            noeuds(i).calculDimension(getFontMetrics(fontObj), getFontMetrics(fontAtt), getFontMetrics(fontRel));
        }

    }

    public Noeud noeudRacine()
    {
        return noeudRacine;
    }

    public void setRacine(Noeud unNoeud)
    {
        if(unNoeud != null)
            unNoeud.setRacine(true);
        if(noeudRacine != null)
            noeudRacine.setRacine(false);
        noeudRacine = unNoeud;
    }

    public Noeud creerNoeudRacine(String s)
    {
        int rech = rechNoeud(s);
        if(rech == -1)
        {
            Noeud r = creerNoeud(s, getBounds().x / 2, getBounds().y / 2);
            setRacine(r);
            ajouterNoeud(r);
            return r;
        } else
        {
            return noeuds(rech);
        }
    }

    public void initRelation(Relation r)
    {
        r.setForme(formeRelation);
        if(showLabelRelation)
            r.showLabel();
        else
            r.hideLabel();
        r.setPosLien(posLien);
        r.setShowArrow(showArrow);
        calculDimension(r);
    }

    public int indexOf(Noeud unNoeud)
    {
        for(int i = 0; i < noeuds.size(); i++)
            if(noeuds(i) == unNoeud)
                return i;

        return 0;
    }

    public void effacerNoeud(int index)
    {
        Noeud unNoeud = noeuds(index);
        unNoeud.removeRelations();
        noeuds.removeElementAt(index);
    }

    public void effacerNoeud(Noeud unNoeud)
    {
        effacerNoeud(indexOf(unNoeud));
    }

    public void effacerNoeudsRec(Noeud unNoeud)
    {
        Vector fils = unNoeud.fils();
        for(Enumeration e = fils.elements(); e.hasMoreElements(); effacerNoeudsRec((Noeud)e.nextElement()));
        effacerNoeud(unNoeud);
    }

    public int rechNoeud(int x, int y)
    {
        for(int i = noeuds.size() - 1; i >= 0; i--)
            if(noeuds(i).sourisDans(x, y))   //判断选中的是什么节点,根据节点编号来
                return i;

        return -1;
    }

    public int rechNoeud(String s)
    {
        for(int i = 0; i < noeuds.size(); i++)
            if(s.compareTo(noeuds(i).getLabel()) == 0)
                return i;

        return -1;
    }

    public void bougeNoeudRec(Noeud unNoeud, int dx, int dy)
    {
        demarquer2();
        bougeNoeudRecs(unNoeud, dx, dy);
    }

    public void bougeNoeudRecs(Noeud unNoeud, int dx, int dy)
    {
        Vector fils = unNoeud.fils();
        if(!unNoeud.getMarque2())
        {
            unNoeud.setMarque2(true);
            unNoeud.bouge(dx, dy);
            for(int i = 0; i < fils.size(); i++)
                bougeNoeudRecs((Noeud)fils.elementAt(i), dx, dy);

        }
    }

    public void bougeNoeud(Noeud unNoeud, int dx, int dy)
    {
        setRect(unNoeud.rectRels());
        unNoeud.bouge(dx, dy);
        addRect(unNoeud.rectRels());
        repaint();
    }

    public void putLastPosition(Noeud unNoeud)
    {
        int index = indexOf(unNoeud);
        noeuds().removeElementAt(index);
        ajouterNoeud(unNoeud);
    }

    protected void marquer()
    {
        for(int i = 0; i < noeuds.size(); i++)
            noeuds(i).setMarque(true);

    }

    protected void demarquer()
    {
        for(int i = 0; i < noeuds.size(); i++)
            noeuds(i).setMarque(false);

    }

    protected void marquer2()
    {
        for(int i = 0; i < noeuds.size(); i++)
            noeuds(i).setMarque2(true);

    }

    protected void demarquer2()
    {
        for(int i = 0; i < noeuds.size(); i++)
            noeuds(i).setMarque2(false);

    }

    public void changeFormeRelation(int forme)
    {
        formeRelation = forme;
        for(int i = 0; i < noeuds().size(); i++)
            noeuds(i).changeFormeRelation(forme);

        repaint();
    }

    public void activeTextRelations()
    {
        showLabelRelation = true;
        for(int i = 0; i < noeuds.size(); i++)
            noeuds(i).showLabelRelations(true);

        repaint();
    }

    public void desactiveTextRelations()
    {
        showLabelRelation = false;
        for(int i = 0; i < noeuds.size(); i++)
            noeuds(i).showLabelRelations(false);

        repaint();
    }

    public void afficherInfo(boolean b)
    {
        info.setAffInfo(b);
    }

    public void posLienRelations(int pos)
    {
        if(posLien != pos)
        {
            posLien = pos;
            for(int i = 0; i < noeuds.size(); i++)
                noeuds(i).setPosLien(pos);

            repaint();
        }
    }

    public void affSousArbre(Noeud unNoeud, boolean aff)
    {
        Vector fils = unNoeud.fils();
        unNoeud.setMarque(true);
        for(int i = 0; i < fils.size(); i++)
        {
            Noeud unFils = (Noeud)fils.elementAt(i);
            if(!unFils.getMarque())
                affSousArbreRec(unFils, aff);
        }

        formatter3(unNoeud);
    }

    public void affSousArbreRec(Noeud unNoeud, boolean aff)
    {
        unNoeud.setMarque(true);
        unNoeud.setVisible(aff);
        Vector fils = unNoeud.fils();
        for(int i = 0; i < fils.size(); i++)
        {
            Noeud unFils = (Noeud)fils.elementAt(i);
            if(!unFils.getMarque())
                affSousArbreRec(unFils, aff);
        }

    }

    public void affAttributs(boolean aff)
    {
        affAttributs = aff;
        for(int i = 0; i < noeuds.size(); i++)
            noeuds(i).setAffAttributs(aff);

        formatter();
    }

    public void changeAffAttributs(Noeud n, boolean aff)
    {
        n.setAffAttributs(aff);
        formatter2();
    }

    public void changeBgColor(Color c)
    {
        for(int i = 0; i < noeuds.size(); i++)
            noeuds(i).setBgColor(c);

        repaint();
    }

    public void changeBgColorAtt(Color c)
    {
        for(int i = 0; i < noeuds.size(); i++)
            noeuds(i).setBgColorAtt(c);

        repaint();
    }

    public void changeLabelColor(Color c)
    {
        for(int i = 0; i < noeuds.size(); i++)
            noeuds(i).setLabelColor(c);

        repaint();
    }

    public void changeLabelColorAtt(Color c)
    {
        for(int i = 0; i < noeuds.size(); i++)
            noeuds(i).setLabelColorAtt(c);

        repaint();
    }

    public void setPoliceObj(String p)
    {
        policeObj = p;
        fontObj = new Font(policeObj, styleObj, getZoom());
        for(int i = 0; i < noeuds.size(); i++)
            calculDimensionObj(noeuds(i));

        formatter2();
    }

    public void setStyleObj(int p)
    {
        styleObj = p;
        fontObj = new Font(policeObj, styleObj, getZoom());
        for(int i = 0; i < noeuds.size(); i++)
            calculDimensionObj(noeuds(i));

        formatter2();
    }

    public void setPoliceAtt(String p)
    {
        policeAtt = p;
        fontAtt = new Font(policeAtt, styleAtt, getZoom());
        for(int i = 0; i < noeuds.size(); i++)
            calculDimensionAtt(noeuds(i));

        formatter2();
    }

    public void setStyleAtt(int p)
    {
        styleAtt = p;
        fontAtt = new Font(policeAtt, styleAtt, getZoom());
        for(int i = 0; i < noeuds.size(); i++)
            calculDimensionAtt(noeuds(i));

        formatter2();
    }

    public void setPoliceRel(String p)
    {
        policeRel = p;
        fontRel = new Font(policeRel, styleRel, getZoom());
        for(int i = 0; i < noeuds.size(); i++)
            calculDimensionRel(noeuds(i));

        formatter2();
    }

    public void setStyleRel(int p)
    {
        styleRel = p;
        fontRel = new Font(policeRel, styleRel, getZoom());
        for(int i = 0; i < noeuds.size(); i++)
            calculDimensionRel(noeuds(i));

        formatter2();
    }

    public synchronized void ZP()
    {
        if(painted)
        {
            int pas = 1;
            if(zoom > 15)
                pas = 2;
            if(zoom > 20)
                pas = 3;
            if(zoom > 30)
                pas = 5;
            if(zoom > 120)
                zoom = 120;
            else
                zoom += pas;
            initFont(getZoom());
            reCalculDimension();
            if(formatter != null)
                formatter.setZoom(zoom);
            formatter2();
            System.out.println(zoom);
        }
    }

    public synchronized void ZM()
    {
        if(painted)
        {
            int pas = 1;
            if(zoom < 120)
                pas = 5;
            if(zoom < 30)
                pas = 3;
            if(zoom < 20)
                pas = 2;
            if(zoom < 15)
                pas = 1;
            if(zoom < 6)
                zoom = 5;
            else
                zoom -= pas;
            initFont(getZoom());
            reCalculDimension();
            if(formatter != null)
                formatter.setZoom(zoom);
            formatter2();
            System.out.println(zoom);
        }
    }

    int maxWidth()
    {
        int maxWidth = 0;
        for(Enumeration e = noeuds.elements(); e.hasMoreElements();)
        {
            Noeud unNoeud = (Noeud)e.nextElement();
            int w = unNoeud.rect().width + unNoeud.supGaucheX();
            if(w > maxWidth)
                maxWidth = w;
        }

        return maxWidth;
    }

    int maxHeight()
    {
        int maxHeight = 0;
        for(Enumeration e = noeuds.elements(); e.hasMoreElements();)
        {
            Noeud unNoeud = (Noeud)e.nextElement();
            int h = unNoeud.rect().height + unNoeud.supGaucheY();
            if(h > maxHeight)
                maxHeight = h;
        }

        return maxHeight;
    }

    public Rectangle dimension()
    {
        if(noeuds.size() > 0)
        {
            Noeud n = noeuds(0);
            int x1 = n.supGaucheX();
            int y1 = n.supGaucheY();
            int x2 = n.infDroitX() + n.rect().width;
            int y2 = n.infDroitY() + n.rect().height;
            for(Enumeration e = noeuds.elements(); e.hasMoreElements();)
            {
                n = (Noeud)e.nextElement();
                x1 = Math.min(n.supGaucheX(), x1);
                x2 = Math.max(n.infDroitX() + n.rect().width, x2);
                y1 = Math.min(n.supGaucheY(), y1);
                y2 = Math.max(n.infDroitY() + n.rect().height, y2);
            }

            return new Rectangle(x1 - getX(), y1 - getY(), x2 - x1, y2 - y1);
        } else
        {
            return null;
        }
    }

    public void initFont(int zoom)
    {
        fontObj = new Font(policeObj, styleObj, zoom);
        fontAtt = new Font(policeAtt, styleAtt, zoom);
        fontRel = new Font(policeRel, styleRel, zoom);
    }

    public synchronized void paint(Graphics g)
    {
        rect = getBounds();
        paintBackground(g);
        if(zoomCanvas != null && !zoomCanvas.getQualite())
            zoomCanvas.repaint();
        paint(g, rect);
        painted = true;
    }

    public synchronized void paintOffscreen(Graphics g, Rectangle rect)
    {
        initFont(getZoom());
        g.setFont(fontRel);
        for(int i = 0; i < noeuds.size(); i++)
            noeuds(i).paintRelations(g, -rect.x, -rect.y);

        g.setFont(fontObj);
        for(int i = 0; i < noeuds.size(); i++)
        {
            Noeud noeudCourant = noeuds(i);
            if(noeudCourant.dansRect(rect) && noeudCourant.visible())
            {
                g.setFont(fontObj);
                noeudCourant.paint(g, -rect.x, -rect.y);
                g.setFont(fontAtt);
                noeudCourant.paintAtt(g, -rect.x, -rect.y);
                zoomCanvas.setZoomPainted(true);
            }
        }

    }

    public synchronized void paintOffscreen(Graphics g, int x, int y, float factor)
    {
        Noeud n = null;
        Noeud n2 = null;
        Color nodeColor = new Color(160, 160, 160);
        for(int i = 0; i < noeuds.size(); i++)
        {
            n = noeuds(i);
            float decX = (float)(n.x() - x) / factor;
            float decY = (float)(n.y() - y) / factor;
            for(int j = 0; j < n.relationDepart().size(); j++)
            {
                g.setColor(n.relationDepart(j).bgColor().brighter());
                n2 = n.relationDepart(j).extremite();
                float decX2 = (float)(n2.x() - x) / factor;
                float decY2 = (float)(n2.y() - y) / factor;
                g.drawLine(Math.round(decX), Math.round(decY), Math.round(decX2), Math.round(decY2));
            }

            g.setColor(n.bgColor().darker());
            float nodeWidth = (float)n.width() / factor;
            float nodeHeight = (float)n.height() / factor;
            g.fillOval(Math.round(decX - nodeWidth / 2.0F), Math.round(decY - nodeHeight / 2.0F), Math.round(nodeWidth), Math.round(nodeHeight));
        }

    }

    public void paint(Graphics g, Rectangle rect)
    {
        g.setFont(fontRel);
        for(int i = 0; i < noeuds.size(); i++)
            noeuds(i).paintRelations(g, getX(), getY());

        g.setFont(fontObj);
        for(int i = 0; i < noeuds.size(); i++)
        {
            Noeud noeudCourant = noeuds(i);
            noeudCourant.bouge(getX(), getY());
            noeudCourant.paint(g, 0, 0);
            noeudCourant.paintAtt(g, 0, 0);
        }

        if(relationSelect != null)
            relationSelect.paint(g, 0, 0);
        setX(0);
        setY(0);
        this.rect = null;
    }

    public static void setBackgroundPicture(Image img)
    {
        backgroundPicture = img;
    }

    public static Image getBackgroundPicture(Image img)
    {
        return backgroundPicture;
    }

    public void paintBackground(Graphics g)
    {
        Dimension d = getSize();
        g.setColor(getBackground());
        g.setClip(rect.x, rect.y, rect.width, rect.height);
        g.fillRect(rect.x, rect.y, rect.width, rect.height);
        if(backgroundPicture != null)
            switch(bgAlignment)
            {
            case 4: // '\004'
            default:
                break;

            case 3: // '\003'
                g.drawImage(backgroundPicture, 0, 0, this);
                break;

            case 5: // '\005'
                g.drawImage(backgroundPicture, 0, d.height - backgroundPicture.getHeight(this), this);
                break;

            case 0: // '\0'
                for(int x = 0; x < d.width; x += backgroundPicture.getWidth(this))
                {
                    for(int y = 0; y < d.height; y += backgroundPicture.getHeight(this))
                        g.drawImage(backgroundPicture, x, y, this);

                }

                break;

            case 2: // '\002'
                g.drawImage(backgroundPicture, 0, 0, d.width, d.height, this);
                break;

            case 1: // '\001'
                int x = (d.width - backgroundPicture.getWidth(this)) / 2;
                int y = (d.height - backgroundPicture.getHeight(this)) / 2;
                g.drawImage(backgroundPicture, x, y, this);
                break;

            case 6: // '\006'
                g.setColor(Color.lightGray);
                int gapX = zoom * 3;
                int gapY = zoom * 3;
                for(int i = 0; i < d.height; i += gapX)
                    g.drawLine(0, i, d.width, i);

                for(int j = 0; j < d.width; j += gapY)
                    g.drawLine(j, 0, j, d.height);

                break;
            }
        if(info != null && paintInfo)
            info.paint(g, d, new Font(policeObj, styleObj, getZoom()));
    }

    public void setBgAlignment(String salign)
    {
        if(salign.equals("Motif"))
            bgAlignment = 0;
        if(salign.equals("Centrer"))
            bgAlignment = 1;
        if(salign.equals("Adapter"))
            bgAlignment = 2;
        if(salign.equals("Gauche"))
            bgAlignment = 3;
        if(salign.equals("Aucun"))
            bgAlignment = 4;
        if(salign.equals("Bas"))
            bgAlignment = 5;
        if(salign.equals("Grille"))
            bgAlignment = 6;
        repaint();
    }

    public void drawZoom(Graphics g, Dimension d)
    {
        if(drag && zoomCanvas != null)
            zoomCanvas.setRect(new Rectangle(-getX(), -getY(), d.width, d.height));
    }

    public void setRect(Noeud n)
    {
        rect = n.rect();
    }

    public void setRect(Relation r)
    {
        rect = r.rect();
    }

    public void recentre(int x, int y)
    {
        setX(getSize().width / 2 - x);
        setY(getSize().height / 2 - y);
        if(zoomCanvas != null)
            zoomCanvas.setRect(new Rectangle(-getX(), -getY(), getSize().width, getSize().height));
        repaint();
    }

    public void recentre(Noeud unNoeud)
    {
        recentre(unNoeud.x(), unNoeud.y());
    }

    public void deplacer(int x, int y)
    {
        int decX = x - pos.x;
        int decY = y - pos.y;
        setX(decX);
        setY(decY);
        pos.x = x;
        pos.y = y;
        if(zoomCanvas != null)
            zoomCanvas.setRect(new Rectangle(-getX(), -getY(), getSize().width, getSize().height));
        repaint();
    }

    public void rePositionne(int x, int y)
    {
        setX(x);
        setY(y);
        pos.x = x;
        pos.y = y;
        repaint();
    }

    public synchronized void formatter()
    {
        if(formatter == null)
            setFormatter(3);
        if(noeudRacine() != null)
            formatter.formatter(noeudRacine());
        if(zoomCanvas != null)
        {
            zoomCanvas.clearRect();
            zoomCanvas.refresh1();
        }
        repaint();
    }

    public synchronized void formatter2()
    {
        if(painted)
        {
            painted = false;
            if(noeudRacine() != null)
            {
                int index = rechNoeud(getSize().width / 2, getSize().height / 2);
                int xRac;
                int yRac;
                if(index != -1)
                {
                    xRac = noeuds(index).x();
                    yRac = noeuds(index).y();
                } else
                {
                    xRac = noeudRacine().x();
                    yRac = noeudRacine().y();
                }
                formatter.formatter(noeudRacine());
                if(index != -1)
                {
                    setX(xRac - noeuds(index).x());
                    setY(yRac - noeuds(index).y());
                } else
                {
                    setX(xRac - noeudRacine().x());
                    setY(yRac - noeudRacine().y());
                }
            }
            repaint();
        }
    }

    public synchronized void formatter3(Noeud noeudSelect)
    {
        int xOld = noeudSelect.x();
        int yOld = noeudSelect.y();
        formatter();
        rePositionne(xOld - noeudSelect.x(), yOld - noeudSelect.y());
        if(zoomCanvas != null)
        {
            zoomCanvas.clearGraphics();
            zoomCanvas.refresh1();
        }
    }

    public void setFormatter(int i)
    {
        posLienRelations(0);
        switch(i)
        {
        case 0: // '\0'
            formatter = new FormatterGD(noeuds, getBounds());
            break;

        case 1: // '\001'
            formatter = new FormatterGD2(noeuds, getBounds());
            break;

        case 2: // '\002'
            formatter = new FormatterGD3(noeuds, getBounds());
            break;

        case 3: // '\003'
            formatter = new FormatterGD4(noeuds);
            break;

        case 4: // '\004'
            formatter = new FormatterGD5(noeuds, getBounds());
            break;

        case 5: // '\005'
            formatter = new FormatterHB(noeuds);
            posLienRelations(1);
            break;
        }
        formatter();
    }

    public void refreshNoeud(Noeud o)
    {
        setRect(o.rectRels());
        calculDimension(o);
        addRect(o.rectRels());
        repaint();
    }

    public void update(Observable o, Object args)
    {
        if(o instanceof Noeud)
            refreshNoeud((Noeud)o);
    }

}
