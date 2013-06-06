program Projectplt;

uses
  Forms,
  pltread in 'pltread.pas' {Form1},
  about in 'about.pas' {AboutBox},
  Unitline in 'Unitline.pas' {FormLine};

{$R *.res}

begin
  Application.Initialize;
  Application.CreateForm(TForm1, Form1);
  Application.CreateForm(TAboutBox, AboutBox);
  Application.CreateForm(TFormLine, FormLine);
  Application.Run;
end.
