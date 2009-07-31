object Form1: TForm1
  Left = 205
  Top = 158
  Width = 699
  Height = 503
  Caption = #31616#26131#32534#35793#22120
  Color = clInfoBk
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
    Left = 312
    Top = 0
    Width = 377
    Height = 345
    Caption = #32467#26524
    Color = clInfoBk
    ParentColor = False
    TabOrder = 1
    object Label2: TLabel
      Left = 8
      Top = 24
      Width = 169
      Height = 13
      AutoSize = False
    end
    object Label3: TLabel
      Left = 192
      Top = 24
      Width = 169
      Height = 13
      AutoSize = False
    end
    object Button3: TButton
      Left = 296
      Top = 312
      Width = 73
      Height = 25
      Caption = #20445#23384
      Font.Charset = GB2312_CHARSET
      Font.Color = clBlack
      Font.Height = -16
      Font.Name = #26999#20307'_GB2312'
      Font.Style = []
      ParentFont = False
      TabOrder = 0
      OnClick = Button3Click
    end
    object ListBox2: TListBox
      Left = 192
      Top = 40
      Width = 177
      Height = 265
      Font.Charset = ANSI_CHARSET
      Font.Color = clPurple
      Font.Height = -19
      Font.Name = 'Times New Roman'
      Font.Style = []
      ItemHeight = 21
      ParentFont = False
      TabOrder = 1
    end
  end
  object ListBox1: TListBox
    Left = 320
    Top = 40
    Width = 177
    Height = 265
    Font.Charset = ANSI_CHARSET
    Font.Color = clGreen
    Font.Height = -19
    Font.Name = 'Times New Roman'
    Font.Style = []
    ItemHeight = 21
    ParentFont = False
    TabOrder = 0
  end
  object GroupBox2: TGroupBox
    Left = 0
    Top = 0
    Width = 305
    Height = 345
    Caption = #28304#31243#24207
    Color = clInfoBk
    ParentColor = False
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
      Left = 232
      Top = 16
      Width = 65
      Height = 19
      Caption = #25171#24320
      Font.Charset = GB2312_CHARSET
      Font.Color = clWindowText
      Font.Height = -16
      Font.Name = #26999#20307'_GB2312'
      Font.Style = []
      ParentFont = False
      TabOrder = 0
      OnClick = Button2Click
    end
    object Button4: TButton
      Left = 98
      Top = 312
      Width = 88
      Height = 25
      Caption = #35821#27861#20998#26512
      Font.Charset = GB2312_CHARSET
      Font.Color = clWindowText
      Font.Height = -16
      Font.Name = #26999#20307'_GB2312'
      Font.Style = []
      ParentFont = False
      TabOrder = 1
      OnClick = Button4Click
    end
    object BitBtn1: TBitBtn
      Left = 192
      Top = 312
      Width = 105
      Height = 25
      Caption = #26174#31034#27719#32534#20195#30721
      Font.Charset = GB2312_CHARSET
      Font.Color = clWindowText
      Font.Height = -16
      Font.Name = #26999#20307'_GB2312'
      Font.Style = []
      ParentFont = False
      TabOrder = 2
      OnClick = BitBtn1Click
    end
  end
  object Memoinput: TMemo
    Left = 8
    Top = 40
    Width = 289
    Height = 265
    Font.Charset = ANSI_CHARSET
    Font.Color = clNavy
    Font.Height = -20
    Font.Name = 'Times New Roman'
    Font.Style = []
    Lines.Strings = (
      'Memoinput')
    ParentFont = False
    ScrollBars = ssBoth
    TabOrder = 2
    OnKeyPress = changeflag
  end
  object Button1: TButton
    Left = 8
    Top = 312
    Width = 84
    Height = 25
    Caption = #35789#27861#20998#26512
    Font.Charset = GB2312_CHARSET
    Font.Color = clWindowText
    Font.Height = -16
    Font.Name = #26999#20307'_GB2312'
    Font.Style = []
    ParentFont = False
    TabOrder = 3
    OnClick = Button1Click
  end
  object GroupBox3: TGroupBox
    Left = 0
    Top = 344
    Width = 689
    Height = 121
    Caption = #38169#35823#20449#24687
    Color = clInfoBk
    ParentColor = False
    TabOrder = 5
    object Memoerror: TMemo
      Left = 5
      Top = 14
      Width = 679
      Height = 105
      Font.Charset = GB2312_CHARSET
      Font.Color = clWindowText
      Font.Height = -19
      Font.Name = #20223#23435'_GB2312'
      Font.Style = []
      Lines.Strings = (
        '')
      ParentFont = False
      ScrollBars = ssVertical
      TabOrder = 0
    end
  end
  object OpenDialog1: TOpenDialog
    Left = 200
    Top = 8
  end
  object SaveDialog1: TSaveDialog
    Left = 568
    Top = 312
  end
end
