/**
@author Di Nardo Di Maio Raffaele
*/

package NCantoni;

import os.Monitor;
import os.Monitor.Condition;
import os.Util;
import os.Timeout;

public class NCantoni
{
   private final static long UNIT = 1000;
   private final static long ATTMAX = 8;
   private final static int NUMPOS = 7;
   private final static int NUMB = 5;
   private Monitor m = new Monitor();
   private Condition[] postazioni;
   //private boolean[] prenotati;
   private boolean[] post_libere;
   private int[] wait_num;
   private int n;
   private long tsMin;
   private long tsMax;
   private long attMax;

   public NCantoni(int n, long tsMin, long tsMax, long attMax)
   {
      this.n = n;
      this.tsMin = UNIT*tsMin;
      this.tsMax = UNIT*tsMax;
      this.attMax = UNIT*attMax;
      postazioni = new Condition[n];
      //prenotati = new boolean[n];
      post_libere = new boolean[n];
      wait_num = new int[n];

      for(int i=0; i<n; i++)
      {
         postazioni[i] = m.new Condition();
         //prenotati[i] = false;
         post_libere[i] = true;
         wait_num[i] = 0;
      }
   }

   public int muovi(int parte, int arriva)
   {
      int dest = arriva;

      m.mEnter();

      wait_num[arriva]++;
      printState();

      if(!post_libere[arriva])
      {
         long timeout = postazioni[arriva].cWait(attMax);

         if(timeout == Timeout.EXPIRED)
         {
            for(int i=0; i<n; i++)
            {
               if(post_libere[i])
               {
                  dest = i;
                  break;
               }
            }
         }
      }

      wait_num[arriva]--;
      post_libere[dest]=false;
      post_libere[parte]=true;
      printState();

      if(wait_num[parte]>0)
         postazioni[parte].cSignal();

      m.mExit();

      return arriva;
   }

   private class Bambino extends Thread
   {
      private int initial;
      private int id;
      private long tMov;

      public Bambino(int initial, long tMov)
      {
         this.initial = initial;
         this.id = initial;
         this.tMov = UNIT*tMov;
         post_libere[initial] = false;
      }

      public void run()
      {
         while(true)
         {
            Util.rsleep(tsMin, tsMax);
            int dest = initial;

            while(initial == dest)
            {
               dest = Util.randVal(0, n-1);
            }

            initial = muovi(initial, dest);
            Util.sleep(tMov);
         }
      }
   }

   public void printState()
   {
      System.out.println("--------------------------------------------------------");
      System.out.println("                In attesa alla stazione");

      String s = "";
      for(int i=0; i<n; i++)
      {
         s+=("  "+i+": "+wait_num[i]);
      }

      System.out.println(s);
      System.out.println("--------------------------------------------------------");
      System.out.println("                Stazioni libere");

      s = "";
      for(int i=0; i<n; i++)
      {
         String state = post_libere[i]?"T":"F";
         s+=("  "+i+": "+state);
      }

      System.out.println(s);
      System.out.println("--------------------------------------------------------");
   }

   public static void main(String[] args)
   {
      NCantoni nc = new NCantoni(NUMPOS, Long.parseLong(args[0]), Long.parseLong(args[1]), ATTMAX);

      Bambino[] b = new Bambino[NUMB];

      for(int i=0; i<NUMB; i++)
      {
         b[i] = nc.new Bambino(i, Util.randVal(2,4));
      }

      for(int i=0; i<NUMB; b[i++].start());
   }
}
