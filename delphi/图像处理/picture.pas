unit picture;

interface

uses
  Windows, Messages, SysUtils, Variants, Classes, Graphics, Controls, Forms,
  Dialogs, Menus, ExtCtrls, ExtDlgs, ActnList;

type
   TRGBArray=array[0..32767] of TRGBTriple;
   PRGBArray=^TRGBArray;

  TForm1 = class(TForm)
    MainMenu1: TMainMenu;
    PopupMenu1: TPopupMenu;
    OpenPictureDialog1: TOpenPictureDialog;
    SavePictureDialog1: TSavePictureDialog;
    Image1: TImage;
    N1: TMenuItem;
    N2: TMenuItem;
    N3: TMenuItem;
    N4: TMenuItem;
    O1: TMenuItem;
    B1: TMenuItem;
    N5: TMenuItem;
    N6: TMenuItem;
    N7: TMenuItem;
    N8: TMenuItem;
    N9: TMenuItem;
    Sobel1: TMenuItem;
    N10: TMenuItem;
    N11: TMenuItem;
    N12: TMenuItem;
    ActionList1: TActionList;
    Actionxsh: TAction;
    Actionpzh: TAction;
    Actiontqlk: TAction;
    Actionxhtx: TAction;
    Actionsobel: TAction;
    Actionhytx: TAction;
    Actionfsh: TAction;
    procedure ActionxshExecute(Sender: TObject);
    procedure ActionpzhExecute(Sender: TObject);
    procedure ActiontqlkExecute(Sender: TObject);
    procedure ActionxhtxExecute(Sender: TObject);
    procedure ActionsobelExecute(Sender: TObject);
    procedure ActionhytxExecute(Sender: TObject);
    procedure ActionfshExecute(Sender: TObject);
    procedure N2Click(Sender: TObject);
    procedure N3Click(Sender: TObject);
    procedure O1Click(Sender: TObject);
    procedure FormCreate(Sender: TObject);
  private
    { Private declarations }
    OldBmp:TBitmap;//定义用来保存原始图像的位图对象
    procedure Bmp_Binary(Bmp:TBitmap);//实现像素化
    procedure Bmp_Expand(Bmp:TBitmap);
    procedure Bmp_Cautery(Bmp:TBitmap);//腐蚀
  public
    { Public declarations }
  end;

var
  Form1: TForm1;

implementation

{$R *.dfm}
procedure TForm1.Bmp_Binary(Bmp:TBitmap);//实现像素化
var
  x,y:integer;
  p:PByteArray;
  newbmp:TBitmap;
  gray:byte;
begin
  newbmp:=TBitmap.Create;
  newbmp.PixelFormat:=Bmp.PixelFormat; //设定像素的显示颜色
  newbmp.Assign(Bmp);
  //开始转换
  for y:=0 to bmp.Height-1 do
  begin
    p:=newbmp.ScanLine[y];//扫描每行像素颜色
    for x:=0 to bmp.Width-1 do
    begin
      gray:=Round(0.299*p[3*x+2]+0.587*p[3*x+1]+0.11*p[3*x]); //灰化的计算公式
      if gray>128 then //128为域值
        gray:=255
      else
         gray:=0;
      p[3*x+2]:=gray;
      p[3*x+1]:=gray;
      p[3*x]:=gray;
    end;
  end;
  Bmp.Assign(newbmp);
  newbmp.Free;
end;

procedure TForm1.Bmp_Expand(Bmp:TBitmap);
var
  x,y:integer;
  p1,p2,p3,p4:pByteArray;
  newbmp:TBitmap;
begin
  newbmp:=TBitmap.Create;
  newbmp.Assign(bmp);
  for y:=1 to newbmp.Height-2 do
  begin
    p1:=bmp.ScanLine[y];
    p2:=newbmp.ScanLine[y-1];
    p3:=newbmp.ScanLine[y];
    p4:=newbmp.ScanLine[y+1];
    for x:=1 to newbmp.Width-2 do
    begin
      if ((p1[3*x]=255)and(p1[3*x+1]=255)and(p1[3*x+2]=255)) then
      begin
        if (((p3[3*(x-1)]=0)and(p3[3*(x-1)+1]=0)and(p3[3*(x-1)+2]=0))
        or((p3[3*(x+1)]=0)and(p3[3*(x+1)+1]=0)and(p3[3*(x+1)+2]=0))
        or((p2[3*x]=0)and(p2[3*x+1]=0)and(p2[3*x+2]=0))
        or((p4[3*x]=0)and(p4[3*x+1]=0)and(p4[3*x+2]=0)))then
        begin
          p1[3*x]:=0;p1[3*x+1]:=0;p1[3*x+2]:=0;
        end;
      end;
    end;
  end;
  newbmp.Free;
end;

procedure TForm1.Bmp_Cautery(Bmp:TBitmap);//腐蚀
var
  x,y:integer;
  p1,p2,p3,p4:pByteArray;
  newbmp:TBitmap;
