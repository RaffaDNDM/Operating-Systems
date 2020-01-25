/**
@author Di Nardo Di Maio Raffaele
*/

package Strade;

import os.Semaphore;
import os.Util;

public class StradeJava extends Strade
{
   private boolean call = false;
   private int[] ticket = new int[3];
   private int[] service = new int[3];

   public StradeJava()
   {
      super();

      for(int i=0; i<3; i++)
      {
         ticket[i] = 0;
         service[i] = 0;
      }
   }

   public synchronized int entra(int strada)
   {
      int next_corsia;

      attesa[strada]++;
      printState();
      int t = ticket[strada]++;

      //Priorit� a strada C
      while(t!=service[strada] || numv[0]+numv[1]>=2*MAXV || (strada<=1 && attesa[2]>0))
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

      service[strada]++;
      attesa[strada]--;

      if(call)
      {
         numv[corsia]++;
         call=false;
         next_corsia=corsia;
      }
      else
      {
         next_corsia = (numv[0]<numv[1])?0:1;
         numv[next_corsia]++;
      }

      printState();

      return next_corsia;
   }

   public synchronized void esce(int corsia)
   {
      numv[corsia]--;
      serviti++;
      this.corsia = corsia;
      call=true;
      printState();

      notifyAll();
   }
}
