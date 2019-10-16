package CarWashing;

import os.Region;
import os.RegionCondition;
import os.Util;

public class CarWashingRegion extends CarWashing
{
   private Region r = new Region(0);

   /**********LAVAGGIO ESTERNI***********/
   public void prenotaParziale()
   {
      r.enterWhen();
         wait_par++;
         stampaSituazioneLavaggio();
      
      r.leave();

      r.enterWhen(new RegionCondition(){
         public boolean evaluate()
         {
            return freeA>0 && (wait_tot==0 || (wait_tot>0 && freeB==0)); 
         }
      });
      
         wait_par--;
         freeA--;   
         stampaSituazioneLavaggio();
      
      r.leave();
   }
   
   public void pagaParziale()
   {
      r.enterWhen();
      
      freeA++;
      done_par++;
      stampaSituazioneLavaggio();
      
      r.leave();
   }
   
   /**********LAVAGGIO INTERNI***********/
   public void prenotaTotale()
   {
      r.enterWhen();
         wait_tot++;
         stampaSituazioneLavaggio();
      r.leave();
   
      r.enterWhen(new RegionCondition()
      {
         public boolean evaluate()
         {
            return freeA>0 && freeB>0;
         }   
      });
      
         wait_tot--;
         freeA--;
         freeB--;
         stampaSituazioneLavaggio();
      
      r.leave();
   }
   
   public void lavaInterno()
   {
      r.enterWhen();
         freeA++;
         stampaSituazioneLavaggio();
      r.leave();
   }
   
   public void pagaTotale()
   {
      r.enterWhen();
         freeB++;
         done_tot++;
         stampaSituazioneLavaggio();
      r.leave();
   }

   public static void main(String[] args)
   {
      CarWashing cw = new CarWashingRegion();
      
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