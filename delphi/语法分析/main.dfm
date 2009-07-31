object Form1: TForm1
  Left = 280
  Top = 309
  Width = 553
  Height = 399
  Caption = #35821#27861#20998#26512
  Color = clBtnFace
  Font.Charset = DEFAULT_CHARSET
  Font.Color = clWindowText
  Font.Height = -11
  Font.Name = 'MS Sans Serif'
  Font.Style = []
  OldCreateOrder = False
  OnCreate = FormCreate
  PixelsPerInch = 96
  TextHeight = 13
  object GroupBox1: TGroupBox
    Left = 272
    Top = 8
    Width = 265
    Height = 345
    Caption = #23646#24615#23383#24207#21015
    TabOrder = 1
    object Button3: TButton
      Left = 184
      Top = 312
      Width = 75
      Height = 25
      Caption = #20445#23384
      TabOrder = 0
      OnClick = Button3Click
    end
  end
  object ListBox1: TListBox
    Left = 280
    Top = 24
    Width = 249
    Height = 289
    Font.Charset = GB2312_CHARSET
    Font.Color = clMaroon
    Font.Height = -13
    Font.Name = #40657#20307
    Font.Style = []
    ItemHeight = 13
    ParentFont = False
    TabOrder = 0
  end
  object GroupBox2: TGroupBox
    Left = 8
    Top = 8
    Width = 257
    Height = 345
    Caption = #28304#31243#24207
    TabOrder = 4
    object Label1: TLabel
      Left = 8
      Top = 16
      Width = 151
      Height = 15
      Caption = #36755#20837'C'#31243#24207#25110#36873#19968'C'#25991#20214
      Font.Charset = ANSI_CHARSET
      Font.Color = clWindowText
      Font.Height = -15
      Font.Name = #23435#20307
      Font.Style = []
      ParentFont = False
    end
    object Button2: TButton
      Left = 208
      Top = 8
      Width = 41
      Height = 25
      Caption = #25171#24320
      TabOrder = 0
      OnClick = Button2Click
    end
    object Button4: TButton
      Left = 176
      Top = 312
      Width = 75
      Height = 25
      Caption = #35821#27861#20998#26512
      TabOrder = 1
      OnClick = Button4Click
    end
  end
  object Memoinput: TMemo
    Left = 16
    Top = 48
    Width = 241
    Height = 265
    Font.Charset = GB2312_CHARSET
    Font.Color = clGreen
    Font.Height = -15
    Font.Name = #40657#20307
    Font.Style = []
    Lines.Strings = (
      'Memoinput')
    ParentFont = False
    ScrollBars = ssBoth
    TabOrder = 2
  end
  object Button1: TButton
    Left = 88
    Top = 320
    Width = 73
    Height = 25
    Caption = #35789#27861#20998#26512
    TabOrder = 3
    OnClick = Button1Click
  end
  object OpenDialog1: TOpenDialog
    Left = 176
    Top = 16
  end
  object SaveDialog1: TSaveDialog
    Left = 416
    Top = 320
  end
end
