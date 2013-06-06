unit Unitchoosefile;

interface

uses
  Windows, Messages, SysUtils, Variants, Classes, Graphics, Controls, Forms,
  Dialogs, StdCtrls, FileCtrl;

type
  TFormselect = class(TForm)
    DriveComboBox1: TDriveComboBox;
    DirectoryListBox1: TDirectoryListBox;
    FileListBox1: TFileListBox;
    FilterComboBox1: TFilterComboBox;
    Label1: TLabel;
    Button1: TButton;
    Button2: TButton;
    procedure Button2Click(Sender: TObject);
    procedure Button1Click(Sender: TObject);
    procedure FormClose(Sender: TObject; var Action: TCloseAction);
  private
    { Private declarations }
  public
    { Public declarations }
  end;

var
  Formselect: TFormselect;

implementation

uses Unitmedia;

{$R *.dfm}

procedure TFormselect.Button2Click(Sender: TObject);
begin
  close;
end;

procedure TFormselect.Button1Click(Sender: TObject);
var
  FName:string;//媒体文件名
  IsValid:Boolean;
  i:integer;
begin
  //只有列表中已经有内容时，才进行添加
  if FilelistBox1.Count>0 then
  begin
    FName:=FileListBox1.FileName;//获得当前文件名
    IsValid:=true;//将标志位置为有效
    //判断当前列表中是否已经有文件存在
    if Formdisplay.ListBox1.Items.Count>0 then
    begin
      //若有文件存在，则判断列表中是否存在当前选择的文件名
      //遍历列表中的文件名
      for i:=0 to Formdisplay.ListBox1.Items.Count-1 do
      begin
        if Formdisplay.ListBox1.Items[i]=FName then
        begin
          IsValid:=false;
          break;
        end;
      end;
      //若不存在，则添加
      if IsValid then  Formdisplay.ListBox1.Items.Add(FName)
      else
      begin
         showmessage('该文件名已存在,不需重复添加！');
      end;
    end
    else
      //若列表为空，则直接添加
      Formdisplay.ListBox1.Items.Add(FName);
    close;
  end;
end;

procedure TFormselect.FormClose(Sender: TObject; var Action: TCloseAction);
begin
     Formdisplay.Show;
end;

end.
