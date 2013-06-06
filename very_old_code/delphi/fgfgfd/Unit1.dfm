object Form1: TForm1
  Left = 277
  Top = 162
  Width = 473
  Height = 361
  Caption = #28176#21464#31383#20307
  Color = clBtnFace
  Font.Charset = DEFAULT_CHARSET
  Font.Color = clWindowText
  Font.Height = -11
  Font.Name = 'MS Sans Serif'
  Font.Style = []
  Menu = MainMenu1
  OldCreateOrder = False
  PopupMenu = PopupMenu1
  OnClose = FormClose
  OnCreate = FormCreate
  OnPaint = FormPaint
  OnResize = FormResize
  PixelsPerInch = 96
  TextHeight = 13
  object Label1: TLabel
    Left = 4
    Top = 8
    Width = 70
    Height = 14
    Caption = #35831#36755#20837#25991#27861
    Font.Charset = ANSI_CHARSET
    Font.Color = clYellow
    Font.Height = -14
    Font.Name = #23435#20307
    Font.Style = []
    ParentFont = False
    Transparent = True
  end
  object Label2: TLabel
    Left = 211
    Top = 4
    Width = 66
    Height = 16
    Caption = 'Firstvt/first'
    Font.Charset = ANSI_CHARSET
    Font.Color = clLime
    Font.Height = -14
    Font.Name = 'Arial'
    Font.Style = []
    ParentFont = False
    Transparent = True
  end
  object Label3: TLabel
    Left = 211
    Top = 152
    Width = 82
    Height = 16
    Caption = 'Lastvt/Follow'
    Font.Charset = ANSI_CHARSET
    Font.Color = clLime
    Font.Height = -14
    Font.Name = 'Arial'
    Font.Style = []
    ParentFont = False
    Transparent = True
  end
  object Label4: TLabel
    Left = 8
    Top = 61
    Width = 14
    Height = 14
    Caption = 'VT'
    Font.Charset = GB2312_CHARSET
    Font.Color = clYellow
    Font.Height = -14
    Font.Name = #20223#23435'_GB2312'
    Font.Style = []
    ParentFont = False
    Transparent = True
  end
  object Label5: TLabel
    Left = 8
    Top = 91
    Width = 25
    Height = 17
    Caption = 'VN'
    Font.Charset = GB2312_CHARSET
    Font.Color = clYellow
    Font.Height = -14
    Font.Name = #20223#23435'_GB2312'
    Font.Style = []
    ParentFont = False
    Transparent = True
  end
  object Label6: TLabel
    Left = 0
    Top = 128
    Width = 36
    Height = 12
    Caption = #20135#29983#24335
    Font.Charset = ANSI_CHARSET
    Font.Color = clYellow
    Font.Height = -12
    Font.Name = #23435#20307
    Font.Style = []
    ParentFont = False
    Transparent = True
  end
  object Label7: TLabel
    Left = 8
    Top = 31
    Width = 24
    Height = 12
    Caption = #24320#22987
    Font.Charset = GB2312_CHARSET
    Font.Color = clYellow
    Font.Height = -12
    Font.Name = #20223#23435'_GB2312'
    Font.Style = []
    ParentFont = False
    Transparent = True
  end
  object resultbtn: TButton
    Left = 137
    Top = 272
    Width = 57
    Height = 25
    Caption = #29983#25104
    TabOrder = 0
    OnClick = resultbtnClick
  end
  object Edit1: TEdit
    Left = 37
    Top = 120
    Width = 120
    Height = 21
    TabOrder = 1
  end
  object Buttonshi: TButton
    Left = 160
    Top = 119
    Width = 33
    Height = 25
    Caption = #22686#21152
    TabOrder = 2
    OnClick = ButtonshiClick
  end
  object Memo1: TMemo
    Left = 240
    Top = 24
    Width = 217
    Height = 121
    TabOrder = 3
  end
  object Memo2: TMemo
    Left = 240
    Top = 168
    Width = 217
    Height = 129
    TabOrder = 4
  end
  object Editvn: TEdit
    Left = 37
    Top = 84
    Width = 121
    Height = 21
    TabOrder = 5
  end
  object Editstart: TEdit
    Left = 37
    Top = 24
    Width = 121
    Height = 21
    TabOrder = 6
  end
  object Buttonvn: TButton
    Left = 160
    Top = 83
    Width = 33
    Height = 25
    Caption = #22686#21152
    TabOrder = 7
    OnClick = ButtonvnClick
  end
  object Buttonvt: TButton
    Left = 160
    Top = 51
    Width = 33
    Height = 25
    Caption = #22686#21152
    TabOrder = 8
    OnClick = ButtonvtClick
  end
  object Editvt: TEdit
    Left = 37
    Top = 52
    Width = 121
    Height = 21
    TabOrder = 9
  end
  object Buttonstart: TButton
    Left = 160
    Top = 22
    Width = 33
    Height = 25
    Caption = #20462#25913
    TabOrder = 10
    OnClick = ButtonstartClick
  end
  object Memo3: TMemo
    Left = 8
    Top = 160
    Width = 185
    Height = 105
    Font.Charset = DEFAULT_CHARSET
    Font.Color = clPurple
    Font.Height = -11
    Font.Name = 'MS Sans Serif'
    Font.Style = []
    Lines.Strings = (
      #25991#27861#24320#22987#31526#21495#65306
      'VT:'
      'Vn:'
      #20135#29983#24335#65306)
    ParentFont = False
    TabOrder = 11
  end
  object MainMenu1: TMainMenu
    Left = 320
    object N1: TMenuItem
      Caption = #35774#32622
      object mnu_Horizontal: TMenuItem
        Caption = #27700#24179#26041#21521
        OnClick = mnu_HorizontalClick
      end
      object mnu_Vertical: TMenuItem
        Caption = #22402#30452#26041#21521
        OnClick = mnu_VerticalClick
      end
      object N4: TMenuItem
        Caption = '-'
      end
      object mnu_StartColor: TMenuItem
        Caption = #36215#22987#39068#33394
        OnClick = mnu_StartColorClick
      end
      object mnu_EndColor: TMenuItem
        Caption = #32456#27490#39068#33394
        OnClick = mnu_EndColorClick
      end
    end
    object N5: TMenuItem
      Caption = #31383#20307#21464#25442
      object flashmenuitem: TMenuItem
        Caption = #38378#28865#31383#20307
        OnClick = flashmenuitemClick
      end
    end
  end
  object ColorDialog1: TColorDialog
    Left = 288
  end
  object PopupMenu1: TPopupMenu
    Left = 368
    object N2: TMenuItem
      Caption = #27700#24179#26041#21521
      OnClick = mnu_HorizontalClick
    end
    object N3: TMenuItem
      Caption = #22402#30452#26041#21521
      OnClick = mnu_VerticalClick
    end
  end
  object Timer1: TTimer
    OnTimer = Timer1Timer
    Left = 248
  end
end
