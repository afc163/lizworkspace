unit Unittranslate;

interface

uses
  Windows, Messages, SysUtils, Variants, Classes, Graphics, Controls, Forms,
  Dialogs, StdCtrls;

type
  Tmainform = class(TForm)
    Editstr: TEdit;
    Button1: TButton;
    Label1: TLabel;
    GroupBox1: TGroupBox;
    Button4: TButton;
    Button5: TButton;
    Buttonadd: TButton;
    CheckBox1: TCheckBox;
    ListBox1: TListBox;
    procedure CheckBox1Click(Sender: TObject);
    procedure FormCreate(Sender: TObject);
    procedure FormClose(Sender: TObject; var Action: TCloseAction);
    procedure Button1Click(Sender: TObject);
    procedure ButtonaddClick(Sender: TObject);
    procedure Button4Click(Sender: TObject);
    procedure Button5Click(Sender: TObject);
  private
    { Private declarations }
  public
    { Public declarations }
  end;

  TWord=record
        english,chinese:string[10];
        end;

  TDictionary=class(TObject)
	private
		word:array[1..100] of TWord;
		count:integer;
		modified:boolean;
	public
		constructor Create();
		procedure Add(aword:TWord);
    procedure delete(index:integer);
    procedure Show();
		function EnglishToChinese(aEnglish:string):string;
		function SearchEnglish(aEnglish:string):integer;
		destructor free();
	end;

var
  mainform: Tmainform;
  Myword: TWord;

implementation

uses add;

var
  MyDic:TDictionary;

{$R *.dfm}

constructor TDictionary.Create();
var
	DicFile:file of TWord;
	aword:TWord;
begin
	AssignFile(DicFile,'Dictionary');
	if not FileExists('Dictionary') then
	begin
		rewrite(DicFile);
		count:=0;
	end
	else
	begin
		reset(DicFile);
		count:=0;
		while not Eof(DicFile) do
		begin
			read(DicFile,aword);
			inc(count);
			word[count]:=aword;
		end;
	end;
	modified:=false;
	close(DicFile);
end;

procedure TDictionary.Add(aword:Tword);
var
  i,j:integer;
begin
  for i:=1 to Count do
    if word[i].English >aWord.English  then break;
  for j:=Count downto i do Word[j+1]:=Word[j];
  if count=0 then word[1]:=aWord
  else  Word[i]:=aWord;
  Inc(Count);
  Modified:=true;
end;

procedure TDictionary.delete(index:integer);
var i:integer;
begin
  if (Index>Count) or (Index<1) then exit;
  for i:=Index to Count-1 do word[i]:=word[i+1];
  Count:=Count-1;
  Modified:=true;
end;

procedure TDictionary.Show();
var
  i:integer;
begin
    mainform.ListBox1.Clear;
    for i:=1 to count do
      mainform.ListBox1.Items.Add(word[i].english+'---'+word[i].chinese);
end;

function TDictionary.EnglishToChinese(aEnglish:string):string;
var
  i:integer;
begin
    result:=aEnglish;
    i:=SearchEnglish(aEnglish);
    if (i<>0)then result:=self.word[i].chinese;
end;

function TDictionary.SearchEnglish(aEnglish:string):integer;
var
  i:integer;
begin
      result:=0;
      aEnglish:=LowerCase(aEnglish);
     for i:=1 to count do
      if aEnglish=word[i].english then result:=i;
end;

destructor TDictionary.free;
var
  i:integer;
  DicFile:file of TWord;
begin
    if not modified then exit;
        AssignFile(DicFile,'Dictionary');
        rewrite(DicFile);
        for i:=1 to count do
          write(DicFile,word[i]);
        CloseFile(DicFile);
end;

procedure Tmainform.CheckBox1Click(Sender: TObject);
begin
    if self.CheckBox1.Checked then
    begin
        self.Height:=468;
        MyDic.Show;
    end
    else  self.Height:=140;
end;

procedure Tmainform.FormCreate(Sender: TObject);
begin
    self.Height:=140;
    MyDic:=TDictionary.Create();
end;

procedure Tmainform.FormClose(Sender: TObject; var Action: TCloseAction);
begin
    MyDic.free;
end;

procedure Tmainform.Button1Click(Sender: TObject);
var
  i:integer;
  word,str,res:string;
begin
    str:=self.Editstr.Text;
    word:='';
    res:='';
    for i:=1 to length(str)do
    begin
      if(str[i]<='Z')and (str[i]>='A')or(str[i]<='z')and (str[i]>='a') then word:=word+str[i]
      else
      begin
        res:=res+MyDic.EnglishToChinese(word);
        if(str[i]<>' ')or(str[i]<>' ') then res:=res+str[i];
        word:='';
      end;
    end;
    res:=res+MyDic.EnglishToChinese(word);
    self.Editstr.text:=res;
end;

procedure Tmainform.ButtonaddClick(Sender: TObject);
begin
  Form1.Edit1.Text:='';
  form1.Edit2.Text:='';
  Form1.ShowModal;
  MyDic.Add(Myword);
  MyDic.show;
end;

procedure Tmainform.Button4Click(Sender: TObject);
begin
  MyDic.delete(ListBox1.Itemindex+1);
  MyDic.show;
end;

procedure Tmainform.Button5Click(Sender: TObject);
var
  str:string;
  i:integer;
begin
  str:=inputbox('查找','请输入英文单词','');
  i:=MyDic.SearchEnglish(str);
  if i<>0 then listbox1.ItemIndex:=i-1
      else showmessage('未找到');
end;

end.

