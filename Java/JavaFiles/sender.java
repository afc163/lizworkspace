//Socket���Ͷ˳���
import java.net.*; 
import java.io.*; 
public class sender
{ 
 public static void main(String[] args)
 { 
  String str,strok;
  try
  { 
   InetAddress addr=InetAddress.getByName("192.168.0.52");
   Socket socket=new Socket(addr,8000); 
   System.out.println("socket="+socket); 

   //��ö�Ӧsocket������/����� 
   InputStream fIn=socket.getInputStream(); 
   OutputStream fOut=socket.getOutputStream(); 

   //���������� 
   InputStreamReader isr=new InputStreamReader(fIn); 
   BufferedReader in=new BufferedReader(isr);   
   PrintStream out=new PrintStream(fOut);
 
   InputStreamReader userisr=new InputStreamReader(System.in); 
   BufferedReader userin=new BufferedReader(userisr);
 
    System.out.print("�����ַ���:"); 
    str=userin.readLine();  //��ȡ�û�������ַ���  
   while(true){ 

    out.println(str); 
    if(str.equals("end")) break;  //�����"end",���˳�
     System.out.println("�ȴ�ȷ��...");
 
     strok=in.readLine(); 

     if(strok.equals("ok!"))
     {
        System.out.println("�ܵ�ȷ��");
        System.out.print("�����ַ���:");
        str=userin.readLine();
     }
    } 
   socket.close(); //�ر����� 
 } 
 catch(Exception e){ 
   System.out.println("�쳣:"+e);  } 
  } 
}
