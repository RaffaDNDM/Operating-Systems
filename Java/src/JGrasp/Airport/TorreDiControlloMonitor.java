package Airport;

import os.Util;
import os.Monitor;
import os.Monitor.Condition;

public class TorreDiControlloMonitor extends Airport
{
   private Monitor m = new Monitor();
   private Condition zonaA_D = m.new Condition();
   private Condition zonaB_D = m.new Condition();
   private Condition zonaA_A = m.new Condition();
   
   public TorreDiControlloMonitor()
   {}
 
   public void richAccessoPista(int num)
   {
      m.mEnter();
   
      wait_pista++;
      
      stampaSituazioneAeroporto();
      
      if(freeA==0 || wait_atterraggi>0)
      {
         zonaA_D.cWait();
      }
      
      wait_pista--;
      freeA--;
      
      stampaSituazioneAeroporto();
      
      m.mExit();
   }
   
   public void richAutorizDecollo(int num)
   {
      m.mEnter();
      
      wait_decolli++;
      
      if(freeB==0)
      {
         zonaB_D.cWait();
      }
      
      wait_decolli--;
      freeA++;
      freeB--;
      
      if(wait_pista>0)
         zonaA_D.cSignal();
      
      stampaSituazioneAeroporto();
      
      m.mExit();
   }
   
   public void inVolo(int num)
   {
      m.mEnter();
   
      freeB++;
      satisfiedD++;
      
      stampaSituazioneAeroporto();
      
      if(freeA==2 && freeB==2 && wait_atterraggi>0)
         zonaA_A.cSignal();
      else if(wait_decolli>0)   
         zonaB_D.cSignal();
            
      m.mExit();
   }
   
   public void richAutorizAtterraggio(int num)
   {
      m.mEnter();
   
      wait_atterraggi++;
      
      stampaSituazioneAeroporto();
      
      if(freeA<2 || freeB<2)
      {
         zonaA_A.cWait();
      }
      
      wait_atterraggi--;
      freeA=0;
      freeB=0;
   
      stampaSituazioneAeroporto();
   
      m.mExit();
   }
   
   public void freniAttivati(int num)
   {
      m.mEnter();
   
      freeA=2;
   
      stampaSituazioneAeroporto();
      
      m.mExit();
   }
   
   public void inParcheggio(int num)
   {
      m.mEnter();
   
      freeB=2;
      satisfiedA++;
      
      stampaSituazioneAeroporto();
      
      if (wait_atterraggi>0)
         zonaA_A.cSignal();
      else if(wait_pista>0)
         zonaA_D.cSignal();
         
      m.mExit();
   }
   
   public static void main(String[] args)
   {
      Airport tc = new TorreDiControlloMonitor();
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