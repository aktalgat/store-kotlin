echo off
REM runscript.bat store C:\\Users\\talgat\\projects\\java\\store-kotlin\\database\\sql superpass > log.txt 2>&1

set dbname=%1
set scriptroot=%2
set pass=%3%

if [%1]==[] goto finish
if [%2]==[] goto finish
if [%3]==[] goto finish

echo dbname %dbname%

set pgpath=C:\Program Files\PostgreSQL\10\bin
set PGPASSWORD=%pass%
set PGCLIENTENCODING=UTF-8
setlocal enabledelayedexpansion

"%pgpath%\psql.exe" -U postgres -c "SELECT pg_terminate_backend(pid) FROM pg_stat_activity WHERE datname = '%dbname%'"

"%pgpath%\dropdb.exe" -U postgres  %dbname%

"%pgpath%\psql.exe" -U postgres -c "\i '%scriptroot%/core/01-create-db.sql'"
"%pgpath%\psql.exe" -U postgres -d %dbname% -c "\i '%scriptroot%/core/02-create-schema.sql'"

for /R %scriptroot%\\initial-data\\ %%f in (*.sql) do (
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