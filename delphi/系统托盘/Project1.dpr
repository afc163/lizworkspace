program Project1;

uses
  Forms,
  tuopan in 'tuopan.pas' {Form1};

{$R *.res}
{$R MYICON.RES}
begin
  Application.Initialize;
  Application.CreateForm(TForm1, Form1);
  Application.Run;
end.
