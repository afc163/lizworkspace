unit signstack;

interface
const
  arrMax=40;
  ERRORstr='ERROR';
type
  Tstacksign=class(Tobject)
  private
    stack:array[1..arrMAX]of string;
    top:0..arrMax;
    numofstack:integer;
  public
    constructor initstack();
    procedure clear;
    function push(elem:string):string;
    function pop():string;
    function isempty():boolean;
    function gettop():string;
  end;

implementation

    constructor Tstacksign.initstack();
    begin
      numofstack:=0;
      top:=0;
    end;

    procedure Tstacksign.clear;
    begin
      numofstack:=0;
      top:=0;
    end;

    function Tstacksign.push(elem:string):string;
    begin
      if(numofstack=arrMax)then result:=ERRORstr
      else
      begin
        inc(top);
        stack[top]:=elem;
        inc(numofstack);
        result:=elem;
      end;
    end;

    function Tstacksign.pop():string;
    begin
      if(numofstack=0)then result:=ERRORstr
      else
      begin
        result:=stack[top];
        dec(top);
        dec(numofstack);
      end;
    end;

    function Tstacksign.isempty():boolean;
    begin
      result:=true;
      if numofstack<>0 then result:=false;
    end;

    function Tstacksign.gettop():string;
    begin
      if(numofstack=0)then result:=ERRORstr
      else result:=stack[top];
    end;
end.

