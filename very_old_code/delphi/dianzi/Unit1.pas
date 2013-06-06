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
    //������ѡ���ĵ������ı߿���ʽ
    procedure SetBChecked(Sender:Tobject);
    //������ѡ���ĵ�����������Ƭ������
    procedure SetSChecked(Sender:Tobject);
  private
    { Private declarations }
    //������ʼʱ�����
    StartTime:Tdatetime;
    //��������ʱ�����
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
   //ȡ�����Զ���߿���ʽ���˵��е������Ӳ˵���Ĺ�
   for i:=0 to TMenuItem(Sender).Parent.Count-1 do
   begin
     TMenuItem(Sender).Parent[i].Checked:=false;
   end;
   //�ڵ�ǰѡ����Ӳ˵���ǰ��
   TMenuItem(Sender).Checked:=true;
   //���õ������ı߿���ʽ
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
  //��ʾʱ��
  LCDNumber1.Value:=FormatDateTime('nn:ss:zzz',ElapsedTime+Now-StartTime);
end;

procedure TForm1.ButtonstartClick(Sender: TObject);
begin
  //��ʼʱ��Ϊ��ǰʱ��
  StartTime:=Now;
  //����ʱ��Ϊ0
  ElapsedTime:=0;
  //self.LCDNumber1.SegmentStyle:=ssFilled;
  //�򿪶�ʱ��
  Timer1.Enabled:=true;
  //���ð�ť����
  ButtonStart.Enabled:=false;
  Buttonhold.Enabled:=true;
  Buttonkeepon.Enabled:=false;
  Buttonstop.Enabled:=true;
  Buttonhold.SetFocus;
end;

procedure TForm1.ButtonholdClick(Sender: TObject);
begin
  Timer1.Enabled:=false;
  //���ð�ť����
  ButtonStart.Enabled:=false;
  Buttonhold.Enabled:=false;
  Buttonkeepon.Enabled:=true;
  Buttonstop.Enabled:=true;
  Buttonkeepon.SetFocus;
end;

procedure TForm1.ButtonkeeponClick(Sender: TObject);
begin
  //��ʼʱ��Ϊ��ǰʱ��
  //StartTime:=Now;
  //�򿪶�ʱ��
  Timer1.Enabled:=true;
  //���ð�ť����
  ButtonStart.Enabled:=false;
  Buttonhold.Enabled:=true;
  Buttonkeepon.Enabled:=false;
  Buttonstop.Enabled:=true;
  Buttonhold.SetFocus;
end;

procedure TForm1.ButtonstopClick(Sender: TObject);
begin
  //�ض�ʱ��
  Timer1.Enabled:=false;
  //self.LCDNumber1.SegmentStyle:=ssOutline;
  //���ð�ť����
  ButtonStart.Enabled:=true;
  Buttonhold.Enabled:=false;
  Buttonkeepon.Enabled:=false;
  Buttonstop.Enabled:=false;
  Buttonstart.SetFocus;
end;

end.