begin
  newbmp:=TBitmap.Create;
  newbmp.Assign(bmp);
  for y:=1 to newbmp.Height-2 do
  begin
    p1:=bmp.ScanLine[y];
    p2:=newbmp.ScanLine[y-1];
    p3:=newbmp.ScanLine[y];
    p4:=newbmp.ScanLine[y+1];
    for x:=1 to newbmp.Width-2 do
    begin
      if ((p1[3*x]=0)and(p1[3*x+1]=0)and(p1[3*x+2]=0)) then
      begin
        if (((p3[3*(x-1)]=255)and(p3[3*(x-1)+1]=255)and(p3[3*(x-1)+2]=255))
        or((p3[3*(x+1)]=255)and(p3[3*(x+1)+1]=255)and(p3[3*(x+1)+2]=255))
        or((p2[3*x]=255)and(p2[3*x+1]=255)and(p2[3*x+2]=255))
        or((p4[3*x]=255)and(p4[3*x+1]=255)and(p4[3*x+2]=255)))then
        begin
          p1[3*x]:=255;p1[3*x+1]:=255;p1[3*x+2]:=255;
        end;
      end;
    end;
  end;
  newbmp.Free;
end;

procedure TForm1.ActionxshExecute(Sender: TObject);
begin
    //像素化
    Bmp_Binary(Image1.Picture.Bitmap);
end;

procedure TForm1.ActionpzhExecute(Sender: TObject);
begin
   //膨胀
   Bmp_Binary(Image1.Picture.Bitmap);//先像素化
   Bmp_Expand(Image1.Picture.Bitmap);
   Image1.Picture.Assign(Image1.Picture.Bitmap);
end;

procedure TForm1.ActionfshExecute(Sender: TObject);
begin
    //腐蚀效果
    Bmp_Binary(Image1.Picture.Bitmap);
    Bmp_cautery(Image1.Picture.Bitmap);
    Image1.Picture.Assign(Image1.Picture.Bitmap);
end;

procedure TForm1.ActiontqlkExecute(Sender: TObject);
var
  bmp1,bmp2:Tbitmap;
  i,j:Integer;
  p1,p2,p3,p4:pbyteArray;
begin
   //提取轮廓
   Bmp_Binary(Image1.Picture.Bitmap);
   bmp1:=TBitmap.Create;
   bmp2:=TBitmap.Create;
   bmp1.Assign(Image1.Picture.Bitmap);
   bmp2.Assign(Image1.Picture.Bitmap);
   bmp1.PixelFormat:=pf24bit;
   bmp2.PixelFormat:=pf24bit;
   //开始转换
   for i:=1 to bmp1.Height-2 do
   begin
     p1:=bmp1.ScanLine[i-1];
     p2:=bmp1.ScanLine[i];
     p3:=bmp1.ScanLine[i+1];
     p4:=bmp2.ScanLine[i];
     for j:=1 to bmp1.Width-2 do
     begin
       if(p2[3*j+2]=0)and(p2[3*j+1]=0)and(p2[3*j]=0)then
       begin
         if ((p2[3*(j-1)]=0)and(p2[3*(j-1)+1]=0)and(p2[3*(j-1)+2]=0)
         and(p2[3*(j+1)]=0)and(p2[3*(j+1)+1]=0)and(p2[3*(j+1)+2]=0)
         and(p1[3*(j-1)]=0)and(p1[3*(j-1)+1]=0)and(p1[3*(j-1)+2]=0)
         and(p1[3*j]=0)and(p1[3*j+1]=0)and(p1[3*j+2]=0)
         and(p1[3*(j+1)]=0)and(p1[3*(j+1)+1]=0)and(p1[3*(j+1)+2]=0)
         and(p3[3*(j-1)]=0)and(p3[3*(j-1)+1]=0)and(p3[3*(j-1)+2]=0)
         and(p3[3*j]=0)and(p3[3*j+1]=0)and(p3[3*j+2]=0)
         and(p3[3*(j+1)]=0)and(p3[3*(j+1)+1]=0)and(p3[3*(j+1)+2]=0))
         then
         begin
           p4[3*j]:=255;p4[3*j+1]:=255;p4[3*j+2]:=255;
         end;
       end;
     end;
   end;
   Image1.Picture.Bitmap.Assign(Bmp2);
   bmp2.Free;bmp1.Free;
end;

procedure TForm1.ActionxhtxExecute(Sender: TObject);
var
  newBmp:TBitmap;
  x,y,ncount:integer;
  p1,p2,p3,p4:pRGBArray;
  nb:array[1..3,1..3]of integer;
  c1,c2,c3,c4:boolean;
