package GestioneCarrelli;

import os.Region;
import os.RegionCondition;
import os.Util;

public  class SmistaReg extends Smista
{  
   private Region r = new Region(0);
   
   public int instrada(final int i)
   { 
      int ticket_in;
      int elevatore;
      
      r.enterWhen();
         ticket_in=ticket[i]++;
         enqueued[i]++;
         stampaSituazione();
      r.leave();
      
      
      r.enterWhen(new RegionCondition(){
         public boolean evaluate()
         {
            return ticket_in==serv[i] && I1 && (B[1] || B[0]) && C[i];
         }
      });
      
         enqueued[i]--;
         serv[i]++;
         I1=false;
         elevatore = B[0]?0:1;
         B[elevatore]=false;
         stampaSituazione();
         
      r.leave();
      
      return elevatore;
   }
   
   public void outI1(int h)
   { 
      Util.sleep(I1ATT);
      
      r.enterWhen();
      
      I1=true;
      freeB--;
      stampaSituazione();
      
      r.leave();
   }
   
   public int outB(int h)
   { 
      int exit;
      Util.rsleep(BMIN, BMAX);
   
      r.enterWhen(new RegionCondition(){
         public boolean evaluate()
         {
            return I2;
         }
      });
      
         freeB++;
         B[h]=true;
         I2=false;
         exit = ((Carrello) Thread.currentThread()).i;
         stampaSituazione();
      
      r.leave();
      return exit;
   }
   
   public void inC(int i)
   { 
      Util.sleep(I2ATT);
   
      r.enterWhen();
      
         I2=true;
         freeC--;
         C[i]=false;
         stampaSituazione();
      
      r.leave();
   }
   
   public void outC(int i)
   {
      Util.rsleep(CMIN, CMAX);
    
      r.enterWhen();
      
         C[i]=true;
         served[i]++;
         freeC++;
         stampaSituazione();
      
      r.leave();
   }
   
   public static void main(String[] args)
   {
      Smista s = new SmistaReg();
   
      for(int i=0; i<CARRELLI; i++)
      {
         new Carrello(i, s).start();
         
         long min=Long.parseLong(args[0]);
         long max=Long.parseLong(args[1]);
         
         Util.rsleep(min,max);
      }
   }
}