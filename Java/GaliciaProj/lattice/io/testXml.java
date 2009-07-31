// Decompiled by Jad v1.5.7g. Copyright 2000 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/SiliconValley/Bridge/8617/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi 
// Source File Name:   testXml.java

package lattice.io;

import java.io.PrintStream;
import javax.swing.JFileChooser;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.*;

public class testXml
{

    public testXml()
    {
    }

    public static void main(String args[])
    {
        try
        {
            DocumentBuilder docBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setFileSelectionMode(0);
            fileChooser.setDialogType(0);
            fileChooser.setMultiSelectionEnabled(false);
            java.io.File fich = null;
            int result = fileChooser.showOpenDialog(null);
            if(result == 1 || fileChooser.getSelectedFile() == null)
                System.exit(0);
            else
                fich = fileChooser.getSelectedFile();
            Document doc = docBuilder.parse(fich);
            Element root = doc.getDocumentElement();
            recursPrint(root, "");
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }

    public static void recursPrint(Node N, String esp)
    {
        if(N.getNodeType() == 3)
            System.out.println(esp + N.getNodeValue());
        if(N.getNodeType() == 1)
        {
            System.out.println(esp + ((Element)N).getTagName());
            for(int i = 0; i < N.getAttributes().getLength(); i++)
                System.out.println(esp + "Attr:" + N.getAttributes().item(i).getNodeName() + "=" + N.getAttributes().item(i).getNodeValue());

        }
        for(int i = 0; i < N.getChildNodes().getLength(); i++)
            recursPrint(N.getChildNodes().item(i), esp + "  ");

    }
}
