unit Unit1;

interface

uses
  Windows, Messages, SysUtils, Variants, Classes, Graphics, Controls, Forms,
  Dialogs, Menus, StdCtrls, ComCtrls, ExtCtrls;

type
  TForm1 = class(TForm)
    RichEdit1: TRichEdit;
    FindDialog1: TFindDialog;
    ReplaceDialog1: TReplaceDialog;
    OpenDialog1: TOpenDialog;
    SaveDialog1: TSaveDialog;
    FontDialog1: TFontDialog;
    PrintDialog1: TPrintDialog;
    MainMenu1: TMainMenu;
    PopupMenu1: TPopupMenu;
    N1file: TMenuItem;
    N2new: TMenuItem;
    N3open: TMenuItem;
    N4save: TMenuItem;
    N5saveas: TMenuItem;
    N6: TMenuItem;
    N7print: TMenuItem;
    N8: TMenuItem;
    N9exit: TMenuItem;
    N10edit: TMenuItem;
    N11format: TMenuItem;
    N12help: TMenuItem;
    N13chx: TMenuItem;
    N14: TMenuItem;
    N15jq: TMenuItem;
    N16fzh: TMenuItem;
    N17nt: TMenuItem;
    N18sch: TMenuItem;
    N19: TMenuItem;
    N20qx: TMenuItem;
    U1: TMenuItem;
    N2undo: TMenuItem;
    N1: TMenuItem;
    C1copy: TMenuItem;
    T1cut: TMenuItem;
    P1paste: TMenuItem;
    L1delete: TMenuItem;
    N2: TMenuItem;
    N3find: TMenuItem;
    N4findnext: TMenuItem;
    N5replace: TMenuItem;
    N7goto: TMenuItem;
    N9: TMenuItem;
    N10selectall: TMenuItem;
    N11time: TMenuItem;
    N12changeline: TMenuItem;
    N13font: TMenuItem;
    N15help: TMenuItem;
    N16about: TMenuItem;
    N17: TMenuItem;
    N18view: TMenuItem;
    N20state: TMenuItem;
    StatusBar1: TStatusBar;
    N3lr: TMenuItem;
    N4: TMenuItem;
    Unicode1: TMenuItem;
    Unicode2: TMenuItem;
    china1: TMenuItem;
    english1: TMenuItem;
    PageSetupDialog1: TPageSetupDialog;
    procedure N3openClick(Sender: TObject);
    procedure N13fontClick(Sender: TObject);
    procedure N2undoClick(Sender: TObject);
    procedure N12changelineClick(Sender: TObject);
    procedure N3findClick(Sender: TObject);
    procedure FindDialog1Find(Sender: TObject);
    procedure N16aboutClick(Sender: TObject);
    procedure N15helpClick(Sender: TObject);
    procedure N2newClick(Sender: TObject);
    procedure N4saveClick(Sender: TObject);
    procedure N5saveasClick(Sender: TObject);
    procedure U1Click(Sender: TObject);
    procedure N7printClick(Sender: TObject);
    procedure N9exitClick(Sender: TObject);
    procedure N20stateClick(Sender: TObject);
    procedure RichEdit1MouseUp(Sender: TObject; Button: TMouseButton;
      Shift: TShiftState; X, Y: Integer);
    procedure FormCreate(Sender: TObject);
    procedure RichEdit1KeyUp(Sender: TObject; var Key: Word;
      Shift: TShiftState);
    procedure T1cutClick(Sender: TObject);
    procedure C1copyClick(Sender: TObject);
    procedure P1pasteClick(Sender: TObject);
    procedure L1deleteClick(Sender: TObject);
    procedure N5replaceClick(Sender: TObject);
    procedure N7gotoClick(Sender: TObject);
    procedure N10selectallClick(Sender: TObject);
    procedure N11timeClick(Sender: TObject);
    procedure N3lrClick(Sender: TObject);
    procedure RichEdit1SelectionChange(Sender: TObject);
    procedure ReplaceDialog1Replace(Sender: TObject);
    procedure ReplaceDialog1Find(Sender: TObject);
    procedure FormResize(Sender: TObject);
  private
    { Private declarations }


    row,col:integer;

    //设置当前操作的文件名称
    FileName:String;
    //实现检查当前文件是否需要保存的功能
    Function CheckModify:Boolean;
    //实现保存当前文件的功能
    Function SaveFile:Boolean;
    procedure init;
  public
    function getrow:integer;
    { Public declarations }
  end;

