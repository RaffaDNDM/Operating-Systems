package CarWashing;

import os.Semaphore;
import os.Util;

public class CarWashingSemaphore extends CarWashing
{
   private Semaphore m = new Semaphore(true);
   private Semaphore tot = new Semaphore(false);
   private Semaphore par = new Semaphore(false);

   /**********LAVAGGIO ESTERNI***********/
   public void prenotaParziale()
   {
      m.p();
      
      wait_par++;
      stampaSituazioneLavaggio();
      
      if(freeA==0 || (wait_tot>0 && freeB>0))
      {
         m.v();
         par.p();
      }
         
      wait_par--;
      freeA--;
      stampaSituazioneLavaggio();
      
      m.v();
   }
   
   public void pagaParziale()
   {
      m.p();
      
      done_par++;
      freeA++;
      stampaSituazioneLavaggio();
      
      if(wait_tot>0 && freeB>0)
         tot.v();
      else if(wait_par>0)
         par.v();
         
      m.v();
   }
   
   /**********LAVAGGIO INTERNI***********/
   public void prenotaTotale()
   {
      m.p();
      
      wait_tot++;
      stampaSituazioneLavaggio();
      
      if(freeA==0 || freeB==0)
      {
         m.v();
         tot.p();
      }
      
      wait_tot--;
      freeA--;
      freeB--;
      stampaSituazioneLavaggio();
      
      m.v();
   }
   
   public void lavaInterno()
   {
      m.p();
      
      freeA++;
      stampaSituazioneLavaggio();
      
      if(wait_par>0)
         par.v();
      
      m.v();
   }
   
   public void pagaTotale()
   {
      m.p();
      
      freeB++;
      done_tot++;
      stampaSituazioneLavaggio();
      
      if(wait_tot>0 && freeA>0)
         tot.v();
      
      m.v();
   }
   
   public static void main(String[] args)
   {
      CarWashing cw = new CarWashingSemaphore();
      
      for(int i=0; i<CARS; i++)
      {
         int choice = Util.randVal(1,10);
         
         if(choice<5)
            new VeicoloTotale(i, cw).start();
         else
            new VeicoloParziale(i, cw).start();
            
         Util.rsleep(100L, 200L);
      }
   }
}