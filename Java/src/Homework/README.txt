Ho creato un package per ogni macro argomento e in ciascuno di essi ci sono diverse classi
con un nome ben definito (questa nomenclatura è valida per tutti tranne Spinlock e OneDesk):

NomePackage = classe astratta
NomePackageMain = main del package
NomePackageSem = implementazione con semafori
NomePackageMon = implementazione con monitor di Hoare
NomePackageJava = implementazione con monitor di Java
............................................................................................

Temi

Temi 1,2,3 = package PoolAllocator
Tema 5 = package Spinlock
Tema 6 = package OneDesk
Temi 7,8,9 = package Crossroad
Temi 10,11 = MultiplePC
Temi 12,13,14 = ReadersWriters
............................................................................................ 