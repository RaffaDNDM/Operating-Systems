package SingleDesk;

import os.Util;

public class SingleDeskJava extends SingleDesk
{
   private boolean notify = false;
   private int ticketPrio = 0;
   private int servicePrio = 0;
   private int ticketNorm = 0;
   private int serviceNorm = 0;

   public synchronized boolean entra(boolean priority)
   {
      if(priority)
      {
         int ticket = ticketPrio++;
         wait_prio++;
         
      
         while(ticket!=servicePrio || !free)
         {
            try
            {
               wait(3000L);
            }
            catch(InterruptedException e)
            {
               System.out.println("Interruzione");
            }
         
            if(!notify)
               return false;
         }
         
         notify=false;
         servicePrio++;
         wait_prio--;
         free = false;
      }
      else
      {
         int ticket = ticketNorm++;
         wait_norm++;
         
         while(ticket!=serviceNorm || !free)
         {
            try
            {
               wait();
            }
            catch(InterruptedException e)
            {
               System.out.println("Interruzione");
            }
         
            if(!notify)
               return false;
         }
         
         notify=false;
         serviceNorm++;
         wait_norm--;
         free = false;
      }
      
      return true;
   }
   
   public synchronized void esce(boolean priority, boolean noStufo)
   {
      if(noStufo)
      {
         free=true;
         
         if(priority)
            done_prio++;
         else
            done_norm++;
            
         notify=true;
         System.out.println("Clienti con priorita' serviti: "+done_prio);
         System.out.println("Clienti senza priorita' serviti: "+done_norm);
         System.out.println("Clienti con priorita' stufi: "+suspended_prio);
         System.out.println("Clienti senza priorita' stufi: "+suspended_norm);
      
         notifyAll();  
      }
      else
      {
         if(priority)
            suspended_prio++;
         else
            suspended_norm++;
      }
      
      System.out.println("Clienti con priorita' serviti: "+done_prio);
      System.out.println("Clienti senza priorita' serviti: "+done_norm);
      System.out.println("Clienti con priorita' stufi: "+suspended_prio);
      System.out.println("Clienti senza priorita' stufi: "+suspended_norm);
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
         System.out.println("Il cliente "+num+" viene attivato");
         int x = Util.randVal(1,10);
      
         boolean noWaitTerminated = entra(x<4);
         
         if(noWaitTerminated)
         {
            System.out.println("Il cliente "+num+" viene servito");
            Util.rsleep(1000L, 3000L);
            System.out.println("Il cliente "+num+" ultima il servizio");
            esce(x<4, true);
         }
         else
         {
            System.out.println("Il cliente "+num+" si è stufato");
            esce(x<4, false);
         }
      }
   }
   
   private class AtWork extends Thread
   {
      private long minS;
      private long maxS;
      private long minD;
      private long maxD;
   
      public AtWork(long minS, long maxS, long minD, long maxD)
      {
         this.minS = minS;
         this.maxS = maxS;
         this.minD = minD;
         this.maxD = maxD;
      }
      
      public void run()
      {
         while(true)
         {
            Util.rsleep(minS, maxS);
            
            synchronized(this)
            {
               if(free = true)
               free=false;
            
               notify=true;
               notifyAll();
            }
            
            System.out.println("Lo sportello e' fuori servizio");
            Util.rsleep(minD, maxD);
            
            synchronized(this)
            {
               free = true;
               notify=true;
               notifyAll();
            }
         }
      }
   }
   
   public static void main(String[] args)
   {
      SingleDeskJava sd = new SingleDeskJava();
      
      long s1 = Long.parseLong(args[0]);
      long s2 = Long.parseLong(args[1]);
      long d1 = Long.parseLong(args[2]);
      long d2 = Long.parseLong(args[3]);
      
      sd.new AtWork(s1, s2, d1, d2).start();
      
      for(int i=0; i<CLIENTS; i++)
      {
         sd.new Client(i).start();
         Util.rsleep(100L, 300L);
      }
   }
}