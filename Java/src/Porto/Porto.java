/**
@author Di Nardo Di Maio Raffaele
*/

package Porto;

import os.Monitor;
import os.Monitor.Condition;
import os.Util;

public class Porto
{
   private final static long UNIT = 200;

   private class Controllo extends Monitor
   {
      private Condition c = new Condition();
      private final static int NUM_BANCHINE = 4;
      private int free_banchine = NUM_BANCHINE;
      private Condition in = new Condition();
      private Condition out = new Condition();
      private Condition rimor = new Condition();
      private Condition traino = new Condition();
      private boolean free_rim = false;
      private int boat_in = 0;
      private int boat_out = 0;

      public void attesaIngresso()
      {
         mEnter();

         if(free_banchine == 0 || free_rim)
         {
            boat_in++;
            in.cWait();
         }
         else
         {
            free_banchine--;
            rimor.cSignal();
         }

         mExit();
      }

      public void ingresso()
      {
         mEnter();
         traino.cWait();
         mExit();
      }

      public void attesaUscita()
      {
         mEnter();

         if(!free_rim)
         {
            boat_out++;
            out.cWait();
         }
         else
         {
            free_banchine++;
            rimor.cSignal();
         }

         mExit();
      }

      public void uscita()
      {
         mEnter();
         traino.cWait();
         mExit();
      }

      public void servizio()
      {
         mEnter();

         if((free_banchine==0 || boat_in==0) && boat_out==0)
         {
            free_rim = true;
            //attende che ci sia una nave che richiede il servizio
            rimor.cWait();
            free_rim = false;
         }
         else if(free_banchine>0 && boat_in>0)
         {
            boat_in--;
            free_banchine--;
            in.cSignal();
         }
         else
         {
            boat_out--;
            free_banchine++;
            out.cSignal();
         }

         mExit();
         return;
      }

      public void fineServizio()
      {
         mEnter();
         traino.cSignal();
         mExit();
      }
   }

   private Controllo c = new Controllo();

   private class Nave extends Thread
   {
      private int id;

      public Nave(int id)
      {
         this.id = id;
      }

      public void run()
      {
         while(true)
         {
            c.attesaIngresso();
            c.ingresso();
            Util.rsleep(60*UNIT, 100*UNIT);
            c.attesaUscita();
            c.uscita();
            Util.rsleep(2000*UNIT, 3000*UNIT);
         }
      }
   }

   private class Rimorchiatore extends Thread
   {
      public void run()
      {
         while(true)
         {
            c.servizio();
            Util.sleep(30*UNIT);
            c.fineServizio();
         }
      }
   }

   public static void main(String[] args)
   {
      Porto p = new Porto();
      p.new Rimorchiatore().start();

      for(int i=0; i<Integer.parseInt(args[0]); i++)
      {
         Util.rsleep(500, 6000);
         p.new Nave(i).start();
      }
   }
}
