package Crossroad;

import os.Semaphore;
import os.Util;

public class CrossroadSemaphore extends Crossroad
{
   Semaphore m = new Semaphore(true);
   Semaphore queueNS = new Semaphore(false);
   Semaphore queueEW = new Semaphore(false);

   public void entra(boolean NS)
   {
      m.p();
      
      if(NS)
      {
         if(free==0 || (!turnNS && queueEW.queue()>0))
         {
            m.v();
            queueNS.p();
         }
         
         free--;
         max_dir--;
         
         if(max_dir==0)
         {
            max_dir=5;
            turnNS = false;
         }
      }
      else
      {
         if(free==0 || (turnNS && queueNS.queue()>0))
         {
            m.v();
            queueEW.p();
         }
         
         free--;
         max_dir--;
         
         if(max_dir==0)
         {
            max_dir=5;
            turnNS = true;
         }
      }
      
      m.v();  
   }
   
   public void esce(boolean NS)
   {
      m.p();
      
      free++;
      
      if(NS)
         done_NS++;
      else
         done_EW++;
         
      if(turnNS && queueNS.queue()>0)
         queueNS.v();
      else if(!turnNS && queueEW.queue()>0)
         queueEW.v();
      else if(queueNS.queue()>0)
         queueNS.v();
      else if(queueEW.queue()>0)
         queueEW.v();
         
      stampaSituazione();
      
      m.v();
   }
   
   public static void main(String[] args)
   {
      Crossroad cs = new CrossroadSemaphore();
      
      for(int i=0; i<CARS; i++)
      {
         new Auto(i, cs).start();
         Util.rsleep(Long.parseLong(args[0]), Long.parseLong(args[1]));
      }
   }
}