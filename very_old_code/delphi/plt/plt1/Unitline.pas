unit Unitline;

interface

uses
  Windows, Messages, SysUtils, Variants, Classes, Graphics, Controls, Forms,
  Dialogs, StdCtrls, ComCtrls;

type
  TFormLine = class(TForm)
    TrackBar1: TTrackBar;
    Button1: TButton;
    Button2: TButton;
    Label1: TLabel;
    Label2: TLabel;
    Label3: TLabel;
    procedure Button2Click(Sender: TObject);
    procedure Button1Click(Sender: TObject);
    procedure TrackBar1Change(Sender: TObject);
    procedure FormShow(Sender: TObject);
  private
    { Private declarations }
    Oldlinesize:integer;
  public
    { Public declarations }
  end;

var
  FormLine: TFormLine;

implementation

uses pltread;

{$R *.dfm}

procedure TFormLine.Button2Click(Sender: TObject);
begin
   //返回以前的线形
   Form1.image1.Canvas.Pen.Width:=Oldlinesize;
   Form1.R1Click(Sender);
   close;
end;

procedure TFormLine.Button1Click(Sender: TObject);
begin
   close;
end;

procedure TFormLine.TrackBar1Change(Sender: TObject);
begin
   //改变调刷新函数
   Form1.image1.Canvas.Pen.Width:=TrackBar1.Position;
   Form1.R1Click(Sender);
end;

procedure TFormLine.FormShow(Sender: TObject);
begin
   self.TrackBar1.Position:=Form1.image1.Canvas.Pen.Width;
   Oldlinesize:=TrackBar1.Position;
end;

end.
