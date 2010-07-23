unit calculate;

interface

uses
  Windows, Messages, SysUtils, Variants, Classes, Graphics, Controls, Forms,
  Dialogs, StdCtrls, ExtCtrls, Menus;

type
  TForm1 = class(TForm)
    GroupBox1: TGroupBox;
    Button1: TButton;
    Button2: TButton;
    Button3: TButton;
    Button4: TButton;
    Button5: TButton;
    Button6: TButton;
    Button7: TButton;
    Button8: TButton;
    Button9: TButton;
    Button12: TButton;
    Button13: TButton;
    Button14: TButton;
    Button15: TButton;
    Button16: TButton;
    Button17: TButton;
    Button18: TButton;
    Button11: TButton;
    Button19: TButton;
    Button20: TButton;
    MainMenu1: TMainMenu;
    Medit: TMenuItem;
    Msee: TMenuItem;
    Mhelp: TMenuItem;
    Mcopy: TMenuItem;
    Mpaste: TMenuItem;
    Mclose: TMenuItem;
    Mhelpc: TMenuItem;
    Mline: TMenuItem;
    Mabout: TMenuItem;
    Mstandard: TMenuItem;
    Mscience: TMenuItem;
    Label1: TLabel;
    Panel1: TPanel;
    Edit1: TEdit;
    Button10: TButton;
    procedure Button1Click(Sender: TObject);
    procedure Button2Click(Sender: TObject);
    procedure Button3Click(Sender: TObject);
    procedure Button5Click(Sender: TObject);
    procedure Button4Click(Sender: TObject);
    procedure Button6Click(Sender: TObject);
    procedure Button8Click(Sender: TObject);
    procedure Button7Click(Sender: TObject);
    procedure Button9Click(Sender: TObject);
    procedure Button11Click(Sender: TObject);
    procedure Button14Click(Sender: TObject);
    procedure Button13Click(Sender: TObject);
    procedure Button12Click(Sender: TObject);
    procedure Button15Click(Sender: TObject);
    procedure Button18Click(Sender: TObject);
    procedure Button17Click(Sender: TObject);
    procedure Button19Click(Sender: TObject);
    procedure Button10Click(Sender: TObject);
    procedure FormCreate(Sender: TObject);
    procedure Button16Click(Sender: TObject);
    procedure exittostart(Sender: TObject);
    procedure opexit(Sender: TObject);
    procedure McloseClick(Sender: TObject);
    procedure McopyClick(Sender: TObject);
    procedure MpasteClick(Sender: TObject);
    procedure MaboutClick(Sender: TObject);
  private
    { Private declarations }
  public
    { Public declarations }
  end;

var
  Form1: TForm1;

implementation
uses about;
const
  Max=32767;
var
  opnum:integer;
  op:char;
  copytext:string;
{$R *.dfm}

function getnum():integer;
var
  str:string;
begin
  str:=Form1.Edit1.Text;
  if str<>'' then
  begin
    if pos('-',str)=1 then
    begin
      delete(str,1,1);
      result:=-StrToInt(str);
      exit;
    end;
    result:=StrToInt(str);
  end
  else result:=0;
end;

function operate(a:integer;theta:char;b:integer):integer;
var
  i:integer;
begin
       case theta of
         '+': result:=a+b;
         '-': result:=a-b;
         '*': result:=a*b;
         '/': result:=a div b;
         '^': begin
                result:=1;
                for i:=1 to b do
                 result:=result*a;
              end;
         '%': result:=a mod b;
         else result:=a;
       end;
end;

procedure TForm1.Button1Click(Sender: TObject);
begin
  if pos('0',self.Edit1.Text)=1 then self.Edit1.Text:='';
  self.Edit1.Text:=self.Edit1.Text+'1';
  self.Label1.Caption:=self.Label1.Caption+'1';
end;

procedure TForm1.Button2Click(Sender: TObject);
begin
  if pos('0',self.Edit1.Text)=1 then self.Edit1.Text:='';
  self.Edit1.Text:=self.Edit1.Text+'2';
  self.Label1.Caption:=self.Label1.Caption+'2';
end;

procedure TForm1.Button3Click(Sender: TObject);
begin
  if pos('0',self.Edit1.Text)=1 then self.Edit1.Text:='';
  self.Edit1.Text:=self.Edit1.Text+'3';
  self.Label1.Caption:=self.Label1.Caption+'3';
end;

procedure TForm1.Button5Click(Sender: TObject);
begin
  if pos('0',self.Edit1.Text)=1 then self.Edit1.Text:='';
  self.Edit1.Text:=self.Edit1.Text+'4';
  self.Label1.Caption:=self.Label1.Caption+'4';
end;

procedure TForm1.Button4Click(Sender: TObject);
begin
  if pos('0',self.Edit1.Text)=1 then self.Edit1.Text:='';
  self.Edit1.Text:=self.Edit1.Text+'5';
  self.Label1.Caption:=self.Label1.Caption+'5';
end;

procedure TForm1.Button6Click(Sender: TObject);
begin
  if pos('0',self.Edit1.Text)=1 then self.Edit1.Text:='';
  self.Edit1.Text:=self.Edit1.Text+'6';
  self.Label1.Caption:=self.Label1.Caption+'6';
end;

procedure TForm1.Button8Click(Sender: TObject);
begin
  if pos('0',self.Edit1.Text)=1 then self.Edit1.Text:='';
  self.Edit1.Text:=self.Edit1.Text+'7';
  self.Label1.Caption:=self.Label1.Caption+'7';
end;

procedure TForm1.Button7Click(Sender: TObject);
begin
  if pos('0',self.Edit1.Text)=1 then self.Edit1.Text:='';
  self.Edit1.Text:=self.Edit1.Text+'8';
  self.Label1.Caption:=self.Label1.Caption+'8';
end;

procedure TForm1.Button9Click(Sender: TObject);
begin
  if pos('0',self.Edit1.Text)=1 then self.Edit1.Text:='';
  self.Edit1.Text:=self.Edit1.Text+'9';
  self.Label1.Caption:=self.Label1.Caption+'9';
end;

procedure TForm1.Button11Click(Sender: TObject);
begin
  if pos('0',self.Edit1.Text)=1 then self.Edit1.Text:='';
  self.Edit1.Text:=self.Edit1.Text+'0';
  self.Label1.Caption:=self.Label1.Caption+'0';
end;

procedure TForm1.Button14Click(Sender: TObject);
begin
  if(opnum=Max)then opnum:=getnum()
  else begin
           opnum:=operate(opnum,op,getnum());
           self.Edit1.Text:=IntToStr(opnum);
       end;
  op:='+';
  self.Label1.Caption:=self.Label1.Caption+'+';
end;

procedure TForm1.Button13Click(Sender: TObject);
begin
  if(opnum=Max)then opnum:=getnum()
  else begin
           opnum:=operate(opnum,op,getnum());
           self.Edit1.Text:=IntToStr(opnum);
       end;
  op:='-';
  self.Label1.Caption:=self.Label1.Caption+'-';
end;

procedure TForm1.Button12Click(Sender: TObject);
begin
  if(opnum=Max)then opnum:=getnum()
  else begin
           opnum:=operate(opnum,op,getnum());
           self.Edit1.Text:=IntToStr(opnum);
       end;
  op:='*';
  self.Label1.Caption:=self.Label1.Caption+'*';
end;

procedure TForm1.Button15Click(Sender: TObject);
begin
  if(opnum=Max)then opnum:=getnum()
  else begin
           opnum:=operate(opnum,op,getnum());
           self.Edit1.Text:=IntToStr(opnum);
       end;
  op:='/';
  self.Label1.Caption:=self.Label1.Caption+'/';
end;

procedure TForm1.Button18Click(Sender: TObject);
begin
  if(opnum=Max)then opnum:=getnum()
  else begin
           opnum:=operate(opnum,op,getnum());
           self.Edit1.Text:=IntToStr(opnum);
       end;
  op:='^';
  self.Label1.Caption:=self.Label1.Caption+'^';
end;

procedure TForm1.Button17Click(Sender: TObject);
begin
  if(opnum=Max)then opnum:=getnum()
  else begin
           opnum:=operate(opnum,op,getnum());
           self.Edit1.Text:=IntToStr(opnum);
       end;
  op:='%';
  self.Label1.Caption:=self.Label1.Caption+'%';
end;

procedure TForm1.Button19Click(Sender: TObject);
var
  str:string;
begin
  str:=self.Edit1.Text;
  if pos('-',str)=0 then str:='-'+str
  else delete(str,1,1);
  self.Edit1.Text:=str;
  self.Label1.Caption:=self.Label1.Caption+'-';
end;

procedure TForm1.Button10Click(Sender: TObject);
begin
  self.Edit1.Text:='0';
  opnum:=Max;
  op:=' ';
  self.Label1.Caption:='«Î ‰»Î£∫';
end;

procedure TForm1.FormCreate(Sender: TObject);
begin
  self.Edit1.Text:='0';
  opnum:=Max;
  op:=' ';
 // self.MainMenu1
end;

procedure TForm1.Button16Click(Sender: TObject);
begin
  if(opnum=Max)then opnum:=getnum()
  else begin
           opnum:=operate(opnum,op,getnum());
           self.Edit1.Text:=IntToStr(opnum);
       end;
  self.Label1.Caption:=self.Label1.Caption+'=';
end;

procedure TForm1.exittostart(Sender: TObject);
begin
  opnum:=Max;
  op:=' ';
  self.Edit1.Text:='';
  self.Label1.Caption:='«Î ‰»Î£∫';
end;

procedure TForm1.opexit(Sender: TObject);
begin
  self.Edit1.Text:='';
end;

procedure TForm1.McloseClick(Sender: TObject);
begin
  close;
end;

procedure TForm1.McopyClick(Sender: TObject);
begin
  copytext:=self.Edit1.Text;
end;

procedure TForm1.MpasteClick(Sender: TObject);
begin
   self.Edit1.Text:=copytext;
end;

procedure TForm1.MaboutClick(Sender: TObject);
begin
  AboutBox.Show;
end;

end.
