
Const ForReading = 1, ForWriting = 2, ForAppending = 3
Const TristateUseDefault = -2, TristateTrue = -1, TristateFalse = 0

set fso = createobject("scripting.filesystemobject")
set of=fso.CreateTextFile("ȫ���ű�(ABS)_oracle.sql")

dim FileName
dim FileNames

FileNames=""

FileNames=FileNames&"kayak_abs_oracle.sql"&","
'FileNames=FileNames&"DB_LINK.sql"&","
FileNames=FileNames&"���ݿ��ʼ��\Init_SQL(�ʲ�֤ȯ�����߰�)_oracle.txt"&","



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


MsgBox "ȫ���ű�������ϣ�"








