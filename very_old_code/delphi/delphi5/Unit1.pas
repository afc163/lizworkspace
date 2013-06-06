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
  mnu.Caption:='��';
  mnu.Name:='mnuOpen';
  mnu.OnClick:=OnMenuClick;
  mnu.AutoHotkeys:=maManual;//��ֹ�Զ�Ϊ�˵������ȼ�
  mnu.AutoLineReduction:=maManual;
  self.mnuFile.Add(mnu);
end;

procedure TForm1.Button2Click(Sender: TObject);
var
  mnu:TMenuItem;
begin
  mnu:=TMenuItem.Create(self);
  mnu.Caption:='�༭';
  mnu.Name:='mnuEdit';
  mnu.OnClick:=OnMenuClick;
  mnu.AutoHotkeys:=maManual;//��ֹ�Զ�Ϊ�˵������ȼ�
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
  //����Ҳ��ͨ��ѡ���Caption�������ж�
  //֮ǰ�������AutoHotkeys��AutoLineReductionΪmaManual
end;

procedure TForm1.FormClick(Sender: TObject);
begin
  self.MainMenu1.AutoHotkeys:=maManual;
  self.MainMenu1.AutoLineReduction:=maManual;
  //ͨ���������������Կ��Խ�ֹ�����Զ�Ϊ�˵������ȼ���
  //���������Զ��ڲ˵���Caption�����м����ȼ�
  //���һ��޸�Caption���Ե�ֵ����ͨ��Caption�����ж�ѡ������鷳
end;

end.
