// Decompiled by Jad v1.5.7g. Copyright 2000 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/SiliconValley/Bridge/8617/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi 
// Source File Name:   RelationalContextWriter.java

package lattice.io;

import java.io.IOException;
import lattice.util.RelationalContext;

// Referenced classes of package lattice.io:
//            BinaryRelationWriter, MultiValuedRelationWriter

public interface RelationalContextWriter
    extends BinaryRelationWriter, MultiValuedRelationWriter
{

    public abstract void writeRelationalContext(RelationalContext relationalcontext)
        throws IOException;
}
