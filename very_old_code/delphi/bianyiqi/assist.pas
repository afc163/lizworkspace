unit assist;

interface
uses
  Windows, Messages, SysUtils, Variants, Classes, Graphics, Controls, Forms,
  Dialogs, StdCtrls, Buttons, Menus;
CONST
  MAX_QUAD=50;
  MAX_CODE=100;
  MAX_SIGNTAB=200;
  MAX_KEYWORD=70;
  MAX_NUMBLE=32767;
  ERROR=-1;
  OTHER=0;
  TEMPVAR=1;
  PTVAR=2;
  CONS=3;
 // ErrorTable:array[1..2]of string=('不存在keyword文件!',
 //                                  '出现未知符号!');      算术表达式
  LRtable:array[0..18,0..10]of integer=((2,-1,-1,-1,-1,-1,-1,-1,-2, 1,-1),
				  (-1,-1,-1,-1,-1,-1,-1,-1,-2,-1,-1),
				  (-1, 3,-1,-1,-1,-1,-1,-1,-1,-1,-1),
          (6,-1,-1, 7,-1,-1, 5,-1,-1,-1, 4),
				  (-1,-1, 8, 9,10,11,-1,-1,101,-1,-1),
          (6,-1,-1, 7,-1,-1, 5,-1,-1,-1,12),
				  (-1,-1,107,107,107,107,-1,107,107,-1,-1),
          (6,-1,-1, 7,-1,-1, 5,-1,-1,-1,13),
          (6,-1,-1, 7,-1,-1, 5,-1,-1,-1,14),
          (6,-1,-1, 7,-1,-1, 5,-1,-1,-1,15),
          (6,-1,-1, 7,-1,-1, 5,-1,-1,-1,16),
          (6,-1,-1, 7,-1,-1, 5,-1,-1,-1,17),
				  (-1,-1, 8, 9, 10, 11,-1,18,-1,-1,-1),
				  (-1,-1,108,108,108,108,-1,108,108,-1,-1),
				  (-1,-1,102,102,10,11,-1,102,102,-1,-1),
				  (-1,-1,103,103,10,11,-1,103,103,-1,-1),
				  (-1,-1,104,104,104,104,-1,104,104,-1,-1),
				  (-1,-1,105,105,105,105,105,-1,105,105,-1),
				  (-1,-1,106,106,106,106,106,-1,106,106,-1)
					 );
  Character:array[0..10]of string=('i','=','+','-','*','/','(',')','#','A','E');
  LRtable1:array[0..33,0..19] of integer=((8,-1,10,-1,-1,11,6,-1,-1,7,-1,-1,18,3,2,9,4,12,5,1),   //程序语句  0
                                          (8,-1,10,-1,-1,11,6,-1,-1,7,-1,-2,24,3,2,9,4,12,5,-1),      //1
                                          (8,-1,10,-1,-1,11,6,-1,-1,7,-1,-1,13,3,2,9,4,12,5,-1),              //2
                                          (8,-1,10,-1,-1,11,6,-1,-1,7,-1,-1,14,3,2,9,4,12,5,-1),              // 3
                                          (8,-1,10,-1,-1,11,6,-1,-1,7,-1,-1,15,3,2,9,4,12,5,-1),              // 4
                                          (-1,-1,-1,16,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1),      // 5
                                          (8,-1,10,-1,-1,11,6,1,-1,7,-1,-1,18,3,2,9,4,12,5,17),               // 6
                                          (-1,-1,-1,-1,-1,-1,-1,29,29,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1), // 7
                                          (-1,-1,-1,19,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1),      // 8
                                          (-1,-1,-1,20,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1),      // 9
                                          (-1,-1,-1,105,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1),     // 10
                                          (108,-1,108,-1,-1,108,108,-1,-1,108,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1), // 11
                                          (8,-1,10,-1,-1,11,6,-1,-1,7,-1,-1,21,3,2,9,4,12,5,-1),              // 12
                                          (103,103,103,-1,-1,103,103,103,-1,103,-1,103,-1,-1,-1,-1,-1,-1,-1,-1), // 13
                                          (104,32,104,-1,-1,104,104,104,104,104,-1,104,-1,-1,-1,-1,-1,-1,-1,-1),  // 14
                                          (107,107,107,-1,-1,107,107,107,-1,107,-1,107,-1,-1,-1,-1,-1,-1,-1,-1), // 15
                                          (-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,22,-1,-1,-1,-1,-1,-1,-1,-1,-1),      // 16
                                          (8,-1,10,-1,-1,11,6,23,-1,7,-1,-1,24,3,2,9,4,12,5,-1),      // 17
                                          (114,-1,114,-1,-1,114,114,114,114,114,-1,114,24,-1,-1,-1,-1,-1,-1,24),     // 18
                                          (-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,25,-1,-1,-1,-1,-1,-1,-1,-1,-1),      // 19
                                          (-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,26,-1,-1,-1,-1,-1,-1,-1,-1,-1),      // 20
                                          (-1,-1,27,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1),      // 21
                                          (-1,-1,-1,-1,28,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1),      // 22
                                          (111,111,111,-1,-1,111,111,111,-1,111,-1,111,-1,-1,-1,-1,-1,-1,-1,-1), // 23
                                          (113,-1,113,113,-1,113,113,113,113,113,-1,113,-1,-1,-1,-1,-1,-1,-1,-1),       // 24
                                          (-1,-1,-1,-1,30,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1),      // 25
                                          (-1,-1,-1,-1,31,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1),      // 26
                                          (-1,-1,-1,109,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1),     // 27
                                          (-1,-1,-1,-1,-1,-1,-1,-1,33,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1), // 28
                                          (112,112,112,-1,-1,112,112,112,-1,112,-1,112,-1,-1,-1,-1,-1,-1,-1,-1),     // 29
                                          (101,-1,101,-1,-1,101,101,-1,-1,101,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1), // 30
                                          (106,-1,106,-1,-1,106,106,-1,-1,106,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1), // 31
                                          (102,-1,102,-1,-1,102,102,-1,-1,102,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1),  // 32
                                          (110,110,110,-1,-1,110,110,110,-1,110,-1,110,-1,-1,-1,-1,-1,-1,-1,-1)     // 32 3,2,9,4,12,5
                                          );
  Character1:array[0..19]of string=('if','else','while','(',')','do','{','}',';','A','B','#','S','C','T','W','Wd','R','U','L');

  LRtablebool:array[0..15,0..10] of integer=((2,-1,3,-1,4,-1,-1,-1,1, 5,6),      //BOOL表达式
				                                     (-1,7,-1,-1,-1,8,9,-2,-1,-1,-1),
                                             (-1,101,-1,101,-1,101,101,101,-1,-1,-1),
                                             (2,-1,3,-1,4,-1,-1,-1,10,5,6),
                                             (2,-1,3,-1,4,-1,-1,-1,11,5,6),
                                             (2,-1,3,-1,4,-1,-1,-1,12,5,6),
                                             (2,-1,3,-1,4,-1,-1,-1,13,5,6),
                                             (2,-1,3,-1,4,-1,-1,-1,14,5,6),
                                             (105,-1,105,-1,105,-1,-1,-1,-1,-1,-1),
                                             (107,-1,107,-1,107,-1,-1,-1,-1,-1,-1),
                                             (-1,7,-1,15,-1,8,9,15,-1,-1,-1),
                                             (-1,104,-1,104,-1,104,104,104,-1,-1,-1),
                                             (-1,106,-1,106,-1,106,106,106,-1,-1,-1),
                                             (-1,108,-1,108,-1,108,108,108,-1,-1,-1),
                                             (-1,102,-1,102,-1,102,102,102,-1,-1,-1),
                                             (-1,103,-1,103,-1,103,103,103,-1,-1,-1)
                                             );
  CharacterB:array[0..10]of string=('i','rop','(',')','!','&&','||','#','B','A','O');

