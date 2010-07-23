unit main;

interface

uses
  Windows, Messages, SysUtils, Variants, Classes, Graphics, Controls, Forms,
  Dialogs, StdCtrls, Buttons, Menus;

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
    ListBox2: TListBox;
    Label2: TLabel;
    Label3: TLabel;
    GroupBox3: TGroupBox;
    Memoerror: TMemo;
    BitBtn1: TBitBtn;
    procedure Button2Click(Sender: TObject);
    procedure FormCreate(Sender: TObject);
    procedure Button1Click(Sender: TObject);
    procedure Button3Click(Sender: TObject);
    procedure Button4Click(Sender: TObject);
    procedure changeflag(Sender: TObject; var Key: Char);
    procedure BitBtn1Click(Sender: TObject);
  private
    { Private declarations }
  public
    { Public declarations }
  end;

var
  Form1: TForm1;

implementation

uses assist,signstack,statestack,placestack;

var
  kword:TDic;
  Sign_table:TSigntable;
  sp1:Tstacknum;
  sp2:Tstacksign;
  sp3:Tstackvalue;
  strpragram:string;
  inum:integer;

var
   quad:array[1..MAX_QUAD]of TQuade;
   quadnum:integer;
   NXQ:integer;
   newtemp:integer;
   flag:boolean;

   stabi:integer;
   temp:TOne_sign;

   B,E,F:TTCFC;

   ROP:string;

   newlabel:integer;
   B_true,B_false,E_true,E_false,S_next,S_begin:string;
   Code:array[1..MAX_CODE] of TH;
   codenum:integer;

   ifand:boolean=false;

{$R *.dfm}

procedure TForm1.Button2Click(Sender: TObject);
begin
    if OpenDialog1.Execute then
       self.Memoinput.Lines.LoadFromFile(OpenDialog1.FileName);
    flag:=true;
end;

procedure TForm1.Button3Click(Sender: TObject);
begin
    if SaveDialog1.Execute then
    begin
       self.ListBox1.Items.SaveToFile(SaveDialog1.FileName);
       self.ListBox2.Items.SaveToFile(SaveDialog1.FileName+'代码');
    end;
end;

procedure TForm1.FormCreate(Sender: TObject);
var
  i:integer;
  word:Tword;
begin
   Form1.Label2.Caption:='关键字';
   kword:=TDic.Create;
   for i:=1 to kword.getcount do
   begin
     word:=kword.getoneword(i);
     Form1.ListBox1.Items.Add(word.keyword+'      '+IntToStr(word.keynum));
   end;
   Sign_table:=TSigntable.Create;
   sp1:=Tstacknum.initstack;
   sp2:=Tstacksign.initstack;
   sp3:=Tstackvalue.initstack;
   Memoinput.Clear;
   flag:=false;
end;

procedure TForm1.changeflag(Sender: TObject; var Key: Char);
begin
  flag:=true;
end;

function  getword():TOne_sign;
var
  one_word:string;
  value:integer;
  temp:TOne_sign;
begin
   one_word:='';
   value:=MAX_NUMBLE;
   if inum<=length(strpragram) then
   begin
      while(strpragram[inum]=' ')or(strpragram[inum]=chr(13))or(strpragram[inum]=chr(10)) do inc(inum);
 	    if strpragram[inum] in ['A'..'Z','a'..'z']  then	//标识符&关键字
        while(strpragram[inum] in ['A'..'Z','a'..'z','0'..'9','_']) do
        begin
			   	one_word:=one_word+strpragram[inum];
          inc(inum);
			  end
	  	else if strpragram[inum] in ['0'..'9'] then	//数字
             while(strpragram[inum] in ['0'..'9']) do
             begin
               one_word:=one_word+strpragram[inum];
               value:=value*10+ord(strpragram[inum])-48;
               inc(inum);
             end
	  	else if(strpragram[inum] in ['+','*','-','/','(',')','[',']','{','}',':',';',',','#'])then
            begin
              one_word:=one_word+strpragram[inum];
              inc(inum);
            end
	  	else if(strpragram[inum] in ['<','=', '!','>'])then
	         begin
             one_word:=one_word+strpragram[inum];
             inc(inum);
			  	   if(strpragram[inum]='=') then
             begin
               one_word:=one_word+strpragram[inum];
               inc(inum);
             end;
           end
      else if(strpragram[inum]='&')then
	         begin
			       one_word:=one_word+strpragram[inum];
             inc(inum);
             if(strpragram[inum]='&') then
             begin
               one_word:=one_word+strpragram[inum];
               inc(inum);
             end;
           end
		  else if(strpragram[inum]='|')then
	         begin
			   	   one_word:=one_word+strpragram[inum];
             inc(inum);
			    	 if(strpragram[inum]='|') then
             begin
               one_word:=one_word+strpragram[inum];
               inc(inum);
             end;
           end
      else
      begin
        inc(inum);
      end;
   end;
   if(one_word<>'')then
   begin
     temp.name:=one_word;
     temp.wordnum:=kword.search(one_word);
     temp.value:=value;
     if temp.wordnum=1 then temp.properties:=PTVAR
     else if temp.wordnum=2 then temp.properties:=CONS
     else  temp.properties:=OTHER;
   end
   else
   begin
     temp.properties:=ERROR;
     temp.name:=strpragram[inum-1];
   end;
   result:=temp;
end;

