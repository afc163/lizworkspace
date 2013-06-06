// Decompiled by Jad v1.5.7g. Copyright 2000 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/SiliconValley/Bridge/8617/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi 
// Source File Name:   XYZLatModel.java

package lattice.gui.graph.threeD;

import java.awt.*;
import java.io.*;
import java.util.*;
import lattice.graph.trees.*;
import lattice.graph.zoom.ZoomViewer;
import lattice.gui.graph.LatticeGraphViewer;
import lattice.gui.graph.LatticeNodeGraph;

// Referenced classes of package lattice.gui.graph.threeD:
//            Matrix3D, ScaleableAtomImpl, ScaleableAtom

class XYZLatModel
{

    float vert[];
    Vector vVert;
    ScaleableAtom atoms[];
    int tvert[];
    int ZsortMap[];
    int nvert;
    int maxvert;
    int bond[][];
    int nbond;
    boolean box;
    boolean label;
    boolean bonds;
    int br;
    int bg;
    int bb;
    int gr;
    int gg;
    int gb;
    int xr;
    int xg;
    int xb;
    int decalX;
    int decalY;
    int nbAtom;
    static Hashtable atomTable;
    static Hashtable atomNumber;
    static ScaleableAtom defaultAtom;
    boolean transformed;
    Matrix3D mat;
    float xmin;
    float xmax;
    float ymin;
    float ymax;
    float zmin;
    float zmax;
    LatticeGraphViewer lgv;

    XYZLatModel()
    {
        vVert = new Vector();
        bond = new int[300][2];
        nbond = -1;
        decalX = 0;
        decalY = 0;
        nbAtom = 1;
        mat = new Matrix3D();
        mat.xrot(20D);
        mat.yrot(30D);
    }

    XYZLatModel(LatticeGraphViewer lgv, Vector niveau, boolean labl, boolean bx, boolean bnds, Color bgcolor, Color bndcolor, 
            Color bxcolor)
        throws Exception
    {
        this();
        this.lgv = lgv;
        label = labl;
        box = bx;
        bonds = bnds;
        br = bndcolor.getRed();
        bg = bndcolor.getGreen();
        bb = bndcolor.getBlue();
        gr = bgcolor.getRed();
        gg = bgcolor.getGreen();
        gb = bgcolor.getBlue();
        xr = bxcolor.getRed();
        xg = bxcolor.getGreen();
        xb = bxcolor.getBlue();
        atomTable = new Hashtable();
        atomNumber = new Hashtable();
        addAllVert(niveau);
        addAllRelation(niveau);
    }

    private void addAllVert(Vector niveau)
    {
        for(int i = 0; i < niveau.size(); i++)
            addVert((Vector)niveau.elementAt(i));

    }

    private void addVert(Vector vniveau)
    {
        for(int i = 0; i < vniveau.size(); i++)
            addVert((LatticeNodeGraph)vniveau.elementAt(i));

    }

    private void addVert(LatticeNodeGraph unNoeud)
    {
        atomTable.put(unNoeud.getLabel().toLowerCase(), unNoeud);
        addVert(unNoeud.getLabel(), (float)unNoeud.xd(), (float)(-unNoeud.yd()), (float)unNoeud.zd());
        atomNumber.put(unNoeud.getLabel().toLowerCase(), new Integer(nvert));
        vVert.add(unNoeud);
    }

    private void addAllRelation(Vector niveau)
    {
        for(int i = 0; i < niveau.size(); i++)
            buildNiveauRelation((Vector)niveau.elementAt(i));

    }

    public void buildNiveauRelation(Vector niveau)
    {
        int nbNode = niveau.size();
        for(int i = 0; i < nbNode; i++)
        {
            LatticeNodeGraph unNoeud = (LatticeNodeGraph)niveau.elementAt(i);
            for(int j = 0; j < unNoeud.nbRelationDepart(); j++)
            {
                LatticeNodeGraph unfils = (LatticeNodeGraph)unNoeud.relationDepart(j).extremite();
                int number = ((Integer)atomNumber.get(unNoeud.getLabel().toLowerCase())).intValue();
                int number2 = ((Integer)atomNumber.get(unfils.getLabel().toLowerCase())).intValue();
                nbond++;
                bond[nbond][0] = number2;
                bond[nbond][1] = number;
            }

        }

    }

