/**
@author Di Nardo Di Maio Raffaele
*/

package DropOff;

import os.Util;

public class DropJava extends DropOff
{
   private int ticketPrio = 0;
   private int servicePrio = 0;
   private int ticketNorm = 0;
   private int serviceNorm = 0;

   public DropJava()
   {
      pLib[0]=true;
      pLib[1]=true;
   }

   public synchronized int inCoda(boolean prio)
   {
      if(prio)
      {
         int ticket = ticketPrio++;
         wprio++;

         while(ticket!=servicePrio || punLib==0)
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

         servicePrio++;
         wprio--;
      }
      else
      {
         int ticket = ticketNorm++;
         wnorm++;

         while(ticket!=serviceNorm || punLib==0 || wprio>0)
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

         serviceNorm++;
         wnorm--;
      }

      punLib--;
      int pos = pLib[0]?0:1;
      pLib[pos]=false;

      return pos;
   }

   public synchronized void regok(int pos)
   {
      while(pos==1 && !libCD)
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

      if(pos==0)
         libCD = false;

   }

   public synchronized void term(int pos)
   {
      if(pos==0)
         libCD = true;

      punLib++;
      pLib[pos]=true;

      tot_num++;

      stampaSituazione();

      notifyAll();
   }

   public static void main(String[] args)
   {
      DropOff d = new DropJava();

      for(int i=0; i<NUM_BAG; i++)
      {
         int n = Util.randVal(1,10);

         new Viag(i, n<6, d, Long.parseLong(args[0]), Long.parseLong(args[1])).start();
         Util.rsleep(100L,500L);
      }
   }
}
