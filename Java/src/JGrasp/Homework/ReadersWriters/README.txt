--------------------------------------------------------------------------------
||                            Tema 12, 13, 14                                 ||
--------------------------------------------------------------------------------
|                     Le stringhe di diagnostica sono:                         |
--------------------------------------------------------------------------------
| > Numero di lock condivisi conferiti a lettori                               |
|   e numero di lock esclusivi attribuiti a scrittori                          |
|      Shared_locks:  x     Exclusive_lock:  y                                 |
|                                                                              |
|   x = numero di lock condivisi assegnati a lettori                           |
|   y = numero di lock esclusivi assegnati a scrittori                         |
--------------------------------------------------------------------------------
| > Numero di richieste accodate                                               |
|   Enqueued_shared:  z Enqueued_exclusive:  w                                 |
|                                                                              |
|   z = numero di richieste di lock condivisi accodate                         |
|   w = numero di richieste di lock esclusivi accodate                         |
--------------------------------------------------------------------------------
| > Numero di richieste completate                                             |
|     Served_shared:  j   Served_exclusive:  i                                 |
|                                                                              |
|   j = numero di richieste di lock condivisi completate                       |
|   i = numero di richieste di lock esclusivi completate                       |
--------------------------------------------------------------------------------