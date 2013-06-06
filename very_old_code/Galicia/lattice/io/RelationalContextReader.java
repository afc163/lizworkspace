// Decompiled by Jad v1.5.7g. Copyright 2000 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/SiliconValley/Bridge/8617/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi 
// Source File Name:   RelationalContextReader.java

package lattice.io;

import java.io.IOException;
import lattice.util.*;

// Referenced classes of package lattice.io:
//            BinaryRelationReader, MultiValuedRelationReader, ReadingFormatException

public interface RelationalContextReader
    extends BinaryRelationReader, MultiValuedRelationReader
{

    public abstract RelationalContext readRelationalContext()
        throws NammingException, BadInputDataException, ReadingFormatException, IOException;
}
