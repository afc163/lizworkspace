object Form1: TForm1
  Left = 400
  Top = 142
  BorderStyle = bsSingle
  Caption = #35745#31639#22120' '
  ClientHeight = 304
  ClientWidth = 309
  Color = clBtnFace
  Font.Charset = DEFAULT_CHARSET
  Font.Color = clWindowText
  Font.Height = -11
  Font.Name = 'MS Sans Serif'
  Font.Style = []
  Menu = MainMenu1
  OldCreateOrder = False
  OnCreate = FormCreate
  PixelsPerInch = 96
  TextHeight = 13
  object Label1: TLabel
    Left = 4
    Top = 2
    Width = 313
    Height = 17
    AutoSize = False
    Caption = #35831#36755#20837#65306
  end
  object Panel1: TPanel
    Left = 8
    Top = 20
    Width = 297
    Height = 57
    Caption = 'Panel1'
    TabOrder = 4
    object Edit1: TEdit
      Left = 8
      Top = 11
      Width = 233
      Height = 35
      Enabled = False
      Font.Charset = GB2312_CHARSET
      Font.Color = clGreen
      Font.Height = -27
      Font.Name = #40657#20307
      Font.Style = []
      ParentFont = False
      TabOrder = 0
    end
    object Button10: TButton
      Left = 248
      Top = 13
      Width = 41
      Height = 33
      Caption = #28165#31354
      Font.Charset = GB2312_CHARSET
      Font.Color = clPurple
      Font.Height = -20
      Font.Name = #24188#22278
      Font.Style = [fsBold]
      ParentFont = False
      TabOrder = 1
      OnClick = Button10Click
    end
  end
  object GroupBox1: TGroupBox
    Left = 8
    Top = 80
    Width = 297
    Height = 217
    TabOrder = 0
    object Button1: TButton
      Left = 8
      Top = 20
      Width = 57
      Height = 33
      Caption = '1'
      Font.Charset = ANSI_CHARSET
      Font.Color = clPurple
      Font.Height = -37
      Font.Name = 'French Script MT'
      Font.Style = [fsBold]
      ParentFont = False
      TabOrder = 0
      OnClick = Button1Click
    end
    object Button2: TButton
      Left = 72
      Top = 20
      Width = 57
      Height = 33
      Caption = '2'
      Font.Charset = ANSI_CHARSET
      Font.Color = clPurple
      Font.Height = -37
      Font.Name = 'French Script MT'
      Font.Style = [fsBold]
      ParentFont = False
      TabOrder = 1
      OnClick = Button2Click
    end
    object Button3: TButton
      Left = 136
      Top = 20
      Width = 57
      Height = 33
      Caption = '3'
      Font.Charset = ANSI_CHARSET
      Font.Color = clPurple
      Font.Height = -37
      Font.Name = 'French Script MT'
      Font.Style = [fsBold]
      ParentFont = False
      TabOrder = 2
      OnClick = Button3Click
    end
    object Button4: TButton
      Left = 72
      Top = 60
      Width = 57
      Height = 33
      Caption = '5'
      Font.Charset = ANSI_CHARSET
      Font.Color = clPurple
      Font.Height = -37
      Font.Name = 'French Script MT'
      Font.Style = [fsBold]
      ParentFont = False
      TabOrder = 3
      OnClick = Button4Click
    end
    object Button5: TButton
      Left = 8
      Top = 60
      Width = 57
      Height = 33
      Caption = '4'
      Font.Charset = ANSI_CHARSET
      Font.Color = clPurple
      Font.Height = -37
      Font.Name = 'French Script MT'
      Font.Style = [fsBold]
      ParentFont = False
      TabOrder = 4
      OnClick = Button5Click
    end
    object Button6: TButton
      Left = 136
      Top = 60
      Width = 57
      Height = 33
      Caption = '6'
      Font.Charset = ANSI_CHARSET
      Font.Color = clPurple
      Font.Height = -37
      Font.Name = 'French Script MT'
      Font.Style = [fsBold]
      ParentFont = False
      TabOrder = 5
      OnClick = Button6Click
    end
    object Button12: TButton
      Left = 208
      Top = 84
      Width = 81
      Height = 29
      Caption = '*'
      Font.Charset = ANSI_CHARSET
      Font.Color = clPurple
      Font.Height = -37
      Font.Name = 'French Script MT'
      Font.Style = [fsBold]
      ParentFont = False
      TabOrder = 6
      OnClick = Button12Click
      OnExit = opexit
    end
    object Button13: TButton
      Left = 208
      Top = 52
      Width = 81
      Height = 29
      Caption = '-'
      Font.Charset = GB2312_CHARSET
      Font.Color = clPurple
      Font.Height = -37
      Font.Name = #40657#20307
      Font.Style = [fsBold]
      ParentFont = False
      TabOrder = 7
      OnClick = Button13Click
      OnExit = opexit
    end
    object Button14: TButton
      Left = 208
      Top = 20
      Width = 81
      Height = 29
      Caption = '+'
      Font.Charset = ANSI_CHARSET
      Font.Color = clPurple
      Font.Height = -37
      Font.Name = 'French Script MT'
      Font.Style = [fsBold]
      ParentFont = False
      TabOrder = 8
      OnClick = Button14Click
      OnExit = opexit
    end
    object Button15: TButton
      Left = 208
      Top = 116
      Width = 81
      Height = 29
      Caption = '/'
      Font.Charset = ANSI_CHARSET
      Font.Color = clPurple
      Font.Height = -37
      Font.Name = 'French Script MT'
      Font.Style = [fsBold]
      ParentFont = False
      TabOrder = 9
      OnClick = Button15Click
      OnExit = opexit
    end
    object Button16: TButton
      Left = 8
      Top = 184
      Width = 185
      Height = 25
      Caption = '='
      Font.Charset = ANSI_CHARSET
      Font.Color = clPurple
      Font.Height = -37
      Font.Name = 'French Script MT'
      Font.Style = [fsBold]
      ParentFont = False
      TabOrder = 10
      OnClick = Button16Click
      OnExit = exittostart
    end
    object Button17: TButton
      Left = 208
      Top = 182
      Width = 81
      Height = 29
      Caption = '%'
      Font.Charset = ANSI_CHARSET
      Font.Color = clPurple
      Font.Height = -37
      Font.Name = 'French Script MT'
      Font.Style = [fsBold]
      ParentFont = False
      TabOrder = 11
      OnClick = Button17Click
      OnExit = opexit
    end
    object Button18: TButton
      Left = 208
      Top = 148
      Width = 81
      Height = 29
      Caption = '^'
      Font.Charset = ANSI_CHARSET
      Font.Color = clPurple
      Font.Height = -37
      Font.Name = 'French Script MT'
      Font.Style = [fsBold]
      ParentFont = False
      TabOrder = 12
      OnClick = Button18Click
      OnExit = opexit
    end
    object Button11: TButton
      Left = 8
      Top = 140
      Width = 57
      Height = 33
      Caption = '0'
      Font.Charset = ANSI_CHARSET
      Font.Color = clPurple
      Font.Height = -37
      Font.Name = 'French Script MT'
      Font.Style = [fsBold]
      ParentFont = False
      TabOrder = 13
      OnClick = Button11Click
    end
    object Button19: TButton
      Left = 72
      Top = 140
      Width = 57
      Height = 33
      Caption = '+/-'
      Font.Charset = GB2312_CHARSET
      Font.Color = clPurple
      Font.Height = -21
      Font.Name = #40657#20307
      Font.Style = [fsBold]
      ParentFont = False
      TabOrder = 14
      OnClick = Button19Click
    end
    object Button20: TButton
      Left = 136
      Top = 140
      Width = 57
      Height = 33
      Font.Charset = GB2312_CHARSET
      Font.Color = clPurple
      Font.Height = -27
      Font.Name = #40657#20307
      Font.Style = [fsBold]
      ParentFont = False
      TabOrder = 15
    end
  end
  object Button7: TButton
    Left = 80
    Top = 179
    Width = 57
    Height = 33
    Caption = '8'
    Font.Charset = ANSI_CHARSET
    Font.Color = clPurple
    Font.Height = -37
    Font.Name = 'French Script MT'
    Font.Style = [fsBold]
    ParentFont = False
    TabOrder = 1
    OnClick = Button7Click
  end
  object Button8: TButton
    Left = 16
    Top = 179
    Width = 57
    Height = 33
    Caption = '7'
    Font.Charset = ANSI_CHARSET
    Font.Color = clPurple
    Font.Height = -37
    Font.Name = 'French Script MT'
    Font.Style = [fsBold]
    ParentFont = False
    TabOrder = 2
    OnClick = Button8Click
  end
  object Button9: TButton
    Left = 144
    Top = 179
    Width = 57
    Height = 33
    Caption = '9'
    Font.Charset = ANSI_CHARSET
    Font.Color = clPurple
    Font.Height = -37
    Font.Name = 'French Script MT'
    Font.Style = [fsBold]
    ParentFont = False
    TabOrder = 3
    OnClick = Button9Click
  end
  object MainMenu1: TMainMenu
    Left = 184
    Top = 65528
    object Medit: TMenuItem
      Caption = #32534#36753
      ShortCut = 32837
      object Mcopy: TMenuItem
        Caption = #22797#21046
        ShortCut = 16451
        OnClick = McopyClick
      end
      object Mpaste: TMenuItem
        Caption = #31896#36148
        ShortCut = 16464
        OnClick = MpasteClick
      end
      object Mclose: TMenuItem
        Caption = #36864#20986
        ShortCut = 16453
        OnClick = McloseClick
      end
    end
    object Msee: TMenuItem
      Caption = #26597#30475
      ShortCut = 32851
      object Mstandard: TMenuItem
        Caption = #26631#20934#22411
      end
      object Mscience: TMenuItem
        Caption = #31185#23398#22411
      end
    end
    object Mhelp: TMenuItem
      Caption = #24110#21161
      ShortCut = 32840
      object Mhelpc: TMenuItem
        Caption = #24110#21161#20027#39064
      end
      object Mline: TMenuItem
        Caption = '-'
      end
      object Mabout: TMenuItem
        Caption = #20851#20110#35745#31639#22120
        OnClick = MaboutClick
      end
    end
  end
end
