package Desks;

import os.Util;

public class DesksJava extends Desks
{
   private int ticket = 0;
   private int service = 0;
   
   public DesksJava()
   {
      for(int i=0; i<DESKS; desks[i++]=true);
   }
 
   public synchronized int entraCoda()
   {
      int my_ticket = ticket++;
      wait_clients++;
      
      while(freeDesks==0 || my_ticket!=service)
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
      
      service++;
      freeDesks--;
      wait_clients--;
      
      int i=0;
      
      for(; i<DESKS; i++)
      {
         if(desks[i])
         {
            desks[i]=false;
            break;   
         }
      }
      
      return i;
   }
   
   public synchronized int esce(int sport)
   {
      freeDesks++;
      done_clients++;
      desks[sport]=true;
      
      System.out.println("Il numero di clienti serviti: "+done_clients);
      
      notifyAll();
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
      DesksJava d = new DesksJava();
      
      for(int i=0; i<CLIENTS; i++)
      {
         d.new Client(i).start();
         Util.rsleep(100, 200);
      }
   }
}