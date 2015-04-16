@echo off
title Aether 1.8 Dev Workspace Setup

:: Configure Aether-1.8
call gradle/gradlew.bat setupDecompWorkspace eclipse

pause