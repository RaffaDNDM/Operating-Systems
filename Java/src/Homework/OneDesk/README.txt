--------------------------------------------------------------------------------
||                                 Tema 6                                     ||
--------------------------------------------------------------------------------
|                     Le stringhe di diagnostica sono:                         |
--------------------------------------------------------------------------------
| > Inattivita' momentanea dello sportello                                     |    
|   The employer isn't at the desk                                             |
|                                                                              |
| > Ritorno all'attivita' dello sportello                                      |
|   The employer has come back                                                 |
--------------------------------------------------------------------------------
| > Uscita del cliente i dal sistema perche' ha superato il suo timeout        |
|   The client i doesn't want to wait anymore and exits from the system        |
--------------------------------------------------------------------------------
| > All'inizio del servizio di un cliente viene stampata la situazione         |
|   Enqueued_P: x Enqueued_N: y                                                |
|                                                                              |
|   x = numero di clienti con priorita' nella coda di ingresso                 |
|   y = numero di clienti senza priorita' nella coda di ingresso               |
--------------------------------------------------------------------------------
| > Alla fine del servizio di un cliente viene stampata la situazione          |
|   Served_P: x Served_N: y Out: z                                             |
|                                                                              |
|   x = numero di clienti con priorita' che sono stati serviti allo sportello  |
|   y = numero di clienti senza priorita' che sono stati serviti allo sportello|
|   z = numero di clienti che sono usciti senza effettuare il servizio perche' |
|      hanno superato il loro timeout                                          |
--------------------------------------------------------------------------------
