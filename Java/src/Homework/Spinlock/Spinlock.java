/**
   @author Di Nardo Di Maio Raffaele

   ---------------------------------------------------------------------------------
                                    Traccia 5
   ---------------------------------------------------------------------------------
   Realizzare la classe Spinlock che implementa il semaforo numerico in una forma
   con parziale busy-waiting. Si forniscano i metodi p(), p(long timeout), v()
   come nella classe Semaphore. Il thread che deve attendere si spospende con uno
   sleep per un tempo variabile prima di rivalutare la condizione sul semaforo. Per
   ridurre il busy waiting tale tempo vale, nell'ordine, 8*T, 4*T, 2*T, 1*T, 8*T,
   4*T ecc. ciclicamente finch� l'attesa termina con la condizione verificata. T sia
   una costante temporale adeguatamente impostata.
   Collaudare la classe Spinlock con un modello Produttore/Consumatore a buffer
   multiplo utilizzando le classi Producer, Consumer, TestPC della libreria.
   ---------------------------------------------------------------------------------
*/

package Spinlock;

import os.Util;
import os.Timeout;

public class Spinlock implements Synchronization
{
   //number of marks initially available
   private int num;
   //max number of possible marks
   private int max;
   //time used to define when the process needs to check again condition
   private final static long TIME=500L;
   //waiting processes
   private int count_enqueued = 0;

   //next ticket for normal processes
   private int ticket_norm = 0;
   //next normal process that is going to be served
   private int service_norm = 0;
   //next ticket for processes with timeout
   private int ticket_time = 0;
   //next process with timeout that is going to be served
   private int service_time = 0;

   /**
      Number Semaphore
      @param init initially available marks
      @param num max number of marks
   */
   public Spinlock(int init, int num)
   {
      this.num = init;
      this.max = num;
   }

   /**
      Number Semaphore
      @param num max number of marks = initially available marks
   */
   public Spinlock(int num)
   {
      this.num = this.max = num;
   }

   public void p()
   {
      int ticket;
      int i = 16;

      synchronized(this)
      {
         //Assignment of ticket to process
         ticket=ticket_norm++;
         //increase number of processes waiting for their turn
         count_enqueued++;
      }

      /**
         Waiting condition
         -> no marks available
         -> the process isn't the first in the queue
      */
      while(ticket!=service_norm || num<1)
      {
         try
         {
            i = (i!=1)? i>>1 : 8;
            Thread.sleep(i*TIME);
         }
         catch(InterruptedException e)
         {
            System.out.println("Interrupted background");
         }
      }

      synchronized(this)
      {
         //decrease number of processes waiting for their turn
         count_enqueued--;
         //update of service ticket
         service_norm++;
         //get a mark
         num--;
      }
   }

   public long p(long timeout)
   {
      if(timeout==Timeout.NOTIMEOUT)
      {
         //no timeout variable
         p();
         return 1;
      }
      else
      {
         long begin = System.currentTimeMillis();
         long end = 0;
         int i = 16;
         int ticket;

         synchronized(this)
         {
            //Assignment of ticket to the process
            ticket = ticket_time++;
            //increase number of processes waiting for their turn
            count_enqueued++;
         }

         /**
            Waiting condition
            -> no marks available
            -> the process isn't the first in the queue
         */
         while(ticket!=service_time || num<1)
         {
            try
            {
               i = (i!=1)? i>>1 : 8;
               Thread.sleep(i*TIME);

               end = System.currentTimeMillis();

               if((end-begin)>=timeout)
               {
                  //the process waiting time is expired
                  return Timeout.EXPIRED;
               }
            }
            catch(InterruptedException e)
            {
               System.out.println("Interrupted background");
            }
         }

         synchronized(this)
         {
            //update of service ticket
            service_time++;
            //decrease number of processes waiting for their turn
            count_enqueued--;
            //get a mark
            num--;
         }

         return end-begin;
      }
   }

   public synchronized void v()
   {
      //release a mark
      if(num<max)
         num++;
   }

   /**
      number of marks in the semaphore
      @return number of available marks
   */
   public int value()
   {
      return num;
   }
}
