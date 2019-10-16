package Parcheggio;

import java.util.Random;
import os.Util;

public class SalitaJava extends Salita
{
   public SalitaJava()
   {
      for(int i=0; i<NUM_PIANI; pren[i++]=0);
   }
   
   public synchronized void inCodaP(int piano) 
   {
      int ticket = ticketP++;
      prenP++;
      
      while(ticket != servP || num==CAP || !aperto || prenS!=0)
      {
         try
         {
            wait();
         }
         catch(InterruptedException e)
         {
            e.printStackTrace();
         }
      }
      
      servP++;
      num++;
      prenP--;
      
      pren[piano]++;
   }
   
   public synchronized void inCodaS(int piano) 
   {
      int ticket = ticketS++;
      prenS++;
      
      while(ticket != servS || num==CAP || !aperto)
      {
         try
         {
            wait();
         }
         catch(InterruptedException e)
         {
            e.printStackTrace();
         }
      }
      
      servS++;
      num++;
      prenS--;
      pren[piano]++;
   }
   
   public synchronized void entra(int piano) 
   {
      Util.sleep(ENTRATIM);
   
      while(piano != arrivato)
      {
         try
         {
            wait();
         }
         catch(InterruptedException e)
         {
            e.printStackTrace();
         }
      }
   }
   
   public synchronized int minPren(int pr[]) 
   {
      for(int i=0; i<NUM_PIANI; i++)
      {
         if(pren[i]!=0)
            return i;
      }
   
      return -1;
   }
   
   public void servizio() 
   {
      num=0;
      
      System.out.println("INIZIO SERVIZIO dell'ascensore");
      
      while(true)
      {
         synchronized(this)
         {
            notifyAll();
         }
         
         if(prenP+prenS==0 && num<CAP)
            Util.sleep(MAXATT);
         
         //ascensore pronto per salire
         if((prenP+prenS==0 && num!=0) || num==CAP)
            break;
      }
      
      aperto = false;
      int min;
      
      while(true)
      {
         if((min=minPren(pren)) == -1)
            break;
            
         Util.sleep((min-arrivato)*PIANOT+DISCESA);
         
         System.out.println("Sono scesi "+pren[min]+" utenti al piano "+(min+1));
         pren[min]=0;
         arrivato = min;
         
         synchronized(this)
         {
            notifyAll();
         }
      }
      
      System.out.println("FINE SERVIZIO dell'ascensore");
   }
   
   public void discesa() 
   {
      if(arrivato != 0)
      {
         Util.sleep(arrivato*PIANOT);
         
         arrivato = 0;
         aperto = true;
      }
      
   }
   
   private class Utente extends Thread
   {
      private int id;
      private int piano;
   
      public Utente(int id, int piano)
      {
         this.id = id;
         this.piano = piano-1;
      }
      
      public void run()
      {
         if(piano<=5)
            inCodaP(piano);
         else
            inCodaS(piano);
         
         entra(piano);   
      }
   }
   
   private class Ascensore extends Thread
   {
      public Ascensore()
      {}
      
      public void run()
      {
         for(;;)
         {
            servizio();
            discesa();
         }
      }
   }
   
   public static void main(String args[])
   {
      long start = Long.parseLong(args[0]);
      long end = Long.parseLong(args[1]);
      
      SalitaJava s = new SalitaJava();
      s.new Ascensore().start();
      
      for(int i=0; i<NUM_UTENTI; i++)
      {
         Util.rsleep(start,end);
         s.new Utente(i,Util.randVal(1,10)).start();
      }
   }
}

