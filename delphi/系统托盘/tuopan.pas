unit tuopan;

interface

uses
  Windows, Messages, SysUtils, Variants, Classes, Graphics, Controls, Forms,
  Dialogs, Menus, ExtCtrls, StdCtrls, ShellApi;  //引用ShellApi.pas单元
const
  //自定义一个鼠标动作的消息
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
    //声明一个消息处理句柄的函数
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
  //如果按下的是鼠标右键,则显示探出菜单
  if message.LParam=WM_RBUTTONDOWN then
  begin
    //获取鼠标在屏幕上的位置       //鼠标右键被按下
    GetCursorPos(p);
    PopupMenu1.Popup(p.X,p.Y);
  end
  else if message.LParam=WM_LBUTTONDOWN then
  begin
    //鼠标左键被按下
  end
  else if message.LParam=WM_RBUTTONUP then
  begin
    // 鼠标右键被释放
  end
  else if message.LParam=WM_LBUTTONUP then
  begin
   // 鼠标左键被释放
  end
  else if message.LParam=WM_MOUSEMOVE then
  begin
  //鼠标在图标上移动
  end
  else if message.LParam=WM_LBUTTONDBLCLK then
  begin
  //鼠标左键双击
  end
  else if message.LParam=WM_RBUTTONDBLCLK then
  begin
  //鼠标右键双击
  end;
end;

procedure TForm1.WMSysCommand(var Msg:TWMSysCommand);
begin
  //若用户按下了'最小化',则进行自定义处理
  if (Msg.CmdType=SC_MINIMIZE) then
  begin
    //任务栏上隐藏
    ShowWindow(Application.Handle,SW_HIDE);
  end;
  //若不是'最小化'按钮,则用默认处理fangf
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
  MyNotifyStruct.szTip:='系统托盘程序';

  Shell_NotifyIcon(NIM_ADD,@MyNotifyStruct);
  ShowWindow(Application.Handle,SW_HIDE);

  timer1.Enabled:=true;
  icount:=1;
  self.Hide;
end;

procedure TForm1.FormShow(Sender: TObject);
begin
  //程序开始运行时,开定量器,将icount赋初始值0
  timer1.Enabled:=true;
  icount:=1;
end;

procedure TForm1.Timer1Timer(Sender: TObject);
begin
  //2s 定时到了,将定时器关闭
  timer1.Enabled:=false;
  inc(icount);
  //每隔四个循环一次
  if icount >4 then icount:=1;
  case icount of
  1:MyNotifyStruct.hIcon:=LoadIcon(hinstance,'BAMBI');
  2:MyNotifyStruct.hIcon:=LoadIcon(hinstance,'FISHGOL');
  3:MyNotifyStruct.hIcon:=LoadIcon(hinstance,'SWORD');
  4:MyNotifyStruct.hIcon:=LoadIcon(hinstance,'KISSME');
  end;
  //显示
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
  //if messagebox(0,'是否退出?','提示',4)=idyes then
  //begin
    Shell_NotifyIcon(NIM_DELETE,@MyNotifyStruct);
  //end;
end;

end.