    XYZLatModel(InputStream is, boolean labl, boolean bx, boolean bnds, Color bgcolor, Color bndcolor, Color bxcolor)
        throws Exception
    {
        this();
        StreamTokenizer st = new StreamTokenizer(new BufferedInputStream(is, 4000));
        st.eolIsSignificant(true);
        st.commentChar(35);
        int slot = 0;
        label = labl;
        box = bx;
        bonds = bnds;
        br = bndcolor.getRed();
        bg = bndcolor.getGreen();
        bb = bndcolor.getBlue();
        gr = bgcolor.getRed();
        gg = bgcolor.getGreen();
        gb = bgcolor.getBlue();
        xr = bxcolor.getRed();
        xg = bxcolor.getGreen();
        xb = bxcolor.getBlue();
        atomTable = new Hashtable();
        defaultAtom = new ScaleableAtomImpl(255, 100, 200, 1.0D);
        try
        {
label0:
            do
                switch(st.nextToken())
                {
                case -2: 
                default:
                    break;

                case -3: 
                    String name = st.sval;
                    if(name.equals("ATOM"))
                    {
                        int r = 255;
                        int g = 255;
                        int b = 255;
                        double s = 1.0D;
                        if(st.nextToken() == -3)
                        {
                            name = st.sval;
                            if(st.nextToken() == -2)
                            {
                                r = (int)st.nval;
                                if(st.nextToken() == -2)
                                {
                                    g = (int)st.nval;
                                    if(st.nextToken() == -2)
                                    {
                                        b = (int)st.nval;
                                        if(st.nextToken() == -2)
                                            s = st.nval;
                                    }
                                }
                            }
                        }
                        if(s >= 2D)
                            s = 2D;
                        if(s < 0.0D)
                            s = 1.0D;
                        atomTable.put(name.toLowerCase(), new ScaleableAtomImpl(r, g, b, s));
                    } else
                    {
                        double x = 0.0D;
                        double y = 0.0D;
                        double z = 0.0D;
                        if(st.nextToken() == -2)
                        {
                            x = st.nval;
                            if(st.nextToken() == -2)
                            {
                                y = st.nval;
                                if(st.nextToken() == -2)
                                    z = st.nval;
                            }
                        }
                        addVert(name, (float)x, (float)y, (float)z);
                        while(st.nextToken() == -2) 
                        {
                            nbond++;
                            bond[nbond][0] = nvert;
                            bond[nbond][1] = (int)st.nval;
                        }
                    }
                    while(st.ttype != 10 && st.ttype != -1) 
                        st.nextToken();
                    break;

                case -1: 
                    is.close();
                    break label0;
                }
            while(true);
        }
        catch(IOException ioexception) { }
        if(st.ttype != -1)
            throw new Exception(st.toString());
        else
            return;
    }

    int addVert(String name, float x, float y, float z)
    {
        int i = nvert;
        if(i >= maxvert)
            if(vert == null)
            {
                maxvert = 100;
                vert = new float[maxvert * 3];
                atoms = new ScaleableAtom[maxvert];
            } else
            {
                maxvert *= 2;
                float nv[] = new float[maxvert * 3];
                System.arraycopy(vert, 0, nv, 0, vert.length);
                vert = nv;
                ScaleableAtom na[] = new ScaleableAtom[maxvert];
                System.arraycopy(atoms, 0, na, 0, atoms.length);
                atoms = na;
            }
        ScaleableAtom a = (ScaleableAtom)atomTable.get(name.toLowerCase());
        if(a == null)
            a = defaultAtom;
        atoms[i] = a;
        i *= 3;
        vert[i] = x;
        vert[i + 1] = y;
        vert[i + 2] = z;
        return nvert++;
    }

    void transform()
    {
        if(transformed || nvert <= 0)
            return;
        if(tvert == null || tvert.length < nvert * 3)
            tvert = new int[nvert * 3];
        mat.transform(vert, tvert, nvert);
        transformed = true;
    }

    void reinitCoord()
    {
        int index = 0;
        for(Iterator e = vVert.iterator(); e.hasNext();)
        {
            LatticeNodeGraph n = (LatticeNodeGraph)e.next();
            vert[index] = n.x();
            vert[index + 1] = -n.y();
            vert[index + 2] = n.z();
            index += 3;
        }

        if(lgv.getX() != 0 || lgv.getY() != 0)
        {
            decalX -= lgv.getX();
            decalY -= lgv.getY();
            lgv.setX(0);
            lgv.setY(0);
            if(lgv.zoomCanvas() != null)
            {
                lgv.zoomCanvas().setDecalX(-decalX + lgv.getSize().width / 2);
                lgv.zoomCanvas().setDecalY(-decalY);
                lgv.zoomCanvas().clearRect();
                lgv.zoomCanvas().refresh1();
            }
        }
    }

