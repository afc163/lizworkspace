// Decompiled by Jad v1.5.7g. Copyright 2000 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/SiliconValley/Bridge/8617/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi 
// Source File Name:   ThumbCreator.java

package lattice.graph.utils;

import java.awt.Component;
import java.awt.Image;
import java.io.PrintStream;

// Referenced classes of package lattice.graph.utils:
//            Ressources, ThumbReceiver

public class ThumbCreator extends Component
    implements Runnable
{

    protected boolean qualite;
    protected int width;
    protected int height;
    protected Image img;
    protected String nomFich;
    protected ThumbReceiver iReceiver;
    protected boolean calcImg;
    protected boolean calcImgRed;
    protected Image imgRed;
    protected int index;
    protected boolean invalide;
    protected Ressources rl;
    protected String path;

    public ThumbCreator(ThumbReceiver ir, Image img, String nomFich, int width, int height)
    {
        qualite = false;
        calcImg = false;
        calcImgRed = false;
        index = -1;
        invalide = false;
        iReceiver = ir;
        this.img = img;
        this.width = width;
        this.height = height;
        this.nomFich = nomFich;
    }

    public ThumbCreator(ThumbReceiver ir, Image img, String nomFich, int width, int height, int index)
    {
        qualite = false;
        calcImg = false;
        calcImgRed = false;
        this.index = -1;
        invalide = false;
        iReceiver = ir;
        this.img = img;
        this.width = width;
        this.height = height;
        this.nomFich = nomFich;
        this.index = index;
    }

    public ThumbCreator(ThumbReceiver ir, Image img, String nomFich, int width, int height, boolean qualite)
    {
        this.qualite = false;
        calcImg = false;
        calcImgRed = false;
        index = -1;
        invalide = false;
        this.qualite = qualite;
        iReceiver = ir;
        this.img = img;
        this.width = width;
        this.height = height;
        this.nomFich = nomFich;
    }

    public ThumbCreator(ThumbReceiver ir, Image img, String nomFich, int width, int height, int index, boolean qualite)
    {
        this.qualite = false;
        calcImg = false;
        calcImgRed = false;
        this.index = -1;
        invalide = false;
        this.qualite = qualite;
        iReceiver = ir;
        this.img = img;
        this.width = width;
        this.height = height;
        this.nomFich = nomFich;
        this.index = index;
    }

    public ThumbCreator(ThumbReceiver ir, Image img, String nomFich, String path, int width, int height, int index, 
            boolean qualite, Ressources rl)
    {
        this(ir, img, nomFich, width, height, index, qualite);
        this.path = path;
        this.rl = rl;
    }

    public boolean getQualite()
    {
        return qualite;
    }

    public void setQualite(boolean q)
    {
        qualite = q;
    }

    public void run()
    {
        try
        {
            if(img != null)
            {
                if(img.getHeight(this) > 0)
                    updateThumb(img);
            } else
            {
                updateThumb(Ressources.getStaticImage("Invalidelogo.jpg"));
            }
        }
        catch(NullPointerException e)
        {
            System.out.println("Attention, certaines ressources graphiques sont inaccessibles");
        }
    }

    protected float calcFactor(Image img)
    {
        int w = img.getWidth(this);
        int h = img.getHeight(this);
        float factor;
        if(w > h)
            factor = (float)width / (float)w;
        else
            factor = (float)height / (float)h;
        return factor;
    }

    public boolean imageUpdate(Image img, int infoflags, int x, int y, int width, int height)
    {
        if(infoflags >= 32 && infoflags < 64)
            updateThumb(img);
        if(infoflags >= 64 && iReceiver != null)
            iReceiver.imageReady(null, nomFich, index, true);
        return super.imageUpdate(img, infoflags, x, y, width, height);
    }

    public Image creerImageReduite(Image img)
        throws NullPointerException
    {
        if(img.getWidth(this) == width && img.getHeight(this) <= height)
            return img;
        if(img.getHeight(this) == height && img.getWidth(this) <= width)
        {
            return img;
        } else
        {
            float factor = calcFactor(img);
            int ws = (int)(factor * (float)img.getWidth(this));
            int hs = (int)(factor * (float)img.getHeight(this));
            imgRed = img.getScaledInstance(ws, hs, qualite ? 4 : 1);
            return imgRed;
        }
    }

    public void updateThumb(Image img)
        throws NullPointerException
    {
        if(!calcImg)
        {
            calcImg = true;
            imgRed = creerImageReduite(img);
            if(imgRed.getHeight(this) > 0)
                updateThumb(imgRed);
        } else
        if(!calcImgRed)
        {
            calcImgRed = true;
            if(iReceiver != null)
                iReceiver.imageReady(imgRed, nomFich, index, invalide);
            if(rl != null && path != null)
                rl.put(path, imgRed);
        }
    }
}
