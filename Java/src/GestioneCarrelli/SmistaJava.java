/**
@author Di Nardo Di Maio Raffaele
*/

package GestioneCarrelli;

import os.Region;
import os.RegionCondition;
import os.Util;

public  class SmistaJava extends Smista
{
   public synchronized int instrada(final int i)
   {
      int ticket_in = ticket[i]++;

      enqueued[i]++;
      stampaSituazione();

      while(ticket_in!=serv[i] || !I1 || (!B[1] && !B[0]) || !C[i])
      {
         try
         {
            wait();
         }
         catch(InterruptedException e)
         {
            System.out.println("Interruzione");
         }
      }

      I1=false;
      serv[i]++;
      enqueued[i]--;
      int elevatore = B[0]?0:1;
      stampaSituazione();

      return elevatore;
   }

   public synchronized void outI1(int h)
   {
      I1=true;
      freeB--;
      B[h]=false;
      stampaSituazione();
   }

   public synchronized int outB(int h)
   {
      while(!I2)
      {
         try
         {
            wait();
         }
         catch(InterruptedException e)
         {
            System.out.println("Interruzione");
         }
      }

      I2=false;
      freeB++;
      B[h]=true;
      stampaSituazione();
      notifyAll();

      return ((Carrello) Thread.currentThread()).i;
   }

   public synchronized void inC(int i)
   {
      I2= true;
      C[i]=false;
      freeC--;
      stampaSituazione();
   }

   public synchronized void outC(int i)
   {
      freeC++;
      C[i]=true;
      served[i]++;
      stampaSituazione();
      notifyAll();
   }

   public static void main(String[] args)
   {
      Smista s = new SmistaJava();

      for(int i=0; i<CARRELLI; i++)
      {
         new Carrello(i, s).start();

         long min=Long.parseLong(args[0]);
         long max=Long.parseLong(args[1]);

         Util.rsleep(min,max);
      }
   }
}
