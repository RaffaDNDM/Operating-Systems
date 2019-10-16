package Desks;

import os.Region;
import os.RegionCondition;
import os.Util;

public class DesksRegion extends Desks
{
   private Region r = new Region(0);
 
   public DesksRegion()
   {
      for(int i=0; i<DESKS; desks[i++]=true);
   }

   public int entraCoda()
   { 
      r.enterWhen();
         wait_clients++;
      r.leave();
    
      r.enterWhen(new RegionCondition()
      {
         public boolean evaluate()
         {
            return freeDesks>0;
         }
      });

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

      r.leave();
      return i;
   }
   
   public int esce(int sport)
   {
      r.enterWhen();
      freeDesks++;
      desks[sport]=true;
      done_clients++;
      
      System.out.println("Il numero di clienti serviti: "+done_clients);

      r.leave();
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
      DesksRegion d = new DesksRegion();
      
      for(int i=0; i<CLIENTS; i++)
      {
         d.new Client(i).start();
         Util.rsleep(100, 200);
      }
   }
}