function cifafenxi:boolean;
var
  word:TOne_sign;
  sign:boolean;
begin
    sign:=true;
    while inum<=length(strpragram) do
    begin
      word:=getword();
      if(word.properties<>ERROR)then
      begin
        Form1.ListBox1.Items.Add(word.name+'---->'+IntToStr(word.wordnum));
        inc(Sign_table.count);
        Sign_table.signtable[Sign_table.count]:=word;
      end
      else
      begin
        Form1.Memoerror.Lines.Add('错误     出现未知符号:'+word.name);
        sign:=false;
      end;
    end;
    if not sign then result:=false
    else result:=true;
end;

function getstate(state:integer;str:string):integer;
var
  i:integer;
begin
  i:=0;
	while Character1[i]<>str do inc(i);
	result:=LRtable1[state,i];   //showmessage(Inttostr(state)+str+IntToStr(result)+'lkl'+Inttostr(i));
end;

function getstatesuan(state:integer;str:string):integer;
var
  i:integer;
begin
  i:=0;
	while Character[i]<>str do inc(i);
	result:=LRtable[state,i];
end;

function getstatebool(state:integer;str:string):integer;
var
  i:integer;
begin
  i:=0;
	while CharacterB[i]<>str do inc(i);
	result:=LRtablebool[state,i];
end;

function gencode(address:integer):string;
begin
  if(address>MAX_SIGNTAB) then result:=IntToStr(address-MAX_SIGNTAB)
  else if (address<>-1) and (address<>0) then result:=Sign_table.signtable[address].name
	else result:='-';
end;

procedure gen(op:string;arg1:integer;arg2:integer;result:integer);
begin
	quad[quadnum].op:=op;
	quad[quadnum].arg1:=arg1;
	quad[quadnum].arg2:=arg2;
	quad[quadnum].result:=result;
	if op='=' then
  begin
    Sign_table.Signtable[result].value:=Sign_table.Signtable[arg1].value;
  end
  else if op='+' then
	begin
    Sign_table.Signtable[result].value:=Sign_table.Signtable[arg1].value+Sign_table.Signtable[arg2].value;
  end
  else if op='-' then
	begin
    Sign_table.Signtable[result].value:=Sign_table.Signtable[arg1].value-Sign_table.Signtable[arg2].value;
  end
  else if op='*' then
  begin
    Sign_table.Signtable[result].value:=Sign_table.Signtable[arg1].value*Sign_table.Signtable[arg2].value;
  end
  else if op='/' then
	begin
    Sign_table.Signtable[result].value:=Sign_table.Signtable[arg1].value div Sign_table.Signtable[arg2].value;
  end
  else if op='@' then
	begin
    Sign_table.Signtable[result].value:=-Sign_table.Signtable[arg1].value;
  end
  else if op='jop' then
  begin
     quad[quadnum].op:='j'+ROP;
  end
  else;
	inc(quadnum);
	NXQ:=quadnum;
end;

procedure huibiancode(op:string;arg1:string;arg2:string);
begin
     inc(codenum);
     Code[codenum].op:=op;
     Code[codenum].op1:=arg1;
     if arg2<>'-1' then    Code[codenum].op2:=arg2
     else Code[codenum].op2:='';
end;

procedure ntemp;
begin
  inc(newtemp);
  Sign_table.signtable[newtemp].name:='temp'+IntToStr(newtemp-MAX_SIGNTAB div 2);
  Sign_table.signtable[newtemp].properties:=TEMPVAR;
end;

function suanshu():integer;           //算术表达式
var                                    //返回表达式值的地址
  word:string;
  wordaddress:integer;
  acc:boolean;
  state,newstate:integer;

  Eplace,iplace,t,E1place,E2place:integer;

