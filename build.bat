@echo off
setlocal

set "ROOT_DIR=%~dp0"
set "BUILD_DIR=%ROOT_DIR%build"
set "CLASSES_DIR=%BUILD_DIR%\classes"
set "SOURCES_FILE=%BUILD_DIR%\sources.txt"
set "JAR_PATH=%BUILD_DIR%\alltomorrows-part1-beta.jar"

where javac >nul 2>nul
if errorlevel 1 (
    echo Java JDK not found. Install JDK 17 or newer, then try again.
    echo Download: https://adoptium.net/
    pause
    exit /b 1
)

if exist "%CLASSES_DIR%" rmdir /s /q "%CLASSES_DIR%"
mkdir "%CLASSES_DIR%" >nul 2>nul

powershell -NoProfile -ExecutionPolicy Bypass -Command ^
    "Get-ChildItem -Path '%ROOT_DIR%src\main\java' -Filter '*.java' -Recurse | ForEach-Object { $_.FullName } | Set-Content -Encoding UTF8 '%SOURCES_FILE%'"

javac -encoding UTF-8 -d "%CLASSES_DIR%" @"%SOURCES_FILE%"
if errorlevel 1 (
    echo Build failed.
    pause
    exit /b 1
)

jar --create --file "%JAR_PATH%" --main-class com.alltomorrows.menu.MainMenuApp -C "%CLASSES_DIR%" .
if errorlevel 1 (
    echo Jar creation failed.
    pause
    exit /b 1
)

echo Built "%JAR_PATH%"
endlocal
