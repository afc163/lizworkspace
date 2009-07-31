// Decompiled by Jad v1.5.7g. Copyright 2000 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/SiliconValley/Bridge/8617/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi 
// Source File Name:   ActionGraphViewer.java

package lattice.graph.trees;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.io.PrintStream;
import java.util.Vector;
import lattice.graph.dialog.DialogListener;
import lattice.graph.dialog.DialogOuiNon;
import lattice.graph.trees.event.ActionGraphViewerAdapter;
import lattice.graph.trees.event.FormatKeyListener;
import lattice.graph.zoom.ZoomInterface;
import lattice.graph.zoom.ZoomViewer;

// Referenced classes of package lattice.graph.trees:
//            GraphViewer, Selectable, Noeud, Attribut, 
//            NodeGraph, Relation, RelationSelect, IkbsPopupMenu, 
//            ComponentPopUp, AttributPopUp

public class ActionGraphViewer extends GraphViewer
    implements Runnable, MouseMotionListener, ZoomInterface, DialogListener
{

    public static int NORMAL = 1;
    public static int RAPIDE = 2;
    public static int TRES_RAPIDE = 3;
    public static boolean anim = true;
    public static double FLUIDITE = 0.050000000000000003D;
    public static int MAX_ITER = 20;
    public static int TIME_SLEEP = 5;
    protected int xDepart;
    protected int yDepart;
    protected Thread monThread;
    protected boolean edition;
    protected IkbsPopupMenu canvasPopUp;
    protected ComponentPopUp componentPopUp;
    protected AttributPopUp attributPopUp;
    protected int index;
    protected Selectable selected[];
    protected int vitesse;
    protected boolean attributClic;
    protected boolean attributDrag;
    protected boolean copyMode;

    public ActionGraphViewer()
    {
        edition = true;
        selected = new Selectable[10];
        vitesse = RAPIDE;
        attributClic = false;
        attributDrag = false;
        copyMode = false;
    }

    public void init()
    {
        super.init();
        addKeyListener(new FormatKeyListener(this));
        addMouseListener(new ActionGraphViewerAdapter(this));
    }

    public Dimension getPreferredSize()
    {
        return getParent().getSize();
    }

    public AttributPopUp getAttributPopUp()
    {
        return attributPopUp;
    }

    public ComponentPopUp getComponentPopUp()
    {
        return componentPopUp;
    }

    public IkbsPopupMenu getCanvasPopUp()
    {
        return canvasPopUp;
    }

    public boolean getEdition()
    {
        return edition;
    }

    public void setEdition(boolean b)
    {
        edition = b;
    }

    public void setCopyMode(boolean b)
    {
        copyMode = b;
    }

    public void setIndex(int index)
    {
        this.index = index;
    }

    public void setAttributSelect(Attribut att)
    {
        selected[0] = att;
    }

    public String nomEdition()
    {
        String s;
        if(edition)
            s = "Editeur de mod?le : ";
        else
            s = "Visualiseur de mod?le : ";
        return s;
    }

    public void affecteRacine(Noeud n)
    {
        if(noeudRacine != null && noeudRacine == n)
            setRacine(null);
        else
            setRacine(n);
        refreshNoeud(n);
    }

    public void setCible(Attribut attribut)
    {
    }

    public boolean isAttribute(int index, int x, int y)
    {
        return noeuds(index).rect2().contains(x, y);
    }

    public Attribut attributAt(int index, int x, int y)
    {
        return noeuds(index).dansAttributs(x, y);
    }

    public void setAttributCible(Attribut a)
    {
        setCible(a);
        repaint();
    }

    public void setAttributCible()
    {
        if(selected[0] != null)
            setAttributCible((Attribut)selected[0]);
    }

    public void editer(Attribut a, int index)
    {
        editer(a, noeuds(index));
    }

    public void editerAttribut()
    {
        editer((Attribut)selected[0], index);
    }

    public void createRelation(int index)
    {
        if(getEdition())
        {
            super.relationMode = true;
            setNoeudSelect(noeuds(index));
        }
    }

    public void createNode(int x, int y)
    {
        if(getEdition())
        {
            Noeud n = creerNoeud(x, y);
            ajouterNoeud(n);
            refreshNoeud(n);
        }
    }

    public void createNode()
    {
        if(getEdition())
            createNode(pos.x, pos.y);
    }

    public NodeGraph createNode(String nom, int x, int y)
    {
        if(getEdition())
        {
            Noeud n = creerNoeud(nom, x, y);
            ajouterNoeud(n);
            refreshNoeud(n);
            return (NodeGraph)n;
        } else
        {
            return null;
        }
    }

    public Attribut createAttribute(Noeud n)
    {
        if(getEdition())
            return n.createAttribute();
        else
            return null;
    }

    public Attribut createAttribute(int i)
    {
        return createAttribute(noeuds(i));
    }

    public void selectNode(int index)
    {
        selectNode(noeuds(index));
    }

    public void selectNode(Noeud unNoeud)
    {
        setNoeudSelect(unNoeud);
        putLastPosition(noeudSelect);
        refreshNoeud(noeudSelect);
        noeudClicked(noeudSelect);
    }

    public void doSelected(Selectable s)
    {
        if(selected[0] != s)
        {
            if(selected[0] != null)
                selected[0].setSelected(false);
            if(s != null)
                s.setSelected(true);
            selected[0] = s;
        }
    }

    public void selectNode2(int index)
    {
        if(selected[0] != null)
            selected[0].setSelected(false);
        select = true;
        noeuds(index).setSelected(true);
        setNoeudSelect(noeuds(index));
        putLastPosition(noeudSelect);
        refreshNoeud(noeudSelect);
        noeudClicked(noeudSelect);
    }

    public void moveTree(int index)
    {
        moveTree(noeuds(index));
    }

    public void moveTree(Noeud n)
    {
        if(edition)
        {
            select = true;
            shiftPressed = true;
            n.setSelected(true);
            setNoeudSelect(n);
            refreshNoeud(noeudSelect);
        }
    }

    public void editNode()
    {
        editNode(index);
    }

    public void editNode(int index)
    {
        selectNode(index);
        editer(noeudSelect);
    }

    public void eraseNode(int index)
    {
        if(getEdition())
        {
            effacerNoeud(index);
            rect = null;
            repaint();
        } else
        {
            System.out.println("Impossible d'effacer en mode visualisation");
        }
    }

    public void eraseNode()
    {
        eraseNode(index);
    }

    public void eraseAttribut()
    {
        ((Attribut)selected[0]).getPere().removeAttribut(((Attribut)selected[0]).getLabel());
    }

    public void eraseTree(int index)
    {
        if(getEdition())
        {
            effacerNoeudsRec(noeuds(index));
            rect = null;
            repaint();
        }
    }

    public void eraseTree()
    {
        eraseTree(index);
    }

    public void eraseAll()
    {
        noeuds = new Vector();
        rect = null;
        formatter = null;
        repaint();
    }

    public void rootOnNode(int index)
    {
        if(getEdition())
            affecteRacine(noeuds(index));
    }

    public void rootOnNode()
    {
        rootOnNode(index);
    }

    public Noeud copyNode()
    {
        return copyNode(index);
    }

    public Noeud copyNode(int index)
    {
        return copyNode(noeuds(index));
    }

    public void changeAffAttributs()
    {
        Noeud n = noeuds(index);
        changeAffAttributs(n, !n.affAttributs());
    }

    public void affSousArbre()
    {
        Noeud n = noeuds(index);
        demarquer();
        affSousArbre(n, !n.isFilsVisible());
    }

    public Noeud copyNode(Noeud n)
    {
        if(edition)
        {
            Noeud copy = (Noeud)n.clone();
            copy.bouge(3, 3);
            calculDimension(copy);
            ajouterNoeud(copy);
            selectNode(copy);
            return copy;
        } else
        {
            return n;
        }
    }

    public Noeud copyTree()
    {
        return copyTree(index);
    }

    public Noeud copyTree(int index)
    {
        return copyTree(noeuds(index));
    }

    public Noeud copyTree(Noeud unNoeud)
    {
        if(edition)
        {
            Noeud copy = copyNode(unNoeud);
            Vector fils = unNoeud.fils();
            for(int i = 0; i < fils.size(); i++)
            {
                Noeud copyFils = copyTree((Noeud)fils.elementAt(i));
                initRelation(new Relation(copy, copyFils));
            }

            selectNode(copy);
            return copy;
        } else
        {
            return unNoeud;
        }
    }

    public void dragMode()
    {
        setCursor(new Cursor(13));
        drag = true;
    }

    public void noeudClicked(Noeud noeudSelect)
    {
        ((Selectable)noeudSelect).setClicked(true);
        select = true;
        setInfo(noeudSelect.getInfo());
        repaint();
    }

    public void attributClicked(Attribut a, Noeud noeudSelect)
    {
        a.setClicked(true);
        setInfo(a.getInfo());
        repaint();
    }

    public void attributClicked(Attribut attribut, int index)
    {
        attributClicked(attribut, noeuds(index));
        attributClic = true;
        setInfo(attribut.getInfo());
    }

    public void attributMoved(int x, int y)
    {
        attributDrag = false;
        eraseNode();
        int index = rechNoeud(x, y);
        if(index != -1 && noeuds(index).find((Attribut)selected[0]) == -1)
        {
            copyAttribut(noeuds(index));
        } else
        {
            DialogOuiNon d = new DialogOuiNon(getFrame(), "Effacer un attribut ", true, this, "Voulez-vous effacer l'attribut " + selected[0].getLabel() + " ?", "annuler", "effacer");
            d.show();
        }
    }

    public void valider()
    {
    }

    public void annuler()
    {
        copyMode = false;
        copyAttribut(((Attribut)selected[0]).getPere());
    }

    protected void copyAttribut(Noeud n)
    {
        if(copyMode)
        {
            Attribut newAtt = (Attribut)((Attribut)selected[0]).clone();
            newAtt.initNewPere(n);
        } else
        {
            ((Attribut)selected[0]).initNewPere(n);
        }
    }

    public void copyAttribut()
    {
        copyMode = true;
        copyAttribut(((Attribut)selected[0]).getPere());
        copyMode = false;
    }

    public void deplacerAttribut(int x, int y)
    {
        if(getEdition() && !attributDrag)
        {
            createNode(((Attribut)selected[0]).getLabel(), x, y);
            attributDrag = true;
            index = rechNoeud(x, y);
            selectNode2(index);
            noeuds(index).setBgColor(Color.white);
            noeuds(index).setLabelColor(((Attribut)selected[0]).getColorType());
            if(!copyMode)
                eraseAttribut();
        }
    }

    public void deplacer2(int x, int y)
    {
        int decX = (x - pos.x) * vitesse;
        int decY = (y - pos.y) * vitesse;
        setX(decX);
        setY(decY);
        pos.x = x;
        pos.y = y;
        if(zoomCanvas != null)
            zoomCanvas.setRect(new Rectangle(-getX(), -getY(), getSize().width, getSize().height));
        repaint();
    }

    public void mouseDragged(MouseEvent e)
    {
        int x = e.getX();
        int y = e.getY();
        if(attributClic && (Math.abs(x - pos.x) > 5 || Math.abs(y - pos.y) > 5))
        {
            deplacerAttribut(x, y);
            attributClic = false;
        } else
        if(drag)
            deplacer2(x, y);
        else
        if(getEdition())
            if(super.relationMode)
                relationMode(x, y);
            else
            if(select)
                selectMode(x, y);
    }

    public void mouseMoved(MouseEvent mouseevent)
    {
    }

    protected void relationMode(int x, int y)
    {
        if(getEdition())
            if(relationSelect == null)
            {
                relationSelect = new RelationSelect(noeudSelect, x, y);
                initRelation(relationSelect);
            } else
            {
                setRect(relationSelect.rect());
                relationSelect.setPos(x, y);
                addRect(relationSelect.rect());
                repaint();
            }
    }

    protected void selectMode(int x, int y)
    {
        if(getEdition())
        {
            int dx = x - pos.x;
            int dy = y - pos.y;
            pos.x += dx;
            pos.y += dy;
            if(shiftPressed)
            {
                bougeNoeudRec(noeudSelect, dx, dy);
                repaint();
            } else
            {
                bougeNoeud(noeudSelect, dx, dy);
            }
        }
    }

    public boolean mouseUp(int x, int y)
    {
        attributClic = false;
        shiftPressed = false;
        if(drag)
        {
            drag = false;
            pos.x = 0;
            pos.y = 0;
            setX(0);
            setY(0);
        }
        if(select)
        {
            ((Selectable)noeudSelect).setClicked(false);
            refreshNoeud(noeudSelect);
            select = false;
        } else
        if(selected[0] != null && (selected[0] instanceof Attribut))
        {
            selected[0].setClicked(false);
            Noeud n = ((Attribut)selected[0]).getPere();
            if(n != null)
                refreshNoeud(n);
        }
        if(super.relationMode)
        {
            int index = rechNoeud(x, y);
            if(index != -1)
            {
                if(noeudSelect == noeuds(index))
                {
                    noeudSelect.createAttribute();
                } else
                {
                    Relation r = new Relation(noeudSelect, noeuds(index));
                    initRelation(r);
                    refreshNoeud(noeudSelect);
                }
            } else
            {
                repaint();
            }
        }
        if(attributDrag)
            attributMoved(x, y);
        copyMode = false;
        super.relationMode = false;
        relationSelect = null;
        setCursor(new Cursor(0));
        return false;
    }

    public void recentreAuto(int x, int y)
    {
        int xFinal = getSize().width / 2 - xDepart;
        int yFinal = getSize().height / 2 - yDepart;
        deplacerAuto(xFinal, yFinal);
    }

    public void deplacerAuto(int x, int y)
    {
        double accumX = 0.0D;
        double accumY = 0.0D;
        int xFinal = x;
        int yFinal = y;
        double distance = Math.sqrt(xFinal * xFinal + yFinal * yFinal);
        int nb_iter = (int)(distance * FLUIDITE);
        if(nb_iter > MAX_ITER)
            nb_iter = MAX_ITER;
        if(nb_iter == 0)
        {
            repaint();
        } else
        {
            for(int i = nb_iter; i > 0; i--)
            {
                double decX = (double)xFinal / (double)nb_iter;
                double decY = (double)yFinal / (double)nb_iter;
                waitTime(i, nb_iter);
                accumX += decX - (double)(int)decX;
                accumY += decY - (double)(int)decY;
                if(accumX >= 1.0D)
                {
                    accumX--;
                    decX++;
                }
                if(accumX <= -1D)
                {
                    accumX++;
                    decX--;
                }
                if(accumY >= 1.0D)
                {
                    accumY--;
                    decY++;
                }
                if(accumY <= -1D)
                {
                    accumY++;
                    decY--;
                }
                while(getX() != 0 || getY() != 0) 
                    try
                    {
                        Thread.sleep(TIME_SLEEP);
                    }
                    catch(Exception exception) { }
                setX((int)decX);
                setY((int)decY);
                if(zoomCanvas != null)
                    zoomCanvas.setRect(new Rectangle(-getX(), -getY(), getSize().width, getSize().height));
                repaint();
            }

        }
    }

    public void setBounds(int x, int y, int w, int h)
    {
        super.setBounds(x, y, w, h);
        if(zoomCanvas != null)
        {
            zoomCanvas.setRect(new Rectangle(-getX(), -getY(), w, h));
            zoomCanvas.repaint();
        }
    }

    public void waitTime(int i, int nb_iter)
    {
        try
        {
            if(i < nb_iter / 4)
                Thread.sleep(TIME_SLEEP + (nb_iter / i) * 4);
            else
            if(i > (3 * nb_iter) / 4)
                Thread.sleep(TIME_SLEEP + (nb_iter / (nb_iter - i)) * 4);
        }
        catch(Exception exception) { }
    }

    public void recentre(Noeud unNoeud)
    {
        doSelected((Selectable)unNoeud);
        super.recentre(unNoeud);
    }

    public void recentre(int x, int y)
    {
        if(anim)
        {
            xDepart = x;
            yDepart = y;
            monThread = new Thread(this);
            monThread.setPriority(4);
            monThread.start();
        } else
        {
            super.recentre(x, y);
        }
    }

    public void recentreAuto()
    {
        recentreAuto(xDepart, yDepart);
    }

    public void run()
    {
        recentreAuto(xDepart, yDepart);
    }

}
