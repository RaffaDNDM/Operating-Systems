package Desks;

import os.Semaphore;
import os.Util;

public class DesksSemaphore extends Desks
{
   private Semaphore m = new Semaphore(true);
   private Semaphore queue = new Semaphore(false);
 
   public DesksSemaphore()
   {
      for(int i=0; i<DESKS; desks[i++]=true);
   }

   public int entraCoda()
   {  
      m.p();
      
      wait_clients++;
   
      if(freeDesks==0)
      {   
         m.v();
         queue.p();
      }
      
      wait_clients--;
      freeDesks--;
      
      int i=0;
      
      for(; i<DESKS; i++)
      {
         if(desks[i])
         {
            desks[i]=false;
            break;
         }
      }
   
      m.v();
      return i;
   }
   
   public int esce(int sport)
   {
      m.p();
      freeDesks++;
      desks[sport]=true;
      done_clients++;
      
      System.out.println("Il numero di clienti serviti: "+done_clients);
      
      if(wait_clients>0)
         queue.v();
   
      m.v();
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
      DesksSemaphore d = new DesksSemaphore();
      
      for(int i=0; i<CLIENTS; i++)
      {
         d.new Client(i).start();
         Util.rsleep(100, 200);
      }
   }
}