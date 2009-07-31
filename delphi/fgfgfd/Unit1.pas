unit Unit1;

interface

uses
  Windows, Messages, SysUtils, Variants, Classes, Graphics, Controls, Forms,
  Dialogs, Menus, StdCtrls, ExtCtrls;

type
  TForm1 = class(TForm)
    MainMenu1: TMainMenu;
    N1: TMenuItem;
    mnu_Horizontal: TMenuItem;
    mnu_Vertical: TMenuItem;
    N4: TMenuItem;
    mnu_StartColor: TMenuItem;
    mnu_EndColor: TMenuItem;
    ColorDialog1: TColorDialog;
    PopupMenu1: TPopupMenu;
    N2: TMenuItem;
    N3: TMenuItem;
    N5: TMenuItem;
    flashmenuitem: TMenuItem;
    Timer1: TTimer;
    resultbtn: TButton;
    Label1: TLabel;
    Edit1: TEdit;
    Buttonshi: TButton;
    Label2: TLabel;
    Memo1: TMemo;
    Label3: TLabel;
    Memo2: TMemo;
    Editvn: TEdit;
    Label4: TLabel;
    Label5: TLabel;
    Label6: TLabel;
    Editstart: TEdit;
    Label7: TLabel;
    Buttonvn: TButton;
    Buttonvt: TButton;
    Editvt: TEdit;
    Buttonstart: TButton;
    Memo3: TMemo;
    procedure FormCreate(Sender: TObject);
    procedure mnu_HorizontalClick(Sender: TObject);
    procedure mnu_VerticalClick(Sender: TObject);
    procedure mnu_StartColorClick(Sender: TObject);
    procedure mnu_EndColorClick(Sender: TObject);
    procedure FormPaint(Sender: TObject);
    procedure FormResize(Sender: TObject);
    procedure Draw(StartColor:TColor;EndColor:TColor;Direction:Integer);
    procedure ButtonshiClick(Sender: TObject);
    procedure resultbtnClick(Sender: TObject);
    procedure flashmenuitemClick(Sender: TObject);
    procedure Timer1Timer(Sender: TObject);
    procedure FormClose(Sender: TObject; var Action: TCloseAction);
    procedure ButtonstartClick(Sender: TObject);
    procedure ButtonvtClick(Sender: TObject);
    procedure ButtonvnClick(Sender: TObject);
  private
    { Private declarations }
  public
    { Public declarations }
  end;

const
  MAXWFNUM=10;
var
  Form1: TForm1;
  StartColor:TColor;
  EndColor:TColor;
  Direction:Integer;

TYPE
  WF=record
     from:char;
     tto:string;
     end;
  Tmytemp=class(Tobject)
  private
     WFnum:integer;
     WFarray:array[1..MAXWFNUM] of WF;
     Vt:set of char;
     Vn:set of char;
     WFStart:char;
  public
     constructor Create;
     procedure add(str:string);
     procedure show;
     procedure first;
     procedure follow;
     procedure firstvt;
     procedure lastvt;
     destructor free;
  end;

var
  Wenfa:Tmytemp;
  flashsign:boolean;

implementation

{$R *.dfm}
constructor Tmytemp.Create();
var
  i:integer;
begin
  WFnum:=0;
  for i:=1 to MAXWFNUM do
  begin
    self.WFarray[i].from:=' ';
    self.WFarray[i].tto:='';
  end;
  Vt:=[];
  Vn:=[];
  WFstart:=' ';
end;
procedure Tmytemp.add(str:string);
var
  k:integer;
begin
       inc(WFnum);
       k:=1;
       if str[k] in Vn then self.WFarray[WFnum].from:=str[k]
       else
       begin
         messagebox(0,'文法错误非终结符','错误！',mb_ok+mb_iconexclamation);
         exit;
       end;
       k:=k+2;
       while k<=length(str) do
       begin
         if str[k] in Vn then self.WFarray[WFnum].tto:=self.WFarray[WFnum].tto+str[k]
         else
         begin
           messagebox(0,'文法错误终结符','错误！',mb_ok+mb_iconexclamation);
           exit;
         end;
         inc(k);
       end;
end;
procedure Tmytemp.show;
begin
  Form1.Memo3.Lines.Clear;
  if WFstart='' then exit;
  Form1.Memo3.Text:='文法开始符号：'+WFstart;
  if Vt=[] then exit;
//  Form1.Memo3.Text:=Form1.Memo3.Text+'Vt:'+Vt;
//  if Vn=[] then exit;
//  Form1.Memo3.Text:=Form1.Memo3.Text+'Vn:'+Vn;
  if WFnum=0 then exit;
  Form1.Memo3.Text:=Form1.Memo3.Text+'产生式:'
       +self.WFarray[WFnum].from+'->'+self.WFarray[WFnum].tto;
  Form1.Memo3.Show;
end;
procedure Tmytemp.first;
//var
 // i,j:integer;
 //  fromstr,ttostr:string;
