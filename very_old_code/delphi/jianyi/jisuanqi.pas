unit jisuanqi;

interface

uses
  Windows, Messages, SysUtils, Variants, Classes, Graphics, Controls, Forms,
  Dialogs, StdCtrls, ExtCtrls;

type
  TForm1 = class(TForm)
    Label1: TLabel;
    Label2: TLabel;
    Label3: TLabel;
    Bevel1: TBevel;
    Edit1: TEdit;
    Edit2: TEdit;
    Edit3: TEdit;
    Button1: TButton;
    Button2: TButton;
    RadioGroup1: TRadioGroup;
    procedure Button1Click(Sender: TObject);
    procedure Button2Click(Sender: TObject);
  private
    { Private declarations }
  public
    { Public declarations }
  end;

type
  TShowMsg=procedure;//定义过程类型

var
  Form1: TForm1;
  ShowMsg:TShowMsg; //声明一个过程类型
  MyHandle:THandle;//定义DLL句柄

implementation

{$R *.dfm}
//声明DLLOpe.dll中的4个函数
function PlusOpe(x,y:integer):integer;stdcall;external 'project1.dll';
function MinusOpe(x,y:integer):integer;stdcall;external 'project1.dll';
function MultiplyOpe(x,y:integer):integer;stdcall;external 'project1.dll';
function DivisionOpe(x,y:integer):double;stdcall;external 'project1.dll';

procedure TForm1.Button1Click(Sender: TObject);
begin
   try
     case self.RadioGroup1.ItemIndex of
     0:
     begin
       Edit3.Text:=IntToStr(PlusOpe(StrToInt(Edit1.Text),StrToInt(Edit2.Text)));
       //raise EConvertError.Create('数据');
     end;
     1:  Edit3.Text:=IntToStr(MinusOpe(StrToInt(Edit1.Text),StrToInt(Edit2.Text)));
     2:  Edit3.Text:=IntToStr(MultiplyOpe(StrToInt(Edit1.Text),StrToInt(Edit2.Text)));
     3:  Edit3.Text:=FloatToStr(DivisionOpe(StrToInt(Edit1.Text),StrToInt(Edit2.Text)));
     end;

   except
   on  E:EConvertError do
      begin
        //动态调用project2.dll
        MyHandle:=LoadLibrary('project2.dll');
        //判断在同一个目录下是否存在project2.dll
        if MyHandle<>0 then
        begin
          //如果存在,获得DLL中函数ShowMsg的入口
          @ShowMsg:=GetProcAddress(MyHandle,'ShowMsg');
          if @ShowMsg<>nil then
          begin
            //执行该函数，即弹出对话框
            TShowMsg(ShowMsg);
            //卸载 project2.dll
            FreeLibrary(MyHandle);
          end;
        end
        else Showmessage('指定的动态链接库不存在！');
      end
   end;
end;

procedure TForm1.Button2Click(Sender: TObject);
begin
  close;
end;

end.
