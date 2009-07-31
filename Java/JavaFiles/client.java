//Socket客户端程序
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

   //获得对应socket的输入/输出流 
   InputStream fIn=socket.getInputStream(); 
   OutputStream fOut=socket.getOutputStream(); 

   //建立数据流 
   InputStreamReader isr=new InputStreamReader(fIn); 
   BufferedReader in=new BufferedReader(isr);   
   PrintStream out=new PrintStream(fOut); 
   InputStreamReader userisr=new InputStreamReader(System.in); 
   BufferedReader userin=new BufferedReader(userisr);
  
  File file=new File("temp.txt");
  BufferedWriter tf=new BufferedWriter(new FileWriter(file)); 

   while(true)
  {   
    System.out.println("输入文件下载的名字   以end结束"); 
    str=userin.readLine();  //读取用户输入的字符串
    
    out.println(str);  //将字符串传给服务器端
    if(str.equals("end")) break;
    
     System.out.println("等待服务器端消息..."); 
     str=in.readLine();  //获取服务器获得字符串
     if(str.equals("开始复制"))
     {
         while( !(str.equals("文件复制完毕"))) 
         {
              str=in.readLine();
              tf.write(str);//存入文件
              tf.newLine();
              tf.flush();
         }
         System.out.println("文件复制完毕!");

     }
     if(str.equals("没有该文件"))   System.out.println("没有该文件,请重输入");
  } 
         tf.close();
   socket.close(); //关闭连接 
 } 
 catch(Exception e){ 
   System.out.println("异常:"+e);  } 
  } 
}
