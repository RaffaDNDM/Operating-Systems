/**
   @author Di Nardo Di Maio Raffaele
*/

package PoolAllocator;

import java.util.*;

public abstract class PoolAllocator
{
   private final String LINE = "------------------------------------------";
   //number of resources R1
   protected int r1;
   //number of resources R2
   protected int r2;
   //number of resources R1 in this moment
   protected int num1;
   //number of resources R2 in this moment
   protected int num2;
   //served requests without timeout
   protected int served=0;

   protected class Request
   {
      //number of already allocated R1 resources by the request
      private int n1;
      //number of already allocated R2 resources by the request
      private int n2;
      //number of max declared R1 resources by the request
      private int max1;
      //number of max declared R2 resources by the request
      private int max2;

      /**
         Request of resources by process
         @param max1 max number of resources R1 requested by the process
         @param max2 max number of resources R2 requested by the process
      */
      public Request(int max1, int max2)
      {
         this.n1 = 0;
         this.n2 = 0;
         this.max1 = max1;
         this.max2 = max2;
      }

      /**
         Allocation of resources
         @param x1 number of resources R1 that needs to be allocated
         @param x2 number of resources R2 that needs to be allocated
      */
      public void allocate(int x1, int x2)
      {
         this.n1 += x1;
         this.n2 += x2;
      }

      /**
         Number of already allocated resources R1 by the process
         @return number of already allocated R1 resources
      */
      public int get1()
      {
         return n1;
      }

      /**
         Number of already allocated resources R2 by the process
         @return number of already allocated R2 resources
      */
      public int get2()
      {
         return n2;
      }

      /**
         Number of max declared resources R1 by the process
         @return number of max declared R1 resources
      */
      public int getMax1()
      {
         return max1;
      }

      /**
         Number of max declared resources R2 by the process
         @return number of max declared R2 resources
      */
      public int getMax2()
      {
         return max2;
      }
   }

   //Dictionary of requests (key = Thread obj, value = Request obj)
   protected HashMap<Thread, Request> requests = new HashMap<>();

   /**
      Creation of the pool of resources
      @param num1 number of R1 resources in the pool
      @param num2 number of R2 resources in the pool
   */
   public PoolAllocator(int num1, int num2)
   {
      this.num1 = this.r1 = num1;
      this.num2 = this.r2 = num2;
   }

   /*
   il thread chiamante dichiara che richiedera' al massimo, rispettivamente,
   max1 per r1 e max2 per r2 risorse complessive prima di restituire
   tutte quelle che ha precedentemente (al rilascio) allocate;
   il metodo solleva l'eccezione NotAllowedException se
   la richiesta viene effettuata quando il thread chiamante ha qualcuna
   delle risorse di r1 e/o r2.
   */
   public abstract void register(int max1, int max2);

   /**
   chiamata non sospensiva per allocare rispettivamente req1 e req2
   risorse da r1 e r2; restituisce true se l'allocazione ha avuto luogo,
   false se non era immediatamente possibile; solleva NotAllowedException
   se una richiesta supera il massimo dichiarato (attualmente valido,
   cio� quello dell'ultima chiamata register()).
   */
   public abstract boolean alloc(int req1, int req2);

   /**
   come la precedente ma sospensiva fino a quando l'allocazione � possibile
   o � scaduto il timeout.
   */
   public abstract boolean alloc(int req1, int req2, long timeout);

   /**
   il thread chiamante rilascia tutte le risorse che aveva precedentemente
   allocato.
   */
   public abstract void release();

   /**
   Restituisce il numero di risorse di tipo r1 ed r2 allocate al thread
   chiamante: adottare un opportuno sistema di parametri per restituire
   questa informazione
   */
   public void query()
   {
      Thread t = Thread.currentThread();
      Request r = requests.get(t);

      ((Process) t).setDoneR1(r.get1());
      ((Process) t).setDoneR2(r.get2());
   }

   /**
      Print the current state of the pool, showing:
      -> number of available resources
      -> completed requests
   */
   public void printState()
   {
      System.out.println(LINE);
      System.out.printf("    resources1:  %2d   resources2:  %2d\n", num1, num2);
      System.out.printf("           served:  %2d\n", served);
      System.out.println(LINE);
   }

   /**
      Number of available R1 resources
      @return number of available R1 resources
   */
   public int num_R1()
   {
      return r1;
   }

   /**
      Number of available R2 resources
      @return number of available R2 resources
   */
   public int num_R2()
   {
      return r2;
   }
}
