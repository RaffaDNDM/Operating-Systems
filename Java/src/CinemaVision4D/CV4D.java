/**
@author Di Nardo Di Maio Raffaele
*/

package CinemaVision4D;

import os.Semaphore;
import os.Timeout;
import os.Util;

public class CV4D
{
   private Semaphore t1 = new Semaphore(0, NMAX);
   private Semaphore t2 = new Semaphore(0, NMAX);
   private Semaphore postiOccupati = new Semaphore(0, NMAX);
   private Semaphore ultimo = new Semaphore(false);

   private final static int NMAX = 20;
   private final static long ATTESAMAX = 6000L;
   private final static long TEMPOPROIEZIONE = 20000L;

   private class Operatore extends Thread
   {
      public void run()
      {
         while(true)
         {
            for(int i=0; i<NMAX; i++)
               t1.v();

            //Attesa che arrivi il primo utente
            postiOccupati.p();
            postiOccupati.v();

            if(ultimo.p(ATTESAMAX)==Timeout.EXPIRED)
            {
               //Azzero il numero di marche disponibili per evitare che
               //qualcun altro possa entrare mentre il film � in riproduzione
               while(t1.p(Timeout.IMMEDIATE)==Timeout.EXPIRED);
            }

            //Riproduzione del film
            Util.sleep(TEMPOPROIEZIONE);

            //Quanti spettatori c'erano in sala?
            int num_spettatori = postiOccupati.value();

            for(int i=0; i<num_spettatori; i++)
            {
               t2.v();
            }

            ultimo.p();
         }
      }
   }

   private class Spettatore extends Thread
   {
      private int id;

      public Spettatore(int id)
      {
         this.id = id;
      }

      public void run()
      {
         t1.p();

         //Attesa che arrivi il primo utente
         postiOccupati.p();

         //Lo spettatore � l'ultimo
         if(postiOccupati.value()==NMAX)
            ultimo.v();

         t2.p();
         postiOccupati.p();

         if(postiOccupati.value()==0)
            ultimo.v();
      }
   }

   public static void main(String args[])
   {
      CV4D c = new CV4D();
      c.new Operatore().start();
      for (int i=1; i<=22; i++)
      {
          Util.rsleep(1000, 10000);
          c.new Spettatore(i).start();
      }
   }
}
