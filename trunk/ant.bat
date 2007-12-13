@echo off
@if not "%ECHO%" == ""  echo %ECHO%
@if "%OS%" == "Windows_NT"  setlocal

set DIRNAME=.\

if "%OS%" == "Windows_NT" set DIRNAME=%~dp0%
set PROGNAME=ant.bat
if "%OS%" == "Windows_NT" set PROGNAME=%~nx0%

set SEAM_HOME="%DIRNAME%\opt\jboss-seam-2.0.0.GA"

set ARGS=%ARGS% %*

java -cp "%JAVA_HOME%\lib\tools.jar;%SEAM_HOME%\build\lib\ant-launcher.jar;%SEAM_HOME%\build\lib\ant-nodeps.jar;%SEAM_HOME%\build\lib\ant.jar" -Dant.home="%SEAM_HOME%\lib" org.apache.tools.ant.launch.Launcher -buildfile "%DIRNAME%\build.xml" %ARGS%

goto END_NO_PAUSE