begin
    sp1.push(0);sp2.push('#');sp3.push(0);
    wordaddress:=stabi;
    word:='i';
    inc(stabi);

    acc:=false;
    while(not acc)do
    begin
      state:=sp1.gettop;
      newstate:=getstatesuan(state,word);
			if(newstate=-1) then
			begin
				Form1.Memoerror.Lines.Add('错误        在'+word+'附近');
        result:=-1; // 错误
 				exit;
			end;
			if(newstate=-2)then
      begin
        acc:=true;
        result:=sp3.gettop; //正确
        sp1.pop;sp2.pop;sp3.pop;
        sp1.pop;sp2.pop;sp3.pop;
      end;
			if(newstate<100)and(newstate>=0)then
			begin
				sp1.push(newstate);
				sp2.push(word);
				if(word='i') then sp3.push(wordaddress)
				else sp3.push(0);
        temp:=Sign_table.signtable[stabi];
        if(temp.wordnum=1) or(temp.wordnum=2)then
        begin
          wordaddress:=stabi;
          word:='i';
        end
        else if temp.wordnum in[24,22]then      //;,}
        begin
          word:='#';
          dec(stabi);
        end
        else if temp.wordnum in[3,4,5,6,16,17,18]then word:=temp.name
        else
        begin
          Form1.Memoerror.Lines.Add('错误：表达式中出现其他符号。'+'在'+temp.name+'处');
          result:=-1;
          exit;
        end;
        inc(stabi);
			end;
			if(newstate>=101)and(newstate<200)then
				   case(newstate)of
				   101://A->i=E
                 begin
						       sp1.pop();sp1.pop();sp1.pop();
                   sp2.pop();sp2.pop();sp2.pop();
                   sp2.push('A');
                   Eplace:=sp3.pop();
                   sp3.pop();
                   iplace:=sp3.pop();
                   t:=sp1.gettop();
                   sp1.push(getstatesuan(t,'A'));
                   gen('=',Eplace,-1,iplace);
                   sp3.push(iplace);
                   ///////////////////////////////////////////////////////////////
                   huibiancode('MOV','R0',Sign_table.signtable[Eplace].name);
                   huibiancode('MOV',Sign_table.signtable[iplace].name,'R0');
                 end;
           102://E->E+E
					       begin
						       sp1.pop();sp1.pop();sp1.pop();
                   sp2.pop();sp2.pop();sp2.pop();
                   sp2.push('E');
                   E2place:=sp3.pop();
                   sp3.pop();
						       E1place:=sp3.pop();
						       t:=sp1.gettop();
                   sp1.push(getstatesuan(t,'E'));
						       ntemp;
						       gen('+',E1place,E2place,newtemp);
						       sp3.push(newtemp);
                   ///////////////////////////////////////////////////////////////
                   huibiancode('MOV','R0',Sign_table.signtable[E1place].name);
                   huibiancode('ADD','R0',Sign_table.signtable[E2place].name);
                   huibiancode('MOV',Sign_table.signtable[newtemp].name,'R0');
					       end;
           103://E->E-E
                 begin
						       sp1.pop();sp1.pop();sp1.pop();
                   sp2.pop();sp2.pop();sp2.pop();
                   sp2.push('E');
                   E2place:=sp3.pop();
                   sp3.pop();
                   E1place:=sp3.pop();
                   t:=sp1.gettop();
                   sp1.push(getstatesuan(t,'E'));
                   ntemp;
                   gen('-',E1place,E2place,newtemp);
                   sp3.push(newtemp);
                   ///////////////////////////////////////////////////////////////
                   huibiancode('MOV','R0',Sign_table.signtable[E1place].name);
                   huibiancode('SUB','R0',Sign_table.signtable[E2place].name);
                   huibiancode('MOV',Sign_table.signtable[newtemp].name,'R0');
                 end;
           104://E->E*E
                 begin
                   sp1.pop();sp1.pop();sp1.pop();
                   sp2.pop();sp2.pop();sp2.pop();
                   sp2.push('E');
                   E2place:=sp3.pop();
                   sp3.pop();
                   E1place:=sp3.pop();
                   t:=sp1.gettop();
						       sp1.push(getstatesuan(t,'E'));
                   ntemp;
                   gen('*',E1place,E2place,newtemp);
                   sp3.push(newtemp);
                   ///////////////////////////////////////////////////////////////
                   huibiancode('MOV','R0',Sign_table.signtable[E1place].name);
                   huibiancode('MUL','R0',Sign_table.signtable[E2place].name);
                   huibiancode('MOV',Sign_table.signtable[newtemp].name,'R0');
                 end;
           105://E->E/E
					       begin
						       sp1.pop();sp1.pop();sp1.pop();
                   sp2.pop();sp2.pop();sp2.pop();
                   sp2.push('E');
                   E2place:=sp3.pop();
                   sp3.pop();
                   E1place:=sp3.pop();
                   t:=sp1.gettop();
                   sp1.push(getstatesuan(t,'E'));
                   ntemp;
                   gen('/',E1place,E2place,newtemp);
                   sp3.push(newtemp);
                   ///////////////////////////////////////////////////////////////
                   huibiancode('MOV','R0',Sign_table.signtable[E1place].name);
                   huibiancode('DIV','R0',Sign_table.signtable[E2place].name);
                   huibiancode('MOV',Sign_table.signtable[newtemp].name,'R0');
                 end;
           106://E->(E)
				       	 begin
						       sp1.pop();sp1.pop();sp1.pop();
                   sp2.pop();sp2.pop();sp2.pop();
                   sp2.push('E');
                   sp3.pop();
                   Eplace:=sp3.pop();
                   sp3.pop();
                   t:=sp1.gettop();
                   sp1.push(getstatesuan(t,'E'));
                   sp3.push(Eplace);
                 end;
           107://E->i
					       begin
						       sp1.pop();sp2.pop();
                   sp2.push('E');
                   sp1.push(getstatesuan(sp1.gettop(),'E'));
                 end;
           108://E->-E
					       begin
                   sp1.pop();sp1.pop();
						       sp2.pop();sp2.pop();
                   sp2.push('E');
                   E1place:=sp3.pop();
                   sp3.pop();
                   t:=sp1.gettop();
                   sp1.push(getstatesuan(t,'E'));
                   ntemp;
                   gen('@',E1place,-1,newtemp);
                   sp3.push(newtemp);
                   ///////////////////////////////////////////////////////////////
                   huibiancode('MOV','R0','0');
                   huibiancode('SUB','R0',Sign_table.signtable[E1place].name);
                   huibiancode('MOV',Sign_table.signtable[newtemp].name,'R0');
                 end;
            end;
      end;
end;

function thisislast(i:integer):boolean;
begin
  inc(i);                                                            //')'   ';'
  while (Sign_table.signtable[i].wordnum<>18)and(Sign_table.signtable[i].wordnum<>24)and(Sign_table.signtable[i].wordnum<>22) do inc(i);
  if Sign_table.signtable[i].name=')' then result:=false
  else result:=true;
