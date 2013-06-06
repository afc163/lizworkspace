/*
 * Created on 2005-1-11
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package lattice.util;
import java.util.Iterator;
import java.util.Vector;

/**
 * @author CJJ
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class SetSlit extends Vector
	implements Slit
{
	public Slit union(Slit slit)
	{
		 Slit result;
	     if(slit.size() > size())
	     {
	     	result = (Slit)slit.clone();
	        result.addAll(this);
	     } else
	     {
	     	result = (Slit)clone();
	     	result.addAll(slit);
	     }
	     return result;
		
	}
	
/*	public Slit difference(Slit slit)
	{
		Slit result =(Slit)this.clone();
        if(!isEmpty() && !slit.isEmpty())
        {
            Iterator it1 = iterator();
            Iterator it2 = slit.iterator();
            
            while(it2.hasNext()) 
            {
                Object c1;
                Object c2 = it2.next();
                for(c1 = it1.next(); it1.hasNext() && ((Comparable)c1).compareTo(c2) > 0; c1 = it1.next());
                if(c1.equals(c2))
                    result.add(c1);
                if(!it2.hasNext() && ((Comparable)c1).compareTo(c2) >= 0)
                    break;
            }
        }
        return result;
		
	}  
*/
	public Slit difference(Slit slit)
	{
		Slit result =(Slit)this.clone();
        if(!isEmpty() && !slit.isEmpty())
        {
            Iterator it1 = iterator();
            Iterator it2 = slit.iterator();
            
            while(it2.hasNext()) 
            {
                Object c1;
                Object c2 = it2.next();
                for(c1 = it1.next(); it1.hasNext(); c1 = it1.next())
                {
                	if(c1.equals(c2))
                	{
                		result.remove(c1);
                		break;
                	}
                }
                if(!it2.hasNext())
                    break;
            }
        }
        return result;
		
	}  
	
	public Object clone()
    {
        SetSlit newObj = new SetSlit();
        newObj.addAll(this);
        return newObj;
    }
	
	 public Slit intersection(Slit slit)
	 {
	 	Slit result = new SetSlit();
	    if(!isEmpty() && !slit.isEmpty())
	    {
	    	Iterator it1 = iterator();
	        Iterator it2 = slit.iterator();
	        Object c2 = it2.next();
	        while(it1.hasNext()) 
	        {
	            Object c1;
	            for(c1 = it1.next(); it2.hasNext(); c2 = it2.next());
	            if(c1.equals(c2))
	                result.add(c1);
	            if(!it2.hasNext())
	                break;
	        }
	    }
	    return result;
	  }

}