TYPE
   TOne_sign=record
         name:string;
         wordnum:integer;
         value:integer;
         properties:integer;
         end;

   TSigntable=class(Tobject)
   public
     signtable:array[1..MAX_SIGNTAB]of TOne_sign;
     count:0..MAX_SIGNTAB;
   public
     constructor  Create();
     function search(str:string):integer;
   end;

  TH=record
       op:string;
       op1,op2:string;
       end;
  TQuade=record
	       op:string;
	       arg1,arg2,result:integer;
         end;

  TTCFC=record
          TC,FC:integer;
        end;
        
   Tword=record
         keyword:string[10];
         keynum:integer;
         end;
   TDic=class(Tobject)
   private
     word:array[1..MAX_KEYWORD]of Tword;
     count:integer;
   public
     constructor  Create();
     function getoneword(num:integer):Tword;
     function getcount():integer;
     function getstring(wordnum:integer):string;
     function search(str:string):integer;
   end;

implementation

constructor  TDic.Create();
var
  fkey:textfile;
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
       readln(fkey);
      end;
     closefile(fkey);
   end;
end;

function TDic.getoneword(num:integer):Tword;
begin
   result:=word[num];
end;

function TDic.getcount():integer;
begin
  result:=count;
end;

function TDic.getstring(wordnum:integer):string;
var
  i:integer;
  sign:boolean;
begin
      i:=0;
      sign:=false;
      while (not sign) and (i<=count-1) do
      begin
        inc(i);
        if wordnum=word[i].keynum then sign:=true;
      end;
      if sign then result:=word[i].keyword
      else result:='';
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
      while (not sign) and (i<=count-1) do
      begin
        inc(i);
        if str=word[i].keyword then sign:=true;
      end;
      if sign then result:=word[i].keynum
      else result:=1;
    end;
end;

constructor  TSigntable.Create();
begin
   count:=0;
end;

function TSigntable.search(str:string):integer;
var
  i:integer;
  sign:boolean;
begin
      i:=1;
      sign:=false;
      while (not sign) and (i<=count) do
      begin
        if str=signtable[i].name then sign:=true;
        inc(i);
      end;
      if sign then result:=i
      else result:=-1;
end;


end.
