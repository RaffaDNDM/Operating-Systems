package CarWashing;

import os.Monitor;
import os.Monitor.Condition;
import os.Util;

public class CarWashingMonitor extends CarWashing
{
   Monitor m = new Monitor();
   Condition tot = m.new Condition();
   Condition par = m.new Condition();

   /**********LAVAGGIO ESTERNI***********/
   public void prenotaParziale()
   {
      m.mEnter();
   
      wait_par++;
      stampaSituazioneLavaggio();
      
      if(freeA==0 || (wait_tot>0 && freeA>0 && freeB>0))
         par.cWait();
         
      wait_par--;
      freeA--;
      stampaSituazioneLavaggio();
      
      m.mExit();
   }
   
   public void pagaParziale()
   {
      m.mEnter();
      
      done_par++;
      freeA++;
      
      stampaSituazioneLavaggio();
      
      if(wait_tot==0 && wait_par>0)
         par.cSignal();         
      
      m.mExit();
   }
   
   /**********LAVAGGIO INTERNI***********/
   public void prenotaTotale()
   {
      m.mEnter();
      
      wait_tot++;
      stampaSituazioneLavaggio();
      
      if(freeA==0 || freeB==0)
         tot.cWait();   
      
      wait_tot--;
      freeA--;
      freeB--;
      stampaSituazioneLavaggio();
      
      m.mExit();
   }
   
   public void lavaInterno()
   {
      m.mEnter();
      
      freeA++;
      stampaSituazioneLavaggio();
      
      if(wait_tot==0 && wait_par>0)
         par.cSignal();
      
      m.mExit();
   }
   
   public void pagaTotale()
   {
      m.mEnter();
      
      freeB++;
      done_tot++;
      stampaSituazioneLavaggio();
      
      if(wait_tot>0 && freeA!=0)
         tot.cSignal();
      
      m.mExit();
   }

   public static void main(String[] args)
   {
      CarWashing cw = new CarWashingMonitor();
      
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