// Decompiled by Jad v1.5.7g. Copyright 2000 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/SiliconValley/Bridge/8617/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi 
// Source File Name:   SetIntent.java

package lattice.util;

import java.util.*;

// Referenced classes of package lattice.util:
//            Intent

public class SetIntent extends TreeSet
    implements Intent
{

    public SetIntent()
    {
    }

    public Intent union(Intent intent)
    {
        Intent result;
        if(intent.size() > size())
        {
            result = (Intent)intent.clone();
            result.addAll(this);
        } else
        {
            result = (Intent)clone();
            result.addAll(intent);
        }
        return result;
    }

    public Intent intersection(Intent intent)
    {
        Intent result = new SetIntent();
        if(!isEmpty() && !intent.isEmpty())
        {
            Iterator it1 = iterator();
            Iterator it2 = intent.iterator();
            Object c2 = it2.next();
            while(it1.hasNext()) 
            {
                Object c1;
                for(c1 = it1.next(); it2.hasNext() && ((Comparable)c1).compareTo(c2) > 0; c2 = it2.next());
                if(c1.equals(c2))
                    result.add(c1);
                if(!it2.hasNext() && ((Comparable)c1).compareTo(c2) >= 0)
                    break;
            }
        }
        return result;
    }

   //计算并集，a没有其他意思，在GodinDB中被调用
   public Intent intersection(Intent intent,int a)
   {
	   Intent result = new SetIntent();
	   if(!isEmpty() && !intent.isEmpty())
	   {
		   Iterator it1=iterator();
		   Iterator it2 = intent.iterator();
		   if(intent.contains("") || contains("")) result.add("");
		   while(it1.hasNext())
		   {
			   String b=(String)it1.next();
			   if(intent.contains(b))
			      result.add(b);
		   }
		   
	   }
	   
	   return result;
	   
   }
    
    public Object clone()
    {
        SetIntent newObj = new SetIntent();
        newObj.addAll(this);
        return newObj;
    }
}
