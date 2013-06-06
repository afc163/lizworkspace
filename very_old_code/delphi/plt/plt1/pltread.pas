unit pltread;

interface

uses
  Windows, Messages, SysUtils, Variants, Classes, Graphics, Controls, Forms,
  Dialogs, ExtCtrls, Menus, jpeg, StdCtrls, ComCtrls;

type
  POne_Point=^TOne_Point;
  TOne_Point=class(Tobject)
     x,y:integer;
     nextpoint:POne_Point;
     end;
  POne_Line=^TOne_Line;
  TOne_Line=class(Tobject)
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
    procedure O1Click(Sender: TObject);
    procedure A1Click(Sender: TObject);
    procedure X1Click(Sender: TObject);
    procedure R1Click(Sender: TObject);
    procedure C1Click(Sender: TObject);
    procedure L1Click(Sender: TObject);
    procedure W1Click(Sender: TObject);
    procedure S1Click(Sender: TObject);
    procedure FormCreate(Sender: TObject);
  private
    { Private declarations }
    Minx,Miny,Maxx,Maxy:integer;
    Head,Tail:POne_Line;
    Tail1:POne_Point;
    tempPoint:TOne_Point;
    tempLine:TOne_Line;
    Sx,Sy:double;
    procedure OnelineProcess(str:string);
    procedure print;
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
    tempLine:=TOne_Line.Create;
    tempLine.firstx:=StrToInt(tempx);
    tempLine.firsty:=StrToInt(tempy);
    tempLine.firstpoint:=nil;
    tempLine.nextline:=nil;
    if Tail<>nil then Tail.nextline:=@tempLine;
    if Head=nil then Head:=@tempLine;
    Tail:=@tempLine;                  //生成线链表
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
    tempPoint:=TOne_Point.Create;
    tempPoint.x:=StrToInt(tempx);
    tempPoint.y:=StrToInt(tempy);
    tempPoint.nextpoint:=nil;
    if Tail1<>nil then Tail1.nextpoint:=@tempPoint;
    if Tail.firstpoint=nil then Tail.firstpoint:=@tempPoint;
    Tail1:=@tempPoint;                  //生成点链表
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
end;


procedure TForm1.print;
var
  TP:POne_Point;
  TL:POne_Line;
begin
    TL:=Head;
    while TL<>nil do
    begin
       self.ListBox1.Items.Add(inttostr(TL.firstx)+';'+inttostr(TL.firsty));
       TP:=TL.firstpoint;
       while TP<>nil do
       begin
         self.ListBox1.Items.Add(inttostr(TP.x)+';'+inttostr(TP.y));
         TP:=TP.nextpoint;
       end;
       TL:=TL.nextline;
    end;
end;


procedure TForm1.Plttograph;
var
  TP:POne_Point;
  TL:POne_Line;
  x,y:integer;
procedure xyprocedure;
begin
       x:=x-Minx;y:=y-Miny;
       if Sx>Sy then
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
    Sx:=image1.Width / (Maxx-Minx);
    Sy:=image1.Height / (Maxy-Miny);
    TL:=Head;

    while TL<>nil do
    begin
       x:=TL.firstx;y:=TL.firsty;
       xyprocedure;
       Image1.Canvas.MoveTo(x,Image1.Height-y);
       TP:=TL.firstpoint;
       while TP<>nil do
       begin
         x:=TP.x;y:=TP.y;
         xyprocedure;
         Image1.Canvas.LineTo(x,Image1.Height-y);
         TP:=TP.nextpoint;
       end;
       TL:=TL.nextline;
    end;
end;

procedure TForm1.O1Click(Sender: TObject);
begin
   if self.OpenDialog1.Execute then
   begin
     self.Caption:='图像读取－－'+self.OpenDialog1.FileName;
     //读取PLT文件
     Pltread;
     print;
     //Plttograph;
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
begin
  //refresh
  image1.Repaint;
  image1.Canvas.MoveTo(0,0);
  image1.Canvas.LineTo(100,100);
  image1.Canvas.Refresh;
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

procedure TForm1.FormCreate(Sender: TObject);
begin
   image1.Canvas.Pen.Color:=clBlack;
   image1.Canvas.Pen.Width:=1;
   image1.Canvas.Create.Pen.Style:=psSolid;
   Minx:=32767;
   Miny:=32767;
   Maxx:=-32767;
   Maxy:=-32767;
end;

end.
