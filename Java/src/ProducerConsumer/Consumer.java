/**
@author Di Nardo Di Maio Raffaele
*/

package ProducerConsumer;

import os.Util;

public class Consumer extends Thread
{
   private int num;
   private ProducerConsumer pc;
   private long min;
   private long max;

   public Consumer(int num, ProducerConsumer pc, long min, long max)
   {
      this.num = num;
      this.pc = pc;
      this.min = min;
      this.max = max;

   }

   public void run()
   {
      while(true)
      {
         System.out.println("Il consumatore P"+num+" arriva per consumare");
         String s = (String) pc.read();
         Util.rsleep(min, max);
         System.out.println("Il consumatore P"+num+" ha consumato "+s);
      }
   }
}
