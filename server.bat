@ECHO ON
set portid=8080
echo %portid%                                                                              
FOR /F "tokens=5" %%T IN ('netstat -a -n -o ^| findstr %portid% ') DO (
SET /A ProcessId=%%T) &GOTO SkipLine                                                   
:SkipLine   
timeout 1 > NUL                                                                           
echo ProcessId to kill = %ProcessId%
taskkill /f /pid %ProcessId%
java -jar C:\Users\nadeg\OneDrive\Desktop\kino\weissbeerger\target\demo-0.0.1-SNAPSHOT.jar 
PAUSE