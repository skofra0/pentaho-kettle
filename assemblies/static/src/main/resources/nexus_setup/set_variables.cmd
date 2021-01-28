@ECHO OFF
@ECHO.
@ECHO *********************************************
@ECHO ** EXISTING ETL VARIABLES                  **
@ECHO *********************************************
@ECHO DI_DESIGNER=%DI_DESIGNER%
@ECHO KETTLE_HOME=%KETTLE_HOME%
@ECHO JAVA_HOME=%JAVA_PENTAHO_HOME%
@ECHO JAVA_PENTAHO_HOME=%JAVA_PENTAHO_HOME%
@ECHO.
@ECHO.
@ECHO *********************************************
@ECHO ** NEW FOLDER LOCATION                     **
@ECHO *********************************************
SET CurrentDir=%~dp0
ECHO %CurrentDir%
PUSHD %CurrentDir%
CD ..

SET DI_DESIGNER=%CD% 
@ECHO DI_DESIGNER=%DI_DESIGNER%
@ECHO Confirm setting of DI_DESIGNER variable
PAUSE:
SETX /m DI_DESIGNER "%CD%"
COPY "DI Designer.lnk" "%userprofile%\desktop" /y

CD ..
CD ..
IF "%KETTLE_HOME%"=="" (
@ECHO SETX KETTLE_HOME=%CD%
SETX /m KETTLE_HOME "%CD%"
SET KETTLE_HOME=%CD% 
) ELSE (
  @ECHO SKIP SETX KETTLE_HOME
)

POPD

@ECHO.
@ECHO *********************************************
@ECHO ** NEW ETL VARIABLES                       **
@ECHO *********************************************
@ECHO.
@ECHO DI_DESIGNER=%DI_DESIGNER%
@ECHO KETTLE_HOME=%KETTLE_HOME%

PAUSE: