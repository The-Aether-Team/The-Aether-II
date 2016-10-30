@echo off
title Building the Aether II...
set GRADLE_OPTS=-Xmx2G
call gradlew.bat build

pause
exit