var
  Form1: TForm1;

implementation

uses about,Unit2, Unit3, Unit5;
{$R *.dfm}

function TForm1.getrow:integer;
begin
  result:=row;
end;

procedure TForm1.init;
begin
  RichEdit1.Modified:=false;
  row:=1;
  col:=1;
  N2undo.Enabled:=false;
  T1cut.Enabled:=false;
  C1copy.Enabled:=false;
  L1delete.Enabled:=false;
  N3find.Enabled:=false;
  N4findnext.Enabled:=false;
  N5replace.Enabled:=false;
  N13chx.Enabled:=false;
  N15jq.Enabled:=false;
  N16fzh.Enabled:=false;
  N18sch.Enabled:=false;
end;

Function TForm1.CheckModify:Boolean;
begin
  Result:=true;
  //判断文件是否被修改过
  if self.RichEdit1.Modified then
  begin
     //如果修改过，则显示提示框
     Case Application.MessageBox('文件没有保存！需要保存吗?','提示',MB_YESNO+MB_ICONQUESTION) of
       IDYES :
          //调用SaveFile函数将当前文件保存
          Result:=SaveFile;
       IDNO :
          Result:=True;
     end;
  end;
end;

Function TForm1.SaveFile:Boolean;
begin
  Result:=false;
  if FileName='无标题' then
  begin
    if SaveDialog1.Execute then
      try
        RichEdit1.Lines.SaveToFile(self.SaveDialog1.FileName);
//        init;
        FileName:=SaveDialog1.FileName;
        Form1.Caption:=ExtractFileName(SaveDialog1.FileName)+'——记事本程序';
        Result:=true;
      except
        on E:Exception do
          begin
            ShowMessage(E.Message);
 //           Result:=False;
          end;
      end
  end
  else
  begin
        RichEdit1.Lines.SaveToFile(FileName);
//        init;
        Result:=true;
  end;
end;

procedure TForm1.N3openClick(Sender: TObject);
begin
    //检查当前文件是否需要保存
    if self.CheckModify then
    begin
      OpenDialog1.FileName:='*.txt';
      if self.OpenDialog1.Execute then
      begin
        try
          //打开一个已存在的文档
          RichEdit1.Lines.LoadFromFile(OpenDialog1.FileName);
          init;
          //将文件名赋给FileName
          FileName:=OpenDialog1.FileName;
          Form1.Caption:=ExtractFileName(OpenDialog1.FileName)+'——记事本程序';
        except                 ////////////////////////////////////???????
          on E:Exception do
            ShowMessage(E.Message);
        end;
      end;
    end;
end;

procedure TForm1.N13fontClick(Sender: TObject);
begin
  //显示当前文档的字体设置情况
  FontDialog1.Font.Assign(RichEdit1.Font);
  //设置字体
  if FontDialog1.Execute then
    RichEdit1.Font.Assign(FontDialog1.Font);
end;

procedure TForm1.N2undoClick(Sender: TObject);
begin
  RichEdit1.Perform(EM_UNDO,0,0)
end;

procedure TForm1.N12changelineClick(Sender: TObject);
begin
  RichEdit1.WordWrap:= not RichEdit1.WordWrap;
  self.N12changeline.Checked:=RichEdit1.WordWrap;
  RichEdit1.Refresh;
end;

procedure TForm1.N3findClick(Sender: TObject);
begin
  //打开“查找”对话框
  FindDialog1.Execute;
end;

procedure TForm1.FindDialog1Find(Sender: TObject);
var
  FindResult:LongInt;
  StartPos,Area:Integer;
  st:TSearchTypes;
