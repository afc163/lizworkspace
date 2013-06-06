object Form5: TForm5
  Left = 210
  Top = 167
  BorderIcons = [biSystemMenu, biHelp]
  BorderStyle = bsDialog
  Caption = 'Form5'
  ClientHeight = 111
  ClientWidth = 238
  Color = clBtnFace
  Font.Charset = DEFAULT_CHARSET
  Font.Color = clWindowText
  Font.Height = -11
  Font.Name = 'MS Sans Serif'
  Font.Style = []
  OldCreateOrder = False
  OnShow = FormShow
  PixelsPerInch = 96
  TextHeight = 13
  object Label1: TLabel
    Left = 16
    Top = 16
    Width = 65
    Height = 17
    AutoSize = False
    Caption = #34892#25968'(L):'
    Font.Charset = ANSI_CHARSET
    Font.Color = clWindowText
    Font.Height = -14
    Font.Name = #23435#20307
    Font.Style = []
    ParentFont = False
  end
  object Buttoncancel: TButton
    Left = 136
    Top = 64
    Width = 75
    Height = 25
    Caption = #21462#28040
    TabOrder = 0
    OnClick = ButtoncancelClick
  end
  object Edit1: TEdit
    Left = 88
    Top = 16
    Width = 121
    Height = 21
    Cursor = crIBeam
    Ctl3D = True
    ParentCtl3D = False
    TabOrder = 1
  end
  object Buttonok: TButton
    Left = 16
    Top = 64
    Width = 75
    Height = 25
    Caption = #30830#23450
    TabOrder = 2
    OnClick = ButtonokClick
  end
end
