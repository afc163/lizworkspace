unit pltread;

interface

uses
  Windows, Messages, SysUtils, Variants, Classes, Graphics, Controls, Forms,
  Dialogs, ExtCtrls, Menus, jpeg, StdCtrls, ComCtrls, ExtDlgs;

type
  POne_Point=^TOne_Point;
  TOne_Point=RECORD
     x,y:integer;
     nextpoint:POne_Point;
     end;
  POne_Line=^TOne_Line;
  TOne_Line=RECORD
     firstx,firsty:integer;
     firstpoint:POne_Point;
     nextline:POne_Line;
     end;

  TForm1 = class(TForm)
    MainMenu1: TMainMenu;
    PopupMenu1: TPopupMenu;
    F1: TMenuItem;
    O1: TMenuItem;
    N1: TMenuItem;
    X1: TMenuItem;
    H1: TMenuItem;
    S1: TMenuItem;
    N2: TMenuItem;
    A1: TMenuItem;
    V1: TMenuItem;
    C1: TMenuItem;
    L1: TMenuItem;
    W1: TMenuItem;
    R1: TMenuItem;
    N3: TMenuItem;
    R2: TMenuItem;
    C2: TMenuItem;
    L2: TMenuItem;
    W2: TMenuItem;
    N4: TMenuItem;
    OpenDialog1: TOpenDialog;
    ColorDialog1: TColorDialog;
    Npzx: TMenuItem;
    Ndx: TMenuItem;
    Ndhx: TMenuItem;
    Nsxx: TMenuItem;
    Nsdhx: TMenuItem;
    Nnssx: TMenuItem;
    Nhbk: TMenuItem;
    StatusBar1: TStatusBar;
    Image1: TImage;
    ListBox1: TListBox;
    SavePictureDialog1: TSavePictureDialog;
    S2: TMenuItem;
    C3: TMenuItem;
    dfd1: TMenuItem;
    N5: TMenuItem;
    N6: TMenuItem;
    N7: TMenuItem;
    N8: TMenuItem;
    N9: TMenuItem;
    N10: TMenuItem;
    procedure O1Click(Sender: TObject);
    procedure A1Click(Sender: TObject);
    procedure X1Click(Sender: TObject);
    procedure R1Click(Sender: TObject);
    procedure C1Click(Sender: TObject);
    procedure L1Click(Sender: TObject);
    procedure W1Click(Sender: TObject);
    procedure S1Click(Sender: TObject);
    procedure S2Click(Sender: TObject);
    procedure FormResize(Sender: TObject);
    procedure C3Click(Sender: TObject);
    procedure FormCreate(Sender: TObject);
  private
    { Private declarations }
    Minx,Miny,Maxx,Maxy:integer;
    Head,Tail:POne_Line;
    Tail1:POne_Point;
    tempPoint:POne_Point;
    tempLine:POne_Line;
    Sx,Sy:double;
    procedure init;
    procedure OnelineProcess(str:string);
    procedure Pltread;
    procedure Plttograph;
  public
    { Public declarations }
  end;

var
  Form1: TForm1;

implementation

uses about, Unitline;

{$R *.dfm}
{$R MYICON.RES}

procedure TForm1.init;
begin
   Minx:=32767;
   Miny:=32767;
   Maxx:=-32767;
   Maxy:=-32767;
   Head:=nil;Tail:=nil;Tail1:=nil;
   self.ListBox1.Clear;
   self.S2.Enabled:=false;
end;

procedure TForm1.OnelineProcess(str:string);
var
  i:integer;
  s,tempx,tempy:string;
