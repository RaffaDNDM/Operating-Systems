/**
@author Di Nardo Di Maio Raffaele
*/

package Strade;

import os.Monitor;
import os.Monitor.Condition;
import os.Util;

public class StradeMon extends Strade
{
   private Monitor mutex = new Monitor();
   private Condition[] priv = new Condition[3];

   public StradeMon()
   {
      super();

      for(int i=0; i<3; priv[i++] = mutex.new Condition());
   }

   public int entra(int strada)
   {
      int next_corsia;

      mutex.mEnter();

      attesa[strada]++;
      printState();

      if(numv[0]+numv[1]>=2*MAXV)
      {
         priv[strada].cWait();
         attesa[strada]--;
         numv[corsia]++;
         mutex.mExit();
         return corsia;
      }

      attesa[strada]--;

      next_corsia = (numv[0]<numv[1])?0:1;
      numv[next_corsia]++;
      printState();

      mutex.mExit();
      return next_corsia;
   }

   public void esce(int corsia)
   {
      mutex.mEnter();

      numv[corsia]--;
      serviti++;
      this.corsia = corsia;
      printState();

      if(attesa[2]>0)
         priv[2].cSignal();
      else if (attesa[1]>0 && attesa[1]>0)
         priv[Util.randVal(0,1)].cSignal();
      else if(attesa[0]>0)
         priv[0].cSignal();
      else if(attesa[1]>0)
         priv[1].cSignal();

      mutex.mExit();
   }
}
