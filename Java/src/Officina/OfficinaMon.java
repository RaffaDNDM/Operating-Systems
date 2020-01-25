/**
@author Di Nardo Di Maio Raffaele
*/

package Officina;

import os.Monitor;
import os.Monitor.Condition;

public class OfficinaMon extends Officina
{
   private Monitor m = new Monitor();
   private Condition[] codabot = new Condition[2];

   public OfficinaMon(int NTAV)
   {
      super(NTAV);
      codabot[TIPO0] = m.new Condition();
      codabot[TIPO1] = m.new Condition();
   }

   public int entra(int tipo)
   {
      int station;
      m.mEnter();

      if((tipo==TIPO0 && postiLiberi==0) || (tipo==TIPO1 && stazioniLibere == 0))
      {
         inAttesa[tipo]++;

         printState();

         codabot[tipo].cWait();
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
                  codabot[TIPO0].cSignal();
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

      m.mExit();
      return station;
   }

   public void esce(int tipo, int pos)
   {
      m.mEnter();

      served[tipo]++;

      switch(tipo)
      {
         case TIPO0:
         {
            postiLiberi++;
            stato[pos]--;
            printState();

            if(stato[pos]==1 && inAttesa[TIPO0]>0)
                  codabot[TIPO0].cSignal();
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
            codabot[TIPO1].cSignal();
         else if(inAttesa[TIPO0]>0)
            codabot[TIPO0].cSignal();
      }

      m.mExit();
   }
}
