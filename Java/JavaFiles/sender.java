//Socket发送端程序
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

   //获得对应socket的输入/输出流 
   InputStream fIn=socket.getInputStream(); 
   OutputStream fOut=socket.getOutputStream(); 

   //建立数据流 
   InputStreamReader isr=new InputStreamReader(fIn); 
   BufferedReader in=new BufferedReader(isr);   
   PrintStream out=new PrintStream(fOut);
 
   InputStreamReader userisr=new InputStreamReader(System.in); 
   BufferedReader userin=new BufferedReader(userisr);
 
    System.out.print("发送字符串:"); 
    str=userin.readLine();  //读取用户输入的字符串  
   while(true){ 

    out.println(str); 
    if(str.equals("end")) break;  //如果是"end",就退出
     System.out.println("等待确认...");
 
     strok=in.readLine(); 

     if(strok.equals("ok!"))
     {
        System.out.println("受到确认");
        System.out.print("发送字符串:");
        str=userin.readLine();
     }
    } 
   socket.close(); //关闭连接 
 } 
 catch(Exception e){ 
   System.out.println("异常:"+e);  } 
  } 
}
