unit Unit1;

interface

uses
  Windows, Messages, SysUtils, Variants, Classes, Graphics, Controls, Forms,
  Dialogs, ExtCtrls, jpeg, StdCtrls, Menus, ImgList;

type
  TForm1 = class(TForm)
    Splitter1: TSplitter;
    Panel1: TPanel;
    Splitter2: TSplitter;
    Image3: TImage;
    MainMenu1: TMainMenu;
    mnuFile: TMenuItem;
    mnuNew: TMenuItem;
    Image1: TImage;
    Button1: TButton;
    Button2: TButton;
    ImageList1: TImageList;
    procedure Button1Click(Sender: TObject);
    procedure Button2Click(Sender: TObject);
    procedure FormClick(Sender: TObject);
    procedure OnMenuClick(Sender:TObject);
  private
    { Private declarations }
  public
    { Public declarations }
  end;

var
  Form1: TForm1;

implementation

{$R *.dfm}

procedure TForm1.Button1Click(Sender: TObject);
var
  mnu:TMenuItem;
begin
  mnu:=TMenuItem.Create(self.MainMenu1);
  mnu.Caption:='打开';
  mnu.Name:='mnuOpen';
  mnu.OnClick:=OnMenuClick;
  mnu.AutoHotkeys:=maManual;//禁止自动为菜单生成热键
  mnu.AutoLineReduction:=maManual;
  self.mnuFile.Add(mnu);
end;

procedure TForm1.Button2Click(Sender: TObject);
var
  mnu:TMenuItem;
begin
  mnu:=TMenuItem.Create(self);
  mnu.Caption:='编辑';
  mnu.Name:='mnuEdit';
  mnu.OnClick:=OnMenuClick;
  mnu.AutoHotkeys:=maManual;//禁止自动为菜单生成热键
  mnu.AutoLineReduction:=maManual;
  self.MainMenu1.Items.Add(mnu);
end;

procedure TForm1.OnMenuClick(Sender:TObject);
begin
  if (Sender as TMenuItem).Name='mnuOpen' then
  begin
    ShowMessage('Open');
  end
  else if (Sender as TMenuItem).Name='mnuEdit' then
  begin
    ShowMessage('Edit');
  end
  //这里也可通过选项的Caption属性来判断
  //之前最好设置AutoHotkeys和AutoLineReduction为maManual
end;

procedure TForm1.FormClick(Sender: TObject);
begin
  self.MainMenu1.AutoHotkeys:=maManual;
  self.MainMenu1.AutoLineReduction:=maManual;
  //通过设置这两个属性可以禁止程序自动为菜单生成热键，
  //否则程序会自动在菜单的Caption属性中加入热键
  //并且会修改Caption属性的值，给通过Caption属性判断选项带来麻烦
end;

end.
