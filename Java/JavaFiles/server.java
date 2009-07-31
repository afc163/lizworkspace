//Socke 服务器程序
import java.net.*; 
import java.io.*;
import java.util.*; 
public class server{ 
  public static final int port=8000;
  public static void main(String args[])
  { 
   String str;

   try{     //在端口port注册服务 
      ServerSocket server=new ServerSocket(port); //创建当前线程的监听对象
      System.out.println("Started:  "+server);
      Socket socket=server.accept(); //负责C/S通信的Socket对象 
      System.out.println("socket:  "+socket);

      //获得对应Socket的输入/输出流 
      InputStream fIn=socket.getInputStream(); 
      OutputStream fOut=socket.getOutputStream(); 

      //建立数据流 
      InputStreamReader isr=new InputStreamReader(fIn); 
      BufferedReader in=new BufferedReader(isr);   
      PrintStream out=new PrintStream(fOut); 

      InputStreamReader userisr=new InputStreamReader(System.in); 
      BufferedReader userin=new BufferedReader(userisr);

   File file;
   BufferedReader source;
   
      while(true){ 
        System.out.println("等待客户端的消息..."); 
        str=in.readLine();//读客户端传送的文件名
        System.out.println("文件名:"+str); 
  
        if(str.equals("end"))break; //如果是end，则退出

        file=new File(str);
          
 
        if(file.exists()==true) 
        {
            source=new BufferedReader(new FileReader(file));
            System.out.print("给客户端发送文件:");
            out.println("开始复制");

            while((str=source.readLine())!=null)
                       out.println(str);
 
            out.println("文件复制完毕");
            System.out.println("文件复制完毕");
            source.close();
         }
        else out.println("没有该文件");
      } //while

     socket.close();
     server.close();
    } //try
   catch(Exception e){ 
      System.out.println("异常:"+e);  } 
   } 
 }  

