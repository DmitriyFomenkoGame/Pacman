@echo off
set MYCLASSPATH=.\lib\anji.jar;.\lib\jgap.jar;.\lib\log4j.jar;.\lib\jakarta-regexp-1.3.jar;.\lib\clibwrapper_jiio.jar;.\lib\mlibwrapper_jiio.jar;.\lib\jai_imageio.jar;.\lib\hb16.jar;.\lib\jcommon.jar;.\lib\jfreechart.jar;.\lib\jakarta-regexp-1.3.jar;.\lib\Pacman.jar;.\properties
java -classpath %MYCLASSPATH% -Xms256m -Xmx384m Anji.PacmanEvaluator %1 %2
