library Project1;

{ Important note about DLL memory management: ShareMem must be the
  first unit in your library's USES clause AND your project's (select
  Project-View Source) USES clause if your DLL exports any procedures or
  functions that pass strings as parameters or function results. This
  applies to all strings passed to and from your DLL--even those that
  are nested in records and classes. ShareMem is the interface unit to
  the BORLNDMM.DLL shared memory manager, which must be deployed along
  with your DLL. To avoid using BORLNDMM.DLL, pass string information
  using PChar or ShortString parameters. }

uses
  SysUtils,
  Classes;

{$R *.res}
//加法的实现
function PlusOpe(x,y:integer):integer;stdcall;
begin
  result:=x+y;
end;

//减法的实现
function MinusOpe(x,y:integer):integer;stdcall;
begin
  result:=x-y;
end;

//乘法的实现
function MultiplyOpe(x,y:integer):integer;stdcall;
begin
  result:=x*y;
end;

//除法的实现
function DivisionOpe(x,y:integer):double;stdcall;
begin
  result:=x/y;
end;
//将上述4个函数在exports中罗列出来
exports
  PlusOpe,MinusOpe,MultiplyOpe,DivisionOpe;

begin
end.
