@echo off
title Launching Map Editor...
cd %~dp0
rem start javaw -jar mapeditor-v1.jar %~n1.xml
rem start javaw -jar mapeditor.jar %~n1-v2.xml

start javaw -jar mapeditor.jar %~n1.xml
