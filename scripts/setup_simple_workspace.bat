@echo off
title Aether II Simple Workspace Setup

echo.
echo ! WARNING !
echo.
echo Developers: You shouldn't be using this script to setup your workspace. Please see
echo CONTRIBUTING.md to setup your workspace.
echo.
echo - Press [ENTER] to continue, or press [CTRL+C] to cancel. -
pause > nul

:: Push in to the root folder
pushd ..

:: Configure Aether-1.8
call gradlew setupDecompWorkspace eclipse

:: Pop back into the scripts directoy
popd

echo.
echo Finished. You can now change your workspace in Eclipse to:
echo %cd%\eclipse\
echo.

pause
exit