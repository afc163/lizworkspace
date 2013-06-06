object FormLine: TFormLine
  Left = 419
  Top = 251
  BorderStyle = bsDialog
  Caption = #36873#25321#32447#24418
  ClientHeight = 93
  ClientWidth = 244
  Color = clBtnFace
  Font.Charset = DEFAULT_CHARSET
  Font.Color = clWindowText
  Font.Height = -11
  Font.Name = 'MS Sans Serif'
  Font.Style = []
  OldCreateOrder = False
  OnShow = FormShow
  PixelsPerInch = 96
  TextHeight = 13
  object Label1: TLabel
    Left = 9
    Top = 34
    Width = 17
    Height = 17
    AutoSize = False
    Caption = '0'
    Font.Charset = GB2312_CHARSET
    Font.Color = clWindowText
    Font.Height = -16
    Font.Name = #40657#20307
    Font.Style = []
    ParentFont = False
  end
  object Label2: TLabel
    Left = 219
    Top = 35
    Width = 17
    Height = 17
    AutoSize = False
    Caption = '10'
    Font.Charset = GB2312_CHARSET
    Font.Color = clWindowText
    Font.Height = -16
    Font.Name = #40657#20307
    Font.Style = []
    ParentFont = False
  end
  object Label3: TLabel
    Left = 116
    Top = 35
    Width = 17
    Height = 17
    AutoSize = False
    Caption = '5'
    Font.Charset = GB2312_CHARSET
    Font.Color = clWindowText
    Font.Height = -16
    Font.Name = #40657#20307
    Font.Style = []
    ParentFont = False
  end
  object TrackBar1: TTrackBar
    Left = 0
    Top = 11
    Width = 241
    Height = 25
    PageSize = 1
    TabOrder = 0
    OnChange = TrackBar1Change
  end
  object Button1: TButton
    Left = 16
    Top = 56
    Width = 75
    Height = 25
    Caption = #30830#23450
    TabOrder = 1
    OnClick = Button1Click
  end
  object Button2: TButton
    Tag = 1
    Left = 160
    Top = 56
    Width = 75
    Height = 25
    Caption = #21462#28040
    TabOrder = 2
    OnClick = Button2Click
  end
end
