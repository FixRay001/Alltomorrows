@echo off
setlocal

set "ROOT_DIR=%~dp0"
set "JAR_PATH=%ROOT_DIR%build\alltomorrows-part1-beta.jar"

if not exist "%JAR_PATH%" (
    call "%ROOT_DIR%build.bat"
    if errorlevel 1 exit /b 1
)

java -jar "%JAR_PATH%"
if errorlevel 1 (
    echo.
    echo Could not start the desktop menu.
    echo Make sure Java is installed and run this on your local computer, not in a remote terminal.
    pause
    exit /b 1
)
