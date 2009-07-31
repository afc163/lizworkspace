/*
 * Created on 2005-1-11
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package lattice.util;
import java.util.List;;

/**
 * @author CJJ
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */


public interface Slit extends List
{
	//SLIT中的差集操作
	public abstract Slit difference(Slit slit);
	
	 //内涵的交集
    public abstract Slit intersection(Slit slit);
	//SLIT的并集操作
	public abstract Slit union(Slit slit);
	
	//	复制操作
    public abstract Object clone();
	
}
