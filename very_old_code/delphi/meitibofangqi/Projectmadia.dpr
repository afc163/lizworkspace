program Projectmadia;

uses
  Forms,
  Unitmedia in 'Unitmedia.pas' {Formdisplay},
  Unitchoosefile in 'Unitchoosefile.pas' {Formselect};

{$R *.res}

begin
  Application.Initialize;
  Application.CreateForm(TFormdisplay, Formdisplay);
  Application.CreateForm(TFormselect, Formselect);
  Application.Run;
end.
