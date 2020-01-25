/**
@author Di Nardo Di Maio Raffaele
*/

package ProducerConsumer;

import os.Util;

public class Producer extends Thread
{
   private int ticketMSG = 0;
   private int num;
   private ProducerConsumer pc;
   private long min;
   private long max;

   public Producer(int num, ProducerConsumer pc, long min, long max)
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
         System.out.println("Il produttore P"+num+" arriva per produrre");
         pc.write((Object) ("P"+num+"_"+ticketMSG));
         Util.rsleep(min, max);
         System.out.println("Il produttore P"+num+" ha prodotto "+"P"+num+"_"+ticketMSG);
         ticketMSG++;
      }
   }
}
