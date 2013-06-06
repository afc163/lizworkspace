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
    Button4: TButton;
    procedure Button2Click(Sender: TObject);
    procedure FormCreate(Sender: TObject);
    procedure Button1Click(Sender: TObject);
    procedure Button3Click(Sender: TObject);
    procedure Button4Click(Sender: TObject);
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
  str:string;
  inum,wn:integer;
  flag:boolean;

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
    if(str[1] in ['0'..'9'])then result:=2
    else
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
      else result:=1;
    end;
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

function  getword(var w:string):integer;
var
  word:string;
begin
   word:='';
   if inum<=length(str) then
   begin
      while(str[inum]=' ')or(str[inum]=chr(13))or(str[inum]=chr(10)) do inc(inum);
 	    if((str[inum]<='Z')and(str[inum]>='A'))or((str[inum]<='z')and(str[inum]>='a')) then	//标识符&关键字
        while((str[inum]<='Z')and(str[inum]>='A'))or((str[inum]<='z')and(str[inum]>='a'))
                         or((str[inum]<='9')and(str[inum]>='0'))or(str[inum]='_') do
        begin
			   	word:=word+str[inum];
          inc(inum);
			  end
	  	else if(str[inum]<='9')and(str[inum]>='0') then	//数字
             while(str[inum]<='9')and(str[inum]>='0') do
             begin
               word:=word+str[inum];
               inc(inum);
             end
	  	else if(str[inum] in ['+','*','-','/','(',')','[',']','{','}',':',';',','])then
            begin
              word:=word+str[inum];
              inc(inum);
            end
	  	else if(str[inum] in ['<','=', '!','>'])then
	         begin
             word:=word+str[inum];
             inc(inum);
			  	   if(str[inum]='=') then
             begin
               word:=word+str[inum];
               inc(inum);
             end;
           end
      else if(str[inum]='&')then
	         begin
			       word:=word+str[inum];
             inc(inum);
             if(str[inum]='&') then
             begin
               word:=word+str[inum];
               inc(inum);
             end;
           end
		  else if(str[inum]='|')then
	         begin
			   	   word:=word+str[inum];
             inc(inum);
			    	 if(str[inum]='|') then
             begin
               word:=word+str[inum];
               inc(inum);
             end;
           end
      else
      begin
        messagebox(0,'出现未知符号!','错误',mb_ok+mb_iconexclamation);
        inc(inum);
      end;
   end;
   if(word<>'')then result:=kword.search(word)
   else result:=-1;
   w:=word;
end;

procedure TForm1.Button1Click(Sender: TObject);
var
  ch,w:string;
  wordnum:integer;
begin
    self.ListBox1.Clear;
    str:=Trim(self.Memoinput.Text);
    inum:=1;
    ch:='';
    while inum<=length(str) do
    begin
      wordnum:=getword(w);
      if(wordnum<>-1)then
      begin
        ch:=IntToStr(wordnum);
        self.ListBox1.Items.Add(w+'-----'+ch)
      end
    end;
end;

procedure TForm1.Button4Click(Sender: TObject);
var
  w:string;

procedure T();forward;
procedure E();
begin
    T();
    while(wn=3)or(wn=4)do   //+ -
    begin
      wn:=getword(w);
      T();
    end;
end;

procedure F();forward;
procedure T();
begin
    F();
    while(wn=5)or(wn=6)do     //* /
    begin
      wn:=getword(w);
      F();
    end;
end;

procedure F();
begin
    if(wn=1)or(wn=2)then wn:=getword(w)
    else
      if(wn=17) then
      begin
        wn:=getword(w);
        E();
        if(wn=18)then wn:=getword(w)
        else
        begin
          messagebox(0,'少)','错误',mb_ok+mb_iconexclamation); 
          flag:=false;  exit;
        end;
      end
    else
    begin
       messagebox(0,'少因子','错误',mb_ok+mb_iconexclamation); 
       flag:=false;   exit;
    end;
end;

begin
    w:='';
    flag:=true;
    str:=Trim(self.Memoinput.Text);
    inum:=1;
    wn:=getword(w);
    if(wn=1)then
    begin
      wn:=getword(w);
      if(wn=16)then
      begin
        wn:=getword(w);
        E();
      end
      else
      begin
        messagebox(0,'少=!','错误',mb_ok+mb_iconexclamation); 
        flag:=false; exit;
      end;
    end
    else
    begin
      messagebox(0,'不是赋值语句!','错误',mb_ok+mb_iconexclamation); 
      flag:=false; exit;
    end;
    if flag then messagebox(0,'语法分析正确','正确',mb_ok+mb_iconexclamation);
end;

end.
