//Socket�ͻ��˳���
import java.net.*; 
import java.io.*; 
import java.util.*; 
public class client
{ 
 
 public static void main(String[] args)
 { 
  String str;

  try{
  InetAddress addr=InetAddress.getByName("0.0.0.0");
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
  
  File file=new File("temp.txt");
  BufferedWriter tf=new BufferedWriter(new FileWriter(file)); 

   while(true)
  {   
    System.out.println("�����ļ����ص�����   ��end����"); 
    str=userin.readLine();  //��ȡ�û�������ַ���
    
    out.println(str);  //���ַ���������������
    if(str.equals("end")) break;
    
     System.out.println("�ȴ�����������Ϣ..."); 
     str=in.readLine();  //��ȡ����������ַ���
     if(str.equals("��ʼ����"))
     {
         while( !(str.equals("�ļ��������"))) 
         {
              str=in.readLine();
              tf.write(str);//�����ļ�
              tf.newLine();
              tf.flush();
         }
         System.out.println("�ļ��������!");

     }
     if(str.equals("û�и��ļ�"))   System.out.println("û�и��ļ�,��������");
  } 
         tf.close();
   socket.close(); //�ر����� 
 } 
 catch(Exception e){ 
   System.out.println("�쳣:"+e);  } 
  } 
}
