@echo off
title Setting up your Forge workspace...
set GRADLE_OPTS=-Xmx2G
call gradlew.bat setupDecompWorkspace

pause
exit