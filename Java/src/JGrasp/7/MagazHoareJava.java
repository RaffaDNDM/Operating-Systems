/**
* @author Di Nardo Di Maio Raffaele
*/

import os.Monitor;
import os.Monitor.Condition;
import os.Util;
import os.Timeout;

public class MagazHoareJava
{
   private final static int NUMPOS = 3;
   private final static long MAXATT = 1000L;
   
   private final static long SCARTIM = 3000L;
   private final static int MAXPARC = 5;
   private final static int NUM_CARRELLI = 30;

   private Monitor m = new Monitor();
   private Condition att = m.new Condition();
   private Parcheggio parc = new Parcheggio();

   //variabili di stato
   private int posLib = NUMPOS;
   private boolean lib[] = new boolean[NUMPOS];

   public MagazHoareJava()
   {
      for(int i=0; i<NUMPOS; i++)
      {
         lib[i]=true;
      }
   }

   public int attAut(long timeout)
   {
      m.mEnter();

      long n = MAXATT;

      if (posLib == 0 || parc.num() > MAXPARC)
      {
         n = att.cWait(timeout);
      }
      
      int pos=0;

      if(n==Timeout.EXPIRED)
      {
         pos = -1;   
      }
      else
      {
         posLib--;

         for(int i=0; i<NUMPOS; i++)
         {
            if(lib[i])
            {
               lib[i]=false;
               pos = i;
               break;
            }
         }
      }
      
      m.mExit();
      return pos;
   }

   public void esce(int pos)
   {
      m.mEnter();
   
      lib[pos]=true;
      posLib++;

      if(parc.num()>MAXPARC)
         parc.outParc(pos);
      else if(att.queue()>0)
         att.cSignal();
      else if(parc.num()>0)
         parc.outParc(pos);
      
      m.mExit();
   }

   private class Parcheggio
   {
      private int num;
      private int ticketParc = 0;
      private int serviceParc = 0;
      private int nextPos=-1;

      public Parcheggio()
      {
         this.num=0;
      }

      public synchronized int num()
      {
         return num;
      }

      public synchronized int inParc()
      {
         num++;
         int ticket=ticketParc++;

         while(nextPos==-1 || ticket!=serviceParc)
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

         serviceParc++;
         num--;
         posLib--;

         int i=nextPos;
         lib[i]=false;
         nextPos = -1;
         return i;
      }

      public synchronized void outParc(int pos)
      {
         nextPos = pos;
         notifyAll();
      }
   }

   public static void main (String[] args)
   {
      MagazHoareJava m = new MagazHoareJava();

      for(int i=0; i<NUM_CARRELLI; i++)
      {
         m.new Carrello(i).start();
         long min= Integer.parseInt(args[0]);
         long max= Integer.parseInt(args[1]);
         Util.rsleep(min, max);
      }
   }
}
