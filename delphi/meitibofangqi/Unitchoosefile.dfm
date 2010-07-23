object Formselect: TFormselect
  Left = 244
  Top = 156
  BorderStyle = bsSingle
  Caption = #36873#25321#23186#20307#25991#20214
  ClientHeight = 369
  ClientWidth = 430
  Color = clBtnFace
  Font.Charset = DEFAULT_CHARSET
  Font.Color = clWindowText
  Font.Height = -11
  Font.Name = 'MS Sans Serif'
  Font.Style = []
  OldCreateOrder = False
  OnClose = FormClose
  PixelsPerInch = 96
  TextHeight = 13
  object Label1: TLabel
    Left = 8
    Top = 8
    Width = 209
    Height = 19
    Caption = #36873#25321#35201#25773#25918#30340#23186#20307#25991#20214#65306
    Font.Charset = GB2312_CHARSET
    Font.Color = clWindowText
    Font.Height = -19
    Font.Name = #40657#20307
    Font.Style = []
    ParentFont = False
  end
  object DriveComboBox1: TDriveComboBox
    Left = 8
    Top = 288
    Width = 145
    Height = 19
    DirList = DirectoryListBox1
    TabOrder = 0
  end
  object DirectoryListBox1: TDirectoryListBox
    Left = 8
    Top = 32
    Width = 193
    Height = 249
    FileList = FileListBox1
    ItemHeight = 16
    TabOrder = 1
  end
  object FileListBox1: TFileListBox
    Left = 208
    Top = 32
    Width = 217
    Height = 249
    ItemHeight = 13
    Mask = '*.wav;*.avi;*.mid;*.dat;*.asf;*.wmv'
    TabOrder = 2
    OnDblClick = Button1Click
  end
  object FilterComboBox1: TFilterComboBox
    Left = 160
    Top = 288
    Width = 265
    Height = 21
    FileList = FileListBox1
    Filter = 
      #23186#20307#25991#20214#26684#24335'(*.wav,*.avi,*.mid,*.dat,*.asf,*.wmv)|*.wav;*.avi;*.mid;*.' +
      'dat;*.asf;*.wmv|'#25152#26377#25991#20214'(*.*)|*.*'
    TabOrder = 3
  end
  object Button1: TButton
    Left = 80
    Top = 328
    Width = 75
    Height = 25
    Caption = #30830#23450
    TabOrder = 4
    OnClick = Button1Click
  end
  object Button2: TButton
    Left = 256
    Top = 328
    Width = 75
    Height = 25
    Caption = #21462#28040
    TabOrder = 5
    OnClick = Button2Click
  end
end
