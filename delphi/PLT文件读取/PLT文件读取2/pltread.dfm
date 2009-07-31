object Form1: TForm1
  Left = 220
  Top = 233
  Width = 696
  Height = 480
  Caption = #22270#24418#35835#21462
  Color = clBtnFace
  Font.Charset = DEFAULT_CHARSET
  Font.Color = clWindowText
  Font.Height = -11
  Font.Name = 'MS Sans Serif'
  Font.Style = []
  Menu = MainMenu1
  OldCreateOrder = False
  PopupMenu = PopupMenu1
  OnCreate = FormCreate
  PixelsPerInch = 96
  TextHeight = 13
  object MainMenu1: TMainMenu
    Left = 248
    object F1: TMenuItem
      Caption = #25991#20214'[&F]'
      object O1: TMenuItem
        Caption = #25171#24320'[&O]'
        ShortCut = 16463
        OnClick = O1Click
      end
      object N1: TMenuItem
        Caption = '-'
      end
      object X1: TMenuItem
        Caption = #36864#20986'[&X]'
        ShortCut = 16472
        OnClick = X1Click
      end
    end
    object V1: TMenuItem
      Caption = #35270#22270'[&V]'
      object R1: TMenuItem
        Caption = #21047#26032'[&R]'
        ShortCut = 16466
        OnClick = R1Click
      end
      object N3: TMenuItem
        Caption = '-'
      end
      object C1: TMenuItem
        Caption = #39068#33394'[&C]'
        ShortCut = 16451
        OnClick = C1Click
      end
      object L1: TMenuItem
        Caption = #32447#24418'[&L]'
        ShortCut = 16460
        object Npzx: TMenuItem
          Caption = #30772#25240#32447
          OnClick = L1Click
        end
        object Ndx: TMenuItem
          Tag = 1
          Caption = #28857#32447
          OnClick = L1Click
        end
        object Ndhx: TMenuItem
          Tag = 2
          Caption = #28857#21010#32447
          OnClick = L1Click
        end
        object Nnssx: TMenuItem
          Tag = 3
          Caption = #20869#25910#23454#32447
          OnClick = L1Click
        end
        object Nsdhx: TMenuItem
          Tag = 4
          Caption = #21452#28857#21010#32447
          OnClick = L1Click
        end
        object Nsxx: TMenuItem
          Tag = 5
          Caption = #23454#24515#32447
          Checked = True
          OnClick = L1Click
        end
        object Nhbk: TMenuItem
          Tag = 6
          Caption = #30011#31508#31354
          OnClick = L1Click
        end
      end
      object W1: TMenuItem
        Caption = #32447#23485'[&W]'
        ShortCut = 16471
        OnClick = W1Click
      end
    end
    object H1: TMenuItem
      Caption = #24110#21161'[&H]'
      object S1: TMenuItem
        Caption = #24110#21161#20027#39064'[&S]'
        ShortCut = 16467
        OnClick = S1Click
      end
      object N2: TMenuItem
        Caption = '-'
      end
      object A1: TMenuItem
        Caption = #20851#20110'[&A]'
        ShortCut = 16449
        OnClick = A1Click
      end
    end
  end
  object PopupMenu1: TPopupMenu
    Left = 280
    object R2: TMenuItem
      Caption = #21047#26032'[&R]'
      OnClick = R1Click
    end
    object N4: TMenuItem
      Caption = '-'
    end
    object C2: TMenuItem
      Caption = #36873#25321#39068#33394'[&C]'
      OnClick = C1Click
    end
    object L2: TMenuItem
      Caption = #36873#25321#32447#24418'[&L]'
      OnClick = L1Click
    end
    object W2: TMenuItem
      Caption = #36873#25321#32447#23485'[&W]'
      OnClick = W1Click
    end
  end
  object OpenDialog1: TOpenDialog
    Filter = 'PLT'#25991#20214'(*.plt)|*.plt'
    InitialDir = '.'
    Left = 320
  end
  object ColorDialog1: TColorDialog
    Left = 360
  end
end
