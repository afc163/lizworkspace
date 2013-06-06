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
    Button1: TButton;
    ListBox1: TListBox;
    Label2: TLabel;
    Memo1: TMemo;
    Label3: TLabel;
    Memo2: TMemo;
    procedure FormCreate(Sender: TObject);
    procedure mnu_HorizontalClick(Sender: TObject);
    procedure mnu_VerticalClick(Sender: TObject);
    procedure mnu_StartColorClick(Sender: TObject);
    procedure mnu_EndColorClick(Sender: TObject);
    procedure FormPaint(Sender: TObject);
    procedure FormResize(Sender: TObject);
    procedure Draw(StartColor:TColor;EndColor:TColor;Direction:Integer);
    procedure Button1Click(Sender: TObject);
    procedure Itemdelete(Sender: TObject);
    procedure resultbtnClick(Sender: TObject);
    procedure flashmenuitemClick(Sender: TObject);
    procedure Timer1Timer(Sender: TObject);
    procedure FormClose(Sender: TObject; var Action: TCloseAction);
  private
    { Private declarations }
  public
    { Public declarations }
  end;

var
  Form1: TForm1;
  StartColor:TColor;
  EndColor:TColor;
  Direction:Integer;

TYPE
  WF=record
     from:string;
     tto:string;
     end;
  Tmytemp=class(Tobject)
  private
     num:integer;
     WFarray:array[1..10] of WF;
  public
     constructor Create;
     procedure addall;
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
  num:=0;
  for i:=1 to 10 do
  begin
    self.WFarray[i].from:='';
    self.WFarray[i].tto:='';
  end;
end;
procedure Tmytemp.addall();
var
  str:string;
  i,j,k:integer;
begin
   for i:=1 to Form1.ListBox1.Count do
   begin
     str:=Form1.ListBox1.Items.Strings[i-1];
     j:=pos('->',str);
     if j<>0 then
     begin
       inc(num);
       k:=1;
       while k<j do
       begin
        self.WFarray[num].from:=self.WFarray[num].from+str[k];  ///////////大写字母
        inc(k);
       end;
       k:=k+2;
       while k<=length(str) do
       begin
        self.WFarray[num].tto:=self.WFarray[num].tto+str[k];
        inc(k);
       end;
     end
     else
     begin
      messagebox(0,'文法错误','错误！',mb_ok+mb_iconexclamation);
      break;
     end;
   end;
end;
procedure Tmytemp.first;
//var
 // i,j:integer;
 //  fromstr,ttostr:string;
begin
  // for i:=1 to num do
  // begin
  //   fromstr:=self.WFarray[i].from;

end;
procedure Tmytemp.follow;
begin
end;
procedure Tmytemp.firstvt;
var
  i,j:integer;
  fromstr,ttostr:string;
begin
    for i:=1 to num do
    begin
    fromstr:=self.WFarray[i].from;
    end;

end;
procedure Tmytemp.lastvt;
begin
end;
destructor Tmytemp.free();
begin
  num:=0;
end;

procedure TForm1.FormCreate(Sender: TObject);
begin
  StartColor:=RGB(200,150,150);
  EndColor:=RGB(100,100,50);
  Direction:=1;
  WenFa:=Tmytemp.Create;
  flashsign:=false;
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

procedure TForm1.Button1Click(Sender: TObject);
begin
  if self.Edit1.Text='' then exit;
  self.ListBox1.Items.Add(self.Edit1.Text);
end;

procedure TForm1.Itemdelete(Sender: TObject);
begin
  self.ListBox1.Items.Delete(self.ListBox1.ItemIndex);
end;

procedure TForm1.resultbtnClick(Sender: TObject);
begin
   WenFa.addall;
   wenFa.first;
   WenFa.follow;
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

end.
