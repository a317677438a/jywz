Const ForReading = 1, ForWriting = 2, ForAppending = 3
Const TristateUseDefault = -2, TristateTrue = -1, TristateFalse = 0

set oExcel=CreateObject("KET.Application")
set fso = createobject("scripting.filesystemobject")

dim FileName
dim FileNames

FileNames=""

FileNames=FileNames&"ϵͳ���ñ�\SYS_MENU.xls"&","
FileNames=FileNames&"ϵͳ���ñ�\SYS_MENU_DETAIL.xls"&","
FileNames=FileNames&"ϵͳ���ñ�\SYS_USER.xls"&","
FileNames=FileNames&"ϵͳ���ñ�\TS_RATE_INFO.xls"&","
FileNames=FileNames&"ϵͳ���ñ�\SL_SCREEN_FIELD_CONFIGURE.xls"&","
FileNames=FileNames&"ϵͳ���ñ�\SYS_MENU_MAPPING.xls"&","


on error resume Next

dim FileNameList
FileNameList=split(FileNames,",")

dim i
i=0
dim TempRow,TempColumn


'---------------------------------------------ORACLE �汾-------------------------------------------


set of=fso.CreateTextFile("Init_SQL(�ʲ�֤ȯ�����߰�)_mysql.txt")

For i =Lbound(FileNameList)  To Ubound(FileNameList) 
   if FileNameList(Ubound(FileNameList)-i)<>"" then
       FileName=fso.GetAbsolutePathName(FileNameList(Ubound(FileNameList)-i))
       set oWb=oExcel.WorkBooks.Open(FileName)
       set oSheet=oWb.Sheets("Sheet1")   '���г�ʼ������ͳһ����sheet1��
       
       TempRow=1
       TempColumn=1
       do while oSheet.cells(1,TempColumn)<>""   '���г�ʼ�����ݵ�SQL������������һ�����м䲻���п���
           TempColumn=TempColumn+1
       loop
       TempColumn=TempColumn-1
       
       'of.WriteLine oSheet.cells(1,TempColumn).Value  '���һ�д��oracle�������ڶ�����mssql
       
       oWb.Close
   end if
next

of.WriteLine "-- --------------------------------------------"

wscript.echo "ORACLE�汾"
For i = Lbound(FileNameList) To Ubound(FileNameList) 
   
   if FileNameList(i)<>"" then
       wscript.echo FileNameList(i)&"  ����"&i+1&"/"&Ubound(FileNameList)
       FileName=fso.GetAbsolutePathName(FileNameList(i))
       set oWb=oExcel.WorkBooks.Open(FileName)
       set oSheet=oWb.Sheets("Sheet1")   '���г�ʼ������ͳһ����sheet1��
       
       TempRow=1
       TempColumn=1
       do while oSheet.cells(1,TempColumn)<>""   '���г�ʼ�����ݵ�SQL������������һ�����м䲻���п���
           TempColumn=TempColumn+1
       loop
       TempColumn=TempColumn-1
       
       do while oSheet.cells(TempRow,TempColumn)<>""  '���г�ʼ�����ݵ�SQL������м䲻���п���
           TempRow=TempRow+1
       loop
       'TempRow=TempRow-1
       
       for j=1 to TempRow
          if FileNameList(i)<>"��������.xls" and FileNameList(i)<>"����ȡֵ��Ӧ��ϵ����.xls" then
              of.WriteLine replace(replace(oSheet.cells(j,TempColumn).Value,"getdate()","SYSDATE"),"' '' ''","'''''")    '���һ�д��oracle�������ڶ�����mssql
          else 
              of.WriteLine replace(replace(oSheet.cells(j,TempColumn).Value,"getdate()","SYSDATE"),"' '' ''","'''''") 
          end if
       next
	   
	   if TempRow>1 then
	       of.WriteLine "commit;"
           end if
		
       oWb.Close
   end if
next

'of.WriteLine "update sys_menu set url=null where url=' ';"

'of.WriteLine "update sys_menu set icon=null;"

'of.WriteLine "update sys_menu set url='' where moduleid='0'; "

of.WriteLine "commit;"


'update sys_menu set url=null where url=' ';

'--------------------------���ϳ�ʼ������--------------------------------------------------

'FileNames=""


'FileNames=FileNames&"��Ӫ��ȯ-���Խ���.txt"&","


'FileNameList=split(FileNames,",")

'i=0
'For i = Lbound(FileNameList) To Ubound(FileNameList) 
'   if FileNameList(i)<>"" then
'       FileName=fso.GetAbsolutePathName(FileNameList(i))
'       of.WriteLine "-------------------"&FileName&"------------------------------"
'       Set file=fso.OpenTextFile(FileName,1,True) 
'       Do While Not file.AtEndOfStream 
'           of.WriteLine file.ReadLine
'       loop
'       of.WriteLine "-------------------------------------------------------------"
'   end if
'next

'of.WriteLine "commit;"
of.WriteLine "-- -------------------"&FormatDateTime(Now,0)&"-------------------------"
of.Close

MsgBox "�ű�������ϣ�"








