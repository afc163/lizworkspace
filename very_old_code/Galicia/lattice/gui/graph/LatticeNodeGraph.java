// Decompiled by Jad v1.5.7g. Copyright 2000 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/SiliconValley/Bridge/8617/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi 
// Source File Name:   LatticeNodeGraph.java

package lattice.gui.graph;

import java.awt.*;
import java.awt.image.IndexColorModel;
import java.awt.image.MemoryImageSource;
import java.util.*;
import lattice.graph.trees.*;
import lattice.gui.graph.magneto.Magnetable;
import lattice.gui.graph.threeD.ScaleableAtom;
import lattice.util.*;

// Referenced classes of package lattice.gui.graph:
//            LatticeRelation, LatticeAttributeGraph

public class LatticeNodeGraph extends NodeGraph
    implements Comparable, Magnetable, ScaleableAtom
{

    public Node latticeNode;
    public int niveau;
    public double tensionX;
    public double tensionY;
    public double tensionZ;
    public boolean isMagnetable;
    public boolean goRight;
    private Color c[];
    public static boolean IS_COLORED = false;
    private static Component applet;
    private static byte data[];
    private static final int R = 40;
    private static final int hx = 15;
    private static final int hy = 15;
    private static final int bgGrey = 192;
    private static int maxr;
    private int Rl;
    private int Gl;
    private int Bl;
    private double Sf;
    private Image balls[];

    public LatticeNodeGraph(Node latticeNode, int x, int y,String title)
    {
        super(latticeNode.toString(), new Point(x, y));
        niveau = -1;
        tensionZ = 4.9406564584124654E-324D;
        isMagnetable = true;
        goRight = true;
        this.latticeNode = latticeNode;
        addIntent();
        addExtent();
        if(title=="Godin incremental Lattice Algorithm")
        	addSlit();
        addGenerator();
    }

    public boolean isMagnetable()
    {
        return isMagnetable && !getSelected();
    }

    public void setIsMagnetable(boolean b)
    {
        isMagnetable = b;
    }

    public Vector getParents()
    {
        return peres();
    }

    public Vector getChildren()
    {
        return fils();
    }

    public int xCoord()
    {
        return x();
    }

    public int yCoord()
    {
        return y();
    }

    public int zCoord()
    {
        return z();
    }

    public void move(int dx, int dy)
    {
        bouge(dx, dy);
    }

    public void move(int dx, int dy, int dz)
    {
        bouge(dx, dy, dz);
    }

    public boolean isSelected()
    {
        return getSelected();
    }

    public int getNiveau()
    {
        return niveau;
    }

    public void setNiveau(int n)
    {
        niveau = n;
    }

    public double repulsion()
    {
        return 1.0D;
    }

    public double tensionX(boolean b)
    {
        if(tensionX != 4.9406564584124654E-324D)
            return tensionX;
        double t = 0.0D;
        double l = 0.0D;
        double lTotale = 0.0D;
        for(int i = 0; i < nbRelationArrive(); i++)
        {
            l += relationArrive(i).origine().xd() - xd();
            lTotale += ((LatticeRelation)relationArrive(i)).longueur();
        }

        if(b)
        {
            for(int j = 0; j < nbRelationDepart(); j++)
            {
                l += relationDepart(j).extremite().xd() - xd();
                lTotale += ((LatticeRelation)relationDepart(j)).longueur();
            }

        }
        if(b)
            tensionX = ((double)(nbRelationDepart() + nbRelationArrive()) * l) / lTotale;
        else
            tensionX = ((double)nbRelationArrive() * l) / lTotale;
        return tensionX;
    }

    public double tensionY(boolean b)
    {
        if(tensionY != 4.9406564584124654E-324D)
            return tensionY;
        double t = 0.0D;
        double l = 0.0D;
        double lTotale = 0.0D;
        for(int i = 0; i < nbRelationArrive(); i++)
        {
            l += relationArrive(i).origine().yd() - yd();
            lTotale += ((LatticeRelation)relationArrive(i)).longueur();
        }

        if(b)
        {
            for(int j = 0; j < nbRelationDepart(); j++)
            {
                l += relationDepart(j).extremite().yd() - yd();
                lTotale += ((LatticeRelation)relationDepart(j)).longueur();
            }

        }
        if(b)
            tensionY = ((double)(nbRelationDepart() + nbRelationArrive()) * l) / lTotale;
        else
            tensionY = ((double)nbRelationArrive() * l) / lTotale;
        return tensionY;
    }

    public double tensionZ(boolean b)
    {
        if(tensionZ != 4.9406564584124654E-324D)
            return tensionZ;
        double t = 0.0D;
        double l = 0.0D;
        double lTotale = 0.0D;
        for(int i = 0; i < nbRelationArrive(); i++)
        {
            l += relationArrive(i).origine().zd() - zd();
            lTotale += ((LatticeRelation)relationArrive(i)).longueur();
        }

        if(b)
        {
            for(int j = 0; j < nbRelationDepart(); j++)
            {
                l += relationDepart(j).extremite().zd() - zd();
                lTotale += ((LatticeRelation)relationDepart(j)).longueur();
            }

        }
        if(b)
            tensionZ = ((double)(nbRelationDepart() + nbRelationArrive()) * l) / lTotale;
        else
            tensionZ = ((double)nbRelationArrive() * l) / lTotale;
        return tensionZ;
    }

    public void bouge(int dx, int dy)
    {
        super.bouge(dx, dy);
        tensionX = 4.9406564584124654E-324D;
        tensionY = 4.9406564584124654E-324D;
    }

    public void bouge(int dx, int dy, int dz)
    {
        x += dx;
        y += dy;
        z += dz;
        tensionX = 4.9406564584124654E-324D;
        tensionY = 4.9406564584124654E-324D;
        tensionZ = 4.9406564584124654E-324D;
    }

    public boolean goRight()
    {
        return goRight;
    }

    public void setGoRight(boolean b)
    {
        goRight = b;
    }

    public Node getNode()
    {
        return latticeNode;
    }

    public Concept getConcept()
    {
        return latticeNode.getConcept();
    }

    public Intent getIntent()
    {
        if(getConcept() != null)
            return getConcept().getIntent();
        else
            return null;
    }

    public Extent getExtent()
    {
        if(getConcept() != null)
            return getConcept().getExtent();
        else
            return null;
    }
    
    //by cjj 2005-4-10 用以在图形上产生Slit
    
    public Slit getSlit()
    {
    	if(getConcept()!=null)
    		return getConcept().getSlit();
    	else
    		return null;
    }

    public Vector getGenerator()
    {
        if(getConcept() != null)
            return getConcept().getGenerator();
        else
            return null;
    }

    public void addIntent()
    {  //Intent 用1表示
        addAttribut(new LatticeAttributeGraph(this, propToString(1)));
    }

    //by cjj 2005-4-10 用于在图形上产生Slit
    public void addSlit()
    {
    	addAttribut(new LatticeAttributeGraph(this,propToString(3)));
    }
    public void addExtent()
    {  //Extent用2表示
        addAttribut(new LatticeAttributeGraph(this, propToString(2)));
    }

    public void addGenerator()
    {
        String s = affGenerator();
        if(s != null)
            addAttribut(new LatticeAttributeGraph(this, s));
    }

    public String affGenerator()
    {
        if(getConcept() == null)
            return null;
        boolean test = true;
        String s = new String("G={");
        for(Iterator i = getGenerator().iterator(); i.hasNext();)
        {
            test = false;
            s = s + i.next().toString();
            if(i.hasNext())
                s = s + ", ";
        }

        s = s + "}";
        if(test)
            return null;
        else
            return s;
    }

    public void setSelected(boolean b)
    {
        super.setSelected(b);
    }

    public String propToString(int b)
    {
        if(getConcept() == null)
            return new String("");
        Iterator i = null;
        String s = null;
        if(b==2)
        {
            s = new String("E={");
            i = getExtent().iterator();
        } else if(b==1)
        {
            s = new String("I={");
            i = getIntent().iterator();
        }
        else if(b==3)
        {
        	s=new String("Slit=(");
        	i=getSlit().iterator();
        }
        while(i.hasNext()) 
        {
            s = s + i.next().toString();
            if(i.hasNext())
                s = s + ", ";
        }
        s = s + "}";
        return s;
    }

    public int nbRelations()
    {
        return nbRelationArrive() + nbRelationDepart();
    }

    public int compareTo(Object o)
    {
        LatticeNodeGraph lng = (LatticeNodeGraph)o;
        if(lng.x() < x())
            return 1;
        return lng.x() != x() || lng.nbRelations() <= nbRelations() ? -1 : 1;
    }

    public double longueurRelations()
    {
        double longueur = 0.0D;
        for(int i = 0; i < nbRelationArrive(); i++)
        {
            Relation r = relationArrive(i);
            longueur += ((LatticeRelation)r).longueur();
        }

        for(int j = 0; j < nbRelationDepart(); j++)
        {
            Relation r = relationDepart(j);
            longueur += ((LatticeRelation)r).longueur();
        }

        return longueur;
    }

    public void paintRelations(Graphics g, int xRel, int yRel)
    {
        if(!IS_COLORED)
        {
            super.paintRelations(g, xRel, yRel);
        } else
        {
            if(c == null)
            {
                c = new Color[nbRelationDepart() + nbRelationArrive()];
                double d = Math.random();
                for(int j = 0; j < nbRelationDepart() + nbRelationArrive(); j++)
                    c[j] = randomColor(d, j);

            }
            int xPoints[] = new int[3];
            int yPoints[] = new int[3];
            xPoints[0] = supGaucheX() + width() / 2 + xRel;
            yPoints[0] = supGaucheY() + yRel;
            for(int j = 0; j < nbRelationDepart() - 1; j++)
            {
                xPoints[1] = relationDepart(j).extremite().supGaucheX() + width() / 2 + xRel;
                yPoints[1] = relationDepart(j).extremite().supGaucheY() + yRel;
                xPoints[2] = relationDepart(j + 1).extremite().supGaucheX() + width() / 2 + xRel;
                yPoints[2] = relationDepart(j + 1).extremite().supGaucheY() + yRel;
                g.setColor(c[j]);
                g.fillPolygon(xPoints, yPoints, 3);
            }

            for(int j = 0; j < nbRelationArrive() - 1; j++)
            {
                xPoints[1] = relationArrive(j).origine().supGaucheX() + width() / 2 + xRel;
                yPoints[1] = relationArrive(j).origine().supGaucheY() + yRel;
                xPoints[2] = relationArrive(j + 1).origine().supGaucheX() + width() / 2 + xRel;
                yPoints[2] = relationArrive(j + 1).origine().supGaucheY() + yRel;
                g.setColor(c[j + nbRelationDepart()]);
                g.fillPolygon(xPoints, yPoints, 3);
            }

        }
    }

    public Color randomColor(double d, int j)
    {
        double f = Math.random();
        int col = 50 + (int)(f * 200D);
        if(d < 0.20000000000000001D)
            return new Color(0, 0, col);
        if(d < 0.40000000000000002D)
            return new Color(0, col, 0);
        if(d < 0.59999999999999998D)
            return new Color(col, 0, 0);
        if(d < 0.80000000000000004D)
            return new Color(col, col, 0);
        else
            return new Color(col, 0, col);
    }

    public static void setApplet(Component app)
    {
        applet = app;
    }

    public void init3D(int Rl, int Gl, int Bl, double Sf)
    {
        this.Rl = Rl;
        this.Gl = Gl;
        this.Bl = Bl;
        this.Sf = Sf;
    }

    public boolean Exist()
    {
        return Sf != 0.0D;
    }

    public int blend(int fg, int bg, float fgfactor)
    {
        return (int)((float)bg + (float)(fg - bg) * fgfactor);
    }

    public void Setup(int nBalls)
    {
        balls = new Image[nBalls];
        byte red[] = new byte[256];
        red[0] = -64;
        byte green[] = new byte[256];
        green[0] = -64;
        byte blue[] = new byte[256];
        blue[0] = -64;
        for(int r = 0; r < nBalls; r++)
        {
            float b = (float)(r + 1) / (float)nBalls;
            for(int i = maxr; i >= 1; i--)
            {
                float d = (float)i / (float)maxr;
                red[i] = (byte)blend(blend(Rl, 255, d), 192, b);
                green[i] = (byte)blend(blend(Gl, 255, d), 192, b);
                blue[i] = (byte)blend(blend(Bl, 255, d), 192, b);
            }

            IndexColorModel model = new IndexColorModel(8, maxr + 1, red, green, blue, 0);
            balls[r] = applet.createImage(new MemoryImageSource(80, 80, model, data, 0, 80));
        }

    }

    public void paint(Graphics gc, int x, int y, int r)
    {
        Image ba[] = balls;
        if(ba == null)
        {
            Setup((int)(16D * Sf));
            ba = balls;
        }
        r = (int)((double)r * Sf);
        Image i = ba[r];
        int size = 10 + r;
        gc.drawImage(i, x - (size >> 1), y - (size >> 1), size, size, applet);
    }

    static 
    {
        data = new byte[6400];
        int mr = 0;
        for(int Y = 80; --Y >= 0;)
        {
            int x0 = (int)(Math.sqrt(1600 - (Y - 40) * (Y - 40)) + 0.5D);
            int p = (Y * 80 + 40) - x0;
            for(int X = -x0; X < x0; X++)
            {
                int x = X + 15;
                int y = (Y - 40) + 15;
                int r = (int)(Math.sqrt(x * x + y * y) + 0.5D);
                if(r > mr)
                    mr = r;
                data[p++] = r > 0 ? (byte)r : 1;
            }

        }

        maxr = mr;
    }
}
