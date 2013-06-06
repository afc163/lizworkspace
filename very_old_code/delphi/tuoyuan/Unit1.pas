unit Unit1;

interface

uses
  Windows, Messages, SysUtils, Variants, Classes, Graphics, Controls, Forms,
  Dialogs, ExtCtrls, jpeg;

type
  TForm1 = class(TForm)
    Image1: TImage;
    Timer1: TTimer;
    procedure FormCreate(Sender: TObject);
    procedure Timer1Timer(Sender: TObject);
  private
    { Private declarations }
  public
    { Public declarations }
  end;

var
  Form1: TForm1;
  x1,x2,y1,y2:integer;
implementation

{$R *.dfm}

procedure TForm1.FormCreate(Sender: TObject);
begin
  Randomize();
  self.Canvas.Brush.Style:=bsClear;//使得绘制出的椭圆除了边框之外不会遮挡后面的绘图区
end;

procedure TForm1.Timer1Timer(Sender: TObject);
begin
  Image1.Canvas.Pen.Mode:=pmNotXor;
  Image1.Canvas.Ellipse(x1,y1,x2,y2);
  x1:=Random(Image1.Width);
  x2:=Random(Image1.Width);
  y1:=Random(Image1.Height);
  y2:=Random(Image1.Height);
  Image1.Canvas.Pen.Mode:=pmCopy;
  Image1.Canvas.Ellipse(x1,y1,x2,y2);
end;

end.
