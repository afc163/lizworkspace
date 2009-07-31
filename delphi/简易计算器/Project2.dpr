library Project2;

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
  Classes,
  Windows;

{$R *.res}
//显示信息对话框
procedure ShowMsg;stdcall;
begin
  MessageBox(0,'有数据输入不正确，请检查！','警告',MB_OK+MB_ICONINFORMATION);
end;
exports
  ShowMsg;
begin
end.
 