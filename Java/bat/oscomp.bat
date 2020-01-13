rem Corso di Sistemi Operativi
rem compilazione della libreria di classi Java
cd ..\lib
javac -classpath . -deprecation -Xlint:unchecked os/*.java os/ada/*.java ostest/*.java ostest/ada/*.java osextra/*.java
cd ..\bat
pause
