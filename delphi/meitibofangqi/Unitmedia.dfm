object Formdisplay: TFormdisplay
  Left = 207
  Top = 209
  BorderStyle = bsSingle
  Caption = #23186#20307#25773#25918#22120
  ClientHeight = 428
  ClientWidth = 659
  Color = clBtnFace
  Font.Charset = DEFAULT_CHARSET
  Font.Color = clWindowText
  Font.Height = -11
  Font.Name = 'MS Sans Serif'
  Font.Style = []
  OldCreateOrder = False
  OnClose = FormClose
  OnCreate = FormCreate
  PixelsPerInch = 96
  TextHeight = 13
  object Splitter1: TSplitter
    Left = 359
    Top = 0
    Height = 428
    Align = alRight
  end
  object MediaPlayer1: TMediaPlayer
    Left = 2
    Top = 395
    Width = 197
    Height = 30
    EnabledButtons = [btPlay, btPause, btStop, btNext, btPrev, btStep, btBack]
    VisibleButtons = [btPlay, btPause, btStop, btNext, btPrev, btStep, btBack]
    AutoOpen = True
    Display = Panel1
    FileName = 'C:\Program Files\Borland\Delphi7\Demos\CoolStuf\speedis.avi'
    TabOrder = 0
    OnClick = MediaPlayer1Click
  end
  object Panel1: TPanel
    Left = 0
    Top = 0
    Width = 361
    Height = 393
    AutoSize = True
    BevelInner = bvLowered
    BevelWidth = 5
    Caption = #20808#21452#20987#23186#20307#25991#20214#65292#21518#28857#20987'paly,'#24320#22987#25773#25918
    TabOrder = 1
  end
  object ListBox1: TListBox
    Left = 362
    Top = 0
    Width = 297
    Height = 428
    Align = alRight
    ItemHeight = 13
    Items.Strings = (
      'C:\Program Files\Borland\Delphi7\Demos\CoolStuf\speedis.avi')
    PopupMenu = PopupMenu1
    TabOrder = 2
    OnDblClick = ListBox1DblClick
  end
  object PopupMenu1: TPopupMenu
    Left = 488
    Top = 240
    object N1: TMenuItem
      Caption = #28155#21152#25991#20214
      ShortCut = 16449
      OnClick = N1Click
    end
    object N2: TMenuItem
      Caption = '-'
    end
    object N3: TMenuItem
      Caption = #21024#38500#25152#36873#30340#25991#20214
      ShortCut = 16452
      OnClick = N3Click
    end
  end
end