begin
    //设置起始位置
    StartPos:=RichEdit1.SelStart+RichEdit1.SelLength;
    //设置搜索区域
    Area:=Length(RichEdit1.Text)-StartPos;
    //设置搜索选项
    st:=[];
    //判断是否区分大小写
    if frMatchCase in FindDialog1.Options then
       st:=st+[stMatchCase];
    //判断是否全字匹配
    if frWholeWord in FindDialog1.Options then
       st:=st+[stWholeWord];
    //开始查找
    FindResult:=RichEdit1.FindText(FindDialog1.FindText,StartPos,Area,st);
    if FindResult<>-1 then
    begin
      //若找到，则光标定位到查找结果处
      RichEdit1.SelStart:=FindResult;
      RichEdit1.SelLength:=Length(FindDialog1.FindText);
      RichEdit1.SetFocus;
      //////////////////////////
    end
    else
      ShowMessage('查找完成！');
end;

procedure TForm1.N16aboutClick(Sender: TObject);
begin
   AboutBox.Show;
end;

procedure TForm1.N15helpClick(Sender: TObject);
begin
   Form2.Show;
end;

procedure TForm1.N2newClick(Sender: TObject);
begin
    //检查当前文件是否需要保存
    if self.CheckModify then
    begin
      RichEdit1.Clear;
        init;
        //将文件名赋给FileName
        FileName:='无标题';
        Form1.Caption:=ExtractFileName(FileName)+'——记事本程序';
    end;
end;

procedure TForm1.N4saveClick(Sender: TObject);
begin
   self.SaveFile;
   init;
end;

procedure TForm1.N5saveasClick(Sender: TObject);
begin
   if self.FileName<>'无标题' then
   begin
         self.SaveDialog1.FileName:=self.FileName;
         FileName:='无标题';
   end;
   self.SaveFile;
   init;
end;

procedure TForm1.U1Click(Sender: TObject);
begin
   //页面设置
   if self.PageSetupDialog1.Execute then
   begin
   end;
end;

procedure TForm1.N7printClick(Sender: TObject);
begin
   //打印
   if self.PrintDialog1.Execute  then
   begin
      //RichEdit1.Print(RichEdit1.Lines.Text);
   end;
end;

procedure TForm1.N9exitClick(Sender: TObject);
begin
   if CheckModify then  close;
end;

procedure TForm1.N20stateClick(Sender: TObject);
begin
   self.N20state.Checked:=not  N20state.Checked;
   if N20state.Checked then
   begin
     RichEdit1.Height:=RichEdit1.Height-19;
     self.StatusBar1.Visible:=true;
   end
   else
   begin
     StatusBar1.Visible:=false;
     RichEdit1.Height:=RichEdit1.Height+19;
   end
end;

procedure TForm1.RichEdit1MouseUp(Sender: TObject; Button: TMouseButton;
  Shift: TShiftState; X, Y: Integer);
begin
   self.StatusBar1.Panels[1].Text:='行数 '+IntToStr(row)+'，列数 '+IntToStr(col);
end;

procedure TForm1.FormCreate(Sender: TObject);
begin
  init;
  FileName:='无标题';
  Form1.Caption:=ExtractFileName(FileName)+'——记事本程序';
end;

procedure TForm1.RichEdit1KeyUp(Sender: TObject; var Key: Word;
  Shift: TShiftState);
begin
  N13chx.Enabled:=true;
  N2undo.Enabled:=true;

  if Key=13  then
  begin
    inc(row);   //回车换行
    col:=1;
  end
  else if Key=8 then
  begin
    dec(col);  //回退键
    if col=0 then
    begin
      dec(row);
      col:=1;
    end;
  end
  else inc(col);
  self.StatusBar1.Panels[1].Text:='行数 '+IntToStr(row)+'，列数 '+IntToStr(col);
end;

procedure TForm1.T1cutClick(Sender: TObject);
begin
  RichEdit1.Perform(WM_CUT,1,1);
end;

procedure TForm1.C1copyClick(Sender: TObject);
begin
  RichEdit1.Perform(WM_COPY,1,1);
end;

procedure TForm1.P1pasteClick(Sender: TObject);
begin
  RichEdit1.Perform(WM_PASTE,1,1);
end;

