program Project1;

uses
  Forms,
  calculate in 'calculate.pas' {Form1},
  stackop in 'stackop.pas',
  stacknum in 'stacknum.pas';

{$R *.res}

begin
  Application.Initialize;
  Application.CreateForm(TForm1, Form1);
  Application.Run;
end.