begin
  i:=3;
  s:=s+str[1]+str[2];
  if s='IN' then
  begin
    //初始化
  end
  else if s='VS' then
  begin
    //vs
  end
  else if s='WU' then
  begin
    //WU
  end
  else if s='PW' then
  begin
    //PenWidth
    //self.Canvas.Pen.Width:=;
  end
  else if s='SP' then
  begin
    //SelectPen
    //self.Canvas.Pen.Style:=ord(s[i])-ord('0');
  end
  else if s='PU' then
  begin
    //PenUp
    while str[i]<>' ' do   //x
    begin
      tempx:=tempx+str[i];
      inc(i);
    end;
    inc(i);
    while str[i]<>';' do   //y
    begin
      tempy:=tempy+str[i];
      inc(i);
    end;
    new(tempLine);
    tempLine.firstx:=StrToInt(tempx);
    tempLine.firsty:=StrToInt(tempy);
    tempLine.firstpoint:=nil;
    tempLine.nextline:=nil;
    if Tail<>nil then Tail.nextline:=tempLine;
    if Head=nil then Head:=tempLine;
    Tail:=tempLine;                  //生成线链表
    Tail1:=Tail.firstpoint;

    //比较大小，查找Minx,Miny,Maxx,Maxy
    if tempLine.firstx>Maxx then Maxx:=tempLine.firstx;
    if tempLine.firsty>Maxy then Maxy:=tempLine.firsty;
    if tempLine.firstx<Minx then Minx:=tempLine.firstx;
    if tempLine.firsty<Miny then Miny:=tempLine.firsty;
  end
  else if s='PD' then
  begin
    //PenDown
    while str[i]<>' ' do   //x
    begin
      tempx:=tempx+str[i];
      inc(i);
    end;
    inc(i);
    while str[i]<>';' do   //y
    begin
      tempy:=tempy+str[i];
      inc(i);
    end;
    new(tempPoint);
    tempPoint.x:=StrToInt(tempx);
    tempPoint.y:=StrToInt(tempy);
    tempPoint.nextpoint:=nil;
    if Tail1<>nil then Tail1.nextpoint:=tempPoint;
    if Tail.firstpoint=nil then Tail.firstpoint:=tempPoint;
    Tail1:=tempPoint;                  //生成点链表
    //比较大小，查找Minx,Miny,Maxx,Maxy
    if tempPoint.x>Maxx then Maxx:=tempPoint.x;
    if tempPoint.y>Maxy then Maxy:=tempPoint.y;
    if tempPoint.x<Minx then Minx:=tempPoint.x;
    if tempPoint.y<Miny then Miny:=tempPoint.y;
  end;
end;

procedure TForm1.Pltread;
var
   filename:textfile;
   oneline:string;
begin
   assignfile(filename,self.OpenDialog1.FileName);
   reset(filename);
   while not eof(filename) do
   begin
      readln(filename,oneline);
      OnelineProcess(oneline);
   end;
   tempPoint:=nil;tempLine:=nil;
end;

procedure TForm1.Plttograph;
var
  TP:POne_Point;
  TL:POne_Line;
  x,y:integer;
procedure xyprocedure;
begin
       x:=x-Minx;y:=y-Miny;
       if Sx<Sy then
       begin
          x:=trunc(x*Sx);
          y:=trunc(y*Sx);
       end
       else
       begin
          x:=trunc(x*Sy);
          y:=trunc(y*Sy);
       end;
end;
begin
    Sx:=(image1.Picture.Width-5) / (Maxx-Minx);
    Sy:=(image1.Picture.Height-5) / (Maxy-Miny);
    TL:=Head;
    while TL<>nil do
    begin
       x:=TL.firstx;y:=TL.firsty;
       xyprocedure;
       Image1.Canvas.MoveTo(x,Image1.Picture.Height-5-y);
       self.ListBox1.Items.Add(inttostr(x)+' , '+inttostr(Image1.Picture.Height-y));
       TP:=TL.firstpoint;
       while TP<>nil do
       begin
         x:=TP.x;y:=TP.y;
         xyprocedure;
         Image1.Canvas.LineTo(x,Image1.Picture.Height-5-y);
         self.ListBox1.Items.Add(inttostr(x)+' , '+inttostr(Image1.Picture.Height-y));
         TP:=TP.nextpoint;
       end;
       TL:=TL.nextline;
    end;
end;

procedure TForm1.O1Click(Sender: TObject);
begin
   if self.OpenDialog1.Execute then
   begin
     self.init;
     self.StatusBar1.Panels[0].Text:='已打开文件:'+OpenDialog1.FileName;
     self.StatusBar1.Panels[1].Text:='正在处理，请稍候！';
     //读取PLT文件
     Pltread;
     Plttograph;
     self.StatusBar1.Panels[1].Text:='处理完成！';
     self.S2.Enabled:=true;
   end;
end;

procedure TForm1.A1Click(Sender: TObject);
begin
   AboutBox.Show;
end;

procedure TForm1.X1Click(Sender: TObject);
begin
  close;
end;

procedure TForm1.R1Click(Sender: TObject);
var
  Oldcolor:TColor;
  tempRect:TRect;