end;

procedure bp(p:integer;t:integer); //用t号四元式回填以p为链首的链
var
  q:integer;
begin
  q:=p;
  t:=t+MAX_SIGNTAB;
  while q<>0 do
  begin
    p:=quad[q].result;
    quad[q].result:=t;
    q:=p;
  end;
end;

function merg(p1:integer;p2:integer):integer;//p1 p2 拉链
var
  p:integer;
begin
  if(p2=0) then result:=p1
  else
  begin
    p:=p2;
    while(quad[p].result<>0) do p:=quad[p].result;
    quad[p].result:=p1;
    result:=p2;
  end;
end;

function bool():integer;           //布尔表达式
var                                    //返回表达式值的地址
  word:string;
  wordaddress:integer;
  acc:boolean;
  state,newstate,B1place,B2place:integer;
begin
    sp1.push(0);sp2.push('#');sp3.push(0);

    wordaddress:=stabi;
    word:='i';

    inc(stabi);

    acc:=false;
    while(not acc)do
    begin
      state:=sp1.gettop;
      newstate:=getstatebool(state,word);
			if(newstate=-1) then
			begin
				Form1.Memoerror.Lines.Add('错误        在'+word+'附近');
        result:=-1; // 错误
 				exit;
			end;
			if(newstate=-2)then
      begin
        acc:=true;
        result:=sp3.gettop; //正确
        sp1.pop;sp2.pop;sp3.pop;
        sp1.pop;sp2.pop;sp3.pop;
      end;
			if(newstate<100)and(newstate>=0)then
			begin
				sp1.push(newstate);
				sp2.push(word);
				if(word='i') then sp3.push(wordaddress)
				else sp3.push(0);
        temp:=Sign_table.signtable[stabi];
        if (temp.wordnum=1) or (temp.wordnum=2) then
        begin
          wordaddress:=stabi;
          word:='i';
        end
        else if (temp.wordnum =18) and (thisislast(stabi)) then     //)
        begin
          word:='#';
          dec(stabi);
        end
        else if temp.wordnum in[7,8,9,10,11,12,14,15,17,18,59]then
        begin
          if temp.wordnum in[7,8,9,10,11,12] then
          begin
            word:='rop';
            ROP:=temp.name;
          end
          else  word:=temp.name;
        end
        else
        begin
          Form1.Memoerror.Lines.Add('错误：表达式中出现其他符号。'+'在'+temp.name+'处');
          result:=-1;
          exit;
        end;
        inc(stabi);
			end;
			if(newstate>=101)and(newstate<200)then
				   case(newstate)of  //rop->> rop->< rop->==  rop->>= rop-><= rop->!=
 				   101://B->i
                 begin
                   B.TC:=NXQ;
                   B.FC:=NXQ+1;
                   gen('jnz',sp3.gettop,-1,0);
                   gen('jmp',-1,-1,0);

                   sp1.pop;sp2.pop;
                   sp2.push('B');          
                   sp1.push(getstatebool(sp1.gettop,'B'));
 ///////////////////////////////////////////////////////////////////////
                   E_true:=B_true;
                   E_false:=B_false;
                   B_true:='L'+IntToStr(newlabel);
                   inc(newlabel);
                   B_false:='L'+IntToStr(newlabel);
                   inc(newlabel);
                   huibiancode('CMP',Sign_table.signtable[sp3.gettop].name,'0');
                   huibiancode('JNZ',B_true,'-1');
                   huibiancode('JMP',B_false,'-1');
                 end;
           102://B->B1 rop B2
                 begin
                   sp1.pop;sp1.pop;sp1.pop;
                   sp2.pop;sp2.pop;sp2.pop;
                   B1place:=sp3.pop;sp3.pop;B2place:=sp3.pop;
                   sp2.push('B');
                   sp1.push(getstatebool(sp1.gettop,'B'));

                   B.TC:=NXQ;B.FC:=NXQ+1;

                   //dec(quadnum);dec(NXQ);dec(quadnum);dec(NXQ);dec(quadnum);dec(NXQ);dec(quadnum);dec(NXQ);
                   gen('jop',B2place,B1place,0);
                   gen('jmp',-1,-1,0);
     ///////////////////////////////////////////////////////////
                   huibiancode('CMP',Sign_table.signtable[B2place].name,Sign_table.signtable[B1place].name);
                   huibiancode('JNC',B_true,'-1');
                   huibiancode('JMP',B_false,'-1');
                 end;
           103://B->(B1)
                 begin
                   sp1.pop;sp1.pop;sp1.pop;
                   sp2.pop;sp2.pop;sp2.pop;
                   sp3.pop;sp3.pop;
                   sp2.push('B');
                   sp1.push(getstatebool(sp1.gettop,'B'));
                   ///////////////////////////////////////////////////
                   E_true:=B_true;
                   E_false:=B_false;
                 end;
           104://B->!B1
                 begin
                   sp1.pop;sp1.pop;
                   sp2.pop;sp2.pop;
                   sp3.pop;
                   sp2.push('B');
                   sp1.push(getstatebool(sp1.gettop,'B'));

                   F.TC:=B.TC;F.FC:=B.FC;
                   B.TC:=F.FC;
                   B.FC:=F.TC;
                   ////////////////////////////////////////////////////////////////
                   E_true:=B_false;
                   E_false:=B_true;
                 end;
           105://A->B&&
                 begin
                   sp1.pop;sp1.pop;
                   sp2.pop;sp2.pop;
                   sp3.pop;
                   sp2.push('A');
                   sp1.push(getstatebool(sp1.gettop,'A'));

                   bp(B.TC,NXQ); E.FC:=B.FC;
      ////////////////////////////////////////////////////////
                   //E_false:=B_false;
                   //E_true:='L'+IntToStr(newlabel);
                   //inc(newlabel);
                   //F_true:=B_true;
                   //F_false:=B_false;
                   //B1;
                   huibiancode(B_true,':','-1');
                   ifand:=true;
                 end;
           106://B->AB
                 begin
                   sp1.pop;sp1.pop;
                   sp2.pop;sp2.pop;
                   sp3.pop;
                   sp2.push('B');
                   sp1.push(getstatebool(sp1.gettop,'B'));

                   B.FC:=merg(E.FC,B.FC);
