package Crossroad;

import os.Region;
import os.RegionCondition;
import os.Util;

public class CrossroadRegion extends Crossroad
{
   private int wait_NS = 0; // macchine in attesa lungo la direzione NS
   private int wait_EW = 0; // macchine in attesa lungo la direzione EW
   private Region r = new Region(0);

   public void entra(boolean NS)
   {
      if(NS)
      {
         r.enterWhen();
            wait_NS++;
         r.leave();
      
         r.enterWhen(new RegionCondition(){
            public boolean evaluate()
            {
               return free>0 && (turnNS || wait_EW==0);
            }
         });
         
         wait_NS--;
         free--;
         max_dir--;
         
         if(max_dir==0)
         {
            max_dir = 5;
            turnNS = false;
         }
         
         r.leave();
      }
      else
      {
         r.enterWhen();
            wait_EW++;
         r.leave();
      
         r.enterWhen(new RegionCondition(){
            public boolean evaluate()
            {
               return free>0 && (!turnNS || wait_NS==0);
            }
         });
         
            wait_EW--;
            free--;
            max_dir--;
            
            if(max_dir==0)
            {
               max_dir = 5;
               turnNS = true;
            }   
         
         r.leave();
      }  
   }
   
   public void esce(boolean NS)
   {
      r.enterWhen();
      
      free++;
      
      if(NS)
         done_NS++;
      else
         done_EW++;
   
      stampaSituazione();
   
      r.leave();
   }
   
   public static void main(String[] args)
   {
      Crossroad cs = new CrossroadRegion();
      
      for(int i=0; i<CARS; i++)
      {
         new Auto(i, cs).start();
         Util.rsleep(Long.parseLong(args[0]), Long.parseLong(args[1]));
      }
   }
}   