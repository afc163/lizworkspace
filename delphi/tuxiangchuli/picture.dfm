object Form1: TForm1
  Left = 192
  Top = 114
  BorderStyle = bsSingle
  Caption = #22270#20687#28857#36816#31639
  ClientHeight = 401
  ClientWidth = 592
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
  object Image1: TImage
    Left = 8
    Top = 8
    Width = 577
    Height = 385
    Center = True
    PopupMenu = PopupMenu1
    Stretch = True
  end
  object MainMenu1: TMainMenu
    Left = 128
    Top = 8
    object N1: TMenuItem
      Caption = #25991#20214
      object N2: TMenuItem
        Caption = #25171#24320#22270#20687'[&O]'
        ShortCut = 49231
        OnClick = N2Click
      end
      object N3: TMenuItem
        Caption = #20445#23384#22270#20687'[&S]'
        ShortCut = 49235
        OnClick = N3Click
      end
      object N4: TMenuItem
        Caption = '-'
      end
      object O1: TMenuItem
        Caption = #36864#20986'[&O]'
        ShortCut = 49233
        OnClick = O1Click
      end
    end
  end
  object PopupMenu1: TPopupMenu
    Left = 208
    Top = 8
    object B1: TMenuItem
      Caption = #20687#32032#21270#22270#20687'[&B]'
      ShortCut = 16450
      OnClick = ActionxshExecute
    end
    object N12: TMenuItem
      Caption = '-'
    end
    object N5: TMenuItem
      Caption = #33192#32960#25928#26524'[&E]'
      ShortCut = 16453
      OnClick = ActionpzhExecute
    end
    object N6: TMenuItem
      Caption = #33104#34432#25928#26524'[&C]'
      ShortCut = 16451
      OnClick = ActionfshExecute
    end
    object N7: TMenuItem
      Caption = '-'
    end
    object N8: TMenuItem
      Caption = #25552#21462#36718#24275'[&F]'
      ShortCut = 16454
      OnClick = ActiontqlkExecute
    end
    object N9: TMenuItem
      Caption = #32454#21270#22270#20687'[&T]'
      ShortCut = 16468
      OnClick = ActionxhtxExecute
    end
    object Sobel1: TMenuItem
      Caption = 'Sobel'#36793#32536#26816#27979'[&S]'
      ShortCut = 16467
      OnClick = ActionsobelExecute
    end
    object N10: TMenuItem
      Caption = '-'
    end
    object N11: TMenuItem
      Caption = #36824#21407#22270#20687'[&R]'
      ShortCut = 16466
      OnClick = ActionhytxExecute
    end
  end
  object OpenPictureDialog1: TOpenPictureDialog
    FilterIndex = 2
    Left = 240
    Top = 8
  end
  object SavePictureDialog1: TSavePictureDialog
    Left = 176
    Top = 8
  end
  object ActionList1: TActionList
    Left = 288
    Top = 24
    object Actionxsh: TAction
      Caption = #20687#32032#21270
      OnExecute = ActionxshExecute
    end
    object Actionpzh: TAction
      Caption = #33192#32960#25928#26524
      OnExecute = ActionpzhExecute
    end
    object Actionfsh: TAction
      Caption = #33104#34432#25928#26524
      OnExecute = ActionfshExecute
    end
    object Actiontqlk: TAction
      Caption = #25552#21462#36718#24275
      OnExecute = ActiontqlkExecute
    end
    object Actionxhtx: TAction
      Caption = #32454#21270#22270#20687
      OnExecute = ActionxhtxExecute
    end
    object Actionsobel: TAction
      Caption = 'Sobel'#36793#32536#26816#27979
      OnExecute = ActionsobelExecute
    end
    object Actionhytx: TAction
      Caption = #36824#21407#22270#20687
      OnExecute = ActionhytxExecute
    end
  end
end
