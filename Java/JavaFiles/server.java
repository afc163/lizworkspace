//Socke ����������
import java.net.*; 
import java.io.*;
import java.util.*; 
public class server{ 
  public static final int port=8000;
  public static void main(String args[])
  { 
   String str;

   try{     //�ڶ˿�portע����� 
      ServerSocket server=new ServerSocket(port); //������ǰ�̵߳ļ�������
      System.out.println("Started:  "+server);
      Socket socket=server.accept(); //����C/Sͨ�ŵ�Socket���� 
      System.out.println("socket:  "+socket);

      //��ö�ӦSocket������/����� 
      InputStream fIn=socket.getInputStream(); 
      OutputStream fOut=socket.getOutputStream(); 

      //���������� 
      InputStreamReader isr=new InputStreamReader(fIn); 
      BufferedReader in=new BufferedReader(isr);   
      PrintStream out=new PrintStream(fOut); 

      InputStreamReader userisr=new InputStreamReader(System.in); 
      BufferedReader userin=new BufferedReader(userisr);

   File file;
   BufferedReader source;
   
      while(true){ 
        System.out.println("�ȴ��ͻ��˵���Ϣ..."); 
        str=in.readLine();//���ͻ��˴��͵��ļ���
        System.out.println("�ļ���:"+str); 
  
        if(str.equals("end"))break; //�����end�����˳�

        file=new File(str);
          
 
        if(file.exists()==true) 
        {
            source=new BufferedReader(new FileReader(file));
            System.out.print("���ͻ��˷����ļ�:");
            out.println("��ʼ����");

            while((str=source.readLine())!=null)
                       out.println(str);
 
            out.println("�ļ��������");
            System.out.println("�ļ��������");
            source.close();
         }
        else out.println("û�и��ļ�");
      } //while

     socket.close();
     server.close();
    } //try
   catch(Exception e){ 
      System.out.println("�쳣:"+e);  } 
   } 
 }  

