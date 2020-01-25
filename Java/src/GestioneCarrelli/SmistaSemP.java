/**
@author Di Nardo Di Maio Raffaele
*/

package GestioneCarrelli;

import os.Semaphore;
import os.Util;

public  class SmistaSemP extends Smista
{
   Semaphore m = new Semaphore(true);
   Semaphore a0 = new Semaphore(false);
   Semaphore a1 = new Semaphore(false);
   Semaphore a2 = new Semaphore(false);
   Semaphore b = new Semaphore(false);

   private boolean[] stat = new boolean[3];

   public SmistaSemP()
   {
      for(int j=0; j<3; stat[j++]=true);
   }

   public int instrada(final int i)
   {
      int elevatore;

      m.p();

      enqueued[i]++;
      stampaSituazione();

      if(!I1 || (!B[0] && !B[1]) || !C[i])
      {
         switch(i)
         {
            case 0:
            {
               m.v();
               a0.p();
               break;
            }

            case 1:
            {
               m.v();
               a1.p();
               break;
            }

            case 2:
            {
               m.v();
               a2.p();
               break;
            }
         }
      }

      enqueued[i]--;
      elevatore = B[0]?0:1;
      serv[i]++;
      freeB--;
      I1 = false;
      stat[i]=false;
      stampaSituazione();

      m.v();

      return elevatore;
   }

   public synchronized void outI1(int h)
   {
      m.p();

      I1 = true;
      B[h]=false;
      stampaSituazione();

      m.v();
   }

   public int outB(int h)
   {
      int exit;

      m.p();

      if(!I2)
      {
         m.v();
         b.p();
      }

      freeB++;
      B[h] = true;
      I2 = false;
      exit = ((Carrello) Thread.currentThread()).i;
      stampaSituazione();

      m.v();

      return exit;
   }

   public void inC(int i)
   {
      m.p();

      freeC--;
      I2 = true;
      C[i] = false;
      stampaSituazione();

      m.v();
   }

   public void outC(int i)
   {
      m.p();

      freeC++;
      C[i] = true;
      served[i]++;
      stat[i]=true;
      stampaSituazione();

      if(freeB<=1)
         b.v();
      else if(enqueued[i]>0 && I1 && freeB>0)
      {
         switch(i)
          {
               case 0:
                  a0.v();

               case 1:
                  a1.v();

               case 2:
                  a2.v();
          }
      }
      else if(freeB>0 && I1)
      {
         int index = (i+1)%3;

         if(stat[index] && enqueued[index]>0 && C[index])
         {
            switch(index)
            {
               case 0:
                  a0.v();

               case 1:
                  a1.v();

               case 2:
                  a2.v();
            }
         }
         else if(stat[(index+1)%3] && enqueued[(index+1)%3]>0 && C[(index+1)%3])
         {
            switch((index+1)%3)
            {
               case 0:
                  a0.v();

               case 1:
                  a1.v();

               case 2:
                  a2.v();
            }
         }
      }

      m.v();
   }

   public static void main(String[] args)
   {
      Smista s = new SmistaSemP();

      for(int i=0; i<CARRELLI; i++)
      {
         new Carrello(i, s).start();

         long min=Long.parseLong(args[0]);
         long max=Long.parseLong(args[1]);

         Util.rsleep(min,max);
      }
   }
}
