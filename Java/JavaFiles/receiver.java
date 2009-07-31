//接受端程序
import java.net.*; 
import java.io.*; 
public class receiver
{ 
 public static void main(String[] args)
 { 
  String str;
  try
  {   //在端口port注册服务 
      ServerSocket server=new ServerSocket(8000); //创建当前线程的监听对象
      System.out.println("Started:  "+server);
      Socket socket=server.accept(); //负责C/S通信的Socket对象 
      System.out.println("socket:  "+socket);

   //获得对应socket的输入/输出流 
   InputStream fIn=socket.getInputStream(); 
   OutputStream fOut=socket.getOutputStream(); 

   //建立数据流 
   InputStreamReader isr=new InputStreamReader(fIn); 
   BufferedReader in=new BufferedReader(isr);   
   PrintStream out=new PrintStream(fOut);
 
   InputStreamReader userisr=new InputStreamReader(System.in); 
   BufferedReader userin=new BufferedReader(userisr);

   while(true){
 
    str=in.readLine();
    System.out.println("受到字符串:"+str); 
    if(str.equals("end")) break;  //如果是"end",就退出
    System.out.println("发确认-->");
    out.println("ok!"); 
    } 
   socket.close(); //关闭连接 
 } 
 catch(Exception e){ 
   System.out.println("异常:"+e);  } 
  } 
}
