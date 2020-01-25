/**
@author Di Nardo Di Maio Raffaele
*/

package Strade;

import os.Semaphore;
import os.Util;

public class StradeSem extends Strade
{
   private Semaphore mutex = new Semaphore(true);
   private Semaphore[] priv = new Semaphore[3];

   public StradeSem()
   {
      super();

      for(int i=0; i<3; priv[i++] = new Semaphore(false));
   }

   public int entra(int strada)
   {
      int next_corsia;

      mutex.p();

      attesa[strada]++;
      printState();

      if(numv[0]+numv[1]>=2*MAXV)
      {
         mutex.v();
         priv[strada].p();
         attesa[strada]--;
         numv[corsia]++;
         mutex.v();
         return corsia;
      }

      attesa[strada]--;

      next_corsia = (numv[0]<numv[1])?0:1;
      numv[next_corsia]++;
      printState();

      mutex.v();
      return next_corsia;
   }

   public void esce(int corsia)
   {
      mutex.p();

      numv[corsia]--;
      serviti++;
      this.corsia = corsia;
      printState();

      if(attesa[2]>0)
         priv[2].v();
      else if (attesa[1]>0 && attesa[1]>0)
         priv[Util.randVal(0,1)].v();
      else if(attesa[0]>0)
         priv[0].v();
      else if(attesa[1]>0)
         priv[1].v();

      mutex.v();
   }
}