/////////////////////////////////////////////////////////////////////////
                   //huibiancode(B_true,':','-1');
                   //B2;
                 end;
           107://O->B||
                 begin
                   sp1.pop;sp1.pop;
                   sp2.pop;sp2.pop;
                   sp3.pop;
                   sp2.push('O');
                   sp1.push(getstatebool(sp1.gettop,'O'));

                   bp(B.FC,NXQ); E.TC:=B.TC;
      ////////////////////////////////////////////////////////
                   //E_true:=B_true;
                   //E_false:='L'+IntToStr(newlabel);
                   //inc(newlabel);
                   //F_true:=B_true;
                   //F_false:=B_false;
                   //B1;
                   huibiancode(B_false,':','-1');
                 end;
           108://B->OB
                 begin
                   sp1.pop;sp1.pop;
                   sp2.pop;sp2.pop;
                   sp3.pop;
                   sp2.push('B');
                   sp1.push(getstatebool(sp1.gettop,'B'));

                   B.TC:=merg(E.TC,B.TC);
 //////////////////////////////////////////////////////////////
                    huibiancode(E_true,':','-1');
                    //huibiancode(B_true,':','-1');
                   //B2;
                 end;
            end;
      end;
end;

function xufafenxi():boolean;
var
  word:string;
  wordaddress:integer;
  acc:boolean;
  state,newstate:integer;
  q,NXQtemp:integer;
  Tchain,Schain:integer;
  quadtemp:TQuade;
