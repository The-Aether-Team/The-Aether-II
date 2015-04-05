@echo off
title Aether II Multi-Project Workspace Setup

:: Push in to the root folder
pushd ..

:: Configure Aether-1.8
call gradlew.bat setupDecompWorkspace

:: Pop back to the scripts folder
popd

echo.
echo Finished. You can now change your workspace in Eclipse to:
echo %cd%\eclipse\
echo.

pause
exit