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
	//SLIT�еĲ����
	public abstract Slit difference(Slit slit);
	
	 //�ں��Ľ���
    public abstract Slit intersection(Slit slit);
	//SLIT�Ĳ�������
	public abstract Slit union(Slit slit);
	
	//	���Ʋ���
    public abstract Object clone();
	
}
