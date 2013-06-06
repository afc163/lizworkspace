unit add;

interface

uses
  Windows, Messages, SysUtils, Variants, Classes, Graphics, Controls, Forms,
  Dialogs, StdCtrls;

type
  TForm1 = class(TForm)
    Edit1: TEdit;
    Edit2: TEdit;
    Label1: TLabel;
    Label2: TLabel;
    Buttoncancel: TButton;
    Buttonok: TButton;
    procedure ButtonokClick(Sender: TObject);
    procedure ButtoncancelClick(Sender: TObject);
  private
    { Private declarations }
  public
    { Public declarations }
  end;

var   
  Form1: TForm1;

implementation

uses Unittranslate;
{$R *.dfm}

procedure TForm1.ButtonokClick(Sender: TObject);
begin
  Myword.english:=Edit1.text;
  Myword.chinese:=Edit2.text;
  close;
end;

procedure TForm1.ButtoncancelClick(Sender: TObject);
begin
  close();
end;

end.
