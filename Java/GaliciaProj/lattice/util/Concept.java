// Decompiled by Jad v1.5.7g. Copyright 2000 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/SiliconValley/Bridge/8617/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi 
// Source File Name:   Concept.java

package lattice.util;

import java.util.Set;
import java.util.Vector;

// Referenced classes of package lattice.util:
//            SetExtent, SetIntent, Extent, Intent

public class Concept
{

	//Extent Intent 都是interface
    private Extent extent;    //外延
    private Intent intent;    //内涵
    
    //by cjj 2005-4-8
    public Slit slit;    //用于存储SLIT集合
    
    private Vector generator;
    private Extent simplifyExtent;
    private Intent simplifyIntent;

    public Concept(Extent extent, Intent intent)
    {
        this.extent = extent;
        this.intent = intent;
        generator = new Vector();
        simplifyExtent = new SetExtent();  //Extent的具体实现类
        simplifyIntent = new SetIntent();  //Intent的具体实现类
    }
    
    //by cjj 2005-4-8  带有SLit的构造函数
    public Concept(Extent extent, Intent intent,Slit slit)
    {
        this.extent = extent;
        this.intent = intent;
        this.slit=slit;
        generator = new Vector();
        simplifyExtent = new SetExtent();  //Extent的具体实现类
        simplifyIntent = new SetIntent();  //Intent的具体实现类
    }
    
    

    public Extent getExtent()
    {
        return extent;
    }

    public Intent getIntent()
    {
        return intent;
    }

    public Vector getGenerator()
    {
        return generator;
    }

    public Extent getSimplifyExtent()
    {
        return simplifyExtent;
    }

    public Intent getSimplifyIntent()
    {
        return simplifyIntent;
    }

    public void setExtent(Extent extent)
    {
        this.extent = extent;
    }

    public void setGenerator(Vector gen)
    {
        generator = gen;
    }

    public void setIntent(Intent intent)
    {
        this.intent = intent;
    }

    public boolean equals(Object obj)
    {
        Concept concept_out = (Concept)obj;
        
        //内涵和外延全部相等,则返回真
        return extent.equals(concept_out.getExtent()) && intent.equals(concept_out.getIntent());
    }

    public String toString()
    {
        String string = "";
        string = string.concat("{ ");
        string = string.concat(extent.toString());
        string = string.concat(" , ");
        string = string.concat(intent.toString());
        string = string.concat(" }");
        return string;
    }
    
    //by cjj 2005-4-8
    public Slit getSlit()
    {
    	return slit;
    }
    
    public void setSlit(Slit slit)
    {
        this.slit=slit;
    }
    
    //--
}
