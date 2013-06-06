object Form1: TForm1
  Left = 230
  Top = 222
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
    Top = 16
    Width = 140
    Height = 14
    Caption = #35831#36755#20837#25991#27861'   '#22914':E->F'
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
    Width = 28
    Height = 16
    Caption = 'First'
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
    Width = 40
    Height = 16
    Caption = 'Follow'
    Font.Charset = ANSI_CHARSET
    Font.Color = clLime
    Font.Height = -14
    Font.Name = 'Arial'
    Font.Style = []
    ParentFont = False
    Transparent = True
  end
  object resultbtn: TButton
    Left = 116
    Top = 272
    Width = 57
    Height = 25
    Caption = #29983#25104
    TabOrder = 0
    OnClick = resultbtnClick
  end
  object Edit1: TEdit
    Left = 4
    Top = 40
    Width = 97
    Height = 21
    TabOrder = 1
  end
  object Button1: TButton
    Left = 116
    Top = 39
    Width = 57
    Height = 25
    Caption = #22686#21152
    TabOrder = 2
    OnClick = Button1Click
  end
  object ListBox1: TListBox
    Left = 8
    Top = 80
    Width = 169
    Height = 185
    ItemHeight = 13
    TabOrder = 3
    OnDblClick = Itemdelete
  end
  object Memo1: TMemo
    Left = 208
    Top = 24
    Width = 249
    Height = 121
    TabOrder = 4
  end
  object Memo2: TMemo
    Left = 210
    Top = 168
    Width = 247
    Height = 129
    TabOrder = 5
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
