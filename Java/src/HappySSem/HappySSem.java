/**
@author Raffaele Di Nardo Di Maio
*/

package HappySSem;

import os.Semaphore;
import os.Util;

public class HappySSem
{
   private final static int NUM_TAVOLI = 10; //numero di tavoli del locale
   private final static int NUM_GROUPS = 20; //numero di gruppi che entrano nel locale
   private final static int NUM_TAVOLI4 = 6; //numero di tavoli da quattro posti

   private int[] posti = new int[NUM_TAVOLI];
   private long minT, maxT;

   private Semaphore mutex = new Semaphore(true);
   private Semaphore att65 = new Semaphore(false);
   private Semaphore att4 = new Semaphore(false);
   private Semaphore att3 = new Semaphore(false);
   private Semaphore att2 = new Semaphore(false);

   private int att65n = 0;
   private int att4n = 0;
   private int att3n = 0;
   private int att2n = 0;

   public HappySSem(long minT, long maxT)
   {
      this.minT = minT;
      this.maxT = maxT;

      for(int i=0; i<NUM_TAVOLI; i++)
      {
         posti[i]=(i<NUM_TAVOLI4)?4:6;
      }
   }

   /**
   Stampa situazione dei tavoli attuale
   */
   public void tavoli()
   {
      System.out.println("------------------------------------------------------------------------");
      System.out.printf("       wait56: %2d    wait4: %2d      wait3: %2d        wait2: %2d\n", att65n, att4n, att3n, att2n);
      System.out.println("------------------------------------------------------------------------");
      System.out.printf("Posti ");

      for(int i=0; i<NUM_TAVOLI; i++)
      {
         System.out.printf("%d->%d/%d<- ", i, posti[i],(i<NUM_TAVOLI4)?4:6);
      }

      System.out.println("");
   }


   /**
      individua se � possibile assegnare un tavolo al gruppo di n persone
      in base alle regole fissate
      @param n numero persone del gruppo che vogliono sedersi
      @return numero tavolo disponibile e idoneo (altrimenti -1)
   */
   public int libero(int n)
   {
      int tav=-1;

      if(n==6 || n==5)
      {
         for(tav=NUM_TAVOLI4; tav<NUM_TAVOLI; tav++)
         {
            if(posti[tav]==6)
               break;
         }
      }
      else if(n==3 || n==4)
      {
         for(tav=0; tav<NUM_TAVOLI; tav++)
         {
            if(tav<NUM_TAVOLI4 && posti[tav]==4)
               break;
            else if(tav>=NUM_TAVOLI4 && posti[tav]==n && att65n==0)
               break;
            else if(tav>=NUM_TAVOLI4 && posti[tav]>n && att65n==0)
               break;
         }
      }
      else
      {
         for(tav=0; tav<NUM_TAVOLI; tav++)
         {
            if(tav<NUM_TAVOLI4 && posti[tav]==2)
               break;
            else if(tav<NUM_TAVOLI4 && posti[tav]==4)
               break;
            else if(tav>=NUM_TAVOLI4 && posti[tav]<=4 && att65n==0)
               break;
            else if(tav>=NUM_TAVOLI4 && posti[tav]>4 && att65n==0)
               break;
         }
      }

      tav = (tav>=0 && tav!=NUM_TAVOLI)?tav:-1;

      return tav;
   }


   /**
      tentativo di assegnazione di un tavolo con l'aiuto del metodo libero()
      in caso di mancanza di tavoli idonei deve fare aspettare il gruppo di clienti
      @param n numero persone del gruppo che vogliono sedersi
      @return numero tavolo assegnato
   */
   public int entra(int n)
   {
      int tav = libero(n);

      mutex.p();

      if(tav==-1)
      {
         if(n==6 || n==5)
         {
            att65n++;
            mutex.v();
            att65.p();
            att65n--;
         }
         else if(n==3)
         {
            att3n++;
            mutex.v();
            att3.p();
            att3n--;
         }
         else if(n==4)
         {
            att4n++;
            mutex.v();
            att4.p();
            att4n--;
         }
         else
         {
            att2n++;
            mutex.v();
            att2.p();
            att2n--;
         }

         tav = libero(n);
      }


      posti[tav]-=n;

      mutex.v();
      return tav;
   }

   /**
      decreta la terminazione del servizio e l'uscita del gruppo di numerosit� n
      @param tav tavolo in cui si era seduto il gruppo
      @param n numero persone del gruppo che termina il servizio
   */
   public void termina(int tav, int n)
   {
      mutex.p();
      posti[tav]+=n;

      tavoli();

      if(tav>=NUM_TAVOLI4)
      {
         if(posti[tav]==6 && att65n>0)
            att65.v();
         else if(posti[tav]==4 && att4n>0)
            att4.v();
         else if(posti[tav]==3 && att3n>0)
            att3.v();
         else if(posti[tav]==2 && att2n>0)
            att2.v();
         else if(posti[tav]>4 && att4n>0)
            att4.v();
         else if(posti[tav]>3 && att3n>0)
            att3.v();
         else if(posti[tav]>2 && att2n>0)
            att2.v();
      }
      else
      {
         if(posti[tav]==4 && (att4n>0 || att3n>0))
         {
            if(att4n>0)
               att4.v();
            else
               att3.v();
         }
         else if(posti[tav]>=2 && att2n>0)
            att2.v();
      }
      mutex.v();
   }

   private class Clienti extends Thread
   {
      private int gruppo;
      private int num_persone;

      public Clienti(int gruppo, int num_persone)
      {
         this.gruppo = gruppo;
         this.num_persone = num_persone;
      }

      public void run()
      {
         //Assegnazione del tavolo
         int tav = entra(num_persone);

         //Simulazione del servizio
         Util.rsleep(minT, maxT);

         //Terminazione del servizio
         termina(tav, num_persone);
      }
   }

   public static void main(String[] args)
   {
      HappySSem hs = new HappySSem(Long.parseLong(args[0]), Long.parseLong(args[1]));

      for(int i=0; i<NUM_GROUPS ; i++)
      {
         int num_clienti = Util.randVal(2, 6);

         hs.new Clienti(i, num_clienti).start();

         Util.rsleep(100L, 300L);
      }
   }

}
