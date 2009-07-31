unit tuopan;

interface

uses
  Windows, Messages, SysUtils, Variants, Classes, Graphics, Controls, Forms,
  Dialogs, Menus, ExtCtrls, StdCtrls, ShellApi;  //����ShellApi.pas��Ԫ
const
  //�Զ���һ����궯������Ϣ
  MY_ICONEVENT=WM_USER+1;
var
  MyNotifyStruct:TNotifyIconData;

type
  TForm1 = class(TForm)
    Label1: TLabel;
    Timer1: TTimer;
    PopupMenu1: TPopupMenu;
    N1back: TMenuItem;
    N2min: TMenuItem;
    N3max: TMenuItem;
    N4exit: TMenuItem;
    N5: TMenuItem;
    N6: TMenuItem;
    N7: TMenuItem;
    procedure FormCreate(Sender: TObject);
    procedure FormShow(Sender: TObject);
    procedure Timer1Timer(Sender: TObject);
    procedure N1backClick(Sender: TObject);
    procedure N2minClick(Sender: TObject);
    procedure N3maxClick(Sender: TObject);
    procedure N4exitClick(Sender: TObject);
    procedure FormClose(Sender: TObject; var Action: TCloseAction);
  private
    { Private declarations }
    icount:integer;
  public
    { Public declarations }
    //����һ����Ϣ�������ĺ���
    procedure IconOnClick(var message:TMessage);message MY_ICONEVENT;
    procedure WMSysCommand(var MSg:TWMSysCommand);message Wm_SYSCOMMAND;
  end;

var
  Form1: TForm1;

implementation

{$R *.dfm}
//{$R MYICON.RES}

procedure TForm1.IconOnClick(var message:TMessage);
var
  p:TPoint;
begin
  //������µ�������Ҽ�,����ʾ̽���˵�
  if message.LParam=WM_RBUTTONDOWN then
  begin
    //��ȡ�������Ļ�ϵ�λ��       //����Ҽ�������
    GetCursorPos(p);
    PopupMenu1.Popup(p.X,p.Y);
  end
  else if message.LParam=WM_LBUTTONDOWN then
  begin
    //������������
  end
  else if message.LParam=WM_RBUTTONUP then
  begin
    // ����Ҽ����ͷ�
  end
  else if message.LParam=WM_LBUTTONUP then
  begin
   // ���������ͷ�
  end
  else if message.LParam=WM_MOUSEMOVE then
  begin
  //�����ͼ�����ƶ�
  end
  else if message.LParam=WM_LBUTTONDBLCLK then
  begin
  //������˫��
  end
  else if message.LParam=WM_RBUTTONDBLCLK then
  begin
  //����Ҽ�˫��
  end;
end;

procedure TForm1.WMSysCommand(var Msg:TWMSysCommand);
begin
  //���û�������'��С��',������Զ��崦��
  if (Msg.CmdType=SC_MINIMIZE) then
  begin
    //������������
    ShowWindow(Application.Handle,SW_HIDE);
  end;
  //������'��С��'��ť,����Ĭ�ϴ���fangf
  DefaultHandler(Msg);
end;

procedure TForm1.FormCreate(Sender: TObject);
begin
  MyNotifyStruct.cbSize:=sizeof(MyNotifyStruct);
  MyNotifyStruct.Wnd:=Handle;
  MyNotifyStruct.uID:=1;
  MyNotifyStruct.uFlags:= NIF_ICON or NIF_TIP or NIF_MESSAGE;
  MyNotifyStruct.uCallbackMessage:=MY_ICONEVENT;
  MyNotifyStruct.hIcon:=LoadIcon(hinstance,'BAMBI');//Application.Icon.Handle;
  MyNotifyStruct.szTip:='ϵͳ���̳���';

  Shell_NotifyIcon(NIM_ADD,@MyNotifyStruct);
  ShowWindow(Application.Handle,SW_HIDE);

  timer1.Enabled:=true;
  icount:=1;
  self.Hide;
end;

procedure TForm1.FormShow(Sender: TObject);
begin
  //����ʼ����ʱ,��������,��icount����ʼֵ0
  timer1.Enabled:=true;
  icount:=1;
end;

procedure TForm1.Timer1Timer(Sender: TObject);
begin
  //2s ��ʱ����,����ʱ���ر�
  timer1.Enabled:=false;
  inc(icount);
  //ÿ���ĸ�ѭ��һ��
  if icount >4 then icount:=1;
  case icount of
  1:MyNotifyStruct.hIcon:=LoadIcon(hinstance,'BAMBI');
  2:MyNotifyStruct.hIcon:=LoadIcon(hinstance,'FISHGOL');
  3:MyNotifyStruct.hIcon:=LoadIcon(hinstance,'SWORD');
  4:MyNotifyStruct.hIcon:=LoadIcon(hinstance,'KISSME');
  end;
  //��ʾ
  Shell_NotifyIcon(NIM_MODIFY,@MyNotifyStruct);
  timer1.Enabled:=true;
end;

procedure TForm1.N1backClick(Sender: TObject);
begin
  self.Repaint;
  //self.Width:=330;
  //self.Height:=200;
  self.Show;
  showwindow(application.Handle,SW_SHOWNORMAL);
end;

procedure TForm1.N2minClick(Sender: TObject);
begin
  showwindow(application.Handle,SW_MINIMIZE);
end;

procedure TForm1.N3maxClick(Sender: TObject);
begin
  showwindow(application.Handle,SW_MAXIMIZE);
end;

procedure TForm1.N4exitClick(Sender: TObject);
begin
  close;
end;

procedure TForm1.FormClose(Sender: TObject; var Action: TCloseAction);
begin                                     // MB_OK+MB_CANCEL+mb_iconexclamation
  //if messagebox(0,'�Ƿ��˳�?','��ʾ',4)=idyes then
  //begin
    Shell_NotifyIcon(NIM_DELETE,@MyNotifyStruct);
  //end;
end;

end.
