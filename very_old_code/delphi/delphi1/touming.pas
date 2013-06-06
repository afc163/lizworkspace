unit touming;

interface

uses
  Windows, Messages, SysUtils, Variants, Classes, Graphics, Controls, Forms,
  Dialogs, ComCtrls, jpeg, ExtCtrls;

type
  TForm1 = class(TForm)
    Image1: TImage;
    TrackBar1: TTrackBar;
    procedure TrackBar1Change(Sender: TObject);
    procedure FormCreate(Sender: TObject);
  private
    { Private declarations }
  public
    { Public declarations }
  end;

var
  Form1: TForm1;

implementation

{$R *.dfm}

procedure TForm1.TrackBar1Change(Sender: TObject);
begin
    self.AlphaBlendValue:=self.TrackBar1.Max-self.TrackBar1.Position;
end;

procedure TForm1.FormCreate(Sender: TObject);
begin
    self.AlphaBlend:=true;
    self.TrackBar1.Min:=0;
    self.TrackBar1.Max:=255;
    self.TrackBar1.Frequency:=25;
end;

end.
