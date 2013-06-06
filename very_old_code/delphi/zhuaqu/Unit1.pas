unit Unit1;

interface

uses
  Windows, Messages, SysUtils, Variants, Classes, Graphics, Controls, Forms,
  Dialogs, ExtCtrls;

type
  TForm1 = class(TForm)
    procedure FormClick(Sender: TObject);
  private
    { Private declarations }
  public
    { Public declarations }
  end;

var
  Form1: TForm1;

implementation

{$R *.dfm}

procedure TForm1.FormClick(Sender: TObject);
var
  ico:TIcon;
  point:TPoint;
begin
  GetCursorPos(point);
  point:=ScreenToClient(point);
  ico:=TIcon.Create;
  ico.Handle:=GetCursor();
  Canvas.Draw(point.X,point.Y,ico);
  ico.Free;
end;

end.
