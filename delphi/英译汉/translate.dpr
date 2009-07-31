program translate;

uses
  Forms,
  Unittranslate in 'Unittranslate.pas' {mainform},
  add in 'add.pas' {Form1};

{$R *.res}

begin
  Application.Initialize;
  Application.CreateForm(Tmainform, mainform);
  Application.CreateForm(TForm1, Form1);
  Application.Run;
end.
