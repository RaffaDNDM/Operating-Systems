/**
@author Di Nardo Di Maio Raffaele
*/

package Desks;

import os.Monitor;
import os.Monitor.Condition;
import os.Util;

public class DesksMonitor extends Desks
{
   private Monitor m = new Monitor();
   private Condition c = m.new Condition();

   public DesksMonitor()
   {
      for(int i=0; i<DESKS; desks[i++]=true);
   }

   public int entraCoda()
   {
      m.mEnter();

      wait_clients++;

      if(freeDesks==0)
         c.cWait();

      freeDesks--;
      wait_clients--;

      int i=0;

      for(; i<DESKS; i++)
      {
         if(desks[i])
         {
            desks[i]=false;
            break;
         }
      }

      m.mExit();
      return i;
   }

   public int esce(int sport)
   {
      m.mEnter();

      freeDesks++;
      desks[sport] = true;
      done_clients++;

      System.out.println("Il numero di clienti serviti: "+done_clients);

      if(c.queue()>0)
         c.cSignal();

      m.mExit();
      return sport;
   }

   private class Client extends Thread
   {
      private int num;

      public Client(int num)
      {
         this.num = num;
      }

      public void run()
      {
         System.out.println("Il cliente "+num+" e' in coda");
         int sport = entraCoda();
         System.out.println("Il cliente "+num+" entra nello sportello "+sport);
         Util.rsleep(1000L, 2000L);
         sport = esce(sport);
         System.out.println("Il cliente "+num+" esce dallo sportello "+sport);
      }
   }

   public static void main(String[] args)
   {
      DesksMonitor d = new DesksMonitor();

      for(int i=0; i<CLIENTS; i++)
      {
         d.new Client(i).start();
         Util.rsleep(100, 200);
      }
   }
}
