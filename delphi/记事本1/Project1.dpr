program Project1;

uses
  Forms,
  Unit1 in 'Unit1.pas' {Form1},
  about in 'about.pas' {AboutBox},
  Unit2 in 'Unit2.pas' {Form2},
  Unit5 in 'Unit5.pas' {Form5};

{$R *.res}

begin
  Application.Initialize;
  Application.CreateForm(TForm1, Form1);
  Application.CreateForm(TAboutBox, AboutBox);
  Application.CreateForm(TForm2, Form2);
  Application.CreateForm(TForm5, Form5);
  Application.Run;
end.
