@echo off
title Building the Aether II...

:: Push in to the root folder
pushd ..

:: Call Gradle Setup
call gradlew.bat build

:: Pop back into the scripts folder
popd

echo.
echo Finished. The compiled jars can be found in the following locations:
echo.
echo Aether II			: %CD%\build\libs
echo.
echo Gilded Games Util	: %CD%\gilded-games-util\build\libs
echo.
pause
exit