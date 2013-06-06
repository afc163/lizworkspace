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

uses Registry;          //引用Registry.pas单元

procedure TForm1.FormShow(Sender: TObject);
var
  //声明一个Tregistry对象
  ARegistry:Tregistry;
begin
  //建立一个Tregistry实例
  ARegistry:=TRegistry.Create;
  with ARegistry do
  begin
    //指定根键为HKEY_LOCAL_MACHINE
    RootKey:=HKEY_LOCAL_MACHINE;
    //打开主键Software\Microsoft\Windows NT\CurrentVersion
    if OpenKey('Software\Microsoft\Windows NT\CurrentVersion',false) then
    begin
      //显示当前键值的路径
      self.Edit1.Text:=CurrentPath;
      //显示产品系列号
      self.Edit2.Text:=ReadString('ProductId');
      //显示产品名
      self.Edit3.Text:=ReadString('ProductName');
      //显示注册公司名称
      self.Edit4.Text:=ReadString('RegisteredOrganization');
      //显示用户名
      self.Edit5.Text:=ReadString('RegisteredOwner');
      //显示软件类型
      self.Edit6.Text:=ReadString('SoftwareType');
    end;
    //关闭主键
    CloseKey;
    //释放内存
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
  //声明一个Tregistry对象
  Reg:Tregistry;
begin
  //判断输入框是否有空值
  if (trim(self.Edit7.Text)='') or (trim(self.Edit8.Text)='') or (trim(self.Edit9.Text)='') then
  begin
    showmessage('输入不能为空！');
    self.Edit7.SelectAll;
    self.Edit7.SetFocus;
    exit;
  end;
  //建立一个Tregistry实例
  Reg:=TRegistry.Create;
  Try
    //指定根键为HKEY_CLASSES_ROOT
    Reg.RootKey:=HKEY_CLASSES_ROOT;
    //打开主键*\Shell\Delphi7,如果不存在则创建
    Reg.OpenKey('*\Shell\'+trim(self.Edit7.Text),True);
    //写入键值 "用Delphi7 打开"
    Reg.WriteString('',trim(self.Edit8.Text));
    //关闭键值
    Reg.CloseKey;
    //打开主键*\Shell\Delphi7\Command,如果不存在则创建
    Reg.OpenKey('*\Shell\'+trim(self.Edit7.Text)+'\Command',True);
    //写入键值 " "对应路径名" "%1" "
    Reg.WriteString('','"'+Trim(self.Edit9.Text)+'" "%1"');
    //关闭键值
    Reg.CloseKey;
  Finally
    //释放内存
    Reg.Free;
  end;
  Showmessage('注册成功!');
end;

end.
