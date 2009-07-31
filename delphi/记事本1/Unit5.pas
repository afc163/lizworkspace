unit Unit5;

interface

uses
  Windows, Messages, SysUtils, Variants, Classes, Graphics, Controls, Forms,
  Dialogs, StdCtrls;

type
  TForm5 = class(TForm)
    Buttoncancel: TButton;
    Label1: TLabel;
    Edit1: TEdit;
    Buttonok: TButton;
    procedure ButtoncancelClick(Sender: TObject);
    procedure ButtonokClick(Sender: TObject);
    procedure FormShow(Sender: TObject);
  private
    { Private declarations }
  public
    { Public declarations }
  end;

var
  Form5: TForm5;

implementation

uses Unit1;

{$R *.dfm}

procedure TForm5.ButtoncancelClick(Sender: TObject);
begin
  close;
end;

procedure TForm5.ButtonokClick(Sender: TObject);
var
  row:integer;
begin
  row:=StrToInt(edit1.Text);
  if row>Form1.getrow then
  begin
    MessageBox(0,'行数超过范围！','记事本-跳行',MB_OK);
    Edit1.Text:=IntToStr(Form1.getrow);
  end
  else
  begin
    Form1.RichEdit1.SelStart:=row;
    close;
    Form1.Refresh;
  end;
end;

procedure TForm5.FormShow(Sender: TObject);
begin
  Edit1.Text:=IntToStr(Form1.getrow);
  Edit1.SetFocus;
  Buttonok.Default:=true;
end;

end.