begin
    NXQtemp:=0;
    Schain:=0;Tchain:=0;

    temp:=Sign_table.signtable[stabi];

    if temp.wordnum=1 then   //表示是表达式
    begin
      wordaddress:=suanshu();
      if wordaddress=-1 then
      begin
        Form1.Memoerror.Lines.Add('错误：表达式中'+'在'+temp.name+'处');
        result:=false;
        exit;
      end;
      word:='A';
      dec(stabi);
    end
    else word:=temp.name;

    inc(stabi);

    acc:=false;
    while(not acc)do
    begin
      state:=sp1.gettop;
      newstate:=getstate(state,word);   //showmessage(word+IntToStr(newstate));
			if(newstate=-1) then
			begin
				Form1.Memoerror.Lines.Add('错误        在'+word+'附近');
        result:=false;
 				exit;
			end;
			if(newstate=-2)then
      begin
        acc:=true;
        result:=true;
      end;
			if(newstate<100)and(newstate>=0)then
			begin
				sp1.push(newstate);   
				sp2.push(word);
				if(word='A') then
        begin
          sp3.push(wordaddress);
        end
				else sp3.push(0);
        temp:=Sign_table.signtable[stabi];

        if temp.wordnum in[1,2] then   //表示是表达式  或布尔表达式
        begin
           if Sign_table.signtable[stabi+1].name='=' then
           begin
             NXQtemp:=NXQ;
             wordaddress:=suanshu();
             if wordaddress=-1 then
             begin
               Form1.Memoerror.Lines.Add('错误：表达式中'+'在'+temp.name+'处');
               result:=false;
               exit;
             end;
             word:='A';
           end
           else
           begin
             NXQtemp:=NXQ;
             wordaddress:=bool();
             if wordaddress=-1 then
             begin
               Form1.Memoerror.Lines.Add('错误: 布尔表达式中 在'+temp.name+'处');
               result:=false;
               exit;
             end;
             word:='B';
           end;
           dec(stabi);
        end
        else if temp.wordnum=35 then  //else
        begin
           word:=temp.name;
           q:=NXQ;
           gen('jmp',-1,-1,0);
        end
        else word:=temp.name;
        inc(stabi);
			end;
			if(newstate>=101)and(newstate<200)then
				   case(newstate)of
				   101:   //C->if(B)                  //if语句
                 begin
						       sp1.pop;sp1.pop;sp1.pop;sp1.pop;
                   sp2.pop;sp2.pop;sp2.pop;sp2.pop;
                   sp2.push('C');
                   sp3.pop;sp3.pop;sp3.pop;
                   sp1.push(getstate(sp1.gettop(),'C'));
                    //B;
                   if (NXQtemp<=NXQ)and(NXQtemp<>0) then
                   begin
                     bp(B.TC,NXQtemp);   //showmessage('fdgf'+Inttostr(nxqtemp));
                     NXQtemp:=0;
                   end
                   else  bp(B.TC,NXQ);
                 end;
           102: //T->CS1 else
					       begin
						       sp1.pop;sp1.pop;sp1.pop;
                   sp2.pop;sp2.pop;sp2.pop;
                   sp2.push('T');
                   sp3.pop;sp3.pop;
                   sp1.push(getstate(sp1.gettop,'T'));
                   if (NXQtemp<=NXQ)and(NXQtemp<>0) then
                   begin
                     bp(B.FC,NXQtemp);
                     NXQtemp:=0;
                   end
                   else  bp(B.FC,NXQ);
                   Tchain:=merg(Schain,q);  //s1chain
					       end;
           103:  //S->TS2
                 begin
						       sp1.pop;sp1.pop;
                   sp2.pop;sp2.pop;
                   sp2.push('S');
                   sp3.pop;
                   sp1.push(getstate(sp1.gettop,'S'));
						         //S2;
                   Schain:=merg(Tchain,Schain);
                 end;
           104://S->CS1
                 begin
						       sp1.pop;sp1.pop;
                   sp2.pop;sp2.pop;
                   sp2.push('S');
                   sp3.pop;
                   sp1.push(getstate(sp1.gettop,'S'));

                   Schain:=merg(B.FC,Schain);
                 end;
           105://W->while                  while语句
					       begin
						       sp1.pop;
                   sp2.pop;
                   sp2.push('W');
                   sp1.push(getstate(sp1.gettop(),'W'));

                   q:=NXQ;
                   if (NXQtemp<=NXQ)and(NXQtemp<>0) then
                   begin
                     q:=NXQtemp;
                     NXQtemp:=0;
                   end
                 end;
           106://Wd->W(B)
				       	 begin
						       sp1.pop;sp1.pop;sp1.pop;sp1.pop;
                   sp2.pop;sp2.pop;sp2.pop;sp2.pop;
                   sp2.push('Wd');
                   sp3.pop;sp3.pop;sp3.pop;
                   sp1.push(getstate(sp1.gettop(),'Wd'));
                   //B;
                   if (NXQtemp<=NXQ)and(NXQtemp<>0) then
                   begin
                     bp(B.TC,NXQtemp);
                     NXQtemp:=0;
                   end
                   else  bp(B.TC,NXQ);
                 end;
           107://S->WdS1
					       begin
						       sp1.pop;sp1.pop;
                   sp2.pop;sp2.pop;
                   sp2.push('S');
                   sp3.pop;
                   sp1.push(getstate(sp1.gettop(),'S'));
                   //S1;
                   bp(Schain,q);
                   gen('jmp',-1,-1,q+MAX_SIGNTAB);
                   if (NXQtemp<=NXQ)and(NXQtemp<>0) then
                   begin
                     quadtemp:=quad[NXQ-1];
                     quad[NXQ-1]:=quad[NXQ-2];
                     quad[NXQ-2]:=quadtemp;
                     inc(NXQtemp);
                   end;
                   Schain:=B.FC;
                 end;
           108://R->do            do while 语句
					       begin
						       sp1.pop;
                   sp2.pop;
                   sp2.push('R');
                   sp1.push(getstate(sp1.gettop(),'R'));

                   q:=NXQ;
                   if (NXQtemp<=NXQ)and(NXQtemp<>0) then
                   begin
                     q:=NXQtemp;
                     NXQtemp:=0;
                   end
                 end;
           109://U->RS1 while           do while 语句
					       begin
						         //s1;
						       sp1.pop;sp1.pop;sp1.pop;
                   sp2.pop;sp2.pop;sp2.pop;
                   sp2.push('U');
                   sp3.pop;sp3.pop;
                   sp1.push(getstate(sp1.gettop(),'U'));

                    bp(Schain,NXQ);
                 end;
           110://S->U(B);           do while 语句
					       begin
                   //B;
						       sp1.pop;sp1.pop;sp1.pop;sp1.pop;sp1.pop;
                   sp2.pop;sp2.pop;sp2.pop;sp2.pop;sp2.pop;
                   sp2.push('S');
                   sp3.pop;sp3.pop;sp3.pop;sp3.pop;
                   sp1.push(getstate(sp1.gettop(),'S'));

                   bp(B.TC,q);
                   Schain:=B.FC;
                 end;
           111://S->{L]
                 begin
						       sp1.pop;sp1.pop;sp1.pop;
                   sp2.pop;sp2.pop;sp2.pop;
                   sp2.push('S');
                   sp3.pop;sp3.pop;
                   sp1.push(getstate(sp1.gettop(),'S'));

                   bp(Schain,NXQ);
                 end;
           112://S->A;
                 begin
						       sp1.pop; sp1.pop;
                   sp2.pop; sp2.pop;
                   sp3.pop;
                   sp2.push('S');
                   sp1.push(getstate(sp1.gettop(),'S'));
                 end;
           113://L->SL
                 begin
						       sp1.pop;sp1.pop;
                   sp2.pop;sp2.pop;
                   sp2.push('L');
                   sp3.pop;
                   sp1.push(getstate(sp1.gettop(),'L'));
                   if (NXQtemp<=NXQ)and(NXQtemp<>0) then
                   begin
                     bp(Schain,NXQtemp);
                     NXQtemp:=0;
                   end
                   else  bp(Schain,NXQ);
                   Schain:=0;
                 end;
           114://L->S
                 begin
						       sp1.pop;sp2.pop;
                   sp2.push('L');
                   sp1.push(getstate(sp1.gettop(),'L'));
                   if (NXQtemp<=NXQ)and(NXQtemp<>0) then
                   begin
                     bp(Schain,NXQtemp);
                     NXQtemp:=0;
                   end
                   else  bp(Schain,NXQ);
                   Schain:=0;
                 end;
            end;
      end;
