package Crossroad;

import os.Semaphore;
import os.Util;

public class CrossroadJava extends Crossroad
{
   private int wait_NS = 0; // macchine in attesa lungo la direzione NS
   private int wait_EW = 0; // macchine in attesa lungo la direzione EW
   private int ticketNS = 0; // ticket per gestire la direzione NS
   private int ticketEW = 0; // ticket per gestire lungo la direzione EW
   private int serviceNS = 0; // ticket dell'auto che deve passare lungo la direzione NS
   private int serviceEW = 0; // ticket dell'auto che deve passare lungo la direzione EW

   public synchronized void entra(boolean NS)
   {
      if(NS)
      {
         int ticket = ticketNS++;
         wait_NS++;
      
         while(ticket!=serviceNS || free==0 || (!turnNS && wait_EW>0))
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
         
         serviceNS++;
         wait_NS--;
      
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
         int ticket = ticketEW++;
         wait_EW++;
         
         while(ticket!=serviceEW || free==0 || (turnNS && wait_NS>0))
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
         
         serviceEW++;
         wait_EW--;
      
         free--;
         max_dir--;
         
         if(max_dir==0)
         {
            max_dir=5;
            turnNS = true;
         }
      }
   }
   
   public synchronized void esce(boolean NS)
   {
      free++;
      
      if(NS)
         done_NS++;
      else
         done_EW++;
         
      stampaSituazione();
      notifyAll();
   }
   
   public static void main(String[] args)
   {
      Crossroad cs = new CrossroadJava();
      
      for(int i=0; i<CARS; i++)
      {
         new Auto(i, cs).start();
         Util.rsleep(Long.parseLong(args[0]), Long.parseLong(args[1]));
      }
   }
}   