unit calculate;

interface

uses
  Windows, Messages, SysUtils, Variants, Classes, Graphics, Controls, Forms,
  Dialogs, StdCtrls, ExtCtrls;

type
  TForm1 = class(TForm)
    GroupBox1: TGroupBox;
    Button1: TButton;
    Panel1: TPanel;
    Edit1: TEdit;
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
    Button10: TButton;
    Button11: TButton;
    Button19: TButton;
    Button20: TButton;
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
    procedure Button16Click(Sender: TObject);
    procedure Button20Click(Sender: TObject);
  private
    { Private declarations }
  public
    { Public declarations }
  end;

var
  Form1: TForm1;

implementation

uses stackop,stacknum;
const
  MAXnum=9;
  op:array[1..MAXnum] of char=('+','-','*','/','(',')','#','^','%');
  precede:array[1..MAXnum,1..MAXnum]of char=(('>','>','<','<','<','>','>','<','<'),
                                             ('>','>','<','<','<','>','>','<','<'),
                                             ('>','>','>','>','<','>','>','<','>'),
                                             ('>','>','>','>','<','>','>','<','>'),
                                             ('<','<','<','<','<','=',' ','<','<'),
                                             ('>','>','>','>',' ','>','>','>','>'),
                                             ('<','<','<','<','<',' ',' ','<','<'),
                                             ('>','>','>','>','<','>','>','>','>'),
                                             ('>','>','>','>','<','>','>','<','>'));
var
  opstack:Tstackop;
  numstack:Tstacknum;
  preceF:array[1..MAXnum] of integer;
  preceG:array[1..MAXnum] of integer;

{$R *.dfm}

procedure TForm1.Button1Click(Sender: TObject);
begin
  self.Edit1.Text:=self.Edit1.Text+'1';
end;

procedure TForm1.Button2Click(Sender: TObject);
begin
  self.Edit1.Text:=self.Edit1.Text+'2';
end;

procedure TForm1.Button3Click(Sender: TObject);
begin
  self.Edit1.Text:=self.Edit1.Text+'3';
end;

procedure TForm1.Button5Click(Sender: TObject);
begin
  self.Edit1.Text:=self.Edit1.Text+'4';
end;

procedure TForm1.Button4Click(Sender: TObject);
begin
  self.Edit1.Text:=self.Edit1.Text+'5';
end;

procedure TForm1.Button6Click(Sender: TObject);
begin
  self.Edit1.Text:=self.Edit1.Text+'6';
end;

procedure TForm1.Button8Click(Sender: TObject);
begin
  self.Edit1.Text:=self.Edit1.Text+'7';
end;

procedure TForm1.Button7Click(Sender: TObject);
begin
  self.Edit1.Text:=self.Edit1.Text+'8';
end;

procedure TForm1.Button9Click(Sender: TObject);
begin
  self.Edit1.Text:=self.Edit1.Text+'9';
end;

procedure TForm1.Button11Click(Sender: TObject);
begin
  self.Edit1.Text:=self.Edit1.Text+'0';
end;

procedure TForm1.Button14Click(Sender: TObject);
begin
  self.Edit1.Text:=self.Edit1.Text+'+';
end;

procedure TForm1.Button13Click(Sender: TObject);
begin
  self.Edit1.Text:=self.Edit1.Text+'-';
end;

procedure TForm1.Button12Click(Sender: TObject);
begin
  self.Edit1.Text:=self.Edit1.Text+'*';
end;

procedure TForm1.Button15Click(Sender: TObject);
begin
  self.Edit1.Text:=self.Edit1.Text+'/';
end;

procedure TForm1.Button18Click(Sender: TObject);
begin
  self.Edit1.Text:=self.Edit1.Text+'^';
end;

procedure TForm1.Button17Click(Sender: TObject);
begin
  self.Edit1.Text:=self.Edit1.Text+'%';
end;

procedure TForm1.Button19Click(Sender: TObject);
begin
  self.Edit1.Text:=self.Edit1.Text+'(';
end;

procedure TForm1.Button10Click(Sender: TObject);
begin
  self.Edit1.Text:='';
end;

function precedeF(a:char;b:char):char;
var
  i,j:integer;
begin
  result:=' ';
  i:=1;j:=1;
  while(op[i]<>a)do inc(i);
  while(op[j]<>b)do inc(j);
  if preceF[i]>preceG[j] then result:='>';
  if preceF[i]<preceG[j] then result:='<';
  if preceF[i]=preceG[j] then result:='=';
end;

procedure Convert;
var
  i,j,max:integer;
  sign:boolean;