end;

procedure display;
var
  i:integer;
begin
  for i:=1 to quadnum-1 do
  begin
  	Form1.ListBox2.Items.Add('('+IntToStr(i)+'):  '+quad[i].op+' '+gencode(quad[i].arg1)+' '+gencode(quad[i].arg2)+' '+gencode(quad[i].result));
  end
end;

procedure displaycode;    
var
  i:integer;
begin
  for i:=1 to codenum do
  begin
  	Form1.ListBox1.Items.Add(Code[i].op+' '+Code[i].op1+' '+Code[i].op2);
  end
end;

function digui:boolean;
var
  i:integer;
begin
    result:=true;
    temp:=Sign_table.signtable[stabi];
    if temp.wordnum=1 then   //表示赋值语句
    begin
      i:=suanshu();
      if i=-1 then
      begin
        Form1.Memoerror.Lines.Add('错误：表达式中'+'在'+temp.name+'处');
        result:=false;
        exit;
      end;
      temp:=Sign_table.signtable[stabi];
      if temp.wordnum=24 then      //;
      begin
        inc(stabi);
        exit;
      end
      else
      begin
        Form1.Memoerror.Lines.Add('错误：赋值语句缺;号');
        result:=false;
        exit;
      end;
    end
    else if temp.wordnum=41 then    //if
    begin
          S_next:='L'+IntToStr(newlabel);
          inc(newlabel);

          inc(stabi);
          temp:=Sign_table.signtable[stabi];
          if temp.wordnum=17 then //(
          begin
            inc(stabi);
            i:=bool();
            if i=-1 then
            begin
               Form1.Memoerror.Lines.Add('错误: 布尔表达式中 在'+temp.name+'处');
               exit;
            end;
            temp:=Sign_table.signtable[stabi];   
            if temp.wordnum=18 then //)
            begin
              inc(stabi);
              huibiancode(B_true,':','-1');
              digui;
              temp:=Sign_table.signtable[stabi];
              if temp.wordnum=35 then //else
              begin
                inc(stabi);
                huibiancode('JMP',S_next,'-1');
                if ifand then
                begin
                   huibiancode(E_false,':','-1');
                   ifand:=false;
                end;
                huibiancode(B_false,':','-1');
                digui;
                huibiancode(S_next,':','-1');
              end
              else
              begin
                if ifand then
                begin
                   huibiancode(E_false,':','-1');
                   ifand:=false;
                end;
                huibiancode(B_false,':','-1');
                //huibiancode(S_next,'dddd:','-1');
              end;
            end
            else begin
                   Form1.Memoerror.Lines.Add('错误：  IF语句中缺少)!');
                   result:=false;
                   exit;
                 end;
          end
          else begin
                   Form1.Memoerror.Lines.Add('错误：  IF语句中缺少(!');
                   result:=false;
                   exit;
                 end;
    end
    else if temp.wordnum=57 then     //while
    begin
        s_begin:='L'+IntToStr(newlabel);
        inc(newlabel);
        inc(stabi);
        temp:=Sign_table.signtable[stabi];

          if temp.wordnum=17 then //(
          begin
            huibiancode(S_begin,':','-1');
            inc(stabi);
            i:=bool();
            if i=-1 then
            begin
               Form1.Memoerror.Lines.Add('错误: 布尔表达式中 在'+temp.name+'处');
               result:=false;
               exit;
            end;
            temp:=Sign_table.signtable[stabi];
            if temp.wordnum=18 then //)
            begin
              huibiancode(B_true,':','-1');
              inc(stabi);
              digui;
              huibiancode('JMP',S_begin,'-1');
                if ifand then
                begin
                   huibiancode(E_false,':','-1');
                   ifand:=false;
                end;
              huibiancode(B_false,':','-1');
            end
            else begin
                   Form1.Memoerror.Lines.Add('错误：  WHILE语句中缺少)!');
                   result:=false;
                   exit;
                 end;
          end
          else begin
                   Form1.Memoerror.Lines.Add('错误：  WHILE语句中缺少(!');
                   result:=false;
                   exit;
                 end;
    end
    else if temp.wordnum=33 then       //do while
    begin
        s_begin:='L'+IntToStr(newlabel);
        inc(newlabel);
        huibiancode(S_begin,':','-1');

        inc(stabi);
        digui;
        temp:=Sign_table.signtable[stabi];
        if temp.wordnum=57 then //while
        begin
            inc(stabi);
            temp:=Sign_table.signtable[stabi];
            if temp.wordnum=17 then //(
            begin
              inc(stabi);
              i:=bool();
              if i=-1 then
              begin
                 Form1.Memoerror.Lines.Add('错误: 布尔表达式中 在'+temp.name+'处');
                 result:=false;
                 exit;
              end;
              temp:=Sign_table.signtable[stabi];
              if temp.wordnum=18 then //)
              begin
                huibiancode(B_true,':','-1');
                huibiancode('JMP',S_begin,'-1');
                if ifand then
                begin
                   huibiancode(E_false,':','-1');
                   ifand:=false;
                end;
                huibiancode(B_false,':','-1');
                inc(stabi);
                temp:=Sign_table.signtable[stabi];
                if temp.wordnum=24 then
                begin  inc(stabi);showmessage(Sign_table.signtable[stabi].name);end//;
                else begin
                     Form1.Memoerror.Lines.Add('错误：  DO  WHILE语句中缺少;!');
                     result:=false;
                     exit;
                     end;
              end
              else begin
                     Form1.Memoerror.Lines.Add('错误：  DO  WHILE语句中缺少)!');
                     result:=false;
                     exit;
                   end;
          end
          else begin
                   Form1.Memoerror.Lines.Add('错误：  DO  WHILE语句中缺少(!');
                   result:=false;
                   exit;
                 end;
        end
        else  begin
                   Form1.Memoerror.Lines.Add('错误：  DO  WHILE语句中缺少while!');
                   result:=false;
                   exit;
             end;
    end
    else if temp.wordnum=21 then       //{
    begin
      inc(stabi);
      temp:=Sign_table.signtable[stabi];
      while (temp.wordnum<>22) and (temp.wordnum<>58) do  digui;
      if temp.wordnum=22 then //}
      begin
        inc(stabi); 
        exit;
      end
      else
      begin
        Form1.Memoerror.Lines.Add('错误：  语句中缺少}!');
        result:=false;
        exit;
      end;
    end
    else if temp.wordnum=58 then   exit  //#
    else if temp.wordnum in [35,22,0] then    //else }
    begin
    end
    else
    begin
      showmessage(temp.name+inttostr(temp.wordnum)+Sign_table.signtable[stabi-1].name+Sign_table.signtable[stabi+1].name);
      Form1.Memoerror.Lines.Add('出现未知语句！');
      result:=false;
    end;
