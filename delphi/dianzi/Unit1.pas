unit Unit1;

interface

uses
  SysUtils, Types, Classes, Variants, QTypes, QGraphics, QControls, QForms, 
  QDialogs, QStdCtrls, QMenus, QExtCtrls;
type
  TForm1 = class(TForm)
    LCDNumber1: TLCDNumber;
    Timer1: TTimer;
    PopupMenu1: TPopupMenu;
    Buttonstart: TButton;
    N1: TMenuItem;
    N2: TMenuItem;
    N3: TMenuItem;
    N4: TMenuItem;
    N5: TMenuItem;
    N6: TMenuItem;
    N7: TMenuItem;
    N8: TMenuItem;
    N9: TMenuItem;
    N10: TMenuItem;
    N11: TMenuItem;
    N12: TMenuItem;
    N13: TMenuItem;
    N14: TMenuItem;
    Buttonhold: TButton;
    Buttonkeepon: TButton;
    Buttonstop: TButton;
    procedure Timer1Timer(Sender: TObject);
    procedure ButtonstartClick(Sender: TObject);
    procedure ButtonholdClick(Sender: TObject);
    procedure ButtonkeeponClick(Sender: TObject);
    procedure ButtonstopClick(Sender: TObject);
    //设置已选定的电子秒表的边框样式
    procedure SetBChecked(Sender:Tobject);
    //设置已选定的电子秒表的数字片段类型
    procedure SetSChecked(Sender:Tobject);
  private
    { Private declarations }
    //声明开始时间变量
    StartTime:Tdatetime;
    //声明消逝时间变量
    ElapsedTime:Tdatetime;
  public
    { Public declarations }
  end;

var
  Form1: TForm1;
implementation

{$R *.xfm}
{$R MYICON.RES}

procedure TForm1.SetBChecked(Sender:Tobject);
var
  i:integer;
begin
   //取消‘自定义边框样式’菜单中的所有子菜单项的勾
   for i:=0 to TMenuItem(Sender).Parent.Count-1 do
   begin
     TMenuItem(Sender).Parent[i].Checked:=false;
   end;
   //在当前选择的子菜单项前打勾
   TMenuItem(Sender).Checked:=true;
   //设置电子秒表的边框样式
   //LCDNumber1.BorderStyle:=TBorderStyle(TMenuItem(Sender).Tag);
   i:=TMenuItem(Sender).Tag;
   //showmessage(inttostr(i));
   case i of
   0: LCDNumber1.BorderStyle:=bsNone;
   1: LCDNumber1.BorderStyle:=bsSingle;
   2: LCDNumber1.BorderStyle:=bsDouble;
   3: LCDNumber1.BorderStyle:=bsSunken3d;
   4: LCDNumber1.BorderStyle:=bsRaised3d;
   5: LCDNumber1.BorderStyle:=bsRaisedPanel;
   6: LCDNumber1.BorderStyle:=bsSunkenPanel;
   7: LCDNumber1.BorderStyle:=bsEtched;
   8: LCDNumber1.BorderStyle:=bsEmbossed;
   end;
end;

procedure TForm1.SetSChecked(Sender:Tobject);
var
  i:integer;
begin
  for i:=0 to TMenuItem(Sender).Parent.Count-1 do
  begin
    TMenuItem(Sender).Parent[i].Checked:=false;
  end;
  TMenuItem(Sender).Checked:=true;
  LCDNumber1.SegmentStyle:=TLCDSegmentStyle(TMenuItem(Sender).Tag);
end;

procedure TForm1.Timer1Timer(Sender: TObject);
begin
  //显示时间
  LCDNumber1.Value:=FormatDateTime('nn:ss:zzz',ElapsedTime+Now-StartTime);
end;

procedure TForm1.ButtonstartClick(Sender: TObject);
begin
  //开始时间为当前时间
  StartTime:=Now;
  //消逝时间为0
  ElapsedTime:=0;
  //self.LCDNumber1.SegmentStyle:=ssFilled;
  //打开定时器
  Timer1.Enabled:=true;
  //设置按钮属性
  ButtonStart.Enabled:=false;
  Buttonhold.Enabled:=true;
  Buttonkeepon.Enabled:=false;
  Buttonstop.Enabled:=true;
  Buttonhold.SetFocus;
end;

procedure TForm1.ButtonholdClick(Sender: TObject);
begin
  Timer1.Enabled:=false;
  //设置按钮属性
  ButtonStart.Enabled:=false;
  Buttonhold.Enabled:=false;
  Buttonkeepon.Enabled:=true;
  Buttonstop.Enabled:=true;
  Buttonkeepon.SetFocus;
end;

procedure TForm1.ButtonkeeponClick(Sender: TObject);
begin
  //开始时间为当前时间
  //StartTime:=Now;
  //打开定时器
  Timer1.Enabled:=true;
  //设置按钮属性
  ButtonStart.Enabled:=false;
  Buttonhold.Enabled:=true;
  Buttonkeepon.Enabled:=false;
  Buttonstop.Enabled:=true;
  Buttonhold.SetFocus;
end;

procedure TForm1.ButtonstopClick(Sender: TObject);
begin
  //关定时器
  Timer1.Enabled:=false;
  //self.LCDNumber1.SegmentStyle:=ssOutline;
  //设置按钮属性
  ButtonStart.Enabled:=true;
  Buttonhold.Enabled:=false;
  Buttonkeepon.Enabled:=false;
  Buttonstop.Enabled:=false;
  Buttonstart.SetFocus;
end;

end.
