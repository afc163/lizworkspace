unit statestack;

interface
const
  arrMax=20;
  ERRORnum=32767;
type
  Tstacknum=class(Tobject)
  private
    stack:array[1..arrMAX]of integer;
    top:0..arrMax;
    numofstack:integer;
  public
    constructor initstack();
    procedure clear;
    function push(elem:integer):integer;
    function pop():integer;
    function isempty():boolean;
    function gettop():integer;
  end;

implementation

    constructor Tstacknum.initstack();
    begin
      numofstack:=0;
      top:=0;
    end;

   procedure Tstacknum.clear;
    begin
      numofstack:=0;
      top:=0;
    end;

    function Tstacknum.push(elem:integer):integer;
    begin
      if(numofstack=arrMax)then result:=ERRORnum
      else
      begin
        inc(top);
        stack[top]:=elem;
        inc(numofstack);
        result:=elem;
      end;
    end;

    function Tstacknum.pop():integer;
    begin
      if(numofstack=0)then result:=ERRORnum
      else
      begin
        result:=stack[top];
        dec(top);
        dec(numofstack);
      end;
    end;

    function Tstacknum.isempty():boolean;
    begin
      result:=true;
      if numofstack<>0 then result:=false;
    end;

    function Tstacknum.gettop():integer;
    begin
      if(numofstack=0)then result:=ERRORnum
      else result:=stack[top];
    end;

end.

