/**
@author Di Nardo Di Maio Raffaele
*/

package DropOff;

import os.Util;
import os.Region;
import os.RegionCondition;

public class DropRegion extends DropOff
{
   private Region r = new Region(0);

   public DropRegion()
   {
      pLib[0]=true;
      pLib[1]=true;
   }

   public int inCoda(boolean prio)
   {
      int pos;

      if(prio)
      {
         r.enterWhen();
            wprio++;
         r.leave();

         r.enterWhen(new RegionCondition(){
            public boolean evaluate()
            {
               return punLib>0;
            }
         });

         wprio--;
         punLib--;

         pos = pLib[0]?0:1;
         pLib[pos] = false;

         r.leave();
      }
      else
      {
         r.enterWhen();
            wnorm++;
         r.leave();

         r.enterWhen(new RegionCondition(){
            public boolean evaluate()
            {
               return punLib>0 && wprio==0;
            }
         });

         wnorm--;
         punLib--;

         pos = pLib[0]?0:1;
         pLib[pos] = false;

         r.leave();
      }

      return pos;
   }

   public void regok(int pos)
   {
      if(pos==1)
      {
         r.enterWhen(new RegionCondition(){
            public boolean evaluate()
            {
               return libCD;
            }
         });

         r.leave();
      }
      else
      {
         r.enterWhen();
            libCD = false;
         r.leave();
      }
   }

   public void term(int pos)
   {
      r.enterWhen();

            if(pos==0)
               libCD = true;

            punLib++;
            pLib[pos] = true;
            tot_num++;

            stampaSituazione();

      r.leave();
   }

   public static void main(String[] args)
   {
      DropOff d = new DropRegion();

      for(int i=0; i<NUM_BAG; i++)
      {
         int n = Util.randVal(1,10);

         new Viag(i, n<6, d, Long.parseLong(args[0]), Long.parseLong(args[1])).start();
         Util.rsleep(100L,500L);
      }
   }
}