end;

procedure TForm1.Button1Click(Sender: TObject);
var
  sign:boolean;
begin
    if flag=true then
    begin
      self.Label2.Caption:='词法分析结果 属性字序列';
      self.Memoerror.Text:='词法分析中.....';
      self.ListBox1.Clear;
      strpragram:=Trim(Form1.Memoinput.Text)+'#';
      inum:=1;
      Sign_table.count:=0;
      sign:=cifafenxi();
      if sign then self.Memoerror.Lines.Add('词法分析正确结束！')
      else  self.Memoerror.Lines.Add('词法分析中有错误！');
    end;
end;

procedure TForm1.Button4Click(Sender: TObject);
var
  sign:boolean;
begin
  if flag=true then
  begin
      self.Label2.Caption:='词法分析结果 属性字序列';
      self.Memoerror.Text:='词法分析中.....';
      self.ListBox1.Clear;
      strpragram:=Trim(Form1.Memoinput.Text)+'#';
      inum:=1;
      Sign_table.count:=0;
      sign:=cifafenxi();
      if sign then self.Memoerror.Lines.Add('词法分析正确结束！')
      else  self.Memoerror.Lines.Add('词法分析中有错误！');

    self.Label3.Caption:='语法分析结果 中间代码';
    self.Memoerror.Lines.Add('语法分析中.....');
    self.ListBox2.Clear;
    newlabel:=1;
    codenum:=0;

    B.TC:=1;    B.FC:=1;
    quadnum:=1;
    NXQ:=1;
    newtemp:=MAX_SIGNTAB div 2;
    sp1.clear;
    sp2.clear;
    sp3.clear;

    stabi:=1;
    sp1.push(0);sp2.push('#');sp3.push(0);
    sign:=xufafenxi();
    if sign then self.Memoerror.Lines.Add('语法分析正确结束！')
    else  self.Memoerror.Lines.Add('语法分析中有错误！');
    display();
    flag:=false;
  end;
end;

procedure TForm1.BitBtn1Click(Sender: TObject);
var
  sign:boolean;
begin
  if flag=true then                       //显示汇编代码
  begin
      self.Label2.Caption:='词法分析结果 属性字序列';
      self.Memoerror.Text:='词法分析中.....';
      self.ListBox1.Clear;
      strpragram:=Trim(Form1.Memoinput.Text)+'#';
      inum:=1;
      Sign_table.count:=0;
      sign:=cifafenxi();
      if sign then self.Memoerror.Lines.Add('词法分析正确结束！')
      else  self.Memoerror.Lines.Add('词法分析中有错误！');

    self.Label2.Caption:='汇编代码';
    self.Memoerror.Lines.Add('汇编代码.....');
    self.ListBox1.Clear;
    stabi:=1;
    newlabel:=1;
    codenum:=0;

    B.TC:=1;    B.FC:=1;
    quadnum:=1;
    NXQ:=1;
    newtemp:=MAX_SIGNTAB div 2;
    sp1.clear;
    sp2.clear;
    sp3.clear;

    sign:=digui;
    if sign then self.Memoerror.Lines.Add('正确结束！')
    else  self.Memoerror.Lines.Add('汇编代码中有错误！');
    while(Sign_table.signtable[stabi].name<>'#')and(sign) do
    begin
      sign:=digui;
      if sign then self.Memoerror.Lines.Add('正确结束！')
      else  self.Memoerror.Lines.Add('汇编代码中有错误！');
    end;
    displaycode;
    flag:=false;
  end;
end;

end.



