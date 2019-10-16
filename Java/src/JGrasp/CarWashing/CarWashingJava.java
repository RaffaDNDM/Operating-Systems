package CarWashing;

import os.Util;

public class CarWashingJava extends CarWashing
{
   private int ticketTot = 0;
   private int serviceTot = 0;
   private int ticketPar = 0;
   private int servicePar = 0;

   /**********LAVAGGIO ESTERNI***********/
   public synchronized void prenotaParziale()
   {
      int ticket = ticketPar++;
      wait_par++;
      
      stampaSituazioneLavaggio();
      
      while(ticket!=servicePar || freeA==0 || (wait_tot>0 && freeA>0 && freeB>0))
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
      
      wait_par--;
      freeA--;
      servicePar++;
      
      stampaSituazioneLavaggio();
   }
   
   public synchronized void pagaParziale()
   {
      freeA++;
      done_par++;
      
      stampaSituazioneLavaggio();
      notifyAll();
   }
   
   /**********LAVAGGIO INTERNI***********/
   public synchronized void prenotaTotale()
   {
      int ticket = ticketTot++;
      wait_tot++;
   
      stampaSituazioneLavaggio();
         
      while(ticket!=serviceTot || freeA==0 || freeB==0)
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
      
      wait_tot--;
      freeA--;
      freeB--;
      serviceTot++;
      
      stampaSituazioneLavaggio();
   }
   
   public synchronized void lavaInterno()
   {
      freeA++;
      stampaSituazioneLavaggio();
   
      notifyAll();
   }
   
   public synchronized void pagaTotale()
   {
      freeB++;
      done_tot++;
      stampaSituazioneLavaggio();
   
      notifyAll();
   }
   
   public static void main(String[] args)
   {
      CarWashing cw = new CarWashingJava();
      
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