begin
  // for i:=1 to num do
  // begin
  //   fromstr:=self.WFarray[i].from;    //假设终结符和非终结符都为单字母

end;
procedure Tmytemp.follow;
begin
end;
procedure Tmytemp.firstvt;
var
  i,j:integer;
  fromstr,ttostr:string;
begin
    for i:=1 to WFnum do
    begin
    fromstr:=self.WFarray[i].from;
    end;

end;
procedure Tmytemp.lastvt;
begin
end;
destructor Tmytemp.free();
begin
  WFnum:=0;
end;

procedure TForm1.FormCreate(Sender: TObject);
begin
  StartColor:=RGB(200,150,150);
  EndColor:=RGB(100,100,50);
  Direction:=1;
  flashsign:=false;

  WenFa:=Tmytemp.Create;
end;

procedure TForm1.mnu_HorizontalClick(Sender: TObject);
begin
  Direction:=0;
  Draw(StartColor,EndColor,Direction);
end;

procedure TForm1.mnu_VerticalClick(Sender: TObject);
begin
  Direction:=1;
  Draw(StartColor,EndColor,Direction);
end;

procedure TForm1.mnu_StartColorClick(Sender: TObject);
begin
  if self.ColorDialog1.Execute then StartColor:=self.ColorDialog1.Color;
  Draw(StartColor,EndColor,Direction);
end;

procedure TForm1.mnu_EndColorClick(Sender: TObject);
begin
  if self.ColorDialog1.Execute then EndColor:=self.ColorDialog1.Color;
  Draw(StartColor,EndColor,Direction);
end;

procedure TForm1.FormPaint(Sender: TObject);
begin
  Draw(StartColor,EndColor,Direction);
end;

procedure TForm1.FormResize(Sender: TObject);
begin
  self.Canvas.Refresh;
  Draw(StartColor,EndColor,Direction);
end;

procedure TForm1.Draw(StartColor:TColor;EndColor:TColor;Direction:Integer);
var
  i:integer;
  Dct:TRect;
  c1,c2,c3:byte;
begin
  if Direction=0 then
  begin
    for i:=0 to self.Width-1 do
    begin
      c1:=GetRValue(StartColor)+Trunc(i*(GetRValue(EndColor)-GetRValue(StartColor))/(self.Width-1));
      c2:=GetGValue(StartColor)+Trunc(i*(GetGValue(EndColor)-GetGValue(StartColor))/(self.Width-1));
      c3:=GetBValue(StartColor)+Trunc(i*(GetBValue(EndColor)-GetBValue(StartColor))/(self.Width-1));
      //每次画矩形的画刷颜色
      Canvas.Brush.Color:=RGB(c1,c2,c3);
      //每次画矩形的区域
      Dct:=Rect(i,0,i+1,self.Height);
      //填充颜色
      Canvas.FillRect(Dct);
    end;
  end
  else
  begin
    for i:=0 to self.Height-1 do
    begin
      c1:=GetRValue(StartColor)+Trunc(i*(GetRValue(EndColor)-GetRValue(StartColor))/(self.Height-1));
      c2:=GetGValue(StartColor)+Trunc(i*(GetGValue(EndColor)-GetGValue(StartColor))/(self.Height-1));
      c3:=GetBValue(StartColor)+Trunc(i*(GetBValue(EndColor)-GetBValue(StartColor))/(self.Height-1));
      Canvas.Brush.Color:=RGB(c1,c2,c3);
      Dct:=Rect(0,i,self.Width,i+1);
      Canvas.FillRect(Dct);
    end;
  end;
end;

procedure TForm1.ButtonshiClick(Sender: TObject);
begin
  if self.Edit1.Text='' then exit;
  WenFa.add(self.Edit1.Text);
end;

procedure TForm1.resultbtnClick(Sender: TObject);
begin

  // WenFa.first;
  // WenFa.follow;
end;

procedure TForm1.flashmenuitemClick(Sender: TObject);
begin
   if not flashsign then flashsign:=true
   else flashsign:=false;
end;

procedure TForm1.Timer1Timer(Sender: TObject);
begin
   FlashWindow(self.Handle,flashsign);
end;

procedure TForm1.FormClose(Sender: TObject; var Action: TCloseAction);
begin
  WenFa.free;
end;

procedure TForm1.ButtonstartClick(Sender: TObject);
begin
  if self.Editstart.Text='' then exit;
  Wenfa.WFStart:=self.Editstart.Text[1];
  Wenfa.show;
end;

procedure TForm1.ButtonvtClick(Sender: TObject);
begin
  if self.Editvt.Text='' then exit;
  WenFa.Vt:=WenFa.Vt+[self.Editvt.text[1]];
  WenFa.show;
end;

procedure TForm1.ButtonvnClick(Sender: TObject);
begin
  if self.Editvt.Text='' then exit;
  WenFa.Vn:=WenFa.Vn+[self.Editvn.text[1]];
  WenFa.show;
end;

end.
