program Project1;

uses
  Forms,
  main in 'main.pas' {Form1},
  statestack in 'statestack.pas',
  placestack in 'placestack.pas',
  signstack in 'signstack.pas',
  assist in 'assist.pas';

{$R *.res}

begin
  Application.Initialize;
  Application.CreateForm(TForm1, Form1);
  Application.Run;
end.
