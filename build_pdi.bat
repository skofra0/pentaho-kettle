
CLS

if not exist "build_pdi.bat" (
  ECHO You have to start this batch file in the plugin root folder
  pause:
  GOTO :END
)


ant clean-all resolve dist