begin
   //细化图像
   Bmp_Binary(Image1.Picture.Bitmap);
   newbmp:=TBitmap.Create;
   newbmp.Assign(Image1.Picture.Bitmap);
   //开始转换
   for y:=1 to newBmp.Height-2 do
   begin
      p1:=newbmp.ScanLine[y];
      p2:=Image1.Picture.Bitmap.ScanLine[y-1];
      p3:=Image1.Picture.Bitmap.ScanLine[y];
      p4:=Image1.Picture.Bitmap.ScanLine[y+1];
      for x:=1 to newBmp.Width-2 do
      begin
        c1:=false;c2:=false;c3:=false;c4:=false;
        //将[x,y]周围的8个像素点和它本身0-1化
        nb[1,1]:=p2[x-1].rgbtRed div 255;
        nb[1,2]:=p2[x].rgbtRed div 255;
        nb[1,3]:=p2[x+1].rgbtRed div 255;
        nb[2,1]:=p3[x-1].rgbtRed div 255;
        nb[2,2]:=p3[x].rgbtRed div 255;
        nb[2,3]:=p3[x+1].rgbtRed div 255;
        nb[3,1]:=p4[x-1].rgbtRed div 255;
        nb[3,2]:=p4[x].rgbtRed div 255;
        nb[3,3]:=p4[x+1].rgbtRed div 255;
        //获得ncount
        ncount:=nb[1,1]+nb[1,2]+nb[1,3]+nb[2,1]+nb[2,2]+nb[2,3]+nb[3,1]+nb[3,2]+nb[3,3];
        if (ncount>=2)and(ncount<=6)then c1:=true;
        ncount:=0;
        if(nb[1,1]=0)and(nb[1,2]=1)then inc(ncount);
        if(nb[1,2]=0)and(nb[1,3]=1)then inc(ncount);
        if(nb[1,3]=0)and(nb[2,1]=1)then inc(ncount);
        if(nb[2,1]=0)and(nb[2,2]=1)then inc(ncount);
        if(nb[2,2]=0)and(nb[2,3]=1)then inc(ncount);
        if(nb[2,3]=0)and(nb[3,1]=1)then inc(ncount);
        if(nb[3,1]=0)and(nb[3,2]=1)then inc(ncount);
        if(nb[3,2]=0)and(nb[3,3]=1)then inc(ncount);
        if ncount=1 then c2:=true;
        if(nb[1,2]*nb[3,2]*nb[2,3]=0)then c3:=true;
        if(nb[2,1]*nb[2,3]*nb[3,2]=0)then c4:=true;
        if(c1 and c2 and c3 and c4)then
        begin
          p1[x].rgbtBlue:=255;
          p1[x].rgbtGreen:=255;
          p1[x].rgbtRed:=255;
        end;
      end;
   end;
   image1.Picture.Bitmap.Assign(newBmp);
   newbmp.Free;
end;

procedure TForm1.ActionsobelExecute(Sender: TObject);
var
  bmp1,bmp2:TBitmap;
  p1,p2,p3,p4:pByteArray;
  i,j:integer;
  r,g,b:Byte;
begin
   //Sobel边缘检测
   //采用双缓冲模式
   self.DoubleBuffered:=true;
   //创建Tbitmap对象bmp1,bmp2
   bmp1:=TBitmap.Create;
   bmp1.Assign(Image1.Picture.Bitmap);
   bmp1.PixelFormat:=pf24bit;
   bmp2:=TBitmap.Create;
   bmp2.Assign(bmp1);
   bmp2.PixelFormat:=pf24bit;
   for j:=1 to bmp1.Height-2 do
   begin
     p1:=bmp1.ScanLine[j];
     p2:=bmp1.ScanLine[j-1];
     p3:=bmp1.ScanLine[j];
     p4:=bmp1.ScanLine[j+1];
     for i:=1 to bmp1.Width-2 do
     begin
        /////////////////////////////////////////

     end;
   end;
   Image1.Picture.Bitmap.Assign(bmp1);
   bmp1.Free;bmp2.Free;
end;

procedure TForm1.ActionhytxExecute(Sender: TObject);
begin
   //还原图像
   Image1.Picture.Bitmap.Assign(OldBmp);
end;

procedure TForm1.N2Click(Sender: TObject);
begin
  if OpenPictureDialog1.Execute then
  begin
      Image1.Picture.LoadFromFile(OpenPictureDialog1.FileName);
      OldBmp.Assign(Image1.Picture.Bitmap);
  end;
end;

procedure TForm1.N3Click(Sender: TObject);
begin
   if self.SavePictureDialog1.Execute then
      self.Image1.Picture.SaveToFile(SavePictureDialog1.FileName);
end;

procedure TForm1.O1Click(Sender: TObject);
begin
  close;
end;

procedure TForm1.FormCreate(Sender: TObject);
begin
   OldBmp:=TBitmap.Create;
   if Image1.Picture.GetNamePath<>''then OldBmp.Assign(Image1.Picture.Bitmap);
end;

end.
