unit placestack;
interface
const
  arrMax=40;
  ERRORvalue=32767;
type
  Tstackvalue=class(Tobject)
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

    constructor Tstackvalue.initstack();
    begin
      numofstack:=0;
      top:=0;
    end;

    procedure Tstackvalue.clear;
    begin
      numofstack:=0;
      top:=0;
    end;

    function Tstackvalue.push(elem:integer):integer;
    begin
      if(numofstack=arrMax)then result:=ERRORvalue
      else
      begin
        inc(top);
        stack[top]:=elem;
        inc(numofstack);
        result:=elem;
      end;
    end;

    function Tstackvalue.pop():integer;
    begin
      if(numofstack=0)then result:=ERRORvalue
      else
      begin
        result:=stack[top];
        dec(top);
        dec(numofstack);
      end;
    end;

    function Tstackvalue.isempty():boolean;
    begin
      result:=true;
      if numofstack<>0 then result:=false;
    end;

    function Tstackvalue.gettop():integer;
    begin
      if(numofstack=0)then result:=ERRORvalue
      else result:=stack[top];
    end;

end.

