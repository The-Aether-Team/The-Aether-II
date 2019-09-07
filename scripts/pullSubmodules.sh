#!/bin/bash
echo Initiating and pulling submodules, please wait.
git submodule update --init --recursive
echo Submodules complete.
read -p "Press [Enter] key to continue..."