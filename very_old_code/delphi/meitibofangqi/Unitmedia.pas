unit Unitmedia;

interface

uses
  Windows, Messages, SysUtils, Variants, Classes, Graphics, Controls, Forms,
  Dialogs, ExtCtrls, MPlayer, StdCtrls, Menus;

type
  TFormdisplay = class(TForm)
    MediaPlayer1: TMediaPlayer;
    Panel1: TPanel;
    PopupMenu1: TPopupMenu;
    ListBox1: TListBox;
    N1: TMenuItem;
    N2: TMenuItem;
    N3: TMenuItem;
    Splitter1: TSplitter;
    procedure ListBox1DblClick(Sender: TObject);
    procedure MediaPlayer1Click(Sender: TObject; Button: TMPBtnType;
      var DoDefault: Boolean);
    procedure N1Click(Sender: TObject);
    procedure N3Click(Sender: TObject);
    procedure FormClose(Sender: TObject; var Action: TCloseAction);
    procedure FormCreate(Sender: TObject);
  private
    { Private declarations }
  public
    { Public declarations }
  end;

var
  Formdisplay: TFormdisplay;
var
  Myicon:TICON;
  
implementation

uses Unitchoosefile;

{$R *.dfm}
{$R MYICON.RES}

procedure TFormdisplay.ListBox1DblClick(Sender: TObject);
begin
   //只有在列表中有内容时，才播放选定的内容
   if ListBox1.Count>0 then
   begin
     //先关闭媒体播放器
     MediaPlayer1.Close;
     //指定媒体播放的窗口
     //MediaPlayer1.Display:=self.Panel1;
     //指定媒体播放器要播放的文件
     MediaPlayer1.FileName:=ListBox1.Items[listbox1.ItemIndex];
     //自动打开
     MediaPlayer1.AutoOpen:=true;
     MediaPlayer1.Open;
     MediaPlayer1.Notify:=true;
     MediaPlayer1.DisplayRect:=Rect(0,0,Panel1.Width,Panel1.Height);
   end
   else showmessage('列表为空，不能播放！');
end;

procedure TFormdisplay.MediaPlayer1Click(Sender: TObject;
  Button: TMPBtnType; var DoDefault: Boolean);
begin  
  if Button=btStop then      //当按下“停止”时，需要将画面返回
  begin
      self.MediaPlayer1.Stop;
      self.MediaPlayer1.Rewind;
  end
  else if Button=btPlay then
  begin
      self.MediaPlayer1.Play;
  end
  else if Button=btPause then
  begin
      self.MediaPlayer1.Pause;
  end
  else if Button=btNext then
  begin
      self.MediaPlayer1.Next;
  end
  else if Button=btPrev then
  begin
      self.MediaPlayer1.Previous;
  end
  else if Button=btStep then
  begin
      self.MediaPlayer1.Step;
  end
  else if Button=btBack then
  begin
      self.MediaPlayer1.Back;
  end;
end;

procedure TFormdisplay.N1Click(Sender: TObject);
begin
   Formselect.Show;
   self.Hide;
end;

procedure TFormdisplay.N3Click(Sender: TObject);
begin
  self.ListBox1.Items.Delete(ListBox1.ItemIndex);
  ListBox1.Refresh;
end;

procedure TFormdisplay.FormClose(Sender: TObject;
  var Action: TCloseAction);
begin
   MediaPlayer1.Close;
end;

procedure TFormdisplay.FormCreate(Sender: TObject);
begin
  Myicon:=TIcon.Create;
  Myicon.Handle:=LoadIcon(Handle,'BAMBI');
  self.Icon:=Myicon;
end;

end.
