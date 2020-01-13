rem Corso di Sistemi Operativi
rem generazione di API javadoc delle libreria di classi Java v1.4
rem richiede javadoc 1.4 o seguenti per l'opzione subpackages
rem si e' assunto che sia stato aggiunto il package exer non compreso nello zip della libreria
javadoc -use -version -package -sourcepath ../lib -d ../lib/javadoc -subpackages os os.ada osTest osTest.ada osExtra
pause

