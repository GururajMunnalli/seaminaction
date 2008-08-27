@echo off
@if not "%ECHO%" == ""  echo %ECHO%
@if "%OS%" == "Windows_NT"  setlocal

set DIRNAME=.\

if "%OS%" == "Windows_NT" set DIRNAME=%~dp0%
set PROGNAME=ant.bat
if "%OS%" == "Windows_NT" set PROGNAME=%~nx0%

set ARGS=%ARGS% %*

java -cp "%JAVA_HOME%\lib\tools.jar;%DIRNAME%\lib\ant-launcher.jar;%DIRNAME%\lib\ant-nodeps.jar;%DIRNAME%\lib\ant.jar" -Dant.home="%DIRNAME%\lib" org.apache.tools.ant.launch.Launcher -buildfile "%DIRNAME%\build.xml" %ARGS%
