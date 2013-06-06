object Form1: TForm1
  Left = 205
  Top = 117
  Width = 696
  Height = 480
  Caption = 'Form1'
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
  object Label1: TLabel
    Left = 120
    Top = 56
    Width = 137
    Height = 25
    AutoSize = False
    Caption = #31995#32479#25176#30424#31243#24207
    Font.Charset = GB2312_CHARSET
    Font.Color = clPurple
    Font.Height = -21
    Font.Name = #24188#22278
    Font.Style = []
    ParentFont = False
  end
  object Timer1: TTimer
    Interval = 2000
    OnTimer = Timer1Timer
    Left = 328
    Top = 56
  end
  object PopupMenu1: TPopupMenu
    Left = 360
    Top = 56
    object N1back: TMenuItem
      Caption = #36820#22238'(&W)'
      OnClick = N1backClick
    end
    object N7: TMenuItem
      Caption = '-'
    end
    object N2min: TMenuItem
      Caption = #26368#23567#21270'(&X)'
      OnClick = N2minClick
    end
    object N6: TMenuItem
      Caption = '-'
    end
    object N3max: TMenuItem
      Caption = #26368#22823#21270'(&Y)'
      OnClick = N3maxClick
    end
    object N5: TMenuItem
      Caption = '-'
    end
    object N4exit: TMenuItem
      Caption = #36864#20986'(&Z)'
      OnClick = N4exitClick
    end
  end
end
