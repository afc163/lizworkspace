unit lianlian;

interface

uses
  Windows, Messages, SysUtils, Variants, Classes, Graphics, Controls, Forms,
  Dialogs, StdCtrls, Buttons, ExtCtrls, Menus;

type
  TForm1 = class(TForm)
    GroupBox1: TGroupBox;
    BitBtn1: TBitBtn;
    BitBtn2: TBitBtn;
    BitBtn3: TBitBtn;
    BitBtn4: TBitBtn;
    BitBtn5: TBitBtn;
    BitBtn6: TBitBtn;
    BitBtn7: TBitBtn;
    BitBtn8: TBitBtn;
    BitBtn9: TBitBtn;
    BitBtn10: TBitBtn;
    BitBtn11: TBitBtn;
    BitBtn12: TBitBtn;
    BitBtn13: TBitBtn;
    BitBtn14: TBitBtn;
    BitBtn15: TBitBtn;
    BitBtn16: TBitBtn;
    BitBtn17: TBitBtn;
    BitBtn18: TBitBtn;
    BitBtn19: TBitBtn;
    BitBtn20: TBitBtn;
    BitBtn21: TBitBtn;
    BitBtn22: TBitBtn;
    BitBtn23: TBitBtn;
    BitBtn24: TBitBtn;
    BitBtn25: TBitBtn;
    BitBtn26: TBitBtn;
    BitBtn27: TBitBtn;
    BitBtn28: TBitBtn;
    BitBtn29: TBitBtn;
    BitBtn30: TBitBtn;
    BitBtn31: TBitBtn;
    BitBtn32: TBitBtn;
    Button1: TButton;
    MainMenu1: TMainMenu;
    Mgame: TMenuItem;
    Mnew: TMenuItem;
    Mexit: TMenuItem;
    help: TMenuItem;
    about: TMenuItem;
    jieshao: TMenuItem;
    Label1: TLabel;
    procedure MexitClick(Sender: TObject);
    procedure Button1Click(Sender: TObject);
    procedure GroupBox1Click(Sender: TObject);
    procedure BitBtn1Click(Sender: TObject);
  private
    { Private declarations }
  public
    { Public declarations }
  end;

var
  Form1: TForm1;
  sign:array[1..4,1..8]of boolean;
  character:string='abcdefghijklmnopqrstuvwxyz';
  flag:integer;
  nowi,nowj:integer;
  lasti,lastj:integer;

implementation

{$R *.dfm}


procedure TForm1.MexitClick(Sender: TObject);
begin
   close;
end;

procedure TForm1.Button1Click(Sender: TObject);
var
  i,j:integer;
begin
   //Randomize;
   i:=Random(100) mod 26 +1;
   self.BitBtn1.Caption:=character[i];
   i:=Random(100) mod 26 +1;
   self.BitBtn2.Caption:=character[i];
   i:=Random(100) mod 26 +1;
   self.BitBtn3.Caption:=character[i];
   i:=Random(100) mod 26 +1;
   self.BitBtn4.Caption:=character[i];
   i:=Random(100) mod 26 +1;
   self.BitBtn5.Caption:=character[i];
   i:=Random(100) mod 26 +1;
   self.BitBtn6.Caption:=character[i];
   i:=Random(100) mod 26 +1;
   self.BitBtn7.Caption:=character[i];
   i:=Random(100) mod 26 +1;
   self.BitBtn8.Caption:=character[i];
   i:=Random(100) mod 26 +1;
   self.BitBtn9.Caption:=character[i];
   i:=Random(100) mod 26 +1;
   self.BitBtn10.Caption:=character[i];
   i:=Random(100) mod 26 +1;
   self.BitBtn11.Caption:=character[i];
   i:=Random(100) mod 26 +1;
   self.BitBtn12.Caption:=character[i];
   i:=Random(100) mod 26 +1;
   self.BitBtn13.Caption:=character[i];
   i:=Random(100) mod 26 +1;
   self.BitBtn14.Caption:=character[i];
   i:=Random(100) mod 26 +1;
   self.BitBtn15.Caption:=character[i];
   i:=Random(100) mod 26 +1;
   self.BitBtn16.Caption:=character[i];
   i:=Random(100) mod 26 +1;
   self.BitBtn17.Caption:=character[i];
   i:=Random(100) mod 26 +1;
   self.BitBtn18.Caption:=character[i];
   i:=Random(100) mod 26 +1;
   self.BitBtn19.Caption:=character[i];
   i:=Random(100) mod 26 +1;
   self.BitBtn20.Caption:=character[i];
   i:=Random(100) mod 26 +1;
   self.BitBtn21.Caption:=character[i];
   i:=Random(100) mod 26 +1;
   self.BitBtn22.Caption:=character[i];
   i:=Random(100) mod 26 +1;
   self.BitBtn23.Caption:=character[i];
   i:=Random(100) mod 26 +1;
   self.BitBtn24.Caption:=character[i];
   i:=Random(100) mod 26 +1;
   self.BitBtn25.Caption:=character[i];
   i:=Random(100) mod 26 +1;
   self.BitBtn26.Caption:=character[i];
   i:=Random(100) mod 26 +1;
   self.BitBtn27.Caption:=character[i];
   i:=Random(100) mod 26 +1;
   self.BitBtn28.Caption:=character[i];
   i:=Random(100) mod 26 +1;
   self.BitBtn29.Caption:=character[i];
   i:=Random(100) mod 26 +1;
   self.BitBtn30.Caption:=character[i];
   i:=Random(100) mod 26 +1;
   self.BitBtn31.Caption:=character[i];
   i:=Random(100) mod 26 +1;
   self.BitBtn32.Caption:=character[i];

   self.Label1.Caption:=' ';
   flag:=0;

   for i:=1 to 4 do
   for j:=1 to 8 do
    sign[i][j]:=false;

   lasti:=0;lastj:=0;
   nowi:=0;nowj:=0;

end;

procedure TForm1.GroupBox1Click(Sender: TObject);
begin
  self.Label1.Caption:='按开始重新游戏';
end;

procedure process();
begin
  if not ((lasti=nowi) and (lastj=nowj)) then
  begin
  //if
    sign[nowi][nowj]:=true;
    sign[lasti][lastj]:=true;
  end
end;

procedure TForm1.BitBtn1Click(Sender: TObject);
begin
  if (lasti=0) or (lastj=0) then
  begin
    lasti:=1;
    lastj:=1;
  end
  else if (nowi=0) or (nowj=0) then
  begin
    nowi:=1;
    nowj:=1;
    process();
  end
  else
  begin
    lasti:=nowi;
    lastj:=nowj;
    nowi:=1;
    nowj:=1;
    process();
  end;

end;

end.
