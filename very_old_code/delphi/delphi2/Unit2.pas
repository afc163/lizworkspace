unit Unit2;

interface

uses
  Windows, Messages, SysUtils, Variants, Classes, Graphics, Controls, Forms,
  Dialogs, jpeg, ExtCtrls;

type
  TForm2 = class(TForm)
    Image1: TImage;
    procedure FormCreate(Sender: TObject);
  private
    { Private declarations }
  public
    { Public declarations }
  end;

var
  Form2: TForm2;

implementation

{$R *.dfm}

procedure TForm2.FormCreate(Sender: TObject);
var
  pt:array[0..7] of Tpoint;
  m_rgn:HRGN;
begin
  pt[0].X:=self.Width div 2;
  pt[0].Y:=0;
  pt[1].X:=self.Width div 3;
  pt[1].Y:=self.Height div 3;
  pt[2].X:=0;
  pt[2].Y:=self.Height div 2;
  pt[3].X:=self.Width div 3;
  pt[3].Y:=2*(self.Height div 3);
  pt[4].X:=self.Width div 2;
  pt[4].Y:=self.Height;
  pt[5].X:=2*(self.Width div 3);
  pt[5].Y:=2*(self.Height div 3);
  pt[6].X:=self.Width;
  pt[6].Y:=self.Height div 2;
  pt[7].X:=2*(self.Width div 3);
  pt[7].Y:=self.Height div 3;
  m_rgn:=CreatePolygonRgn(pt,8,WINDING);
  SetWindowRgn(self.Handle,m_rgn,TRUE);
end;

end.
