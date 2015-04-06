@echo off
title Aether II Multi-Project Workspace Setup

:: Push in to the root folder
pushd ..

:: Configure Aether-1.8
call gradlew.bat setupDecompWorkspace

:: Pop back to the scripts folder
popd

echo.
echo Finished.
echo.

pause
exit