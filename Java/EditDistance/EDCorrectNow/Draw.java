import   java.awt.*;  
  import   java.awt.event.*;  
  import   java.applet.*;  
  import   java.util.Vector;  
  /*<applet   code=Draw.class   width=300   height=300>  
  </applet>*/  
  public   class   Draw   extends   Applet  
  {  
  Panel   radioPanel;  
  drawPanel   dPanel;  
  public   void   init()  
  {  
  dPanel=new   drawPanel();  
  this.setLayout(new   BorderLayout());  
  this.add("Center",dPanel);  
  }  
   
  }  
  class   drawPanel   extends   Panel    
  {  
  int   x1,y1;  
  int   x2,y2;  
  Vector   lines=new   Vector();  
  public   drawPanel()  
  {  
  this.setBackground(Color.orange);  
  this.addMouseListener(new   Mou_Lis());  
  this.addMouseMotionListener(new   MouMotion_Lis());  
  }  
  public   void   paint(Graphics   g)  
  {  
  int   np=lines.size();  
  for(int   i=0;i<np;i++)  
  {  
  Rectangle   p=(Rectangle)lines.elementAt(i);  
  if(p.width!=-1)  
  g.drawOval(p.x,p.y,p.width-p.x,p.height-p.y);  
  }  
  if(x2!=-1)  
  g.drawOval(x1,y1,x2-x1,y2-y1);  
   
  }  
  private   class   Mou_Lis   extends   MouseAdapter  
  {  
  public   void   mousePressed(MouseEvent   e)  
  {  
   
  x1=e.getX();  
  y1=e.getY();  
  x2=-1;  
   
  }  
                  public   void   mouseReleased(MouseEvent   e)  
                  {  
                   
   
  x2=e.getX();  
  y2=e.getY();  
  lines.addElement(new   Rectangle(x1,y1,x2,y2));  
  x2=-1;  
  repaint();  
   
   
                  }  
                 
          }  
          private   class   MouMotion_Lis   extends   MouseMotionAdapter  
          {  
          public   void   mouseDragged(MouseEvent   e)  
                  {  
                   
  x2=e.getX();  
  y2=e.getY();  
  repaint();  
   
                  }  
                   
          }  
  }