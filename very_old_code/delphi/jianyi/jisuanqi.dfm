object Form1: TForm1
  Left = 206
  Top = 169
  BorderStyle = bsSingle
  Caption = #31616#26131#35745#31639#22120
  ClientHeight = 324
  ClientWidth = 382
  Color = clBtnFace
  Font.Charset = DEFAULT_CHARSET
  Font.Color = clWindowText
  Font.Height = -11
  Font.Name = 'MS Sans Serif'
  Font.Style = []
  OldCreateOrder = False
  PixelsPerInch = 96
  TextHeight = 13
  object Bevel1: TBevel
    Left = 8
    Top = 8
    Width = 369
    Height = 241
  end
  object Label1: TLabel
    Left = 40
    Top = 32
    Width = 113
    Height = 25
    AutoSize = False
    Caption = #31532#19968#20010#25968#25454#65306
    Font.Charset = GB2312_CHARSET
    Font.Color = clWindowText
    Font.Height = -19
    Font.Name = #40657#20307
    Font.Style = []
    ParentFont = False
  end
  object Label2: TLabel
    Left = 40
    Top = 80
    Width = 113
    Height = 25
    AutoSize = False
    Caption = #31532#20108#20010#25968#25454#65306
    Font.Charset = GB2312_CHARSET
    Font.Color = clWindowText
    Font.Height = -19
    Font.Name = #40657#20307
    Font.Style = []
    ParentFont = False
  end
  object Label3: TLabel
    Left = 40
    Top = 128
    Width = 113
    Height = 25
    AutoSize = False
    Caption = #35745#31639#26426#32467#26524#65306
    Font.Charset = GB2312_CHARSET
    Font.Color = clWindowText
    Font.Height = -19
    Font.Name = #40657#20307
    Font.Style = []
    ParentFont = False
  end
  object Edit1: TEdit
    Left = 160
    Top = 27
    Width = 201
    Height = 27
    Font.Charset = GB2312_CHARSET
    Font.Color = clWindowText
    Font.Height = -19
    Font.Name = #40657#20307
    Font.Style = []
    ParentFont = False
    TabOrder = 0
  end
  object Edit2: TEdit
    Left = 160
    Top = 75
    Width = 201
    Height = 27
    Font.Charset = GB2312_CHARSET
    Font.Color = clWindowText
    Font.Height = -19
    Font.Name = #40657#20307
    Font.Style = []
    ParentFont = False
    TabOrder = 1
  end
  object Edit3: TEdit
    Left = 160
    Top = 123
    Width = 201
    Height = 27
    Font.Charset = GB2312_CHARSET
    Font.Color = clWindowText
    Font.Height = -19
    Font.Name = #40657#20307
    Font.Style = []
    ParentFont = False
    TabOrder = 2
  end
  object Button1: TButton
    Left = 48
    Top = 280
    Width = 75
    Height = 25
    Caption = #35745#31639#32467#26524
    TabOrder = 3
    OnClick = Button1Click
  end
  object Button2: TButton
    Left = 248
    Top = 280
    Width = 75
    Height = 25
    Caption = #36864#20986
    TabOrder = 4
    OnClick = Button2Click
  end
  object RadioGroup1: TRadioGroup
    Left = 12
    Top = 171
    Width = 361
    Height = 73
    Columns = 4
    Font.Charset = GB2312_CHARSET
    Font.Color = clWindowText
    Font.Height = -19
    Font.Name = #40657#20307
    Font.Style = []
    Items.Strings = (
      '+ '
      '-'
      '*'
      '\')
    ParentFont = False
    TabOrder = 5
  end
end
