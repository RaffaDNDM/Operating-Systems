/**
   @author Di Nardo Di Maio Raffaele 1204879

   ---------------------------------------------------------------------------------
                                    Traccia 5
   ---------------------------------------------------------------------------------
   Realizzare la classe Spinlock che implementa il semaforo numerico in una forma
   con parziale busy-waiting. Si forniscano i metodi p(), p(long timeout), v()
   come nella classe Semaphore. Il thread che deve attendere si spospende con uno
   sleep per un tempo variabile prima di rivalutare la condizione sul semaforo. Per
   ridurre il busy waiting tale tempo vale, nell'ordine, 8*T, 4*T, 2*T, 1*T, 8*T,
   4*T ecc. ciclicamente finchè l'attesa termina con la condizione verificata. T sia
   una costante temporale adeguatamente impostata.
   Collaudare la classe Spinlock con un modello Produttore/Consumatore a buffer
   multiplo utilizzando le classi Producer, Consumer, TestPC della libreria.
   ---------------------------------------------------------------------------------
*/

package Spinlock;

public interface Synchronization
{
   /**
      wait call
   */
   void p();
   
   
   /**
      wait call
      @param timeout max waiting time
      @return remaining time w.r.t. timeout
   */
   long p(long timeout);
   
   
   /**
      signal call
   */   
   void v();
}