    void paint(Graphics g)
    {
        if(vert == null || nvert <= 0)
        {
            return;
        } else
        {
            reinitCoord();
            transform();
            draw(g);
            return;
        }
    }

    void draw(Graphics g)
    {
        Font largest = new Font("Geneva", 0, 18);
        Font large = new Font("Geneva", 0, 12);
        Font small = new Font("Geneva", 0, 10);
        Font smallest = new Font("Geneva", 0, 7);
        int v[] = tvert;
        int zs[] = ZsortMap;
        if(zs == null)
        {
            ZsortMap = zs = new int[nvert];
            for(int i = nvert; --i >= 0;)
                zs[i] = i * 3;

        }
        for(int i = nvert - 1; --i >= 0;)
        {
            boolean flipped = false;
            for(int j = 0; j <= i; j++)
            {
                int a = zs[j];
                int b = zs[j + 1];
                if(v[a + 2] > v[b + 2])
                {
                    zs[j + 1] = a;
                    zs[j] = b;
                    flipped = true;
                }
            }

            if(!flipped)
                break;
        }

        int lg = 0;
        int lim = nvert;
        ScaleableAtom ls[] = atoms;
        int drawn[] = new int[nbond + 1];
        if(lim <= 0 || nvert <= 0)
            return;
        for(int i = 0; i < lim; i++)
        {
            int j = zs[i];
            int grey = v[j + 2];
            if(grey < 0)
                grey = 0;
            if(grey > 15)
                grey = 15;
            boolean e = atoms[j / 3].Exist();
            int v1 = j / 3 + 1;
            if(e && bonds || !e && box)
            {
                for(int k = 0; k <= nbond; k++)
                {
                    int v2 = -1;
                    if(bond[k][0] == v1)
                        v2 = bond[k][1] - 1;
                    if(bond[k][1] == v1)
                        v2 = bond[k][0] - 1;
                    if(v2 != -1 && drawn[k] == 0)
                    {
                        drawn[k] = 1;
                        double rr = (double)(v[j + 2] + v[v2 * 3 + 2] + 6) / 36D;
                        int r1;
                        int b1;
                        int g1;
                        if(e)
                        {
                            r1 = (int)(rr * (double)(br - gr) + (double)gr);
                            b1 = (int)(rr * (double)(bb - gb) + (double)gb);
                            g1 = (int)(rr * (double)(bg - gg) + (double)gg);
                        } else
                        {
                            r1 = (int)(rr * (double)(xr - gr) + (double)gr);
                            b1 = (int)(rr * (double)(xb - gb) + (double)gb);
                            g1 = (int)(rr * (double)(xg - gg) + (double)gg);
                        }
                        Color line = null;
                        try
                        {
                            line = new Color(r1, g1, b1);
                        }
                        catch(Exception exc)
                        {
                            line = Color.lightGray;
                        }
                        g.setColor(line);
                        g.drawLine(v[j] - decalX, v[j + 1] - decalY, v[v2 * 3] - decalX, v[v2 * 3 + 1] - decalY);
                    }
                }

            }
            if(e)
                atoms[j / 3].paint(g, v[j] - decalX, v[j + 1] - decalY, grey);
            if(label)
            {
                g.setFont(new Font("Geneva", 0, grey));
                g.setColor(new Color(150 - 10 * grey, 150 - 10 * grey, 150 - 10 * grey));
                FontMetrics fm = g.getFontMetrics();
                LatticeNodeGraph n = (LatticeNodeGraph)vVert.get(v1 - 1);
                g.drawString(n.getLabel(), v[j] - decalX - fm.stringWidth(n.getLabel()) / 2, (v[j + 1] - decalY) + fm.getMaxDescent());
            }
        }

    }

    void findBB()
    {
        if(nvert <= 0)
            return;
        float v[] = vert;
        float xmin = v[0];
        float xmax = xmin;
        float ymin = v[1];
        float ymax = ymin;
        float zmin = v[2];
        float zmax = zmin;
        for(int i = nvert * 3; (i -= 3) > 0;)
        {
            float x = v[i];
            if(x < xmin)
                xmin = x;
            if(x > xmax)
                xmax = x;
            float y = v[i + 1];
            if(y < ymin)
                ymin = y;
            if(y > ymax)
                ymax = y;
            float z = v[i + 2];
            if(z < zmin)
                zmin = z;
            if(z > zmax)
                zmax = z;
        }

        this.xmax = xmax;
        this.xmin = xmin;
        this.ymax = ymax;
        this.ymin = ymin;
        this.zmax = zmax;
        this.zmin = zmin;
    }
}
