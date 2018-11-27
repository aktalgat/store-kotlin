echo off
REM runscript.bat store superpass > log.txt 2>&1

set dbname=%1
set pass=%2

set path=%~dp0
set scriptpath=%mypath:~0,-1%

if [%1]==[] goto finish
if [%2]==[] goto finish

echo dbname %dbname%

set pgpath=C:\Program Files\PostgreSQL\10\bin
set PGPASSWORD=%pass%
set PGCLIENTENCODING=UTF-8
setlocal enabledelayedexpansion

"%pgpath%\psql.exe" -U postgres -c "SELECT pg_terminate_backend(pid) FROM pg_stat_activity WHERE datname = '%dbname%'"

"%pgpath%\dropdb.exe" -U postgres  %dbname%

"%pgpath%\psql.exe" -U postgres -c "\i '%scriptpath%/core/01-create-db.sql'"
"%pgpath%\psql.exe" -U postgres -d %dbname% -c "\i '%scriptpath%/core/02-create-schema.sql'"

for /R %scriptpath%\\initial-data\\ %%f in (*.sql) do (
	set var=%%f
    set var1=!var:\=\\!
    echo ---------------------------------------------------------------
    echo !var1!
    echo ---------------------------------------------------------------
    "%pgpath%\psql.exe" -U postgres -d %dbname% -c  "\i '!var1!'"
)

goto done

:finish
	echo Wrong parameters
	exit /B 1

:done
	echo Done
	exit /B 1