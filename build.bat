@echo off
title Building the Aether II...

:: Call Gradle Setup
call gradle/gradlew.bat build

echo.
echo Finished. The compiled jars can be found in the following locations:
echo.
echo Aether II          : %CD%\build\libs
echo.
echo Gilded Games Util  : %CD%\gilded-games-util\build\libs
echo.

pause
exit
