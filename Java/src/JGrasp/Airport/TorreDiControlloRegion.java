package Airport;

import os.Util;
import os.Region;
import os.RegionCondition;

public class TorreDiControlloRegion extends Airport
{
   private Region r = new Region(0);
   private boolean prenotAtt=false;
   
   public TorreDiControlloRegion()
   {}
 
   public void richAccessoPista(int num)
   {
      r.enterWhen();
      
      wait_pista++;
      stampaSituazioneAeroporto();
      
      r.leave();
   
   
      r.enterWhen(new RegionCondition(){
         public boolean evaluate()
         {
            return freeA>0 && !prenotAtt && wait_atterraggi==0;
         }
      });
      
      freeA--;
      wait_pista++;
      stampaSituazioneAeroporto();
      
      r.leave();
   }
   
   public void richAutorizDecollo(int num)
   {
      r.enterWhen();
      
      wait_decolli++;
      stampaSituazioneAeroporto();
      
      r.leave();
      

      r.enterWhen(new RegionCondition(){
         public boolean evaluate()
         {
            return freeB>0;
         }
      });
      
      freeA++;
      freeB--;
      wait_decolli--;
      stampaSituazioneAeroporto();
      
      r.leave();
   }
   
   public void inVolo(int num)
   {
      r.enterWhen();
      
      freeB++;
      satisfiedD++;
      stampaSituazioneAeroporto();
      
      r.leave();
   }
   
   public void richAutorizAtterraggio(int num)
   {
      r.enterWhen();
      
      wait_atterraggi++;
      stampaSituazioneAeroporto();
      
      r.leave();
   
   
      r.enterWhen(new RegionCondition(){
         public boolean evaluate()
         {
            return freeA==2 && freeB==2;
         }
      });
      
      prenotAtt=true;
      freeA=0;
      wait_atterraggi--;
      stampaSituazioneAeroporto();
      
      r.leave();
   }
   
   public void freniAttivati(int num)
   {
      r.enterWhen();
      
      freeA=2;
      freeB=0;
      stampaSituazioneAeroporto();
      
      r.leave();
   }
   
   public void inParcheggio(int num)
   {
      r.enterWhen();
      
      freeB=2;
      prenotAtt=false;
      satisfiedA++;
      stampaSituazioneAeroporto();
      
      r.leave();
   }
   
   public static void main(String[] args)
   {
      Airport tc = new TorreDiControlloRegion();
      int i=0;
      
      for(; i<AEREI; i++)
      {
         int choice = Util.randVal(0,9);
         
         if(choice < 5)
         {
            new AereoCheDecolla(i,tc).start();
         }
         else
         {
            new AereoCheAtterra(i,tc).start();
         }
         
         Util.rsleep(200,400);
      }
      
      System.out.println(i);
   }
}