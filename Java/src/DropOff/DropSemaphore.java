/**
@author Di Nardo Di Maio Raffaele
*/

package DropOff;

import os.Util;
import os.Semaphore;

public class DropSemaphore extends DropOff
{
   private Semaphore m = new Semaphore(true);
   private Semaphore queuePrio = new Semaphore(false);
   private Semaphore queueNorm = new Semaphore(false);
   private Semaphore queueCD = new Semaphore(false);

   public DropSemaphore()
   {
      pLib[0]=true;
      pLib[1]=true;
   }

   public int inCoda(boolean prio)
   {
      int pos;

      m.p();

      if(prio)
      {
         if(punLib==0)
         {
            m.v();
            queuePrio.p();
         }
      }
      else
      {
         if(punLib==0 || queuePrio.queue()>0)
         {
            m.v();
            queueNorm.p();
         }
      }

      punLib--;
      pos = pLib[0]?0:1;
      pLib[pos]=false;

      m.v();

      return pos;
   }

   public void regok(int pos)
   {
      m.p();

      if(pos==1)
      {
         if(!libCD)
         {
            m.v();
            queueCD.p();
         }
      }
      else
         libCD = false;

      m.v();
   }

   public void term(int pos)
   {
      m.p();

      if(pos==0)
         libCD = true;

      punLib++;
      pLib[pos]=true;
      tot_num++;
      wprio=queuePrio.queue();
      wnorm=queueNorm.queue();
      stampaSituazione();

      if(queueCD.queue()>0)
         queueCD.v();
      else if(queuePrio.queue()>0)
         queuePrio.v();
      else if(queueNorm.queue()>0)
         queueNorm.v();

      m.v();
   }

   public static void main(String[] args)
   {
      DropOff d = new DropSemaphore();

      for(int i=0; i<NUM_BAG; i++)
      {
         int n = Util.randVal(1,10);

         new Viag(i, n<6, d, Long.parseLong(args[0]), Long.parseLong(args[1])).start();
         Util.rsleep(100L,500L);
      }
   }
}
