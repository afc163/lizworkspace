unit Unit1;

interface

uses
  Windows, Messages, SysUtils, Variants, Classes, Graphics, Controls, Forms,
  Dialogs, StdCtrls;

type
  TForm1 = class(TForm)
    Button1: TButton;
    procedure FormCreate(Sender: TObject);
    procedure Button1Click(Sender: TObject);
  private
    { Private declarations }
  public
    { Public declarations }
  end;

var
  Form1: TForm1;

implementation
 uses unit2;
{$R *.dfm}

procedure TForm1.FormCreate(Sender: TObject);
var
  pt:array[0..4] of TPoint;
  m_rgn:HRGN;
begin
  pt[0].X:=self.Width div 2;
  pt[0].Y:=0;
  pt[1].X:=0;
  pt[1].Y:=self.Height div 2;
  pt[2].X:=self.Width div 2;
  pt[2].Y:=self.Height;
  pt[3].X:=self.Width;
  pt[3].Y:=self.Height div 2;
  pt[4].X:=self.Width;
  pt[4].Y:=0;
  m_rgn:=CreatePolygonRgn(pt,5,WINDING);
  SetWindowRgn(self.Handle,m_rgn,TRUE);
end;

procedure TForm1.Button1Click(Sender: TObject);
begin
   Form2.Show;
end;

end.
 