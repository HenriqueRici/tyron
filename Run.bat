@echo off
setlocal enabledelayedexpansion

rem utf-8
chcp 65001>nul
set args=%*

rem procura, os arquivos .java e compila

set java_files=

for /f "tokens=*" %%f in ('dir /b /s *.java') do (
    set java_files=%%f !java_files!
)
rem set parent_folder=%cd%
javac %java_files% -d ./compiled/

set test_folder=%cd%\teste

cd compiled

java org.carlos.principal.Main %*

endlocal
exit /b /0