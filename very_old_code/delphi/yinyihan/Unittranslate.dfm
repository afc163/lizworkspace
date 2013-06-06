object mainform: Tmainform
  Left = 285
  Top = 116
  AutoScroll = False
  Caption = 'mainform'
  ClientHeight = 434
  ClientWidth = 370
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
    Left = 8
    Top = 8
    Width = 89
    Height = 16
    AutoSize = False
    Caption = #35831#36755#20837#33521#25991
    Font.Charset = DEFAULT_CHARSET
    Font.Color = clWindowText
    Font.Height = -11
    Font.Name = 'MS Sans Serif'
    Font.Style = []
    ParentFont = False
  end
  object Editstr: TEdit
    Left = 8
    Top = 33
    Width = 353
    Height = 21
    TabOrder = 0
  end
  object Button1: TButton
    Left = 288
    Top = 65
    Width = 75
    Height = 25
    Caption = #32763#35793
    TabOrder = 1
    OnClick = Button1Click
  end
  object GroupBox1: TGroupBox
    Left = 8
    Top = 120
    Width = 353
    Height = 305
    Caption = #35789#24211
    TabOrder = 2
    object Button4: TButton
      Left = 264
      Top = 80
      Width = 75
      Height = 25
      Caption = #21024#38500
      TabOrder = 0
      OnClick = Button4Click
    end
    object Button5: TButton
      Left = 264
      Top = 128
      Width = 75
      Height = 25
      Caption = #26597#25214
      TabOrder = 1
      OnClick = Button5Click
    end
    object Buttonadd: TButton
      Left = 264
      Top = 32
      Width = 75
      Height = 25
      Caption = #22686#21152
      TabOrder = 2
      OnClick = ButtonaddClick
    end
    object ListBox1: TListBox
      Left = 8
      Top = 16
      Width = 241
      Height = 281
      ItemHeight = 13
      TabOrder = 3
    end
  end
  object CheckBox1: TCheckBox
    Left = 8
    Top = 72
    Width = 97
    Height = 17
    Caption = #26174#31034#35789#24211
    TabOrder = 3
    OnClick = CheckBox1Click
  end
end
