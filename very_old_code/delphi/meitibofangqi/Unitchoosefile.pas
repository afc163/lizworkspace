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
  FName:string;//ý���ļ���
  IsValid:Boolean;
  i:integer;
begin
  //ֻ���б����Ѿ�������ʱ���Ž������
  if FilelistBox1.Count>0 then
  begin
    FName:=FileListBox1.FileName;//��õ�ǰ�ļ���
    IsValid:=true;//����־λ��Ϊ��Ч
    //�жϵ�ǰ�б����Ƿ��Ѿ����ļ�����
    if Formdisplay.ListBox1.Items.Count>0 then
    begin
      //�����ļ����ڣ����ж��б����Ƿ���ڵ�ǰѡ����ļ���
      //�����б��е��ļ���
      for i:=0 to Formdisplay.ListBox1.Items.Count-1 do
      begin
        if Formdisplay.ListBox1.Items[i]=FName then
        begin
          IsValid:=false;
          break;
        end;
      end;
      //�������ڣ������
      if IsValid then  Formdisplay.ListBox1.Items.Add(FName)
      else
      begin
         showmessage('���ļ����Ѵ���,�����ظ���ӣ�');
      end;
    end
    else
      //���б�Ϊ�գ���ֱ�����
      Formdisplay.ListBox1.Items.Add(FName);
    close;
  end;
end;

procedure TFormselect.FormClose(Sender: TObject; var Action: TCloseAction);
begin
     Formdisplay.Show;
end;

end.
