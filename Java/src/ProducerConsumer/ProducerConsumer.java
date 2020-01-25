/**
@author Di Nardo Di Maio Raffaele
*/

package ProducerConsumer;

import java.util.Scanner;

public abstract class ProducerConsumer<T>
{
   protected Buffer buff;
   public abstract Object read();
   public abstract void write(Object o);

   public void initState()
   {
      Scanner input = new Scanner(System.in);

      System.out.println("Insert the number of slots for the buffer?");

      int n = Integer.parseInt(input.nextLine());

      buff = new Buffer(n);
   }

   public void printState()
   {
      System.out.println("-----------------------");
      System.out.printf("|Available slots: %4d|\n", buff.num_Empty());
      System.out.printf("| Occupied slots: %4d|\n", buff.num_Full());
      System.out.println("-----------------------");
   }
}
