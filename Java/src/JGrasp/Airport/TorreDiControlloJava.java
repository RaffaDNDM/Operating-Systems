package Airport;

import os.Util;

public class TorreDiControlloJava extends Airport
{
   private static int ticketPista = 0;
   private static int ticketDecollo = 0;
   private static int ticketAtterraggio = 0;
   private static int servicePista = 0;
   private static int serviceDecollo = 0;
   private static int serviceAtterraggio = 0;

   public TorreDiControlloJava()
   {}
 
   public synchronized void richAccessoPista(int num)
   {
      int ticket = ticketPista++;
      wait_pista++;
      
      stampaSituazioneAeroporto();
      
      while(ticket!=servicePista || freeA == 0 || wait_atterraggi>0)
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
      
      freeA--;
      servicePista++;
      wait_pista--;
      
      stampaSituazioneAeroporto();
   }
   
   public synchronized void richAutorizDecollo(int num)
   {
      int ticket = ticketDecollo++;
      wait_decolli++;
      
      while(freeB==0 || ticket!=serviceDecollo)
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
      
      wait_decolli--;
      serviceDecollo++;
      
      freeA++;
      freeB--;
      
      stampaSituazioneAeroporto();
      
      notifyAll();
   }
   
   public synchronized void inVolo(int num)
   {
      freeB++;
      satisfiedD++;
   
      stampaSituazioneAeroporto();
      
      notifyAll();
   }
   
   public synchronized void richAutorizAtterraggio(int num)
   {
      int ticket = ticketAtterraggio++;
      wait_atterraggi++;
   
      while(ticket!=serviceAtterraggio || freeA!=2 || freeB!=2)
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
      
      wait_atterraggi--;
      serviceAtterraggio++;
      
      freeA=0;
      freeB=0;
   
      stampaSituazioneAeroporto();
   }
   
   public synchronized void freniAttivati(int num)
   {
      freeA=2;
      stampaSituazioneAeroporto();
      notifyAll();
   }
   
   public synchronized void inParcheggio(int num)
   {
      freeB=2;
      satisfiedA++;
      
      stampaSituazioneAeroporto();
      notifyAll();
   }
   
   public static void main(String[] args)
   {
      Airport tc = new TorreDiControlloJava();
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