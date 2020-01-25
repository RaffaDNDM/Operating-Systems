/**
@author Di Nardo Di Maio Raffaele
*/

package Officina;

import os.Semaphore;

public class OfficinaJava extends Officina
{
   private int[] ticket = new int[2];
   private int[] service = new int[2];

   public OfficinaJava(int NTAV)
   {
      super(NTAV);
   }

   public synchronized int entra(int tipo)
   {
      int station;
      inAttesa[tipo]++;
      printState();
      int t = ticket[tipo]++;

      while((t!=service[tipo]) || (tipo==TIPO0 && (postiLiberi==0)) || (tipo==TIPO1 && stazioniLibere == 0))
      {
         try
         {
            wait();
         }
         catch(InterruptedException e)
         {
            System.out.println("Interrupted");
         }
      }

      inAttesa[tipo]--;
      service[tipo]++;

      switch(tipo)
      {
         case TIPO0:
         {
            station = trovaPosto();

            postiLiberi--;
            stato[station]++;

            printState();

            if(stato[station]==1)
            {
               stazioniLibere--;

               printState();

               if(inAttesa[TIPO0]>0)
                  notifyAll();
            }

            break;
         }

         case TIPO1:
         {
            station = trovaStazione();
            stazioniLibere--;
            postiLiberi-=2;
            stato[station]=2;

            printState();

            break;
         }

         default:
            return -1;
      }

      return station;
   }

   public synchronized void esce(int tipo, int pos)
   {
      served[tipo]++;

      switch(tipo)
      {
         case TIPO0:
         {
            postiLiberi++;
            stato[pos]--;
            printState();
         }
            break;

         case TIPO1:
         {
            postiLiberi+=2;
            stato[pos]-=2;
         }
            break;
      }

      if(stato[pos]==0)
      {
         stazioniLibere++;
      }

      printState();
      notifyAll();

   }
}
