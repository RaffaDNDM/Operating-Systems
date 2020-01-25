/**
@author Di Nardo Di Maio Raffaele
*/

package PrioritySemaphore;

import os.Monitor;
import os.Monitor.Condition;
import os.Util;

public class PMSem extends Monitor
{
   private final static int MAX_PRIO = 10;
   private Condition[] c = new Condition[MAX_PRIO+1];
   private int[] suspended = new int[MAX_PRIO+1];
   private int wait_tot = 0;
   private int num = 0;

   public PMSem(int num)
   {
      this.num = num;

      for(int i=0; i<=MAX_PRIO; i++)
      {
         c[i] = new Condition();
         suspended[i]=0;
      }
   }

   private class ThreadSem extends Thread
   {
      private int prio;

      public ThreadSem(int prio)
      {
         super("Thread con prio="+prio);
         this.prio = prio;
      }


      public void run()
      {
         System.out.println("Attivato "+getName());
         while(true)
         {
             Util.rsleep(1000+prio*1000, 4000+prio*1000);
             System.out.println(getName()+" attende");
             p(prio);
             System.out.println(getName()+" risvegliato");
         }
      }
   }

   public void p(int prio)
   {
      mEnter();

      if(num==0)
      {
         suspended[prio]++;
         wait_tot++;
         c[prio].cWait();
         wait_tot--;
         suspended[prio]--;
      }

      num--;

      mExit();
   }

   public void v()
   {
      mEnter();

      num++;

      if(wait_tot>0)
      {
         for(int i=MAX_PRIO; i>=0; i--)
         {
            if(suspended[i]>0)
            {
               c[i].cSignal();
               break;
            }
         }
      }

      mExit();
   }

   public static void main(String[] args)
   {
     // non fa controlli sui parametri
     PMSem ps = new PMSem(3);

     for (int i=1; i<=6; i++)
         ps.new ThreadSem(i).start();

     while(true)
     {
         Util.rsleep(500, 3000);
         System.out.println("*** v()");
         ps.v();
     }
   }
}
