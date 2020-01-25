/**
@author Di Nardo Di Maio Raffaele
*/

package GestioneMolo;

import os.Util;
import os.Semaphore;

public class MoloSem extends Molo
{
   private long tMin;
   private long tMax;
   private Semaphore mutex = new Semaphore(true);
   private Semaphore[] att = new Semaphore[2];
   private Semaphore[] stopGru = new Semaphore[2];
   private Semaphore[] servito = new Semaphore[3];
   private int tot_num;

   public MoloSem(long min, long max)
   {
      for(int i=0; i<3; i++)
      {
         puntoDiCarico[i]=true;
         servito[i]=new Semaphore(false);
      }
      for(int i=0; i<2; i++)
      {
         stopGru[i]= new Semaphore(false);
         att[i]= new Semaphore(false);
      }

      tMin = min;
      tMax = max;
   }

   public int entra(boolean richiestaA)
   {
      mutex.p();

      int punto=0;
      int i=0;

      if(richiestaA)
      {
         if(!puntoDiCarico[0])
         {
            truckD++;
            mutex.v();
            att[0].p();
            truckD--;
         }

         punto = 0;
         puntoDiCarico[punto]=false;
         mutex.v();
         stopGru[0].v();
      }
      else
      {
         if(!puntoDiCarico[1] && !puntoDiCarico[2])
         {
            truckS++;
            mutex.v();
            att[1].p();
            truckS--;
         }

         punto = puntoDiCarico[1]?1:2;
         puntoDiCarico[punto]=false;

         if(punto == 1 && puntoDiCarico[0])
         {
            mutex.v();
            stopGru[0].v();
         }
         else if(((!puntoDiCarico[0] || puntoDiCarico[2]) && punto==1) || punto==2)
         {
            mutex.v();
            stopGru[1].v();
         }
      }

      return punto;
   }


   public void esce(int punto)
   {
      mutex.p();

      puntoDiCarico[punto]=true;
      System.out.println("**** "+Thread.currentThread().getName()+" fine servizio su posto = "+punto);

      tot_num++;

      System.out.println("Numero totale di serviti: "+tot_num);

      if(punto==0 && truckD>0)
         att[0].v();
      else if(punto!=0 && truckS>0)
         att[1].v();
      else
         mutex.v();
   }

   private class Camion extends Thread
   {
      private boolean deperibile;
      private int num;

      public Camion(boolean deperibile, int num)
      {
         super("Camion "+num);
         this.deperibile = deperibile;
         this.num = num;
      }

      public void run()
      {
         System.out.println("Attivato "+getName());

         int punto = entra(deperibile);
         System.out.println("** "+Thread.currentThread().getName()+" inizia il servizio su posto="+punto);

         servito[punto].p();

         esce(punto);
      }
   }

   private class Gru extends Thread
   {
      private int num;
      private int left;
      private int right;

      public Gru(int num)
      {
         this.num = num;

         if(num==0)
         {
            left=0;
            right=1;
         }
         else if(num==1)
         {
            left=1;
            right=2;
         }
      }

      public void run()
      {
         while(true)
         {
            stopGru[num].p();

            for(int j=left; j<=right; j++)
            {
               mutex.p();

               if(!puntoDiCarico[j])
               {
                  mutex.v();
                  Util.rsleep(tMin, tMax);
                  servito[j].v();
                  break;
               }

               mutex.v();
            }

         }
      }
   }

   public static void main(String[] args)
   {
      long min = Long.parseLong(args[0]);
      long max = Long.parseLong(args[1]);

      MoloSem m = new MoloSem(min, max);

      m.new Gru(0).start();
      m.new Gru(1).start();

      for(int i=0; i<NUM_CAMION; i++)
      {
         int choice = Util.randVal(0,9);

         m.new Camion(choice<5, i).start();

         Util.rsleep(50L, 200L);
      }
   }
}
