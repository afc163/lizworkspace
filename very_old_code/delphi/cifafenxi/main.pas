unit main;

interface

uses
  Windows, Messages, SysUtils, Variants, Classes, Graphics, Controls, Forms,
  Dialogs, StdCtrls;

type
  TForm1 = class(TForm)
    ListBox1: TListBox;
    GroupBox1: TGroupBox;
    Memoinput: TMemo;
    Button1: TButton;
    GroupBox2: TGroupBox;
    Label1: TLabel;
    OpenDialog1: TOpenDialog;
    Button2: TButton;
    Button3: TButton;
    SaveDialog1: TSaveDialog;
    procedure Button2Click(Sender: TObject);
    procedure FormCreate(Sender: TObject);
    procedure Button1Click(Sender: TObject);
    procedure Button3Click(Sender: TObject);
  private
    { Private declarations }
  public
    { Public declarations }
  end;

  Tword=record
         keyword:string[5];
         keynum:integer;
         end;

   TDic=class(Tobject)
   private
     word:array[1..50]of Tword;
     count:integer;
   public
     constructor  Create();
     function search(str:string):integer;
   end;

var
  Form1: TForm1;


implementation

var
  kword:TDic;

{$R *.dfm}

constructor  TDic.Create();
var
  fkey:textfile;
  ch:string;
begin
   AssignFile(fkey,'.\keyword.txt');
   if not FileExists('.\keyword.txt') then
         messagebox(0,'不存在keyword文件!','错误',mb_ok+mb_iconexclamation)
   else
   begin
     count:=0;
     reset(fkey);
     while not EOF(fkey) do
     begin
       inc(count);
       read(fkey,word[count].keyword);
       word[count].keyword:=Trim(word[count].keyword);
       read(fkey,word[count].keynum);
       ch:=IntToStr(word[count].keynum);
       Form1.ListBox1.Items.Add(word[count].keyword+'     '+ch);
       readln(fkey);
      end;
     closefile(fkey);
   end;
end;

function TDic.search(str:string):integer;
var
  i:integer;
  sign:boolean;
begin
  i:=0;
  sign:=false;
  str:=LowerCase(str);
  while (not sign) and (i<=count-1) do
  begin
    inc(i);
    if str=word[i].keyword then sign:=true;
  end;
  if sign then result:=word[i].keynum
    else result:=0;
end;

procedure TForm1.Button2Click(Sender: TObject);
begin
    if OpenDialog1.Execute then
       self.Memoinput.Lines.LoadFromFile(OpenDialog1.FileName);
end;

procedure TForm1.Button3Click(Sender: TObject);
begin
    if SaveDialog1.Execute then
       self.ListBox1.Items.SaveToFile(SaveDialog1.FileName);
end;

procedure TForm1.FormCreate(Sender: TObject);
begin
   kword:=TDic.Create;
   Memoinput.Clear;
end;

procedure TForm1.Button1Click(Sender: TObject);
var
   i:integer;
   str,ch,word:string;
begin
    self.ListBox1.Clear;
    str:=Trim(self.Memoinput.Text);
    i:=1;
    while i<=length(str) do
    begin
      word:='';
      while(str[i]=' ')or(str[i]=chr(13))or(str[i]=chr(10)) do inc(i);
 	    if((str[i]<='Z')and(str[i]>='A'))or((str[i]<='z')and(str[i]>='a')) then	//标识符&关键字
        while((str[i]<='Z')and(str[i]>='A'))or((str[i]<='z')and(str[i]>='a'))
                         or((str[i]<='9')and(str[i]>='0'))or(str[i]='_') do
        begin
			   	word:=word+str[i];
          inc(i);
			  end
	  	else if(str[i]<='9')and(str[i]>='0') then		//数字
            while(str[i]<='9')and(str[i]>='0') do
            begin
              word:=word+str[i];
              inc(i);
            end
	  	else if(str[i] in ['+','*','-','/','(',')','[',']','{','}',':',';',','])then
            begin
              word:=word+str[i];
              inc(i);
            end
	  	else if(str[i] in ['<','=', '!','>'])then
	         begin
             word:=word+str[i];
             inc(i);
			  	   if(str[i]='=') then
             begin
               word:=word+str[i];
               inc(i);
             end;
           end
      else if(str[i]='&')then
	         begin
			       word:=word+str[i];
             inc(i);
             if(str[i]='&') then
             begin
               word:=word+str[i];
               inc(i);
             end;
           end
		  else if(str[i]='|')then
      	  begin
			   	  word:=word+str[i];
            inc(i);
			    	if(str[i]='|') then
            begin
              word:=word+str[i];
              inc(i);
            end;
          end
      else
         begin
           messagebox(0,'出现未知符号!','错误',mb_ok+mb_iconexclamation);
           break;
         end;
      ch:=IntToStr(kword.search(word));
      if ch<>'0' then self.ListBox1.Items.Add(word+'-----'+ch)
      else if word[1] in ['0'..'9'] then  self.ListBox1.Items.Add(word+'----'+'2')
           else self.ListBox1.Items.Add(word+'------'+'1');
   end;
end;

end.
