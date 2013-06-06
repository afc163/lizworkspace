// Decompiled by Jad v1.5.7g. Copyright 2000 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/SiliconValley/Bridge/8617/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi 
// Source File Name:   ImageButton.java

package lattice.graph.imageButton;

import java.awt.*;
import java.awt.event.*;
import lattice.graph.utils.*;

public class ImageButton extends Component
    implements ChoixComponent, MouseListener, ThumbReceiver
{

    static final int META = 20;
    static final int CTRL = 18;
    static final int ALT = 24;
    static final int SHIFT = 17;
    static final int CTRLSHIFT = 19;
    static final int NORMAL = 16;
    static final int SHIFTALT = 25;
    public static String FONT = "Geneva";
    ActionListener actionListener;
    private boolean modal;
    private boolean selected;
    private boolean isDown;
    private int borderW;
    private boolean affLabel;
    public static final int LEFT = 0;
    public static final int TOP = 1;
    public static final int RIGHT = 2;
    public static final int BOTTOM = 3;
    private String label;
    private String info;
    private String infoSelect;
    private Image img;
    private Image inactive_img;
    private Image shadowed_img;
    private float imageScale;
    private InfoListener ibm;
    private boolean fShowBorder;
    private boolean fDrawPushedIn;
    private int pos;
    private int padding;
    private int gap;
    private int ix;
    private int iy;
    private int iw;
    private int ih;
    private Color color;
    public int choix;

    public ImageButton(Image image, ActionListener listener, int choix)
    {
        this(image, ((String) (null)), listener, choix);
    }

    public ImageButton(Image image, boolean modal, ActionListener listener, int choix)
    {
        this(image, ((String) (null)), listener, choix);
        this.modal = modal;
        if(listener instanceof InfoListener)
            ibm = (InfoListener)listener;
    }

    public ImageButton(Image image, String label, boolean modal, ActionListener listener, int choix)
    {
        this(image, label, listener, choix);
        this.modal = modal;
        addMouseListener(this);
        if(listener instanceof InfoListener)
            ibm = (InfoListener)listener;
    }

    public ImageButton(Image image, String label, ActionListener listener, int choix)
    {
        modal = false;
        selected = false;
        isDown = false;
        borderW = Rectangle3D.borderWidthOfMode(0);
        affLabel = true;
        imageScale = 1.0F;
        fShowBorder = true;
        fDrawPushedIn = true;
        pos = 2;
        padding = 2;
        gap = 3;
        ix = 0;
        iy = 0;
        iw = 0;
        ih = 0;
        color = new Color(210, 210, 210);
        this.label = label;
        img = image;
        initFont(FONT);
        this.choix = choix;
        enableEvents(16L);
        addActionListener(listener);
        addMouseListener(this);
        if(listener instanceof InfoListener)
            ibm = (InfoListener)listener;
    }

    public void initFont(String nomFont)
    {
        super.setFont(new Font(nomFont, 0, 10));
        setSize(getPreferredSize());
    }

    public InfoListener getListener()
    {
        return ibm;
    }

    public int getChoix()
    {
        return choix;
    }

    public void setModal(boolean b)
    {
        modal = b;
    }

    public void setSelected(boolean b)
    {
        if(selected != b)
        {
            selected = b;
            repaint();
        }
    }

    public boolean selected()
    {
        return selected;
    }

    public void setInfo(String i)
    {
        info = i;
    }

    public String getInfo()
    {
        return info;
    }

    public void setInfoSelect(String i)
    {
        infoSelect = i;
    }

    public String getInfoSelect()
    {
        return infoSelect;
    }

    public void setFont(Font f)
    {
        super.setFont(f);
        setSize(getPreferredSize());
    }

    public void setShowBorder(boolean b)
    {
        fShowBorder = b;
        if(b)
            borderW = Rectangle3D.borderWidthOfMode(0);
        else
            borderW = 0;
        setSize(getPreferredSize());
    }

    public void setDrawPushedIn(boolean b)
    {
        fDrawPushedIn = b;
    }

    public int getLabelPosition()
    {
        return pos;
    }

    public void setLabelPosition(int a)
    {
        if(a != 0 && a != 1 && a != 2 && a != 3)
        {
            throw new IllegalArgumentException();
        } else
        {
            pos = a;
            setSize(getPreferredSize());
            return;
        }
    }

    public String getLabel()
    {
        return label;
    }

    public void setLabel(String l)
    {
        label = l;
        setSize(getPreferredSize());
        repaint();
    }

    public void setPadding(int p)
    {
        padding = p;
        setSize(getPreferredSize());
        repaint();
    }

    public int getPadding()
    {
        return padding;
    }

    public void setImageLabelGap(int g)
    {
        gap = g;
        setSize(getPreferredSize());
        repaint();
    }

    public int getImageLabelGap()
    {
        return gap;
    }

    public void setImage(Image i)
    {
        if(i == null || img == null)
        {
            img = i;
            setSize(getPreferredSize());
        } else
        if(i.getWidth(this) != img.getWidth(this) || i.getHeight(this) != img.getHeight(this))
        {
            img = i;
            setSize(getPreferredSize());
        } else
        {
            img = i;
        }
        repaintImage();
    }

    public boolean getAffLabel()
    {
        return affLabel;
    }

    public void setAffLabel(boolean t)
    {
        affLabel = t;
    }

    public void setImageScale(double f)
    {
        setImageScale((float)f);
    }

    public void setImageScale(float pct)
    {
        if(pct <= 0.0F)
            pct = 1.0F;
        imageScale = pct;
        setSize(getPreferredSize());
    }

    public void setEnabled(boolean b)
    {
        if(!isEnabled())
        {
            isDown = false;
            super.setEnabled(b);
            repaint();
        }
    }

    public boolean selection()
    {
        return (selected || isDown) && fDrawPushedIn;
    }

    protected void repaintImage()
    {
        if(img != null)
        {
            Graphics g = getGraphics();
            if(g == null)
                return;
            if(imageScale == 1.0F)
                g.drawImage(isEnabled() ? img : inactive_img, ix, iy, this);
            else
                g.drawImage(isEnabled() ? img : inactive_img, ix, iy, iw, ih, this);
        }
    }

    public synchronized void paint(Graphics g)
    {
        g.setFont(getFont());
        int w = getSize().width;
        int h = getSize().height;
        if(fShowBorder)
        {
            Rectangle3D r = new Rectangle3D(color, 0, 0, w, h);
            if(selection())
                r.setDrawingMode(0);
            else
                r.setDrawingMode(1);
            r.paint(g);
        } else
        if(selection() && modal)
        {
            int xt = 1;
            int yt = 1;
            int wt = w - 1;
            int ht = h - 1;
            g.setColor(Color.yellow);
            g.drawLine(xt, yt, (xt + wt) - 1, yt);
            g.drawLine(xt, yt, xt, (yt + ht) - 1);
            g.drawLine(xt, (yt + ht) - 1, (xt + wt) - 1, (yt + ht) - 1);
            g.drawLine((xt + wt) - 1, yt, (xt + wt) - 1, (yt + ht) - 1);
        }
        int o = padding + borderW + (selection() ? 1 : 0);
        iw = 0;
        ih = 0;
        int _gap = 0;
        if(img != null)
        {
            iw = (int)((float)img.getWidth(this) * imageScale);
            ih = (int)((float)img.getHeight(this) * imageScale);
            _gap = gap;
        }
        FontMetrics fm = null;
        ix = (w - iw) / 2 + (selection() ? 1 : 0);
        iy = (h - ih) / 2 + (selection() ? 1 : 0);
        if(label != null && affLabel)
        {
            fm = g.getFontMetrics();
            if(pos == 2)
                ix = o;
            else
            if(pos == 0)
                ix = o + _gap + fm.stringWidth(label);
            if(pos == 1)
                iy = o + _gap + fm.getAscent();
            else
            if(pos == 3)
                iy -= fm.getAscent() / 2;
        }
        repaintImage();
        if(label != null && fm != null && affLabel)
        {
            int x;
            if(pos == 0)
                x = o;
            else
            if(pos == 2)
                x = o + _gap + iw;
            else
                x = (w - fm.stringWidth(label)) / 2 + (selection() ? 1 : 0);
            int y;
            if(pos == 1)
                y = o + fm.getAscent();
            else
            if(pos == 3)
                y = h - fm.getAscent() / 2;
            else
                y = ((h - (h - fm.getAscent()) / 2) + (selection() ? 1 : 0)) - 1;
            g.setColor(getForeground());
            if(color.getGreen() + color.getRed() + color.getBlue() < 400)
                g.setColor(Color.white);
            if(!isEnabled())
            {
                g.setColor(Color.white);
                g.drawString(label, x + 1, y + 1);
                g.setColor(getBackground().darker());
            }
            g.drawString(label, x, y);
        }
    }

    public Dimension getPreferredSize()
    {
        Font f = getFont();
        FontMetrics fm = null;
        if(f != null && label != null && affLabel)
            fm = getToolkit().getFontMetrics(f);
        int iw = 0;
        int ih = 0;
        int _gap = 0;
        if(img != null)
        {
            iw = (int)((float)img.getWidth(this) * imageScale);
            ih = (int)((float)img.getHeight(this) * imageScale);
            _gap = gap;
        }
        int w = iw + 2 * (padding + borderW);
        int h = ih + 2 * (padding + borderW);
        if(fm != null)
            if(pos == 0 || pos == 2)
            {
                w += _gap + fm.stringWidth(label);
                h = Math.max(h, fm.getAscent() + 2 * (padding + borderW));
            } else
            {
                h += _gap + fm.getAscent();
                w = Math.max(w, fm.stringWidth(label) + 2 * (padding + borderW));
            }
        return new Dimension(w, h);
    }

    public void addActionListener(ActionListener listener)
    {
        actionListener = AWTEventMulticaster.add(actionListener, listener);
        enableEvents(16L);
    }

    public void removeActionListener(ActionListener listener)
    {
        actionListener = AWTEventMulticaster.remove(actionListener, listener);
    }

    public void setCouleurFond(Color c)
    {
        color = c;
    }

    public void setChoix(int c)
    {
        choix = c;
    }

    protected void afficherInfo()
    {
        if(!selected)
        {
            if(getInfo() != null && getListener() != null)
                getListener().setInfo(getInfo());
        } else
        if(getInfoSelect() != null && getListener() != null)
            getListener().setInfo(getInfoSelect());
    }

    public void mouseEntered(MouseEvent e)
    {
        afficherInfo();
    }

    public void mouseExited(MouseEvent e)
    {
        if(getInfo() != null && getListener() != null)
            getListener().removeInfo();
        if(isEnabled() && isDown)
        {
            isDown = false;
            repaint();
        }
    }

    public void mousePressed(MouseEvent e)
    {
        if(isEnabled())
        {
            isDown = true;
            repaint();
        }
    }

    public void mouseReleased(MouseEvent e)
    {
        if(isEnabled() && isDown)
        {
            isDown = false;
            if(e.getClickCount() >= 2)
            {
                if(modal)
                    selected = true;
                performAction(2);
            } else
            {
                if(modal)
                {
                    if(selected)
                        selected = false;
                    else
                        selected = true;
                } else
                {
                    selected = false;
                }
                if(actionListener != null)
                    switch(e.getModifiers())
                    {
                    case 20: // '\024'
                        performAction(4);
                        break;

                    case 18: // '\022'
                        performAction(2);
                        break;

                    case 24: // '\030'
                        performAction(8);
                        break;

                    case 17: // '\021'
                        performAction(1);
                        break;

                    case 19: // '\023'
                        performAction(5);
                        break;

                    case 21: // '\025'
                    case 22: // '\026'
                    case 23: // '\027'
                    default:
                        performAction(0);
                        break;
                    }
            }
            repaint();
        }
    }

    public void performAction(int action)
    {
        actionListener.actionPerformed(new ActionEvent(this, 1001, label, action));
    }

    public void mouseClicked(MouseEvent e)
    {
        afficherInfo();
    }

    public void imageReady(Image imgRed, String nomFich, int index, boolean invalide)
    {
        if(nomFich != null)
            label = nomFich;
        else
            label = "";
        if(!invalide)
        {
            if(imgRed != null)
                img = imgRed;
            repaint();
        }
    }

    public void imageReady2(Image image, String s, int i, boolean flag)
    {
    }

}
