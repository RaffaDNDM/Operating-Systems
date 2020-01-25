/**
@author Di Nardo Di Maio Raffaele
*/

package Parcheggio;

import os.Util;

public abstract class Salita
{
   /**true se l'ascensore sta caricando passeggeri a terra*/
   protected boolean aperto = true;
   /**numero di piani a cui gli utenti vogliono accedere (escluso piano terra)*/
   protected final static int NUM_UTENTI = 40;
   /**numero di piani a cui gli utenti vogliono accedere (escluso piano terra)*/
   protected final static int NUM_PIANI = 10;
   /**numero di utenti che vogliono salire in ciascun piano*/
   protected int[] pren = new int[NUM_PIANI];
   /**numero di utenti in P*/
   protected int prenP;
   /**numero di utenti in S*/
   protected int prenS;
   /**ticket di P*/
   protected int ticketP=0;
   /**ticket di S*/
   protected int ticketS=0;
   /**ticket da servire di P*/
   protected int servP=0;
   /**ticket da servire di S*/
   protected int servS=0;
   /**occupazione (progressiva) dell'ascensore*/
   protected int num;
   /**piano corrente di fermata*/
   protected int arrivato;
   /**tempo per salire o scendere di un piano*/
   protected final static long PIANOT = 2000L;
   /**tempo per far scendere le persone al piano*/
   protected final static long DISCESA = 3000L;
   /**tempo di un utente per l'ingresso nell'ascensore*/
   protected final static long ENTRATIM = 1000L;
   /**tempo di un utente per l'ingresso nell'ascensore*/
   protected final static int CAP = 10;
   /**tempo di un utente per l'ingresso nell'ascensore*/
   protected final static long MAXATT = 2000L;

   /**
   attesa ordinata nella coda P
   @param piano piano in cui l'utente vuole andare
   */
   public abstract void inCodaP(int piano);

   /**
   attesa ordinata nella coda S
   @param piano piano in cui l'utente vuole andare
   */
   public abstract void inCodaS(int piano);

   /**
   simula l�ingresso di un utente nell�ascensore
   e l'attesa di quest'ultimo fino all'arrivo al piano richiesto
   @param piano piano in cui l'utente vuole andare
   */
   public abstract void entra(int piano);

   /**
   indice del piano
   @param pr array con prenotazioni pendenti
   @return indice del piano inferiore che ha prenotazioni pendenti
   */
   public abstract int minPren(int pr[]);

   /**
   simula l�attesa dell�ascensore per il carico seguita dalla
   visita nell�ordine di tutti i piani prenotati
   */
   public abstract void servizio();

   /**
   simula la discesa dell�ascensore dall'ultimo piano visitato
   al piano terra
   */
   public abstract void discesa();
}
