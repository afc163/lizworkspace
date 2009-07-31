program Project1;

uses
  Forms,
  lianlian in 'lianlian.pas' {Form1};

{$R *.res}

begin
  Application.Initialize;
  Application.CreateForm(TForm1, Form1);
  Application.Run;
end.
