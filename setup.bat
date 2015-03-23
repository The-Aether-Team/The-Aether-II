@echo off
title Aether II Development Workspace Setup

:: Call Gradle Setup
call gradlew setupDecompWorkspace eclipse
pause