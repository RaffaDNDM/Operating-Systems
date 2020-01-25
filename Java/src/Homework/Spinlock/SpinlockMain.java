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

import java.util.Scanner;
import os.Producer;
import os.Consumer;
import osTest.TestPC;
import os.Buffer;

public class SpinlockMain extends TestPC
{
   public static void main(String[] args)
   {
      Scanner input = new Scanner(System.in);

      int num_elements = 0;
      boolean flag = false;

      //Choice of number of elements in the buffer
      while(!flag)
      {
         System.out.println("Choose number of elements in the buffer");

         try
         {
            num_elements = Integer.parseInt(input.nextLine());

            if(num_elements>0)
               flag = true;
         }
         catch(NumberFormatException e)
         {
            System.out.println("It's not an integer");
         }
      }

      //Definition of the buffer
      Buffer buf = new SyncMultiBuf(num_elements);
      //Testing phase
      test(buf);
   }
}
