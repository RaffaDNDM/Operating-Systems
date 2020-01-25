/**
@author Di Nardo Di Maio Raffaele
*/

package Officina;

import os.Semaphore;

public class OfficinaSem extends Officina
{
   private Semaphore m = new Semaphore(true);
   private Semaphore[] codabot = new Semaphore[2];

   public OfficinaSem(int NTAV)
   {
      super(NTAV);
      codabot[TIPO0] = new Semaphore(false);
      codabot[TIPO1] = new Semaphore(false);
   }

   public int entra(int tipo)
   {
      int station;
      m.p();

      if((tipo==TIPO0 && postiLiberi==0) || (tipo==TIPO1 && stazioniLibere == 0))
      {
         inAttesa[tipo]++;

         printState();

         m.v();
         codabot[tipo].p();
         inAttesa[tipo]--;
      }

      switch(tipo)
      {
         case TIPO0:
         {
            station = trovaPosto();

            if(stato[station]==0)
            {
               stazioniLibere--;

               printState();

               if(inAttesa[TIPO0]>0)
                  codabot[TIPO0].v();
            }

            postiLiberi--;
            stato[station]++;

            printState();

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

      m.v();
      return station;
   }

   public void esce(int tipo, int pos)
   {
      m.p();

      served[tipo]++;

      switch(tipo)
      {
         case TIPO0:
         {
            postiLiberi++;
            stato[pos]--;
            printState();

            if(stato[pos]==1 && inAttesa[TIPO0]>0)
                  codabot[TIPO0].v();
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

         printState();

         if(inAttesa[TIPO1]>0)
            codabot[TIPO1].v();
         else if(inAttesa[TIPO0]>0)
            codabot[TIPO0].v();
      }

      m.v();
   }
}
