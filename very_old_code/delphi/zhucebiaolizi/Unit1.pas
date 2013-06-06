unit Unit1;

interface

uses
  Windows, Messages, SysUtils, Variants, Classes, Graphics, Controls, Forms,
  Dialogs, Buttons, StdCtrls;

type
  TForm1 = class(TForm)
    GroupBox1: TGroupBox;
    GroupBox2: TGroupBox;
    Label1: TLabel;
    Label2: TLabel;
    Label3: TLabel;
    Label4: TLabel;
    Label5: TLabel;
    Label6: TLabel;
    Edit1: TEdit;
    Edit2: TEdit;
    Edit3: TEdit;
    Edit4: TEdit;
    Edit5: TEdit;
    Edit6: TEdit;
    Label7: TLabel;
    Label8: TLabel;
    Label9: TLabel;
    Edit7: TEdit;
    Edit8: TEdit;
    Edit9: TEdit;
    Button1: TButton;
    BitBtn1: TBitBtn;
    BitBtn2: TBitBtn;
    OpenDialog1: TOpenDialog;
    procedure FormShow(Sender: TObject);
    procedure Button1Click(Sender: TObject);
    procedure BitBtn2Click(Sender: TObject);
    procedure BitBtn1Click(Sender: TObject);
  private
    { Private declarations }
  public
    { Public declarations }
  end;

var
  Form1: TForm1;

implementation
{$R *.dfm}

uses Registry;          //����Registry.pas��Ԫ

procedure TForm1.FormShow(Sender: TObject);
var
  //����һ��Tregistry����
  ARegistry:Tregistry;
begin
  //����һ��Tregistryʵ��
  ARegistry:=TRegistry.Create;
  with ARegistry do
  begin
    //ָ������ΪHKEY_LOCAL_MACHINE
    RootKey:=HKEY_LOCAL_MACHINE;
    //������Software\Microsoft\Windows NT\CurrentVersion
    if OpenKey('Software\Microsoft\Windows NT\CurrentVersion',false) then
    begin
      //��ʾ��ǰ��ֵ��·��
      self.Edit1.Text:=CurrentPath;
      //��ʾ��Ʒϵ�к�
      self.Edit2.Text:=ReadString('ProductId');
      //��ʾ��Ʒ��
      self.Edit3.Text:=ReadString('ProductName');
      //��ʾע�ṫ˾����
      self.Edit4.Text:=ReadString('RegisteredOrganization');
      //��ʾ�û���
      self.Edit5.Text:=ReadString('RegisteredOwner');
      //��ʾ�������
      self.Edit6.Text:=ReadString('SoftwareType');
    end;
    //�ر�����
    CloseKey;
    //�ͷ��ڴ�
    Destroy;
  end;
end;

procedure TForm1.Button1Click(Sender: TObject);
begin
   if self.OpenDialog1.Execute then
   begin
     self.Edit9.Text:=self.OpenDialog1.FileName;
   end
end;

procedure TForm1.BitBtn2Click(Sender: TObject);
begin
   self.Close;
end;

procedure TForm1.BitBtn1Click(Sender: TObject);
var
  //����һ��Tregistry����
  Reg:Tregistry;
begin
  //�ж�������Ƿ��п�ֵ
  if (trim(self.Edit7.Text)='') or (trim(self.Edit8.Text)='') or (trim(self.Edit9.Text)='') then
  begin
    showmessage('���벻��Ϊ�գ�');
    self.Edit7.SelectAll;
    self.Edit7.SetFocus;
    exit;
  end;
  //����һ��Tregistryʵ��
  Reg:=TRegistry.Create;
  Try
    //ָ������ΪHKEY_CLASSES_ROOT
    Reg.RootKey:=HKEY_CLASSES_ROOT;
    //������*\Shell\Delphi7,����������򴴽�
    Reg.OpenKey('*\Shell\'+trim(self.Edit7.Text),True);
    //д���ֵ "��Delphi7 ��"
    Reg.WriteString('',trim(self.Edit8.Text));
    //�رռ�ֵ
    Reg.CloseKey;
    //������*\Shell\Delphi7\Command,����������򴴽�
    Reg.OpenKey('*\Shell\'+trim(self.Edit7.Text)+'\Command',True);
    //д���ֵ " "��Ӧ·����" "%1" "
    Reg.WriteString('','"'+Trim(self.Edit9.Text)+'" "%1"');
    //�رռ�ֵ
    Reg.CloseKey;
  Finally
    //�ͷ��ڴ�
    Reg.Free;
  end;
  Showmessage('ע��ɹ�!');
end;

end.
