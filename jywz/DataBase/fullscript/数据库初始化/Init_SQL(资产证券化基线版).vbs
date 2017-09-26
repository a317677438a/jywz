Const ForReading = 1, ForWriting = 2, ForAppending = 3
Const TristateUseDefault = -2, TristateTrue = -1, TristateFalse = 0

set oExcel=CreateObject("KET.Application")
set fso = createobject("scripting.filesystemobject")

dim FileName
dim FileNames

FileNames=""

FileNames=FileNames&"系统配置表\SYS_MENU.xls"&","
FileNames=FileNames&"系统配置表\SYS_MENU_DETAIL.xls"&","
FileNames=FileNames&"系统配置表\SYS_USER.xls"&","
FileNames=FileNames&"系统配置表\TS_RATE_INFO.xls"&","
FileNames=FileNames&"系统配置表\SL_SCREEN_FIELD_CONFIGURE.xls"&","
FileNames=FileNames&"系统配置表\SYS_MENU_MAPPING.xls"&","


on error resume Next

dim FileNameList
FileNameList=split(FileNames,",")

dim i
i=0
dim TempRow,TempColumn


'---------------------------------------------ORACLE 版本-------------------------------------------


set of=fso.CreateTextFile("Init_SQL(资产证券化基线版)_mysql.txt")

For i =Lbound(FileNameList)  To Ubound(FileNameList) 
   if FileNameList(Ubound(FileNameList)-i)<>"" then
       FileName=fso.GetAbsolutePathName(FileNameList(Ubound(FileNameList)-i))
       set oWb=oExcel.WorkBooks.Open(FileName)
       set oSheet=oWb.Sheets("Sheet1")   '所有初始化数据统一放在sheet1里
       
       TempRow=1
       TempColumn=1
       do while oSheet.cells(1,TempColumn)<>""   '所有初始化数据的SQL语句必须放在最后一列且中间不能有空列
           TempColumn=TempColumn+1
       loop
       TempColumn=TempColumn-1
       
       'of.WriteLine oSheet.cells(1,TempColumn).Value  '最后一列存放oracle，倒数第二列是mssql
       
       oWb.Close
   end if
next

of.WriteLine "-- --------------------------------------------"

wscript.echo "ORACLE版本"
For i = Lbound(FileNameList) To Ubound(FileNameList) 
   
   if FileNameList(i)<>"" then
       wscript.echo FileNameList(i)&"  进度"&i+1&"/"&Ubound(FileNameList)
       FileName=fso.GetAbsolutePathName(FileNameList(i))
       set oWb=oExcel.WorkBooks.Open(FileName)
       set oSheet=oWb.Sheets("Sheet1")   '所有初始化数据统一放在sheet1里
       
       TempRow=1
       TempColumn=1
       do while oSheet.cells(1,TempColumn)<>""   '所有初始化数据的SQL语句必须放在最后一列且中间不能有空列
           TempColumn=TempColumn+1
       loop
       TempColumn=TempColumn-1
       
       do while oSheet.cells(TempRow,TempColumn)<>""  '所有初始化数据的SQL语句行中间不能有空行
           TempRow=TempRow+1
       loop
       'TempRow=TempRow-1
       
       for j=1 to TempRow
          if FileNameList(i)<>"报文配置.xls" and FileNameList(i)<>"报文取值对应关系配置.xls" then
              of.WriteLine replace(replace(oSheet.cells(j,TempColumn).Value,"getdate()","SYSDATE"),"' '' ''","'''''")    '最后一列存放oracle，倒数第二列是mssql
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

'--------------------------整合初始化数据--------------------------------------------------

'FileNames=""


'FileNames=FileNames&"自营现券-测试交易.txt"&","


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

MsgBox "脚本生成完毕！"








