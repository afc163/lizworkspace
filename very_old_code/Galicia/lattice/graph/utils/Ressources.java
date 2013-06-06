// Decompiled by Jad v1.5.7g. Copyright 2000 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/SiliconValley/Bridge/8617/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi 
// Source File Name:   Ressources.java

package lattice.graph.utils;

import java.applet.Applet;
import java.applet.AppletContext;
import java.awt.*;
import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.*;

public class Ressources
{

    public static String directory = "Ressources";
    public static String dirSeparator = "/";
    public static String defautDocBase = "http://www.univ-reunion.fr/~ikbs/";
    protected static Applet applet;
    protected static Hashtable images = new Hashtable();
    protected static Toolkit toolkit;
    protected static URL docBase;
    public static int URL_DIR = 0;
    public static int URL_SANSDIR = 1;
    public static int SANS_URL = 2;
    public static int FROM_JAR = 3;
    public String defautJarDirectory;
    protected Component pere;
    protected MediaTracker mediaTracker;
    protected int acces;
    protected Hashtable imagesLocales;
    protected boolean local;
    protected boolean accesDistant;
    public boolean wait;

    public Ressources(Component pere)
    {
        defautJarDirectory = "ikbs/ressources";
        acces = URL_DIR;
        imagesLocales = new Hashtable();
        local = false;
        accesDistant = false;
        wait = true;
        this.pere = pere;
        toolkit = pere.getToolkit();
        mediaTracker = new MediaTracker(pere);
    }

    public Ressources(Component pere, boolean local)
    {
        this(pere);
        this.local = local;
    }

    public Ressources(Applet applet)
    {
        this(((Component) (applet)));
        applet = applet;
        docBase = applet.getDocumentBase();
    }

    public int getAcces()
    {
        return acces;
    }

    public void setAcces(int a)
    {
        acces = a;
    }

    public boolean getAccesDistant()
    {
        return accesDistant;
    }

    public void setAccesDistant(boolean a)
    {
        accesDistant = a;
    }

    public static AppletContext getAppletContext()
    {
        if(applet != null)
        {
            return applet.getAppletContext();
        } else
        {
            System.out.println("Impossible d'acc?der au contexte de l'applet");
            return null;
        }
    }

    public static URL getDocBase()
    {
        if(docBase == null)
        {
            try
            {
                URL u = new URL(defautDocBase);
                return u;
            }
            catch(MalformedURLException e)
            {
                System.out.println("Impossible d'acc?der ? l'URL de base");
            }
            return null;
        } else
        {
            return docBase;
        }
    }

    public static String getDefautDocBase()
    {
        return defautDocBase;
    }

    public static Toolkit getToolkit()
    {
        return toolkit;
    }

    public void init(String nomImages[])
        throws MalformedURLException, InterruptedException
    {
        for(int i = 0; i < nomImages.length; i++)
            if(get(nomImages[i]) == null)
                addImage(nomImages[i], i);

        loadImages();
    }

    public void init(Vector nomImages)
        throws MalformedURLException, InterruptedException
    {
        for(int i = 0; i < nomImages.size(); i++)
            if(get((String)nomImages.elementAt(i)) == null)
                addImage((String)nomImages.elementAt(i), i);

        loadImages();
    }

    public void init(String nomImage)
        throws MalformedURLException, InterruptedException, OutOfMemoryError
    {
        if(get(nomImage) == null)
        {
            addImage(nomImage, 0);
            loadImages();
        }
    }

    public static String getParameter(String s)
    {
        if(applet != null)
        {
            return applet.getParameter(s);
        } else
        {
            System.out.println("Impossible d'acc?der aux param?tres de l'applet");
            return null;
        }
    }

    public void addImage(String nomImage, int i)
        throws MalformedURLException, InterruptedException
    {
        if(get(nomImage) == null)
        {
            InputStream inputstream = null;
            if(acces == FROM_JAR)
            {
                inputstream = getClass().getResourceAsStream("/" + defautJarDirectory + "/" + nomImage);
                if(!unjarImage(nomImage, inputstream) && getAccesDistant())
                    urlImage(nomImage, i);
            } else
            {
                urlImage(nomImage, i);
            }
        }
    }

    protected void urlImage(String nomImage, int i)
        throws MalformedURLException, InterruptedException
    {
        URL u = buildURL(nomImage);
        if(u != null)
        {
            Image img = toolkit.getImage(u);
            if(wait)
                mediaTracker.addImage(img, i);
            put(nomImage, img);
        }
    }

    public boolean unjarImage(String nomImage, InputStream inputstream)
    {
        try
        {
            BufferedInputStream bufferedinputstream = new BufferedInputStream(inputstream);
            ByteArrayOutputStream bytearrayoutputstream = new ByteArrayOutputStream(1024);
            byte abyte0[] = new byte[1024];
            int i;
            while((i = bufferedinputstream.read(abyte0)) > 0) 
                bytearrayoutputstream.write(abyte0, 0, i);
            bufferedinputstream.close();
            bytearrayoutputstream.flush();
            abyte0 = bytearrayoutputstream.toByteArray();
            if(abyte0.length == 0)
            {
                return false;
            } else
            {
                Image img = toolkit.createImage(abyte0);
                put(nomImage, img);
                return true;
            }
        }
        catch(IOException ioexception)
        {
            return false;
        }
    }