begin
  for i:=1 to MAXnum do
  begin
    preceF[i]:=1;
    preceG[i]:=1;
  end;
  sign:=true; max:=1;
  while sign do
  begin
    sign:=false;
    for i:=1 to MAXnum do
    for j:=1 to MAXnum do
      case precede[i,j] of
      '<' :if preceF[i]>=preceG[j] then
           begin
                preceG[j]:=preceF[i]+1;
                sign:=true;
           end;
      '>' :if preceF[i]<=preceG[j] then
           begin
                preceF[i]:=preceG[j]+1;
                sign:=true;
           end;
      '=' :if preceF[i]<>preceG[j] then
           begin
                if preceF[i]>preceG[j] then max:=preceF[i];
                if preceF[i]<preceG[j] then max:=preceG[j];
                preceF[i]:=Max;
                preceG[j]:=Max;
                sign:=true;
           end;
      ' ' : ;
      end;
    end;
end;

function precedeT(a:char;b:char):char;
var
  i,j:integer;
begin
  i:=1;j:=1;
  while(op[i]<>a)do inc(i);
  while(op[j]<>b)do inc(j);
  result:=precede[i,j];
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
         else result:=32767;
       end;
end;

procedure TForm1.Button16Click(Sender: TObject);
var
  str:string;
  i,number,op1,op2:integer;
  threta:char;
begin
    opstack:=Tstackop.initstack;
    numstack:=Tstacknum.initstack;
    opstack.push('#');

    str:=self.Edit1.Text+'#';
    convert;

    i:=1;
    while not((str[i]='#')and(opstack.gettop='#'))do
    begin
      if str[i] in ['0'..'9'] then
      begin
        number:=0;
        while(str[i] in ['0'..'9'])do
        begin
          number:=number*10+ord(str[i])-ord('0');
          inc(i);
        end;
        if numstack.push(number)=ERRORnum then
        begin
          messagebox(0,'À„¡ø’ª¬˙£°»±…ŸÀ„∑˚','¥ÌŒÛ£°',mb_ok+mb_iconexclamation);
          exit;
        end;
      end;
      if str[i] in ['+','-','*','/','%','^','(',')','#'] then
          case precedeT(opstack.gettop(),str[i]) of   //////////////////////////////////////////////
                    '<':begin
                          if opstack.push(str[i])=ERRORop then
                          begin
                            messagebox(0,'À„∑˚’ª¬˙£°»±…ŸÀ„¡ø','¥ÌŒÛ£°',mb_ok+mb_iconexclamation);
                            exit;
                          end;
                          inc(i);
                        end;
                    '=':begin
                          threta:=opstack.pop();
                          if threta=ERRORop then
                          begin
                            messagebox(0,'À„∑˚’ªø’£°»±…ŸÀ„∑˚','¥ÌŒÛ£°',mb_ok+mb_iconexclamation);
                            exit;
                          end;
                          inc(i);
                        end;
                    '>':begin
                          op2:=numstack.pop;
                          if op2=ERRORnum then
                          begin
                            messagebox(0,'À„¡ø’ªø’£°»±…ŸÀ„¡ø','¥ÌŒÛ£°',mb_ok+mb_iconexclamation);
                            exit;
                          end;
                          op1:=numstack.pop;
                          if op1=ERRORnum then
                          begin
                            messagebox(0,'À„¡ø’ªø’£°»±…ŸÀ„¡ø','¥ÌŒÛ£°',mb_ok+mb_iconexclamation);
                            exit;
                          end;
                          threta:=opstack.pop();
                          if threta=ERRORop then
                          begin
                            messagebox(0,'À„∑˚’ªø’£°»±…ŸÀ„¡ø','¥ÌŒÛ£°',mb_ok+mb_iconexclamation);
                            exit;
                          end;
                          numstack.push(operate(op1,threta,op2));
                        end;
                    ' ':begin
                          messagebox(0,'±Ì¥Ô Ω¥ÌŒÛ','¥ÌŒÛ£°',mb_ok+mb_iconexclamation);
                          exit;
                        end;
          end;
    end;
    op1:=numstack.pop;
    if op1=ERRORnum then
    begin
      messagebox(0,'À„¡ø’ªø’£°»±…ŸÀ„¡ø','¥ÌŒÛ£°',mb_ok+mb_iconexclamation);
      exit;
    end;
    self.Edit1.Text:=IntToStr(op1);
end;

procedure TForm1.Button20Click(Sender: TObject);
begin
  self.Edit1.Text:=self.Edit1.Text+')';
end;

end.
