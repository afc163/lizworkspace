unit stackop;

interface
const
  arrMax=20;
  ERRORop='0';
type
  Tstackop=class(Tobject)
  private
    stack:array[1..arrMAX]of char;
    top:0..arrMax;
    numofstack:integer;
  public
    constructor initstack();
    function push(elem:char):char;
    function pop():char;
    function empty():boolean;
    function gettop():char;
  end;

implementation

    constructor Tstackop.initstack();
    begin
      numofstack:=0;
      top:=0;
    end;

    function Tstackop.push(elem:char):char;
    begin
      if(numofstack=arrMax)then result:=ERRORop
      else
      begin
        inc(top);
        stack[top]:=elem;
        inc(numofstack);
        result:=elem;
      end;
    end;

    function Tstackop.pop():char;
    begin
      if(numofstack=0)then result:=ERRORop
      else
      begin
        result:=stack[top];
        dec(top);
        dec(numofstack);
      end;
    end;

    function Tstackop.empty():boolean;
    begin
      result:=true;
      if numofstack<>0 then result:=false;
    end;

    function Tstackop.gettop():char;
    begin
      if(numofstack=0)then result:=ERRORop
      else result:=stack[top];
    end;

end.
