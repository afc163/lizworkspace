//���ܶ˳���
import java.net.*; 
import java.io.*; 
public class receiver
{ 
 public static void main(String[] args)
 { 
  String str;
  try
  {   //�ڶ˿�portע����� 
      ServerSocket server=new ServerSocket(8000); //������ǰ�̵߳ļ�������
      System.out.println("Started:  "+server);
      Socket socket=server.accept(); //����C/Sͨ�ŵ�Socket���� 
      System.out.println("socket:  "+socket);

   //��ö�Ӧsocket������/����� 
   InputStream fIn=socket.getInputStream(); 
   OutputStream fOut=socket.getOutputStream(); 

   //���������� 
   InputStreamReader isr=new InputStreamReader(fIn); 
   BufferedReader in=new BufferedReader(isr);   
   PrintStream out=new PrintStream(fOut);
 
   InputStreamReader userisr=new InputStreamReader(System.in); 
   BufferedReader userin=new BufferedReader(userisr);

   while(true){
 
    str=in.readLine();
    System.out.println("�ܵ��ַ���:"+str); 
    if(str.equals("end")) break;  //�����"end",���˳�
    System.out.println("��ȷ��-->");
    out.println("ok!"); 
    } 
   socket.close(); //�ر����� 
 } 
 catch(Exception e){ 
   System.out.println("�쳣:"+e);  } 
  } 
}