    public void put(String nomImage, Image img)
    {
        if(local)
            imagesLocales.put(nomImage, img);
        else
            images.put(nomImage, img);
    }

    public Image get(String nomImage)
    {
        Image img = null;
        if(local)
        {
            img = (Image)imagesLocales.get(nomImage);
            if(img == null)
            {
                img = (Image)images.get(nomImage);
                if(img != null)
                    System.out.println(nomImage + " trouv?e dans la table globale");
            }
        } else
        {
            img = (Image)images.get(nomImage);
        }
        return img;
    }

    public void copyLocal()
    {
        Image img = null;
        for(Enumeration e = imagesLocales.keys(); e.hasMoreElements();)
        {
            String nomImage = (String)e.nextElement();
            img = (Image)imagesLocales.get(nomImage);
            if(img != null)
                images.put(nomImage, img);
        }

    }

    public String find(Image img)
    {
        Image m = null;
        for(Enumeration e = imagesLocales.keys(); e.hasMoreElements();)
        {
            String clef = (String)e.nextElement();
            m = get(clef);
            if(m == null)
                removeImage(clef);
            else
            if(img == get(clef))
                return clef;
        }

        return null;
    }

    public boolean getLocal()
    {
        return local;
    }

    public void copyLocal(boolean b)
    {
        if(local != b)
        {
            local = b;
            if(!b)
                copyLocal();
        }
    }

    public void setLocal(boolean b)
    {
        local = b;
    }

    public void toPrint()
    {
        System.out.println("Liste des images en m?moire statique :\n");
        for(Enumeration e = images.keys(); e.hasMoreElements(); System.out.println(e.nextElement()));
        System.out.println("Liste des images en m?moire dynamique :\n");
        for(Enumeration e = imagesLocales.keys(); e.hasMoreElements(); System.out.println(e.nextElement()));
    }

    public static void removeStaticImage(String nomImage)
    {
        images.remove(nomImage);
    }

    public void removeImage(String nomImage)
    {
        if(local)
        {
            Image img = get(nomImage);
            imagesLocales.remove(nomImage);
        } else
        {
            images.remove(nomImage);
        }
    }

    public void removeImages(Vector nomImages)
    {
        for(int i = 0; i < nomImages.size(); i++)
            images.remove((String)nomImages.elementAt(i));

    }

    public void removeImage(Image img)
    {
        String nomImg = find(img);
        if(nomImg != null)
            removeImage(nomImg);
    }

    public void removeImages(Enumeration e)
    {
        for(; e.hasMoreElements(); images.remove((String)e.nextElement()));
    }

    protected URL buildURL(String nomImage)
        throws MalformedURLException
    {
        URL docBase = getDocBase();
        URL u = null;
        if(acces == URL_DIR)
            u = new URL(docBase, directory + dirSeparator + nomImage);
        if(acces == URL_SANSDIR)
            u = new URL(docBase, nomImage);
        if(acces == SANS_URL)
            u = new URL("file://" + nomImage);
        try
        {
            u.openStream();
        }
        catch(IOException e)
        {
            if(getAccesDistant())
            {
                showAll("Ressource non accessible a l'URL : " + u + ", test sur " + defautDocBase);
                u = new URL(new URL(defautDocBase), nomImage);
            } else
            {
                u = null;
            }
        }
        return u;
    }

    protected static URL buildStaticURL(String ress)
        throws MalformedURLException
    {
        URL u = new URL(getDocBase(), ress);
        try
        {
            u.openStream();
        }
        catch(IOException e)
        {
            showAll("Ressource non accessible a l'URL : " + u);
        }
        return u;
    }

    public void loadImages()
        throws InterruptedException
    {
        if(wait)
            mediaTracker.waitForAll();
    }

    public static Image getStaticImage(String nomImage)
    {
        return (Image)images.get(nomImage);
    }

    public Image getImage(String nomImage)
    {
        Image img = null;
        try
        {
            init(nomImage);
            img = get(nomImage);
        }
        catch(Exception exception) { }
        return img;
    }

    public MediaTracker getMediaTracker()
    {
        return mediaTracker;
    }

    public void setDirectory(String dir)
    {
        directory = dir;
    }

    public static void showDocument(String url)
    {
        if(url != null && !url.equals(""))
        {
            URL u = null;
            try
            {
                if((new String("http://")).indexOf(url) != -1 || (new String("file:/")).indexOf(url) != -1)
                    u = new URL(url);
                else
                    u = buildStaticURL(url);
            }
            catch(MalformedURLException e)
            {
                showAll("Erreur : acc?s aux ressources invalide");
            }
            System.out.println(u);
            getAppletContext().showDocument(u, "Illustrations");
        } else
        {
            showAll("Pas d'URL associ? ? cet ?l?ment");
        }
    }

    public static void showStatus(String s)
    {
        getAppletContext().showStatus(s);
    }

    public static void showAll(String s)
    {
        System.out.println(s);
        showStatus(s);
    }

}
