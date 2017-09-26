
Const ForReading = 1, ForWriting = 2, ForAppending = 3
Const TristateUseDefault = -2, TristateTrue = -1, TristateFalse = 0

set fso = createobject("scripting.filesystemobject")
set of=fso.CreateTextFile("全量脚本(ABS)_oracle.sql")

dim FileName
dim FileNames

FileNames=""

FileNames=FileNames&"kayak_abs_oracle.sql"&","
'FileNames=FileNames&"DB_LINK.sql"&","
FileNames=FileNames&"数据库初始化\Init_SQL(资产证券化基线版)_oracle.txt"&","



dim FileNameList
FileNameList=split(FileNames,",")


dim i
i=0

For i = Lbound(FileNameList) To Ubound(FileNameList) 
   if FileNameList(i)<>"" then
       FileName=fso.GetAbsolutePathName(FileNameList(i))
       of.WriteLine "-------------------"&FileName&"------------------------------"
       Set file=fso.OpenTextFile(FileName,1,True) 
       Do While Not file.AtEndOfStream 
           of.WriteLine file.ReadLine
       loop
       of.WriteLine "-------------------------------------------------------------"
   end if
next

of.WriteLine "commit;"
of.WriteLine "---------------------"&FormatDateTime(Now,0)&"-------------------------"
of.Close


MsgBox "全量脚本生成完毕！"








