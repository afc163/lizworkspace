// Decompiled by Jad v1.5.7g. Copyright 2000 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/SiliconValley/Bridge/8617/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi 
// Source File Name:   BinaryRelationWriter.java

package lattice.io;

import java.io.IOException;
import lattice.util.BinaryRelation;

public interface BinaryRelationWriter
{

    public abstract void writeBinaryRelation(BinaryRelation binaryrelation)
        throws IOException;
}
