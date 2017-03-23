@ECHO OFF
REM Only edit this variables:
SET DISK_Project=E:
SET DISK_Java=C:
SET SrcMainJavaDir=%DISK_Project%\Programmierprojekte\AidWare\Workspace_AidDesk\src\main\java\
REM === DO NOT EDIT THE FOLLOWING ===
%DISK_Project%
if not exist %SrcMainJavaDir%de\odinoxin\aidcloud\service mkdir %SrcMainJavaDir%de\odinoxin\aidcloud\service
cd %SrcMainJavaDir%de\odinoxin\aidcloud\service
del *.*
%DISK_Java%
cd %JAVA_HOME%\bin
@ECHO ON
wsimport -s %SrcMainJavaDir% -p de.odinoxin.aidcloud.service -Xnocompile http://localhost:15123/AidCloud/Login?wsdl
wsimport -s %SrcMainJavaDir% -p de.odinoxin.aidcloud.service -Xnocompile http://localhost:15123/AidCloud/LanguageProvider?wsdl
wsimport -s %SrcMainJavaDir% -p de.odinoxin.aidcloud.service -Xnocompile http://localhost:15123/AidCloud/Translator?wsdl
wsimport -s %SrcMainJavaDir% -p de.odinoxin.aidcloud.service -Xnocompile http://localhost:15123/AidCloud/PersonProvider?wsdl
wsimport -s %SrcMainJavaDir% -p de.odinoxin.aidcloud.service -Xnocompile http://localhost:15123/AidCloud/AddressProvider?wsdl
wsimport -s %SrcMainJavaDir% -p de.odinoxin.aidcloud.service -Xnocompile http://localhost:15123/AidCloud/CountryProvider?wsdl
wsimport -s %SrcMainJavaDir% -p de.odinoxin.aidcloud.service -Xnocompile http://localhost:15123/AidCloud/ContactTypeProvider?wsdl
wsimport -s %SrcMainJavaDir% -p de.odinoxin.aidcloud.service -Xnocompile http://localhost:15123/AidCloud/ContactInformationProvider?wsdl
pause