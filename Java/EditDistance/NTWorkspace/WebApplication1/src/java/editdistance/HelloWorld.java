/*
 * HelloWorld.java
 *
 * Created on 2007��9��5��, ����8:32
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package editdistance;

/**
 *
 * @author shengyan
 */
public class HelloWorld
{
    public static void main(String[] argv)
    {
        try
        {
            System.out.print(1);
            System.out.println("����");
            System.out.print(2);
            System.out.println("����".getBytes());
            System.out.print(3);
            System.out.println("����".getBytes("GB2312"));
            System.out.print(4);
            System.out.println("����".getBytes("ISO8859_1"));
            System.out.print(5);
            System.out.println(new String("����".getBytes()));
            System.out.print(6);
            System.out.println(new String("����".getBytes(),"GB2312"));
            System.out.print(7);
            System.out.println(new String("����".getBytes(),"ISO8859_1"));
            System.out.print(8);
            System.out.println(new String("����".getBytes("GB2312")));
            System.out.print(9);
            System.out.println(new String("����".getBytes("GB2312"),"GB2312"));
            System.out.print(10);
            System.out.println(new String("����".getBytes("GB2312"),"ISO8859_1"));
            System.out.print(11);
            System.out.println(new String("����".getBytes("ISO8859_1")));
            System.out.print(12);
            System.out.println(new String("����".getBytes("ISO8859_1"),"GB2312"));
            System.out.print(13);
            System.out.println(new String("����".getBytes("ISO8859_1"),"ISO8859_1"));
            
            String myString = new String("ʢ��".getBytes("GB2312"), "GB2312");
            String myStringAnother = new String("���");
            System.out.print(14);
            System.out.println(myString+myStringAnother);
            for (int j = 0; j < myString.length(); j++)
            {
              System.out.println(myString.charAt(j));  
            }
            System.out.print(15);
            System.out.println(new String("|"));
            System.out.print(16);
            System.out.println(new String("|".getBytes("ISO8859_1"), "GB2312"));
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }
} 
