/*
 * Created on 2005-1-10
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package lattice.gui.filter;

/**
 * @author CJJ
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class Bin_Filter extends AbstractFilter
{
	

	    public Bin_Filter()
	    {
	    }

	    public String getDescription()
	    {
	        return "Relational Contexts Fammily (0,1): *" + getFileExtension();
	    }

	    public String getFileExtension()
	    {
	        return ".cjj";
	    }
	

}