procedure TForm1.L1deleteClick(Sender: TObject);
begin
  if RichEdit1.SelText<>'' then RichEdit1.SelText:=''
  else
  begin
    RichEdit1.SelLength:=1;
    RichEdit1.SelText:='';
  end;
end;

procedure TForm1.N5replaceClick(Sender: TObject);
begin
   self.ReplaceDialog1.Execute;
end;

procedure TForm1.N7gotoClick(Sender: TObject);
begin
   Form5.Show;
end;

procedure TForm1.N10selectallClick(Sender: TObject);
begin
  RichEdit1.SelectAll;
end;

procedure TForm1.N11timeClick(Sender: TObject);
begin
  RichEdit1.Lines.Append(FormatDateTime('yyyy年mm月dd日hh时nn分ss秒',Now));
end;

procedure TForm1.N3lrClick(Sender: TObject);
begin
  N3lr.Checked:=not N3lr.Checked;
  if N3lr.Checked then  richEdit1.UseRightToLeftReading;
end;

procedure TForm1.RichEdit1SelectionChange(Sender: TObject);
begin
  T1cut.Enabled:=true;
  C1copy.Enabled:=true;
  L1delete.Enabled:=true;
  N3find.Enabled:=true;
  N4findnext.Enabled:=true;
  N5replace.Enabled:=true;
  N15jq.Enabled:=true;
  N16fzh.Enabled:=true;
  N18sch.Enabled:=true;
end;

procedure TForm1.ReplaceDialog1Replace(Sender: TObject);
var
  FindResult:LongInt;
  StartPos,Area:Integer;
  st:TSearchTypes;
begin
    //设置起始位置
    StartPos:=RichEdit1.SelStart+RichEdit1.SelLength;
    //设置搜索区域
    Area:=Length(RichEdit1.Text)-StartPos;
    //设置搜索选项
    st:=[];
    //判断是否区分大小写
    if frMatchCase in ReplaceDialog1.Options then
       st:=st+[stMatchCase];
    //判断是否全字匹配
    if frWholeWord in ReplaceDialog1.Options then
       st:=st+[stWholeWord];
    //开始查找
    FindResult:=RichEdit1.FindText(ReplaceDialog1.FindText,StartPos,Area,st);
    if FindResult<>-1 then
    begin
      //若找到，则光标定位到查找结果处
      RichEdit1.SelStart:=FindResult;
      RichEdit1.SelLength:=Length(ReplaceDialog1.FindText);
      RichEdit1.SelText:=ReplaceDialog1.ReplaceText;
      RichEdit1.SelStart:=FindResult;
      RichEdit1.SelLength:=Length(ReplaceDialog1.ReplaceText);
      RichEdit1.SetFocus;
      //////////////////////////
    end
    else
      ShowMessage('替换完成！');
end;

procedure TForm1.ReplaceDialog1Find(Sender: TObject);
var
  FindResult:LongInt;
  StartPos,Area:Integer;
  st:TSearchTypes;
begin
    //设置起始位置
    StartPos:=RichEdit1.SelStart+RichEdit1.SelLength;
    //设置搜索区域
    Area:=Length(RichEdit1.Text)-StartPos;
    //设置搜索选项
    st:=[];
    //判断是否区分大小写
    if frMatchCase in ReplaceDialog1.Options then
       st:=st+[stMatchCase];
    //判断是否全字匹配
    if frWholeWord in ReplaceDialog1.Options then
       st:=st+[stWholeWord];
    //开始查找
    FindResult:=RichEdit1.FindText(ReplaceDialog1.FindText,StartPos,Area,st);
    if FindResult<>-1 then
    begin
      //若找到，则光标定位到查找结果处
      RichEdit1.SelStart:=FindResult;
      RichEdit1.SelLength:=Length(ReplaceDialog1.FindText);
      RichEdit1.SetFocus;
      //////////////////////////
    end
    else
      ShowMessage('查找完成！');
end;

procedure TForm1.FormResize(Sender: TObject);
var
  height,width:integer;
begin
  height:=self.Height-72;
  width:=self.Width-10;
  RichEdit1.Height:=height;
  RichEdit1.Width:=width;
  StatusBar1.Width:=width;
end;

end.
