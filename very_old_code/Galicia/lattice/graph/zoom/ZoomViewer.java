// Decompiled by Jad v1.5.7g. Copyright 2000 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/SiliconValley/Bridge/8617/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi 
// Source File Name:   ZoomViewer.java

package lattice.graph.zoom;

import java.awt.*;
import java.awt.event.*;
import java.io.PrintStream;
import javax.swing.JComponent;
import javax.swing.JPanel;

// Referenced classes of package lattice.graph.zoom:
//            ZoomInterface, ZoomEditorInterface

public class ZoomViewer extends JPanel
    implements Runnable, MouseMotionListener, MouseListener
{

    protected ZoomEditorInterface editor;
    protected ZoomInterface canvas;
    protected Image offscreen;
    protected Image iGrand;
    protected Image iRed;
    protected Graphics offgraphics;
    protected Rectangle rect;
    protected Rectangle rTotale;
    protected Rectangle rRefresh;
    protected float factor;
    protected boolean drag;
    protected boolean qualite;
    protected Thread monThread;
    protected boolean active;
    protected boolean zoomPainted;
    int debordX;
    int debordY;
    int bordX;
    int bordY;
    int decX;
    int decY;
    boolean deplacement;
    int decalX;
    int decalY;

    public ZoomViewer(ZoomInterface ic)
    {
        drag = false;
        qualite = false;
        active = false;
        zoomPainted = false;
        debordX = 0;
        debordY = 0;
        bordX = 5;
        bordY = 5;
        decX = 0;
        decY = 0;
        deplacement = false;
        decalX = 0;
        decalY = 0;
        canvas = ic;
        ic.setZoomViewer(this);
        init();
    }

    public ZoomViewer(ZoomInterface ic, ZoomEditorInterface editor)
    {
        drag = false;
        qualite = false;
        active = false;
        zoomPainted = false;
        debordX = 0;
        debordY = 0;
        bordX = 5;
        bordY = 5;
        decX = 0;
        decY = 0;
        deplacement = false;
        decalX = 0;
        decalY = 0;
        this.editor = editor;
        canvas = ic;
        ic.setZoomViewer(this);
        init();
    }

    protected void init()
    {
        setOpaque(true);
        setBackground(canvas.getBackground());
        addMouseListener(this);
        addMouseMotionListener(this);
    }

    public boolean getQualite()
    {
        return qualite;
    }

    public void clearRect()
    {
        rect = null;
    }

    public boolean qualite()
    {
        return qualite;
    }

    public void setQualite(boolean b)
    {
        qualite = b;
    }

    public float getFactor()
    {
        return factor;
    }

    public void setFactor(float f)
    {
        factor = f;
    }

    public void run()
    {
        if(qualite)
            refreshZoom();
    }

    public void refresh()
    {
        if(rTotale != null)
        {
            if(monThread != null && monThread.isAlive())
                monThread.stop();
            creerNewThread();
        }
    }

    protected void creerNewThread()
    {
        monThread = new Thread(this);
        monThread.setPriority(monThread.getPriority() - 1);
        monThread.start();
    }

    public void refresh1()
    {
        rTotale = canvas.dimension();
        if(rTotale != null && rTotale.width != 0 && rTotale.height != 0)
        {
            factor = calculFactor(rTotale);
            debordX = Math.round(((float)getSize().width * factor - (float)rTotale.width) / 2.0F);
            rTotale.x -= debordX;
            rTotale.width += 2 * debordX;
            debordY = Math.round(((float)getSize().height * factor - (float)rTotale.height) / 2.0F);
            rTotale.y -= debordY;
            rTotale.height += 2 * debordY;
            if(editor != null)
                editor.updateZoomFactor(factor);
            rect = null;
            setRect(new Rectangle(debordX - decalX, debordY - decalY, canvas.getSize().width, canvas.getSize().height));
            decX = 0;
            decY = 0;
            refresh();
        }
    }

    protected void refreshZoom(Graphics g)
    {
        initOffScreen(getSize().width, getSize().height);
        offgraphics.setColor(canvas.getBackground());
        offgraphics.fillRect(0, 0, getSize().width, getSize().height);
        canvas.paintOffscreen(offgraphics, rTotale.x + decX, rTotale.y + decY, factor);
    }

    protected void refreshZoom()
    {
        int nbW = rTotale.width / 200 + 1;
        int nbH = rTotale.height / 200 + 1;
        Dimension d = new Dimension((int)((double)rTotale.width / (double)nbW), (int)((double)rTotale.height / (double)nbH));
        initOffScreen(getSize().width, getSize().height);
        iGrand = createImage(d.width, d.height);
        Graphics g = iGrand.getGraphics();
        Rectangle r2 = new Rectangle(0, 0, d.width, d.height);
        int largeur = (int)((float)d.width / factor);
        int hauteur = (int)((float)d.height / factor);
        for(int i = 0; i < nbH; i++)
        {
            for(int j = 0; j <= nbW; j++)
            {
                setZoomPainted(false);
                r2.x = rTotale.x + d.width * j;
                r2.y = rTotale.y + d.height * i;
                g.setColor(canvas.getBackground());
                g.fillRect(0, 0, d.width, d.height);
                canvas.paintOffscreen(g, r2);
                if(qualite)
                    iRed = iGrand.getScaledInstance(largeur, hauteur, 4);
                else
                    iRed = iGrand.getScaledInstance(largeur, hauteur, 1);
                offgraphics.drawImage(iRed, j * largeur, i * hauteur, this);
            }

        }

    }

    protected void initOffScreen(int w, int h)
    {
        if(offscreen == null || offscreen.getWidth(this) != w || offscreen.getHeight(this) != h)
        {
            if(offscreen != null)
                offscreen.flush();
            offscreen = createImage(w, h);
            offgraphics = offscreen.getGraphics();
            Graphics g = getGraphics();
            g.clearRect(0, 0, getSize().width, getSize().height);
        }
    }

    protected float calculFactor(Rectangle r)
    {
        float factorW = (float)r.width / (float)(getSize().width - bordX);
        float factorH = (float)r.height / (float)(getSize().height - bordY);
        float factor = factorH;
        if(factorW > factorH)
            factor = factorW;
        return factor;
    }

    public void setBounds(int x, int y, int w, int h)
    {
        super.setBounds(x, y, w, h);
        refresh1();
    }

    public void setRect(Rectangle rect)
    {
        if(this.rect != null)
        {
            this.rect.x += rect.x;
            this.rect.y += rect.y;
            decX -= rect.x;
            decY -= rect.y;
            this.rect.width = rect.width;
            this.rect.height = rect.height;
            deplacement = true;
        } else
        if(rTotale != null)
            this.rect = new Rectangle(-rTotale.x - decalX, -rTotale.y - decalY, rect.width, rect.height);
        repaint();
    }

    protected Rectangle calculRectReduit(Rectangle r)
    {
        return new Rectangle(Math.round((float)r.x / factor), Math.round((float)r.y / factor), Math.round((float)r.width / factor), Math.round((float)r.height / factor));
    }

    protected boolean dansRect(int x, int y)
    {
        Rectangle r = new Rectangle((int)((float)rect.x / factor), (int)((float)rect.y / factor), (int)((float)rect.width / factor), (int)((float)rect.height / factor));
        return r.contains(x, y);
    }

    public void update(Graphics g)
    {
        paint(g);
    }

    public void setZoomPainted(boolean b)
    {
        zoomPainted = b;
    }

    public void dispose()
    {
        if(monThread != null && monThread.isAlive())
            monThread.stop();
        monThread = null;
        if(offscreen != null)
            offscreen.flush();
        if(iGrand != null)
            iGrand.flush();
    }

    public void clearGraphics()
    {
        offscreen = null;
        if(monThread != null && monThread.isAlive())
            monThread.stop();
        repaint();
    }

    public void mouseDragged(MouseEvent e)
    {
        if(drag)
        {
            int posX = (int)((float)e.getX() * factor);
            int posY = (int)((float)e.getY() * factor);
            canvas.deplacer(-posX, -posY);
        }
    }

    public void mouseMoved(MouseEvent mouseevent)
    {
    }

    public void mouseEntered(MouseEvent mouseevent)
    {
    }

    public void mouseExited(MouseEvent mouseevent)
    {
    }

    public void mousePressed(MouseEvent e)
    {
        int posX = (int)((float)e.getX() * factor);
        int posY = (int)((float)e.getY() * factor);
        if(dansRect(e.getX(), e.getY()))
        {
            drag = true;
            setCursor(new Cursor(13));
            canvas.setPosX(-posX);
            canvas.setPosY(-posY);
            canvas.dragMode();
        } else
        {
            canvas.recentre(posX - rect.x, posY - rect.y);
        }
    }

    public void mouseReleased(MouseEvent e)
    {
        drag = false;
        setCursor(new Cursor(0));
        canvas.mouseUp(e.getX(), e.getY());
    }

    public void mouseClicked(MouseEvent mouseevent)
    {
    }

    public void paint(Graphics g)
    {
        if(!qualite)
            if(!deplacement)
                refreshZoom(g);
            else
                deplacement = false;
        try
        {
            g.clearRect(0, 0, getSize().width, getSize().height);
            if(offscreen != null)
                g.drawImage(offscreen, 0, 0, this);
        }
        catch(Exception e)
        {
            System.out.println("Pas assez de m?moire !");
        }
        if(rect != null)
        {
            Rectangle rect2 = rect;
            rect2.x = rect.x;
            rect2.y = rect.y;
            rect2.width = rect.width;
            rect2.height = rect.height;
            Rectangle rReduit = calculRectReduit(rect2);
            g.setColor(canvas.getBackground());
            g.setColor(Color.red);
            g.drawRect(rReduit.x, rReduit.y, rReduit.width, rReduit.height);
        }
    }

    public Dimension getPreferredSize()
    {
        return new Dimension(150, 140);
    }

    public void setDecalX(int dx)
    {
        decalX = dx;
    }

    public void setDecalY(int dy)
    {
        decalY = dy;
    }
}
