/**
@author Di Nardo Di Maio Raffaele
*/

package ProducerConsumer;

import os.Semaphore;

public class PC_Semaphore_Parallel extends ProducerConsumer
{
   Semaphore mutexProd = new Semaphore(true);
   Semaphore mutexCons = new Semaphore(true);
   Semaphore occupied = new Semaphore(false);
   Semaphore available = new Semaphore(false);

   public PC_Semaphore_Parallel()
   {
      initState();
   }

   public Object read()
   {
      mutexCons.p();

      if(buff.num_Full()==0)
         occupied.p();

      Object o = buff.read();
      printState();

      if(available.queue()>0)
         available.v();

      mutexCons.v();

      return o;
   }

   public void write(Object o)
   {
      mutexProd.p();

      if(buff.num_Empty()==0)
         available.p();

      buff.write(o);
      printState();

      if(occupied.queue()>0)
         occupied.v();

      mutexProd.v();
   }
}
