object Form1: TForm1
  Left = 185
  Top = 114
  AutoScroll = False
  Caption = #26080#26631#39064#8212#8212#35760#20107#26412#31243#24207
  ClientHeight = 498
  ClientWidth = 697
  Color = clBtnFace
  TransparentColorValue = clActiveCaption
  Font.Charset = DEFAULT_CHARSET
  Font.Color = clWindowText
  Font.Height = -11
  Font.Name = 'MS Sans Serif'
  Font.Style = []
  Menu = MainMenu1
  OldCreateOrder = False
  OnCreate = FormCreate
  OnResize = FormResize
  PixelsPerInch = 96
  TextHeight = 13
  object RichEdit1: TRichEdit
    Left = 0
    Top = -1
    Width = 697
    Height = 482
    Font.Charset = GB2312_CHARSET
    Font.Color = clNavy
    Font.Height = -19
    Font.Name = 'Ashby Medium'
    Font.Style = [fsBold]
    HideScrollBars = False
    Lines.Strings = (
      '')
    ParentFont = False
    PlainText = True
    PopupMenu = PopupMenu1
    ScrollBars = ssBoth
    TabOrder = 0
    OnKeyPress = RichEdit1KeyPress
    OnMouseUp = RichEdit1MouseUp
    OnSelectionChange = RichEdit1SelectionChange
  end
  object StatusBar1: TStatusBar
    Left = 0
    Top = 479
    Width = 697
    Height = 19
    Panels = <
      item
        Text = #20320#22909#65281#25105#26159#30427#33395#65292#36825#26159#23646#20110#25105#30340#35760#20107#26412
        Width = 500
      end
      item
        Text = #34892#25968' 1'#65292#21015#25968' 1'
        Width = 50
      end>
  end
  object FindDialog1: TFindDialog
    OnFind = FindDialog1Find
    Left = 400
    Top = 72
  end
  object ReplaceDialog1: TReplaceDialog
    OnFind = ReplaceDialog1Find
    OnReplace = ReplaceDialog1Replace
    Left = 432
    Top = 72
  end
  object OpenDialog1: TOpenDialog
    FileName = '*.txt'
    Filter = #25991#26412#25991#26723'(*.txt))|*.txt|'#25152#26377#25991#20214'|*.*'
    InitialDir = '.'
    Left = 304
    Top = 72
  end
  object SaveDialog1: TSaveDialog
    FileName = '*.txt'
    Filter = #25991#26412#25991#26723'(*.txt)|*.txt|'#25152#26377#25991#20214'|*.*'
    Left = 336
    Top = 72
  end
  object FontDialog1: TFontDialog
    Font.Charset = DEFAULT_CHARSET
    Font.Color = clWindowText
    Font.Height = -11
    Font.Name = 'MS Sans Serif'
    Font.Style = []
    Left = 368
    Top = 72
  end
  object PrintDialog1: TPrintDialog
    Left = 464
    Top = 72
  end
  object MainMenu1: TMainMenu
    Left = 272
    Top = 72
    object N1file: TMenuItem
      Caption = #25991#20214'[&F]'
      object N2new: TMenuItem
        Caption = #26032#24314'[&N]'
        ShortCut = 16462
        OnClick = N2newClick
      end
      object N3open: TMenuItem
        Caption = #25171#24320'[&O]...'
        ShortCut = 16463
        OnClick = N3openClick
      end
      object N4save: TMenuItem
        Caption = #20445#23384'[&S]'
        ShortCut = 16467
        OnClick = N4saveClick
      end
      object N5saveas: TMenuItem
        Caption = #21478#23384#20026'[&A]...'
        OnClick = N5saveasClick
      end
      object N6: TMenuItem
        Caption = '-'
      end
      object U1: TMenuItem
        Caption = #39029#38754#35774#32622'[&U]...'
        OnClick = U1Click
      end
      object N7print: TMenuItem
        Caption = #25171#21360'[&P]...'
        ShortCut = 16464
        OnClick = N7printClick
      end
      object N8: TMenuItem
        Caption = '-'
      end
      object N9exit: TMenuItem
        Caption = #36864#20986'[&X]'
        OnClick = N9exitClick
      end
    end
    object N10edit: TMenuItem
      Caption = #32534#36753'[&E]'
      object N2undo: TMenuItem
        Caption = #25764#38144'[&U]'
        ShortCut = 16474
        OnClick = N2undoClick
      end
      object N1: TMenuItem
        Caption = '-'
      end
      object T1cut: TMenuItem
        Caption = #21098#20999'[&T]'
        ShortCut = 16472
        OnClick = T1cutClick
      end
      object C1copy: TMenuItem
        Caption = #22797#21046'[&C]'
        ShortCut = 16451
        OnClick = C1copyClick
      end
      object P1paste: TMenuItem
        Caption = #31896#36148'[&P]'
        ShortCut = 16470
        OnClick = P1pasteClick
      end
      object L1delete: TMenuItem
        Caption = #21024#38500'[&L]'
        ShortCut = 46
        OnClick = L1deleteClick
      end
      object N2: TMenuItem
        Caption = '-'
      end
      object N3find: TMenuItem
        Caption = #26597#25214'[&F]...'
        ShortCut = 16454
        OnClick = N3findClick
      end
      object N4findnext: TMenuItem
        Caption = #26597#25214#19979#19968#20010'[&N]'
        ShortCut = 114
        OnClick = N3findClick
      end
      object N5replace: TMenuItem
        Caption = #26367#25442'[&R]...'
        ShortCut = 16456
        OnClick = N5replaceClick
      end
      object N7goto: TMenuItem
        Caption = #36716#21040'[&G]...'
        ShortCut = 16455
        OnClick = N7gotoClick
      end
      object N9: TMenuItem
        Caption = '-'
      end
      object N10selectall: TMenuItem
        Caption = #20840#36873'[&A]'
        ShortCut = 16449
        OnClick = N10selectallClick
      end
      object N11time: TMenuItem
        Caption = #26102#38388'/'#26085#26399'[&D]'
        ShortCut = 116
        OnClick = N11timeClick
      end
    end
    object N11format: TMenuItem
      Caption = #26684#24335'[&O]'
      object N12changeline: TMenuItem
        Caption = #33258#21160#25442#34892'[&W]'
        OnClick = N12changelineClick
      end
      object N13font: TMenuItem
        Caption = #23383#20307'[&F]...'
        OnClick = N13fontClick
      end
    end
    object N18view: TMenuItem
      Caption = #26597#30475
      object N20state: TMenuItem
        Caption = #29366#24577#26639'[&S]'
        Checked = True
        OnClick = N20stateClick
      end
    end
    object N12help: TMenuItem
      Caption = #24110#21161'[&H]'
      object N15help: TMenuItem
        Caption = #24110#21161#20027#39064'[&H]'
        OnClick = N15helpClick
      end
      object N17: TMenuItem
        Caption = '-'
      end
      object N16about: TMenuItem
        Caption = #20851#20110#35760#20107#26412'[&A]'
        OnClick = N16aboutClick
      end
    end
  end
  object PopupMenu1: TPopupMenu
    Left = 240
    Top = 72
    object N13chx: TMenuItem
      Caption = #25764#38144'[&U]'
      OnClick = N2undoClick
    end
    object N14: TMenuItem
      Caption = '-'
    end
    object N15jq: TMenuItem
      Caption = #21098#20999'[&T]'
      OnClick = T1cutClick
    end
    object N16fzh: TMenuItem
      Caption = #22797#21046'[&C]'
      OnClick = C1copyClick
    end
    object N17nt: TMenuItem
      Caption = #31896#36148'[&P]'
      OnClick = P1pasteClick
    end
    object N18sch: TMenuItem
      Caption = #21024#38500'[&D]'
      OnClick = L1deleteClick
    end
    object N19: TMenuItem
      Caption = '-'
    end
    object N20qx: TMenuItem
      Caption = #20840#36873'[&A]'
      OnClick = N10selectallClick
    end
    object N4: TMenuItem
      Caption = '-'
    end
    object N3lr: TMenuItem
      Caption = #20174#24038#21040#21491#30340#38405#35835#39034#24207
      OnClick = N3lrClick
    end
    object Unicode1: TMenuItem
      Caption = #26174#31034'Unicode'#25511#21046#23383#31526
    end
    object Unicode2: TMenuItem
      Caption = #25554#20837'Unicode'#25511#21046#23383#31526
      object china1: TMenuItem
        Caption = 'china'
      end
      object english1: TMenuItem
        Caption = 'english'
      end
    end
  end
  object PageSetupDialog1: TPageSetupDialog
    MinMarginLeft = 0
    MinMarginTop = 0
    MinMarginRight = 0
    MinMarginBottom = 0
    MarginLeft = 2500
    MarginTop = 2500
    MarginRight = 2500
    MarginBottom = 2500
    PageWidth = 21000
    PageHeight = 29700
    Left = 504
    Top = 72
  end
end
