/**
@author Di Nardo Di Maio Raffaele
*/

package DropOff;

import os.Monitor;
import os.Monitor.Condition;
import os.Util;

public class DropMonitor extends DropOff
{
   private Monitor m = new Monitor();
   private Condition queuePrio = m.new Condition();
   private Condition queueNorm = m.new Condition();
   private Condition queueCD = m.new Condition();

   public DropMonitor()
   {
      pLib[0]=true;
      pLib[1]=true;
   }

   public int inCoda(boolean prio)
   {
      int pos;

      m.mEnter();

         if(prio)
         {
            if(punLib==0)
               queuePrio.cWait();

         }
         else
         {
            if(punLib==0)
               queueNorm.cWait();
         }

         punLib--;
         pos = pLib[0]? 0:1;
         pLib[pos] = false;

      m.mExit();

      return pos;
   }

   public void regok(int pos)
   {
      m.mEnter();

      if(pos==1)
      {
         if(!libCD)
            queueCD.cWait();
      }
      else
         libCD=false;


      m.mExit();
   }

   public void term(int pos)
   {
      m.mEnter();

      punLib++;
      pLib[pos]=true;
      tot_num++;
      wprio=queuePrio.queue();
      wnorm=queueNorm.queue();
      stampaSituazione();

      if(queueCD.queue()>0)
         queueCD.cSignal();
      else if(queuePrio.queue()>0)
         queuePrio.cSignal();
      else if(queueNorm.queue()>0)
         queueNorm.cSignal();

      m.mExit();
   }

   public static void main(String[] args)
   {
      DropOff d = new DropMonitor();

      for(int i=0; i<NUM_BAG; i++)
      {
         int n = Util.randVal(1,10);

         new Viag(i, n<6, d, Long.parseLong(args[0]), Long.parseLong(args[1])).start();
         Util.rsleep(100L,500L);
      }
   }
}
