@echo off
setlocal
set d=%date%
set t=%time%
if "%t:~0,1%"==" " set t=0%t:~1,4%
set cdate=%d:~6,4%%d:~3,2%%d:~0,2%
set logfile=d:\deem\Jobs\log\job_finance.log
echo Start: %date% %time% >> %logfile%


D:
cd %DI_DESIGNER%
call %DI_DESIGNER%\Kitchen.bat /rep:prd /job:job_finance /dir:/mi_finance /user:admin  /pass:admin /level:Basic > %logfile%