begin
  //refresh//先清空画布
  Oldcolor:=Image1.Canvas.Pen.Color;
  Image1.Canvas.Pen.Color:=clWhite;
  tempRect.Left:=0;
  tempRect.Top:=0;
  tempRect.Right:=Image1.Left+Image1.Width;
  tempRect.Bottom:=Image1.Top+Image1.Height+5;
  Image1.Canvas.FillRect(tempRect);
  Image1.Canvas.Pen.Color:=Oldcolor;

  self.ListBox1.Clear;
  self.StatusBar1.Panels[1].Text:='正在处理，请稍候！';
  //self.Canvas.Pen.Mode:=pmNotXor;//擦除以前的
  //image1.Repaint;
  Plttograph;
  //self.Canvas.Refresh;
  self.StatusBar1.Panels[1].Text:='处理完成！';
  self.S2.Enabled:=true;
end;

procedure TForm1.C1Click(Sender: TObject);
begin
  //选择颜色
  if ColorDialog1.Execute then
  begin
      image1.Canvas.Pen.Color:=ColorDialog1.Color;
      self.R1Click(Sender);
  end;
end;

procedure TForm1.L1Click(Sender: TObject);
var
  i:integer;
begin
  //选择线形
  for i:=0 to TMenuItem(Sender).Parent.Count-1 do
    TMenuItem(Sender).Parent[i].Checked:=false;
  TMenuItem(Sender).Checked:=true;
  i:=TMenuItem(Sender).Tag;
  case i of
  0: image1.Canvas.Pen.Style:=psDash;
  1: image1.Canvas.Pen.Style:=psDot;
  2: image1.Canvas.Pen.Style:=psDashDot;
  3: image1.Canvas.Pen.Style:=psInsideFrame;
  4: image1.Canvas.Pen.Style:=psDashDotDot;
  5: image1.Canvas.Pen.Style:=psSolid;
  6: image1.Canvas.Pen.Style:=psClear;
  end;
  self.R1Click(Sender);
end;

procedure TForm1.W1Click(Sender: TObject);
begin
//选择线宽
  Formline.Show;
end;

procedure TForm1.S1Click(Sender: TObject);
begin
  showmessage('未完成！');
end;

procedure TForm1.S2Click(Sender: TObject);
var
 str:string;
begin
  if self.SavePictureDialog1.Execute then
  begin
    case self.SavePictureDialog1.FilterIndex of
    1: str:='.jpg';
    2: str:='.jpg';
    3: str:='.jpeg';
    4: str:='.bmp';
    5: str:='.ico';
    6: str:='.emf';
    7: str:='.wmf';
    end;
      self.Image1.Picture.SaveToFile(self.SavePictureDialog1.FileName+str);
      self.StatusBar1.Panels[1].Text:='保存完毕！';
      self.StatusBar1.Panels[0].Text:='已保存到'+SavePictureDialog1.FileName+str;
      self.S2.Enabled:=false;
  end;
end;

procedure TForm1.FormResize(Sender: TObject);
var
  height,width:integer;
begin
  height:=self.Height;
  width:=self.Width;
  StatusBar1.Width:=width;
  Image1.Width:=width-110;
  Image1.Height:=height-90;
  ListBox1.Left:=Image1.Left+width-110+10;
  ListBox1.Height:=height-90;
end;

procedure TForm1.C3Click(Sender: TObject);
var
  Oldcolor:TColor;
  TP,TP1:POne_Point;
  TL,TL1:POne_Line;
  tempRect:TRect;
begin
  //清空画布                                        //Image1.Width   Image1.Height
  //showmessage(inttostr(Image1.Picture.Height)+';'+inttostr(Image1.Picture.Width));
  Oldcolor:=Image1.Canvas.Pen.Color;
  Image1.Canvas.Pen.Color:=clWhite;
  //self.R1Click(Sender);
  tempRect.Left:=0;
  tempRect.Top:=0;
  tempRect.Right:=Image1.Left+Image1.Width;
  tempRect.Bottom:=Image1.Top+Image1.Height;
  Image1.Canvas.FillRect(tempRect);
  Image1.Canvas.Pen.Color:=Oldcolor;
  self.StatusBar1.Panels[1].Text:='已清空！';
  self.StatusBar1.Panels[0].Text:='';

  TL:=Head;
  while TL<>nil do
    begin
       TP:=TL.firstpoint;
       while TP<>nil do
       begin
         TP1:=TP.nextpoint;
         dispose(TP);
         TP:=TP1;
       end;
       TL1:=TL.nextline;
       dispose(TL);
       TL:=TL1;
    end;
  self.init;
end;

procedure TForm1.FormCreate(Sender: TObject);
begin
   self.init;
   image1.Canvas.Pen.Color:=clBlack;
end;

end.
