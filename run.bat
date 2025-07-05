@echo off
cd /d D:\Automation\ui-tests
mvn clean test -PCrossBrowser_tests
pause
