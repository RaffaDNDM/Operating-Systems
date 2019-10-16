package Airport;

import os.Util;
import os.Semaphore;

public class TorreDiControlloSemPriv1 extends Airport
{  
   private Semaphore m = new Semaphore(true);
   private Semaphore zonaA_A = new Semaphore(false);
   private Semaphore zonaA_D = new Semaphore(false);
   private Semaphore zonaB_D = new Semaphore(false);
   private boolean prenotAtt=false;
   
   
   public TorreDiControlloSemPriv1()
   {}
 
   public void richAccessoPista(int num)
   {
      m.p();

      wait_pista++;

      if(freeA==0 || ( wait_atterraggi>0 && prenotAtt))
      {
         m.v();
         zonaA_D.p();
      }
      
      wait_pista--;
      freeA--;
      
      m.v();
   }
   
   public void richAutorizDecollo(int num)
   {
      m.p();
      
      wait_decolli++;
      
      stampaSituazioneAeroporto();
      
      if(freeB==0 || prenotAtt)
      {
         zonaB_D.p();
      }
      
      freeB--;
      freeA++;
      wait_decolli--;
      
      stampaSituazioneAeroporto();
      
      if(wait_pista>0)
         zonaA_D.v();
        
      m.v();
   }
   
   public void inVolo(int num)
   {
      m.p();
   
      freeB++;
      satisfiedD++;
      
      stampaSituazioneAeroporto();
      
      if(freeB==2 && freeA==2 && wait_atterraggi>0)
         zonaA_A.v();
      else if(wait_decolli>0)
         zonaB_D.v();
     
      m.v();
   }
   
   public void richAutorizAtterraggio(int num)
   {
      m.p();
   
      wait_atterraggi++;
      
      stampaSituazioneAeroporto();
      
      if(freeA<2 || freeB<2)
      {
         m.v();
         zonaA_A.p();
      }
      
      prenotAtt=true;
      freeA=0;
      
      wait_atterraggi--;
      
      stampaSituazioneAeroporto();
      
      m.v();
   }
   
   public void freniAttivati(int num)
   {
      m.p();
   
      freeA=2;
      freeB=0;
      
      stampaSituazioneAeroporto();
      
      m.v();
   }
   
   public void inParcheggio(int num)
   {
      m.p();
   
      freeB=2;
      prenotAtt = false;
      satisfiedA++;
      
      stampaSituazioneAeroporto();
      
      if(wait_atterraggi>0)
         zonaA_A.v();
      else if(wait_pista>0)
         zonaA_D.v();
      
      m.v();
   }
   
   public static void main(String[] args)
   {
      Airport tc = new TorreDiControlloSemPriv1();
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